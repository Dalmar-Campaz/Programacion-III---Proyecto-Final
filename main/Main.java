package main;
import estructuras.*;
public class Main {
    public static void main(String[] args) {
        LinkedList<Integer> lista = new LinkedList<>();
        lista.add(10);
        lista.add(20);
        lista.add(30);
        lista.add(40);

        lista.imprimir();

        Pila<Integer> pila = new Pila<>();
        pila.push(10);
        pila.push(20);
        pila.push(30);
        pila.push(40);
        pila.push(50);

        pila.imprimir();
    }
}
