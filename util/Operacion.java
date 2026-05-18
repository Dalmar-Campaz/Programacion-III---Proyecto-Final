package util;

public class Operacion {
    public static final String REGISTRAR_ESTUDIANTE = "REGISTRAR_ESTUDIANTE";
    public static final String ELIMINAR_ESTUDIANTE = "ELIMINAR_ESTUDIANTE";
    public static final String INSCRIBIR_MATERIA = "INSCRIBIR_MATERIA";
    public static final String RESERVAR_HORARIO = "RESERVAR_HORARIO";
    public static final String LIBERAR_HORARIO = "LIBERAR_HORARIO";
    public static final String CANCELAR_INSCRIPCION = "CANCELAR_INSCRIPCION";
    public static final String REGISTRAR_NOTA = "REGISTRAR_NOTA";

    private String tipo;
    private Object dato;
    private String descripcion;

    public Operacion(String tipo, Object dato, String descripcion) {
        this.tipo = tipo;
        this.dato = dato;
        this.descripcion = descripcion;
    }

    public Object getDato() {
        return dato;
    }

    public String getTipo() {
        return tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return tipo + ": " + descripcion;
    }

}
