# Pseudocódigos — Grafos Dirigidos (UT4)

---

## Algoritmo de Dijkstra

### Lenguaje natural

Dado un vértice origen, Dijkstra calcula el camino de menor costo desde ese origen a todos los demás vértices. En cada iteración elige el vértice no visitado con menor distancia conocida (técnica ávida) y actualiza las distancias de sus vecinos. Solo funciona con pesos no negativos.

### Precondición
- El grafo es dirigido y todos los pesos de arcos son ≥ 0.
- El vértice origen existe en el grafo.

### Postcondición
- El vector D contiene la distancia mínima desde origen hasta cada vértice.
- El vector P contiene el predecesor de cada vértice en el camino mínimo.

### Pseudocódigo

```
dijkstra(origen, C, V):
    // D = distancias mínimas, P = predecesores, S = visitados
    S ← {origen}
    Para todo v en V:
        D[v] = C[origen, v]   // ∞ si no hay arco directo
        P[v] = origen

    D[origen] = 0

    Mientras V ≠ S:
        w ← vértice en V-S tal que D[w] sea mínimo
        agregar w a S
        Para todo v en V-S:
            Si D[w] + C[w,v] < D[v]:
                D[v] = D[w] + C[w,v]
                P[v] = w
```

### Recuperación de camino

```
recuperarCamino(origen, destino, P):
    camino ← lista vacía
    actual ← destino
    mientras actual ≠ origen:
        camino.insertarAlFrente(actual)
        actual ← P[actual]
    camino.insertarAlFrente(origen)
    retornar camino
```

### Variantes para el parcial

**Con aristas bloqueadas** — agregar condición `H[w,v]` antes de relajar:
```
Si D[w] + C[w,v] < D[v] Y H[w,v] = verdadero:
    D[v] = D[w] + C[w,v]
    P[v] = w
```

**Con parada obligatoria en un nodo v** — ejecutar dos veces:
```
resultado1 ← dijkstra(origen, G)   // origen → v
resultado2 ← dijkstra(v, G)        // v → destino
costoTotal = resultado1.D[v] + resultado2.D[destino]
```

**Con aristas por horario** — condición `H[hora][w,v]`:
```
Si D[w] + C[w,v] < D[v] Y H[hora][w,v] = verdadero:
    D[v] = D[w] + C[w,v]
    P[v] = w
```

---

## Algoritmo de Floyd

### Lenguaje natural

Floyd calcula el camino de menor costo entre todos los pares de vértices. Itera usando cada vértice k como posible intermediario y actualiza la distancia entre cada par (i, j) si pasar por k la mejora. La matriz P registra el intermediario que causó cada mejora, permitiendo recuperar los caminos.

### Precondición
- El grafo es dirigido con pesos no negativos.
- La matriz C está inicializada: C[i,j] = costo del arco, ∞ si no hay arco, 0 en la diagonal.

### Postcondición
- La matriz A contiene las distancias mínimas entre todos los pares.
- La matriz P permite recuperar cualquier camino mínimo.

### Pseudocódigo

```
floyd(C, n):
    // inicializar A con C; inicializar diagonal en 0; P en 0
    Para todo i = 1..n:
        Para todo j = 1..n:
            A[i,j] = C[i,j]
            P[i,j] = 0
        A[i,i] = 0

    Para k = 1..n:
        Para i = 1..n:
            Para j = 1..n:
                Si A[i,k] + A[k,j] < A[i,j]:
                    A[i,j] = A[i,k] + A[k,j]
                    P[i,j] = k
```

### Recuperación de camino

```
camino(i, j, P):
    k ← P[i,j]
    Si k = 0:
        // el arco i→j es directo, no hay intermediario
        retornar
    camino(i, k, P)
    imprimir k
    camino(k, j, P)
```

Para imprimir el camino completo de i a j: imprimir i, llamar camino(i, j, P), imprimir j.

### Excentricidad y centro del grafo

```
excentricidades(A, n):
    Para todo v = 1..n:
        e[v] = 0
        Para todo u = 1..n:
            Si A[u,v] > e[v]:
                e[v] = A[u,v]
    retornar e

centroDelGrafo(e, n):
    centro = 1
    Para todo v = 2..n:
        Si e[v] < e[centro]:
            centro = v
    retornar centro
```

