package ucu.edu.aed.tda.grafo.model.edge;

// Clase sencilla para guardar el peso (o distancia/costo) de una conexión
public class WeightedEdge {
    private final double weight;


    public WeightedEdge(double weight) {
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return String.valueOf(weight);
    }
}
