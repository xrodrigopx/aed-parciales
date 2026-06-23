package ucu.edu.aed.utils;

import junit.framework.TestCase;
import ucu.edu.aed.tda.grafo.impl.GrafoDirigido;
import ucu.edu.aed.tda.grafo.model.edge.WeightedEdge;
import java.util.List;

public class AlgoritmosCaminosTest extends TestCase {

    private GrafoDirigido<String, WeightedEdge> buildGraph() {
        GrafoDirigido<String, WeightedEdge> g = new GrafoDirigido<>();
        g.agregarVertice("A");
        g.agregarVertice("B");
        g.agregarVertice("C");
        g.agregarVertice("D");

        g.agregarArista("A", "B", new WeightedEdge(2));
        g.agregarArista("A", "C", new WeightedEdge(10));
        g.agregarArista("B", "C", new WeightedEdge(3));
        g.agregarArista("C", "D", new WeightedEdge(1));
        return g;
    }

    public void testTodosLosCaminos() {
        AlgoritmosCaminos<String, WeightedEdge> alg = new AlgoritmosCaminos<>();
        GrafoDirigido<String, WeightedEdge> g = buildGraph();
        
        List<List<String>> caminos = alg.todosLosCaminos(g, "A", "C");
        assertEquals(2, caminos.size()); // A->C y A->B->C
    }

    public void testCaminoMinimoYMaximo() {
        AlgoritmosCaminos<String, WeightedEdge> alg = new AlgoritmosCaminos<>();
        GrafoDirigido<String, WeightedEdge> g = buildGraph();

        List<String> min = alg.caminoMinCosto(g, "A", "C"); // A->B->C (costo 5)
        List<String> max = alg.caminoCritico(g, "A", "C"); // A->C (costo 10)

        assertEquals(3, min.size());
        assertEquals("B", min.get(1));

        assertEquals(2, max.size());
        assertEquals("C", max.get(1));
    }
}
