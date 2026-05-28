package edu.ucu.aed.tda.grafo;

import edu.ucu.aed.tda.grafo.model.IGraph;
import edu.ucu.aed.tda.grafo.model.edge.WeightedEdge;
import edu.ucu.aed.tda.grafo.model.result.IDijkstraResult;
import edu.ucu.aed.tda.grafo.model.result.IFloydWarshallResult;
import edu.ucu.aed.tda.grafo.model.result.Path;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Contrato de tests para cualquier implementación de IDirectGraphAlgorithms.
 * Vértices: String. Dato de arista: W (peso).
 * <p>
 * Extendé esta clase y proveé:
 * - algos(): IDirectGraphAlgorithms
 * - grafoVacio(): IDirectedIGraph<String, W>
 */
public abstract class AbstractDirectedAlgorithmsContractTest {

    /**
     * Implementación a testear.
     */
    protected abstract IGraphAlgorithms algos();

    /**
     * Debe devolver un grafo dirigido vacío, listo para cargar vértices/aristas.
     */
    protected abstract IDirectedIGraph<String, WeightedEdge> grafoVacio();


    // ==== Helpers comunes ====

    /**
     * Comparable que devuelve 0 cuando x.equals(v). Compatible con existeVertice(...) de IGraph.
     */
    protected static Comparable<String> cmpEq(String v) {
        return x -> Objects.equals(x, v) ? 0 : 1;
    }

    protected static void addVertices(IGraph<String, WeightedEdge> g, String... vs) {
        g.agregarVertices(Arrays.asList(vs));
    }

    protected static void addEdge(IDirectedIGraph<String, WeightedEdge> g, String u, String v, double w) {
        assertTrue(g.agregarArista(u, v, new WeightedEdge(w)), "No agregó arista " + u + "->" + v);
    }

    /**
     * Suma de pesos de un camino en el grafo (valida que cada arista exista).
     */
    protected static double costOfPath(IDirectedIGraph<String, WeightedEdge> g, List<String> path) {
        if (path.isEmpty()) return Double.POSITIVE_INFINITY;
        double acc = 0.0;
        for (int i = 0; i + 1 < path.size(); i++) {
            var e = g.obtenerArista(cmpEq(path.get(i)), cmpEq(path.get(i + 1)));
            assertNotNull(e, "Falta arista en camino: " + path.get(i) + "->" + path.get(i + 1));
            acc += e.dato().getWeight();
        }
        return acc;
    }

    // =====================================================================================
    //                                         TESTS
    // =====================================================================================
    @Test
    void obtenerTodosLosCaminos_devuelveRutasDistintas() {
        var g = grafoVacio();
        // Grafo con dos caminos de A a D: A-B-D y A-C-D
        addVertices(g, "A", "B", "C", "D");
        addEdge(g, "A", "B", 1);
        addEdge(g, "B", "D", 1);
        addEdge(g, "A", "C", 1);
        addEdge(g, "C", "D", 1);

        List<Path<String>> paths = algos().obtenerTodosLosCaminos("A", "D", g);
        // Para comparar sin importar orden:
        Set<String> norm = new HashSet<>();
        for (Path<String> p : paths) norm.add(String.join(">", p.getPath()));

        assertEquals(Set.of("A>B>D", "A>C>D"), norm);
    }

    @Test
    void dijkstra_caminoMinimo_y_costos() {
        var g = grafoVacio();
        // A->B (1), A->C (5), B->C (1), B->D (2), C->D (1)
        addVertices(g, "A", "B", "C", "D");
        addEdge(g, "A", "B", 1);
        addEdge(g, "A", "C", 5);
        addEdge(g, "B", "C", 1);
        addEdge(g, "B", "D", 2);
        addEdge(g, "C", "D", 1);

        IDijkstraResult<String> r = algos().dijkstra(cmpEq("A"), g);

        var pC = r.getPath("C");
        assertEquals(List.of("A", "B", "C"), pC, "Camino mínimo A->C");
        assertEquals(2.0, costOfPath(g, pC), 1e-9);

        var pD = r.getPath("D");
        assertEquals(List.of("A", "B", "D"), pD, "Camino mínimo A->D");
        assertEquals(3.0, costOfPath(g, pD), 1e-9);

        assertEquals(List.of("A"), r.getPath("A"), "El origen se reconstruye a sí mismo");
        assertTrue(r.getPath("Z").isEmpty(), "Destino inexistente => camino vacío");
    }

    @Test
    void dijkstra_pesoNegativo_debeRechazarse() {
        var g = grafoVacio();
        addVertices(g, "A", "B");
        addEdge(g, "A", "B", -1.0);
        assertThrows(IllegalArgumentException.class,
                () -> algos().dijkstra(cmpEq("A"), g),
                "Dijkstra no debe aceptar pesos negativos");
    }

