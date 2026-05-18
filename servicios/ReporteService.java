package servicios;

import estructuras.Pila;
import estructuras.LinkedList;
import modelos.Estudiante;
import excepciones.EstudianteNoEncontradoException;
import util.Operacion;;

public class ReporteService {

    private EstudianteService estudianteService;
    private MateriaService materiaService;
    private Pila<String> pilaAtras;
    private Pila<String> pilaAdelante;

    public ReporteService(EstudianteService estudianteService, MateriaService materiaService,
            Pila<Operacion> pilaDeshacer, Pila<Operacion> pilaRehacer) {
        this.estudianteService = estudianteService;
        this.materiaService = materiaService;
        this.pilaAdelante = new Pila<>();
        this.pilaAtras = new Pila<>();
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

            System.out.printf("  Promedio: %.2f%n", promedio);
            System.out.println("─────────────────────────");

            double acumulado = calcularPromedioGeneral(idEstudiante);
            System.out.println("=== RESUMEN ===");
            System.out.printf("Promedio acumulado: %.2f%n", acumulado);
            System.out.println("Materias aprobadas:  " + totalAprobadas);
            System.out.println("Materias reprobadas: " + totalReprobadas);
        }

        pilaAtras.push(idEstudiante);
    }

    public void reporteAnterior() throws EstudianteNoEncontradoException, Exception {
        if (pilaAtras.getTamaño() < 2) {
            System.out.println("No existen reportes anteriores");
            return;
        }
        String actual = pilaAtras.pop();
        pilaAdelante.push(actual);

        String idAnterior = pilaAtras.peek();
        generarReporteEstudiante(idAnterior);
    }

    public void reporteSiguiente() throws EstudianteNoEncontradoException, Exception {
        if (pilaAdelante.estaVacia()) {
            System.out.println("No extistes reportes siguientes");
            return;
        }

        String idSiguiente = pilaAdelante.pop();
        pilaAtras.push(idSiguiente);

        generarReporteEstudiante(idSiguiente);
    }

    public void listarReprobadas(String idEstudiante)
            throws EstudianteNoEncontradoException {

        Estudiante e = estudianteService.buscarEstudiante(idEstudiante);

        System.out.println("=== MATERIAS REPROBADAS ===");
        System.out.println("Estudiante: " + e.getNombre());

        boolean hayReprobadas = false;

        for (int semestre = 1; semestre <= 10; semestre++) {
            double[] notas = e.getNotasPorSemestre(semestre);
            for (double nota : notas) {
                if (nota < 3.0) {
                    System.out.println("Semestre " + semestre +
                                       ": " + nota + "(Reprobada), ");
                    hayReprobadas = true;
                }
            }
        }

        if (!hayReprobadas) {
            System.out.println("No tiene materias reprobadas");
        }
    }

    public Pila<String> getPilaAdelante() {
        return pilaAdelante;
    }
    public Pila<String> getPilaAtras() {
        return pilaAtras;
    }
}