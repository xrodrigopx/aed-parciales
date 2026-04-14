package uy.edu.ucu.aed.tdas;

/**
 * Interfaz para el elemento de un árbol binario.
 *
 * @param <T> Tipo de los datos almacenados en el elemento.
 */
@SuppressWarnings({"rawtypes"})
public interface IElementoAB<T> {

    /**
     * Obtiene el valor de la etiqueta del nodo.
     *
     * @return Etiqueta del nodo.
     */
    public Comparable getEtiqueta();

    /**
     * Obtiene el hijo izquierdo del nodo.
     *
     * @return Hijo izquierdo del nodo.
     */
    public IElementoAB<T> getHijoIzq();

    /**
     * Obtiene el hijo derecho del nodo.
     *
     * @return Hijo derecho del nodo.
     */
    public IElementoAB<T> getHijoDer();

    /**
     * Asigna el hijo izquierdo del nodo.
     *
     * @param elemento Elemento a asignar como hijo izquierdo.
     */
    public void setHijoIzq(IElementoAB<T> elemento);

    /**
     * Asigna el hijo derecho del nodo.
     *
     * @param elemento Elemento a asignar como hijo derecho.
     */
    public void setHijoDer(IElementoAB<T> elemento);

    /**
     * Busca un elemento dentro del árbol con la etiqueta indicada.
     *
     * @param unaEtiqueta Etiqueta del nodo a buscar.
     * @return Elemento encontrado. Si no se encuentra, retorna nulo.
     */
    public IElementoAB<T> buscar(Comparable unaEtiqueta);

    /**
     * Inserta un elemento en el árbol.
     *
     * @param elemento Elemento a insertar.
     * @return Éxito de la operación.
     */
    public boolean insertar(IElementoAB<T> elemento);

    /**
     * Retorna los datos contenidos en el elemento.
     *
     * @return Los datos del elemento.
     */
    public T getDatos();
   
    /**
     * Elimina un elemento dado una etiqueta.
     *
     * @param unaEtiqueta Etiqueta del elemento a eliminar.
     * @return Elemento que fue eliminado. Si no se encuentra, retorna nulo.
     */
    public IElementoAB<T> eliminar(Comparable unaEtiqueta);
}