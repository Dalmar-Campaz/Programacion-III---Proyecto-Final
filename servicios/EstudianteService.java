package servicios;

import modelos.Estudiante;
import estructuras.Pila;
import excepciones.EstudianteNoEncontradoException;
import util.Operacion;
import java.util.HashMap;

public class EstudianteService {

    private HashMap<String, Estudiante> estudiantes;
    private Pila<Operacion> pilaDeshacer;

    public EstudianteService(Pila<Operacion> pilaDeshacer,
            Pila<Operacion> pilaRehacer) {
        this.estudiantes = new HashMap<>();
        this.pilaDeshacer = pilaDeshacer;
    }

    public void registrarEstudiante(String nombre, String id,
            String email, int semestre) {
        Estudiante nuevo = new Estudiante(nombre, id, email, semestre);
        estudiantes.put(id, nuevo);

        pilaDeshacer.push(new Operacion(
                Operacion.REGISTRAR_ESTUDIANTE,
                id,
                "Registrar estudiante: " + nombre + " (ID: " + id + ")"));

        System.out.println("Estudiante registrado exitosamente.");
    }

    public void registrarNota(String idEstudiante, String codigoMateria, int semestre, double nota)
            throws EstudianteNoEncontradoException, Exception {
        if (nota < 0.0 || nota > 5.0) {
            throw new Exception("La nota debe estar entre 0.0 y 5.0");
        }

        if (semestre < 1 || semestre > 10) {
            throw new Exception("El semestre debe estar entre 1 y 10");
        }

        Estudiante e = buscarEstudiante(idEstudiante);

        boolean registrado = e.registrarNota(semestre, nota);
        if (!registrado) {
            throw new Exception("No se pudo registrar la nota, el semestre tiene el maximo de notas registradas");
        }

        if (nota >= 3.0) {
            e.getMateriasInscritas().delete(codigoMateria);
            e.getMateriasAprovadas().add(codigoMateria);
        } else {
            e.getMateriasInscritas().delete(codigoMateria);
            System.out.println("El estudiante no aprobó la materia");
        }

        pilaDeshacer.push(
                new Operacion(Operacion.REGISTRAR_NOTA, new Object[] { idEstudiante, codigoMateria, semestre, nota },
                        "Registrar nota: " + nota + " a " + e.getNombre() + " (ID: " + idEstudiante + ")"
                                + " en materia: " + codigoMateria));
    }

    public Estudiante buscarEstudiante(String id)
            throws EstudianteNoEncontradoException {
        Estudiante e = estudiantes.get(id);
        if (e == null) {
            throw new EstudianteNoEncontradoException(
                    "No existe estudiante con ID: " + id);
        }
        return e;
    }

    public void eliminarEstudiante(String id)
            throws EstudianteNoEncontradoException {
        Estudiante e = buscarEstudiante(id);
        estudiantes.remove(id);

        pilaDeshacer.push(new Operacion(
                Operacion.ELIMINAR_ESTUDIANTE,
                e,
                "Eliminar estudiante: " + e.getNombre() + " (ID: " + id + ")"));

        System.out.println("Estudiante eliminado exitosamente.");
    }

    public void listarEstudiantes() {
        if (estudiantes.isEmpty()) {
            System.out.println("No hay estudiantes registrados.");
            return;
        }
        System.out.println("=== ESTUDIANTES REGISTRADOS ===");
        for (Estudiante e : estudiantes.values()) {
            e.mostrarInformacion();
            System.out.println("───────────────────────────────");
        }
    }

    public HashMap<String, Estudiante> getEstudiantes() {
        return estudiantes;
    }
}