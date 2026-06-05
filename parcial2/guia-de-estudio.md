# Guía de estudio — Parcial 2 (UT3 + UT4)

---

## TDAs del Parcial 2

### UT3

| TDA | Archivo pseudocódigo | Archivo metodos_java |
|-----|---------------------|---------------------|
| Árbol Genérico | `pseudocodigos/arbol-generico.md` | `metodos_java.md` |
| Trie | `pseudocodigos/trie.md` | `metodos_java.md` |
| Patricia (Trie comprimido) | `pseudocodigos/trie-patricia.md` | — |
| Hash (open addressing) | `pseudocodigos/hash.md` | `metodos_java.md` |
| TDA Mapa | `pseudocodigos/mapa.md` | `metodos_java.md` |
| TDA Diccionario | `pseudocodigos/diccionario.md` | `metodos_java.md` |

### UT4

| Algoritmo | Archivo pseudocódigo | Archivo metodos_java |
|-----------|---------------------|---------------------|
| Dijkstra | `pseudocodigos/grafo-dirigido.md` | `metodos_java.md` |
| Floyd-Warshall | `pseudocodigos/grafo-dirigido.md` | `metodos_java.md` |
| Warshall | `pseudocodigos/grafo-dirigido.md` | `metodos_java.md` |
| DFS / BPF | `pseudocodigos/grafo-dirigido.md` | `metodos_java.md` |
| BEA / BFS | `pseudocodigos/grafo-dirigido.md` | `metodos_java.md` |
| Clasificación topológica | `pseudocodigos/grafo-dirigido.md` | `metodos_java.md` |
| Detección de ciclos | `pseudocodigos/grafo-dirigido.md` | `metodos_java.md` |
| Todos los caminos | `pseudocodigos/grafo-dirigido.md` | `metodos_java.md` |
| Prim (AGM) | `pseudocodigos/grafo-dirigido.md` | `metodos_java.md` |
| Kruskal (AGM) | `pseudocodigos/grafo-dirigido.md` | `metodos_java.md` |
| Puntos de articulación | `pseudocodigos/grafo-dirigido.md` | `metodos_java.md` |

---

## Patrones recurrentes en parciales

### Patrón 1 — Árbol genealógico / estructura jerárquica

El enunciado describe una jerarquía (empresa, familia, categorías). La solución siempre usa Árbol Genérico.

**Métodos que más aparecen:**
- `listarDescendientes(nombre)` → postOrden o preOrden filtrando subárbol
- `altura()` → recursión sobre hijos
- `obtenerGeneracion(nivel)` → BFS o recursión con nivel como parámetro
- `ancestroComun(a, b)` → LCA: obtener camino raíz→a, raíz→b, encontrar último común
- `esDescendiente(a, b)` → buscar `a` en el subárbol de `b`

**Pista clave:** para `obtenerGeneracion(nivel)` se pasa el nivel como parámetro en la recursión:
```java
void obtenerGeneracion(NodoGenerico<T> nodo, int nivelBuscado, int nivelActual, List<T> resultado) {
    if (nivelActual == nivelBuscado) {
        resultado.add(nodo.getDato());
        return;
    }
    for (NodoGenerico<T> hijo : nodo.getHijosNodos()) {
        obtenerGeneracion(hijo, nivelBuscado, nivelActual + 1, resultado);
    }
}
```

---

### Patrón 2 — Autocompletar / búsqueda de palabras con prefijo

El enunciado pide buscar palabras que empiezan con un prefijo, o verificar si una palabra existe.

**Siempre es Trie.**

Flujo de `predecir`:
1. Navegar hasta el nodo del último carácter del prefijo
2. DFS desde ahí, concatenando caracteres al string acumulado
3. Agregar a resultado cuando `esPalabra = true`

**Error típico:** confundir `buscar` (retorna Entry con esPalabra) con `esPalabra` del nodo. Si la palabra es solo prefijo de otra, `buscar` retorna Entry con `esPalabra=false`, **no retorna null**.

---

### Patrón 3 — Tabla hash y colisiones

El enunciado da un conjunto de claves y pide construir la tabla a mano, calculando posiciones y colisiones.

**Algoritmo manual:**
1. `h0 = clave mod N`
2. Si `tabla[h0]` está ocupada: sondear `(h0 + 1) mod N`, `(h0 + 2) mod N`, ...
3. Contar comparaciones para búsqueda exitosa y no exitosa

