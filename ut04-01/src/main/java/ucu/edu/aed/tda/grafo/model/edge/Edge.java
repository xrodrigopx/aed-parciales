package ucu.edu.aed.tda.grafo.model.edge;

// Interfaz base que representa una conexión (arista) entre dos nodos
public interface Edge<V, D> {
    V source();

    V target();

    D dato();

    boolean directed();
}