package vista;

import controlador.Controlador;
import java.util.Scanner;

public class Interfaz {

    private Controlador controlador;
    private Scanner scanner;

    public Interfaz() {
        this.controlador = new Controlador();
        this.scanner     = new Scanner(System.in);
    }

    public void iniciar() {
        int opcion = -1;
        while (opcion != 0) {
            mostrarMenu();
            try {
                opcion = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Ingresa un número válido.");
                continue;
            }
            procesarOpcion(opcion);
        }
        System.out.println("¡Hasta luego!");
        scanner.close();
    }
       private void mostrarMenu() {
        System.out.println("\n   PLANIFICACIÓN ACADÉMICA - SISTEMA UNIVERSITARIO");
        System.out.println("============================================================");
        System.out.println("=== GESTIÓN DE ESTUDIANTES ===");
        System.out.println("  1. Registrar estudiante");
        System.out.println("  2. Buscar estudiante por ID");
        System.out.println("  3. Listar todos los estudiantes");
        System.out.println("  4. Eliminar estudiante");
        System.out.println("=== GESTIÓN DE MATERIAS ===");
        System.out.println("  5. Crear materia");
        System.out.println("  6. Agregar prerequisito");
        System.out.println("  7. Mostrar prerequisitos");
        System.out.println("  8. Inscribir estudiante");
        System.out.println("  9. Cancelar inscripción");
        System.out.println(" 10. Mostrar cola de espera");
        System.out.println("=== GESTIÓN DE HORARIOS ===");
        System.out.println(" 11. Crear aula");
        System.out.println(" 12. Reservar horario");
        System.out.println(" 13. Liberar horario");
        System.out.println(" 14. Consultar disponibilidad");
        System.out.println("=== RUTAS ENTRE EDIFICIOS ===");
        System.out.println(" 15. Mostrar edificios");
        System.out.println(" 16. Calcular ruta más corta");
        System.out.println(" 17. Agregar conexión entre edificios");
        System.out.println("=== REPORTES ACADÉMICOS ===");
        System.out.println(" 18. Registrar nota");
        System.out.println(" 19. Ver reporte académico");
        System.out.println(" 20. Reporte anterior");
        System.out.println(" 21. Reporte siguiente");
        System.out.println("=== DESHACER / REHACER ===");
        System.out.println(" 22. Deshacer última operación");
        System.out.println(" 23. Rehacer última operación");
        System.out.println("=== SALIR ===");
        System.out.println(" 0. Salir");
        System.out.println("------------------------------------------------------------");
        System.out.print("Seleccione una opción: ");
    }

