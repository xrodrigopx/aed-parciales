# Pseudocódigos Completos — Parcial 2

Referencia unificada de todos los pseudocódigos del curso (UT3 + UT4 + Sorting), organizados para consulta rápida en el parcial.

---

## Índice

**UT3**
1. [Árbol Genérico](#árbol-genérico)
2. [Hash](#hash)
3. [Diccionario](#diccionario)
4. [Mapa](#mapa)
5. [Trie](#trie)
6. [Trie Patricia](#trie-patricia)

**Sorting**
7. [Inserción](#inserción)
8. [Heapsort](#heapsort)
9. [Quicksort](#quicksort)

**UT4 — Grafos**
10. [Dijkstra](#dijkstra)
11. [Floyd](#floyd)
12. [Warshall](#warshall)
13. [DFS (BPF)](#dfs--búsqueda-en-profundidad)
14. [BEA](#bea--búsqueda-en-amplitud)
15. [Clasificación topológica](#clasificación-topológica)
16. [Detección de ciclos](#detección-de-ciclos)
17. [Todos los caminos](#todos-los-caminos)
18. [Prim](#prim)
19. [Kruskal](#kruskal)
20. [Puntos de articulación](#puntos-de-articulación)

---

## Árbol Genérico

**Nodo:** `dato`, `hijos: List<NodoGenerico>`  
**Representación:** primer hijo — hermano derecho (convierte AG en árbol binario)

### agregarHijo

```
agregarHijo(padre: T, hijo: T): booleano
  si raiz = nulo: retornar falso
  nodoPadre ← buscarNodo(raiz, padre)
  si nodoPadre = nulo: retornar falso
  para cada h en nodoPadre.hijos:
      si h.dato = hijo: retornar falso
  nodoPadre.hijos.agregar(NodoGenerico(hijo))
  retornar verdadero
```

**O(n)**

### buscarNodo (auxiliar)

```
buscarNodo(nodo: NodoGenerico, criterio: T): NodoGenerico
  si nodo = nulo: retornar nulo
  si nodo.dato = criterio: retornar nodo
  para cada hijo en nodo.hijos:
      encontrado ← buscarNodo(hijo, criterio)
      si encontrado ≠ nulo: retornar encontrado
  retornar nulo
```

### eliminar

```
eliminar(criterio: T): void
  si raiz.dato = criterio:
      raiz ← nulo
      retornar
  eliminarEnSubarbol(raiz, criterio)

eliminarEnSubarbol(nodo, criterio):
  i ← 0
  mientras i < nodo.hijos.tamaño():
      si nodo.hijos[i].dato = criterio:
          nodo.hijos.eliminar(i)
          retornar
      i ← i + 1
  para cada hijo en nodo.hijos:
      eliminarEnSubarbol(hijo, criterio)
```

### obtenerPadre

```
obtenerPadre(criterio: T): T
  si raiz.dato = criterio: retornar nulo
  nodoPadre ← obtenerPadreNodo(raiz, criterio)
  si nodoPadre = nulo: retornar nulo
  retornar nodoPadre.dato

obtenerPadreNodo(nodo, criterio):
  para cada hijo en nodo.hijos:
      si hijo.dato = criterio: retornar nodo
  para cada hijo en nodo.hijos:
      resultado ← obtenerPadreNodo(hijo, criterio)
      si resultado ≠ nulo: retornar resultado
  retornar nulo
```

### preOrden / postOrden

```
preOrden(nodo):
  visitar(nodo)
  para cada hijo en nodo.hijos:
      preOrden(hijo)

postOrden(nodo):
  para cada hijo en nodo.hijos:
      postOrden(hijo)
  visitar(nodo)
```

### altura

```
altura(nodo): entero
  si nodo.hijos está vacío: retornar 0
  max ← 0
  para cada hijo en nodo.hijos:
      h ← altura(hijo)
      si h > max: max ← h
  retornar max + 1
```

---

## Hash

**h(K) = K mod N** — usar N primo.  
**Factor de carga α = M/N** — redimensionar si α > 0.70.

### Encadenamiento directo (Chaining)

```
insertar_chaining(clave, valor):
  pos ← h(clave)
  tabla[pos].agregarAlFrente(clave, valor)      // O(1)

buscar_chaining(clave):
  pos ← h(clave)
  para cada nodo en tabla[pos]:
      si nodo.clave = clave: retornar nodo.valor
  retornar nulo

eliminar_chaining(clave):
  pos ← h(clave)
  para cada nodo en tabla[pos]:
      si nodo.clave = clave:
          tabla[pos].eliminar(nodo)
          retornar verdadero
  retornar falso
```

### Open Addressing — sondeo lineal

**Tombstone (loteLibre):** al eliminar, marcar como libre en lugar de null — si se pone null se rompe la cadena de búsqueda.

```
insertar(clave, valor):
  si factorDeCarga > 0.70: redimensionar()
  h0 ← funcionHash(clave)
  primerLibre ← -1
  i ← 0
  mientras i < tabla.tamaño():
      pos ← (h0 + i) mod tabla.tamaño()
      si tabla[pos] = nulo:
          si primerLibre = -1: primerLibre ← pos
          salir
      sino si tabla[pos].loteLibre:
          si primerLibre = -1: primerLibre ← pos
      sino si tabla[pos].clave = clave:
          retornar falso
      i ← i + 1
  tabla[primerLibre] ← NodoHash(clave, valor)
  retornar verdadero

buscar(clave):
  h0 ← funcionHash(clave)
  i ← 0
  mientras i < tabla.tamaño():
      pos ← (h0 + i) mod tabla.tamaño()
      si tabla[pos] = nulo: retornar nulo
      si no tabla[pos].loteLibre:
          si tabla[pos].clave = clave: retornar tabla[pos].valor
      i ← i + 1
  retornar nulo

eliminar(clave):
  h0 ← funcionHash(clave)
  i ← 0
  mientras i < tabla.tamaño():
      pos ← (h0 + i) mod tabla.tamaño()
      si tabla[pos] = nulo: retornar falso
      si no tabla[pos].loteLibre:
          si tabla[pos].clave = clave:
              tabla[pos].loteLibre ← verdadero
              retornar verdadero
      i ← i + 1
  retornar falso
```

| Op | Promedio | Peor |
|----|----------|------|
| Búsqueda | O(1) | O(n) |
| Inserción | O(1) | O(n) |
| Eliminación | O(1) | O(n) |

---

## Diccionario

**Permite múltiples valores por clave.** `insertar` siempre agrega (nunca reemplaza).

Implementación: `Map<K, List<V>>`

```
insertar(clave, valor):
  si diccionario.contiene(clave):
      diccionario.obtener(clave).agregar(valor)
  sino:
      lista ← Lista vacía
      lista.agregar(valor)
      diccionario.poner(clave, lista)

buscarTodos(clave):
  si no diccionario.contiene(clave): retornar Lista vacía
  retornar diccionario.obtener(clave)
```

**Diferencia con Mapa:** Mapa tiene clave única, `poner` reemplaza. Diccionario permite duplicados, `insertar` siempre agrega.

---

## Mapa

**Clave única.** `poner(k,v)` reemplaza si k ya existe.

### Patrones Java clave

```java
// Contar frecuencias
Map<String, Integer> frecuencias = new HashMap<>();
Integer actual = frecuencias.get(palabra);
if (actual == null) {
    frecuencias.put(palabra, 1);
} else {
    frecuencias.put(palabra, actual + 1);
}

// Agrupar elementos
Map<String, List<String>> grupos = new HashMap<>();
List<String> lista = grupos.get(clave);
if (lista == null) {
    lista = new ArrayList<>();
    grupos.put(clave, lista);
}
lista.add(elemento);

// Buscar máximo por valor
String claveMax = null;
int valorMax = Integer.MIN_VALUE;
for (Map.Entry<String, Integer> entry : mapa.entrySet()) {
    if (entry.getValue() > valorMax) {
        valorMax = entry.getValue();
        claveMax = entry.getKey();
    }
}
```

| Clase | Complejidad | Orden |
|-------|-------------|-------|
| `HashMap` | O(1) prom. | Sin orden |
| `LinkedHashMap` | O(1) prom. | Inserción |
| `TreeMap` | O(log n) | Natural de claves |

---

## Trie

**Nodo:** `esPalabra: boolean`, `dato: T`, `hijos: Map<Character, NodoTrie>`

### insertar

```
insertar(palabra, dato):
  si raiz = nulo: raiz ← NodoTrie()
  nodo ← raiz
  para cada carácter c en palabra:
      si nodo.hijos no contiene c:
          nodo.hijos.poner(c, NodoTrie())
      nodo ← nodo.hijos.obtener(c)
  eraExistente ← nodo.esPalabra
  nodo.esPalabra ← verdadero
  nodo.dato ← dato
  retornar no eraExistente
```

**O(m)** donde m = largo de la palabra.

### buscar

```
buscar(palabra):
  si raiz = nulo: retornar nulo
  nodo ← raiz
  para cada carácter c en palabra:
      si nodo.hijos no contiene c: retornar nulo
      nodo ← nodo.hijos.obtener(c)
  retornar Entry(nodo.dato, nodo.esPalabra, palabra)
```

Retorna `esPalabra=verdadero` si es palabra completa, `esPalabra=falso` si es solo prefijo.

### predecir (búsqueda de prefijo)

```
predecir(prefijo):
  resultado ← Lista vacía
  nodo ← raiz
  para cada carácter c en prefijo:
      si nodo.hijos no contiene c: retornar resultado
      nodo ← nodo.hijos.obtener(c)
  recolectarPalabras(nodo, prefijo, resultado)
  retornar resultado

recolectarPalabras(nodo, palabraActual, resultado):
  si nodo.esPalabra:
      resultado.agregar(Entry(nodo.dato, verdadero, palabraActual))
  para cada (c, hijo) en nodo.hijos:
      recolectarPalabras(hijo, palabraActual + c, resultado)
```

### eliminar

```
eliminar(palabra):
  nodo ← raiz
  para cada carácter c en palabra:
      si nodo.hijos no contiene c: retornar falso
      nodo ← nodo.hijos.obtener(c)
  si no nodo.esPalabra: retornar falso
  nodo.esPalabra ← falso
  nodo.dato ← nulo
  retornar verdadero
```

---

## Trie Patricia

**Comprime nodos de un solo hijo.** Aristas llevan substrings (tríadas `i, j, k`).  
Todos los nodos internos tienen ≥ 2 hijos → tamaño O(s) en vez de O(n).

```
buscar(raiz, patron):
  nodo ← raiz
  posicion ← 0
  mientras nodo no es hoja:
      arista ← elegirHijo(nodo, patron, posicion)
      si arista = nulo: retornar falso
      longitud ← arista.k - arista.j + 1
      substring ← S[arista.i][arista.j..arista.k]
      si patron[posicion..posicion+longitud-1] ≠ substring: retornar falso
      posicion ← posicion + longitud
      nodo ← arista.destino
  retornar patron = S[nodo.indice]
```

---

## Inserción

**Cuándo:** datos casi ordenados + memoria limitada.  
**Complejidad:** mejor O(n), promedio/peor O(n²), espacio O(1).

```
insercion(datos: double[], n):
  i ← 1
  mientras i < n:
    clave ← datos[i]
    j ← i - 1
    mientras j >= 0 Y datos[j] > clave:
      datos[j + 1] ← datos[j]
      j ← j - 1
    datos[j + 1] ← clave
    i ← i + 1
```

---

## Heapsort

**Cuándo:** peor caso garantizado O(n log n) + memoria limitada.  
**Complejidad:** O(n log n) todos los casos, espacio O(1).

```
hundir(datos, i, tamanioHeap):
  izq ← 2*i+1 ; der ← 2*i+2 ; maximo ← i
  si izq < tamanioHeap:
    si datos[izq] > datos[maximo]: maximo ← izq
  si der < tamanioHeap:
    si datos[der] > datos[maximo]: maximo ← der
  si maximo ≠ i:
    intercambiar datos[i] con datos[maximo]
    hundir(datos, maximo, tamanioHeap)

heapsort(datos, n):
  // Fase 1: construir max-heap desde el último nodo interior
  i ← n/2 - 1
  mientras i >= 0:
    hundir(datos, i, n)
    i ← i - 1

  // Fase 2: extraer máximo repetidamente
  i ← n - 1
  mientras i > 0:
    intercambiar datos[0] con datos[i]
    hundir(datos, 0, i)
    i ← i - 1
```

---

## Quicksort

**Cuándo:** buen promedio, pocas comparaciones, sin garantía de peor caso.  
**Complejidad:** promedio O(n log n), peor O(n²), espacio O(log n).

```
particionar(datos, inicio, fin):
  pivote ← datos[fin]
  i ← inicio - 1
  j ← inicio
  mientras j < fin:
    si datos[j] <= pivote:
      i ← i + 1
      intercambiar datos[i] con datos[j]
    j ← j + 1
  intercambiar datos[i+1] con datos[fin]
  retornar i + 1

quicksort(datos, inicio, fin):
  si inicio < fin:
    posPivote ← particionar(datos, inicio, fin)
    quicksort(datos, inicio, posPivote - 1)
    quicksort(datos, posPivote + 1, fin)
```

**Descendente:** cambiar `datos[j] <= pivote` por `datos[j] >= pivote` en particionar.

---

## Dijkstra

**Cuándo:** camino de menor costo desde un origen a todos (o a un destino específico). Solo pesos ≥ 0.  
**Complejidad:** O(V²) implementación simple.

```
dijkstra(origen, C, V):
  S ← {origen}
  Para todo v en V:
      D[v] = C[origen, v]     // ∞ si no hay arco directo
      P[v] = origen
  D[origen] = 0

  Mientras V ≠ S:
      w ← vértice en V-S tal que D[w] sea mínimo
      agregar w a S
      Para todo v en V-S:
          Si D[w] + C[w,v] < D[v]:
              D[v] = D[w] + C[w,v]
              P[v] = w

recuperarCamino(origen, destino, P):
  camino ← lista vacía
  actual ← destino
  mientras actual ≠ origen:
      camino.insertarAlFrente(actual)
      actual ← P[actual]
  camino.insertarAlFrente(origen)
  retornar camino
```

**Variantes:**
- Con aristas bloqueadas: agregar condición `Y H[w,v] = verdadero` en la relajación
- Con parada obligatoria en v: Dijkstra(origen) + Dijkstra(v), sumar costos

---

## Floyd

**Cuándo:** camino mínimo entre **todos** los pares. O(V³).

```
floyd(C, n):
  Para todo i,j: A[i,j] = C[i,j]; P[i,j] = 0
  Para todo i:   A[i,i] = 0

  Para k = 1..n:
      Para i = 1..n:
          Para j = 1..n:
              Si A[i,k] + A[k,j] < A[i,j]:
                  A[i,j] = A[i,k] + A[k,j]
                  P[i,j] = k

camino(i, j, P):
  k ← P[i,j]
  Si k = 0: retornar   // arco directo
  camino(i, k, P)
  imprimir k
  camino(k, j, P)
```

**Excentricidad de v** = max de la **columna v** en A.  
**Centro** = vértice con menor excentricidad.

---

## Warshall

**Cuándo:** solo verificar si **existe** camino entre cada par (alcanzabilidad). O(V³).

```
warshall(C, n):
  Para todo i,j: A[i,j] = C[i,j]

  Para k = 1..n:
      Para i = 1..n:
          Para j = 1..n:
              Si A[i,j] = falso:
                  A[i,j] = A[i,k] Y A[k,j]
```

**Diferencia:** Floyd minimiza costos. Warshall propaga conectividad booleana.

---

## DFS — Búsqueda en profundidad

**Cuándo:** recorrer el grafo, detectar ciclos, topológica, puntos de articulación.

```
dfs(G, consumer):
  visitados ← conjunto vacío
  Para cada v en G.vertices():
      si v no en visitados:
          dfsAux(v, visitados, G, consumer)

dfsAux(v, visitados, G, consumer):
  agregar v a visitados
  consumer.accept(v)
  Para cada w en G.adyacencias(v):
      si w no en visitados:
          dfsAux(w, visitados, G, consumer)
```

| Tipo de arco | Identificación |
|---|---|
| Árbol | destino no visitado |
| Retroceso | destino activo en la pila (antecesor) |
| Avance | destino ya terminado, descendiente |
| Cruzado | destino ya terminado, no relacionado |

---

## BEA — Búsqueda en amplitud

**Cuándo:** distancias en saltos, componentes conectadas, número de Bacon.

```
bea(origen, G, consumer):
  visitados ← conjunto vacío
  cola ← cola vacía
  agregar origen a visitados
  encolar origen

  Mientras cola no vacía:
      v ← desencolar
      consumer.accept(v)
      Para cada w en G.adyacencias(v):
          si w no en visitados:
              agregar w a visitados
              encolar w
```

**BEA calcula distancias mínimas en grafos sin pesos** (número de saltos). Con pesos → Dijkstra.

---

## Clasificación topológica

**Cuándo:** ordenar tareas con dependencias (solo en GDA — grafo acíclico dirigido).

```
topologica(G):
  visitados ← conjunto vacío
  lista ← lista vacía
  Para cada v en G.vertices():
      topologicaAux(v, visitados, lista, G)
  retornar lista

topologicaAux(nodo, visitados, lista, G):
  si nodo no en visitados:
      agregar nodo a visitados
      Para cada w en G.adyacencias(nodo):
          topologicaAux(w, visitados, lista, G)
      insertar nodo AL PRINCIPIO de lista   ← clave: en la salida, no en la entrada
```

---

## Detección de ciclos

```
tieneCiclos(G):
  enRecursion ← conjunto vacío
  Para cada v en G.vertices():
      si tieneCiclosAux(v, enRecursion, G):
          retornar verdadero
  retornar falso

tieneCiclosAux(v, enRecursion, G):
  agregar v a enRecursion
  Para cada w en G.adyacencias(v):
      si w en enRecursion: retornar verdadero
      si tieneCiclosAux(w, enRecursion, G): retornar verdadero
  remover v de enRecursion
  retornar falso
```

---

## Todos los caminos

**DFS con backtracking.** Clave: desmarcar el vértice al retroceder.

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
  si actual = destino:
      resultado.agregar(copia de camino)
  sino:
      Para cada w en G.adyacencias(actual):
          si w no en visitados:
              todosLosCaminosAux(w, destino, visitados, camino, resultado, G)
  camino.eliminarUltimo()
  remover actual de visitados    ← backtracking
```

**Con restricción de tipo de nodo** (ej. procesadores solo conectan por switches):
```
Para cada w en G.adyacencias(actual):
    si w no en visitados:
        si no (tipoDeNodo(actual) = PROCESADOR Y tipoDeNodo(w) = PROCESADOR):
            todosLosCaminosAux(w, destino, ...)
```

---

## Prim

**Cuándo:** árbol generador mínimo, grafo **no dirigido** con pesos.  
**Complejidad:** O(V·E) implementación simple.

```
prim(G, origen):
  arbol ← nuevo GrafoNoDirigido con todos los vértices de G
  U ← {origen}
  noU ← G.vertices() \ {origen}

  Mientras noU no vacío:
      minArista ← searchMinEdge(G, U, noU)
      si minArista = nulo: detener  // grafo no conexo
      nuevoVertice ← minArista.target()
      U.agregar(nuevoVertice)
      noU.remover(nuevoVertice)
      arbol.agregarArista(minArista.source(), minArista.target(), minArista.dato())

  retornar arbol

searchMinEdge(G, U, noU):
  minArista ← nulo ; minPeso ← ∞
  Para cada u en U:
      Para cada arista en G.adyacencias(u):
          si arista.target() en noU Y arista.dato().getWeight() < minPeso:
              minPeso ← arista.dato().getWeight()
              minArista ← arista
  retornar minArista
```

---

## Kruskal

**Cuándo:** árbol generador mínimo, grafo **no dirigido** con pesos.  
**Complejidad:** O(E log E) por el ordenamiento.

```
kruskal(G):
  arbol ← nuevo GrafoNoDirigido con todos los vértices de G
  aristas ← todas las aristas de G ordenadas por peso (selection sort)
  grupos ← [{A}, {B}, {C}, ...]   // un grupo por vértice

  Para cada arista en aristas:
      u ← arista.source() ; v ← arista.target()
      grupoU ← grupo que contiene u
      grupoV ← grupo que contiene v
      si grupoU ≠ grupoV:
          arbol.agregarArista(u, v, arista.dato())
          grupoU.agregar todos de grupoV
          grupos.remover(grupoV)

  retornar arbol
```

**Ordenar por peso (selection sort sin lambdas):**
```
Para i = 0..n-1:
  minIdx ← i
  Para j = i+1..n-1:
      si aristas[j].peso < aristas[minIdx].peso: minIdx ← j
  intercambiar aristas[i] con aristas[minIdx]
```

**Prim vs Kruskal:**
| | Prim | Kruskal |
|---|---|---|
| Crece | un vértice por vez | una arista por vez |
| Mejor para | grafos densos | grafos dispersos |
| Estructura auxiliar | conjuntos U/noU | union-find (grupos) |

---

## Puntos de articulación

**Cuándo:** vértices cuya eliminación desconecta el grafo. Grafo **no dirigido**.

**Regla:** `u` es punto de articulación si:
- Es raíz del DFS y tiene ≥ 2 hijos en el árbol DFS, **o**
- No es raíz y existe hijo `v` con `low[v] >= disc[u]`

```
puntosDeArticulacion(G):
  disc ← mapa vacío ; low ← mapa vacío
  padres ← mapa vacío ; visitados ← conjunto vacío
  tiempo ← 0 ; resultado ← lista vacía
  Para cada v en G.vertices():
      si v no en visitados:
          dfsArtic(G, v, disc, low, padres, visitados, tiempo, resultado)
  retornar resultado

dfsArtic(G, actual, disc, low, padres, visitados, tiempo, resultado):
  agregar actual a visitados
  tiempo ← tiempo + 1
  disc[actual] ← tiempo ; low[actual] ← tiempo
  hijosEnArbol ← 0

  Para cada arista en G.adyacencias(actual):
      vecino ← arista.target()
      si vecino no en visitados:
          hijosEnArbol ← hijosEnArbol + 1
          padres[vecino] ← actual
          dfsArtic(G, vecino, ...)
          si low[vecino] < low[actual]: low[actual] ← low[vecino]
          // condición no-raíz
          si actual no es raíz:
              si low[vecino] >= disc[actual]:
                  si actual no en resultado: resultado.agregar(actual)
      sino:
          // arista de retroceso
          si vecino ≠ padres[actual]:
              si disc[vecino] < low[actual]: low[actual] ← disc[vecino]

  // condición raíz
  si actual es raíz Y hijosEnArbol > 1:
      si actual no en resultado: resultado.agregar(actual)
```

**`tiempo` en Java:** usar `int[] tiempo = {0}` para pasar mutable a método recursivo.
