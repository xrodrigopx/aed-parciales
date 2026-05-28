# Ejemplos Prácticos — Algoritmos de Grafos

---

## DIJKSTRA

- **Función:** Calcula camino mínimo desde un vértice origen, a los demás.
- **Orden de ejecución:** O(V^2)

---

## DIJKSTRA — PROBLEMA 1

**Enunciado:** Dado un grafo dirigido con pesos positivos, se bloquea temporalmente un conjunto de aristas. Calcula el camino más corto desde un nodo fuente a todos los nodos, ignorando las aristas bloqueadas.

**Pseudocódigo original (Dijkstra base):**

```
procedure DIJKSTRA(origen, G, C)
  for v ∈ vertices(G) do
      D[v] ← Inf
      P[v] ← ∅
  end for

  NoVisitados ← vertices(G)
  D[origen] ← 0

  while NoVisitados ≠ ∅ do
      elegir w en NoVisitados tal que D[w] sea mínimo
      remover w de NoVisitados
      for v ∈ NoVisitados do
          if D[w] + C[w, v] < D[v] then
              D[v] ← D[w] + C[w, v]
              P[v] ← w
          end if
      end for
  end while
end procedure
```

**Precisamos:** Una matriz de booleanos que defina para cada par de vértices, si la arista está disponible.

**Solución — Modificación:** Adaptar Dijkstra para que omita ciertas aristas dadas como "bloqueadas".

```
procedure DIJKSTRA(origen, G, C, H)
  for v ∈ vertices(G) do
      D[v] ← Inf
      P[v] ← ∅
  end for

  NoVisitados ← vertices(G)
  D[origen] ← 0

  while NoVisitados ≠ ∅ do
      elegir w en NoVisitados tal que D[w] sea mínimo
      remover w de NoVisitados
      for v ∈ NoVisitados do
          if D[w] + C[w, v] < D[v] y H[w, v] then
              D[v] ← D[w] + C[w, v]
              P[v] ← w
          end if
      end for
  end while
end procedure
```

---

## DIJKSTRA — PROBLEMA 2

**Enunciado:** *Dijkstra con parada obligatoria.* Desde un nodo fuente **s** hasta un destino **t**, se debe pasar obligatoriamente por un nodo intermedio **v**.

**Solución:** Ejecutar Dijkstra dos veces: de **s** a **v** y luego de **v** a **t**. Combinar los resultados.

---

## DIJKSTRA — PROBLEMA 3

**Enunciado:** Algunas rutas están disponibles solo en ciertos horarios. Dado un grafo y una hora, encontrar el camino más corto respetando esos horarios.

**Sugerencia:** Modelar la disponibilidad de las aristas, como un vector de 0 a 23, donde cada entrada contiene una matriz indicando si está disponible o no la arista.

```
procedure DIJKSTRA(origen, G, C, H, hora)
  for v ∈ vertices(G) do
      D[v] ← Inf
      P[v] ← ∅
  end for

  NoVisitados ← vertices(G)
  D[origen] ← 0

  while NoVisitados ≠ ∅ do
      elegir w en NoVisitados tal que D[w] sea mínimo
      remover w de NoVisitados
      for v ∈ NoVisitados do
          if D[w] + C[w, v] < D[v] y H[hora][w, v] then
              D[v] ← D[w] + C[w, v]
              P[v] ← w
          end if
      end for
  end while
end procedure
```

---

## FLOYD-WARSHALL

```
procedure FLOYD(A, C, P, n)
  for i ← 0; i < n do
      for j ← 0; j < n do
          A[i, j] ← C[i, j]
          P[i, j] ← 0
      end for
  end for

  for k ← 0; k < n do
      for i ← 0; i < n do
          for j ← 0; j < n do
              if A[i, k] + A[k, j] < A[i, j] then
                  A[i, j] ← A[i, k] + A[k, j]
                  P[i, j] ← k
              end if
          end for
      end for
  end for
end procedure
```

