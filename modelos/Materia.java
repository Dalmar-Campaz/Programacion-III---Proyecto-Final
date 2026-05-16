package modelos;

import estructuras.*;

public class Materia {
    private String nombre;
    private String codigo;
    private int cuposMaximos;
    private int cuposOcupados;
    private int creditos;
    private LinkedList<String> prerequisitos;
    private Cola<String> colaEspera;

    public Materia(String nombre, String codigo, int cuposMaximos, int creditos) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.cuposMaximos = cuposMaximos;
        this.creditos = creditos;
        this.cuposOcupados = 0;
        this.prerequisitos = new LinkedList<>();
        this.colaEspera = new Cola<>();
    }

    public boolean hayCuposDisponibles() {
        return cuposOcupados < cuposMaximos;
    }

    public boolean ocuparCupo() {
        if (hayCuposDisponibles()) {
            cuposOcupados++;
            return true;
        }
        return false;
    }

    public boolean liberarCupo() {
        if (cuposOcupados == 0)
            return false;
        cuposOcupados--;
        return true;
    }

    public void agregarPrerequisito(String codigoMateria) {
        prerequisitos.add(codigoMateria);
    }

    public void agregarAColaEspera(String idEstudiante) {
        colaEspera.add(idEstudiante);
    }

    public String siguienteEnEspera() {
        return colaEspera.remove();
    }

    public int cantidadEnEspera() {
        return colaEspera.getTamaño();
    }

    public void mostrarInformacion() {
        System.out.println("=== MATERIA ===");
        System.out.println("Código:   " + codigo);
        System.out.println("Nombre:   " + nombre);
        System.out.println("Créditos: " + creditos);
        System.out.println("Cupos:    " + cuposOcupados + "/" + cuposMaximos);
        System.out.println("En espera: " + cantidadEnEspera());
        System.out.print("Prerequisitos: ");
        prerequisitos.imprimir();
    }

    public String getCodigo() {
        return codigo;
    }
    
    public Cola<String> getColaEspera() {
        return colaEspera;
    }

    public int getCreditos() {
        return creditos;
    }

    public int getCuposMaximos() {
        return cuposMaximos;
    }

    public int getCuposOcupados() {
        return cuposOcupados;
    }

    public String getNombre() {
        return nombre;
    }

    public LinkedList<String> getPrerequisitos() {
        return prerequisitos;
    }

}
