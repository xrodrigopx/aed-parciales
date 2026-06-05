package ucu.edu.aed.tda.trie.impl;

import ucu.edu.aed.tda.trie.Entry;
import ucu.edu.aed.tda.trie.TNodoTrie;
import ucu.edu.aed.tda.trie.TTrie;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Trie<T> implements TTrie<T> {

    private TNodoTrie<T> raiz;

    public Trie() {
    }

    @Override
    public void recorrer(Consumer<Entry<T>> consumer) {
        if (raiz != null) {
            if (consumer != null) {
                raiz.recorrer(consumer);
            }
        }
    }

    @Override
    public Entry<T> buscar(String palabra) {
        if (raiz == null) {
            return null;
        }
        if (palabra == null) {
            return null;
        }
        return raiz.buscar(palabra);
    }

    @Override
    public boolean insertar(String palabra, T dato) {
        if (palabra == null) {
            return false;
        }
        if (raiz == null) {
            raiz = new NodoTrie<>();
        }
        return raiz.insertar(palabra, dato);
    }

    @Override
    public List<Entry<T>> predecir(String prefijo) {
        if (raiz == null) {
            return new ArrayList<>();
        }
        if (prefijo == null) {
            return new ArrayList<>();
        }
        return raiz.predecir(prefijo);
    }

    @Override
    public boolean eliminar(String palabra) {
        if (raiz == null) {
            return false;
        }
        if (palabra == null) {
            return false;
        }
        return raiz.eliminar(palabra);
    }
}
