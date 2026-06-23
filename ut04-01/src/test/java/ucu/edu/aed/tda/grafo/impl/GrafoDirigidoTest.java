package ucu.edu.aed.tda.grafo.impl;

import junit.framework.TestCase;
import ucu.edu.aed.tda.grafo.model.edge.Edge;

import java.util.List;
import java.util.Set;

public class GrafoDirigidoTest extends TestCase {

    // 1. Agregar vértice: duplicado devuelve false, buscarVertice lo encuentra
    public void testAgregarVertice() {
        GrafoDirigido<String, Integer> grafo = new GrafoDirigido<>();
        boolean primerAgregado = grafo.agregarVertice("A");
        boolean duplicado = grafo.agregarVertice("A");

        assertTrue(primerAgregado);
        assertFalse(duplicado);
        assertEquals(1, grafo.vertices().size());
        assertNotNull(grafo.buscarVertice(grafo.construirComparable("A")));
    }

    // 2. Agregar arista, verificar que existe y aparece en adyacencias
    public void testAgregarArista() {
        GrafoDirigido<String, Integer> grafo = new GrafoDirigido<>();
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        boolean agregada = grafo.agregarArista("A", "B", 5);

        assertTrue(agregada);
        assertTrue(grafo.existeArista(grafo.construirComparable("A"), grafo.construirComparable("B")));
        assertFalse(grafo.existeArista(grafo.construirComparable("B"), grafo.construirComparable("A")));

        List<Edge<String, Integer>> ady = grafo.adyacencias(grafo.construirComparable("A"));
        assertEquals(1, ady.size());
        assertEquals("B", ady.get(0).target());
    }

    // 3. Eliminar arista: deja de existir pero el vértice sigue en el grafo
    public void testEliminarArista() {
        GrafoDirigido<String, Integer> grafo = new GrafoDirigido<>();
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarArista("A", "B", 10);

        boolean eliminada = grafo.eliminarArista(grafo.construirComparable("A"), grafo.construirComparable("B"));

        assertTrue(eliminada);
        assertFalse(grafo.existeArista(grafo.construirComparable("A"), grafo.construirComparable("B")));
        assertTrue(grafo.vertices().contains("A"));
        assertTrue(grafo.vertices().contains("B"));
    }

    // 4. successors y predecessors
    public void testSuccessorsYPredecessors() {
        GrafoDirigido<String, Integer> grafo = new GrafoDirigido<>();
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarVertice("C");
        grafo.agregarArista("A", "B", 1);
        grafo.agregarArista("B", "C", 2);

        Set<String> sucesoresA = grafo.successors(grafo.construirComparable("A"));
        Set<String> predecesoresC = grafo.predecessors(grafo.construirComparable("C"));

        assertEquals(1, sucesoresA.size());
        assertTrue(sucesoresA.contains("B"));
        assertEquals(1, predecesoresC.size());
        assertTrue(predecesoresC.contains("B"));
    }

    // 5. tieneCiclos: grafo lineal no tiene, grafo con ciclo sí
    public void testTieneCiclos() {
        GrafoDirigido<String, Integer> sinCiclo = new GrafoDirigido<>();
        sinCiclo.agregarVertice("A");
        sinCiclo.agregarVertice("B");
        sinCiclo.agregarVertice("C");
        sinCiclo.agregarArista("A", "B", 1);
        sinCiclo.agregarArista("B", "C", 1);

        GrafoDirigido<String, Integer> conCiclo = new GrafoDirigido<>();
        conCiclo.agregarVertice("A");
        conCiclo.agregarVertice("B");
        conCiclo.agregarVertice("C");
        conCiclo.agregarArista("A", "B", 1);
        conCiclo.agregarArista("B", "C", 1);
        conCiclo.agregarArista("C", "A", 1);

        assertFalse(sinCiclo.tieneCiclos());
        assertTrue(conCiclo.tieneCiclos());
    }
}
