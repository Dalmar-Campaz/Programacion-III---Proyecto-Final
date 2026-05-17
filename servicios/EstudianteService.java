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

    public EstudianteService(Pila<Operacion> pilaDeshacer,
                               Pila<Operacion> pilaRehacer) {
        this.estudiantes  = new HashMap<>();
        this.pilaDeshacer = pilaDeshacer;
        this.pilaRehacer  = pilaRehacer;
    }

    public void registrarEstudiante(String nombre, String id,
                                     String email, int semestre) {
        Estudiante nuevo = new Estudiante(nombre, id, email, semestre);
        estudiantes.put(id, nuevo);

        pilaDeshacer.push(new Operacion(
            Operacion.REGISTRAR_ESTUDIANTE,
            id,
            "Registrar estudiante: " + nombre + " (ID: " + id + ")"
        ));

        System.out.println("Estudiante registrado exitosamente.");
    }

    public Estudiante buscarEstudiante(String id)
            throws EstudianteNoEncontradoException {
        Estudiante e = estudiantes.get(id);
        if (e == null) {
            throw new EstudianteNoEncontradoException(
                "No existe estudiante con ID: " + id
            );
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
            "Eliminar estudiante: " + e.getNombre() + " (ID: " + id + ")"
        ));

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