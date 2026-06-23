package ucu.edu.aed.classes;

import java.util.Comparator;
import java.util.function.Predicate;

import ucu.edu.aed.tda.TDAConjunto;
import ucu.edu.aed.tda.TDALista;

// conjunto simple sin repetidos sobre una lista enlazada
public class Set<T> extends LinkedList<T> implements TDAConjunto<T> {

    // crea el conjunto vacio
    public Set() {
        super();
    }

    @Override
    // arma la diferencia entre conjuntos
    public TDAConjunto<T> diferencia(TDAConjunto<T> otro) {
        Set<T> difference = new Set<>();
        for (int i = 0; i < super.tamaño(); i++) {
            T actualData = super.obtener(i);
            if (!otro.contiene(actualData)) {
                difference.agregar(actualData);
            }
        }
        return difference;
    }

    @Override
    // revisa si todos los elementos estan en el otro conjunto
    public boolean esSubconjuntoDe(TDAConjunto<T> otro) {
        return diferencia(otro).esVacio();
    }

    @Override
    // arma la interseccion entre conjuntos
    public TDAConjunto<T> interseccion(TDAConjunto<T> otro) {
        Set<T> intersection = new Set<>();
        for (int i = 0; i < super.tamaño(); i++) {
            T actualData = super.obtener(i);
            if (otro.contiene(actualData)) {
                intersection.agregar(actualData);
            }
        }
        return intersection;
    }

    @Override
    // arma la union de dos conjuntos
    public TDAConjunto<T> union(TDAConjunto<T> otro) {
        Set<T> union = new Set<>();
        for (int i = 0; i < super.tamaño(); i++) {
            union.agregar(super.obtener(i));
        }
        for (int i = 0; i < otro.tamaño(); i++) {
            union.agregar(otro.obtener(i));
        }
        return union;
    }

    @Override
    // agrega solo si no estaba antes
    public void agregar(T elem) {
        if (!contiene(elem)) {
            super.agregar(elem);
        }
    }

    @Override
    // no usa insercion por indice en este TDA
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
    // reutiliza el acceso por indice
    public T obtener(int index) {
        return super.obtener(index);
    }

    @Override
    // no define ordenamiento para el conjunto
    public TDALista<T> ordenar(Comparator<T> comparator) {
        throw new UnsupportedOperationException();
    }

    @Override
    // no permite borrar por indice en esta version
    public T remover(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    // reutiliza el borrado por valor de la lista base
    public boolean remover(T elem) {
        return super.remover(elem);
    }

    @Override
    // devuelve el tamanio actual
    public int tamaño() {
        return super.tamaño();
    }

    @Override
    // limpia todos los elementos
    public void vaciar() {
        super.vaciar();    
    }
}
