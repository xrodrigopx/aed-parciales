# Solución: Todos los caminos posibles (con restricción de tipo de nodo)

**Apareció en:** 2024-1S Parte 2 Examen 1 y Examen 2 — redes de centros de datos y transporte urbano  
**Patrón:** "calcular todos los caminos simples entre origen y destino, donde la comunicación entre nodos de tipo A debe pasar por nodos de tipo B"

---

## Lenguaje natural

Dado un grafo con nodos de dos tipos (ej. procesadores y switches, o estaciones de bus y tren), encontrar todos los caminos simples desde un origen hasta un destino. La restricción es que dos nodos del mismo tipo no pueden estar conectados directamente en el camino — siempre debe pasar por el otro tipo.

El algoritmo usa DFS con backtracking:
1. Marcar el nodo actual como visitado y agregarlo al camino en curso.
2. Si el nodo actual es el destino, guardar una copia del camino.
3. Si no, explorar todos los vecinos no visitados que cumplen la restricción de tipo.
4. Al retroceder, desmarcar el nodo actual (backtracking) para que otros caminos puedan usarlo.

La restricción de tipo se verifica antes de llamar recursivamente: no se permite un arco directo entre dos nodos del mismo tipo (ej. dos procesadores no pueden conectarse directamente).

---

## Precondición

- `origen` y `destino` existen en el grafo.
- Los tipos de los nodos están definidos (`PROCESADOR` o `SWITCH`, o `AUTOBUS` o `TREN`).
- El grafo puede tener ciclos; el conjunto `visitados` los previene.

## Postcondición

- Retorna una lista con todos los caminos simples posibles desde `origen` hasta `destino` que cumplen la restricción de tipo.
- Si no existe ninguno, retorna lista vacía.
- Cada camino es una lista de strings (nombres de nodos) en orden de visita.

---

## Pseudocódigo

```
calcularCaminos(grafo, origen, destino):
  resultado ← lista vacía
  visitados ← conjunto vacío
  camino ← lista vacía
  calcularCaminosAux(grafo, origen, destino, visitados, camino, resultado)
  retornar resultado

calcularCaminosAux(grafo, actual, destino, visitados, camino, resultado):
  agregar actual a visitados
  camino.agregar(actual)

  si actual = destino:
      resultado.agregar(copia de camino)
  sino:
      para cada vecino en adyacentes(grafo, actual):
          si vecino no en visitados:
              // restricción: no conectar dos nodos del mismo tipo directamente
              si tipoNodo(actual) ≠ tipoNodo(vecino):
                  calcularCaminosAux(grafo, vecino, destino, visitados, camino, resultado)

  camino.eliminarUltimo()
  remover actual de visitados    ← backtracking clave
```

**Sin restricción de tipo:** eliminar la condición `tipoNodo(actual) ≠ tipoNodo(vecino)`.

---

## Java — con restricción de tipo (Examen 1: procesadores y switches)

```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

class SistemaRed {
    private Map<String, List<String>> grafo;
    private Map<String, String> tipoNodo;   // "PROCESADOR" o "SWITCH"

    public SistemaRed() {
        grafo = new HashMap<>();
        tipoNodo = new HashMap<>();
    }

    public void agregarNodo(String nodo, String tipo) {
        if (!grafo.containsKey(nodo)) {
            grafo.put(nodo, new ArrayList<>());
        }
        tipoNodo.put(nodo, tipo);
    }

    public void agregarConexion(String origen, String destino) {
        List<String> vecinos = grafo.get(origen);
        if (vecinos != null) {
            vecinos.add(destino);
        }
    }

    public List<List<String>> calcularCaminos(String origen, String destino) {
        List<List<String>> resultado = new ArrayList<>();
        Set<String> visitados = new HashSet<>();
        List<String> camino = new ArrayList<>();
        calcularCaminosAux(origen, destino, visitados, camino, resultado);
        return resultado;
    }

    private void calcularCaminosAux(String actual, String destino,
                                    Set<String> visitados, List<String> camino,
                                    List<List<String>> resultado) {
        visitados.add(actual);
        camino.add(actual);

        if (actual.equals(destino)) {
            resultado.add(new ArrayList<>(camino));
        } else {
            List<String> vecinos = grafo.get(actual);
            if (vecinos != null) {
                for (String vecino : vecinos) {
                    if (!visitados.contains(vecino)) {
                        String tipoActual = tipoNodo.get(actual);
                        String tipoVecino = tipoNodo.get(vecino);
                        // restriccion: no conectar dos procesadores directamente
                        if (!tipoActual.equals(tipoVecino)) {
                            calcularCaminosAux(vecino, destino, visitados, camino, resultado);
                        }
                    }
                }
            }
        }

        camino.remove(camino.size() - 1);
        visitados.remove(actual);
    }
}
```

