package ucu.edu.aed.utils;

import ucu.edu.aed.tda.grafo.model.IGraph;
import ucu.edu.aed.tda.grafo.model.edge.Edge;
import ucu.edu.aed.tda.grafo.model.edge.WeightedEdge;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

// Utilidad con varios algoritmos extra para trabajar con los caminos de un grafo
public class AlgoritmosCaminos<V, D extends WeightedEdge> {

    // Punto de entrada: inicializa estructuras y delega en el auxiliar recursivo
    public List<List<V>> todosLosCaminos(IGraph<V, D> grafo, V origen, V destino) {
        HashSet<V> visitados = new HashSet<>();
        List<V> camino = new ArrayList<>();
        List<List<V>> resultado = new ArrayList<>();
        todosLosCaminosAux(grafo, origen, destino, visitados, camino, resultado);
        return resultado;
    }

    // DFS con backtracking: al llegar al destino guarda una copia del camino actual
    private void todosLosCaminosAux(IGraph<V, D> grafo, V actual, V destino,
            HashSet<V> visitados, List<V> camino, List<List<V>> resultado) {
        visitados.add(actual);
        camino.add(actual);
        if (actual.equals(destino)) {
            resultado.add(new ArrayList<>(camino));
        } else {
            for (Edge<V, D> arista : grafo.adyacencias(grafo.construirComparable(actual))) {
                V vecino = arista.target();
                if (!visitados.contains(vecino)) {
                    todosLosCaminosAux(grafo, vecino, destino, visitados, camino, resultado);
                }
            }
        }
        // Backtracking: desmarcar para que otros caminos puedan pasar por aquí
        camino.remove(camino.size() - 1);
        visitados.remove(actual);
    }

    // Suma los pesos de las aristas entre vértices consecutivos del camino
    public double calcularCosto(IGraph<V, D> grafo, List<V> camino) {
        double costo = 0;
        for (int i = 0; i < camino.size() - 1; i++) {
            V actual = camino.get(i);
            V siguiente = camino.get(i + 1);
            for (Edge<V, D> arista : grafo.adyacencias(grafo.construirComparable(actual))) {
                if (arista.target().equals(siguiente)) {
                    costo = costo + arista.dato().getWeight();
                    break;
                }
            }
        }
        return costo;
    }

    // Retorna el camino de mayor costo entre todos los caminos posibles
    public List<V> caminoCritico(IGraph<V, D> grafo, V origen, V destino) {
        List<List<V>> todos = todosLosCaminos(grafo, origen, destino);
        List<V> critico = null;
        double maxCosto = -1;
        for (List<V> camino : todos) {
            double costo = calcularCosto(grafo, camino);
            if (costo > maxCosto) {
                maxCosto = costo;
                critico = camino;
            }
        }
        return critico;
    }

    // Retorna el costo del camino crítico, o 0 si no existe camino
    public double costoCritico(IGraph<V, D> grafo, V origen, V destino) {
        List<V> critico = caminoCritico(grafo, origen, destino);
        if (critico == null) {
            return 0;
        }
        return calcularCosto(grafo, critico);
    }

    // Retorna el camino de menor costo entre todos los caminos posibles
    public List<V> caminoMinCosto(IGraph<V, D> grafo, V origen, V destino) {
        List<List<V>> todos = todosLosCaminos(grafo, origen, destino);
        List<V> minCamino = null;
        double minCosto = Double.MAX_VALUE;
        for (List<V> camino : todos) {
            double costo = calcularCosto(grafo, camino);
            if (costo < minCosto) {
                minCosto = costo;
                minCamino = camino;
            }
        }
        return minCamino;
    }

    // Holgura = cuánto le falta a este camino para ser crítico
    public double holgura(IGraph<V, D> grafo, List<V> camino, double costoCritico) {
        double costo = calcularCosto(grafo, camino);
        return costoCritico - costo;
    }

    // Imprime cada camino con su costo y su holgura respecto al camino crítico
    public void imprimirCaminosConHolgura(IGraph<V, D> grafo, V origen, V destino) {
        List<List<V>> todos = todosLosCaminos(grafo, origen, destino);
        double costoMax = costoCritico(grafo, origen, destino);
        for (List<V> camino : todos) {
            double costo = calcularCosto(grafo, camino);
            double h = costoMax - costo;
            System.out.print("Camino: ");
            for (int i = 0; i < camino.size(); i++) {
                if (i > 0) {
                    System.out.print(" -> ");
                }
                System.out.print(camino.get(i));
            }
            System.out.println("  |  Costo: " + costo + "  |  Holgura: " + h);
        }
    }
}