La excentricidad de v es el **máximo de la columna v** de la matriz A.

### Variantes para el parcial

**Contar caminos mínimos alternativos** — agregar matriz Q:
```
Para k = 1..n:
    Para i = 1..n:
        Para j = 1..n:
            Si A[i,k] + A[k,j] < A[i,j]:
                A[i,j] = A[i,k] + A[k,j]
                P[i,j] = k
                Q[i,j] = 0
            Sino si A[i,k] + A[k,j] = A[i,j]:
                Q[i,j] = Q[i,j] + 1
```

---

## Algoritmo de Warshall (cerradura transitiva)

### Lenguaje natural

Warshall determina si existe un camino entre cada par de vértices. Usa la misma estructura de tres bucles de Floyd, pero con valores booleanos: en lugar de minimizar costos, propaga la existencia de conexión.

### Precondición
- La matriz C es booleana: C[i,j] = verdadero si hay arco directo de i a j.

### Postcondición
- La matriz A es booleana: A[i,j] = verdadero si existe algún camino de i a j.

### Pseudocódigo

```
warshall(C, n):
    Para todo i = 1..n:
        Para todo j = 1..n:
            A[i,j] = C[i,j]

    Para k = 1..n:
        Para i = 1..n:
            Para j = 1..n:
                Si A[i,j] = falso:
                    A[i,j] = A[i,k] Y A[k,j]
```

**Diferencia con Floyd:** Floyd usa `min(A[i,j], A[i,k]+A[k,j])`; Warshall usa `A[i,j] OR (A[i,k] AND A[k,j])`.

---

## DFS — Búsqueda en profundidad (BPF)

### Lenguaje natural

DFS recorre el grafo eligiendo siempre un vecino no visitado y profundizando hasta no poder avanzar, luego retrocede. Visita todos los vértices alcanzables desde el punto de inicio. Para grafos desconectados, se repite el proceso desde cada vértice no visitado.

### Precondición
- El grafo es dirigido.

### Postcondición
- Todos los vértices han sido visitados.
- Se ejecutó la operación indicada (Consumer) en cada vértice en orden DFS.

### Pseudocódigo

```
main(G, consumer):
    visitados ← conjunto vacío
    Para cada v en G.vertices():
        Si v no en visitados:
            bpf(v, visitados, G, consumer)

bpf(v, visitados, G, consumer):
    agregar v a visitados
    consumer.accept(v)
    Para cada w en G.adyacencias(v):
        Si w no en visitados:
            bpf(w, visitados, G, consumer)
```

### Tipos de arcos en DFS

| Tipo | Cómo identificarlo |
|------|-------------------|
| Árbol | El destino no estaba visitado al momento del arco |
| Retroceso | El destino está en la recursión activa (antecesor) |
| Avance | El destino ya fue terminado y es descendiente |
| Cruzado | El destino ya fue terminado y no es antecesor ni descendiente |

---

## Clasificación topológica

### Lenguaje natural

Ordena los vértices de un grafo acíclico dirigido (GDA) de forma que si existe un arco de i a j, entonces i aparece antes que j en el orden resultante. Se implementa con DFS: cada vértice se inserta al principio de la lista cuando termina su recursión (después de procesar todos sus sucesores).

### Precondición
- El grafo es un GDA (no tiene ciclos). Si tiene ciclos, el resultado no es válido.

### Postcondición
- La lista retornada es un orden topológico válido.

### Pseudocódigo

```
clasificacionTopologica(G):
    visitados ← conjunto vacío
    lista ← lista vacía
    Para cada v en G.vertices():
        clasificacionTopologicaAux(v, visitados, lista, G)
    retornar lista

clasificacionTopologicaAux(nodo, visitados, lista, G):
    Si nodo no en visitados:
        agregar nodo a visitados
        Para cada w en G.adyacencias(nodo):
            clasificacionTopologicaAux(w, visitados, lista, G)
        insertar nodo AL PRINCIPIO de lista
```

**Clave:** la inserción es en la **salida** de la recursión, no en la entrada.

---

## Detección de ciclos

