package ucu.edu.aed.tda.grafo.impl;

import ucu.edu.aed.tda.grafo.IDirectedGraphAlgorithms;
import ucu.edu.aed.tda.grafo.IDirectedIGraph;
import ucu.edu.aed.tda.grafo.model.IGraph;
import ucu.edu.aed.tda.grafo.model.edge.Edge;
import ucu.edu.aed.tda.grafo.model.edge.WeightedEdge;
import ucu.edu.aed.tda.grafo.model.result.IDijkstraResult;
import ucu.edu.aed.tda.grafo.model.result.IFloydWarshallResult;
import ucu.edu.aed.tda.grafo.model.result.Path;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class AlgoritmosGrafoDirigido implements IDirectedGraphAlgorithms {

    // Método para encontrar el camino más corto desde un nodo origen (Dijkstra)
    @Override
    public <V, D extends WeightedEdge> IDijkstraResult<V> dijkstra(Comparable<V> source, IDirectedIGraph<V, D> grafo) {
        // Buscamos el vértice de origen en el grafo
        V origen = grafo.buscarVertice(source);
        if (origen == null) {
            return new DijkstraResult<>(new HashMap<>(), new HashMap<>());
        }

        // Mapas para guardar la distancia más corta y el nodo anterior
        HashMap<V, Double> distancias = new HashMap<>();
        HashMap<V, V> predecesores = new HashMap<>();
        java.util.HashSet<V> visitados = new java.util.HashSet<>();

        // Inicializamos las distancias como infinito (muy grande)
        for (V v : grafo.vertices()) {
            distancias.put(v, Double.MAX_VALUE);
            predecesores.put(v, null);
        }

        // La distancia al origen siempre es 0
        distancias.put(origen, 0.0);

        // Repetimos hasta visitar todos los vértices
        while (visitados.size() < grafo.cantidadDeVertices()) {
            V minVertice = null;
            double minDist = Double.MAX_VALUE;
            
            // Buscamos el nodo no visitado que tenga la distancia más pequeña
            for (V v : distancias.keySet()) {
                if (!visitados.contains(v)) {
                    if (distancias.get(v) < minDist) {
                        minDist = distancias.get(v);
                        minVertice = v;
                    }
                }
            }

            // Si no hay más nodos alcanzables, terminamos
            if (minVertice == null) {
                break;
            }
            if (minDist == Double.MAX_VALUE) {
                break;
            }

            // Marcamos el nodo como visitado
            visitados.add(minVertice);

            // Actualizamos las distancias de los vecinos
            for (Edge<V, D> arista : grafo.adyacencias(grafo.construirComparable(minVertice))) {
                V vecino = arista.target();
                if (!visitados.contains(vecino)) {
                    double pesoArista = arista.dato().getWeight();
                    double nuevaDistancia = distancias.get(minVertice) + pesoArista;
                    // Si encontramos un camino más corto, lo guardamos
                    if (nuevaDistancia < distancias.get(vecino)) {
                        distancias.put(vecino, nuevaDistancia);
                        predecesores.put(vecino, minVertice);
                    }
                }
            }
        }

        return new DijkstraResult<>(distancias, predecesores);
    }

    // Algoritmo de Floyd-Warshall para encontrar todos los caminos mínimos entre pares de nodos
    @Override
    public <V, D extends WeightedEdge> IFloydWarshallResult<V> floyd(IDirectedIGraph<V, D> grafo) {
        List<V> verticesList = new ArrayList<>(grafo.vertices());
        int n = verticesList.size();

        // Mapeamos cada vértice a un índice numérico
        HashMap<V, Integer> indice = new HashMap<>();
        for (int i = 0; i < n; i++) {
            indice.put(verticesList.get(i), i);
        }

        // Matrices para guardar costos y predecesores
        double[][] costos = new double[n][n];
        int[][] predecesores = new int[n][n];

        // Inicializamos las matrices
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    costos[i][j] = 0.0;
                } else {
                    costos[i][j] = Double.MAX_VALUE / 2; // Evitamos desbordamiento al sumar
                }
                predecesores[i][j] = -1;
            }
        }

        // Cargamos los pesos de las aristas existentes
        for (Edge<V, D> arista : grafo.aristas()) {
            int i = indice.get(arista.source());
            int j = indice.get(arista.target());
            double peso = arista.dato().getWeight();
            if (peso < costos[i][j]) {
                costos[i][j] = peso;
            }
        }

        // Verificamos si pasar por un nodo k mejora el camino de i a j
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    boolean iKAlcanzable = costos[i][k] < Double.MAX_VALUE / 2;
                    if (iKAlcanzable) {
                        boolean kjAlcanzable = costos[k][j] < Double.MAX_VALUE / 2;
                        if (kjAlcanzable) {
                            double nuevoCosto = costos[i][k] + costos[k][j];
                            if (nuevoCosto < costos[i][j]) {
                                costos[i][j] = nuevoCosto;
                                predecesores[i][j] = k;
                            }
                        }
                    }
                }
            }
        }

        return new FloydWarshallResult<>(costos, predecesores, verticesList);
    }

    // Algoritmo de Warshall para saber si hay conexión entre cualquier par de nodos
    @Override
    public <V, D extends WeightedEdge> IFloydWarshallResult<V> warshall(IDirectedIGraph<V, D> grafo) {
        List<V> vertexList = new ArrayList<>(grafo.vertices());
        int quantityVertex = grafo.cantidadDeVertices();
        int[][] adjacencyMatrix = new int[quantityVertex][quantityVertex];
        
        // Creamos la matriz de adyacencia inicial con 1s y 0s
        for (int i = 0 ; i < quantityVertex ; i++) {
            for (int j = 0 ; j < quantityVertex ; j++) {
                V source = vertexList.get(i);
                V target = vertexList.get(j);
                adjacencyMatrix[i][j] = (grafo.obtenerArista(source, target) != null) ? 1 : 0;
            }
        }
        
        // Aplicamos el algoritmo
        for (int k = 0; k < quantityVertex; k++) {
            for (int i = 0; i < quantityVertex; i++) {
                if (adjacencyMatrix[i][k] == 0)
                    continue;
                for (int j = 0; j < quantityVertex; j++) {
                    if (adjacencyMatrix[i][j] == 0) {
                        // Si i llega a k, y k llega a j, entonces i llega a j
                        adjacencyMatrix[i][j] = adjacencyMatrix[i][k] & adjacencyMatrix[k][j];
                    }
                }
            }
        }
        return new WarshallResult<V>(adjacencyMatrix, vertexList);
    }

    // Calcula el centro del grafo (el nodo con la menor excentricidad)
    @Override
    public <V, D extends WeightedEdge> V obtenerCentroGrafo(IDirectedIGraph<V, D> grafo) {
        V centro = null;
        double minExcentricidad = Double.MAX_VALUE;
        // Revisamos cada vértice para ver cuál tiene la excentricidad más baja
        for (V v : grafo.vertices()) {
            double exc = obtenerExcentricidad(grafo, grafo.construirComparable(v));
            if (exc < minExcentricidad) {
                minExcentricidad = exc;
                centro = v;
            }
        }
        return centro;
    }

    // Calcula la excentricidad (la distancia más larga desde un nodo a cualquier otro)
    @Override
    public <V, D extends WeightedEdge> double obtenerExcentricidad(IDirectedIGraph<V, D> grafo, Comparable<V> vertexCriteria) {
        V origen = grafo.buscarVertice(vertexCriteria);
        if (origen == null) {
            return Double.MAX_VALUE;
        }
        // Usamos Dijkstra para saber todas las distancias desde el origen
        IDijkstraResult<V> result = dijkstra(vertexCriteria, grafo);
        double maxDist = -1;
        
        // Buscamos la distancia más grande
        for (V destino : grafo.vertices()) {
            if (!destino.equals(origen)) {
                double dist = result.getCost(destino);
                if (dist > maxDist) {
                    maxDist = dist;
                }
            }
        }
        return maxDist;
    }

    // Encuentra todos los caminos posibles entre un nodo origen y un destino
    @Override
    public <V, D extends WeightedEdge> List<Path<V>> obtenerTodosLosCaminos(Comparable<V> source, Comparable<V> target, IGraph<V, D> grafo) {
        V origen = grafo.buscarVertice(source);
        V destino = grafo.buscarVertice(target);
        
        List<Path<V>> resultado = new ArrayList<>();
        if (origen == null) {
            return resultado;
        }
        if (destino == null) {
            return resultado;
        }
        
        java.util.HashSet<V> visitados = new java.util.HashSet<>();
        List<V> caminoActual = new ArrayList<>();
        
        // Empezamos la búsqueda recursiva
        todosLosCaminosAux(grafo, origen, destino, visitados, caminoActual, 0.0, resultado);
        return resultado;
    }

    // Método auxiliar recursivo para buscar todos los caminos (Backtracking)
    private <V, D extends WeightedEdge> void todosLosCaminosAux(
            IGraph<V, D> grafo, V actual, V destino, java.util.Set<V> visitados, 
            List<V> caminoActual, double costoActual, List<Path<V>> resultado) {
        
        visitados.add(actual);
        caminoActual.add(actual);
        
        // Si llegamos al destino, guardamos el camino
        if (actual.equals(destino)) {
            resultado.add(new Path<>(new ArrayList<>(caminoActual), costoActual));
        } else {
            // Sino, probamos ir por todos los vecinos que no hayamos visitado
            for (Edge<V, D> arista : grafo.adyacencias(grafo.construirComparable(actual))) {
                V vecino = arista.target();
                if (!visitados.contains(vecino)) {
                    double pesoArista = arista.dato().getWeight();
                    todosLosCaminosAux(grafo, vecino, destino, visitados, caminoActual, 
                                       costoActual + pesoArista, resultado);
                }
            }
        }
        
        // Al terminar, quitamos el nodo para poder usarlo en otros caminos (backtracking)
        caminoActual.remove(caminoActual.size() - 1);
        visitados.remove(actual);
    }

    // Recorrido en Profundidad (DFS)
    @Override
    public <V, D> void recorridoEnProfundidad(IGraph<V, D> grafo, Comparable<V> sourceCriteria, Consumer<V> consumer) {
        V origen = grafo.buscarVertice(sourceCriteria);
        if (origen == null) return;
        
        java.util.Set<V> visitados = new java.util.HashSet<>();
        dfs(grafo, origen, visitados, consumer);
    }

    // Función recursiva para DFS
    private <V, D> void dfs(IGraph<V, D> grafo, V actual, java.util.Set<V> visitados, Consumer<V> consumer) {
        visitados.add(actual);
        consumer.accept(actual); // Hacemos la acción requerida sobre el nodo
        
        for (Edge<V, D> arista : grafo.adyacencias(grafo.construirComparable(actual))) {
            V vecino = arista.target();
            if (!visitados.contains(vecino)) {
                dfs(grafo, vecino, visitados, consumer);
            }
        }
    }

    // Recorrido en Amplitud (BFS)
    @Override
    public <V, D> void recorridoEnAmplitud(IGraph<V, D> grafo, Comparable<V> sourceCriteria, Consumer<V> consumer) {
        V origen = grafo.buscarVertice(sourceCriteria);
        if (origen == null) return;
        
        java.util.Set<V> visitados = new java.util.HashSet<>();
        java.util.Queue<V> cola = new java.util.LinkedList<>();
        
        cola.offer(origen);
        visitados.add(origen);
        
        while (!cola.isEmpty()) {
            V actual = cola.poll();
            consumer.accept(actual); // Acción sobre el nodo actual
            
            // Agregamos a la cola todos los vecinos no visitados
            for (Edge<V, D> arista : grafo.adyacencias(grafo.construirComparable(actual))) {
                V vecino = arista.target();
                if (!visitados.contains(vecino)) {
                    visitados.add(vecino);
                    cola.offer(vecino);
                }
            }
        }
    }

    // Clasificación Topológica usando orden de finalización DFS
    @Override
    public <V, D> List<V> calcularClasificacionTopologica(IDirectedIGraph<V, D> grafo) {
        List<V> resultado = new ArrayList<>();
        java.util.Set<V> visitados = new java.util.HashSet<>();
        java.util.Stack<V> pila = new java.util.Stack<>();

        // Revisamos todos los nodos por si el grafo está desconectado
        for (V v : grafo.vertices()) {
            if (!visitados.contains(v)) {
                dfsTopologico(grafo, v, visitados, pila);
            }
        }

        // Vaciamos la pila para tener el orden correcto
        while (!pila.isEmpty()) {
            resultado.add(pila.pop());
        }
        return resultado;
    }

    private <V, D> void dfsTopologico(IDirectedIGraph<V, D> grafo, V actual, java.util.Set<V> visitados, java.util.Stack<V> pila) {
        visitados.add(actual);
        for (Edge<V, D> arista : grafo.adyacencias(grafo.construirComparable(actual))) {
            V vecino = arista.target();
            if (!visitados.contains(vecino)) {
                dfsTopologico(grafo, vecino, visitados, pila);
            }
        }
        // Agregamos a la pila al terminar de procesar todos sus hijos
        pila.push(actual);
    }
    
    // Calcula el Camino Crítico (ruta más larga en un DAG)
    public <V, D extends WeightedEdge> Path<V> obtenerCaminoCritico(IDirectedIGraph<V, D> grafo) {
        // Primero obtenemos el orden topológico
        List<V> orden = calcularClasificacionTopologica(grafo);
        
        HashMap<V, Double> distancias = new HashMap<>();
        HashMap<V, V> predecesores = new HashMap<>();
        
        for (V v : grafo.vertices()) {
            distancias.put(v, -Double.MAX_VALUE); // Empezamos en negativo muy grande
            predecesores.put(v, null);
        }
        
        // Iniciamos el primer nodo de cada componente en 0
        for (V v : orden) {
            if (grafo.predecessors(grafo.construirComparable(v)).isEmpty()) {
                distancias.put(v, 0.0);
            }
        }
        
        // Calculamos el camino más largo relajando aristas en orden
        for (V u : orden) {
            if (distancias.get(u) != -Double.MAX_VALUE) {
                for (Edge<V, D> arista : grafo.adyacencias(grafo.construirComparable(u))) {
                    V v = arista.target();
                    double peso = arista.dato().getWeight();
                    if (distancias.get(v) < distancias.get(u) + peso) {
                        distancias.put(v, distancias.get(u) + peso);
                        predecesores.put(v, u);
                    }
                }
            }
        }
        
        // Buscamos el nodo con la distancia máxima total
        V nodoFinal = null;
        double maxDist = -Double.MAX_VALUE;
        for (V v : distancias.keySet()) {
            if (distancias.get(v) > maxDist) {
                maxDist = distancias.get(v);
                nodoFinal = v;
            }
        }
        
        // Reconstruimos el camino hacia atrás
        List<V> caminoCritico = new ArrayList<>();
        V actual = nodoFinal;
        while (actual != null) {
            caminoCritico.add(0, actual);
            actual = predecesores.get(actual);
        }
        
        return new Path<>(caminoCritico, maxDist);
    }
}
