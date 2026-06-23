package ucu.edu.aed.classes;

import java.util.Comparator;
import java.util.function.Predicate;

import ucu.edu.aed.tda.TDACola;
import ucu.edu.aed.tda.TDALista;

// cola hecha arriba de la lista enlazada
public class Queue<T> extends LinkedList<T> implements TDACola<T> {

    // crea la cola vacia
    public Queue() {
        super();
    } 

    @Override
    // mira el primer elemento sin sacarlo
    public T frente() {
        if (esVacio()) {
            throw new java.util.NoSuchElementException();
        }
        return super.obtener(0);
    }

    @Override
    // mete un dato al final de la cola
    public boolean poneEnCola(T dato) {
        final int previoussize = super.tamaño();
        super.agregar(dato);
        return super.tamaño() > previoussize;
    }

    @Override
    // saca el primer dato de la cola
    public T quitaDeCola() {
        if (esVacio()) {
            throw new java.util.NoSuchElementException();
        }
        return super.remover(0);
    }

    @Override
    // no deja usar insercion libre en una cola
    public void agregar(T elem) {
        throw new UnsupportedOperationException();
    }

    @Override
    // no deja usar insercion por indice en una cola
    public void agregar(int index, T elem) {
        throw new UnsupportedOperationException();
    }

    @Override
    // reutiliza la busqueda de la lista base
    public T buscar(Predicate<T> criterio) {
        return super.buscar(criterio);
    }

    @Override
    // reutiliza la consulta de la lista base
    public boolean contiene(T elem) {
        return super.contiene(elem);
    }

    @Override
    // reutiliza el chequeo de vacio
    public boolean esVacio() {
        return super.esVacio();
    }

    @Override
    // reutiliza el indice de la lista base
    public int indiceDe(T elem) {
        return super.indiceDe(elem);
    }

    @Override
    // no deja acceder por indice en una cola
    public T obtener(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    // no define ordenamiento para la cola
    public TDALista<T> ordenar(Comparator<T> comparator) {
        throw new UnsupportedOperationException();
    }

    @Override
    // no deja borrar por posicion en una cola
    public T remover(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    // no deja borrar por valor con esta interfaz
    public boolean remover(T elem) {
        throw new UnsupportedOperationException();
    }

    @Override
    // devuelve el tamanio de la lista base
    public int tamaño() {
        return super.tamaño();
    }

    @Override
    // vacia toda la estructura
    public void vaciar() {
        super.vaciar();
    }
}
