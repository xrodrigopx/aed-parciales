package ucu.edu.aed.ej03;

import ucu.edu.aed.tda.grafo.impl.AlgoritmosGrafoDirigido;
import ucu.edu.aed.tda.grafo.impl.GrafoDirigido;
import ucu.edu.aed.tda.grafo.model.edge.WeightedEdge;
import ucu.edu.aed.tda.grafo.model.result.IFloydWarshallResult;

import java.util.List;

// Clase de prueba rápida para verificar que el algoritmo de Floyd funcione correctamente
public class MainFloyd {

    public static void main(String[] args) {
        GrafoDirigido<String, WeightedEdge> grafo = new GrafoDirigido<>();

        grafo.agregarVertice("Montevideo");
        grafo.agregarVertice("Colonia");
        grafo.agregarVertice("Buenos_Aires");
        grafo.agregarVertice("Punta_del_Este");

        grafo.agregarArista("Montevideo",    "Colonia",         new WeightedEdge(180));
        grafo.agregarArista("Montevideo",    "Punta_del_Este",  new WeightedEdge(140));
        grafo.agregarArista("Colonia",       "Buenos_Aires",    new WeightedEdge(50));
        grafo.agregarArista("Buenos_Aires",  "Montevideo",      new WeightedEdge(230));
        grafo.agregarArista("Punta_del_Este","Colonia",         new WeightedEdge(220));

        AlgoritmosGrafoDirigido algoritmos = new AlgoritmosGrafoDirigido();
        IFloydWarshallResult<String> resultado = algoritmos.floyd(grafo);

        System.out.println("=== Distancias minimas (Floyd) ===");

        String[] ciudades = {"Montevideo", "Colonia", "Buenos_Aires", "Punta_del_Este"};

        for (String origen : ciudades) {
            for (String destino : ciudades) {
                if (!origen.equals(destino)) {
                    boolean hayConexion = resultado.connected(origen, destino);
                    if (hayConexion) {
                        double costo = resultado.getCost(origen, destino);
                        System.out.println(origen + " -> " + destino + ": " + costo + " km");
                    } else {
                        System.out.println(origen + " -> " + destino + ": sin conexion");
                    }
                }
            }
        }

        System.out.println();
        System.out.println("=== Itinerarios ===");

        for (String origen : ciudades) {
            for (String destino : ciudades) {
                if (!origen.equals(destino)) {
                    boolean hayConexion = resultado.connected(origen, destino);
                    if (hayConexion) {
                        List<String> camino = resultado.getPath(origen, destino);
                        System.out.print(origen + " -> " + destino + ": ");
                        for (int i = 0; i < camino.size(); i++) {
                            if (i > 0) {
                                System.out.print(" -> ");
                            }
                            System.out.print(camino.get(i));
                        }
                        System.out.println();
                    }
                }
            }
        }
    }
}
