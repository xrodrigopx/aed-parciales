# Solución: Dijkstra — Ruta óptima (tiempo/costo mínimo)

**Apareció en:** 2024-2S Parte 2 Ej 1 — `consultaTiempoMinimo`; 2025-1S Parte 2 Ej 1 (A→E); 2025-1S Parte 2 Ej 3 — ruta por camión  
**Patrón:** "dado origen y destino, calcular el costo/tiempo mínimo y el camino a seguir"

---

## Lenguaje natural

Dado un grafo dirigido con pesos (tiempos entre estaciones), encontrar el camino de menor costo desde un origen hasta un destino.

El algoritmo de Dijkstra mantiene un mapa de distancias conocidas (inicialmente infinitas) y un mapa de predecesores. En cada iteración, selecciona el vértice no visitado con la menor distancia conocida, lo marca como visitado y actualiza las distancias de sus vecinos si se puede mejorar pasando por él.

Al terminar, si la distancia al destino sigue siendo infinita, no hay camino. Si no, se recupera el camino recorriendo los predecesores hacia atrás.

---

## Precondición

- El grafo tiene vértices y aristas con pesos ≥ 0.
- `origen` y `destino` son nombres de estaciones que existen en el grafo.

## Postcondición

- Retorna el tiempo mínimo de viaje, o -1 si no existe camino.
- (Variante) Retorna también la lista de vértices del camino mínimo.

---

## Pseudocódigo

```
consultaTiempoMinimo(origen, destino):
  distancias ← mapa: todos los vértices con valor ∞
  predecesores ← mapa vacío
  visitados ← conjunto vacío
  distancias[origen] ← 0

  repetir V veces:
      actual ← vértice no visitado con menor distancia en distancias
      si actual = nulo o distancias[actual] = ∞: salir
      si actual = destino: salir
      agregar actual a visitados
      para cada (vecino, peso) en adyacentes(actual):
          si vecino no en visitados:
              nuevaDist ← distancias[actual] + peso
              si nuevaDist < distancias[vecino]:
                  distancias[vecino] ← nuevaDist
                  predecesores[vecino] ← actual

  si distancias[destino] = ∞: retornar -1
  retornar distancias[destino]

recuperarCamino(origen, destino, predecesores):
  camino ← lista vacía
  actual ← destino
  mientras actual ≠ nulo:
      camino.insertarAlFrente(actual)
      actual ← predecesores[actual]
  retornar camino
```

---

## Java — `Map<String, List<Ruta>>` (formato del examen)

```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

class SistemaTransporte {
    private Map<String, List<Ruta>> grafo;

    private static class Ruta {
        String destino;
        int tiempo;

        Ruta(String destino, int tiempo) {
            this.destino = destino;
            this.tiempo = tiempo;
        }
    }

    public SistemaTransporte() {
        grafo = new HashMap<>();
    }

    public void agregarRuta(String origen, String destino, int tiempo) {
        List<Ruta> rutas = grafo.get(origen);
        if (rutas == null) {
            rutas = new ArrayList<>();
            grafo.put(origen, rutas);
        }
        rutas.add(new Ruta(destino, tiempo));
        if (!grafo.containsKey(destino)) {
            grafo.put(destino, new ArrayList<>());
        }
    }

    public int consultaTiempoMinimo(String origen, String destino) {
        if (!grafo.containsKey(origen)) {
            return -1;
        }
        Map<String, Integer> distancias = new HashMap<>();
        Map<String, String> predecesores = new HashMap<>();
        Set<String> visitados = new HashSet<>();

        for (String nodo : grafo.keySet()) {
            distancias.put(nodo, Integer.MAX_VALUE);
        }
        distancias.put(origen, 0);

        while (true) {
            String actual = null;
            int minDist = Integer.MAX_VALUE;
            for (String nodo : distancias.keySet()) {
                if (!visitados.contains(nodo)) {
                    int dist = distancias.get(nodo);
                    if (dist < minDist) {
                        minDist = dist;
                        actual = nodo;
                    }
                }
            }
            if (actual == null) {
                break;
            }
            if (actual.equals(destino)) {
                break;
            }
            visitados.add(actual);
            List<Ruta> rutas = grafo.get(actual);
            if (rutas != null) {
                for (Ruta ruta : rutas) {
                    if (!visitados.contains(ruta.destino)) {
                        int nuevaDist = distancias.get(actual) + ruta.tiempo;
                        if (nuevaDist < distancias.get(ruta.destino)) {
                            distancias.put(ruta.destino, nuevaDist);
                            predecesores.put(ruta.destino, actual);
                        }
                    }
                }
            }
        }

        int resultado = distancias.get(destino);
        if (resultado == Integer.MAX_VALUE) {
            return -1;
        }
        return resultado;
    }

    public List<String> recuperarCamino(String origen, String destino,
                                        Map<String, String> predecesores) {
        List<String> camino = new ArrayList<>();
        String actual = destino;
        while (actual != null) {
            camino.add(0, actual);
            actual = predecesores.get(actual);
        }
        return camino;
    }
}
```

