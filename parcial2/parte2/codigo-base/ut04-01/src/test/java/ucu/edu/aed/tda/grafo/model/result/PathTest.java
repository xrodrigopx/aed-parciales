package ucu.edu.aed.tda.grafo.model.result;

import junit.framework.TestCase;
import java.util.Arrays;
import java.util.List;

public class PathTest extends TestCase {

    public void testPath() {
        List<String> nodos = Arrays.asList("A", "B", "C");
        Path<String> camino = new Path<>(nodos, 25.0);
        
        assertEquals(25.0, camino.getCost());
        assertEquals(3, camino.getPath().size());
        assertEquals("B", camino.getPath().get(1));
    }
}
