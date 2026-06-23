package ucu.edu.aed.tda.generic_tree;


import java.util.List;
import java.util.function.Consumer;

public interface TNodoGenerico<T extends Comparable<T>> {
    T getDato();

    /**
     * Agrega un nodo con dato T al padre
     */
    boolean agregarHijo(T padre, T hijo);

    /**
     * elimina el nodo y sus hijos directos utilizando el criterio pasado
     */
    TNodoGenerico<T> eliminar(Comparable<T> criterio);

    /**
     * busca un nodo utilizando el criterio pasado
     */
    TNodoGenerico<T> buscar(Comparable<T> criterio);

    /**
     * obtiene al nodo padre del hijo que satisface el criterio pasado
     */
    TNodoGenerico<T> obtenerPadre(Comparable<T> criterio);

    void preOrden(Consumer<TNodoGenerico<T>> consumidor);

    void inOrden(Consumer<TNodoGenerico<T>> consumidor);

    void postOrden(Consumer<TNodoGenerico<T>> consumidor);

    int altura();

    /**
     * retorna la cantidad de hijos que tiene el nodo actual
     */
    int grado();

    void vaciar();

    /**
     * retorna los hijos directos de este nodo
     */
    List<T> obtenerHijos();
}