### Lenguaje natural

Usa DFS manteniendo un conjunto de vértices activos en la pila de llamadas actual. Si durante la exploración se encuentra un vértice que ya está activo (no terminado), se encontró un arco de retroceso, lo que indica un ciclo. Al retroceder, se quita el vértice del conjunto activo.

### Precondición
- El grafo es dirigido.

### Postcondición
- Retorna verdadero si el grafo tiene al menos un ciclo, falso si es acíclico.

### Pseudocódigo

```
tieneCiclos(G):
    enRecursion ← conjunto vacío
    Para cada v en G.vertices():
        Si tieneCiclosAux(v, enRecursion, G):
            retornar verdadero
    retornar falso

tieneCiclosAux(v, enRecursion, G):
    agregar v a enRecursion
    Para cada w en G.adyacencias(v):
        Si w en enRecursion:
            retornar verdadero
        Si tieneCiclosAux(w, enRecursion, G):
            retornar verdadero
    remover v de enRecursion
    retornar falso
```

**Nota:** este enfoque puede revisar vértices ya terminados varias veces. Una variante eficiente usa tres estados: NO_VISITADO, EN_PILA, TERMINADO. Un vértice TERMINADO no se procesa de nuevo.

---

## Todos los caminos posibles

### Lenguaje natural

Usa DFS con backtracking para explorar todos los caminos entre origen y destino. A diferencia del DFS simple, al retroceder se desmarca el vértice actual para que otros caminos puedan volver a usarlo. Cuando se llega al destino, se guarda una copia del camino actual.

### Precondición
- origen y destino existen en el grafo.

### Postcondición
- Retorna una lista con todos los caminos simples posibles de origen a destino.

### Pseudocódigo

```
todosLosCaminos(origen, destino, G):
    visitados ← conjunto vacío
    camino ← lista vacía
    resultado ← lista vacía
    todosLosCaminosAux(origen, destino, visitados, camino, resultado, G)
    retornar resultado

todosLosCaminosAux(actual, destino, visitados, camino, resultado, G):
    agregar actual a visitados
    camino.agregar(actual)
    Si actual = destino:
        resultado.agregar(copia de camino)
    Sino:
        Para cada w en G.adyacencias(actual):
            Si w no en visitados:
                todosLosCaminosAux(w, destino, visitados, camino, resultado, G)
    camino.eliminarUltimo()
    remover actual de visitados   // backtracking
```

**Clave del backtracking:** al remover el vértice de visitados al retroceder, se permite que otros caminos alternativos lo reutilicen.

---

## Búsqueda en amplitud (BEA)

### Lenguaje natural

BEA recorre el grafo nivel a nivel. Usa una cola: agrega el vértice inicial, luego en cada paso extrae el primero de la cola, lo procesa y encola todos sus vecinos no visitados. A diferencia de DFS, visita primero los vértices más cercanos al origen.

### Precondición
- El grafo puede ser dirigido o no dirigido.

### Postcondición
- Todos los vértices alcanzables desde el origen son visitados en orden de distancia (saltos).

### Pseudocódigo

```
bea(origen, G, consumer):
    visitados ← conjunto vacío
    cola ← cola vacía
    agregar origen a visitados
    encolar origen en cola

    Mientras cola no vacía:
        v ← desencolar de cola
        consumer.accept(v)
        Para cada w en G.adyacencias(v):
            Si w no en visitados:
                agregar w a visitados
                encolar w en cola
```

**BEA calcula distancias mínimas en grafos sin pesos** (número de saltos). Para grafos con pesos usar Dijkstra.

---

## Prim — Árbol generador mínimo (grafo no dirigido)

### Lenguaje natural

Dado un grafo no dirigido con pesos, Prim construye el árbol generador mínimo (AGM): el subgrafo que conecta todos los vértices con el menor costo total posible, sin ciclos. Empieza con un vértice cualquiera en el árbol y en cada paso agrega la arista de menor peso que une un vértice del árbol con uno que aún no está. Repite hasta incluir todos los vértices.

### Precondición
- El grafo es no dirigido, conexo, y todas las aristas tienen peso ≥ 0.
- El vértice origen existe en el grafo.

