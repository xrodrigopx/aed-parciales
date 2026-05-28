package edu.ucu.aed.tda.grafo.impl;

import edu.ucu.aed.tda.grafo.IGraphAlgorithms;
import edu.ucu.aed.tda.grafo.IDirectedIGraph;
import edu.ucu.aed.tda.grafo.IUndirectedGraph;
import edu.ucu.aed.tda.grafo.model.IGraph;
import edu.ucu.aed.tda.grafo.model.edge.Edge;
import edu.ucu.aed.tda.grafo.model.edge.WeightedEdge;
import edu.ucu.aed.tda.grafo.model.result.Path;

import java.util.*;
import java.util.function.Consumer;

public class GraphAlgorithms implements IGraphAlgorithms {
    @Override
    public <V, D extends WeightedEdge> DijkstraResult<V> dijkstra(Comparable<V> source, IDirectedIGraph<V, D> grafo) {
        V src = grafo.buscarVertice(source);

        if (src == null) return null;

        Map<V, Double> D = new HashMap<>();
        Map<V, V> P = new HashMap<>();
        Set<V> V = grafo.vertices();
        Set<V> S = new HashSet<>(V);

        S.remove(src);

        for (V v : V) {
            Edge<V, D> e = grafo.obtenerArista(src, v);
            if (e == null) {
                D.put(v, Double.POSITIVE_INFINITY);
            } else {
                double peso = e.dato().getWeight();
                if (peso < 0) {
                    throw new IllegalArgumentException();
                }
                D.put(v, peso);
            }

            P.put(v, src);
        }

        P.put(src, null);
        D.put(src, 0d);

        while (!S.isEmpty()) {
            V w = null;
            double cost = Double.POSITIVE_INFINITY;
            for (V v : S) {
                double localCost = D.getOrDefault(v, Double.POSITIVE_INFINITY);
                if (localCost < cost) {
                    w = v;
                    cost = localCost;
                }
            }

            if (w != null) {
                S.remove(w);

                for (V v : S) {
                    Edge<V, D> arista = grafo.obtenerArista(w, v);
                    if (arista != null) {
                        double d = cost + arista.dato().getWeight();
                        if (d < D.getOrDefault(v, Double.POSITIVE_INFINITY)) {
                            D.put(v, d);
                            P.put(v, w);
                        }
                    }
                }
            }
        }

        return new DijkstraResult<>(src, D, P);
    }

    @Override
    public <V, D extends WeightedEdge> FloydWarshallResult<V> floyd(IDirectedIGraph<V, D> grafo) {
        Set<V> V = grafo.vertices();
        Map<V, Map<V, Double>> A = new HashMap<>();
        Map<V, Map<V, V>> P = new HashMap<>();

        for (V i : V) {
            A.putIfAbsent(i, new HashMap<>());
            P.putIfAbsent(i, new HashMap<>());
            A.get(i).put(i, 0d);

            for (V j : V) {
                Edge<V, D> e = grafo.obtenerArista(i, j);
                if (i == j) {
                    A.get(i).put(j, 0d);
                } else if (e != null) {
                    A.get(i).put(j, e.dato().getWeight());
                    P.get(i).put(j, j);
                } else {
                    A.get(i).put(j, Double.POSITIVE_INFINITY);
                }
            }
        }

        for (V k : V) {
            for (V i : V) {
                for (V j : V) {
                    double c = A.get(i).getOrDefault(k, Double.POSITIVE_INFINITY) + A.get(k).getOrDefault(j, Double.POSITIVE_INFINITY);
                    if (c < A.get(i).getOrDefault(j, Double.POSITIVE_INFINITY)) {
                        A.get(i).put(j, c);
                        P.get(i).put(j, k);
                    }
                }
            }
        }

        return new FloydWarshallResult<>(A, P);
    }

    @Override
    public <V, D extends WeightedEdge> FloydWarshallResult<V> warshall(IDirectedIGraph<V, D> grafo) {
        Set<V> V = grafo.vertices();
        Map<V, Map<V, Double>> A = new HashMap<>();
        Map<V, Map<V, V>> P = new HashMap<>();

        for (V i : V) {
            A.putIfAbsent(i, new HashMap<>());
            P.putIfAbsent(i, new HashMap<>());

            for (V j : V) {
                Edge<V, D> e = grafo.obtenerArista(i, j);
                if (i == j) {
                    A.get(i).put(j, 1d);
                } else {
                    A.get(i).put(j, e == null ? 0d : 1d);
                }
            }
        }

        for (V k : V) {
            for (V i : V) {
                for (V j : V) {
                    if (A.get(i).get(j) == 0) {
                        boolean hasPath = A.get(i).get(k) == 1 && A.get(k).get(j) == 1;
                        A.get(i).put(j, hasPath ? 1d : 0d);
                        if (hasPath) {
                            P.get(i).put(j, k);
                        }
                    }
                }
            }
        }

        return new FloydWarshallResult<>(A, P);
    }

