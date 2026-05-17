package servicios;

import modelos.Materia;
import modelos.Estudiante;
import estructuras.LinkedList;
import estructuras.Pila;
import excepciones.EstudianteNoEncontradoException;
import excepciones.PreRequisitoNoAprobadoException;
import util.Operacion;
import java.util.HashMap;

public class MateriaService {

    private HashMap<String, Materia> materias;
    private EstudianteService estudianteServicio;
    private Pila<Operacion> pilaDeshacer;
    private Pila<Operacion> pilaRehacer;

    public MateriaService(EstudianteService estudianteServicio,
            Pila<Operacion> pilaDeshacer,
            Pila<Operacion> pilaRehacer) {
        this.materias = new HashMap<>();
        this.estudianteServicio = estudianteServicio;
        this.pilaDeshacer = pilaDeshacer;
        this.pilaRehacer = pilaRehacer;
    }

    public void crearMateria(String codigo, String nombre,
            int cuposMaximos, int creditos) {
        Materia nueva = new Materia(codigo, nombre, cuposMaximos, creditos);
        materias.put(codigo, nueva);
        System.out.println("Materia creada: " + nombre + " (" + codigo + ")");
    }

    public Materia buscarMateria(String codigo)
            throws Exception {
        Materia m = materias.get(codigo);
        if (m == null) {
            throw new Exception(
                    "No existe materia con codigo: " + codigo);
        }
        return m;
    }

    public void agregarPrerequisito(String codigoMateria, String codigoPrerequisito) throws Exception {
        Materia m = buscarMateria(codigoMateria);
        m.agregarPrerequisito(codigoPrerequisito);
        System.out.println("Prerequisito " + codigoPrerequisito +
                " agregado a " + codigoMateria);
    }

    public void inscribir(String idEstudiante, String codigoMateria)
            throws EstudianteNoEncontradoException,
            PreRequisitoNoAprobadoException,
            Exception {

        Estudiante e = estudianteServicio.buscarEstudiante(idEstudiante);
        Materia m = buscarMateria(codigoMateria);

        verificarPrerequisitos(e, m);

        if (!m.hayCuposDisponibles()) {
            m.agregarAColaEspera(idEstudiante);
            System.out.println("Materia llena. " + e.getNombre() +
                    " agregado a cola de espera." +
                    " Posición: " + m.cantidadEnEspera());
            return;
        }

        m.ocuparCupo();
        e.getMateriasInscritas().add(codigoMateria);

        pilaDeshacer.push(new Operacion(
                Operacion.INSCRIBIR_MATERIA,
                new String[] { idEstudiante, codigoMateria },
                "Inscribir " + e.getNombre() + " en " + m.getNombre()));

        System.out.println("Inscripción exitosa: " + e.getNombre() +
                " en " + m.getNombre() +
                " (cupos restantes: " +
                (m.getCuposMaximos() - m.getCuposOcupados()) + ")");
    }

    public void cancelarInscripcion(String idEstudiante,
            String codigoMateria)
            throws EstudianteNoEncontradoException,
            Exception {

        Estudiante e = estudianteServicio.buscarEstudiante(idEstudiante);
        Materia m = buscarMateria(codigoMateria);

        m.liberarCupo();
        e.getMateriasInscritas().delete(codigoMateria);
        System.out.println("Cancelación exitosa. Cupo liberado.");

        String siguienteId = m.siguienteEnEspera();
        if (siguienteId != null) {
            try {
                Estudiante siguiente = estudianteServicio
                        .buscarEstudiante(siguienteId);
                m.ocuparCupo();
                siguiente.getMateriasInscritas().add(codigoMateria);
                System.out.println("Cupo asignado a: " +
                        siguiente.getNombre() +
                        " (primer estudiante en cola)");
            } catch (EstudianteNoEncontradoException ex) {
                System.out.println("Estudiante en cola no encontrado: "
                        + siguienteId);
            }
        }

        pilaDeshacer.push(new Operacion(
                Operacion.CANCELAR_INSCRIPCION,
                new String[] { idEstudiante, codigoMateria },
                "Cancelar inscripción de " + e.getNombre() +
                        " en " + m.getNombre()));
    }

    private void verificarPrerequisitos(Estudiante e, Materia m)
            throws PreRequisitoNoAprobadoException {

        LinkedList<String> prerequisitos = m.getPrerequisitos();
        LinkedList<String> historial = e.getMateriasAprovadas();

        for (int i = 0; i < prerequisitos.getTamaño(); i++) {
            String prerequisito = prerequisitos.obtener(i);
            if (!historial.contiene(prerequisito)) {
                throw new PreRequisitoNoAprobadoException(
                        e.getNombre() + " no ha aprobado el prerequisito: "
                                + prerequisito);
            }
        }
    }

    public void mostrarColaEspera(String codigoMateria)
            throws Exception {
        Materia m = buscarMateria(codigoMateria);
        System.out.println("=== COLA DE ESPERA: " + m.getNombre() + " ===");
        System.out.println("En espera: " + m.cantidadEnEspera());
        m.getColaEspera().imprimir();
    }

    public void listarMaterias() {
        if (materias.isEmpty()) {
            System.out.println("No hay materias registradas.");
            return;
        }
        System.out.println("=== MATERIAS REGISTRADAS ===");
        for (Materia m : materias.values()) {
            m.mostrarInformacion();
            System.out.println("───────────────────────────");
        }
    }

    public HashMap<String, Materia> getMaterias() {
        return materias;
    }

}
