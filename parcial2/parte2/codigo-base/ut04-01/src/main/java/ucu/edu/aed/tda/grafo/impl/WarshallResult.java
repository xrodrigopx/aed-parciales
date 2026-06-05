package ucu.edu.aed.tda.grafo.impl;

import java.util.HashMap;
import java.util.List;

import ucu.edu.aed.tda.grafo.model.result.IFloydWarshallResult;

// Guarda el resultado de Warshall (solo la matriz de ceros y unos para saber si hay conexión)
public class WarshallResult<V> implements IFloydWarshallResult<V> {

    private final int[][] adjacencyMatrix;
    private final HashMap<V, Integer> indice;

    public WarshallResult(int[][] adjacencyMatrix, List<V> vertexList) {
        this.adjacencyMatrix = adjacencyMatrix;
        this.indice = new HashMap<>();
        for (int i = 0; i < vertexList.size(); i++) {
            indice.put(vertexList.get(i), i);
        }
    }
    
    @Override
    public boolean connected(V source, V target) {
        Integer i = indice.get(source);
        Integer j = indice.get(target);
        if (i == null || j == null) {
            return false;
        }
        return adjacencyMatrix[i][j] == 1;
    }

    @Override
    public double getCost(V source, V target) {
        throw new UnsupportedOperationException("Warshall no calcula costos");
    }

    @Override
    public List<V> getPath(V source, V target) {
        throw new UnsupportedOperationException("Warshall no reconstruye caminos");
    }

}