    @Override
    public <V, D extends WeightedEdge> V obtenerCentroGrafo(IDirectedIGraph<V, D> grafo) {
        V node = null;
        double x = Double.POSITIVE_INFINITY;
        for (V vertex : grafo.vertices()) {
            double v = obtenerExcentricidad(grafo, grafo.construirComparable(vertex));
            if (v < x) {
                node = vertex;
                x = v;
            }
        }
        return node;
    }

    @Override
    public <V, D extends WeightedEdge> double obtenerExcentricidad(IDirectedIGraph<V, D> grafo, Comparable<V> vertexCriteria) {


        V v = grafo.buscarVertice(vertexCriteria);
        if (v == null) {
            return Double.POSITIVE_INFINITY;
        }

        double max = 0;
        FloydWarshallResult<V> f = floyd(grafo);

        for (V vertex : grafo.vertices()) {
            double cost = f.getCost(vertex, v);
            if (cost > max && !Double.isInfinite(cost)) {
                max = cost;
            }
        }

        return max;
    }

    @Override
    public <V, D> void recorridoEnProfundidad(IGraph<V, D> grafo, Comparable<V> sourceCriteria, Consumer<V> consumer) {
        V v = grafo.buscarVertice(sourceCriteria);
        if (v == null) return;

        recorridoEnProfundidadAux(new HashSet<>(), v, grafo, consumer);
    }

    private <V, D> void recorridoEnProfundidadAux(Set<V> visitados, V node, IGraph<V, D> grafo, Consumer<V> consumer) {
        consumer.accept(node);
        visitados.add(node);

        List<Edge<V, D>> edges = grafo.adyacencias(grafo.construirComparable(node));

        for (Edge<V, D> edge : edges) {
            V target = edge.target();
            if (!visitados.contains(target)) {
                recorridoEnProfundidadAux(visitados, target, grafo, consumer);
            }
        }
    }

    @Override
    public <V, D> void recorridoEnAmplitud(IGraph<V, D> grafo, Comparable<V> sourceCriteria, Consumer<V> consumer) {
        V src = grafo.buscarVertice(sourceCriteria);

        if (src == null) return;

        Queue<V> queue = new LinkedList<>();
        queue.add(src);
        Set<V> visitados = new HashSet<>();
        while (!queue.isEmpty()) {
            V v = queue.poll();
            if (!visitados.contains(v)) {
                consumer.accept(v);
                visitados.add(v);
                grafo.adyacencias(grafo.construirComparable(v)).forEach(x -> {
                    V target = x.target();
                    queue.add(target);

                });
            }

        }
    }


    @Override
    public <V, D extends WeightedEdge> List<Path<V>> obtenerTodosLosCaminos(Comparable<V> source, Comparable<V> target, IGraph<V, D> grafo) {

        List<Path<V>> paths = new LinkedList<>();
        V src = grafo.buscarVertice(source);

        if (src != null) {

            Stack<V> stack = new Stack<>();
            Set<V> visitados = new HashSet<>();
            todosLosCaminos(stack, src, target, visitados, paths, 0, grafo);
        }

        return paths;
    }


    private <V, D extends WeightedEdge> void todosLosCaminos(
            Stack<V> stack,
            V node,
            Comparable<V> targetCriteria,
            Set<V> visitados,
            List<Path<V>> caminos,
            double cost,
            IGraph<V, D> grafo
    ) {
        if (visitados.contains(node)) {
            return;
        }

        stack.push(node);
        visitados.add(node);

        if (targetCriteria.compareTo(node) == 0) {
            caminos.add(new Path<>(List.copyOf(stack), cost));
        } else {
            for (Edge<V, D> adyacencia : grafo.adyacencias(grafo.construirComparable(node))) {
                todosLosCaminos(stack, adyacencia.target(), targetCriteria, visitados, caminos, cost + adyacencia.dato().getWeight(), grafo);
            }
        }

        stack.pop();
        visitados.remove(node);
    }


    @Override
    public <V, D> List<V> calcularClasificacionTopologica(IDirectedIGraph<V, D> grafo) {
        return List.of();
    }


    @Override
    public <V, D extends WeightedEdge> IUndirectedGraph<V, D> kruskal(IUndirectedGraph<V, D> graph) {
        return null;
    }

    @Override
    public <V, D extends WeightedEdge> IUndirectedGraph<V, D> prim(IUndirectedGraph<V, D> graph, Comparable<V> source) {
        return null;
    }

    @Override
    public <V, D extends WeightedEdge> Edge<V, D> searchMinEdge(IUndirectedGraph<V, D> graph, Collection<V> U, Collection<V> V) {
        return null;
    }

    @Override
    public <V, D> void bea(IUndirectedGraph<V, D> graph, Consumer<V> consumer) {

    }
}
