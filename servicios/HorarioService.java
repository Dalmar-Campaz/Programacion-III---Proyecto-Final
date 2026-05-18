package servicios;

import excepciones.HorarioConflictivoException;
import modelos.Aula;
import estructuras.Pila;
import util.Operacion;
import java.util.TreeMap;

public class HorarioService {
    private TreeMap<String, Aula> aulas;
    private Pila<Operacion> pilaDeshacer;
    private Pila<Operacion> pilaRehacer;

    public HorarioService(Pila<Operacion> pilaDeshacer, Pila<Operacion> pilaRehacer) {
        this.aulas = new TreeMap<>();
        this.pilaDeshacer = pilaDeshacer;
        this.pilaRehacer = pilaRehacer;
    }

    public void crearAula(String nombre, int capacidad) {
        Aula nueva = new Aula(nombre, capacidad);
        aulas.put(nombre, nueva);
        System.out.println("Aula creada: " + nombre + " (Capacidad: " + capacidad + ")");
    }

    public Aula buscarAula(String nombre) throws Exception {
        Aula aula = aulas.get(nombre);
        if (aula == null) {
            throw new Exception("Aula no encontrada: " + nombre);
        }
        return aula;
    }

    public void listarAulas() {
        if (aulas.isEmpty()) {
            System.out.println("No hay aulas registradas.");
            return;
        }
        System.out.println("Aulas disponibles:");
        for (Aula aula : aulas.values()) {
            aula.mostrarInformacion();
        }
    }

    public void reservar(String nombreAula, int dia,
            int hora, int duracion)
            throws Exception, HorarioConflictivoException {

        Aula a = buscarAula(nombreAula);
        a.reservar(dia, hora, duracion); 

        String descripcion = "Reservar " + nombreAula +
                " día " + dia +
                " hora " + hora +
                " duración " + duracion + "h";

        pilaDeshacer.push(new Operacion(
                Operacion.RESERVAR_HORARIO,
                new Object[] { nombreAula, dia, hora, duracion },
                descripcion));

        System.out.println("Reserva exitosa en aula " + nombreAula);
        pilaRehacer.limpiar();
    }

    public void liberar(String nombreAula, int dia,
            int hora, int duracion) throws Exception {

        Aula a = buscarAula(nombreAula);
        a.liberar(dia, hora, duracion);

        pilaDeshacer.push(new Operacion(
                Operacion.LIBERAR_HORARIO,
                new Object[] { nombreAula, dia, hora, duracion },
                "Liberar " + nombreAula + " día " + dia + " hora " + hora));

        System.out.println("Horario liberado en aula " + nombreAula);
        pilaRehacer.limpiar();
    }

    public void consultarDisponibilidad(String nombreAula,
            int dia, int hora)
            throws Exception {
        Aula a = buscarAula(nombreAula);
        boolean disponible = a.consultarDisponibilidad(dia, hora);
        System.out.println("Aula " + nombreAula +
                " — día " + dia +
                " hora " + hora + ":00 → " +
                (disponible ? "LIBRE" : "OCUPADO"));
    }

    public void mostrarDia(String nombreAula, int dia) throws Exception {
        Aula a = buscarAula(nombreAula);
        a.mostrarDia(dia);
    }

    public TreeMap<String, Aula> getAulas() {
        return aulas;
    }
}
