package ucu.edu.aed.tda;

import java.util.function.Consumer;

/**
 * Implementación de un árbol binario auto-balanceado (AVL) sobre la interfaz {@link TDAArbolBinario}.
 *
 * <p>Esta clase delega la lógica recursiva en {@link AVLElemento} y se encarga de mantener
 * la referencia a la raíz, así como de reasignarla cuando una rotación cambia el nodo raíz
 * absoluto del árbol.</p>
 *
 * <p>Garantiza tiempo de búsqueda <strong>O(log n) en peor caso</strong> sin importar el orden
 * de inserción, satisfaciendo el requerimiento R3 del sistema de gestión de naves.</p>
 *
 * @param <T> tipo de dato almacenado
 */
public class AVLArbol<T> implements TDAArbolBinario<T> {

    private AVLElemento<T> raiz;

    /**
     * Busca un dato según un criterio (clave) y devuelve el dato encontrado.
     *
     * @param predicate criterio comparable contra T que define la clave a buscar
     * @return el dato encontrado o {@code null} si no existe
     */
    @Override
    public T buscar(Comparable<T> predicate) {
        if (raiz == null) return null;
        TDAElemento<T> encontrado = raiz.buscar(predicate);
        return encontrado == null ? null : encontrado.getDato();
    }

    /**
     * @return el nodo raíz del árbol o {@code null} si el árbol está vacío.
     */
    @Override
    public TDAElemento<T> obtenerRaiz() {
        return raiz;
    }

    /**
     * Eliminación de nodos según un criterio.
     *
     * <p>El sistema de gestión de naves no requiere eliminar tareas del historial,
     * por lo que esta operación no se implementa.</p>
     *
     * @param criterioBusqueda criterio que indica qué nodo eliminar
     * @return siempre {@code false}
     */
    @Override
    public boolean eliminar(Comparable<T> criterioBusqueda) {
        return false;
    }

    /**
     * Inserta un dato en el árbol manteniendo la propiedad AVL.
     *
     * <p>Si el árbol está vacío crea la raíz; si ya existe un nodo con la misma clave
     * la operación falla. En caso contrario delega la inserción balanceada a la raíz
     * y reasigna la referencia, ya que la rotación puede cambiar el nodo raíz.</p>
     *
     * @param dato dato a insertar (debe ser comparable contra T)
     * @return {@code true} si se insertó; {@code false} si ya existía un nodo con esa clave
     */
    @Override
    public boolean insertar(Comparable<T> dato) {
        if (raiz == null) {
            raiz = new AVLElemento<>(AVLElemento.comoT(dato));
            return true;
        }
        if (raiz.buscar(dato) != null) return false;
        raiz = raiz.insertarBalanceado(dato);
        return true;
    }

    /**
     * Recorrido in-order (orden ascendente de claves).
     *
     * @param consumidor acción a ejecutar sobre cada dato
     */
    @Override
    public void inOrder(Consumer<T> consumidor) {
        if (raiz != null) raiz.inOrder(n -> consumidor.accept(n.getDato()));
    }

    /**
     * Recorrido pre-order.
     *
     * @param consumidor acción a ejecutar sobre cada dato
     */
    @Override
    public void preOrder(Consumer<T> consumidor) {
        if (raiz != null) raiz.preOrder(n -> consumidor.accept(n.getDato()));
    }

    /**
     * Recorrido post-order.
     *
     * @param consumidor acción a ejecutar sobre cada dato
     */
    @Override
    public void postOrder(Consumer<T> consumidor) {
        if (raiz != null) raiz.postOrder(n -> consumidor.accept(n.getDato()));
    }

    /**
     * @return {@code true} si el árbol no tiene nodos.
     */
    @Override
    public boolean esVacio() {
        return raiz == null;
    }

    /**
     * @return cantidad total de nodos del árbol.
     */
    @Override
    public int cantidadNodos() {
        return raiz == null ? 0 : raiz.cantidadNodos();
    }

    /**
     * @return cantidad de nodos hoja del árbol.
     */
    @Override
    public int cantidadHojas() {
        return raiz == null ? 0 : raiz.cantidadHojas();
    }

    /**
     * @return cantidad de nodos internos (no hoja) del árbol.
     */
    @Override
    public int cantidadNodosInternos() {
        return raiz == null ? 0 : raiz.cantidadNodosInternos();
    }
}
