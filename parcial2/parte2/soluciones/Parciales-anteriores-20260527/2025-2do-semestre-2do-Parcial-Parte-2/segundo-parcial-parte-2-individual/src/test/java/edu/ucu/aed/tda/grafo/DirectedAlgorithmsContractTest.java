package edu.ucu.aed.tda.grafo;

import edu.ucu.aed.tda.grafo.impl.GraphAlgorithms;
import edu.ucu.aed.tda.grafo.impl.DirectedGraph;
import edu.ucu.aed.tda.grafo.model.edge.WeightedEdge;

public class DirectedAlgorithmsContractTest extends AbstractDirectedAlgorithmsContractTest{
    @Override
    protected IGraphAlgorithms algos() {
        return new GraphAlgorithms();
    }

    @Override
    protected IDirectedIGraph<String, WeightedEdge> grafoVacio() {
        return new DirectedGraph<>();
    }
}
