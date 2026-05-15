package estructuras;

public class LinkedList<T> {
    private Nodo<T> cabeza;
    private int tamaño;

    public LinkedList() {
        this.cabeza = null;
        this.tamaño = 0;
    }

    public void add(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);

        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            Nodo<T> actual = cabeza;
            while (actual.getSiguiente() != null) {
                actual = actual.getSiguiente();
            }
            actual.setSiguiente(nuevo);
        }

        tamaño++;
    }

    public boolean delete(T dato) {
        if (cabeza == null) {
            return false;
        }

        if (cabeza.getDato().equals(dato)) {
            cabeza = cabeza.getSiguiente();
            tamaño--;
            return true;
        }

        Nodo<T> actual = cabeza;

        while (actual.getSiguiente() != null) {
            if (actual.getSiguiente().getDato().equals(dato)) {
                actual.setSiguiente(actual.getSiguiente().getSiguiente());
                tamaño--;
                return true;
            }
            actual = actual.getSiguiente();
        }

        return false;
    }

}
