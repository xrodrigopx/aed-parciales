package ucu.edu.aed.tda.grafo.model.edge;

import junit.framework.TestCase;

public class UndirectedEdgeTest extends TestCase {

    public void testUndirectedEdgeCreation() {
        UndirectedEdge<String, Integer> edge = new UndirectedEdge<>("A", "B", 5);
        
        assertEquals("A", edge.source());
        assertEquals("B", edge.target());
        assertEquals(Integer.valueOf(5), edge.dato());
        assertFalse(edge.directed());
    }

    public void testEqualsSimetrico() {
        UndirectedEdge<String, Integer> edge1 = new UndirectedEdge<>("A", "B", 5);
        UndirectedEdge<String, Integer> edge2 = new UndirectedEdge<>("B", "A", 5);

        // Al ser no dirigido, (A,B) debería ser igual a (B,A)
        assertEquals(edge1, edge2);
        assertEquals(edge1.hashCode(), edge2.hashCode());
    }
}
