package ucu.edu.aed.tda.grafo.impl;

import junit.framework.TestCase;
import ucu.edu.aed.tda.grafo.IDirectedIGraph;
import ucu.edu.aed.tda.grafo.model.edge.WeightedEdge;
import ucu.edu.aed.tda.grafo.model.result.IFloydWarshallResult;
import ucu.edu.aed.tda.grafo.model.result.Path;

import java.util.ArrayList;
import java.util.List;

public class AlgoritmosGrafoDirigidoTest extends TestCase {

    private IDirectedIGraph<String, WeightedEdge> buildGraph() {
        IDirectedIGraph<String, WeightedEdge> g = new GrafoDirigido<>();

        g.agregarVertice("A");
        g.agregarVertice("B");
        g.agregarVertice("C");
        g.agregarVertice("D");

        g.agregarArista("A", "B", new WeightedEdge(1));
        g.agregarArista("B", "C", new WeightedEdge(1));
        g.agregarArista("C", "D", new WeightedEdge(1));

        return g;
    }

    // ---------------- WARSHALL ----------------

    public void testWarshallTransitivo() {
        AlgoritmosGrafoDirigido alg = new AlgoritmosGrafoDirigido();
        IDirectedIGraph<String, WeightedEdge> g = buildGraph();

        IFloydWarshallResult<String> result = alg.warshall(g);

        assertTrue(result.connected("A", "B"));
        assertTrue(result.connected("A", "C"));
        assertTrue(result.connected("A", "D"));
    }

    public void testWarshallNoConexion() {
        AlgoritmosGrafoDirigido alg = new AlgoritmosGrafoDirigido();
        IDirectedIGraph<String, WeightedEdge> g = buildGraph();

        IFloydWarshallResult<String> result = alg.warshall(g);

        assertFalse(result.connected("D", "A"));
        assertFalse(result.connected("C", "A"));
    }

    public void testWarshallSelf() {
        AlgoritmosGrafoDirigido alg = new AlgoritmosGrafoDirigido();
        IDirectedIGraph<String, WeightedEdge> g = buildGraph();

        IFloydWarshallResult<String> result = alg.warshall(g);

        assertFalse(result.connected("A", "A"));
    }

    public void testWarshallDisconnected() {
        AlgoritmosGrafoDirigido alg = new AlgoritmosGrafoDirigido();

        IDirectedIGraph<String, WeightedEdge> g = new GrafoDirigido<>();

        g.agregarVertice("A");
        g.agregarVertice("B");
        g.agregarVertice("C");

        g.agregarArista("A", "B", new WeightedEdge(1));

        IFloydWarshallResult<String> result = alg.warshall(g);

        assertTrue(result.connected("A", "B"));
        assertFalse(result.connected("B", "A"));
        assertFalse(result.connected("A", "C"));
    }

    // ---------------- FLOYD ----------------

    public void testFloydCostSimple() {
        AlgoritmosGrafoDirigido alg = new AlgoritmosGrafoDirigido();
        IDirectedIGraph<String, WeightedEdge> g = buildGraph();

        IFloydWarshallResult<String> result = alg.floyd(g);

        assertEquals(1.0, result.getCost("A", "B"));
        assertEquals(2.0, result.getCost("A", "C"));
        assertEquals(3.0, result.getCost("A", "D"));
    }

    public void testFloydIndirectPathBetter() {
        AlgoritmosGrafoDirigido alg = new AlgoritmosGrafoDirigido();

        IDirectedIGraph<String, WeightedEdge> g = new GrafoDirigido<>();

        g.agregarVertice("A");
        g.agregarVertice("B");
        g.agregarVertice("C");

        g.agregarArista("A", "C", new WeightedEdge(10));
        g.agregarArista("A", "B", new WeightedEdge(2));
        g.agregarArista("B", "C", new WeightedEdge(2));

        IFloydWarshallResult<String> result = alg.floyd(g);

        assertEquals(4.0, result.getCost("A", "C"));
    }

    public void testFloydNoPath() {
        AlgoritmosGrafoDirigido alg = new AlgoritmosGrafoDirigido();

        IDirectedIGraph<String, WeightedEdge> g = new GrafoDirigido<>();

        g.agregarVertice("A");
        g.agregarVertice("B");

        IFloydWarshallResult<String> result = alg.floyd(g);

        assertEquals(Double.MAX_VALUE / 2, result.getCost("A", "B"));
    }

