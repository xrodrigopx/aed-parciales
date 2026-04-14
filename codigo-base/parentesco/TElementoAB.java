package uy.edu.ucu.aed.tdas;

@SuppressWarnings({"rawtypes"})
/**
 * Clase que representa un nodo de un árbol binario de búsqueda (ABB). Cada nodo almacena una etiqueta
 * que lo identifica de manera única, los datos asociados a esa etiqueta, y las referencias a sus hijos
 * izquierdo y derecho.
 *
 * @param <T> Tipo de dato que se almacena en el nodo.
 */
public class TElementoAB<T> implements IElementoAB<T> {

    private Comparable etiqueta;
    private IElementoAB<T> hijoIzq;
    private IElementoAB<T> hijoDer;
    private T datos;

    /**
     * Constructor para el nodo del árbol binario de búsqueda.
     *
     * @param unaEtiqueta Etiqueta del nodo.
     * @param unosDatos   Datos asociados a la etiqueta.
     */
    public TElementoAB(Comparable unaEtiqueta, T unosDatos) {
        etiqueta = unaEtiqueta;
        datos = unosDatos;
    }

    /**
     * Retorna el hijo izquierdo del nodo actual.
     *
     * @return Hijo izquierdo del nodo.
     */
    public IElementoAB<T> getHijoIzq() {
        return hijoIzq;
    }

    /**
     * Retorna el hijo derecho del nodo actual.
     *
     * @return Hijo derecho del nodo.
     */
    public IElementoAB<T> getHijoDer() {
        return hijoDer;
    }

    /**
     * Inserta un elemento en el subárbol cuya raíz es el nodo actual, respetando las
     * propiedades del árbol binario de búsqueda.
     *
     * @param unElemento Elemento a insertar.
     * @return Verdadero si el elemento fue insertado, falso si ya existía un elemento con la misma etiqueta.
     */
    @SuppressWarnings("unchecked")
    public boolean insertar(IElementoAB<T> unElemento) {
        if (unElemento.getEtiqueta().compareTo(etiqueta) < 0) {
            if (hijoIzq != null) {
                return getHijoIzq().insertar(unElemento);
            } else {
                hijoIzq = unElemento;
                return true;
            }
        } else if (unElemento.getEtiqueta().compareTo(etiqueta) > 0) {
            if (hijoDer != null) {
                return getHijoDer().insertar(unElemento);
            } else {
                hijoDer = unElemento;
                return true;
            }
        } else {
            // Ya existe un elemento con la misma etiqueta.
            return false;
        }
    }

    /**
     * Busca un elemento en el subárbol cuya raíz es el nodo actual, utilizando la etiqueta como clave de búsqueda.
     *
     * @param unaEtiqueta Etiqueta del elemento a buscar.
     * @return El nodo encontrado. Si no se encuentra, retorna nulo.
     */
    @SuppressWarnings({"unchecked"})
    public IElementoAB<T> buscar(Comparable unaEtiqueta) {
        if (unaEtiqueta.equals(etiqueta)) {
            return this;
        } else if (unaEtiqueta.compareTo(etiqueta) < 0) {
            if (hijoIzq != null) {
                return getHijoIzq().buscar(unaEtiqueta);
            } else {
                return null;
            }
        } else if (hijoDer != null) {
            return getHijoDer().buscar(unaEtiqueta);
        } else {
            return null;
        }
    }

    /**
     * Retorna la etiqueta del nodo.
     *
     * @return Etiqueta del nodo.
     */
    @Override
    public Comparable getEtiqueta() {
        return etiqueta;
    }

    /**
     * Retorna una representación en cadena de texto de la etiqueta del nodo.
     *
     * @return Representación en texto de la etiqueta del nodo.
     */
    public String imprimir() {
        return (etiqueta.toString());
    }

    /**
     * Retorna los datos asociados a la etiqueta del nodo.
     *
     * @return Datos del nodo.
     */
    @Override
    public T getDatos() {
        return datos;
    }

    /**
     * Asigna un nodo como hijo izquierdo del nodo actual.
     *
     * @param elemento Nodo que será el hijo izquierdo.
     */
    @Override
    public void setHijoIzq(IElementoAB<T> elemento) {
        this.hijoIzq = elemento;
    }

    /**
     * Asigna un nodo como hijo derecho del nodo actual.
     *
     * @param elemento Nodo que será el hijo derecho.
     */
    @Override
    public void setHijoDer(IElementoAB<T> elemento) {
        this.hijoDer = elemento;
    }

    /**
     * Elimina un elemento del subárbol cuya raíz es el nodo actual, utilizando la etiqueta como clave de búsqueda.
     *
     * @param unaEtiqueta Etiqueta del elemento a eliminar.
     * @return El nodo que reemplaza al nodo eliminado en la posición que ocupaba dentro del árbol.
     */
    @Override
    @SuppressWarnings({"unchecked"})
    public IElementoAB<T> eliminar(Comparable unaEtiqueta) {
        if (unaEtiqueta.compareTo(this.getEtiqueta()) < 0) {
            if (this.hijoIzq != null) {
                this.hijoIzq = hijoIzq.eliminar(unaEtiqueta);
            }
            return this;
        }
        if (unaEtiqueta.compareTo(this.getEtiqueta()) > 0) {
            if (this.hijoDer != null) {
                this.hijoDer = hijoDer.eliminar(unaEtiqueta);
            }
            return this;
        }
        return quitaElNodo();
    }

    /**
     * Auxiliar para el método eliminar. Realiza las operaciones necesarias para quitar un nodo y mantener las
     * propiedades del árbol binario de búsqueda.
     *
     * @return El nodo que reemplaza al nodo eliminado.
     */
    private IElementoAB<T> quitaElNodo() {
        if (hijoIzq == null) {    // solo tiene un hijo o es hoja
            return hijoDer;
        }
        if (hijoDer == null) { // solo tiene un hijo o es hoja
            return hijoIzq;
        }
        // El nodo tiene dos hijos, se busca el nodo lexicográficamente anterior
        IElementoAB<T> elHijo = hijoIzq;
        IElementoAB<T> elPadre = this;

        while (elHijo.getHijoDer() != null) {
            elPadre = elHijo;
            elHijo = elHijo.getHijoDer();
        }
        if (elPadre != this) {
            elPadre.setHijoDer(elHijo.getHijoIzq());
            elHijo.setHijoIzq(hijoIzq);
        }
        elHijo.setHijoDer(hijoDer);
        
        // Se desconectan los hijos del nodo actual para facilitar el trabajo del recolector de basura
        setHijoIzq(null);
        setHijoDer(null);
        
        return elHijo;
    }
}