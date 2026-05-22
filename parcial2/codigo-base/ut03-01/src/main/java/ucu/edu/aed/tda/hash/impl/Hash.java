package ucu.edu.aed.tda.hash.impl;

import ucu.edu.aed.tda.hash.Entry;
import ucu.edu.aed.tda.hash.Report;
import ucu.edu.aed.tda.hash.THash;
import ucu.edu.aed.tda.hash.TNodoHash;

import java.util.ArrayList;
import java.util.List;

public class Hash<K, V> extends THash<K, V> {

    private int cantidadElementos;

    public Hash(int elementosEsperados) {
        super(elementosEsperados);
        this.cantidadElementos = 0;
    }

    @Override
    protected int functionHashing(K clave) {
        if (clave == null) {
            return 0;
        }
        int h = clave.hashCode() % hashTable.length;
        if (h < 0) {
            h += hashTable.length;
        }
        return h;
    }

    @Override
    public V buscar(K clave, Report report) {
        if (clave == null) {
            return null;
        }
        if (cantidadElementos == 0) {
            return null;
        }
        int h0 = functionHashing(clave);
        int comparaciones = 0;

        for (int i = 0; i < hashTable.length; i++) {
            int pos = (h0 + i) % hashTable.length;
            comparaciones++;

            if (hashTable[pos] == null) {
                break;
            }
            if (hashTable[pos].isLoteLibre()) {
                continue;
            }
            if (hashTable[pos].getClave().equals(clave)) {
                report.setCantidadComparaciones(comparaciones);
                return hashTable[pos].getValor();
            }
        }

        report.setCantidadComparaciones(comparaciones);
        return null;
    }

    @Override
    public boolean insertar(K clave, V valor, Report report) {
        if (clave == null) {
            return false;
        }
        if (cantidadElementos > hashTable.length * 0.7) {
            redimensionar();
        }

        int h0 = functionHashing(clave);
        int primerLibre = -1;
        int comparaciones = 0;

        for (int i = 0; i < hashTable.length; i++) {
            int pos = (h0 + i) % hashTable.length;
            comparaciones++;

            if (hashTable[pos] == null) {
                if (primerLibre == -1) {
                    primerLibre = pos;
                }
                break;
            }
            if (hashTable[pos].isLoteLibre()) {
                if (primerLibre == -1) {
                    primerLibre = pos;
                }
                continue;
            }
            if (hashTable[pos].getClave().equals(clave)) {
                report.setCantidadComparaciones(comparaciones);
                return false;
            }
        }

        if (primerLibre == -1) {
            redimensionar();
            return insertar(clave, valor, report);
        }

        hashTable[primerLibre] = new TNodoHash<>(clave, valor);
        cantidadElementos++;
        report.setCantidadComparaciones(comparaciones);
        return true;
    }

    @Override
    public boolean delete(K clave, Report report) {
        if (clave == null) {
            return false;
        }
        if (cantidadElementos == 0) {
            return false;
        }
        int h0 = functionHashing(clave);
        int comparaciones = 0;

        for (int i = 0; i < hashTable.length; i++) {
            int pos = (h0 + i) % hashTable.length;
            comparaciones++;

            if (hashTable[pos] == null) {
                break;
            }
            if (hashTable[pos].isLoteLibre()) {
                continue;
            }
            if (hashTable[pos].getClave().equals(clave)) {
                hashTable[pos].setLoteLibre(true);
                cantidadElementos--;
                report.setCantidadComparaciones(comparaciones);
                return true;
            }
        }

        report.setCantidadComparaciones(comparaciones);
        return false;
    }

    @Override
    public boolean esVacio() {
        return cantidadElementos == 0;
    }

    @Override
    public void vaciar() {
        for (int i = 0; i < hashTable.length; i++) {
            hashTable[i] = null;
        }
        cantidadElementos = 0;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected boolean redimensionar() {
        TNodoHash<K, V>[] tablaVieja = hashTable;
        hashTable = new TNodoHash[siguientePrimo(tablaVieja.length * 2)];
        cantidadElementos = 0;

        for (int i = 0; i < tablaVieja.length; i++) {
            if (tablaVieja[i] != null) {
                if (!tablaVieja[i].isLoteLibre()) {
                    insertar(tablaVieja[i].getClave(), tablaVieja[i].getValor(), new Report());
                }
            }
        }
        return true;
    }

    @Override
    protected int calcularCapacidadOptima(int elementosEsperados) {
        int minimo = elementosEsperados * 2;
        if (minimo < 11) {
            minimo = 11;
        }
        return siguientePrimo(minimo);
    }

    @Override
    public Iterable<Entry<K, V>> entries() {
        List<Entry<K, V>> lista = new ArrayList<>();
        for (int i = 0; i < hashTable.length; i++) {
            if (hashTable[i] != null) {
                if (!hashTable[i].isLoteLibre()) {
                    lista.add(hashTable[i].getEntry());
                }
            }
        }
        return lista;
    }

    @Override
    public Iterable<K> keys() {
        List<K> lista = new ArrayList<>();
        for (int i = 0; i < hashTable.length; i++) {
            if (hashTable[i] != null) {
                if (!hashTable[i].isLoteLibre()) {
                    lista.add(hashTable[i].getClave());
                }
            }
        }
        return lista;
    }

    @Override
    public Iterable<V> values() {
        List<V> lista = new ArrayList<>();
        for (int i = 0; i < hashTable.length; i++) {
            if (hashTable[i] != null) {
                if (!hashTable[i].isLoteLibre()) {
                    lista.add(hashTable[i].getValor());
                }
            }
        }
        return lista;
    }

    private boolean esPrimo(int n) {
        if (n < 2) {
            return false;
        }
        if (n == 2) {
            return true;
        }
        if (n % 2 == 0) {
            return false;
        }
        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    private int siguientePrimo(int n) {
        int candidato = n;
        while (!esPrimo(candidato)) {
            candidato++;
        }
        return candidato;
    }
}
