package ucu.edu.aed.tda.grafo.impl;

import ucu.edu.aed.tda.grafo.IUndirectedGraph;
import ucu.edu.aed.tda.grafo.model.edge.Edge;
import ucu.edu.aed.tda.grafo.model.edge.UndirectedEdge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GrafoNoDirigido<V, D> implements IUndirectedGraph<V, D> {

    private HashMap<V, List<Edge<V, D>>> adyacencias;

    public GrafoNoDirigido() {
        adyacencias = new HashMap<>();
    }

    // Agrega un vértice al mapa de adyacencias
    @Override
    public boolean agregarVertice(V vertex) {
        if (adyacencias.containsKey(vertex)) {
            return false;
        }
        adyacencias.put(vertex, new ArrayList<>());
        return true;
    }

    // Busca si tenemos guardado a ese nodo
    @Override
    public V buscarVertice(Comparable<V> criterio) {
        for (V v : adyacencias.keySet()) {
            if (criterio.compareTo(v) == 0) {
                return v;
            }
        }
        return null;
    }

    // Conecta dos nodos creando una línea entre ellos. Como no tiene dirección, va a los dos lados.
    @Override
    public boolean agregarArista(V source, V target, D dato) {
        if (!adyacencias.containsKey(source)) {
            return false;
        }
        if (!adyacencias.containsKey(target)) {
            return false;
        }
        List<Edge<V, D>> listaSource = adyacencias.get(source);
        for (Edge<V, D> arista : listaSource) {
            if (arista.target().equals(target)) {
                return false;
            }
        }
        // Guardar en ambas listas; el criterio aparece como source() en cada una
        listaSource.add(new UndirectedEdge<>(source, target, dato));
        adyacencias.get(target).add(new UndirectedEdge<>(target, source, dato));
        return true;
    }

    // Corta la conexión entre dos nodos, de ida y de vuelta
    @Override
    public boolean eliminarArista(Comparable<V> source, Comparable<V> target) {
        V verticeSource = buscarVertice(source);
        V verticeTarget = buscarVertice(target);
        if (verticeSource == null || verticeTarget == null) {
            return false;
        }

        boolean removidoDeSource = removerDeLista(adyacencias.get(verticeSource), target);
        boolean removidoDeTarget = removerDeLista(adyacencias.get(verticeTarget), source);

        return removidoDeSource || removidoDeTarget;
    }

    private boolean removerDeLista(List<Edge<V, D>> lista, Comparable<V> criterioBusqueda) {
        Edge<V, D> aRemover = null;
        for (Edge<V, D> arista : lista) {
            if (criterioBusqueda.compareTo(arista.target()) == 0) {
                aRemover = arista;
                break;
            }
        }
        if (aRemover == null) {
            return false;
        }
        lista.remove(aRemover);
        return true;
    }

    // Quita un nodo entero y todas las líneas que lo conectaban
    @Override
    public boolean removerVertice(Comparable<V> criteria) {
        V vertice = buscarVertice(criteria);
        if (vertice == null) {
            return false;
        }
        adyacencias.remove(vertice);
        for (List<Edge<V, D>> lista : adyacencias.values()) {
            List<Edge<V, D>> aRemover = new ArrayList<>();
            for (Edge<V, D> arista : lista) {
                if (arista.target().equals(vertice)) {
                    aRemover.add(arista);
                }
            }
            lista.removeAll(aRemover);
        }
        return true;
    }

    // Devuelve la lista de nodos
    @Override
    public Set<V> vertices() {
        return adyacencias.keySet();
    }

    // Devuelve todas las líneas del grafo
    @Override
    public Set<Edge<V, D>> aristas() {
        Set<Edge<V, D>> resultado = new HashSet<>();
        for (List<Edge<V, D>> lista : adyacencias.values()) {
            for (Edge<V, D> arista : lista) {
                resultado.add(arista);
            }
        }
        return resultado;
    }

    // Se fija si los dos nodos están unidos
    @Override
    public boolean existeArista(Comparable<V> sourceCriteria, Comparable<V> targetCriteria) {
        V verticeSource = buscarVertice(sourceCriteria);
        if (verticeSource == null) {
            return false;
        }
        List<Edge<V, D>> lista = adyacencias.get(verticeSource);
        for (Edge<V, D> arista : lista) {
            if (targetCriteria.compareTo(arista.target()) == 0) {
                return true;
            }
        }
        return false;
    }

    // Consigue la línea exacta que une a los dos nodos
    @Override
    public Edge<V, D> obtenerArista(Comparable<V> sourceCriteria, Comparable<V> targetCriteria) {
        V verticeSource = buscarVertice(sourceCriteria);
        if (verticeSource == null) {
            return null;
        }
        List<Edge<V, D>> lista = adyacencias.get(verticeSource);
        for (Edge<V, D> arista : lista) {
            if (targetCriteria.compareTo(arista.target()) == 0) {
                return arista;
            }
        }
        return null;
    }

    // Las líneas que tocan al nodo
    @Override
    public List<Edge<V, D>> adyacencias(Comparable<V> verticeCriteria) {
        V vertice = buscarVertice(verticeCriteria);
        if (vertice == null) {
            return new ArrayList<>();
        }
        return adyacencias.get(vertice);
    }

    // Revisa si todo el grafo es una sola pieza (conexo)
    @Override
    public boolean esConexo() {
        if (adyacencias.isEmpty()) {
            return true;
        }
        V primero = null;
        for (V v : adyacencias.keySet()) {
            primero = v;
            break;
        }
        Set<V> visitados = new HashSet<>();
        dfsConexo(primero, visitados);
        return visitados.size() == adyacencias.size();
    }

    private void dfsConexo(V actual, Set<V> visitados) {
        visitados.add(actual);
        for (Edge<V, D> arista : adyacencias.get(actual)) {
            if (!visitados.contains(arista.target())) {
                dfsConexo(arista.target(), visitados);
            }
        }
    }

    // Reinicia el grafo a cero
    @Override
    public void vaciar() {
        adyacencias = new HashMap<>();
    }

    // Revisa si hay bucles o caminos que se cierran
    @Override
    public boolean tieneCiclos() {
        Set<V> visitados = new HashSet<>();
        for (V v : adyacencias.keySet()) {
            if (!visitados.contains(v)) {
                if (dfsCiclo(v, null, visitados)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean dfsCiclo(V actual, V padre, Set<V> visitados) {
        visitados.add(actual);
        for (Edge<V, D> arista : adyacencias.get(actual)) {
            V vecino = arista.target();
            if (!visitados.contains(vecino)) {
                if (dfsCiclo(vecino, actual, visitados)) {
                    return true;
                }
            } else if (!vecino.equals(padre)) {
                return true;
            }
        }
        return false;
    }
}