    private void procesarOpcion(int opcion) {
        switch (opcion) {

            case 1:
                System.out.print("Nombre: ");
                String nombre = scanner.nextLine();
                System.out.print("ID: ");
                String id = scanner.nextLine();
                System.out.print("Email: ");
                String email = scanner.nextLine();
                System.out.print("Semestre actual: ");
                int semestre = leerEntero();
                controlador.registrarEstudiante(nombre, id, email, semestre);
                break;

            case 2:
                System.out.print("ID del estudiante: ");
                controlador.buscarEstudiante(scanner.nextLine());
                break;

            case 3:
                controlador.listarEstudiantes();
                break;

            case 4:
                System.out.print("ID del estudiante a eliminar: ");
                controlador.eliminarEstudiante(scanner.nextLine());
                break;

            case 5:
                System.out.print("Código: ");
                String codigo = scanner.nextLine();
                System.out.print("Nombre: ");
                String nombreMat = scanner.nextLine();
                System.out.print("Cupos máximos: ");
                int cupos = leerEntero();
                System.out.print("Créditos: ");
                int creditos = leerEntero();
                controlador.crearMateria(codigo, nombreMat, cupos, creditos);
                break;

            case 6:
                System.out.print("Código de la materia: ");
                String codMat = scanner.nextLine();
                System.out.print("Código del prerequisito: ");
                String codPre = scanner.nextLine();
                controlador.agregarPrerequisito(codMat, codPre);
                break;

            case 7:
                System.out.print("Código de la materia: ");
                controlador.mostrarPrerequisitos(scanner.nextLine());
                break;

            case 8:
                System.out.print("ID del estudiante: ");
                String idEst = scanner.nextLine();
                System.out.print("Código de la materia: ");
                String codMatIns = scanner.nextLine();
                controlador.inscribir(idEst, codMatIns);
                break;

            case 9:
                System.out.print("ID del estudiante: ");
                String idEstCan = scanner.nextLine();
                System.out.print("Código de la materia: ");
                String codMatCan = scanner.nextLine();
                controlador.cancelarInscripcion(idEstCan, codMatCan);
                break;

            case 10:
                System.out.print("Código de la materia: ");
                controlador.mostrarColaEspera(scanner.nextLine());
                break;

            case 11:
                System.out.print("Nombre del aula: ");
                String nombreAula = scanner.nextLine();
                System.out.print("Capacidad: ");
                int capacidad = leerEntero();
                controlador.crearAula(nombreAula, capacidad);
                break;

            case 12:
                System.out.print("Nombre del aula: ");
                String aulaRes = scanner.nextLine();
                System.out.print("Día (0=Dom,1=Lun,2=Mar,3=Mié,4=Jue,5=Vie,6=Sáb): ");
                int diaRes = leerEntero();
                System.out.print("Hora inicio (0-23): ");
                int horaRes = leerEntero();
                System.out.print("Duración (horas): ");
                int durRes = leerEntero();
                controlador.reservarHorario(aulaRes, diaRes, horaRes, durRes);
                break;

            case 13:
                System.out.print("Nombre del aula: ");
                String aulaLib = scanner.nextLine();
                System.out.print("Día (0=Dom,1=Lun,2=Mar,3=Mié,4=Jue,5=Vie,6=Sáb): ");
                int diaLib = leerEntero();
                System.out.print("Hora inicio (0-23): ");
                int horaLib = leerEntero();
                System.out.print("Duración (horas): ");
                int durLib = leerEntero();
                controlador.liberarHorario(aulaLib, diaLib, horaLib, durLib);
                break;

            case 14:
                System.out.print("Nombre del aula: ");
                String aulaDisp = scanner.nextLine();
                System.out.print("Día (0=Dom,1=Lun,2=Mar,3=Mié,4=Jue,5=Vie,6=Sáb): ");
                int diaDisp = leerEntero();
                System.out.print("Hora (0-23): ");
                int horaDisp = leerEntero();
                controlador.consultarDisponibilidad(aulaDisp, diaDisp, horaDisp);
                break;

            case 15:
                controlador.mostrarEdificios();
                break;

            case 16:
                controlador.mostrarEdificios();
                System.out.print("Índice edificio origen: ");
                int origen = leerEntero();
                System.out.print("Índice edificio destino: ");
                int destino = leerEntero();
                controlador.calcularRuta(origen, destino);
                break;

            case 17:
                controlador.mostrarEdificios();
                System.out.print("Índice edificio A: ");
                int edA = leerEntero();
                System.out.print("Índice edificio B: ");
                int edB = leerEntero();
                System.out.print("Distancia en metros: ");
                int metros = leerEntero();
                controlador.agregarConexion(edA, edB, metros);
                break;

            case 18:
                System.out.print("ID del estudiante: ");
                String idNota = scanner.nextLine();
                System.out.print("Código de la materia: ");
                String codNota = scanner.nextLine();
                System.out.print("Semestre (1-10): ");
                int semNota = leerEntero();
                System.out.print("Nota (0.0 - 5.0): ");
                double nota = leerDecimal();
                controlador.registrarNota(idNota, codNota, semNota, nota);
                break;

            case 19:
                System.out.print("ID del estudiante: ");
                controlador.generarReporte(scanner.nextLine());
                break;

            case 20:
                controlador.reporteAnterior();
                break;

            case 21:
                controlador.reporteSiguiente();
                break;

            case 22:
                controlador.deshacer();
                break;

            case 23:
                controlador.rehacer();
                break;

            case 0:
                System.out.println("¡Hasta luego!");
                System.exit(0);
                break;

            default:
                System.out.println("Opción inválida. Intenta de nuevo.");
        }
    }

    private int leerEntero() {
        while (true) {
            try {
                int valor = Integer.parseInt(scanner.nextLine().trim());
                return valor;
            } catch (NumberFormatException e) {
                System.out.print("Ingresa un número entero válido: ");
            }
        }
    }

    private double leerDecimal() {
        while (true) {
            try {
                double valor = Double.parseDouble(scanner.nextLine().trim());
                return valor;
            } catch (NumberFormatException e) {
                System.out.print("Ingresa un número decimal válido: ");
            }
        }
    }

}