### Postcondición
- Retorna un nuevo grafo no dirigido con los mismos vértices y exactamente V-1 aristas que forman el árbol generador mínimo.

### Pseudocódigo

```
prim(G, origen):
    arbol ← nuevo GrafoNoDirigido con todos los vértices de G
    U ← {origen}          // vértices ya en el árbol
    noU ← G.vertices() \ {origen}  // vértices fuera del árbol

    Mientras noU no esté vacío:
        minArista ← searchMinEdge(G, U, noU)
        Si minArista = nulo:
            detener  // grafo no conexo
        nuevoVertice ← minArista.target()
        agregar nuevoVertice a U
        remover nuevoVertice de noU
        arbol.agregarArista(minArista.source(), minArista.target(), minArista.dato())

    retornar arbol

searchMinEdge(G, U, noU):
    minArista ← nulo
    minPeso ← ∞
    Para cada u en U:
        Para cada arista en G.adyacencias(u):
            Si arista.target() en noU Y arista.dato().getWeight() < minPeso:
                minPeso ← arista.dato().getWeight()
                minArista ← arista
    retornar minArista
```

### Java

```java
@Override
public <V, D extends WeightedEdge> IUndirectedGraph<V, D> prim(
        IUndirectedGraph<V, D> grafo, Comparable<V> source) {
    V origen = grafo.buscarVertice(source);
    GrafoNoDirigido<V, D> arbol = new GrafoNoDirigido<>();

    if (origen == null) {
        return arbol;
    }

    for (V v : grafo.vertices()) {
        arbol.agregarVertice(v);
    }

    Set<V> U = new HashSet<>();
    Set<V> noU = new HashSet<>();
    for (V v : grafo.vertices()) {
        noU.add(v);
    }
    U.add(origen);
    noU.remove(origen);

    while (!noU.isEmpty()) {
        Edge<V, D> minArista = searchMinEdge(grafo, U, noU);
        if (minArista == null) {
            break;
        }
        V nuevoVertice = minArista.target();
        U.add(nuevoVertice);
        noU.remove(nuevoVertice);
        arbol.agregarArista(minArista.source(), minArista.target(), minArista.dato());
    }

    return arbol;
}

@Override
public <V, D extends WeightedEdge> Edge<V, D> searchMinEdge(
        IUndirectedGraph<V, D> grafo, Collection<V> U, Collection<V> noU) {
    Edge<V, D> minArista = null;
    double minPeso = Double.MAX_VALUE;

    for (V u : U) {
        for (Edge<V, D> arista : grafo.adyacencias(grafo.construirComparable(u))) {
            V vecino = arista.target();
            if (noU.contains(vecino)) {
                double peso = arista.dato().getWeight();
                if (peso < minPeso) {
                    minPeso = peso;
                    minArista = arista;
                }
            }
        }
    }
    return minArista;
}
```

### JUnit

```java
public class PrimTest extends TestCase {

    public void testPrimArbolTieneV1Aristas() {
        GrafoNoDirigido<String, WeightedEdge> grafo = new GrafoNoDirigido<>();
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarVertice("C");
        grafo.agregarVertice("D");
        grafo.agregarArista("A", "B", new WeightedEdge(2));
        grafo.agregarArista("A", "C", new WeightedEdge(5));
        grafo.agregarArista("B", "C", new WeightedEdge(1));
        grafo.agregarArista("B", "D", new WeightedEdge(3));
        grafo.agregarArista("C", "D", new WeightedEdge(4));

        AlgoritmosGrafoNoDirigido alg = new AlgoritmosGrafoNoDirigido();
        IUndirectedGraph<String, WeightedEdge> arbol = alg.prim(grafo, grafo.construirComparable("A"));

        assertEquals(4, arbol.cantidadDeVertices());
        assertEquals(3, arbol.cantidadDeAristas());
    }

    public void testPrimGrafoVacio() {
        GrafoNoDirigido<String, WeightedEdge> grafo = new GrafoNoDirigido<>();
        AlgoritmosGrafoNoDirigido alg = new AlgoritmosGrafoNoDirigido();
        IUndirectedGraph<String, WeightedEdge> arbol = alg.prim(grafo, null);
        assertEquals(0, arbol.cantidadDeVertices());
    }
}
```

