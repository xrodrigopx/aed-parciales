package ucu.edu.aed.tda.grafo.main;

import ucu.edu.aed.tda.grafo.IDirectedGraphAlgorithms;
import ucu.edu.aed.tda.grafo.IDirectedIGraph;
import ucu.edu.aed.tda.grafo.impl.AlgoritmosGrafoDirigido;
import ucu.edu.aed.tda.grafo.impl.GrafoDirigido;
import ucu.edu.aed.tda.grafo.model.edge.WeightedEdge;
import ucu.edu.aed.tda.grafo.model.result.IFloydWarshallResult;
import ucu.edu.aed.tda.grafo.model.result.Path;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class MainEjercicios {

    public static void main(String[] args) {
        System.out.println("=== RESOLUCIÓN PRÁCTICA EJERCICIOS DE GRAFOS ===\n");
        
        resolverEjercicio3();
        resolverEjercicio6();
        resolverEjercicio7();
    }

    // ----------------------------------------------------
    // EJERCICIO 3: Floyd-Warshall (8 ciudades)
    // ----------------------------------------------------
    public static void resolverEjercicio3() {
        System.out.println("=== EJERCICIO 3: 8 Ciudades (Floyd-Warshall) ===");
        IDirectedIGraph<String, WeightedEdge> grafo = cargarGrafoEjemplo8Ciudades();
        IDirectedGraphAlgorithms algoritmos = new AlgoritmosGrafoDirigido();

        // Aplicamos Floyd
        IFloydWarshallResult<String> floydResult = algoritmos.floyd(grafo);

        // a) Distancia de Montevideo a Rio de Janeiro (ejemplo)
        double costoMvdRio = floydResult.getCost("Montevideo", "Rio de Janeiro");
        System.out.println("Distancia mínima de MVD a RIO: " + costoMvdRio);

        // b) Itinerario completo
        List<String> caminoMvdRio = floydResult.getPath("Montevideo", "Rio de Janeiro");
        System.out.print("Itinerario de MVD a RIO: ");
        printPath(caminoMvdRio);

        // c) Mejor aeropuerto para Hub de mantenimiento (Centro del Grafo)
        String mejorHub = algoritmos.obtenerCentroGrafo(grafo);
        System.out.println("Mejor ciudad para el Hub de Mantenimiento (Centro del Grafo): " + mejorHub);
        System.out.println();
    }

    // ----------------------------------------------------
    // EJERCICIO 6: Ciclos, Topológico y Camino Crítico
    // ----------------------------------------------------
    public static void resolverEjercicio6() {
        System.out.println("=== EJERCICIO 6: Orden Topológico y Camino Crítico ===");
        IDirectedIGraph<String, WeightedEdge> grafo = cargarGrafoDAG();
        AlgoritmosGrafoDirigido algoritmos = new AlgoritmosGrafoDirigido();

        // 1. ¿Tiene ciclos?
        System.out.println("¿El grafo tiene ciclos?: " + grafo.tieneCiclos());

        if (!grafo.tieneCiclos()) {
            // 2. Orden Topológico
            List<String> orden = algoritmos.calcularClasificacionTopologica(grafo);
            System.out.print("Orden Topológico: ");
            printPath(orden);

            // 3. Camino Crítico
            Path<String> caminoCritico = algoritmos.obtenerCaminoCritico(grafo);
            System.out.println("Costo del Camino Crítico: " + caminoCritico.getCost());
            System.out.print("Ruta del Camino Crítico: ");
            printPath(caminoCritico.getPath());
        }
        System.out.println();
    }

    // ----------------------------------------------------
    // EJERCICIO 7: Dijkstra y Grafo Figura
    // ----------------------------------------------------
    public static void resolverEjercicio7() {
        System.out.println("=== EJERCICIO 7: Dijkstra, Excentricidad y Grafo Figura ===");
        IDirectedIGraph<String, WeightedEdge> grafo = cargarGrafoFigura();
        AlgoritmosGrafoDirigido algoritmos = new AlgoritmosGrafoDirigido();

        // Excentricidad de todos
        for (String vertice : grafo.vertices()) {
            double exc = algoritmos.obtenerExcentricidad(grafo, vertice);
            System.out.println("Excentricidad de " + vertice + ": " + exc);
        }

        // Centro del grafo
        String centro = algoritmos.obtenerCentroGrafo(grafo);
        System.out.println("Centro del Grafo de la figura: " + centro);
        System.out.println();
    }

    // ----------------------------------------------------
    // MÉTODOS AUXILIARES (Utilidades y Carga Falsa/Real)
    // ----------------------------------------------------
    
    // Método que imprime la ruta de forma bonita A -> B -> C
    public static void printPath(List<String> camino) {
        if (camino == null || camino.isEmpty()) {
            System.out.println("Sin camino");
            return;
        }
        for (int i = 0; i < camino.size(); i++) {
            System.out.print(camino.get(i));
            if (i < camino.size() - 1) {
                System.out.print(" -> ");
            }
        }
        System.out.println();
    }

    // Carga un grafo desde archivos de texto. 
    // Como no tenemos los archivos, uso un Try-Catch e imprimo si falla.
    public static IDirectedIGraph<String, WeightedEdge> cargarDesdeArchivos(String archivoV, String archivoA) {
        IDirectedIGraph<String, WeightedEdge> grafo = new GrafoDirigido<>();
        try (BufferedReader brV = new BufferedReader(new FileReader(archivoV))) {
            String linea;
            while ((linea = brV.readLine()) != null) {
                grafo.agregarVertice(linea.trim());
            }
        } catch (IOException e) {
            System.err.println("No se encontro archivo de vertices: " + e.getMessage());
        }
        
        try (BufferedReader brA = new BufferedReader(new FileReader(archivoA))) {
            String linea;
            while ((linea = brA.readLine()) != null) {
                String[] partes = linea.split(",");
                if(partes.length == 3){
                    grafo.agregarArista(partes[0].trim(), partes[1].trim(), new WeightedEdge(Double.parseDouble(partes[2].trim())));
                }
            }
        } catch (IOException e) {
            System.err.println("No se encontro archivo de aristas: " + e.getMessage());
        }
        return grafo;
    }

    // Crea manualmente el grafo del Ejercicio 3
    private static IDirectedIGraph<String, WeightedEdge> cargarGrafoEjemplo8Ciudades() {
        IDirectedIGraph<String, WeightedEdge> g = new GrafoDirigido<>();
        String[] ciudades = {"Asuncion", "Buenos Aires", "Curitiba", "Montevideo", "Porto Alegre", "Rio de Janeiro", "San Pablo", "Santos"};
        for (String c : ciudades) g.agregarVertice(c);

        g.agregarArista("Asuncion", "Buenos Aires", new WeightedEdge(1600));
        g.agregarArista("Asuncion", "Curitiba", new WeightedEdge(800));
        g.agregarArista("Asuncion", "Porto Alegre", new WeightedEdge(700));
        g.agregarArista("Buenos Aires", "Montevideo", new WeightedEdge(200));
        g.agregarArista("Buenos Aires", "Porto Alegre", new WeightedEdge(1250));
        g.agregarArista("Curitiba", "Rio de Janeiro", new WeightedEdge(1500));
        g.agregarArista("Montevideo", "San Pablo", new WeightedEdge(980));
        g.agregarArista("Montevideo", "Santos", new WeightedEdge(1200));
        g.agregarArista("Porto Alegre", "San Pablo", new WeightedEdge(980));
        g.agregarArista("San Pablo", "Asuncion", new WeightedEdge(1200)); // Cuidado, esto hace ciclo
        
        return g;
    }

    // Crea manualmente un DAG para el Ejercicio 6
    private static IDirectedIGraph<String, WeightedEdge> cargarGrafoDAG() {
        IDirectedIGraph<String, WeightedEdge> g = new GrafoDirigido<>();
        g.agregarVertice("T1");
        g.agregarVertice("T2");
        g.agregarVertice("T3");
        g.agregarVertice("T4");

        g.agregarArista("T1", "T2", new WeightedEdge(3));
        g.agregarArista("T1", "T3", new WeightedEdge(2));
        g.agregarArista("T2", "T4", new WeightedEdge(4));
        g.agregarArista("T3", "T4", new WeightedEdge(1));
        return g;
    }

    // Crea manualmente el grafo de la Figura del Ejercicio 7
    private static IDirectedIGraph<String, WeightedEdge> cargarGrafoFigura() {
        IDirectedIGraph<String, WeightedEdge> g = new GrafoDirigido<>();
        String[] letras = {"A", "B", "C", "D", "E"};
        for (String l : letras) g.agregarVertice(l);

        g.agregarArista("B", "A", new WeightedEdge(6));
        g.agregarArista("A", "D", new WeightedEdge(4));
        g.agregarArista("D", "B", new WeightedEdge(3));
        g.agregarArista("A", "C", new WeightedEdge(1));
        g.agregarArista("C", "E", new WeightedEdge(1));
        g.agregarArista("E", "D", new WeightedEdge(2));
        g.agregarArista("D", "C", new WeightedEdge(2));
        g.agregarArista("B", "E", new WeightedEdge(3));
        
        return g;
    }
}
