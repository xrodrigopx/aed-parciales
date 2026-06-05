package ucu.edu.aed.tda;

import java.util.function.Consumer;

/**
 * Implementación recursiva de un nodo de árbol AVL (auto-balanceado).
 *
 * <p>Un árbol AVL es un BST que mantiene la diferencia de alturas entre los subárboles
 * izquierdo y derecho de cada nodo (factor de balance) acotada en {-1, 0, 1}. Esto garantiza
 * altura O(log n) y por lo tanto búsquedas, inserciones y eliminaciones en O(log n)
 * <strong>independientemente del orden de inserción</strong>, lo que satisface el requerimiento
 * R3 del sistema (historial de tareas procesadas).</p>
 *
 * <p>Cada nodo conserva su propia altura para evitar recalcularla recursivamente.
 * Tras cada inserción se reacomoda mediante rotaciones (simples y dobles).</p>
 *
 * @param <T> tipo de dato almacenado en el nodo
 */
public class AVLElemento<T> implements TDAElemento<T> {

    private T dato;
    private TDAElemento<T> hijoIzquierdo;
    private TDAElemento<T> hijoDerecho;
    private int altura;

    /**
     * Construye un nodo hoja AVL con el dato indicado y altura inicial 1.
     *
     * @param dato dato a almacenar; no se realizan validaciones (la responsabilidad
     *             corresponde al árbol contenedor).
     */
    public AVLElemento(T dato) {
        this.dato = dato;
        this.altura = 1;
    }

    /**
     * Asigna el hijo izquierdo del nodo. Útil internamente durante las rotaciones.
     *
     * @param hijoIzquierdo nuevo hijo izquierdo (puede ser {@code null})
     */
    @Override
    public void setHijoIzquierdo(TDAElemento<T> hijoIzquierdo) {
        this.hijoIzquierdo = hijoIzquierdo;
    }

    /**
     * Asigna el hijo derecho del nodo. Útil internamente durante las rotaciones.
     *
     * @param hijoDerecho nuevo hijo derecho (puede ser {@code null})
     */
    @Override
    public void setHijoDerecho(TDAElemento<T> hijoDerecho) {
        this.hijoDerecho = hijoDerecho;
    }

    /**
     * @return el hijo izquierdo del nodo, o {@code null} si no tiene.
     */
    @Override
    public TDAElemento<T> getHijoIzquierdo() {
        return hijoIzquierdo;
    }

    /**
     * @return el hijo derecho del nodo, o {@code null} si no tiene.
     */
    @Override
    public TDAElemento<T> getHijoDerecho() {
        return hijoDerecho;
    }

    /**
     * Reemplaza el dato del nodo. No altera la estructura del árbol; el llamador
     * es responsable de no romper el invariante BST si se cambia la clave.
     *
     * @param dato nuevo valor a almacenar
     */
    @Override
    public void setDato(T dato) {
        this.dato = dato;
    }

    /**
     * @return el dato almacenado en el nodo.
     */
    @Override
    public T getDato() {
        return dato;
    }

    /**
     * Búsqueda binaria recursiva por un criterio.
     *
     * <p>El criterio actúa como clave de comparación: {@code criterioBusqueda.compareTo(dato)}
     * devuelve negativo, cero o positivo para indicar si la búsqueda debe seguir por la
     * izquierda, terminar (coincidencia) o seguir por la derecha. Esta es la operación
     * que satisface la búsqueda O(log n) del historial (R3).</p>
     *
     * @param criterioBusqueda criterio que define la clave a buscar
     * @return el nodo cuyo dato coincide con el criterio, o {@code null} si no existe
     */
    @Override
    public TDAElemento<T> buscar(Comparable<T> criterioBusqueda) {
        int cmp = criterioBusqueda.compareTo(this.dato);
        if (cmp == 0) return this;
        if (cmp < 0) {
            return hijoIzquierdo == null ? null : hijoIzquierdo.buscar(criterioBusqueda);
        }
        return hijoDerecho == null ? null : hijoDerecho.buscar(criterioBusqueda);
    }

