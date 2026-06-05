package edu.ucu.aed.tda.grafo.impl;

import edu.ucu.aed.tda.grafo.model.result.IDijkstraResult;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DijkstraResult<V> implements IDijkstraResult<V> {
    private final V source;
    private final Map<V, Double> distancias;
    private final Map<V, V> predecesor;

    public DijkstraResult(V source, Map<V, Double> distancias, Map<V, V> predecesor) {
        this.source = source;
        this.distancias = distancias;
        this.predecesor = predecesor;
    }

    @Override
    public double getCost(V otherVertex) {
        return distancias.getOrDefault(otherVertex, Double.POSITIVE_INFINITY);
    }

    public List<V> getPath(V destino) {
        LinkedList<V> path = new LinkedList<>();
        if (!distancias.containsKey(destino) || distancias.get(destino).isInfinite()) return path;
        for (V at = destino; at != null; at = predecesor.get(at)) path.addFirst(at);
        if (!path.isEmpty() && !path.getFirst().equals(source)) return List.of(); // sin camino
        return path;
    }
}
