package ucu.edu.aed.tda.grafo.impl;

import junit.framework.TestCase;
import ucu.edu.aed.tda.grafo.model.edge.Edge;

import java.util.List;
import java.util.Set;

public class GrafoNoDirigidoTest extends TestCase {

    // 1. agregarArista guarda la arista en ambos sentidos
    public void testAgregarAristaEsSimetrica() {
        GrafoNoDirigido<String, Integer> grafo = new GrafoNoDirigido<>();
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarArista("A", "B", 7);

        List<Edge<String, Integer>> adyA = grafo.adyacencias(grafo.construirComparable("A"));
        List<Edge<String, Integer>> adyB = grafo.adyacencias(grafo.construirComparable("B"));

        assertEquals(1, adyA.size());
        assertEquals(1, adyB.size());
        // El vértice criterio aparece como source() en cada lista
        assertEquals("A", adyA.get(0).source());
        assertEquals("B", adyA.get(0).target());
        assertEquals("B", adyB.get(0).source());
        assertEquals("A", adyB.get(0).target());
    }

    // 2. eliminarArista la quita de las dos listas
    public void testEliminarAristaEsSimetrica() {
        GrafoNoDirigido<String, Integer> grafo = new GrafoNoDirigido<>();
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarArista("A", "B", 3);

        grafo.eliminarArista(grafo.construirComparable("A"), grafo.construirComparable("B"));

        assertEquals(0, grafo.adyacencias(grafo.construirComparable("A")).size());
        assertEquals(0, grafo.adyacencias(grafo.construirComparable("B")).size());
    }

    // 3. aristas() no duplica pares: (A,B) y (B,A) cuentan como una sola arista
    public void testAristasNoDuplicadas() {
        GrafoNoDirigido<String, Integer> grafo = new GrafoNoDirigido<>();
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarVertice("C");
        grafo.agregarArista("A", "B", 1);
        grafo.agregarArista("B", "C", 2);

        Set<Edge<String, Integer>> aristas = grafo.aristas();
        assertEquals(2, aristas.size());
    }

    // 4. esConexo: grafo conexo vs grafo con vértice aislado
    public void testEsConexo() {
        GrafoNoDirigido<String, Integer> conexo = new GrafoNoDirigido<>();
        conexo.agregarVertice("A");
        conexo.agregarVertice("B");
        conexo.agregarVertice("C");
        conexo.agregarArista("A", "B", 1);
        conexo.agregarArista("B", "C", 1);

        GrafoNoDirigido<String, Integer> desconexo = new GrafoNoDirigido<>();
        desconexo.agregarVertice("A");
        desconexo.agregarVertice("B");
        desconexo.agregarVertice("Z");  // aislado
        desconexo.agregarArista("A", "B", 1);

        assertTrue(conexo.esConexo());
        assertFalse(desconexo.esConexo());
    }

    // 5. tieneCiclos: triángulo tiene ciclo, árbol no
    public void testTieneCiclos() {
        GrafoNoDirigido<String, Integer> arbol = new GrafoNoDirigido<>();
        arbol.agregarVertice("A");
        arbol.agregarVertice("B");
        arbol.agregarVertice("C");
        arbol.agregarArista("A", "B", 1);
        arbol.agregarArista("B", "C", 1);

        GrafoNoDirigido<String, Integer> triangulo = new GrafoNoDirigido<>();
        triangulo.agregarVertice("A");
        triangulo.agregarVertice("B");
        triangulo.agregarVertice("C");
        triangulo.agregarArista("A", "B", 1);
        triangulo.agregarArista("B", "C", 1);
        triangulo.agregarArista("C", "A", 1);

        assertFalse(arbol.tieneCiclos());
        assertTrue(triangulo.tieneCiclos());
    }
}
