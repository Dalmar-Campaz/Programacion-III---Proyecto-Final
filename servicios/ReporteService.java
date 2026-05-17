package servicios;

import estructuras.Pila;
import estructuras.LinkedList;
import modelos.Estudiante;
import excepciones.EstudianteNoEncontradoException;
import util.Operacion;;

public class ReporteService {

    private EstudianteService estudianteService;
    private Pila<String> pilaReportes;
    private MateriaService materiaService;
    private Pila<Operacion> pilaDeshacer;
    private Pila<Operacion> pilaRehacer;

    public ReporteService(EstudianteService estudianteService, MateriaService materiaService,
            Pila<Operacion> pilaDeshacer, Pila<Operacion> pilaRehacer) {
        this.estudianteService = estudianteService;
        this.materiaService = materiaService;
        this.pilaReportes = new Pila<>();
        this.pilaDeshacer = pilaDeshacer;
        this.pilaRehacer = pilaRehacer;
    }

    /**
     * Este metodo se llama igual que el metodo de la clase Estudiante, pero este se
     * encarga de
     * definir si el estudiante aprobo o no la materia, y en base a eso, mueve la
     * materia de inscritas
     * a aprobadas o simplemente la elimina de inscritas
     */
    public void registrarNota(String idEstudiante, String codigoMateria, int semestre, double nota)
            throws EstudianteNoEncontradoException, Exception {
        if (nota < 0.0 || nota > 5.0) {
            throw new Exception("La nota debe estar entre 0.0 y 5.0");
        }

        if (semestre < 1 || semestre > 10) {
            throw new Exception("El semestre debe estar entre 1 y 10");
        }

        Estudiante e = estudianteService.buscarEstudiante(idEstudiante);

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

    public double calcularPromedioPorSemestre(String idEstudiante, int semestre)
            throws EstudianteNoEncontradoException, Exception {
        if (semestre < 1 || semestre > 10) {
            throw new Exception("El semestre debe estar entre 1 y 10");
        }

        Estudiante e = estudianteService.buscarEstudiante(idEstudiante);
        double[] notas = e.getNotasPorSemestre(semestre);

        if (notas.length == 0) {
            throw new Exception("No hay notas registradas para el semestre " + semestre);
        }

        double suma = 0.0;
        for (double nota : notas) {
            suma += nota;
        }
        return suma / notas.length;

    }

    public double calcularPromedioGeneral(String idEstudiante) throws EstudianteNoEncontradoException {

        Estudiante e = estudianteService.buscarEstudiante(idEstudiante);
        double suma = 0.0;
        int totalNotas = 0;

        for (int semestre = 1; semestre <= 10; semestre++) {
            double[] notas = e.getNotasPorSemestre(semestre);
            for (double nota : notas) {
                suma += nota;
                totalNotas++;
            }
        }
        if (totalNotas == 0) {
            return 0.0; // No hay notas registradas
        }
        return suma / totalNotas;
    }

    public void generarReporteEstudiante(String idEstudiante) throws EstudianteNoEncontradoException, Exception {
        Estudiante e = estudianteService.buscarEstudiante(idEstudiante);
        System.out.println("=== REPORTE DEL ESTUDIANTE ===");
        System.out.println("ID: " + e.getId());
        System.out.println("Nombre: " + e.getNombre());
        System.out.println("Email: " + e.getEmail());
        System.out.println("Semestre: " + e.getSemestre());
        System.out.println("Materias inscritas: " + e.getMateriasInscritas().toString());
        System.out.println("Materias aprobadas: " + e.getMateriasAprovadas().toString());

        int totalAprobadas = 0;
        int totalReprobadas = 0;

        for (int semestre = 1; semestre <= 10; semestre++) {
            double[] notas = e.getNotasPorSemestre(semestre);
            if (notas.length == 0) {
                System.out.println("El semestre " + semestre + ": No tiene notas registradas");
                continue;
            }
            System.out.println("Semestre " + semestre + ":");
            for (double nota : notas) {
                if (nota >= 3.0) {
                    totalAprobadas++;
                } else {
                    totalReprobadas++;
                }
            }
            double promedio = calcularPromedioPorSemestre(idEstudiante, semestre);

            System.out.println("  Promedio: " + promedio);
            System.out.println("─────────────────────────");

            double acumulado = calcularPromedioGeneral(idEstudiante);
            System.out.println("=== RESUMEN ===");
            System.out.printf("Promedio acumulado: %.2f%n", acumulado);
            System.out.println("Materias aprobadas:  " + totalAprobadas);
            System.out.println("Materias reprobadas: " + totalReprobadas);
        }

        pilaReportes.push(idEstudiante);
    }
}