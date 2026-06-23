package ucu.edu.aed.classes.problem_set_01;

import java.util.Comparator;
import java.util.function.IntFunction;
import java.util.function.Predicate;

import ucu.edu.aed.classes.LinkedList;
import ucu.edu.aed.tda.TDALista;

public class SortedList<T> extends LinkedList<T> {

    private final Comparator<T> compareData;

    // constructor principal: requiere un comparador para saber como ordenar
    public SortedList(Comparator<T> comparator) {
        super();
        if (comparator == null) {
            throw new IllegalArgumentException("El comparador no puede ser nulo");
        }
        this.compareData = comparator;
    }

    @Override
    public void agregar(T elem) {
        for (int i = 0; i < super.tamaño(); i++) {
            T actualData = super.obtener(i);
            if (compareData.compare(elem, actualData) <= 0) {
                super.agregar(i, elem);
                return;
            }
        }
        super.agregar(elem);
    }

    @Override
    public Object[] aArreglo() {
        return super.aArreglo();
    }

    @Override
    public T[] aArreglo(IntFunction<T[]> generador) {
        return super.aArreglo(generador);
    }

    @Override
    public void agregar(int index, T elem) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T buscar(Predicate<T> criterio) {
        return super.buscar(criterio);
    }

    @Override
    public boolean contiene(T elem) {
        return super.contiene(elem);
    }

    @Override
    public boolean esVacio() {
        return super.esVacio();
    }

    @Override
    public int indiceDe(T elem) {
        return super.indiceDe(elem);
    }

    @Override
    public T obtener(int index) {
        return super.obtener(index);
    }

    @Override
    public TDALista<T> ordenar(Comparator<T> comparator) {
        return super.ordenar(comparator);
    }

    @Override
    public T remover(int index) {
        return super.remover(index);
    }

    @Override
    public boolean remover(T elem) {
        return super.remover(elem);
    }

    @Override
    public int tamaño() {
        return super.tamaño();
    }

    @Override
    public void vaciar() {
        super.vaciar();
    }

}