**Claves de análisis:**
- Más colisiones → más comparaciones promedio
- Factor de carga alto → muchas colisiones → tabla cuadrática se desempeña mejor
- Open addressing no tolera eliminaciones sin tombstones

---

### Patrón 4 — hashCode / equals

El enunciado pide implementar `equals` y `hashCode` para una clase, o preguntar qué pasa si se viola el contrato.

**Regla:** si `a.equals(b)` → `a.hashCode() == b.hashCode()`.

**Error clásico a identificar:**
> `equals` usa `isbn`, `hashCode` usa `titulo` → dos libros con mismo isbn pero diferente título tienen hashCodes distintos → `HashSet` los trata como elementos distintos → viola el contrato.

---

### Patrón 5 — Elegir la colección correcta

El enunciado describe un problema y pide elegir la colección Java más adecuada.

**Árbol de decisión:**
```
¿Necesito asociar clave→valor?
  Sí → ¿Necesito orden?
         No  → HashMap
         Sí (inserción) → LinkedHashMap
         Sí (clave natural) → TreeMap
  No → ¿Permito duplicados?
         Sí → ArrayList o LinkedList
         No → ¿Necesito orden?
                No → HashSet
                Sí → TreeSet
```

**Justificación esperada en parcial:**
- `HashMap` para conteo de frecuencias (O(1) amortizado)
- `TreeMap` si necesito iterar en orden lexicográfico
- `LinkedHashMap` para LRU cache o cuando el orden de inserción importa

---

---

## Patrones recurrentes en parciales — UT4

### Patrón 6 — Camino mínimo entre dos ciudades / vértices

El enunciado da un grafo con pesos y pide el camino de menor costo entre un origen y uno o todos los destinos.

**Si es un solo origen → Dijkstra.** Si son todos los pares → Floyd.

**Claves:**
- Inicializar D[origen] = 0, D[resto] = ∞.
- El vector P[v] guarda el predecesor de v en el camino óptimo.
- Para recuperar el camino de origen a t: recorrer P desde t hacia atrás (`t ← P[t]`) hasta llegar al origen. Invertir la lista.

**Error típico:** olvidar actualizar P[v] cuando se mejora D[v]. Sin P no se puede recuperar el camino.

---

### Patrón 7 — Todos los pares / excentricidad / centro

El enunciado pide la distancia mínima entre todos los pares y encontrar el vértice más "central".

**Siempre es Floyd.**

**Flujo:**
1. Construir matriz C inicial (∞ si no hay arco, 0 en diagonal).
2. Ejecutar Floyd → matriz A de distancias mínimas.
3. Para cada vértice v: `e(v) = max(columna v de A)`.
4. Centro = vértice con menor e(v).

**Error típico:** confundir fila con columna. La excentricidad de v es el máximo de la columna v (la distancia máxima desde cualquier otro vértice hacia v).

---

### Patrón 8 — Conectividad / cerradura transitiva

El enunciado pregunta "¿puede llegarse de X a Y?" para todos los pares.

**Siempre es Warshall.**

**Flujo:**
1. Construir matriz C booleana (verdadero si hay arco directo).
2. Ejecutar Warshall → matriz A donde `A[i,j] = verdadero` si existe algún camino de i a j.

**Diferencia con Floyd:** Warshall solo responde "¿existe camino?", no calcula costos.

---

### Patrón 9 — Recorrido sistemático / DFS

El enunciado pide visitar todos los vértices o hacer algo en cada uno (imprimir, marcar, acumular).

**Siempre es DFS.**

**Flujo:**
1. Inicializar conjunto visitados vacío.
2. Para cada vértice no visitado: llamar a BPF recursivo.
3. En BPF: marcar como visitado, procesar, recorrer adyacentes no visitados.

**Error típico:** olvidar que DFS en un grafo desconectado necesita llamar BPF desde múltiples vértices de inicio.

---

### Patrón 10 — Orden topológico / previaturas

El enunciado modela tareas con dependencias (A debe completarse antes de B).

**Siempre es clasificación topológica** (solo válida si el grafo no tiene ciclos).

**Clave:** la inserción se hace **al principio de la lista en la salida recursiva**, no al entrar.

**Flujo:**
1. DFS sobre todos los vértices.
2. Al terminar la recursión de cada vértice (después de procesar sus adyacentes): insertar al frente de la lista resultado.

---

