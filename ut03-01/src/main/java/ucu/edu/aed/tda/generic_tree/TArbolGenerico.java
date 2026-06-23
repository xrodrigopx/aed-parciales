package ucu.edu.aed.tda.generic_tree;

import java.util.function.Consumer;

public interface TArbolGenerico<T extends Comparable<T>> {
    /**
     * Agrega un nodo como hijo del padre.
     */
     boolean agregarHijo(Comparable<T> padre, T hijo);

    /**
     * Elimina un nodo utilizando el criterio. Los hijos se eliminan también.
     */
    void eliminar(Comparable<T> criterio);

    /**
     * retorna el padre del hijo que satisface el criterio
     */
    T obtenerPadre(Comparable<T> criterio);

    T buscar(Comparable<T> criterio);

    void preOrden(Consumer<T> consumidor);

    void inOrden(Consumer<T> consumidor);

    void postOrden(Consumer<T> consumidor);

    void vaciar();

    int grado(Comparable<T> nodo);

    int altura(Comparable<T> nodo);
}
