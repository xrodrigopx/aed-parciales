package ucu.edu.aed.classes;

import java.util.Comparator;
import java.util.function.Predicate;

import ucu.edu.aed.tda.TDALista;
import ucu.edu.aed.tda.TDAPila;

// pila montada sobre la lista enlazada
public class Stack<T> extends LinkedList<T> implements TDAPila<T>{

    // crea la pila vacia
    public Stack() {
        super();
    }

    @Override
    // mete un dato arriba de todo
    public void mete(T dato) {
        super.agregar(0,dato);
    }

    @Override
    // saca el dato del tope
    public T saca() {
        if (esVacio()) {
            throw new java.util.NoSuchElementException();
        }
        return super.remover(0);
    }

    @Override
    // mira el dato del tope sin sacarlo
    public T tope() {
        if (esVacio()) {
            throw new java.util.NoSuchElementException();
        }
        return super.obtener(0);
    }

    @Override
    // no deja usar insercion libre en una pila
    public void agregar(T elem) {
        throw new UnsupportedOperationException();
    }

    @Override
    // no deja usar insercion por indice en una pila
    public void agregar(int index, T elem) {
        throw new UnsupportedOperationException();
    }

    @Override
    // reutiliza la busqueda de la lista base
    public T buscar(Predicate<T> criterio) {
        return super.buscar(criterio);
    }

    @Override
    // reutiliza la verificacion de contenido
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
    // no deja acceder por indice en una pila
    public T obtener(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    // no define ordenamiento para la pila
    public TDALista<T> ordenar(Comparator<T> comparator) {
        throw new UnsupportedOperationException();
    }

    @Override
    // no permite borrar por posicion en esta estructura
    public T remover(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    // no permite borrar por valor en esta estructura
    public boolean remover(T elem) {
        throw new UnsupportedOperationException();
    }

    @Override
    // devuelve el tamanio actual
    public int tamaño() {
        return super.tamaño();
    }

    @Override
    // vacia toda la pila
    public void vaciar() {
        super.vaciar();
    }

}
