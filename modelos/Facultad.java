package modelos;

public class Facultad {
    private String nombre;
    private String codigo; 

    public Facultad(String nombre, String codigo){
        this.nombre = nombre;
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }
    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString(){
        return codigo + " - " + nombre;
    }
}
