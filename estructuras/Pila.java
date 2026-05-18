package estructuras;

public class Pila<T> {
    private LinkedList<T> elementos;

    public Pila() {
        this.elementos = new LinkedList<>();
    }

    public void push(T elemento) {
        elementos.addInicio(elemento);
    }

    public T pop() {
        if (estaVacia())
            return null;
        T cima = elementos.obtener(0);
        elementos.deletePrimero();
        return cima;
    }

    public T peek() {
        return elementos.obtener(0);
    }

    public void limpiar() {
        elementos = new LinkedList<>();
    }

    public boolean estaVacia() {
        return elementos.estaVacia();
    }

    public int getTamaño() {
        return elementos.getTamaño();
    }

    public void imprimir() {
        System.out.print("Cima -> ");
        elementos.imprimir();
    }
}