    // ---------------- RECORRIDOS ----------------
    
    public void testRecorridoDFS() {
        AlgoritmosGrafoDirigido alg = new AlgoritmosGrafoDirigido();
        IDirectedIGraph<String, WeightedEdge> g = buildGraph();
        List<String> visitados = new ArrayList<>();
        
        alg.recorridoEnProfundidad(g, "A", new java.util.function.Consumer<String>() {
            public void accept(String v) {
                visitados.add(v);
            }
        });
        
        assertEquals(4, visitados.size());
        assertEquals("A", visitados.get(0));
        assertTrue(visitados.contains("D"));
    }
    
    public void testRecorridoBFS() {
        AlgoritmosGrafoDirigido alg = new AlgoritmosGrafoDirigido();
        IDirectedIGraph<String, WeightedEdge> g = new GrafoDirigido<>();
        g.agregarVertice("A");
        g.agregarVertice("B");
        g.agregarVertice("C");
        g.agregarArista("A", "B", new WeightedEdge(1));
        g.agregarArista("A", "C", new WeightedEdge(1));
        
        List<String> visitados = new ArrayList<>();
        alg.recorridoEnAmplitud(g, "A", new java.util.function.Consumer<String>() {
            public void accept(String v) {
                visitados.add(v);
            }
        });
        
        assertEquals(3, visitados.size());
        assertEquals("A", visitados.get(0));
    }

    // ---------------- EXCENTRICIDAD Y CENTRO ----------------
    
    public void testExcentricidad() {
        AlgoritmosGrafoDirigido alg = new AlgoritmosGrafoDirigido();
        IDirectedIGraph<String, WeightedEdge> g = buildGraph();
        
        double excA = alg.obtenerExcentricidad(g, "A");
        double excB = alg.obtenerExcentricidad(g, "B");
        
        assertEquals(3.0, excA);
        assertEquals(Double.MAX_VALUE, excB);
    }
    
    public void testCentroDelGrafo() {
        AlgoritmosGrafoDirigido alg = new AlgoritmosGrafoDirigido();
        IDirectedIGraph<String, WeightedEdge> g = buildGraph();
        
        // En un grafo linea A->B->C->D dirigido, el que llega a todo con menor distancia es C? No.
        // A llega a todos (max dist 3).
        // B llega a C, D (max dist 2). A los demas no llega (distancia infinita).
        // Por lo tanto, A es el único con distancias finitas a todos si consideramos solo alcanzables
        // Pero en la implementacion estricta, la excentricidad es maxDist. 
        // Solo para nodos sin aristas, puede dar negativo o infinito.
        String centro = alg.obtenerCentroGrafo(g);
        assertNotNull(centro);
    }

    // ---------------- TOPOLOGICO Y CAMINO CRITICO ----------------
    
    public void testClasificacionTopologica() {
        AlgoritmosGrafoDirigido alg = new AlgoritmosGrafoDirigido();
        IDirectedIGraph<String, WeightedEdge> g = buildGraph();
        
        List<String> orden = alg.calcularClasificacionTopologica(g);
        
        assertEquals(4, orden.size());
        assertEquals("A", orden.get(0)); // A debe ir antes que todos
        assertEquals("D", orden.get(3)); // D debe ir ultimo
    }
    
    public void testCaminoCritico() {
        AlgoritmosGrafoDirigido alg = new AlgoritmosGrafoDirigido();
        IDirectedIGraph<String, WeightedEdge> g = new GrafoDirigido<>();
        g.agregarVertice("Inicio");
        g.agregarVertice("T1");
        g.agregarVertice("T2");
        g.agregarVertice("Fin");
        
        g.agregarArista("Inicio", "T1", new WeightedEdge(5));
        g.agregarArista("Inicio", "T2", new WeightedEdge(10));
        g.agregarArista("T1", "Fin", new WeightedEdge(2));
        g.agregarArista("T2", "Fin", new WeightedEdge(3));
        
        Path<String> camino = alg.obtenerCaminoCritico(g);
        
        assertEquals(13.0, camino.getCost());
        assertEquals(3, camino.getPath().size());
        assertEquals("Inicio", camino.getPath().get(0));
        assertEquals("T2", camino.getPath().get(1));
        assertEquals("Fin", camino.getPath().get(2));
    }
}