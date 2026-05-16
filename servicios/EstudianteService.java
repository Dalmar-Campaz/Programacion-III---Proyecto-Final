
package servicios;

import modelos.Estudiante;
import estructuras.Pila;
import excepciones.EstudianteNoEncontradoException;
import util.Operacion;
import java.util.HashMap;

public class EstudianteService {

    private HashMap<String, Estudiante> estudiantes;
    private Pila<Operacion> pilaDeshacer;
    private Pila<Operacion> pilaRehacer;

    public EstudianteService() {
        this.pilaDeshacer = new Pila<>();
        this.pilaRehacer = new Pila<>();
        this.estudiantes = new HashMap<>();
    }

    public void registrarEstudiante(String nombre, String id, String email, int semestre) {
        Estudiante nuevoEstudiante = new Estudiante(nombre, id, email, semestre);

        estudiantes.put(id, nuevoEstudiante);
        pilaDeshacer.push(new Operacion(Operacion.REGISTRAR_ESTUDIANTE, id,
                "Registrar estudiante: " + nombre + "(ID: " + id + ")"));
        pilaRehacer = new Pila<>();

        System.out.println("Estudiante registrado exitosamente.");
    }

    public Estudiante buscarEstudiante(String id) throws EstudianteNoEncontradoException {
        Estudiante query = estudiantes.get(id);
        if (query == null) {
            throw new EstudianteNoEncontradoException("No existe estudiante con ID: " + id);
        }

        return query;
    }

    public void eliminarEstudiante(String id) throws EstudianteNoEncontradoException {
        Estudiante eliminado = buscarEstudiante(id);

        estudiantes.remove(id);

        pilaDeshacer.push(new Operacion(Operacion.ELIMINAR_ESTUDIANTE, eliminado,
                "Eliminar estudiante: " + eliminado.getNombre() + "(ID: " + eliminado.getId() + ")"));

        pilaRehacer = new Pila<>();

        System.out.println("Estudiante eliminado exitosamente");
    }

    public void listarEstudiantes() {

        if (estudiantes.isEmpty()) {
            System.out.println("No hay estudiantes registrados");
            return;
        }

        System.out.println("=== ESTUDIANTES REGISTRADOS ===");
        for (Estudiante e : estudiantes.values()) {
            e.mostrarInformacion();
        }
    }

    public void deshacer() {
        if (pilaDeshacer.estaVacia()) {
            System.out.println("No hay acciones para deshacer");
            return;
        }

        Operacion op = pilaDeshacer.pop();
        System.out.println("deshaciendo...");

        if (op.getTipo().equals(Operacion.REGISTRAR_ESTUDIANTE)) {
            String id = (String) op.getDato();
            estudiantes.remove(id);
            System.out.println("Estudiante eliminado del sistema");
        } else if (op.getTipo().equals(Operacion.ELIMINAR_ESTUDIANTE)) {
            Estudiante e = (Estudiante) op.getDato();
            estudiantes.put(e.getId(), e);
            System.out.println("Estudiante restaurado: " + e.getNombre());
        }

        pilaRehacer.push(op);
    }

    public void rehacer() {
        if (pilaRehacer.estaVacia()) {
            System.out.println("No hay acciones para rehacer");
            return;
        }

        Operacion op = pilaRehacer.pop();
        System.out.println("restaurando...");

        if (op.getTipo().equals(Operacion.REGISTRAR_ESTUDIANTE)) {
            Estudiante e = (Estudiante) op.getDato();
            estudiantes.put(e.getId(), e);
            System.out.println("Estudiante restaurado: " + e.getNombre());
        } else if (op.getTipo().equals(Operacion.ELIMINAR_ESTUDIANTE)) {
            String id = (String) op.getDato();
            estudiantes.remove(id);
            System.out.println("Estudiante eliminado del sistema");
        }

        pilaDeshacer.push(op);

    }

    public HashMap<String, Estudiante> getEstudiantes() {
        return estudiantes;
    }
}
