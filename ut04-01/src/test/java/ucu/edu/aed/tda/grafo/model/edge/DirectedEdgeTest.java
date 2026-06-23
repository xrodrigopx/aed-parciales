package ucu.edu.aed.tda.grafo.model.edge;

import junit.framework.TestCase;

public class DirectedEdgeTest extends TestCase {

    public void testDirectedEdgeCreation() {
        DirectedEdge<String, Integer> edge = new DirectedEdge<>("A", "B", 10);
        
        assertEquals("A", edge.source());
        assertEquals("B", edge.target());
        assertEquals(Integer.valueOf(10), edge.dato());
        assertTrue(edge.directed());
    }

    public void testEqualsAndHashCode() {
        DirectedEdge<String, Integer> edge1 = new DirectedEdge<>("A", "B", 10);
        DirectedEdge<String, Integer> edge2 = new DirectedEdge<>("A", "B", 10);
        DirectedEdge<String, Integer> edge3 = new DirectedEdge<>("B", "A", 10);

        assertEquals(edge1, edge2);
        assertFalse(edge1.equals(edge3));
        assertEquals(edge1.hashCode(), edge2.hashCode());
    }
}
