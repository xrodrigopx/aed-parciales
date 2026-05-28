import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Clase auxiliar para obtener todos los caminos posibles en un grafo dirigido.
 *
 * Requiere: IGraph<V,D> con vertices() y adyacencias(V)
 *           Edge<V,D> con target() y weight()
 *
 * Copiar en el paquete que corresponda y ajustar los imports.
 */
public class AlgoritmosCaminos<V, D> {

    // ---------------------------------------------------------------
    // Todos los caminos simples entre origen y destino
    // ---------------------------------------------------------------

    public List<List<V>> todosLosCaminos(IGraph<V, D> grafo, V origen, V destino) {
        HashSet<V> visitados = new HashSet<>();
        List<V> camino = new ArrayList<>();
        List<List<V>> resultado = new ArrayList<>();
        todosLosCaminosAux(grafo, origen, destino, visitados, camino, resultado);
        return resultado;
    }

    private void todosLosCaminosAux(IGraph<V, D> grafo, V actual, V destino,
            HashSet<V> visitados, List<V> camino, List<List<V>> resultado) {
        visitados.add(actual);
        camino.add(actual);
        if (actual.equals(destino)) {
            resultado.add(new ArrayList<>(camino));
        } else {
            for (Edge<V, D> arista : grafo.adyacencias(actual)) {
                V vecino = arista.target();
                if (!visitados.contains(vecino)) {
                    todosLosCaminosAux(grafo, vecino, destino, visitados, camino, resultado);
                }
            }
        }
        camino.remove(camino.size() - 1);
        visitados.remove(actual);
    }

    // ---------------------------------------------------------------
    // Costo total de un camino (suma de pesos de sus aristas)
    // ---------------------------------------------------------------

    public double calcularCosto(IGraph<V, D> grafo, List<V> camino) {
        double costo = 0;
        for (int i = 0; i < camino.size() - 1; i++) {
            V actual = camino.get(i);
            V siguiente = camino.get(i + 1);
            for (Edge<V, D> arista : grafo.adyacencias(actual)) {
                if (arista.target().equals(siguiente)) {
                    costo = costo + arista.weight();
                    break;
                }
            }
        }
        return costo;
    }

    // ---------------------------------------------------------------
    // Camino crítico: el de mayor costo (usado en Ej 6)
    // ---------------------------------------------------------------

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

    public double costoCritico(IGraph<V, D> grafo, V origen, V destino) {
        List<V> critico = caminoCritico(grafo, origen, destino);
        if (critico == null) {
            return 0;
        }
        return calcularCosto(grafo, critico);
    }

    // ---------------------------------------------------------------
    // Camino de menor costo
    // ---------------------------------------------------------------

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

    // ---------------------------------------------------------------
    // Holgura de un camino = costoCritico - costoCamino
    // Un camino con holgura 0 es crítico.
    // ---------------------------------------------------------------

    public double holgura(IGraph<V, D> grafo, List<V> camino, double costoCritico) {
        double costo = calcularCosto(grafo, camino);
        return costoCritico - costo;
    }

    // ---------------------------------------------------------------
    // Imprime todos los caminos con su costo y holgura
    // ---------------------------------------------------------------

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
