package modelos;

public class Aula {

    private boolean[][] horario;
    private String nombre;
    private int capacidad;

    private static final String[] nombreDias = {
            "Domingo", "Lunes", "Martes", "Miércoles",
            "Jueves", "Viernes", "Sábado"
    };

    public Aula(String nombre, int capacidad) {
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.horario = new boolean[7][24];
    }

    public boolean saberDisponibilidad(int dia, int hora){
        if(!esValido(dia, hora)) return false;
        return !horario[dia][hora];
    }

    private boolean esValido(int dia, int hora) {
        return dia >= 0 && dia <= 6 && hora >= 0 && hora <= 23;
    }

    public void reservar() {
    }

    public void liberar() {
    }

    public boolean estaDisponible() {
        return false;
    }
}