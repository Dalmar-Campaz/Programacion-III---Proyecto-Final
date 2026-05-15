import java.util.Queue;

public class Materia {

    private int codigo;
    private String nombre;
    private int creditos;
    private int cuposMax;
    private String prerequisitos;
    private Queue<Estudiante> colaEspera;
}