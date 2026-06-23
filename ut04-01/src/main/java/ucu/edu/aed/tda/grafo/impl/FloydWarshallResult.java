package ucu.edu.aed.tda.grafo.impl;

import ucu.edu.aed.tda.grafo.model.result.IFloydWarshallResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// Clase que guarda los resultados de Floyd-Warshall (matrices de costos y caminos)
public class FloydWarshallResult<V> implements IFloydWarshallResult<V> {

    private final double[][] costos;
    private final int[][] predecesores;
    private final List<V> verticesList;
    private final HashMap<V, Integer> indice;

    public FloydWarshallResult(double[][] costos, int[][] predecesores, List<V> verticesList) {
        this.costos = costos;
        this.predecesores = predecesores;
        this.verticesList = verticesList;
        this.indice = new HashMap<>();
        for (int i = 0; i < verticesList.size(); i++) {
            indice.put(verticesList.get(i), i);
        }
    }

    // Cuánto cuesta ir del nodo 'source' al 'target'
    @Override
    public double getCost(V source, V target) {
        Integer i = indice.get(source);
        Integer j = indice.get(target);
        if (i == null) {
            return Double.MAX_VALUE;
        }
        if (j == null) {
            return Double.MAX_VALUE;
        }
        return costos[i][j];
    }

    // Revisa si es posible llegar del origen al destino
    @Override
    public boolean connected(V source, V target) {
        Integer i = indice.get(source);
        Integer j = indice.get(target);
        if (i == null) {
            return false;
        }
        if (j == null) {
            return false;
        }
        return costos[i][j] < Double.MAX_VALUE / 2;
    }

    // Arma la ruta completa desde 'source' hasta 'target' usando la matriz de predecesores
    @Override
    public List<V> getPath(V source, V target) {
        List<V> camino = new ArrayList<>();
        if (!connected(source, target)) {
            return camino;
        }
        int i = indice.get(source);
        int j = indice.get(target);
        reconstruirCamino(i, j, camino);
        return camino;
    }

    // Función recursiva que reconstruye el camino leyendo los pasos intermedios (nodo k)
    private void reconstruirCamino(int i, int j, List<V> camino) {
        if (predecesores[i][j] == -1) {
            camino.add(verticesList.get(i));
            camino.add(verticesList.get(j));
        } else {
            int k = predecesores[i][j];
            reconstruirCamino(i, k, camino);
            camino.remove(camino.size() - 1);
            reconstruirCamino(k, j, camino);
        }
    }
}
