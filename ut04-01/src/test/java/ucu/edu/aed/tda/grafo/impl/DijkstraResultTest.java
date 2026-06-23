package ucu.edu.aed.tda.grafo.impl;

import junit.framework.TestCase;

import java.util.HashMap;
import java.util.List;

public class DijkstraResultTest extends TestCase {

    // Grafo: A -2-> B -3-> C; D no alcanzable
    // Origen: A
    private DijkstraResult<String> buildResult() {
        HashMap<String, Double> distancias = new HashMap<>();
        distancias.put("A", 0.0);
        distancias.put("B", 2.0);
        distancias.put("C", 5.0);
        distancias.put("D", Double.MAX_VALUE);

        HashMap<String, String> predecesores = new HashMap<>();
        predecesores.put("A", null);
        predecesores.put("B", "A");
        predecesores.put("C", "B");
        predecesores.put("D", null);

        return new DijkstraResult<>(distancias, predecesores);
    }

    // 1. getCost retorna el costo correcto para vértice alcanzable
    public void testGetCostAlcanzable() {
        DijkstraResult<String> resultado = buildResult();
        assertEquals(2.0, resultado.getCost("B"));
        assertEquals(5.0, resultado.getCost("C"));
        assertEquals(0.0, resultado.getCost("A"));
    }

    // 2. getCost retorna MAX_VALUE para vértice no alcanzable
    public void testGetCostNoAlcanzable() {
        DijkstraResult<String> resultado = buildResult();
        assertEquals(Double.MAX_VALUE, resultado.getCost("D"));
    }

    // 3. getPath reconstruye el camino completo desde origen
    public void testGetPathCompleto() {
        DijkstraResult<String> resultado = buildResult();
        List<String> camino = resultado.getPath("C");

        assertEquals(3, camino.size());
        assertEquals("A", camino.get(0));
        assertEquals("B", camino.get(1));
        assertEquals("C", camino.get(2));
    }

    // 4. getPath retorna lista vacía para vértice no alcanzable
    public void testGetPathNoAlcanzable() {
        DijkstraResult<String> resultado = buildResult();
        List<String> camino = resultado.getPath("D");

        assertNotNull(camino);
        assertEquals(0, camino.size());
    }

    // 5. connected distingue entre alcanzable y no alcanzable
    public void testConnected() {
        DijkstraResult<String> resultado = buildResult();

        assertTrue(resultado.connected("B"));
        assertTrue(resultado.connected("C"));
        assertFalse(resultado.connected("D"));
    }
}
