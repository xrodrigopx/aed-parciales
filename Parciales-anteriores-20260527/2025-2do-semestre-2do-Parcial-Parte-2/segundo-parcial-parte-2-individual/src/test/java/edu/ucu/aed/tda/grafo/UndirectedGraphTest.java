package edu.ucu.aed.tda.grafo;

import edu.ucu.aed.tda.grafo.impl.UndirectedGraph;
import edu.ucu.aed.tda.grafo.model.IGraph;
import org.junit.jupiter.api.Test;

public class UndirectedGraphTest extends AbstractGraphContractTest {

    @Override
    protected IGraph<String, Integer> newGraph() {
        return new UndirectedGraph<>();
    }

    @Override
    protected boolean isDirected() {
        return false;
    }

    @Test
    public void basic1() {
        IGraph<Integer, Integer> graph = new UndirectedGraph<>();
        graph.agregarArista(1, 2, 0);
        graph.agregarArista(2, 3, 0);
        graph.agregarArista(3, 1, 0);

        assertTrue(graph.esConexo());
        assertTrue(graph.tieneCiclos());
    }

    @Test
    public void basic2() {
        IGraph<Integer, Integer> graph = new UndirectedGraph<>();
        graph.agregarArista(1, 2, 0);
        graph.agregarArista(2, 3, 0);
        assertTrue(graph.esConexo());
        assertFalse(graph.tieneCiclos());
    }

    @Test
    public void basic3() {
        IGraph<Integer, Integer> graph = new UndirectedGraph<>();
        graph.agregarArista(1, 2, 0);
        graph.agregarArista(2, 3, 0);
        graph.agregarArista(3, 1, 0);

        assertEquals(3, graph.vertices().size());
        assertEquals(3, graph.aristas().size());
        graph.removerVertice(1);
        assertEquals(2, graph.vertices().size());

        assertEquals(1, graph.aristas().size());
    }
}