---

## Kruskal — Árbol generador mínimo (grafo no dirigido)

### Lenguaje natural

Kruskal construye el árbol generador mínimo ordenando todas las aristas por peso de menor a mayor y agregándolas de a una al resultado. Una arista se agrega solo si los dos vértices que une están en componentes distintas (es decir, unirlos no forma un ciclo). Para verificar si formarían ciclo se usan grupos (union-find simplificado): cada vértice empieza en su propio grupo y cuando se acepta una arista los dos grupos se fusionan.

### Precondición
- El grafo es no dirigido con aristas pesadas.

### Postcondición
- Retorna un nuevo grafo no dirigido con exactamente V-1 aristas que forman el árbol generador mínimo.

### Pseudocódigo

```
kruskal(G):
    arbol ← nuevo GrafoNoDirigido con todos los vértices de G
    aristas ← todas las aristas de G ordenadas por peso ascendente
    grupos ← lista de conjuntos, uno por vértice ({A}, {B}, {C}, ...)

    Para cada arista en aristas:
        u ← arista.source()
        v ← arista.target()
        grupoU ← grupo que contiene a u
        grupoV ← grupo que contiene a v
        Si grupoU ≠ grupoV:
            arbol.agregarArista(u, v, arista.dato())
            grupoU.agregar todos los de grupoV
            grupos.remover(grupoV)

    retornar arbol
```

**Ordenar por peso (selection sort, sin lambdas):**
```
Para i = 0 .. n-1:
    minIdx ← i
    Para j = i+1 .. n-1:
        Si aristas[j].dato().getWeight() < aristas[minIdx].dato().getWeight():
            minIdx ← j
    intercambiar aristas[i] con aristas[minIdx]
```

### Java

```java
@Override
public <V, D extends WeightedEdge> IUndirectedGraph<V, D> kruskal(IUndirectedGraph<V, D> grafo) {
    GrafoNoDirigido<V, D> arbol = new GrafoNoDirigido<>();
    for (V v : grafo.vertices()) {
        arbol.agregarVertice(v);
    }

    List<Edge<V, D>> aristas = new ArrayList<>();
    for (Edge<V, D> arista : grafo.aristas()) {
        aristas.add(arista);
    }

    // selection sort por peso — sin lambdas
    for (int i = 0; i < aristas.size(); i++) {
        int minIdx = i;
        for (int j = i + 1; j < aristas.size(); j++) {
            if (aristas.get(j).dato().getWeight() < aristas.get(minIdx).dato().getWeight()) {
                minIdx = j;
            }
        }
        Edge<V, D> temp = aristas.get(i);
        aristas.set(i, aristas.get(minIdx));
        aristas.set(minIdx, temp);
    }

    // cada nodo empieza en su propio grupo (union-find simple)
    List<Set<V>> grupos = new ArrayList<>();
    for (V v : grafo.vertices()) {
        Set<V> grupo = new HashSet<>();
        grupo.add(v);
        grupos.add(grupo);
    }

    for (Edge<V, D> arista : aristas) {
        V source = arista.source();
        V target = arista.target();

        Set<V> grupoSource = null;
        Set<V> grupoTarget = null;
        boolean encontrados = false;
        for (Set<V> grupo : grupos) {
            if (!encontrados) {
                if (grupo.contains(source)) {
                    grupoSource = grupo;
                }
                if (grupo.contains(target)) {
                    grupoTarget = grupo;
                }
                if (grupoSource != null) {
                    if (grupoTarget != null) {
                        encontrados = true;
                    }
                }
            }
        }

        if (grupoSource != grupoTarget) {
            arbol.agregarArista(source, target, arista.dato());
            grupoSource.addAll(grupoTarget);
            grupos.remove(grupoTarget);
        }
    }

    return arbol;
}
```

### JUnit