    /**
     * Operación de eliminación.
     *
     * <p>El sistema de gestión de naves no requiere eliminar tareas del historial
     * (R4 cancela tareas pendientes, no procesadas), por lo que esta operación
     * se deja sin implementar.</p>
     *
     * @param criterioBusqueda criterio que indica qué nodo eliminar
     * @return siempre {@code null}
     */
    @Override
    public TDAElemento<T> eliminar(Comparable<T> criterioBusqueda) {
        return null;
    }

    /**
     * Convierte un {@link Comparable Comparable&lt;T&gt;} entrante en una instancia de T.
     *
     * <p>El TDA define los métodos de inserción recibiendo {@code Comparable<T>} para
     * permitir criterios de búsqueda sintéticos (p.ej. {@code CriterioPorId}). Al insertar,
     * sin embargo, el dato debe ser una instancia real de T. La contrapartida de esta
     * flexibilidad es que el cast no puede ser verificado por el compilador; centralizarlo
     * aquí limita el {@code @SuppressWarnings} a un único punto y documenta la
     * precondición: <em>quien llame a {@code insertar} con un objeto que no sea de tipo T
     * provocará un {@link ClassCastException} en tiempo de ejecución</em>.</p>
     *
     * @param dato comparable que en realidad debe ser una instancia de T
     * @return el mismo objeto, tipado como T
     */
    @SuppressWarnings("unchecked")
    static <T> T comoT(Comparable<T> dato) {
        return (T) dato;
    }

    /**
     * Inserta recursivamente respetando el invariante BST. <strong>No</strong> rebalancea
     * por sí mismo; está pensada para casos donde no se requiere AVL estricto. Para uso
     * dentro del sistema (R3) se utiliza {@link #insertarBalanceado(Comparable)} a través
     * del árbol contenedor.
     *
     * @param nuevoDato dato a insertar (debe ser una instancia de T comparable contra T)
     * @return {@code true} si se insertó; {@code false} si ya existía un nodo con esa clave
     */
    @Override
    public boolean insertar(Comparable<T> nuevoDato) {
        int cmp = nuevoDato.compareTo(this.dato);
        if (cmp == 0) return false;
        if (cmp < 0) {
            if (hijoIzquierdo == null) {
                hijoIzquierdo = new AVLElemento<>(comoT(nuevoDato));
                actualizarAltura();
                return true;
            }
            boolean ok = hijoIzquierdo.insertar(nuevoDato);
            actualizarAltura();
            return ok;
        }
        if (hijoDerecho == null) {
            hijoDerecho = new AVLElemento<>(comoT(nuevoDato));
            actualizarAltura();
            return true;
        }
        boolean ok = hijoDerecho.insertar(nuevoDato);
        actualizarAltura();
        return ok;
    }

    /**
     * Inserta y devuelve la raíz del subárbol balanceado.
     *
     * <p>Este es el método AVL real: tras insertar recursivamente actualiza la altura,
     * calcula el factor de balance y, si es necesario, ejecuta una rotación
     * (simple izquierda/derecha o doble izquierda-derecha/derecha-izquierda).
     *
     * <p>Devuelve la raíz resultante porque la rotación puede cambiar el nodo raíz del
     * subárbol; el árbol contenedor utiliza ese valor para reasignar el puntero al padre
     * (o el campo raíz, en el caso de la raíz absoluta del árbol).</p>
     *
     * @param nuevoDato dato a insertar (debe ser comparable contra T)
     * @return la nueva raíz del subárbol después de balancear
     */
    public AVLElemento<T> insertarBalanceado(Comparable<T> nuevoDato) {
        int cmp = nuevoDato.compareTo(this.dato);
        if (cmp == 0) return this;
        if (cmp < 0) {
            if (hijoIzquierdo == null) {
                hijoIzquierdo = new AVLElemento<>(comoT(nuevoDato));
            } else {
                hijoIzquierdo = ((AVLElemento<T>) hijoIzquierdo).insertarBalanceado(nuevoDato);
            }
        } else {
            if (hijoDerecho == null) {
                hijoDerecho = new AVLElemento<>(comoT(nuevoDato));
            } else {
                hijoDerecho = ((AVLElemento<T>) hijoDerecho).insertarBalanceado(nuevoDato);
            }
        }
        actualizarAltura();
        return balancear();
    }

