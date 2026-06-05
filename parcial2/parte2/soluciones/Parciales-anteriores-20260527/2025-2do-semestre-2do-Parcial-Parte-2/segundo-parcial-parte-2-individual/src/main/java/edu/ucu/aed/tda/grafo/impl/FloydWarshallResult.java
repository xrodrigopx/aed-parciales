package edu.ucu.aed.tda.grafo.impl;

import edu.ucu.aed.tda.grafo.model.result.IFloydWarshallResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class FloydWarshallResult<V> implements IFloydWarshallResult<V> {
    public final Map<V, Map<V, Double>> dist;  // dist[u][v]
    public final Map<V, Map<V, V>> next;       // next[u][v] para reconstruir camino

    public FloydWarshallResult(Map<V, Map<V, Double>> dist, Map<V, Map<V, V>> next) {
        this.dist = dist;
        this.next = next;
    }

    /**
     * Camino óptimo u -> v (vacío si inalcanzable).
     */
    public List<V> getPath(V u, V v) {
        List<V> p = new ArrayList<>();
        V at = u;
        while (!at.equals(v)) {
            p.add(at);
            at = next.get(at).get(v);
            if (at == null) {
                return List.of();
            }
        }
        p.add(v);
        return p;
    }

    public double getCost(V source, V target) {
        return dist.get(source).getOrDefault(target, Double.POSITIVE_INFINITY);
    }

    public boolean connected(V source, V target) {
        Map<V, Double> row = dist.get(source);
        if (row == null) return false;
        Double v = row.get(target);
        return v != 0 && !Double.isInfinite(v);
    }
}