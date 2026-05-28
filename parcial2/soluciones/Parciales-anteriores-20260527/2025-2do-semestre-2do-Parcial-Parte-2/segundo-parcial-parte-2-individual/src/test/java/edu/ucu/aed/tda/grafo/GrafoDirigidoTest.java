package edu.ucu.aed.tda.grafo;

import edu.ucu.aed.tda.grafo.impl.DirectedGraph;
import edu.ucu.aed.tda.grafo.model.IGraph;
import org.junit.jupiter.api.Tag;

@Tag("directed")
public class GrafoDirigidoTest extends AbstractGraphContractTest {
    @Override
    protected IGraph<String, Integer> newGraph() {
        return new DirectedGraph<>();
    }
    @Override
    protected boolean isDirected() { return true; }
}
