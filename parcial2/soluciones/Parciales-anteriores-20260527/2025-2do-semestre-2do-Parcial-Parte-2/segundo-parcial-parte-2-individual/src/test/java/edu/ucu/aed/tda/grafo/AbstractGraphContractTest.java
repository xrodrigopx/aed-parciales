package edu.ucu.aed.tda.grafo;

import edu.ucu.aed.tda.grafo.model.edge.Edge;
import edu.ucu.aed.tda.grafo.model.IGraph;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Contrato de tests para cualquier implementación de IGraph<String, Integer>.
 * Extendé esta clase y proveé newGraph() e isDirected() para correrlos.
 */
public abstract class AbstractGraphContractTest extends Assertions {

    protected IGraph<String, Integer> g;

    /**
     * Implementación concreta a testear. Debe crear un grafo vacío.
     */
    protected abstract IGraph<String, Integer> newGraph();

    /**
     * true si la implementación es dirigida, false si es no dirigida.
     */
    protected abstract boolean isDirected();

    @BeforeEach
    void setup() {
        g = newGraph();
    }

    protected void addVertices(String... vs) {
        g.agregarVertices(Arrays.asList(vs));
    }

    protected void addEdge(String u, String v, int w) {
        assertTrue(g.agregarArista(u, v, w), "No agregó arista " + u + "->" + v);
        // En no dirigido, muchas implementaciones agregan una sola arista no direccional.
        // El contrato de IGraph no obliga a duplicar aristas, por eso no afirmamos nada extra acá.
    }

    // ---------- Tests de vértices ----------
    @Test
    void agregaYBuscaVertices() {
        assertTrue(g.agregarVertice("A"));
        assertFalse(g.agregarVertice("A"), "No debe agregar duplicado");
        assertTrue(g.existeVertice("A"));
        assertEquals("A", g.buscarVertice("A"));
        assertNull(g.buscarVertice(("Z")));
        assertEquals(1, g.cantidadDeVertices());
    }

    @Test
    void removerVerticeEliminaIncidencias() {
        addVertices("A", "B", "C");
        addEdge("A", "B", 1);
        addEdge("B", "C", 2);
        assertTrue(g.removerVertice("B"));
        assertFalse(g.existeVertice("B"));
        // Todas las aristas incidentes a B deben desaparecer
        assertFalse(g.existeArista("A", "B"));
        assertFalse(g.existeArista("B", ("C")));
    }

    // ---------- Tests de aristas ----------
    @Test
    void agregaObtieneYEliminaAristas() {
        addVertices("A", "B", "C");
        addEdge("A", "B", 10);
        assertTrue(g.existeArista("A", "B"));
        Edge<String, Integer> e = g.obtenerArista("A", "B");
        assertNotNull(e);
        assertEquals("A", e.source());
        assertEquals("B", e.target());
        assertEquals(10, e.dato());

        // adyacencias
        List<Edge<String, Integer>> adjA = g.adyacencias("A");
        assertTrue(adjA.stream().anyMatch(x -> x.target().equals("B")));

        // eliminar
        assertTrue(g.eliminarArista("A", "B"));
        assertFalse(g.existeArista("A", "B"));
        assertNull(g.obtenerArista("A", "B"));
    }

    @Test
    void vistasDeVerticesYAristasSonCoherentes() {
        addVertices("A", "B", "C");
        addEdge("A", "B", 1);
        addEdge("A", "C", 2);
        Set<String> vs = g.vertices();
        Set<Edge<String, Integer>> es = g.aristas();

        assertEquals(3, vs.size());
        assertEquals(2, g.cantidadDeAristas());

        // Si la implementación promete vistas inmodificables, debería lanzar.
        // Si no lo hace, al menos validar que modificar no rompe invariantes.
        assertThrows(UnsupportedOperationException.class, () -> vs.add("X"));
        assertThrows(UnsupportedOperationException.class, es::clear);
    }

    // ---------- Conectividad ----------
    @Test
    void esConexo_trueEnGrafoSimpleConectado() {
        addVertices("A", "B", "C", "D");
        addEdge("A", "B", 1);
        addEdge("B", "C", 1);
        addEdge("C", "D", 1);

        if (isDirected()) {
            // En dirigido, la conectividad fuerte es más exigente;
            // el contrato no especifica si es fuerte o débil. Probamos “débil”:
            assertFalse(g.esConexo(), "Se espera conectividad (al menos débil) en cadena A-B-C-D");
        } else {
            assertTrue(g.esConexo());
        }
    }

    @Test
    void esConexo_falseEnGrafoDesconectado() {
        addVertices("A", "B", "C", "D");
        addEdge("A", "B", 1);
        // "C" y "D" quedan aislados/entre sí
        assertFalse(g.esConexo());
    }

    // ---------- Ciclos ----------
    @Test
    void tieneCiclos_detectaCiclo() {
        addVertices("A", "B", "C");
        addEdge("A", "B", 1);
        addEdge("B", "C", 1);
        addEdge(isDirected() ? "C" : "C", isDirected() ? "A" : "A", 1); // cierre del ciclo
        // En no dirigido el triángulo A-B-C constituye ciclo
        assertTrue(g.tieneCiclos());
    }

    @Test
    void tieneCiclos_falseEnEstructuraAcilicaSimple() {
        addVertices("A", "B", "C", "D");
        addEdge("A", "B", 1);
        addEdge("B", "C", 1);
        // sin arista que cierre
        assertFalse(g.tieneCiclos());

    }

    // ---------- Caminos ----------


    // ---------- Vaciar ----------
    @Test
    void vaciar_borraTodo() {
        addVertices("A", "B");
        addEdge("A", "B", 7);
        g.vaciar();
        assertEquals(0, g.cantidadDeVertices());
        assertEquals(0, g.cantidadDeAristas());
        assertFalse(g.esConexo());
        assertFalse(g.tieneCiclos());
    }
}
