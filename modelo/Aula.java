public class Aula {

    private boolean [][] horario;
    private String nombre;
    private int capacidad;

    public Aula() {
        this.horario = new boolean[7][24];
    }

    public void reservar(){}
    public void liberar(){}
    public boolean estaDisponible(){ return false;}
}