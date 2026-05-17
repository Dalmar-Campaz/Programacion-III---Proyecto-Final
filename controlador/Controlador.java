package controlador;

import estructuras.*;
import modelos.Estudiante;
import util.*;
import servicios.*;

public class Controlador {

    // Una sola pila compartida por todos los serviceHorarioServices
    private Pila<Operacion> pilaDeshacer;
    private Pila<Operacion> pilaRehacer;

    private EstudianteService estudianteService;
    private MateriaService materiaService;

    public Controlador() {
        // Crear las pilas UNA SOLA VEZ
        this.pilaDeshacer = new Pila<>();
        this.pilaRehacer = new Pila<>();

        // Pasar las mismas pilas a todos los serviceHorarioServices
        this.estudianteService = new EstudianteService(pilaDeshacer, pilaRehacer);
        this.materiaService = new MateriaService(estudianteService, pilaDeshacer, pilaRehacer);
    }
    public void deshacer() {
    if (pilaDeshacer.estaVacia()) {
        System.out.println("Error: no hay operaciones para deshacer.");
        return;
    }

    Operacion op = pilaDeshacer.pop();
    System.out.println("Deshaciendo: " + op.getDescripcion());

    switch (op.getTipo()) {

        case Operacion.REGISTRAR_ESTUDIANTE:
            // Deshacer registro = eliminar el estudiante
            String id = (String) op.getDato();
            estudianteService.getEstudiantes().remove(id);
            System.out.println("Estudiante eliminado del sistema.");
            break;

        case Operacion.ELIMINAR_ESTUDIANTE:
            // Deshacer eliminación = restaurar objeto completo
            Estudiante e = (Estudiante) op.getDato();
            estudianteService.getEstudiantes().put(e.getId(), e);
            System.out.println("Estudiante restaurado: " + e.getNombre());
            break;

        case Operacion.INSCRIBIR_MATERIA:
            // Deshacer inscripción = cancelar
            String[] datos = (String[]) op.getDato();
            // datos[0] = idEstudiante, datos[1] = codigoMateria
            try {
                materiaService.cancelarInscripcion(datos[0], datos[1]);
            } catch (Exception ex) {
                System.out.println("Error al deshacer inscripción: "
                                   + ex.getMessage());
            }
            break;

        case Operacion.CANCELAR_INSCRIPCION:
            // Deshacer cancelación = volver a inscribir
            String[] datosCancelacion = (String[]) op.getDato();
            try {
                materiaService.inscribir(
                    datosCancelacion[0], datosCancelacion[1]
                );
            } catch (Exception ex) {
                System.out.println("Error al deshacer cancelación: "
                                   + ex.getMessage());
            }
            break;
    }

    pilaRehacer.push(op);
}

public void rehacer() {
    if (pilaRehacer.estaVacia()) {
        System.out.println("Error: no hay operaciones para rehacer.");
        return;
    }

    Operacion op = pilaRehacer.pop();
    System.out.println("Rehaciendo: " + op.getDescripcion());

    // La lógica es inversa a deshacer
    switch (op.getTipo()) {

        case Operacion.ELIMINAR_ESTUDIANTE:
            Estudiante e = (Estudiante) op.getDato();
            estudianteService.getEstudiantes().remove(e.getId());
            System.out.println("Estudiante eliminado nuevamente.");
            break;

        case Operacion.INSCRIBIR_MATERIA:
            String[] datos = (String[]) op.getDato();
            try {
                materiaService.inscribir(datos[0], datos[1]);
            } catch (Exception ex) {
                System.out.println("Error al rehacer inscripción: "
                                   + ex.getMessage());
            }
            break;
    }

    pilaDeshacer.push(op);
}
}