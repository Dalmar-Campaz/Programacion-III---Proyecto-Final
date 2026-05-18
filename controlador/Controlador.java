package controlador;

import estructuras.*;
import modelos.Estudiante;
import util.Operacion;
import servicios.*;

public class Controlador {

    private Pila<Operacion> pilaDeshacer;
    private Pila<Operacion> pilaRehacer;
    private boolean deshaciendo = false;
    private HorarioService horarioService;
    private EstudianteService estudianteService;
    private MateriaService materiaService;
    private ReporteService reporteService;
    private RutaService rutaService;

    public Controlador() {
        this.pilaDeshacer = new Pila<>();
        this.pilaRehacer = new Pila<>();
        this.horarioService = new HorarioService(pilaDeshacer, pilaRehacer);
        this.estudianteService = new EstudianteService(pilaDeshacer, pilaRehacer);
        this.materiaService = new MateriaService(estudianteService, pilaDeshacer, pilaRehacer);
        this.reporteService = new ReporteService(estudianteService, materiaService);
        this.rutaService = new RutaService();
    }

    public void registrarEstudiante(String nombre, String id,
            String email, int semestre) {
        estudianteService.registrarEstudiante(nombre, id, email, semestre);
        if (!deshaciendo)
            pilaRehacer.limpiar();
    }

