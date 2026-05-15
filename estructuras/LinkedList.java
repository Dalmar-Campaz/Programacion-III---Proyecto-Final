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

    public boolean contiene(T dato) {
        Nodo<T> actual = cabeza;

        while (actual.getSiguiente() != null) {
            if (actual.getDato() == dato) {
                return true;
            }
            actual = actual.getSiguiente();
        }
        return false;
    }

    public T obtener(int posicion) {
        if (posicion < 0 || posicion >= tamaño)
            return null;

        Nodo<T> actual = cabeza;

        for (int i = 0; i < posicion; i++) {
            actual = actual.getSiguiente();
        }
        return actual.getDato();
    }

    public boolean estaVacia() {
        return tamaño == 0;
    }

    public int getTamaño() {
        return tamaño;
    }

    public void imprimir() {
        if (estaVacia()) {
            System.out.println("La lista esta vacia");
            return;
        }
        Nodo<T> actual = cabeza;

        System.out.print("[");
        while (actual.getSiguiente() != null) {
            System.out.print(actual.getDato() + ", ");
            actual = actual.getSiguiente();
        }
        System.out.println(actual.getDato()+"]");
    }

    public void addInicio(T dato){
        Nodo<T> nuevo = new Nodo<>(dato);
        nuevo.setSiguiente(cabeza);
        cabeza = nuevo;
        tamaño++;
    }

    public void deletePrimero() {
        if (cabeza == null) return;
        cabeza = cabeza.getSiguiente();
        tamaño--;
    }
}
