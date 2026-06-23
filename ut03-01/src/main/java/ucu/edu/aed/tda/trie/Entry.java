package ucu.edu.aed.tda.trie;

public class Entry<T> {
    private final T dato;
    private final String palabra;
    private final boolean esPalabra;

    public Entry(T dato, boolean esPalabra, String palabra) {
        this.dato = dato;
        this.esPalabra = esPalabra;
        this.palabra = palabra;
    }

    public String getPalabra() {
        return palabra;
    }

    public T getDato() {
        return dato;
    }

    public boolean esPalabra() {
        return esPalabra;
    }
}
