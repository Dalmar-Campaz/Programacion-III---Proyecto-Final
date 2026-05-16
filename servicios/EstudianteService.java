
package servicios;

import modelos.Estudiante;
import estructuras.Pila;
import excepciones.EstudianteNoEncontradoException;
import util.Operacion;
import java.util.HashMap;

class EstudianteService {

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
        pilaDeshacer.push(new Operacion(Operacion.ELIMINAR_ESTUDIANTE, eliminado,
                "Eliminar estudiante: " + eliminado.getNombre() + "(ID: " + eliminado.getId() + ")"));
        estudiantes.remove(id);
    }
}
