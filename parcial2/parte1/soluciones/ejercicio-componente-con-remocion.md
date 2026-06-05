# Solución: Componente conectada con remoción de nodos

**Apareció en:** 2025-2S Parte 2 — Traje Mark XLV (Iron Man)  
**Patrón:** (1) dado un conjunto de nodos dañados, ¿cuántos nodos siguen conectados al núcleo? (2) ¿es un nodo "crítico" para la conectividad del núcleo?

---

## Escenario

El traje se modela como un grafo no dirigido. Un vértice especial (`Pecho`) es el núcleo. Piezas dañadas quedan fuera del grafo. Se quiere:

1. **`tamañoTrajeFuncional(damaged)`**: con el conjunto de piezas dañadas removidas, ¿cuántas piezas siguen conectadas a `Pecho`?
2. **`esPiezaCritica(pieza)`**: si se remueve esa pieza, ¿cambia el conjunto de piezas conectadas a `Pecho`?

---

## Lenguaje natural

### tamañoTrajeFuncional

Hacer BFS/DFS desde `Pecho` en el grafo, ignorando los nodos del conjunto `damaged`. Contar cuántos nodos se alcanzan (incluyendo `Pecho` si no está dañado).

### esPiezaCritica

Comparar:
1. BFS sin remover ninguna pieza → tamaño del componente de `Pecho` = `base`
2. BFS removiendo la pieza específica → nuevo tamaño = `sinPieza`

Si `sinPieza < base - 1`, la pieza es crítica (su remoción desconecta piezas adicionales). Si `sinPieza == base - 1`, no es crítica (solo se removió ella misma y el resto sigue conectado).

---

## Precondición

- El grafo fue cargado con todas las piezas y conexiones.
- `Pecho` existe en el grafo.

## Postcondición

- `tamañoTrajeFuncional(D)`: retorna la cantidad de piezas conectadas a `Pecho` cuando se ignoran las piezas de `D`.
- `esPiezaCritica(p)`: retorna `verdadero` si al remover `p`, el número de piezas conectadas a `Pecho` disminuye en más de 1.

---

## Pseudocódigo

```
tamañoTrajeFuncional(damaged):
  si "Pecho" en damaged: retornar 0
  visitados ← conjunto vacío
  cola ← cola vacía
  encolar "Pecho"
  agregar "Pecho" a visitados
  contador ← 1
  mientras cola no vacía:
      actual ← desencolar
      para cada vecino en adyacentes(actual):
          si vecino no en visitados:
              si vecino no en damaged:
                  agregar vecino a visitados
                  encolar vecino
                  contador ← contador + 1
  retornar contador

esPiezaCritica(pieza):
  si pieza = "Pecho": retornar verdadero  // nucleo siempre critico
  conjuntoVacio ← conjunto vacío
  tamanioBase ← tamañoTrajeFuncional(conjuntoVacio)
  conjuntoPieza ← {pieza}
  tamanioSinPieza ← tamañoTrajeFuncional(conjuntoPieza)
  // si cae en mas de 1 (la propia pieza + otras desconectadas)
  retornar tamanioSinPieza < tamanioBase - 1
```

---

## Java

