package modelos;

public class CampusGrafo {
    private static final int MAX_EDIFICIOS = 10;
    private static final int INFINITO = Integer.MAX_VALUE / 2;

    // Integer.MAX_VALUE = 2,147,483,647 (el mayor int posible)
    // Si lo usamos como "infinito" y le sumamos cualquier distancia:
    // 2,147,483,647 + 150 = -2,147,483,499 ← overflow, se vuelve negativo
    // Dijkstra pensaría que encontró un camino más corto cuando no hay ninguno.

    // Solución: dividirlo entre 2 → 1,073,741,823
    // Sigue siendo "inalcanzable" para distancias reales en metros,
    // pero al sumarle cualquier peso no se desborda.

    private int[][] distancias;
    private Edificio[] edificios;
    private int cantidadEdificios;

    public CampusGrafo() {
        this.distancias = new int[MAX_EDIFICIOS][MAX_EDIFICIOS];
        this.edificios = new Edificio[MAX_EDIFICIOS];
        this.cantidadEdificios = 0;
    }

    public int agregarEdificio(String nombre) {
        if (cantidadEdificios >= MAX_EDIFICIOS) {
            System.out.println("Campus lleno — máximo " + MAX_EDIFICIOS + " edificios");
            return -1;
        }
        int index = cantidadEdificios;
        edificios[index] = new Edificio(nombre, index);
        cantidadEdificios++;
        return index;
    }

    public void conectar(int indexA, int indexB, int metros) {
        distancias[indexA][indexB] = metros;
        distancias[indexB][indexA] = metros;
    }

    public int dijkstra(int origen, int destino) {

        int[] distanciaMinima = new int[cantidadEdificios];

        boolean[] visitado = new boolean[cantidadEdificios];

        int[] anterior = new int[cantidadEdificios];

        // Inicializar — todo infinito, nada visitado
        for (int i = 0; i < cantidadEdificios; i++) {
            distanciaMinima[i] = INFINITO;
            visitado[i] = false;
            anterior[i] = -1; // -1 significa "sin predecesor"
        }

        // El origen está a distancia 0 de sí mismo
        distanciaMinima[origen] = 0;

        // se repite para cada edificio
        for (int iteracion = 0; iteracion < cantidadEdificios; iteracion++) {

            // Encontrar el no visitado con menor distancia conocida
            int actual = -1;
            for (int i = 0; i < cantidadEdificios; i++) {
                if (!visitado[i]) {
                    if (actual == -1 || distanciaMinima[i] < distanciaMinima[actual]) {
                        actual = i;
                    }
                }
            }

            // Si no hay más nodos alcanzables, terminar
            if (distanciaMinima[actual] == INFINITO)
                break;

            // Marcar como visitado
            visitado[actual] = true;

            // Actualizar distancias de los vecinos
            for (int vecino = 0; vecino < cantidadEdificios; vecino++) {
                int peso = distancias[actual][vecino];

                // Si hay conexión directa y no está visitado
                if (peso > 0 && !visitado[vecino]) {
                    int nuevaDistancia = distanciaMinima[actual] + peso;

                    // Si encontramos un camino más corto — actualizar
                    if (nuevaDistancia < distanciaMinima[vecino]) {
                        distanciaMinima[vecino] = nuevaDistancia;
                        anterior[vecino] = actual; // recordar por dónde llegamos
                    }
                }
            }
        }

        // Reconstruir e imprimir el camino
        imprimirCamino(origen, destino, anterior, distanciaMinima);

        return distanciaMinima[destino];
    }

    /**
     * Reconstruye el camino desde destino hacia origen
     * usando el arreglo anterior[], luego lo imprime al revés.
     */
    private void imprimirCamino(int origen, int destino,
            int[] anterior, int[] distanciaMinima) {

        if (distanciaMinima[destino] == INFINITO) {
            System.out.println("No hay ruta entre " +
                    edificios[origen].getNombre() + " y " +
                    edificios[destino].getNombre());
            return;
        }

        // Reconstruir camino hacia atrás
        int[] camino = new int[cantidadEdificios];
        int longitud = 0;
        int actual = destino;

        while (actual != -1) {
            camino[longitud++] = actual;
            actual = anterior[actual];
        }

        // Imprimir al revés (de origen a destino)
        System.out.println("=== RUTA MÁS CORTA ===");
        System.out.print("Ruta: ");
        for (int i = longitud - 1; i >= 0; i--) {
            System.out.print(edificios[camino[i]].getNombre());
            if (i > 0) {
                System.out.print(" ──(" +
                        distancias[camino[i]][camino[i - 1]] + "m)──▶ ");
            }
        }
        System.out.println();
        System.out.println("Distancia total: " +
                distanciaMinima[destino] + " metros");
    }

    /**
     * Muestra todos los edificios registrados.
     */
    public void mostrarEdificios() {
        System.out.println("=== EDIFICIOS DEL CAMPUS ===");
        for (int i = 0; i < cantidadEdificios; i++) {
            System.out.println(i + ": " + edificios[i].getNombre());
        }
    }

    // Getters
    public int getCantidadEdificios() {
        return cantidadEdificios;
    }

    public Edificio[] getEdificios() {
        return edificios;
    }

    public int[][] getDistancias() {
        return distancias;
    }
}
