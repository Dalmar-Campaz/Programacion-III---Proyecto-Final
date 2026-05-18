package vista;

import controlador.Controlador;
import java.util.Scanner;

/**
 * Vista por consola del sistema universitario.
 *
 * Responsabilidades:
 *   - Mostrar el menú al usuario
 *   - Leer entradas del usuario
 *   - Llamar al controlador con los datos recogidos
 *
 * Lo que NO hace:
 *   - No aplica lógica de negocio
 *   - No accede directamente a servicios ni modelos
 */
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
 
            try {
                opcion = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Ingresa un número válido.");
                continue;
            }

        }
        System.out.println("¡Hasta luego!");
        scanner.close();
    }


}