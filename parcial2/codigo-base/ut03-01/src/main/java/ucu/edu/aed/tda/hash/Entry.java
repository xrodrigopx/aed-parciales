package ucu.edu.aed.tda.hash;

/**
 * Es un simple data-type que se expone al usuario, u mno mostramos detalles del TNodoHash
 */
public class Entry<K, V> {
    private final K clave;
    private final V valor;

    public Entry(K clave, V valor) {
        this.clave = clave;
        this.valor = valor;
    }

    public K getClave() {
        return clave;
    }

    public V getValor() {
        return valor;
    }
}
