package ucu.edu.aed.tda.grafo.impl;

import ucu.edu.aed.tda.grafo.IDirectedIGraph;
import ucu.edu.aed.tda.grafo.model.edge.DirectedEdge;
import ucu.edu.aed.tda.grafo.model.edge.Edge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Implementación de un Grafo Dirigido (IDirectedIGraph) utilizando la API de Colecciones de Java.
 * 
 * DISCUSIÓN: HashMap + LinkedList vs Matriz de Adyacencia (Tradeoffs - Ejercicio 1):
 * 
 * 1. HashMap + LinkedList (Listas de Adyacencia):
 *    - Ventajas: Es altamente eficiente en el uso de memoria para grafos dispersos (pocas aristas comparado al máximo posible V^2).
 *      Permite iterar sobre los vecinos de un vértice en tiempo proporcional a su grado O(grado(V)), lo que hace a los 
 *      algoritmos como BFS, DFS y Dijkstra mucho más rápidos en la práctica O(V + E) o O((V + E) log V).
 *    - Desventajas: Saber si existe una arista (u, v) toma tiempo O(grado(u)) en el peor caso, frente a O(1) en la matriz.
 * 
 * 2. Matriz de Adyacencia (V x V):
 *    - Ventajas: La verificación de existencia de aristas (u, v) o acceso a su peso es inmediato O(1).
 *    - Desventajas: Consume mucho espacio O(V^2) independiente de la cantidad de aristas reales. Iterar sobre los
 *      vecinos de un nodo toma siempre O(V), lo que hace ineficientes a los recorridos si el grafo es disperso.
 */
public class GrafoDirigido<V, D> implements IDirectedIGraph<V, D> {

    private HashMap<V, List<Edge<V, D>>> adyacencias;

    public GrafoDirigido() {
        adyacencias = new HashMap<>();
    }

    // Agrega un vértice nuevo al grafo
    @Override
    public boolean agregarVertice(V vertex) {
        if (adyacencias.containsKey(vertex)) {
            return false; // Ya existe, no hacemos nada
        }
        adyacencias.put(vertex, new ArrayList<>());
        return true;
    }

    // Busca un vértice usando un criterio (como el nombre)
    @Override
    public V buscarVertice(Comparable<V> criterio) {
        for (V v : adyacencias.keySet()) {
            if (criterio.compareTo(v) == 0) {
                return v;
            }
        }
        return null; // No lo encontramos
    }

    // Agrega una flecha (arista) de un nodo a otro con un peso/dato
    @Override
    public boolean agregarArista(V source, V target, D dato) {
        if (!adyacencias.containsKey(source)) {
            return false;
        }
        if (!adyacencias.containsKey(target)) {
            return false;
        }
        List<Edge<V, D>> lista = adyacencias.get(source);
        for (Edge<V, D> arista : lista) {
            if (arista.target().equals(target)) {
                return false;
            }
        }
        lista.add(new DirectedEdge<>(source, target, dato));
        return true;
    }

    // Borra la conexión entre dos nodos
    @Override
    public boolean eliminarArista(Comparable<V> source, Comparable<V> target) {
        V verticeSource = buscarVertice(source);
        if (verticeSource == null) {
            return false;
        }
        List<Edge<V, D>> lista = adyacencias.get(verticeSource);
        Edge<V, D> aRemover = null;
        for (Edge<V, D> arista : lista) {
            if (target.compareTo(arista.target()) == 0) {
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

    // Elimina un vértice y todas las flechas que lo apuntan o salen de él
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

    // Devuelve todos los nodos del grafo
    @Override
    public Set<V> vertices() {
        return adyacencias.keySet();
    }

    // Devuelve todas las conexiones (aristas) del grafo
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

    // Revisa si hay un camino directo de un origen a un destino
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

    // Consigue la flecha exacta que une al origen con el destino
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

    // Devuelve todas las flechas que salen de un vértice
    @Override
    public List<Edge<V, D>> adyacencias(Comparable<V> verticeCriteria) {
        V vertice = buscarVertice(verticeCriteria);
        if (vertice == null) {
            return new ArrayList<>();
        }
        return adyacencias.get(vertice);
    }

    // Nodos a los que puedo llegar desde este nodo
    @Override
    public Set<V> successors(Comparable<V> criteria) {
        Set<V> resultado = new HashSet<>();
        List<Edge<V, D>> lista = adyacencias(criteria);
        for (Edge<V, D> arista : lista) {
            resultado.add(arista.target());
        }
        return resultado;
    }

    // Nodos que apuntan hacia este nodo
    @Override
    public Set<V> predecessors(Comparable<V> criteria) {
        V verticeDestino = buscarVertice(criteria);
        Set<V> resultado = new HashSet<>();
        if (verticeDestino == null) {
            return resultado;
        }
        for (V v : adyacencias.keySet()) {
            List<Edge<V, D>> lista = adyacencias.get(v);
            for (Edge<V, D> arista : lista) {
                if (arista.target().equals(verticeDestino)) {
                    resultado.add(v);
                    break;
                }
            }
        }
        return resultado;
    }

    // Verifica si todos los nodos están conectados entre sí
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

    // Metodo de ayuda para recorrer y ver si todo esta conectado
    private void dfsConexo(V actual, Set<V> visitados) {
        visitados.add(actual);
        for (Edge<V, D> arista : adyacencias.get(actual)) {
            if (!visitados.contains(arista.target())) {
                dfsConexo(arista.target(), visitados);
            }
        }
    }

    // Borra todo el contenido del grafo
    @Override
    public void vaciar() {
        adyacencias = new HashMap<>();
    }

    // Averigua si el grafo tiene un camino que da vueltas en círculo
    @Override
    public boolean tieneCiclos() {
        Set<V> enPila = new HashSet<>();
        Set<V> terminado = new HashSet<>();

        for (V v : adyacencias.keySet()) {
            if (!terminado.contains(v)) {
                if (dfsCiclo(v, enPila, terminado)) {
                    return true;
                }
            }
        }
        return false;
    }

    // Función auxiliar recursiva para buscar ciclos
    private boolean dfsCiclo(V actual, Set<V> enPila, Set<V> terminado) {
        enPila.add(actual);
        List<Edge<V, D>> lista = adyacencias.get(actual);
        if (lista != null) {
            for (Edge<V, D> arista : lista) {
                V vecino = arista.target();
                if (enPila.contains(vecino)) {
                    return true;
                }
                if (!terminado.contains(vecino)) {
                    if (dfsCiclo(vecino, enPila, terminado)) {
                        return true;
                    }
                }
            }
        }
        enPila.remove(actual);
        terminado.add(actual);
        return false;
    }
}