---

## Java — sin restricción de tipo (versión general)

```java
public List<List<String>> calcularTodosLosCaminos(String origen, String destino) {
    List<List<String>> resultado = new ArrayList<>();
    Set<String> visitados = new HashSet<>();
    List<String> camino = new ArrayList<>();
    dfsBacktracking(origen, destino, visitados, camino, resultado);
    return resultado;
}

private void dfsBacktracking(String actual, String destino,
                              Set<String> visitados, List<String> camino,
                              List<List<String>> resultado) {
    visitados.add(actual);
    camino.add(actual);

    if (actual.equals(destino)) {
        resultado.add(new ArrayList<>(camino));
    } else {
        List<String> vecinos = grafo.get(actual);
        if (vecinos != null) {
            for (String vecino : vecinos) {
                if (!visitados.contains(vecino)) {
                    dfsBacktracking(vecino, destino, visitados, camino, resultado);
                }
            }
        }
    }

    camino.remove(camino.size() - 1);
    visitados.remove(actual);
}
```

---

## JUnit

```java
import junit.framework.TestCase;
import java.util.List;

public class SistemaRedTest extends TestCase {

    public void testUnCaminoExiste() {
        SistemaRed red = new SistemaRed();
        red.agregarNodo("P1", "PROCESADOR");
        red.agregarNodo("S1", "SWITCH");
        red.agregarNodo("P2", "PROCESADOR");
        red.agregarConexion("P1", "S1");
        red.agregarConexion("S1", "P2");

        List<List<String>> caminos = red.calcularCaminos("P1", "P2");
        assertEquals(1, caminos.size());
        assertEquals(3, caminos.get(0).size());
    }

    public void testDosCaminosParalelos() {
        SistemaRed red = new SistemaRed();
        red.agregarNodo("P1", "PROCESADOR");
        red.agregarNodo("S1", "SWITCH");
        red.agregarNodo("S2", "SWITCH");
        red.agregarNodo("P2", "PROCESADOR");
        red.agregarConexion("P1", "S1");
        red.agregarConexion("P1", "S2");
        red.agregarConexion("S1", "P2");
        red.agregarConexion("S2", "P2");

        List<List<String>> caminos = red.calcularCaminos("P1", "P2");
        assertEquals(2, caminos.size());
    }

    public void testConexionDirectaEntreProcessadoresBloqueada() {
        SistemaRed red = new SistemaRed();
        red.agregarNodo("P1", "PROCESADOR");
        red.agregarNodo("P2", "PROCESADOR");
        red.agregarConexion("P1", "P2");

        List<List<String>> caminos = red.calcularCaminos("P1", "P2");
        assertEquals(0, caminos.size());
    }

    public void testSinCamino() {
        SistemaRed red = new SistemaRed();
        red.agregarNodo("P1", "PROCESADOR");
        red.agregarNodo("P2", "PROCESADOR");

        List<List<String>> caminos = red.calcularCaminos("P1", "P2");
        assertEquals(0, caminos.size());
    }
}
```

---

## Análisis de complejidad

- **Peor caso:** O(V!) — todos los caminos posibles en un grafo completo
- **Espacio:** O(V) — pila de recursión + visitados
- **Nota:** no hay forma de evitar O(V!) en el peor caso; el backtracking es la única estrategia correcta
