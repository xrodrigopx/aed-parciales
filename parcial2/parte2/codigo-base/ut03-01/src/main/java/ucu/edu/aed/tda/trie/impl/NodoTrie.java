package ucu.edu.aed.tda.trie.impl;

import ucu.edu.aed.tda.trie.Entry;
import ucu.edu.aed.tda.trie.TNodoTrie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class NodoTrie<T> implements TNodoTrie<T> {

    private Map<Character, NodoTrie<T>> hijos;
    private boolean esPalabra;
    private T dato;

    public NodoTrie() {
        this.hijos = new HashMap<>();
        this.esPalabra = false;
        this.dato = null;
    }

    @Override
    public void recorrer(Consumer<Entry<T>> consumer) {
        recorrer("", consumer);
    }

    private void recorrer(String prefijo, Consumer<Entry<T>> consumer) {
        if (this.esPalabra) {
            consumer.accept(new Entry<>(this.dato, this.esPalabra, prefijo));
        }
        for (Map.Entry<Character, NodoTrie<T>> entry : hijos.entrySet()) {
            entry.getValue().recorrer(prefijo + entry.getKey(), consumer);
        }
    }

    @Override
    public Entry<T> buscar(String palabra) {
        if (palabra == null) return null;
        
        NodoTrie<T> nodo = this;
        for (int i = 0; i < palabra.length(); i++) {
            char c = palabra.charAt(i);
            if (!nodo.hijos.containsKey(c)) {
                return null; // No se encuentra en el Trie
            }
            nodo = nodo.hijos.get(c);
        }
        return new Entry<>(nodo.getDato(), nodo.esPalabra(), palabra);
    }

    @Override
    public boolean insertar(String palabra, T dato) {
        if (palabra == null) return false;
        
        NodoTrie<T> nodo = this;
        for (int i = 0; i < palabra.length(); i++) {
            char c = palabra.charAt(i);
            nodo.hijos.putIfAbsent(c, new NodoTrie<>());
            nodo = nodo.hijos.get(c);
        }
        boolean insertado = !nodo.esPalabra;
        nodo.esPalabra = true;
        nodo.dato = dato;
        return insertado;
    }

    @Override
    public List<Entry<T>> predecir(String prefijo) {
        List<Entry<T>> resultado = new ArrayList<>();
        if (prefijo == null) return resultado;
        
        NodoTrie<T> nodo = this;
        for (int i = 0; i < prefijo.length(); i++) {
            char c = prefijo.charAt(i);
            if (!nodo.hijos.containsKey(c)) {
                return resultado; // Lista vacía, no hay predicciones
            }
            nodo = nodo.hijos.get(c);
        }
        nodo.recorrer(prefijo, resultado::add);
        return resultado;
    }

    @Override
    public T getDato() {
        return dato;
    }

    @Override
    public boolean esPalabra() {
        return esPalabra;
    }

    @Override
    public boolean eliminar(String palabra) {
        if (palabra == null) {
            return false;
        }
        NodoTrie<T> nodo = this;
        for (int i = 0; i < palabra.length(); i++) {
            char c = palabra.charAt(i);
            if (!nodo.hijos.containsKey(c)) {
                return false;
            }
            nodo = nodo.hijos.get(c);
        }
        if (!nodo.esPalabra) {
            return false;
        }
        nodo.esPalabra = false;
        nodo.dato = null;
        return true;
    }
}
