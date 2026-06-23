package ucu.edu.aed.tda.grafo.impl;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import ucu.edu.aed.tda.grafo.IUndirectedGraph;
import ucu.edu.aed.tda.grafo.IUndirectedGraphAlgorithm;
import ucu.edu.aed.tda.grafo.model.IGraph;
import ucu.edu.aed.tda.grafo.model.edge.Edge;
import ucu.edu.aed.tda.grafo.model.edge.WeightedEdge;

public class AlgoritmosGrafoNoDirigido implements IUndirectedGraphAlgorithm {

    // bea: recorre el grafo por niveles usando una cola
    @Override
    public <V, D> void bea(IUndirectedGraph<V, D> graph, Consumer<V> consumer) {
        Set<V> visitados = new HashSet<>(); // nodos que ya pasaron por la cola, para no repetirlos
        ArrayDeque<V> cola = new ArrayDeque<>(); // cola fifo, el orden de entrada define el orden de visita

        for (V v : graph.vertices()) { // arrancamos desde cada nodo por si el grafo esta desconectado
            if (!visitados.contains(v)) { // si ya fue visitado en una iteracion anterior lo salteamos
                cola.offer(v); // metemos el nodo inicial en la cola
                visitados.add(v); // lo marcamos ya al entrar, no al salir, para no meterlo dos veces
                while (!cola.isEmpty()) { // procesamos hasta vaciar la cola
                    V actual = cola.poll(); // sacamos el primero que entro
                    consumer.accept(actual); // lo visitamos, hace lo que el llamador definio
                    for (Edge<V, D> arista : graph.adyacencias(graph.construirComparable(actual))) { // recorremos sus vecinos
                        V vecino = arista.target();
                        if (!visitados.contains(vecino)) { // si el vecino todavia no fue visto
                            visitados.add(vecino); // lo marcamos antes de meterlo para evitar duplicados en la cola
                            cola.offer(vecino); // lo encolamos para visitarlo despues
                        }
                    }
                }
            }
        }
    }

    // recorre los nodos que ya estan en el arbol y busca la arista mas barata hacia afuera
    @Override
    public <V, D extends WeightedEdge> Edge<V, D> searchMinEdge(
            IUndirectedGraph<V, D> graph, Collection<V> U, Collection<V> noIncluidos) {
        Edge<V, D> minArista = null;
        double minPeso = Double.MAX_VALUE;

        for (V u : U) {
            for (Edge<V, D> arista : graph.adyacencias(graph.construirComparable(u))) {
                V vecino = arista.target();
                if (noIncluidos.contains(vecino)) {
                    double peso = arista.dato().getWeight();
                    if (peso < minPeso) {
                        minPeso = peso;
                        minArista = arista;
                    }
                }
            }
        }
        return minArista;
    }

    // prim: va creciendo el arbol de a un vertice por vez, siempre el mas barato disponible
    @Override
    public <V, D extends WeightedEdge> IUndirectedGraph<V, D> prim(
            IUndirectedGraph<V, D> graph, Comparable<V> source) {
        V origen = graph.buscarVertice(source);
        GrafoNoDirigido<V, D> arbol = new GrafoNoDirigido<>();

        if (origen == null) {
            return arbol;
        }

        for (V v : graph.vertices()) {
            arbol.agregarVertice(v);
        }

        // U es lo que ya conectamos, noU es lo que falta
        Set<V> U = new HashSet<>();
        Set<V> noU = new HashSet<>();
        for (V v : graph.vertices()) {
            noU.add(v);
        }

        U.add(origen);
        noU.remove(origen);

        while (!noU.isEmpty()) {
            Edge<V, D> minArista = searchMinEdge(graph, U, noU);
            if (minArista == null) { // si no hay arista es porque el grafo esta desconectado
                break;
            }
            V nuevoVertice = minArista.target();
            U.add(nuevoVertice);
            noU.remove(nuevoVertice);
            arbol.agregarArista(minArista.source(), minArista.target(), minArista.dato());
        }

        return arbol;
    }