    public void buscarEstudiante(String id) {
        try {
            estudianteService.buscarEstudiante(id).mostrarInformacion();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void eliminarEstudiante(String id) {
        try {
            estudianteService.eliminarEstudiante(id);
            if (!deshaciendo)
                pilaRehacer.limpiar();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void listarEstudiantes() {
        estudianteService.listarEstudiantes();
    }

    public void crearMateria(String codigo, String nombre,
            int cupos, int creditos) {
        materiaService.crearMateria(codigo, nombre, cupos, creditos);
        if (!deshaciendo)
            pilaRehacer.limpiar();
    }

    public void agregarPrerequisito(String codigoMateria,
            String codigoPrerequisito) {
        try {
            materiaService.agregarPrerequisito(codigoMateria, codigoPrerequisito);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void inscribir(String idEstudiante, String codigoMateria) {
        try {
            materiaService.inscribir(idEstudiante, codigoMateria);
            if (!deshaciendo)
                pilaRehacer.limpiar();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void cancelarInscripcion(String idEstudiante,
            String codigoMateria) {
        try {
            materiaService.cancelarInscripcion(idEstudiante, codigoMateria);
            if (!deshaciendo)
                pilaRehacer.limpiar();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void mostrarColaEspera(String codigoMateria) {
        try {
            materiaService.mostrarColaEspera(codigoMateria);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void listarMaterias() {
        materiaService.listarMaterias();
    }


    public void crearAula(String nombre, int capacidad) {
        horarioService.crearAula(nombre, capacidad);
    }

    public void reservarHorario(String aula, int dia,
            int hora, int duracion) {
        try {
            horarioService.reservar(aula, dia, hora, duracion);
            if (!deshaciendo)
                pilaRehacer.limpiar();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void liberarHorario(String aula, int dia,
            int hora, int duracion) {
        try {
            horarioService.liberar(aula, dia, hora, duracion);
            if (!deshaciendo)
                pilaRehacer.limpiar();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void consultarDisponibilidad(String aula, int dia, int hora) {
        try {
            horarioService.consultarDisponibilidad(aula, dia, hora);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void listarAulas() {
        horarioService.listarAulas();
    }


    public void mostrarEdificios() {
        rutaService.mostrarEdificios();
    }

    public void calcularRuta(int origen, int destino) {
        rutaService.calcularRuta(origen, destino);
    }

    public void agregarConexion(int origen, int destino, int metros) {
        rutaService.agregarConexion(origen, destino, metros);
    }

    public void registrarNota(String idEstudiante, String codigoMateria,
            int semestre, double nota) {
        try {
            estudianteService.registrarNota(idEstudiante, codigoMateria,
                    semestre, nota);
            if (!deshaciendo)
                pilaRehacer.limpiar();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void generarReporte(String idEstudiante) {
        try {
            reporteService.generarReporteEstudiante(idEstudiante);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void reporteAnterior() {
        try {
            reporteService.reporteAnterior();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void reporteSiguiente() {
        try {
            reporteService.reporteSiguiente();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void listarReprobadas(String idEstudiante) {
        try {
            reporteService.listarReprobadas(idEstudiante);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void deshacer() {
        if (pilaDeshacer.estaVacia()) {
            System.out.println("No hay acciones para deshacer");
            return;
        }

        deshaciendo = true;
        Operacion op = pilaDeshacer.pop();
        System.out.println("Deshaciendo: " + op.getDescripcion());

        switch (op.getTipo()) {

            case Operacion.REGISTRAR_ESTUDIANTE:
                Estudiante eReg = (Estudiante) op.getDato();
                estudianteService.getEstudiantes().remove(eReg.getId());
                System.out.println("Registro deshecho.");
                break;

            case Operacion.ELIMINAR_ESTUDIANTE:
                Estudiante eElim = (Estudiante) op.getDato();
                estudianteService.getEstudiantes().put(eElim.getId(), eElim);
                System.out.println("Eliminación deshecha.");
                break;

            case Operacion.INSCRIBIR_MATERIA:
                String[] datosIns = (String[]) op.getDato();
                try {
                    materiaService.cancelarInscripcion(datosIns[0], datosIns[1]);
                } catch (Exception error) {
                    System.out.println("Error al deshacer inscripción: "
                            + error.getMessage());
                }
                break;

            case Operacion.CANCELAR_INSCRIPCION:
                String[] datosCan = (String[]) op.getDato();
                try {
                    materiaService.inscribir(datosCan[0], datosCan[1]);
                } catch (Exception error) {
                    System.out.println("Error al deshacer cancelación: "
                            + error.getMessage());
                }
                break;

            case Operacion.RESERVAR_HORARIO:
                Object[] datosRes = (Object[]) op.getDato();
                String nombreAula = (String) datosRes[0];
                int dia = (int) datosRes[1];
                int hora = (int) datosRes[2];
                int duracion = (int) datosRes[3];
                try {
                    horarioService.liberar(nombreAula, dia, hora, duracion);
                    System.out.println("Reserva deshecha en aula " + nombreAula);
                } catch (Exception error) {
                    System.out.println("Error al deshacer reserva: "
                            + error.getMessage());
                }
                break;

            case Operacion.LIBERAR_HORARIO:
                Object[] datosLib = (Object[]) op.getDato();
                try {
                    horarioService.reservar(
                            (String) datosLib[0],
                            (int) datosLib[1],
                            (int) datosLib[2],
                            (int) datosLib[3]);
                    System.out.println("Liberación deshecha.");
                } catch (Exception error) {
                    System.out.println("Error al deshacer liberación: "
                            + error.getMessage());
                }
                break;

            case Operacion.REGISTRAR_NOTA:
                Object[] datosNota = (Object[]) op.getDato();
                try {
                    Estudiante est = estudianteService
                            .buscarEstudiante((String) datosNota[0]);
                    est.getCantidadNotas()[(int) datosNota[2] - 1]--;
                    System.out.println("Nota deshecha.");
                } catch (Exception error) {
                    System.out.println("Error al deshacer nota: "
                            + error.getMessage());
                }
                break;
        }

        deshaciendo = false;
        pilaRehacer.push(op);
    }

    public void rehacer() {
        if (pilaRehacer.estaVacia()) {
            System.out.println("Error: no hay operaciones para rehacer.");
            return;
        }

        deshaciendo = true;
        Operacion op = pilaRehacer.pop();
        System.out.println("Rehaciendo: " + op.getDescripcion());

        switch (op.getTipo()) {

            case Operacion.REGISTRAR_ESTUDIANTE:
                Estudiante eReg = (Estudiante) op.getDato();
                estudianteService.getEstudiantes().put(eReg.getId(), eReg);
                System.out.println("Registro rehecho.");
                break;

            case Operacion.ELIMINAR_ESTUDIANTE:
                Estudiante eElim = (Estudiante) op.getDato();
                estudianteService.getEstudiantes().remove(eElim.getId());
                System.out.println("Eliminación rehecha.");
                break;

            case Operacion.INSCRIBIR_MATERIA:
                String[] datosIns = (String[]) op.getDato();
                try {
                    materiaService.inscribir(datosIns[0], datosIns[1]);
                } catch (Exception error) {
                    System.out.println("Error al rehacer inscripción: "
                            + error.getMessage());
                }
                break;

            case Operacion.CANCELAR_INSCRIPCION:
                String[] datosCan = (String[]) op.getDato();
                try {
                    materiaService.cancelarInscripcion(datosCan[0], datosCan[1]);
                } catch (Exception error) {
                    System.out.println("Error al rehacer cancelación: "
                            + error.getMessage());
                }
                break;

            case Operacion.RESERVAR_HORARIO:
                Object[] datosRes = (Object[]) op.getDato();
                try {
                    horarioService.reservar(
                            (String) datosRes[0],
                            (int) datosRes[1],
                            (int) datosRes[2],
                            (int) datosRes[3]);
                    System.out.println("Reserva rehecha.");
                } catch (Exception error) {
                    System.out.println("Error al rehacer reserva: "
                            + error.getMessage());
                }
                break;

            case Operacion.LIBERAR_HORARIO:
                Object[] datosLib = (Object[]) op.getDato();
                try {
                    horarioService.liberar(
                            (String) datosLib[0],
                            (int) datosLib[1],
                            (int) datosLib[2],
                            (int) datosLib[3]);
                    System.out.println("Liberación rehecha.");
                } catch (Exception error) {
                    System.out.println("Error al rehacer liberación: "
                            + error.getMessage());
                }
                break;

            case Operacion.REGISTRAR_NOTA:
                Object[] datosNota = (Object[]) op.getDato();
                try {
                    estudianteService.registrarNota(
                            (String) datosNota[0],
                            (String) datosNota[1],
                            (int) datosNota[2],
                            (double) datosNota[3]);
                    System.out.println("Nota rehecha.");
                } catch (Exception error) {
                    System.out.println("Error al rehacer nota: "
                            + error.getMessage());
                }
                break;
        }

        deshaciendo = false;
        pilaDeshacer.push(op);
    }
}