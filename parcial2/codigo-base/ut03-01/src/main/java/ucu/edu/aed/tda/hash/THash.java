package ucu.edu.aed.tda.hash;

abstract public class THash<K, V> {
    protected TNodoHash<K, V>[] hashTable;

    @SuppressWarnings("unchecked")
    public THash(int elementosEsperados) {
        hashTable = new TNodoHash[calcularCapacidadOptima(elementosEsperados)];
    }

    /**
     * Busca un valor V, con clave K. Devuelve null si la clave no existe.
     */
    public V buscar(K clave) {
        return buscar(clave, new Report());
    }

    /**
     * implementación de buscar, con soporte de reporte
     */
    public abstract V buscar(K clave, Report report);


    /**
     * Elimina una clave K, sin generar reporte
     */
    public boolean delete(K clave) {
        return delete(clave, new Report());
    }

    /**
     * Elimina la clave K, y genera reportes
     */
    public abstract boolean delete(K clave, Report report);

    /**
     * Retorna true si el valor  se agregó
     */
    public boolean insertar(K clave, V valor) {
        return insertar(clave, valor, new Report());
    }

    /**
     * implementación del insertar con generación de reportes
     */
    public abstract boolean insertar(K clave, V valor, Report report);

    /**
     * Devuelve la posición generada por la función dado una clave K.
     * CUIDADO: el valor devuelve debe ser MAYOR O IGUAL A 0.
     */
    protected abstract int functionHashing(K valor);

    /**
     * retorna true si la tabla hash es vacía
     */
    public abstract boolean esVacio();


    /**
     * Elimina todas las entradas del THash
     */
    public abstract void vaciar();

    /**
     * Devuelve la capacidad óptima que debe tener la tabla Hash para evitar colisiones y mejorar las operaciones de búsquedas.
     */
    protected abstract int calcularCapacidadOptima(int elementosEsperados);


    /**
     * Cuando se supera determinado factor de carga es necesario redimensionar la tabla para reducir las probabilidad de colisión.
     */
    protected abstract boolean redimensionar();


    /**
     * requerimientos opcionales
     */
    public abstract Iterable<Entry<K, V>> entries();

    public abstract Iterable<K> keys();

    public abstract Iterable<V> values();

}