    /**
     * Recalcula la altura del nodo a partir de la altura de sus hijos.
     * Debe invocarse tras cada modificación estructural (inserción o rotación).
     */
    private void actualizarAltura() {
        this.altura = 1 + Math.max(alturaDe(hijoIzquierdo), alturaDe(hijoDerecho));
    }

    /**
     * Devuelve la altura de un nodo, tratando el caso {@code null} como altura 0.
     *
     * @param n nodo a consultar (puede ser {@code null})
     * @return altura del nodo o 0 si es {@code null}
     */
    private static <T> int alturaDe(TDAElemento<T> n) {
        return n == null ? 0 : ((AVLElemento<T>) n).altura;
    }

    /**
     * Calcula el factor de balance: altura(izq) - altura(der).
     * Un árbol AVL mantiene este valor en {-1, 0, 1}.
     *
     * @return factor de balance del nodo
     */
    private int factorBalance() {
        return alturaDe(hijoIzquierdo) - alturaDe(hijoDerecho);
    }

    /**
     * Aplica las rotaciones necesarias para restaurar la propiedad AVL en este nodo.
     *
     * <p>Casos:
     * <ul>
     *   <li><b>Izquierda-Izquierda</b> (fb&gt;1, hijo izq con fb&gt;=0): rotación simple a la derecha.</li>
     *   <li><b>Izquierda-Derecha</b> (fb&gt;1, hijo izq con fb&lt;0): doble — primero izq sobre el hijo, luego der.</li>
     *   <li><b>Derecha-Derecha</b> (fb&lt;-1, hijo der con fb&lt;=0): rotación simple a la izquierda.</li>
     *   <li><b>Derecha-Izquierda</b> (fb&lt;-1, hijo der con fb&gt;0): doble — primero der sobre el hijo, luego izq.</li>
     * </ul>
     *
     * @return la nueva raíz del subárbol (puede ser {@code this} si no hizo falta rotar)
     */
    private AVLElemento<T> balancear() {
        int fb = factorBalance();
        if (fb > 1) {
            AVLElemento<T> izq = (AVLElemento<T>) hijoIzquierdo;
            if (izq.factorBalance() < 0) {
                hijoIzquierdo = izq.rotarIzquierda();
            }
            return rotarDerecha();
        }
        if (fb < -1) {
            AVLElemento<T> der = (AVLElemento<T>) hijoDerecho;
            if (der.factorBalance() > 0) {
                hijoDerecho = der.rotarDerecha();
            }
            return rotarIzquierda();
        }
        return this;
    }

    /**
     * Rotación simple a la derecha. Promueve al hijo izquierdo a raíz del subárbol.
     *
     * <pre>
     *      this              izq
     *      / \               / \
     *    izq  C   ===&gt;     A  this
     *    / \                    / \
     *   A   B                  B   C
     * </pre>
     *
     * @return la nueva raíz del subárbol (el antiguo hijo izquierdo)
     */
    private AVLElemento<T> rotarDerecha() {
        AVLElemento<T> nuevaRaiz = (AVLElemento<T>) this.hijoIzquierdo;
        this.hijoIzquierdo = nuevaRaiz.hijoDerecho;
        nuevaRaiz.hijoDerecho = this;
        this.actualizarAltura();
        nuevaRaiz.actualizarAltura();
        return nuevaRaiz;
    }

    /**
     * Rotación simple a la izquierda. Promueve al hijo derecho a raíz del subárbol.
     *
     * <pre>
     *      this              der
     *      / \               / \
     *     A  der    ===&gt;  this  C
     *        / \           / \
     *       B   C         A   B
     * </pre>
     *
     * @return la nueva raíz del subárbol (el antiguo hijo derecho)
     */
    private AVLElemento<T> rotarIzquierda() {
        AVLElemento<T> nuevaRaiz = (AVLElemento<T>) this.hijoDerecho;
        this.hijoDerecho = nuevaRaiz.hijoIzquierdo;
        nuevaRaiz.hijoIzquierdo = this;
        this.actualizarAltura();
        nuevaRaiz.actualizarAltura();
        return nuevaRaiz;
    }