```java
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

class GarageMarkXLV {
    private Map<String, List<String>> grafo;
    private static final String NUCLEO = "Pecho";

    public GarageMarkXLV() {
        grafo = new HashMap<>();
    }

    public void agregarPieza(String pieza) {
        if (!grafo.containsKey(pieza)) {
            grafo.put(pieza, new ArrayList<>());
        }
    }

    public void agregarConexion(String pieza1, String pieza2) {
        List<String> vecinos1 = grafo.get(pieza1);
        if (vecinos1 != null) {
            vecinos1.add(pieza2);
        }
        List<String> vecinos2 = grafo.get(pieza2);
        if (vecinos2 != null) {
            vecinos2.add(pieza1);
        }
    }

    public int tamañoTrajeFuncional(Set<String> damaged) {
        if (damaged.contains(NUCLEO)) {
            return 0;
        }
        Set<String> visitados = new HashSet<>();
        Queue<String> cola = new ArrayDeque<>();
        cola.add(NUCLEO);
        visitados.add(NUCLEO);
        int contador = 1;
        while (!cola.isEmpty()) {
            String actual = cola.poll();
            List<String> vecinos = grafo.get(actual);
            if (vecinos != null) {
                for (String vecino : vecinos) {
                    if (!visitados.contains(vecino)) {
                        if (!damaged.contains(vecino)) {
                            visitados.add(vecino);
                            cola.add(vecino);
                            contador++;
                        }
                    }
                }
            }
        }
        return contador;
    }

    public boolean esPiezaCritica(String pieza) {
        if (pieza.equals(NUCLEO)) {
            return true;
        }
        Set<String> vacio = new HashSet<>();
        int tamanioBase = tamañoTrajeFuncional(vacio);
        Set<String> conjuntoPieza = new HashSet<>();
        conjuntoPieza.add(pieza);
        int tamanioSinPieza = tamañoTrajeFuncional(conjuntoPieza);
        // es critica si desconecta mas piezas de las que por si misma representa
        return tamanioSinPieza < tamanioBase - 1;
    }
}
```

---

## JUnit

```java
import junit.framework.TestCase;
import java.util.HashSet;
import java.util.Set;

public class GarageMarkXLVTest extends TestCase {

    private GarageMarkXLV garage;

    @Override
    protected void setUp() {
        // Pecho - Casco
        // Pecho - Abs
        // Abs - Cadera
        // Cadera - BotaIzq
        garage = new GarageMarkXLV();
        garage.agregarPieza("Pecho");
        garage.agregarPieza("Casco");
        garage.agregarPieza("Abs");
        garage.agregarPieza("Cadera");
        garage.agregarPieza("BotaIzq");
        garage.agregarConexion("Pecho", "Casco");
        garage.agregarConexion("Pecho", "Abs");
        garage.agregarConexion("Abs", "Cadera");
        garage.agregarConexion("Cadera", "BotaIzq");
    }

    public void testSinDanados_TodasConectadas() {
        Set<String> noDanadas = new HashSet<>();
        assertEquals(5, garage.tamañoTrajeFuncional(noDanadas));
    }

    public void testConAbsDanado_CaidaCadera_BotaIzq() {
        // Sin Abs, Cadera y BotaIzq quedan desconectadas de Pecho
        Set<String> damaged = new HashSet<>();
        damaged.add("Abs");
        assertEquals(2, garage.tamañoTrajeFuncional(damaged));
    }

    public void testAbsEsCritica() {
        // Sin Abs: base=5, sinAbs=2 → 2 < 5-1=4 → critica
        assertTrue(garage.esPiezaCritica("Abs"));
    }

    public void testCascoNoCritico() {
        // Sin Casco: base=5, sinCasco=4 → 4 < 5-1=4 es falso → no critico
        assertFalse(garage.esPiezaCritica("Casco"));
    }

    public void testPechoSiempreCritico() {
        assertTrue(garage.esPiezaCritica("Pecho"));
    }

    public void testPechoEnDanados_RetornaCero() {
        Set<String> damaged = new HashSet<>();
        damaged.add("Pecho");
        assertEquals(0, garage.tamañoTrajeFuncional(damaged));
    }
}
```

---

## Análisis de complejidad

- **`tamañoTrajeFuncional`:** O(V+E) — BFS estándar ignorando nodos dañados
- **`esPiezaCritica`:** O(V+E) — llama a `tamañoTrajeFuncional` dos veces

## Diferencia con puntos de articulación clásicos

| Puntos de articulación | `esPiezaCritica` de este ejercicio |
|---|---|
| Desconecta cualquier parte del grafo | Desconecta piezas del núcleo (`Pecho`) |
| DFS con `disc` y `low` | BFS doble (con y sin la pieza) |
| O(V+E) con DFS avanzado | O(V+E) con BFS simple (más fácil de implementar) |
| Retorna lista de todos los AP | Consulta puntual sobre una pieza específica |

Para este patrón de examen, la solución BFS doble es más simple que el algoritmo de Tarjan y da el mismo resultado para la consulta puntual.
