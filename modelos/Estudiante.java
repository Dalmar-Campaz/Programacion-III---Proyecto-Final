package modelos;

import estructuras.*;

public class Estudiante extends Persona {
    private int semestre;
    private double[][] notas;
    private int[] contadorNotas;
    private LinkedList<String> materiasInscritas;
    private LinkedList<String> materiasAprovadas;

    public Estudiante(String nombre, String id, String email, int semestre) {
        super(nombre, id, email);
        this.semestre = semestre;
        this.notas = new double[10][20];
        this.contadorNotas = new int[10];
        this.materiasInscritas = new LinkedList<>();
        this.materiasAprovadas = new LinkedList<>();
    }

    public boolean registrarNota(int semestre, double nota) {
        int index = semestre - 1;
        if (index >= 20)
            return false;
        notas[index][contadorNotas[index]] = nota;
        contadorNotas[index]++;

        return true;
    }

    public double[] getNotasPorSemestre(int semestre) {
        int index = semestre - 1;
        int cantidad = contadorNotas[index];
        double[] resultado = new double[cantidad];
        for(int i = 0; i < cantidad; i++){
            resultado[i] = notas[index][i]; 
        }
        return resultado;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public double[][] getNotas() {
        return notas;
    }

    public int[] getCantidadNotas() {
        return contadorNotas;
    }

    public LinkedList<String> getMateriasInscritas() {
        return materiasInscritas;
    }
    public LinkedList<String> getMateriasAprovadas() {
        return materiasAprovadas;
    }

    @Override
    public void mostrarInformacion() {
        System.out.println("=== ESTUDIANTE ===");
        System.out.println("ID:       " + getId());
        System.out.println("Nombre:   " + getNombre());
        System.out.println("Email:    " + getEmail());
        System.out.println("Semestre: " + semestre);
    }
}