    // kruskal con sets en lugar de union-find
    @Override
    public <V, D extends WeightedEdge> IUndirectedGraph<V, D> kruskal(IUndirectedGraph<V, D> graph) {
        GrafoNoDirigido<V, D> arbol = new GrafoNoDirigido<>();
        for (V v : graph.vertices()) {
            arbol.agregarVertice(v);
        }

        // copiamos las aristas a una lista para ordenarlas
        List<Edge<V, D>> aristasOrdenadas = new ArrayList<>();
        for (Edge<V, D> arista : graph.aristas()) {
            aristasOrdenadas.add(arista);
        }

        // selection sort por peso
        for (int i = 0; i < aristasOrdenadas.size(); i++) {
            int minIdx = i;
            for (int j = i + 1; j < aristasOrdenadas.size(); j++) {
                if (aristasOrdenadas.get(j).dato().getWeight() < aristasOrdenadas.get(minIdx).dato().getWeight()) {
                    minIdx = j;
                }
            }
            Edge<V, D> temp = aristasOrdenadas.get(i);
            aristasOrdenadas.set(i, aristasOrdenadas.get(minIdx));
            aristasOrdenadas.set(minIdx, temp);
        }

        // cada nodo arranca en su propio grupo
        List<Set<V>> grupos = new ArrayList<>();
        for (V v : graph.vertices()) {
            Set<V> grupo = new HashSet<>();
            grupo.add(v);
            grupos.add(grupo);
        }

        for (Edge<V, D> arista : aristasOrdenadas) {
            V source = arista.source();
            V target = arista.target();

            // buscamos en que grupo esta cada nodo, cortamos cuando los encontramos a los dos
            Set<V> grupoSource = null;
            Set<V> grupoTarget = null;
            for (Set<V> grupo : grupos) {
                if (grupo.contains(source)) grupoSource = grupo;
                if (grupo.contains(target)) grupoTarget = grupo;
                if (grupoSource != null) {
                    if (grupoTarget != null) {
                        break;
                    }
                }
            }

            // si estan en grupos distintos no hay ciclo, aceptamos la arista
            if (grupoSource != grupoTarget) {
                arbol.agregarArista(source, target, arista.dato());
                grupoSource.addAll(grupoTarget); // fusionamos los dos grupos
                grupos.remove(grupoTarget); // el grupo de target ya no existe mas
            }
            // si estan en el mismo grupo formaria ciclo, la ignoramos
        }

        return arbol;
    }

    // devuelve los vertices que si los sacas el grafo se parte en dos o mas pedazos
    public <V, D> List<V> puntosDeArticulacion(IGraph<V, D> grafo) {
        List<V> resultado = new ArrayList<>();
        HashMap<V, Integer> disc = new HashMap<>(); // tiempo en que se descubrio cada nodo
        HashMap<V, Integer> low = new HashMap<>();  // el nodo mas antiguo al que puede llegar cada nodo
        HashMap<V, V> padres = new HashMap<>();     // de donde llegamos a cada nodo en el dfs
        Set<V> visitados = new HashSet<>();
        int[] tiempo = {0}; // array de un elemento para poder modificarlo dentro del recursivo

        for (V v : grafo.vertices()) {
            if (!visitados.contains(v)) {
                dfsArticulacion(grafo, v, disc, low, padres, visitados, tiempo, resultado);
            }
        }
        return resultado;
    }

    private <V, D> void dfsArticulacion(
            IGraph<V, D> grafo, V actual,
            HashMap<V, Integer> disc, HashMap<V, Integer> low,
            HashMap<V, V> padres, Set<V> visitados,
            int[] tiempo, List<V> resultado) {

        visitados.add(actual);
        tiempo[0]++;
        disc.put(actual, tiempo[0]);
        low.put(actual, tiempo[0]);
        int hijosEnArbol = 0;

        for (Edge<V, D> arista : grafo.adyacencias(grafo.construirComparable(actual))) {
            V vecino = arista.target();
            if (!visitados.contains(vecino)) {
                hijosEnArbol++;
                padres.put(vecino, actual);
                dfsArticulacion(grafo, vecino, disc, low, padres, visitados, tiempo, resultado);

                // si el vecino puede llegar mas atras, nosotros tambien
                if (low.get(vecino) < low.get(actual)) {
                    low.put(actual, low.get(vecino));
                }

                // si no es raiz y el vecino no puede escapar por arriba de actual, es punto de articulacion
                boolean esRaiz = !padres.containsKey(actual);
                if (!esRaiz) {
                    if (low.get(vecino) >= disc.get(actual)) {
                        if (!resultado.contains(actual)) {
                            resultado.add(actual);
                        }
                    }
                }
            } else {
                // arista de retroceso: actualizamos low si no es el padre directo
                V padre = padres.get(actual);
                if (!vecino.equals(padre)) {
                    if (disc.get(vecino) < low.get(actual)) {
                        low.put(actual, disc.get(vecino));
                    }
                }
            }
        }

        // la raiz es punto de articulacion solo si tiene mas de un hijo
        // porque si tiene uno solo, sacarla no desconecta nada
        boolean esRaizFinal = !padres.containsKey(actual);
        if (esRaizFinal) {
            if (hijosEnArbol > 1) {
                if (!resultado.contains(actual)) {
                    resultado.add(actual);
                }
            }
        }
    }
}