    @Test
    void floyd_distancias_y_reconstruccion() {
        var g = grafoVacio();
        // A->B 2, B->C 2, A->C 10, C->D 1, B->D 5
        addVertices(g, "A", "B", "C", "D");
        addEdge(g, "A", "B", 2);
        addEdge(g, "B", "C", 2);
        addEdge(g, "A", "C", 10);
        addEdge(g, "C", "D", 1);
        addEdge(g, "B", "D", 5);

        IFloydWarshallResult<String> fw = algos().floyd(g);

        assertEquals(4.0, fw.getCost("A", "C"), 1e-9, "A->B->C mejora de 10 a 4");
        assertEquals(5.0, fw.getCost("A", "D"), 1e-9, "A->B->C->D = 5");
        assertEquals(0.0, fw.getCost("B", "B"), 1e-9, "Diagonal debe ser 0");

        assertEquals(5, fw.getCost("A", "D"), "Path óptimo A->D");
        assertEquals(Double.POSITIVE_INFINITY, fw.getCost("D", "A"), 1e-9, "D no alcanza A");
        assertEquals(List.of(), fw.getPath("D", "A"), "Sin camino D->A");


        PrettyGrid.printGraphMatrix(g);
    }

    @Test
    void warshall_cierreTransitivo_alcanzabilidad() {
        var g = grafoVacio();
        // A->B, B->C (sin C->A)
        addVertices(g, "A", "B", "C", "D");
        addEdge(g, "A", "B", 1);
        addEdge(g, "B", "C", 1);

        IFloydWarshallResult<String> w = algos().warshall(g);

        // Dos formas válidas de verificar alcanzabilidad:
        // 1) path(u,v) no vacío; 2) dist[u][v] < +∞
        boolean aReachC = w.connected("A", "C");
        boolean cReachA = w.connected("C", "A");

        assertTrue(aReachC, "A debe alcanzar C");
        assertFalse(cReachA, "C NO debe alcanzar A");
        assertFalse(w.connected("A", "D"), "A NO debe alcanzar D");
    }

    @Test
    void centro_y_excentricidad() {
        var g = grafoVacio();
        // Grafo fuertemente conexo:
        // A<->B(1), B<->C(1), C<->D(2), A<->D(4)
        addVertices(g, "A", "B", "C", "D");
        addEdge(g, "A", "B", 1);
        addEdge(g, "B", "A", 1);
        addEdge(g, "B", "C", 1);
        addEdge(g, "C", "B", 1);
        addEdge(g, "C", "D", 2);
        addEdge(g, "D", "C", 2);
        addEdge(g, "A", "D", 4);
        addEdge(g, "D", "A", 4);

        String center = algos().obtenerCentroGrafo(g);
        assertTrue(Set.of("B", "C").contains(center), "Centro esperado B o C");

        double excA = algos().obtenerExcentricidad(g, cmpEq("A"));
        double excB = algos().obtenerExcentricidad(g, cmpEq("B"));
        double excC = algos().obtenerExcentricidad(g, cmpEq("C"));
        double excD = algos().obtenerExcentricidad(g, cmpEq("D"));

        // B y C deben ser los de menor excentricidad
        assertTrue(excB <= excA && excB <= excD);
        assertTrue(excC <= excA && excC <= excD);
//        assertEquals(excB, excC, 1e-9);
    }

    @Test
    void dfs_y_bfs() {
        var g = grafoVacio();
        // A->B, A->C, B->D, C->D (insertar B antes que C para orden determinista)
        addVertices(g, "A", "B", "C", "D");
        addEdge(g, "A", "B", 1);
        addEdge(g, "A", "C", 1);
        addEdge(g, "B", "D", 1);
        addEdge(g, "C", "D", 1);

        List<String> dfs = new ArrayList<>();
        algos().recorridoEnProfundidad(g, cmpEq("A"), dfs::add);
        assertEquals("A", dfs.get(0));
        assertTrue(dfs.containsAll(List.of("B", "C", "D")));
        assertEquals(4, dfs.size());

        List<String> bfs = new ArrayList<>();
        algos().recorridoEnAmplitud(g, cmpEq("A"), bfs::add);
        // Esperado con orden de inserción: A, B, C, D
        assertEquals(List.of("A", "B", "C", "D"), bfs);
    }

    @Test
    void defaults_de_recorridos() {
        var g = grafoVacio();
        addVertices(g, "A", "B", "C");
        addEdge(g, "A", "B", 1);
        addEdge(g, "B", "C", 1);

        List<String> dfsAll = new ArrayList<>();
        algos().recorridoEnProfundidad(g, dfsAll::add);
        // Debe haber visitado todos los vértices
        assertTrue(dfsAll.containsAll(List.of("A", "B", "C")));

        List<String> bfsFromFirst = new ArrayList<>();
        algos().recorridoEnAmplitud(g, bfsFromFirst::add);
        // Arranca desde el primer vértice de vertices()
        assertFalse(bfsFromFirst.isEmpty());
    }
}
