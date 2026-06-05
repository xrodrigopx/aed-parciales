# Solución: Conectividad en grafo (Warshall / BFS)

**Apareció en:** 2023-2S Parte 2 Ej 1 — Smart City Algorville  
**Patrón:** "¿existe camino entre nodo X e Y (posiblemente a través de nodos intermedios)?"

---

## Lenguaje natural

Dado un grafo de dispositivos de red, determinar si existe un camino (directo o por intermediarios) entre dos nodos dados.

Hay dos enfoques:
- **BFS/DFS desde X**: más eficiente para una consulta puntual (O(V+E)). Recorrer el grafo desde nodoX; si se llega a nodoY, están conectados.
- **Warshall**: útil si se necesitará responder muchas consultas (precalcula todos los pares en O(V³)).

Para el parcial, BFS desde el origen es la solución estándar para una consulta única.

---

## Precondición

- El grafo fue inicializado con sus vértices y aristas.
- `nodoX` y `nodoY` existen en el grafo.

## Postcondición

- Retorna `verdadero` si existe al menos un camino de `nodoX` a `nodoY` (directo o por intermediarios).
- Retorna `falso` si los nodos están en componentes desconectadas.

---

## Pseudocódigo — BFS (una consulta)

```
TCiudad.conectados(nodoX, nodoY):
  si nodoX = nulo: retornar falso
  si nodoY = nulo: retornar falso
  si nodoX = nodoY: retornar verdadero
  visitados ← conjunto vacío
  cola ← cola vacía
  encolar nodoX en cola
  agregar nodoX a visitados
  mientras cola no vacía:
      actual ← desencolar
      si actual = nodoY: retornar verdadero
      para cada vecino en adyacentes(actual):
          si vecino no en visitados:
              agregar vecino a visitados
              encolar vecino
  retornar falso
```

**O(V + E)** — una consulta.

---

## Pseudocódigo alternativo — Warshall (todas las consultas)

```
TCiudad.inicializarAlcanzabilidad():
  // A[i][j] = verdadero si existe camino de i a j
  Para todo i,j: A[i][j] = C[i][j]   // C = matriz de adyacencia booleana

  Para k = 1..n:
      Para i = 1..n:
          Para j = 1..n:
              si A[i][j] = falso:
                  A[i][j] = A[i][k] Y A[k][j]

TCiudad.conectados(nodoX, nodoY):
  i ← indice(nodoX)
  j ← indice(nodoY)
  retornar A[i][j]
```

**O(V³)** precálculo + O(1) por consulta.

---

## Java — BFS en grafo con `Map<String, List<String>>`

```java
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

class TCiudad {
    private Map<String, List<String>> grafo;

    public TCiudad() {
        grafo = new HashMap<>();
    }

    public void agregarNodo(String nodo) {
        if (!grafo.containsKey(nodo)) {
            grafo.put(nodo, new ArrayList<>());
        }
    }

    public void agregarConexion(String origen, String destino) {
        List<String> vecinos = grafo.get(origen);
        if (vecinos != null) {
            vecinos.add(destino);
        }
    }

    public boolean conectados(String nodoX, String nodoY) {
        if (nodoX == null) {
            return false;
        }
        if (nodoY == null) {
            return false;
        }
        if (nodoX.equals(nodoY)) {
            return true;
        }
        Set<String> visitados = new HashSet<>();
        Queue<String> cola = new ArrayDeque<>();
        cola.add(nodoX);
        visitados.add(nodoX);
        while (!cola.isEmpty()) {
            String actual = cola.poll();
            if (actual.equals(nodoY)) {
                return true;
            }
            List<String> vecinos = grafo.get(actual);
            if (vecinos != null) {
                for (String vecino : vecinos) {
                    if (!visitados.contains(vecino)) {
                        visitados.add(vecino);
                        cola.add(vecino);
                    }
                }
            }
        }
        return false;
    }
}
```

---

## JUnit

```java
import junit.framework.TestCase;

public class TCiudadTest extends TestCase {

    private TCiudad ciudad;

    @Override
    protected void setUp() {
        ciudad = new TCiudad();
        ciudad.agregarNodo("A");
        ciudad.agregarNodo("B");
        ciudad.agregarNodo("C");
        ciudad.agregarNodo("D");
        ciudad.agregarConexion("A", "B");
        ciudad.agregarConexion("B", "C");
        // D queda desconectado
    }

    public void testConectadosDirectos() {
        assertTrue(ciudad.conectados("A", "B"));
    }

    public void testConectadosPorIntermediario() {
        assertTrue(ciudad.conectados("A", "C"));
    }

    public void testNoConectados() {
        assertFalse(ciudad.conectados("A", "D"));
    }

    public void testMismoNodo() {
        assertTrue(ciudad.conectados("A", "A"));
    }

    public void testNodoNulo() {
        assertFalse(ciudad.conectados(null, "B"));
    }
}
```

---

## Nota: cuándo usar Warshall vs BFS

| Situación | Algoritmo recomendado |
|---|---|
| Una consulta puntual | BFS — O(V+E), más simple |
| Muchas consultas sobre el mismo grafo | Warshall — O(V³) preproceso, O(1) por consulta |
| Grafo dirigido con pesos | Warshall (alcanzabilidad) o Floyd (costos) |
| Grafo no dirigido | BFS desde el origen basta |