### Patrón 11 — Detección de ciclos

El enunciado pide determinar si existe un ciclo en un grafo dirigido.

**Flujo con conjunto activo:**
1. Mantener un conjunto `enRecursion` (vértices activos en la pila de llamadas).
2. Al entrar a un vértice: agregarlo a `enRecursion`.
3. Si un adyacente ya está en `enRecursion`: hay ciclo (arco de retroceso).
4. Al salir: remover de `enRecursion` (backtracking).

**Error típico:** usar solo "visitados" sin distinción entre "en proceso" y "terminado" — esto no detecta si hay un arco de retroceso.

---

### Patrón 12 — Todos los itinerarios posibles

El enunciado pide listar todos los caminos entre origen y destino.

**DFS con backtracking:**
1. Agregar vértice actual al camino y a visitados.
2. Si es el destino: guardar copia del camino.
3. Si no: explorar adyacentes no visitados.
4. Al retroceder: **desmarcar el vértice** (sacarlo de visitados) para que otros caminos puedan usarlo.

**Clave:** el backtracking (desmarcar al retroceder) es lo que permite explorar todos los caminos, no solo uno.

---

### Patrón 13 — Número de saltos / distancia mínima sin pesos

El enunciado pide la distancia mínima en cantidad de aristas (saltos), no en costo. Ejemplo: número de Bacon, grados de separación, distancia en red de ciudades sin pesos.

**Siempre es BEA** (no Dijkstra — Dijkstra es para pesos). BEA garantiza que el primer camino encontrado a cada vértice es el más corto en saltos.

**Flujo:**
1. Encolar el origen, marcarlo como visitado.
2. En cada paso: desencolar vértice, visitar, encolar sus vecinos no visitados.
3. Para contar saltos: guardar la distancia junto al vértice en la cola (o usar nivel del BEA).

```java
// Contar saltos con mapa de distancias
Map<V, Integer> distancias = new HashMap<>();
Queue<V> cola = new ArrayDeque<>();
distancias.put(origen, 0);
cola.offer(origen);
while (!cola.isEmpty()) {
    V actual = cola.poll();
    for (Edge<V, D> arista : grafo.adyacencias(grafo.construirComparable(actual))) {
        V vecino = arista.target();
        if (!distancias.containsKey(vecino)) {
            distancias.put(vecino, distancias.get(actual) + 1);
            cola.offer(vecino);
        }
    }
}
```

---

### Patrón 14 — Árbol generador mínimo (red de cableado, caminos, conexiones)

El enunciado tiene un grafo **no dirigido** y pide conectar todos los vértices con el menor costo total (sin ciclos). Ejemplo: red de cableado, sistema de caminos de tierra, red eléctrica.

**Dos opciones:**
- **Prim**: crece desde un vértice de origen, agrega la arista más barata que conecta el árbol con el exterior. Necesita un vértice de inicio.
- **Kruskal**: ordena todas las aristas por peso, agrega de a una evitando ciclos (union-find). No necesita vértice de inicio.

**Para el parcial son equivalentes** — producen el mismo AGM (puede haber empate si hay aristas de igual peso). Usar el que pida el enunciado.

**Señal clave:** grafo no dirigido + "conectar todos con menor costo" → Prim o Kruskal.

**Diferencia con Dijkstra:** Dijkstra busca el camino más corto desde un origen a todos los demás (no un árbol). Prim/Kruskal construyen el árbol que conecta todos con menor costo total.

---

### Patrón 15 — Nodo cuya eliminación desconecta la red

El enunciado pregunta qué vértice es crítico: si se elimina, parte de la red queda aislada. Ejemplo: servidor cuya caída aísla parte de la red, ciudad cuya desconexión separa regiones.

**Siempre es puntos de articulación** (DFS con disc/low).

**Regla práctica:** un vértice u es punto de articulación si:
- Es raíz del DFS y tiene más de un hijo directo en el árbol DFS, o
- No es raíz y tiene un hijo v tal que `low[v] >= disc[u]` (el subárbol de v no puede "subir" por encima de u).

**Error típico:** intentar resolverlo eliminando cada vértice y verificando conectividad — es correcto pero O(V²), mientras el algoritmo con disc/low es O(V+E).

---

## Errores comunes a evitar

### UT3

