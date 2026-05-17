package modelos;

import excepciones.HorarioConflictivoException;

public class Aula {

    private boolean[][] horario;
    private String nombre;
    private int capacidad;

    private static final String[] NOMBRES_DIAS = {
            "Domingo", "Lunes", "Martes", "Miércoles",
            "Jueves", "Viernes", "Sábado"
    };

    public Aula(String nombre, int capacidad) {
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.horario = new boolean[7][24];
    }

    public boolean consultarDisponibilidad(int dia, int hora) {
        if (!esValido(dia, hora))
            return false;
        return !horario[dia][hora];
    }

    private boolean esValido(int dia, int hora) {
        return dia >= 0 && dia <= 6 && hora >= 0 && hora <= 23;
    }

    public void reservar(int dia, int hora, int duracion) throws HorarioConflictivoException {

        if (dia < 0 || dia > 6) {
            throw new HorarioConflictivoException(
                    "Día inválido: " + dia + ". Debe estar entre 0 (Domingo) y 6 (Sábado)");
        }

        if (hora < 0 || hora > 23) {
            throw new HorarioConflictivoException("Hora inválida: " + hora + ". Debe estar entre 0 y 23");
        }

        if (hora + duracion > 24) {
            throw new HorarioConflictivoException("La clase no puede cruzar la medianoche. " + "Inicio: " + hora
                    + ":00, Duración: " + duracion + "h");
        }

        for (int i = 0; i < duracion; i++) {
            if (horario[dia][hora + i]) {
                throw new HorarioConflictivoException(
                        NOMBRES_DIAS[dia] + " " + (hora + i) + ":00 ya está reservado en el aula " + nombre);
            }
        }

        for (int i = 0; i < duracion; i++) {
            horario[dia][hora + i] = true;
        }
    }

    public void liberar(int dia, int hora, int duracion) {
        for (int i = 0; i < duracion; i++) {
            int horaActual = hora + i;
            if (esValido(dia, horaActual)) {
                horario[dia][horaActual] = false;
            }
        }
    }

    public void mostrarDia(int dia) {
        System.out.println("=== " + NOMBRES_DIAS[dia] + " — Aula " + nombre + " ===");
        for (int hora = 0; hora < 24; hora++) {
            String estado = horario[dia][hora] ? "OCUPADO" : "LIBRE  ";
            System.out.println("  " + hora + ":00 → " + estado);
        }
    }

    public String getNombre() {
        return nombre;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public boolean[][] getHorario() {
        return horario;
    }

}