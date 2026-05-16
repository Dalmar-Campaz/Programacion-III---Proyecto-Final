package modelos;

public class Edificio {

    private String nombre;
    private int indice;

    public Edificio(String nombre, int indice) {
        this.nombre = nombre;
        this.indice = indice;
    }

    public String getNombre() {
        return nombre;
    }

    public int getIndice() {
        return indice;
    }

    @Override
    public String toString() {
        return nombre;
    }
}