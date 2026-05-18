package controlador;

import estructuras.*;
import modelos.Estudiante;
import util.*;
import servicios.*;

public class Controlador {

    private Pila<Operacion> pilaDeshacer;
    private Pila<Operacion> pilaRehacer;
    private HorarioService horarioService;
    private EstudianteService estudianteService;
    private MateriaService materiaService;

    public Controlador() {
        this.pilaDeshacer = new Pila<>();
        this.pilaRehacer = new Pila<>();
        this.horarioService = new HorarioService(pilaDeshacer, pilaRehacer);
        this.estudianteService = new EstudianteService(pilaDeshacer, pilaRehacer);
        this.materiaService = new MateriaService(estudianteService, pilaDeshacer, pilaRehacer);
    }

    public void deshacer() {
        if (pilaDeshacer.estaVacia()) {
            System.out.println("No hay acciones para deshacer");
            return;
        }

        Operacion op = pilaDeshacer.pop();

        switch (op.getTipo()) {

            case Operacion.REGISTRAR_ESTUDIANTE:

                String id = (String) op.getDato();

                estudianteService.getEstudiantes().remove(id);
                System.out.println("Estudiante con ID: " + id + " eliminado del sistema.");
                break;

            case Operacion.ELIMINAR_ESTUDIANTE:

                Estudiante e = (Estudiante) op.getDato();
                estudianteService.getEstudiantes().put(e.getId(), e);
                System.out.println("Estudiante restaurado: " + e.getNombre());
                break;

            case Operacion.INSCRIBIR_MATERIA:

                String[] datos = (String[]) op.getDato();

                try {
                    materiaService.cancelarInscripcion(datos[0], datos[1]);
                } catch (Exception error) {
                    System.out.println("Error al deshacer inscripción: "
                            + error.getMessage());
                }
                break;

            case Operacion.CANCELAR_INSCRIPCION:

                String[] datosCancelacion = (String[]) op.getDato();
                try {
                    materiaService.inscribir(
                            datosCancelacion[0], datosCancelacion[1]);
                } catch (Exception error) {
                    System.out.println("Error al deshacer cancelación: "
                            + error.getMessage());
                }
                break;
            case Operacion.RESERVAR_HORARIO:
                Object[] datosReserva = (Object[]) op.getDato();
                String nombreAula = (String) datosReserva[0];
                int dia = (int) datosReserva[1];
                int hora = (int) datosReserva[2];
                int duracion = (int) datosReserva[3];
                try {
                    horarioService.liberar(nombreAula, dia, hora, duracion);
                    System.out.println("Reserva deshecha en aula " + nombreAula);
                } catch (Exception error) {
                    System.out.println("Error al deshacer reserva: " + error.getMessage());
                }
                break;

            case Operacion.LIBERAR_HORARIO:

                Object[] datosLiberar = (Object[]) op.getDato();
                try {
                    horarioService.reservar(
                            (String) datosLiberar[0],
                            (int) datosLiberar[1],
                            (int) datosLiberar[2],
                            (int) datosLiberar[3]);
                    System.out.println("Liberación deshecha.");
                } catch (Exception error) {
                    System.out.println("Error al deshacer liberación: " + error.getMessage());
                }
                break;
            case Operacion.REGISTRAR_NOTA:
                // Deshacer nota — reducir el contador del semestre
                Object[] datosNota = (Object[]) op.getDato();
                String idEst = (String) datosNota[0];
                int semestreN = (int) datosNota[2];
                try {
                    Estudiante est = estudianteService.buscarEstudiante(idEst);
                    est.getCantidadNotas()[semestreN - 1]--;
                    System.out.println("Nota deshecha.");
                } catch (Exception ex) {
                    System.out.println("Error al deshacer nota: " + ex.getMessage());
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

        // La lógica es inversa a deshacer
        switch (op.getTipo()) {
            case Operacion.REGISTRAR_ESTUDIANTE:
                Estudiante e = (Estudiante) op.getDato();
                estudianteService.getEstudiantes().put(e.getId(), e);
                System.out.println("Estudiante registrado nuevamente con ID: " + e.getId());
                break;

            case Operacion.ELIMINAR_ESTUDIANTE:
                String id = (String) op.getDato();

                estudianteService.getEstudiantes().remove(id);
                System.out.println("Estudiante con ID: " + id + " eliminado del sistema.");
                break;

            case Operacion.INSCRIBIR_MATERIA:
                String[] datos = (String[]) op.getDato();
                try {
                    materiaService.inscribir(datos[0], datos[1]);
                } catch (Exception error) {
                    System.out.println("Error al rehacer inscripción: "
                            + error.getMessage());
                }
                break;

            case Operacion.CANCELAR_INSCRIPCION:

                String[] datosCancelacion = (String[]) op.getDato();
                try {
                    materiaService.cancelarInscripcion(datosCancelacion[0], datosCancelacion[1]);
                } catch (Exception error) {
                    System.out.println("Error al rehacer cancelación: " + error.getMessage());
                }
                break;

            case Operacion.RESERVAR_HORARIO:

                Object[] datosReserva = (Object[]) op.getDato();
                try {
                    horarioService.reservar(
                            (String) datosReserva[0],
                            (int) datosReserva[1],
                            (int) datosReserva[2],
                            (int) datosReserva[3]);
                    System.out.println("Reserva rehecha.");
                } catch (Exception error) {
                    System.out.println("Error al rehacer reserva: " + error.getMessage());
                }
                break;

            case Operacion.LIBERAR_HORARIO:
                // deshacer → volvía a reservar
                // rehacer → vuelve a liberar
                Object[] datosLiberar = (Object[]) op.getDato();
                try {
                    horarioService.liberar(
                            (String) datosLiberar[0],
                            (int) datosLiberar[1],
                            (int) datosLiberar[2],
                            (int) datosLiberar[3]);
                    System.out.println("Liberación rehecha.");
                } catch (Exception error) {
                    System.out.println("Error al rehacer liberación: " + error.getMessage());
                }
                break;
        }

        pilaDeshacer.push(op);
    }
}