```java
public class KruskalTest extends TestCase {

    public void testKruskalArbolTieneV1Aristas() {
        GrafoNoDirigido<String, WeightedEdge> grafo = new GrafoNoDirigido<>();
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarVertice("C");
        grafo.agregarVertice("D");
        grafo.agregarArista("A", "B", new WeightedEdge(2));
        grafo.agregarArista("A", "C", new WeightedEdge(5));
        grafo.agregarArista("B", "C", new WeightedEdge(1));
        grafo.agregarArista("B", "D", new WeightedEdge(3));
        grafo.agregarArista("C", "D", new WeightedEdge(4));

        AlgoritmosGrafoNoDirigido alg = new AlgoritmosGrafoNoDirigido();
        IUndirectedGraph<String, WeightedEdge> arbol = alg.kruskal(grafo);

        assertEquals(4, arbol.cantidadDeVertices());
        assertEquals(3, arbol.cantidadDeAristas());
    }

    public void testKruskalEligeMenorPeso() {
        // A-B:1, A-C:10, B-C:1 -> AGM usa A-B y B-C, costo 2
        GrafoNoDirigido<String, WeightedEdge> grafo = new GrafoNoDirigido<>();
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarVertice("C");
        grafo.agregarArista("A", "B", new WeightedEdge(1));
        grafo.agregarArista("A", "C", new WeightedEdge(10));
        grafo.agregarArista("B", "C", new WeightedEdge(1));

        AlgoritmosGrafoNoDirigido alg = new AlgoritmosGrafoNoDirigido();
        IUndirectedGraph<String, WeightedEdge> arbol = alg.kruskal(grafo);

        assertEquals(2, arbol.cantidadDeAristas());
        assertFalse(arbol.existeArista(grafo.construirComparable("A"), grafo.construirComparable("C")));
    }
}
```

---

## Puntos de articulación (grafo no dirigido)

### Lenguaje natural

Un punto de articulación es un vértice cuya eliminación desconecta el grafo. El algoritmo usa DFS y para cada vértice calcula dos valores: `disc` (tiempo en que fue descubierto) y `low` (el menor `disc` al que puede llegar cualquier vértice en su subárbol mediante aristas de retroceso). Un vértice `u` es punto de articulación si:

- Es la raíz del DFS y tiene más de un hijo en el árbol DFS, o
- No es raíz y tiene algún hijo `v` tal que `low[v] >= disc[u]` (el hijo no puede "escapar" pasando por encima de `u`).

### Precondición
- El grafo es no dirigido y conexo.

### Postcondición
- Retorna la lista de vértices que son puntos de articulación (puede estar vacía si ninguno lo es).

### Pseudocódigo

```
puntosDeArticulacion(G):
    disc ← mapa vacío       // tiempo de descubrimiento
    low  ← mapa vacío       // tiempo mínimo alcanzable
    padres ← mapa vacío     // predecesor en el árbol DFS
    visitados ← conjunto vacío
    tiempo ← 0
    resultado ← lista vacía

    Para cada v en G.vertices():
        Si v no en visitados:
            dfsArticulacion(G, v, disc, low, padres, visitados, tiempo, resultado)

    retornar resultado

dfsArticulacion(G, actual, disc, low, padres, visitados, tiempo, resultado):
    agregar actual a visitados
    tiempo ← tiempo + 1
    disc[actual] ← tiempo
    low[actual]  ← tiempo
    hijosEnArbol ← 0

    Para cada arista en G.adyacencias(actual):
        vecino ← arista.target()
        Si vecino no en visitados:
            hijosEnArbol ← hijosEnArbol + 1
            padres[vecino] ← actual
            dfsArticulacion(G, vecino, disc, low, padres, visitados, tiempo, resultado)
            Si low[vecino] < low[actual]:
                low[actual] ← low[vecino]
            // condicion de punto de articulacion (no raiz)
            Si actual no es raiz:
                Si low[vecino] >= disc[actual]:
                    Si actual no en resultado:
                        resultado.agregar(actual)
        Sino:
            // arista de retroceso — actualizar low si no es el padre directo
            Si vecino ≠ padres[actual]:
                Si disc[vecino] < low[actual]:
                    low[actual] ← disc[vecino]

    // condicion de raiz
    Si actual es raiz Y hijosEnArbol > 1:
        Si actual no en resultado:
            resultado.agregar(actual)
```

### Java

