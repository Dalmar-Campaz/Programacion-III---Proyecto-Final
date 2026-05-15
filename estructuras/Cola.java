package estructuras;

public class Cola<T> {
    private LinkedList<T> elementos;

    public Cola() {
        elementos = new LinkedList<>();
    }

    public void add(T elemento) {
        elementos.add(elemento);
    }

    public T peek(){
        if(estaVacia()) return null;
        return elementos.obtener(0);
    }
    public int getTamaño(){
        return elementos.getTamaño();
    }

    public boolean estaVacia(){
        return elementos.estaVacia();
    }

    public T remove() {
        if (elementos.estaVacia())
            return null;
        T cabeza = elementos.obtener(0);
        elementos.deletePrimero();
        return cabeza;
    }

    public void imprimir() {
        System.out.print("Cabeza -> ");
        elementos.imprimir();
    }
}
