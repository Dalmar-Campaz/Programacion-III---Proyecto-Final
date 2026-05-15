public class Estudiante extends Persona {
    private int semestre;
    private double [][] notas; 
    private String historialMaterias;
    private String [] materiasInscritas;

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public double[][] getNotas() {
        return notas;
    }

    public void setNotas(double[][] notas) {
        this.notas = notas;
    }
    public String getHistorialMaterias() {
        return historialMaterias;
    }

    public void setHistorialMaterias(String historialMaterias) {
        this.historialMaterias = historialMaterias;
    }

    @Override
    public void mostrarInformacion() {
        System.out.println("Nombre: " + getNombre());
        System.out.println("Semestre: " + getSemestre());
        System.out.println("Historial de Materias: " + getHistorialMaterias());
    }
}
