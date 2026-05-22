package ucu.edu.aed.tda.generic_tree.impl;

import ucu.edu.aed.tda.generic_tree.TArbolGenerico;
import ucu.edu.aed.tda.generic_tree.TNodoGenerico;

import java.util.function.Consumer;

public class ArbolGenerico<T extends Comparable<T>> implements TArbolGenerico<T> {

    private NodoGenerico<T> raiz;

    public ArbolGenerico(T raiz) {
        this.raiz = new NodoGenerico<>(raiz);
    }

    @Override
    public boolean agregarHijo(Comparable<T> padre, T hijo) {
        if (raiz == null) {
            return false;
        }
        if (padre == null) {
            return false;
        }
        if (hijo == null) {
            return false;
        }
        TNodoGenerico<T> nodoPadre = raiz.buscar(padre);
        if (nodoPadre == null) {
            return false;
        }
        return nodoPadre.agregarHijo(nodoPadre.getDato(), hijo);
    }

    @Override
    public void eliminar(Comparable<T> criterio) {
        if (raiz == null) {
            return;
        }
        if (criterio == null) {
            return;
        }
        if (criterio.compareTo(raiz.getDato()) == 0) {
            raiz = null;
            return;
        }
        raiz.eliminar(criterio);
    }

    @Override
    public T obtenerPadre(Comparable<T> criterio) {
        if (raiz == null) {
            return null;
        }
        if (criterio == null) {
            return null;
        }
        if (criterio.compareTo(raiz.getDato()) == 0) {
            return null;
        }
        TNodoGenerico<T> nodo = raiz.obtenerPadre(criterio);
        if (nodo == null) {
            return null;
        }
        return nodo.getDato();
    }

    @Override
    public T buscar(Comparable<T> criterio) {
        if (raiz == null) {
            return null;
        }
        if (criterio == null) {
            return null;
        }
        TNodoGenerico<T> nodo = raiz.buscar(criterio);
        if (nodo == null) {
            return null;
        }
        return nodo.getDato();
    }

    @Override
    public void preOrden(final Consumer<T> consumidor) {
        if (raiz != null) {
            if (consumidor != null) {
                raiz.preOrden(new Consumer<TNodoGenerico<T>>() {
                    public void accept(TNodoGenerico<T> nodo) {
                        consumidor.accept(nodo.getDato());
                    }
                });
            }
        }
    }

    @Override
    public void inOrden(final Consumer<T> consumidor) {
        if (raiz != null) {
            if (consumidor != null) {
                raiz.inOrden(new Consumer<TNodoGenerico<T>>() {
                    public void accept(TNodoGenerico<T> nodo) {
                        consumidor.accept(nodo.getDato());
                    }
                });
            }
        }
    }

    @Override
    public void postOrden(final Consumer<T> consumidor) {
        if (raiz != null) {
            if (consumidor != null) {
                raiz.postOrden(new Consumer<TNodoGenerico<T>>() {
                    public void accept(TNodoGenerico<T> nodo) {
                        consumidor.accept(nodo.getDato());
                    }
                });
            }
        }
    }

    @Override
    public void vaciar() {
        raiz = null;
    }

    @Override
    public int grado(Comparable<T> nodo) {
        if (raiz == null) {
            return 0;
        }
        if (nodo == null) {
            return 0;
        }
        TNodoGenerico<T> encontrado = raiz.buscar(nodo);
        if (encontrado == null) {
            return 0;
        }
        return encontrado.grado();
    }

    @Override
    public int altura(Comparable<T> nodo) {
        if (raiz == null) {
            return -1;
        }
        if (nodo == null) {
            return -1;
        }
        TNodoGenerico<T> encontrado = raiz.buscar(nodo);
        if (encontrado == null) {
            return -1;
        }
        return encontrado.altura();
    }
}
