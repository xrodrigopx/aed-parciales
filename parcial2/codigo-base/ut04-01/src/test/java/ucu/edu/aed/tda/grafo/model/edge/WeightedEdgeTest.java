package ucu.edu.aed.tda.grafo.model.edge;

import junit.framework.TestCase;

public class WeightedEdgeTest extends TestCase {

    public void testWeightedEdge() {
        WeightedEdge edge = new WeightedEdge(15.5);
        
        assertEquals(15.5, edge.getWeight());
        assertEquals("15.5", edge.toString());
    }
}
