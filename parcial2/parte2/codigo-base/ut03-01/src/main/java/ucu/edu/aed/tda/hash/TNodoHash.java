package ucu.edu.aed.tda.hash;

public class TNodoHash<K, V> {
    private final K clave;
    private final V valor;
    private boolean loteLibre = false;

    public TNodoHash(K clave, V valor) {
        this.clave = clave;
        this.valor = valor;
    }

    public K getClave() {
        return clave;
    }

    public V getValor() {
        return valor;
    }

    public boolean isLoteLibre() {
        return loteLibre;
    }

    public void setLoteLibre(boolean loteLibre) {
        this.loteLibre = loteLibre;
    }

    public Entry<K, V> getEntry() {
        return new Entry<>(clave, valor);
    }
}