---

## Variante: Dijkstra por camión (2025-1S Ej 3)

Cuando hay varios camiones con velocidades distintas y se necesita `tiempo = distancia / velocidad`:

```java
public List<Recorrido> ruta(String ciudadOrigen, String ciudadDestino) {
    List<Recorrido> resultado = new ArrayList<>();

    for (Map.Entry<String, Double> camion : camiones.entrySet()) {
        String camionID = camion.getKey();
        double velocidad = camion.getValue();

        // Dijkstra sobre distancias en km
        Map<String, Double> distancias = dijkstraDistancias(ciudadOrigen);

        Double distKm = distancias.get(ciudadDestino);
        if (distKm == null) {
            // no hay ruta para este camión, omitir
        } else {
            if (distKm < Double.MAX_VALUE) {
                double tiempoHoras = distKm / velocidad;
                List<String> path = recuperarCaminoDouble(ciudadOrigen, ciudadDestino);
                Recorrido rec = new Recorrido(camionID, tiempoHoras, path);
                resultado.add(rec);
            }
        }
    }
    return resultado;
}
```

---

## JUnit

```java
import junit.framework.TestCase;

public class SistemaTransporteTest extends TestCase {

    private SistemaTransporte sistema;

    @Override
    protected void setUp() {
        sistema = new SistemaTransporte();
        sistema.agregarRuta("A", "B", 5);
        sistema.agregarRuta("A", "C", 10);
        sistema.agregarRuta("B", "C", 2);
        sistema.agregarRuta("B", "D", 4);
        sistema.agregarRuta("C", "D", 1);
    }

    public void testCaminoMinimo() {
        // A→B(5)→C(2)→D(1) = 8
        int tiempo = sistema.consultaTiempoMinimo("A", "D");
        assertEquals(8, tiempo);
    }

    public void testConexionDirecta() {
        assertEquals(5, sistema.consultaTiempoMinimo("A", "B"));
    }

    public void testSinCamino() {
        sistema.agregarRuta("X", "Y", 3);   // componente desconectada
        assertEquals(-1, sistema.consultaTiempoMinimo("A", "X"));
    }

    public void testMismoNodo() {
        assertEquals(0, sistema.consultaTiempoMinimo("A", "A"));
    }
}
```

---

## Análisis de complejidad

- **O(V²)** — implementación simple (buscar mínimo linealmente en cada paso)
- **O((V+E) log V)** — con cola de prioridad (no se usa en parcial por restricción de lambdas)
- **Espacio:** O(V) — mapas de distancias, predecesores, visitados

## ¿Cuándo Dijkstra vs Floyd?

| Dijkstra | Floyd |
|---|---|
| Un origen → todos los destinos | Todos los pares |
| O(V²) / O((V+E) log V) | O(V³) |
| Solo pesos ≥ 0 | Solo pesos ≥ 0 |
| Recupera un camino con predecesores | Recupera cualquier camino |
| Mejor cuando el grafo es disperso | Mejor cuando se necesitan todos los pares |
