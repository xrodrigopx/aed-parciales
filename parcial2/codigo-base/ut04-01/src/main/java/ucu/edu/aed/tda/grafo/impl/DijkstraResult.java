package ucu.edu.aed.tda.grafo.impl;

import ucu.edu.aed.tda.grafo.model.result.IDijkstraResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// Clase que guarda el resultado final de ejecutar Dijkstra
public class DijkstraResult<V> implements IDijkstraResult<V> {

    private final HashMap<V, Double> distancias;
    private final HashMap<V, V> predecesores;

    public DijkstraResult(HashMap<V, Double> distancias, HashMap<V, V> predecesores) {
        this.distancias = distancias;
        this.predecesores = predecesores;
    }

    // Devuelve el costo de ir desde el origen hasta otherVertex
    @Override
    public double getCost(V otherVertex) {
        Double costo = distancias.get(otherVertex);
        if (costo == null) {
            return Double.MAX_VALUE;
        }
        return costo;
    }

    // Reconstruye el camino completo hacia otherVertex
    @Override
    public List<V> getPath(V otherVertex) {
        List<V> camino = new ArrayList<>();
        if (!distancias.containsKey(otherVertex)) {
            return camino;
        }
        if (distancias.get(otherVertex) == Double.MAX_VALUE) {
            return camino;
        }
        V actual = otherVertex;
        while (actual != null) {
            camino.add(0, actual);
            actual = predecesores.get(actual);
        }
        return camino;
    }

    // Se fija si pudimos llegar a este nodo (si la distancia no es infinita)
    public boolean connected(V target) {
        if (!distancias.containsKey(target)) {
            return false;
        }
        return distancias.get(target) < Double.MAX_VALUE;
    }
}
