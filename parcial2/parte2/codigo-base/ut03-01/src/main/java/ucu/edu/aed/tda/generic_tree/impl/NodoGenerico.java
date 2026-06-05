package ucu.edu.aed.tda.generic_tree.impl;

import ucu.edu.aed.tda.generic_tree.TNodoGenerico;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class NodoGenerico<T extends Comparable<T>> implements TNodoGenerico<T> {

    private T dato;
    private List<NodoGenerico<T>> hijos;

    public NodoGenerico(T dato) {
        this.dato = dato;
        this.hijos = new ArrayList<>();
    }

    @Override
    public T getDato() {
        return dato;
    }

    @Override
    public boolean agregarHijo(T padre, T hijo) {
        if (padre == null) {
            return false;
        }
        if (hijo == null) {
            return false;
        }
        if (this.dato.compareTo(padre) == 0) {
            for (NodoGenerico<T> h : hijos) {
                if (h.dato.compareTo(hijo) == 0) {
                    return false;
                }
            }
            hijos.add(new NodoGenerico<>(hijo));
            return true;
        }
        for (NodoGenerico<T> h : hijos) {
            if (h.agregarHijo(padre, hijo)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public TNodoGenerico<T> eliminar(Comparable<T> criterio) {
        if (criterio == null) {
            return this;
        }
        int i = 0;
        while (i < hijos.size()) {
            if (criterio.compareTo(hijos.get(i).dato) == 0) {
                hijos.remove(i);
                return this;
            }
            i++;
        }
        for (NodoGenerico<T> hijo : hijos) {
            hijo.eliminar(criterio);
        }
        return this;
    }

    @Override
    public TNodoGenerico<T> buscar(Comparable<T> criterio) {
        if (criterio == null) {
            return null;
        }
        if (criterio.compareTo(this.dato) == 0) {
            return this;
        }
        for (NodoGenerico<T> hijo : hijos) {
            TNodoGenerico<T> encontrado = hijo.buscar(criterio);
            if (encontrado != null) {
                return encontrado;
            }
        }
        return null;
    }

    @Override
    public TNodoGenerico<T> obtenerPadre(Comparable<T> criterio) {
        if (criterio == null) {
            return null;
        }
        for (NodoGenerico<T> hijo : hijos) {
            if (criterio.compareTo(hijo.dato) == 0) {
                return this;
            }
        }
        for (NodoGenerico<T> hijo : hijos) {
            TNodoGenerico<T> resultado = hijo.obtenerPadre(criterio);
            if (resultado != null) {
                return resultado;
            }
        }
        return null;
    }

    @Override
    public void preOrden(Consumer<TNodoGenerico<T>> consumidor) {
        consumidor.accept(this);
        for (NodoGenerico<T> hijo : hijos) {
            hijo.preOrden(consumidor);
        }
    }

    // En árbol genérico: primer hijo, luego nodo, luego el resto de hijos.
    @Override
    public void inOrden(Consumer<TNodoGenerico<T>> consumidor) {
        if (hijos.isEmpty()) {
            consumidor.accept(this);
            return;
        }
        hijos.get(0).inOrden(consumidor);
        consumidor.accept(this);
        for (int i = 1; i < hijos.size(); i++) {
            hijos.get(i).inOrden(consumidor);
        }
    }

    @Override
    public void postOrden(Consumer<TNodoGenerico<T>> consumidor) {
        for (NodoGenerico<T> hijo : hijos) {
            hijo.postOrden(consumidor);
        }
        consumidor.accept(this);
    }

    @Override
    public int altura() {
        if (hijos.isEmpty()) {
            return 0;
        }
        int max = 0;
        for (NodoGenerico<T> hijo : hijos) {
            int h = hijo.altura();
            if (h > max) {
                max = h;
            }
        }
        return max + 1;
    }

    @Override
    public int grado() {
        return hijos.size();
    }

    @Override
    public void vaciar() {
        hijos.clear();
    }

    @Override
    public List<T> obtenerHijos() {
        List<T> resultado = new ArrayList<>();
        for (NodoGenerico<T> hijo : hijos) {
            resultado.add(hijo.dato);
        }
        return resultado;
    }
}
