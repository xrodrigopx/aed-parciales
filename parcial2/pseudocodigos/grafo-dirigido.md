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