```java
public <V, D> List<V> puntosDeArticulacion(IGraph<V, D> grafo) {
    List<V> resultado = new ArrayList<>();
    HashMap<V, Integer> disc = new HashMap<>();
    HashMap<V, Integer> low = new HashMap<>();
    HashMap<V, V> padres = new HashMap<>();
    Set<V> visitados = new HashSet<>();
    int[] tiempo = {0};

    for (V v : grafo.vertices()) {
        if (!visitados.contains(v)) {
            dfsArticulacion(grafo, v, disc, low, padres, visitados, tiempo, resultado);
        }
    }
    return resultado;
}

private <V, D> void dfsArticulacion(
        IGraph<V, D> grafo, V actual,
        HashMap<V, Integer> disc, HashMap<V, Integer> low,
        HashMap<V, V> padres, Set<V> visitados,
        int[] tiempo, List<V> resultado) {

    visitados.add(actual);
    tiempo[0]++;
    disc.put(actual, tiempo[0]);
    low.put(actual, tiempo[0]);
    int hijosEnArbol = 0;

    for (Edge<V, D> arista : grafo.adyacencias(grafo.construirComparable(actual))) {
        V vecino = arista.target();
        if (!visitados.contains(vecino)) {
            hijosEnArbol++;
            padres.put(vecino, actual);
            dfsArticulacion(grafo, vecino, disc, low, padres, visitados, tiempo, resultado);

            if (low.get(vecino) < low.get(actual)) {
                low.put(actual, low.get(vecino));
            }

            boolean esRaiz = !padres.containsKey(actual);
            if (!esRaiz) {
                if (low.get(vecino) >= disc.get(actual)) {
                    if (!resultado.contains(actual)) {
                        resultado.add(actual);
                    }
                }
            }
        } else {
            V padre = padres.get(actual);
            if (!vecino.equals(padre)) {
                if (disc.get(vecino) < low.get(actual)) {
                    low.put(actual, disc.get(vecino));
                }
            }
        }
    }

    boolean esRaizFinal = !padres.containsKey(actual);
    if (esRaizFinal) {
        if (hijosEnArbol > 1) {
            if (!resultado.contains(actual)) {
                resultado.add(actual);
            }
        }
    }
}
```

### JUnit

```java
public class PuntosDeArticulacionTest extends TestCase {

    public void testCaminoLinealB_EsPuntoDeArticulacion() {
        // A - B - C: B es el unico punto de articulacion
        GrafoNoDirigido<String, String> grafo = new GrafoNoDirigido<>();
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarVertice("C");
        grafo.agregarArista("A", "B", "");
        grafo.agregarArista("B", "C", "");

        AlgoritmosGrafoNoDirigido alg = new AlgoritmosGrafoNoDirigido();
        List<String> puntos = alg.puntosDeArticulacion(grafo);

        assertEquals(1, puntos.size());
        assertTrue(puntos.contains("B"));
    }

    public void testTrianguloSinPuntosDeArticulacion() {
        // A - B - C - A: todos tienen grado 2, ningun punto de articulacion
        GrafoNoDirigido<String, String> grafo = new GrafoNoDirigido<>();
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarVertice("C");
        grafo.agregarArista("A", "B", "");
        grafo.agregarArista("B", "C", "");
        grafo.agregarArista("C", "A", "");

        AlgoritmosGrafoNoDirigido alg = new AlgoritmosGrafoNoDirigido();
        List<String> puntos = alg.puntosDeArticulacion(grafo);

        assertEquals(0, puntos.size());
    }

    public void testGrafoConDosAristas_AmbosPuntosDeArticulacion() {
        // A - B - C - D: B y C son puntos de articulacion
        GrafoNoDirigido<String, String> grafo = new GrafoNoDirigido<>();
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarVertice("C");
        grafo.agregarVertice("D");
        grafo.agregarArista("A", "B", "");
        grafo.agregarArista("B", "C", "");
        grafo.agregarArista("C", "D", "");

        AlgoritmosGrafoNoDirigido alg = new AlgoritmosGrafoNoDirigido();
        List<String> puntos = alg.puntosDeArticulacion(grafo);

        assertEquals(2, puntos.size());
        assertTrue(puntos.contains("B"));
        assertTrue(puntos.contains("C"));
    }
}
```
