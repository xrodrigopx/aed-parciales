package edu.ucu.aed.tda.grafo.impl;

import edu.ucu.aed.tda.grafo.IDirectedIGraph;
import edu.ucu.aed.tda.grafo.model.edge.DirectedEdge;
import edu.ucu.aed.tda.grafo.model.edge.Edge;

import java.util.*;

public class DirectedGraph<V, D> implements IDirectedIGraph<V, D> {
    private final Set<V> vertices;
    private final Set<Edge<V, D>> edges;

    public DirectedGraph() {
        vertices = new HashSet<>();
        edges = new HashSet<>();
    }

    @Override
    public Set<V> successors(Comparable<V> criteria) {
        Set<V> vs = new HashSet<>();
        edges.forEach(edge -> {
            if (criteria.compareTo(edge.source()) == 0) {
                vs.add(edge.target());
            }
        });
        return vs;
    }

    @Override
    public Set<V> predecessors(Comparable<V> criteria) {
        Set<V> vs = new HashSet<>();
        edges.forEach(edge -> {
            if (criteria.compareTo(edge.target()) == 0) {
                vs.add(edge.source());
            }
        });
        return vs;
    }

    @Override
    public boolean agregarVertice(V vertex) {
        return vertices.add(vertex);
    }

    @Override
    public V buscarVertice(Comparable<V> criterio) {
        for (V vertex : vertices) {
            if (criterio.compareTo(vertex) == 0) {
                return vertex;
            }
        }
        return null;
    }

    @Override
    public boolean agregarArista(V source, V target, D dato) {
        boolean added = edges.add(new DirectedEdge<>(source, target, dato));
        if (added) {
            vertices.add(source);
            vertices.add(target);
        }
        return added;
    }

    @Override
    public boolean eliminarArista(Comparable<V> source, Comparable<V> target) {

        List<Edge<V, D>> removeThis = new LinkedList<>();
        edges.stream().filter(x -> source.compareTo(x.source()) == 0 && target.compareTo(x.target()) == 0).forEach(removeThis::add);
        removeThis.forEach(edges::remove);

        return !removeThis.isEmpty();
    }

    @Override
    public boolean removerVertice(Comparable<V> criteria) {
        List<Edge<V, D>> removeThisE = new LinkedList<>();
        List<V> removeThisV = new LinkedList<>();
        edges.stream().filter(x -> criteria.compareTo(x.source()) == 0 || criteria.compareTo(x.target()) == 0).forEach(removeThisE::add);
        vertices.stream().filter(x -> criteria.compareTo(x) == 0).forEach(removeThisV::add);

        removeThisV.forEach(vertices::remove);
        removeThisE.forEach(edges::remove);

        return !vertices.isEmpty() || !edges.isEmpty();
    }

    @Override
    public boolean existeArista(Comparable<V> sourceCriteria, Comparable<V> targetCriteria) {
        return edges.stream().anyMatch(x -> sourceCriteria.compareTo(x.source()) == 0 && targetCriteria.compareTo(x.target()) == 0);
    }

    @Override
    public Set<V> vertices() {
        return Set.copyOf(vertices);
    }

    @Override
    public Set<Edge<V, D>> aristas() {
        return Set.copyOf(edges);
    }

    @Override
    public Edge<V, D> obtenerArista(Comparable<V> sourceCriteria, Comparable<V> targetCriteria) {
        return edges.stream().filter(x -> sourceCriteria.compareTo(x.source()) == 0 && targetCriteria.compareTo(x.target()) == 0).findFirst().orElse(null);
    }

    @Override
    public List<Edge<V, D>> adyacencias(Comparable<V> verticeCriteria) {
        List<Edge<V, D>> ady = new LinkedList<>();
        for (Edge<V, D> edge : edges) {
            if (verticeCriteria.compareTo(edge.source()) == 0) {
                ady.add(edge);
            }
        }
        return ady;
    }

    @Override
    public boolean esConexo() {

        int size = vertices.size();
        if (size == 0) return false;

        GraphAlgorithms algorithms = new GraphAlgorithms();

        for (V vertex : vertices) {

            List<V> nodes = new LinkedList<>();
            algorithms.recorridoEnProfundidad(this, this.construirComparable(vertex), nodes::add);

            if (size != nodes.size()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void vaciar() {
        edges.clear();
        vertices.clear();
    }

    @Override
    public boolean tieneCiclos() {
        for (V vertex : vertices) {
            if (tieneCiclosAux(vertex, new HashSet<>())) {
                return true;
            }
        }
        return false;
    }

    private boolean tieneCiclosAux(V node, Set<V> visitados) {
        if (visitados.contains(node)) return true;
        visitados.add(node);

        List<Edge<V, D>> edgeList = adyacencias(construirComparable(node));

        for (Edge<V, D> edge : edgeList) {
            boolean b = tieneCiclosAux(edge.target(), visitados);
            if (b) return true;
        }

        return false;
    }

}
