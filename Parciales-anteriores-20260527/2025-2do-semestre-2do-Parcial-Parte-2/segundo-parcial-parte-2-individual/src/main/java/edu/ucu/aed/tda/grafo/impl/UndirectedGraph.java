package edu.ucu.aed.tda.grafo.impl;

import edu.ucu.aed.tda.grafo.IUndirectedGraph;
import edu.ucu.aed.tda.grafo.model.edge.Edge;
import edu.ucu.aed.tda.grafo.model.edge.UndirectedEdge;

import java.util.*;

public class UndirectedGraph<V, D> implements IUndirectedGraph<V, D> {
    private final Set<V> vertices;
    private final Set<Edge<V, D>> aristas;

    public UndirectedGraph() {
        vertices = new HashSet<>();
        aristas = new HashSet<>();
    }

    public UndirectedGraph(Collection<V> vertices, Collection<Edge<V, D>> aristas) {
        this();
        this.vertices.addAll(vertices);
        this.aristas.addAll(aristas);
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
        boolean added = aristas.add(new UndirectedEdge<>(source, target, dato));
        if (added) {
            vertices.add(source);
            vertices.add(target);
        }
        return added;
    }

    @Override
    public boolean eliminarArista(Comparable<V> source, Comparable<V> target) {
        for (Edge<V, D> arista : aristas) {
            V src = arista.source();
            V trg = arista.target();

            boolean cond = (source.compareTo(src) == 0 && target.compareTo(trg) == 0) || (source.compareTo(trg) == 0 && target.compareTo(src) == 0);
            if (cond) {
                return aristas.remove(arista);
            }
        }
        return false;
    }

    @Override
    public boolean removerVertice(Comparable<V> criteria) {
        Set<V> verticesToRemove = new HashSet<>();
        Set<Edge<V, D>> aristaToRemove = new HashSet<>();

        for (V vertex : vertices) {
            if (criteria.compareTo(vertex) == 0) {
                verticesToRemove.add(vertex);
            }
        }

        for (Edge<V, D> arista : aristas) {
            V src = arista.source();
            V trg = arista.target();
            boolean cond = verticesToRemove.contains(src) || verticesToRemove.contains(trg);
            if (cond) {
                aristaToRemove.add(arista);
            }
        }

        boolean b1 = vertices.removeAll(verticesToRemove);
        boolean b2 = aristas.removeAll(aristaToRemove);
//        System.out.println(b1);
//        System.out.println(b2);
        return b1 || b2;
    }

    @Override
    public Set<V> vertices() {
        return Set.copyOf(vertices);
    }

    @Override
    public Set<Edge<V, D>> aristas() {
        return Set.copyOf(aristas);
    }

    @Override
    public boolean existeArista(Comparable<V> source, Comparable<V> target) {
        for (Edge<V, D> arista : aristas) {
            V src = arista.source();
            V trg = arista.target();

            boolean cond = (source.compareTo(src) == 0 && target.compareTo(trg) == 0) || (source.compareTo(trg) == 0 && target.compareTo(src) == 0);
            if (cond) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Edge<V, D> obtenerArista(Comparable<V> source, Comparable<V> target) {
        for (Edge<V, D> arista : aristas) {
            V src = arista.source();
            V trg = arista.target();

            boolean cond = (source.compareTo(src) == 0 && target.compareTo(trg) == 0) || (source.compareTo(trg) == 0 && target.compareTo(src) == 0);
            if (cond) {
                return arista;
            }
        }

        return null;
    }

    @Override
    public List<Edge<V, D>> adyacencias(Comparable<V> verticeCriteria) {
        List<Edge<V, D>> adjacencias = new LinkedList<>();
        for (Edge<V, D> arista : aristas) {
            V src = arista.source();
            V trg = arista.target();
            if (verticeCriteria.compareTo(src) == 0 || verticeCriteria.compareTo(trg) == 0) {
                if (verticeCriteria.compareTo(src) == 0) {
                    adjacencias.add(new UndirectedEdge<>(src, trg, arista.dato()));
                } else {
                    adjacencias.add(new UndirectedEdge<>(trg, src, arista.dato()));
                }
            }
        }
        return adjacencias;
    }

    @Override
    public boolean esConexo() {

        int size = vertices.size();
        if (size == 0) return false;

        Set<V> visitados = new HashSet<>();

        for (V vertex : vertices) {

            esConexoAux(vertex, visitados);
            // con un llamado deberíamos visitar a todos los vértices
            if (size != visitados.size()) {
                return false;
            }
        }

        return true;
    }

    private void esConexoAux(V vertice, Set<V> visitados) {
        if (visitados.contains(vertice)) return;

        visitados.add(vertice);

        for (Edge<V, D> arista : adyacencias(construirComparable(vertice))) {
            esConexoAux(arista.target(), visitados);
        }
    }

    @Override
    public void vaciar() {
        vertices.clear();
        aristas.clear();
    }

    @Override
    public boolean tieneCiclos() {
        for (V vertex : vertices) {
            if (tieneCiclosAux(vertex, new HashSet<>(), null)) {
                return true;
            }
        }
        return false;
    }


    private boolean tieneCiclosAux(V node, Set<V> visitados, V padre) {
        visitados.add(node);

        List<Edge<V, D>> edgeList = adyacencias(construirComparable(node));

        for (Edge<V, D> edge : edgeList) {
            V target = edge.target();

            if (!visitados.contains(target)) {
                boolean b = tieneCiclosAux(target, visitados, node);
                if (b) return true;
            } else if (padre != null && edge.target() != padre) {
                return true;
            }
        }

        return false;
    }
}
