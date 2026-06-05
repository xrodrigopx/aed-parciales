# Solución: Árbol generador mínimo con Prim

**Apareció en:** 2024-2S Parte 2 Ej 1 Problema 2 — `redDeMantenimiento`  
**Patrón:** "encontrar el subgrafo de mínimo costo que conecte todos los nodos" (árbol generador mínimo)

---

## Lenguaje natural

Dado un grafo no dirigido con pesos (ej. estaciones conectadas por rutas con tiempos), encontrar el subconjunto mínimo de aristas que conecte todos los nodos con el menor costo total posible, sin ciclos. Esto es el árbol generador mínimo (AGM).

El algoritmo de Prim construye el árbol creciendo vértice por vértice:
1. Empezar con un vértice cualquiera en el árbol (conjunto U).
2. En cada paso, buscar la arista de menor peso que conecta un vértice de U con uno fuera de U.
3. Agregar esa arista y el nuevo vértice al árbol.
4. Repetir hasta que todos los vértices estén en el árbol.

---

## Precondición

- El grafo es no dirigido y conexo (todos los nodos están conectados).
- Todas las aristas tienen peso ≥ 0.
- El grafo tiene al menos un nodo.

## Postcondición

- Retorna una lista con exactamente V-1 aristas que forman el árbol generador mínimo.
- El costo total de esas aristas es el mínimo posible.

---

## Pseudocódigo

```
redDeMantenimiento(grafo):
  resultado ← lista vacía
  si grafo está vacío: retornar resultado

  U ← {primerVertice(grafo)}
  noU ← todosLosVertices(grafo) \ U

  mientras noU no vacío:
      minArista ← nulo
      minPeso ← ∞
      para cada vOrigen en U:
          para cada (vDestino, peso) en adyacentes(vOrigen):
              si vDestino en noU:
                  si peso < minPeso:
                      minPeso ← peso
                      minArista ← (vOrigen, vDestino, peso)
      si minArista = nulo: salir  // grafo no conexo
      U.agregar(minArista.destino)
      noU.remover(minArista.destino)
      resultado.agregar(minArista)

  retornar resultado
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

    public static class Ruta {
        String destino;
        int tiempo;

        Ruta(String destino, int tiempo) {
            this.destino = destino;
            this.tiempo = tiempo;
        }
    }

    public static class RutaConectada {
        String origen;
        String destino;
        int tiempo;

        RutaConectada(String origen, String destino, int tiempo) {
            this.origen = origen;
            this.destino = destino;
            this.tiempo = tiempo;
        }
    }

    public SistemaTransporte() {
        grafo = new HashMap<>();
    }

    public void agregarRuta(String origen, String destino, int tiempo) {
        agregarConexionDirecta(origen, destino, tiempo);
        agregarConexionDirecta(destino, origen, tiempo);   // no dirigido
    }

    private void agregarConexionDirecta(String origen, String destino, int tiempo) {
        List<Ruta> rutas = grafo.get(origen);
        if (rutas == null) {
            rutas = new ArrayList<>();
            grafo.put(origen, rutas);
        }
        rutas.add(new Ruta(destino, tiempo));
    }

    public List<RutaConectada> redDeMantenimiento() {
        List<RutaConectada> resultado = new ArrayList<>();
        if (grafo.isEmpty()) {
            return resultado;
        }

        Set<String> U = new HashSet<>();
        Set<String> noU = new HashSet<>(grafo.keySet());

        // tomar cualquier vertice inicial
        String primero = grafo.keySet().iterator().next();
        U.add(primero);
        noU.remove(primero);

        while (!noU.isEmpty()) {
            String minOrigen = null;
            String minDestino = null;
            int minPeso = Integer.MAX_VALUE;

            for (String vOrigen : U) {
                List<Ruta> rutas = grafo.get(vOrigen);
                if (rutas != null) {
                    for (Ruta ruta : rutas) {
                        if (noU.contains(ruta.destino)) {
                            if (ruta.tiempo < minPeso) {
                                minPeso = ruta.tiempo;
                                minOrigen = vOrigen;
                                minDestino = ruta.destino;
                            }
                        }
                    }
                }
            }

            if (minOrigen == null) {
                break;   // grafo no conexo
            }

            U.add(minDestino);
            noU.remove(minDestino);
            resultado.add(new RutaConectada(minOrigen, minDestino, minPeso));
        }

        return resultado;
    }
}
```

---

## JUnit

```java
import junit.framework.TestCase;
import java.util.List;

public class RedDeMantenimientoTest extends TestCase {

    public void testAGMTieneV1Aristas() {
        SistemaTransporte sistema = new SistemaTransporte();
        sistema.agregarRuta("A", "B", 5);
        sistema.agregarRuta("A", "C", 10);
        sistema.agregarRuta("B", "C", 2);
        sistema.agregarRuta("B", "D", 4);
        sistema.agregarRuta("C", "D", 1);

        List<SistemaTransporte.RutaConectada> red = sistema.redDeMantenimiento();
        assertEquals(3, red.size());   // V-1 = 4-1 = 3
    }

    public void testCostoMinimo() {
        // A-B:5, A-C:10, B-C:2, B-D:4, C-D:1
        // AGM optimo: A-B(5), B-C(2), C-D(1) — costo 8
        SistemaTransporte sistema = new SistemaTransporte();
        sistema.agregarRuta("A", "B", 5);
        sistema.agregarRuta("A", "C", 10);
        sistema.agregarRuta("B", "C", 2);
        sistema.agregarRuta("B", "D", 4);
        sistema.agregarRuta("C", "D", 1);

        List<SistemaTransporte.RutaConectada> red = sistema.redDeMantenimiento();

        int costoTotal = 0;
        for (SistemaTransporte.RutaConectada ruta : red) {
            costoTotal += ruta.tiempo;
        }
        assertEquals(8, costoTotal);
    }

    public void testGrafoVacio() {
        SistemaTransporte sistema = new SistemaTransporte();
        List<SistemaTransporte.RutaConectada> red = sistema.redDeMantenimiento();
        assertEquals(0, red.size());
    }
}
```

---

## Análisis de complejidad

- **O(V·E)** — por cada vértice (V), busca la arista mínima recorriendo todas las aristas (E)
- **Espacio:** O(V) — conjuntos U y noU

## Prim vs Kruskal

| Propiedad | Prim | Kruskal |
|---|---|---|
| Crece | por vértice | por arista |
| Busca | arista mínima cruzando U/noU | arista mínima global |
| Detección de ciclo | implícita (noU) | union-find |
| Mejor para | grafos densos | grafos dispersos |
| Complejidad simple | O(V·E) | O(E log E) por el sort |

Ambos producen el mismo AGM (o uno igualmente óptimo si hay empates).
