package servicios;

import modelos.CampusGrafo;


public class RutaService {

    private CampusGrafo campus;

    public RutaService() {
        this.campus = new CampusGrafo();
        inicializarCampus();
    }

    private void inicializarCampus() {
        int ing = campus.agregarEdificio("Ingeniería");
        int bib = campus.agregarEdificio("Biblioteca");
        int caf = campus.agregarEdificio("Cafetería");
        int rec = campus.agregarEdificio("Rectoría");
        int lab = campus.agregarEdificio("Laboratorios");

        campus.conectar(ing, bib, 200);
        campus.conectar(ing, caf, 150);
        campus.conectar(bib, rec, 120);
        campus.conectar(caf, rec, 180);
        campus.conectar(rec, lab, 90);
    }

    public void calcularRuta(int origen, int destino) {
        if (origen < 0 || origen >= campus.getCantidadEdificios() ||
                destino < 0 || destino >= campus.getCantidadEdificios()) {
            System.out.println("Error: índice de edificio inválido.");
            return;
        }
        campus.dijkstra(origen, destino);
    }

    public void agregarConexion(int origen, int destino, int metros) {
        campus.conectar(origen, destino, metros);
        System.out.println("Conexión agregada: " +
                campus.getEdificios()[origen].getNombre() + " ↔ " +
                campus.getEdificios()[destino].getNombre() +
                " (" + metros + "m)");
    }

    public void mostrarEdificios() {
        campus.mostrarEdificios();
    }

    public CampusGrafo getCampus() {
        return campus;
    }
}