    /**
     * Recorrido in-order (izq, raíz, der). En un BST genera el orden ascendente de las claves.
     *
     * @param consumidor acción a ejecutar sobre cada nodo
     */
    @Override
    public void inOrder(Consumer<TDAElemento<T>> consumidor) {
        if (hijoIzquierdo != null) hijoIzquierdo.inOrder(consumidor);
        consumidor.accept(this);
        if (hijoDerecho != null) hijoDerecho.inOrder(consumidor);
    }

    /**
     * Recorrido pre-order (raíz, izq, der). Útil para serializar/duplicar la estructura.
     *
     * @param consumidor acción a ejecutar sobre cada nodo
     */
    @Override
    public void preOrder(Consumer<TDAElemento<T>> consumidor) {
        consumidor.accept(this);
        if (hijoIzquierdo != null) hijoIzquierdo.preOrder(consumidor);
        if (hijoDerecho != null) hijoDerecho.preOrder(consumidor);
    }

    /**
     * Recorrido post-order (izq, der, raíz). Útil para liberar recursos hijos antes que el padre.
     *
     * @param consumidor acción a ejecutar sobre cada nodo
     */
    @Override
    public void postOrder(Consumer<TDAElemento<T>> consumidor) {
        if (hijoIzquierdo != null) hijoIzquierdo.postOrder(consumidor);
        if (hijoDerecho != null) hijoDerecho.postOrder(consumidor);
        consumidor.accept(this);
    }

    /**
     * @return {@code true} si el nodo no tiene hijos (es hoja).
     */
    @Override
    public boolean esHoja() {
        return hijoIzquierdo == null && hijoDerecho == null;
    }

    /**
     * Cuenta recursivamente las hojas del subárbol enraizado en este nodo.
     *
     * @return cantidad de hojas (mínimo 1 si este nodo es hoja)
     */
    @Override
    public int cantidadHojas() {
        if (esHoja()) return 1;
        int total = 0;
        if (hijoIzquierdo != null) total += hijoIzquierdo.cantidadHojas();
        if (hijoDerecho != null) total += hijoDerecho.cantidadHojas();
        return total;
    }

    /**
     * @return cantidad de nodos internos (no hojas) del subárbol.
     */
    @Override
    public int cantidadNodosInternos() {
        return cantidadNodos() - cantidadHojas();
    }

    /**
     * Cuenta recursivamente todos los nodos del subárbol incluyendo este.
     *
     * @return cantidad total de nodos del subárbol
     */
    @Override
    public int cantidadNodos() {
        int total = 1;
        if (hijoIzquierdo != null) total += hijoIzquierdo.cantidadNodos();
        if (hijoDerecho != null) total += hijoDerecho.cantidadNodos();
        return total;
    }

    /**
     * @return altura del subárbol enraizado en este nodo (1 si es hoja).
     */
    @Override
    public int altura() {
        return altura;
    }

    /**
     * Devuelve el nivel relativo (distancia en aristas desde este nodo) del primer nodo
     * que coincide con el criterio. La búsqueda sigue las reglas BST.
     *
     * @param criterioBusqueda criterio que define la clave a localizar
     * @return nivel relativo, 0 si coincide con este nodo, o -1 si no se encuentra
     */
    @Override
    public int obtenerNivel(Comparable<T> criterioBusqueda) {
        int cmp = criterioBusqueda.compareTo(this.dato);
        if (cmp == 0) return 0;
        TDAElemento<T> sig = cmp < 0 ? hijoIzquierdo : hijoDerecho;
        if (sig == null) return -1;
        int n = sig.obtenerNivel(criterioBusqueda);
        return n == -1 ? -1 : n + 1;
    }
}
