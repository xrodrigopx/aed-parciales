package ucu.edu.aed.tda.grafo.impl;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class WarshallResultTest extends TestCase {

    // 0=A, 1=B, 2=C

    private WarshallResult<String> buildSimple() {
        List<String> vertices = new ArrayList<>();
        vertices.add("A");
        vertices.add("B");
        vertices.add("C");

        // A -> B -> C (transitivo esperado ya resuelto por Warshall)
        int[][] matriz = new int[][] {
                {0, 1, 1}, // A conecta con B y C
                {0, 0, 1}, // B conecta con C
                {0, 0, 0}
        };

        return new WarshallResult<>(matriz, vertices);
    }

    // 1. conectividad directa
    public void testConnectedDirecto() {
        WarshallResult<String> resultado = buildSimple();

        assertTrue(resultado.connected("A", "B"));
        assertTrue(resultado.connected("B", "C"));
    }

    // 2. conectividad transitiva (clave de Warshall)
    public void testConnectedTransitivo() {
        WarshallResult<String> resultado = buildSimple();

        // A llega a C aunque no sea directa
        assertTrue(resultado.connected("A", "C"));
    }

    // 3. no conectados
    public void testNotConnected() {
        WarshallResult<String> resultado = buildSimple();

        assertFalse(resultado.connected("C", "A"));
        assertFalse(resultado.connected("B", "A"));
    }

    // 4. vértice inexistente
    public void testVerticeInexistente() {
        WarshallResult<String> resultado = buildSimple();

        assertFalse(resultado.connected("A", "Z"));
        assertFalse(resultado.connected("Z", "A"));
    }

    // 5. matriz sin conexiones
    public void testGrafoVacio() {
        List<String> vertices = new ArrayList<>();
        vertices.add("A");
        vertices.add("B");
        vertices.add("C");

        int[][] matriz = new int[][] {
                {0, 0, 0},
                {0, 0, 0},
                {0, 0, 0}
        };

        WarshallResult<String> resultado = new WarshallResult<>(matriz, vertices);

        assertFalse(resultado.connected("A", "B"));
        assertFalse(resultado.connected("A", "C"));
        assertFalse(resultado.connected("B", "C"));
    }

    // 6. self-loop
    public void testSelfLoop() {
        List<String> vertices = new ArrayList<>();
        vertices.add("A");

        int[][] matriz = new int[][] {
                {1}
        };

        WarshallResult<String> resultado = new WarshallResult<>(matriz, vertices);

        assertTrue(resultado.connected("A", "A"));
    }

    // 7. caso mixto más complejo
    public void testGrafoComplejo() {
        List<String> vertices = new ArrayList<>();
        vertices.add("A");
        vertices.add("B");
        vertices.add("C");
        vertices.add("D");

        int[][] matriz = new int[][] {
                {0, 1, 1, 1}, // A -> B, C, D
                {0, 0, 1, 1}, // B -> C, D
                {0, 0, 0, 1}, // C -> D
                {0, 0, 0, 0}
        };

        WarshallResult<String> resultado = new WarshallResult<>(matriz, vertices);

        assertTrue(resultado.connected("A", "D")); // transitivo
        assertTrue(resultado.connected("A", "C"));
        assertTrue(resultado.connected("B", "D"));

        assertFalse(resultado.connected("D", "A"));
    }
}