| Error | Correcto |
|-------|----------|
| Eliminar en open addressing poniendo `null` | Poner tombstone (`loteLibre = true`) |
| Trie: retornar `null` cuando la palabra es solo prefijo | Retornar Entry con `esPalabra=false` |
| `TreeMap` acepta clave `null` | No acepta — lanza `NullPointerException` |
| Usar `Stack` de Java (legacy) | Usar `ArrayDeque` como pila |
| N = potencia de 2 en función hash con strings | N = número primo |
| `hashCode` con atributos mutables | Solo atributos que definen identidad lógica |

### UT4

| Error | Correcto |
|-------|----------|
| Usar Dijkstra con pesos negativos | Dijkstra solo funciona con pesos ≥ 0 |
| Inicializar diagonal de Floyd en ∞ | La diagonal siempre es 0 (`A[i,i] = 0`) |
| Warshall con costos numéricos | Warshall usa booleanos; Floyd usa números |
| En DFS poner null en el conjunto visitados al retroceder | Solo remover del conjunto activo en detección de ciclos |
| Clasificación topológica: agregar al final de la lista | Agregar **al principio** (en la salida recursiva) |
| En Floyd, recuperar camino con solo `P[i,j]` | `P[i,j]` es el vértice intermedio, hay que llamar recursivamente a `camino(i,k)` y `camino(k,j)` |
| Confundir excentricidad de fila con excentricidad de columna | `e(v)` = máximo de la **columna** v (mayor distancia que alguien debe recorrer para llegar a v) |
| Usar Dijkstra para contar saltos (sin pesos) | Usar BEA — Dijkstra es para costos, BEA es para saltos |
| Prim sobre grafo dirigido | Prim y Kruskal solo funcionan en grafos **no dirigidos** |
| En Kruskal, poner null en lugar de tombstone al eliminar un grupo | No se elimina: se fusionan los dos grupos en el union-find |
| En puntos de articulación, confundir `low[v] >= disc[u]` con `low[v] > disc[u]` | La condición es `>=`: si `low[v] = disc[u]` exactamente, u también es punto de articulación |
| Marcar la raíz del DFS como punto de articulación si tiene exactamente 1 hijo | La raíz solo es punto de articulación si tiene **más de 1 hijo** en el árbol DFS |

---

## Complejidades para tener en la cabeza

### UT3

| Estructura | Búsqueda | Inserción | Eliminación |
|------------|---------|-----------|-------------|
| ArrayList | O(n) | O(1) amort. (final) | O(n) |
| LinkedList | O(n) | O(1) (extremos) | O(1) si tengo el nodo |
| HashMap | O(1) prom. | O(1) prom. | O(1) prom. |
| TreeMap | O(log n) | O(log n) | O(log n) |
| Trie | O(m) | O(m) | O(m) |
| Hash open addr. | O(1) prom. | O(1) prom. | O(1) prom. |
| Árbol genérico (buscar) | O(n) | O(n) | O(n) |

m = largo de la palabra/clave

### UT4

| Algoritmo | Complejidad | Nota |
|-----------|-------------|------|
| Dijkstra (naive) | O(V²) | V = cantidad de vértices |
| Floyd | O(V³) | Para todos los pares |
| Warshall | O(V³) | Solo conectividad |
| DFS | O(V+E) | E = cantidad de aristas |
| BEA | O(V+E) | Distancia mínima en saltos |
| Clasificación topológica | O(V+E) | Solo grafos acíclicos |
| Detección de ciclos | O(V+E) | — |
| Todos los caminos | exponencial | DFS + backtracking |
| Prim (naive) | O(V·E) | Busca mínima arista en cada paso |
| Kruskal | O(E log E) | Dominado por el ordenamiento |
| Puntos de articulación | O(V+E) | DFS con disc/low |

---

## Orden de implementación recomendado (según CLAUDE.md del código base)

```
1. Ej 16  → ArbolGenealogico (árbol genérico desde cero)
2. Ej 5   → eliminar en Trie + tests
3. Ej 13  → Alumno: equals/hashCode
4. Ej 15  → Libro: equals/hashCode
5. Ej 14  → 3 estrategias de colisión
6. Ej 11  → frecuencia de palabras (HashMap/TreeMap)
7. Ej 12  → TTrieHashMap + buscarPatron
8. Ej 7   → mediciones comparativas
9. Ej 9   → factor de carga (depende de Hash implementado)
```

**Ejercicios entregables:** 5, 7, 9, 11, 12, 13, 14, 15, 16