```
procedure WARSHALL(A, C, n)
  // A, C matrices booleanas
  for i ← 0; i < n do
      for j ← 0; j < n do
          A[i, j] ← C[i, j]
      end for
  end for

  for k ← 0; k < n do
      for i ← 0; i < n do
          for j ← 0; j < n do
              if A[i, j] = false then
                  A[i, j] ← A[i, k] and A[k, j]
              end if
          end for
      end for
  end for
end procedure
```

---

## FLOYD — PROBLEMA 1

**Enunciado:** Además de obtener la matriz de distancias mínimas entre todos los pares, contar cuántos caminos más cortos (de igual costo mínimo) existen entre cada par.

**Modificación:** Agregar una matriz de conteos y actualizarla según se encuentren caminos alternativos con la misma distancia mínima.

```
procedure FLOYD(A, C, P, Q, n)
  // Q cuenta los caminos alternativos
  for i ← 0; i < n do
      for j ← 0; j < n do
          A[i, j] ← C[i, j]
          P[i, j] ← 0
          Q[i, j] ← 0
      end for
  end for

  for k ← 0; k < n do
      for i ← 0; i < n do
          for j ← 0; j < n do
              if A[i, k] + A[k, j] < A[i, j] then
                  A[i, j] ← A[i, k] + A[k, j]
                  P[i, j] ← k
              end if
              if A[i, k] + A[k, j] = A[i, j] then
                  Q[i, j] ← Q[i, j] + 1
              end if
          end for
      end for
  end for
end procedure
```

---

## FLOYD — PROBLEMA 2

**Enunciado:** Determinar qué nodos son críticos, es decir, si al quitarlos aumentan las distancias mínimas entre algún par de nodos. Utilizar Floyd.

**Estrategia:** Ejecutar el algoritmo sin cada nodo y comparar las matrices de distancias.

---

## FLOYD — PROBLEMA 3

**Enunciado:** Una vez al mes, se cierran ciertas rutas por mantenimiento. Averiguar cuál es el mayor incremento de distancia mínima entre pares si se quita temporalmente una sola arista.

**Estrategia:** Eliminar aristas una a una y comparar las matrices de distancias.

---

## BÚSQUEDA EN PROFUNDIDAD (BPF)

```
procedure MAIN(G)
  Visitados ← ∅
  for v ∈ vertices(G) do
      if v ∉ Visitados then
          BPF(v, Visitados)
      end if
  end for
end procedure

procedure BPF(v, Visitado)
  agregar v a Visitados
  for w ∈ adyacente(v) do
      if w no visitado then
          BPS(w, Visitados)
      end if
  end for
end procedure
```

---

## BPF — PROBLEMA 1

**Enunciado:** Determinar si hay algún ciclo en el grafo dirigido.

**Estrategia:** Usar marcas temporales: visitado, recursión, no visitado. Si se visita un nodo en llamada recursiva, hay ciclos.

```
procedure MAIN(G)
  Visitados ← ∅
  for v ∈ vertices(G) do
      if TieneCiclo(v, Visitados) then
          return true
      end if
  end for
  return false
end procedure

procedure TIENECICLO(v, Visitado)
  agregar v a Visitados
  for w ∈ adyacente(v) do
      if w ∈ Visitados then
          return true
      else if TieneCiclo(w, Visitados) then
          return true
      end if
  end for
  remover v de Visitados
  return false
end procedure
```

---

## BPF — PROBLEMA 2

**Enunciado:** Desde un nodo **s**, encontrar todos los caminos posibles hasta **t** sin pasar por un conjunto de nodos P.

**Estrategia:** En BPF, verificar que el nodo no esté en P antes de continuar.

```
procedure MAIN(G, P, origen, destino)
  // P son los vértices que no se deben considerar
  Caminos ← ∅
  ArmarCaminos(origen, destino, ∅, Caminos, P)
  return Caminos
end procedure

procedure ARMARCAMINOS(v, destino, camino, Caminos, P)
  // camino se comporta como una pila
  camino.push(v)
  if v = destino then
      copia ← copiar camino
      agregar copia a Caminos
  else
      agregar v a Visitados
      for w ∈ adyacente(v) do
          if w ∉ Visitados and w ∉ P then
              ArmarCaminos(w, destino, camino, Caminos, P)
          end if
      end for
      remover v de Visitados
  end if
  camino.pop()
end procedure
```
