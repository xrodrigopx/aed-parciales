package ucu.edu.aed.tda.grafo.impl;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class FloydWarshallResultTest extends TestCase {

    // Vértices: A=0, B=1, C=2
    // Aristas directas: A->B=2, B->C=3, A->C=10 (mejorado a 5 vía B)
    // C->A no existe
    private FloydWarshallResult<String> buildResult() {
        List<String> vertices = new ArrayList<>();
        vertices.add("A");
        vertices.add("B");
        vertices.add("C");

        int n = 3;
        double[][] costos = new double[n][n];
        int[][] predecesores = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                costos[i][j] = (i == j) ? 0.0 : Double.MAX_VALUE;
                predecesores[i][j] = -1;
            }
        }

        // Aristas: A->B=2, B->C=3, A->C=5 (vía B), no hay de C a ninguno
        costos[0][1] = 2.0;
        costos[1][2] = 3.0;
        costos[0][2] = 5.0;
        predecesores[0][2] = 1; // A->C pasa por B

        return new FloydWarshallResult<>(costos, predecesores, vertices);
    }

    // 1. getCost de arista directa
    public void testGetCostDirecto() {
        FloydWarshallResult<String> resultado = buildResult();
        assertEquals(2.0, resultado.getCost("A", "B"));
        assertEquals(3.0, resultado.getCost("B", "C"));
    }

    // 2. getCost de camino con intermedio
    public void testGetCostIndirecto() {
        FloydWarshallResult<String> resultado = buildResult();
        assertEquals(5.0, resultado.getCost("A", "C"));
    }

    // 3. getCost sin camino devuelve MAX_VALUE
    public void testGetCostSinCamino() {
        FloydWarshallResult<String> resultado = buildResult();
        // No hay aristas desde C hacia A o B
        assertEquals(Double.MAX_VALUE, resultado.getCost("C", "A"));
    }

    // 4. getPath con un nodo intermedio reconstruye [A, B, C]
    public void testGetPathConIntermedio() {
        FloydWarshallResult<String> resultado = buildResult();
        List<String> camino = resultado.getPath("A", "C");

        assertEquals(3, camino.size());
        assertEquals("A", camino.get(0));
        assertEquals("B", camino.get(1));
        assertEquals("C", camino.get(2));
    }

    // 5. connected distingue camino existente de inexistente
    public void testConnected() {
        FloydWarshallResult<String> resultado = buildResult();

        assertTrue(resultado.connected("A", "B"));
        assertTrue(resultado.connected("A", "C"));
        assertFalse(resultado.connected("C", "A"));
    }
}
