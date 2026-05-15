package estructuras;

public class Nodo<T> {

    // El dato que carga este nodo
    private T dato;

    // Referencia al siguiente nodo (null si es el último)
    private Nodo<T> siguiente;

    // Constructor: nace con su dato, sin siguiente todavía
    public Nodo(T dato) {
        this.dato = dato;
        this.siguiente = null;
    }

    // Getters y setters — el acceso siempre a través de métodos
    public T getDato() {
        return dato;
    }

    public void setDato(T dato) {
        this.dato = dato;
    }

    public Nodo<T> getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Nodo<T> siguiente) {
        this.siguiente = siguiente;
    }
}
