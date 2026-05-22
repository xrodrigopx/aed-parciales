# Cuadernola — Parcial 2 (UT3)

---

## Árbol Genérico

- Árbol con cualquier número de hijos por nodo
- **Grado:** cantidad de hijos directos
- **Hoja:** nodo con grado 0
- **Altura:** longitud del camino más largo hasta una hoja (hoja = 0)
- **Representación:** Primer Hijo — Hermano Derecho → convierte en árbol binario

**Recorridos:**
| Recorrido | Orden |
|-----------|-------|
| Preorden | Raíz → A₁ → A₂ → ... |
| Postorden | A₁ → A₂ → ... → Raíz |

**Operaciones:** `agregarHijo`, `eliminar`, `buscar`, `obtenerPadre`, `preOrden`, `postOrden`, `altura`, `grado`

---

## Trie

- Árbol de prefijos — búsqueda digital O(m) donde m = largo de la palabra
- Cada nodo tiene: `esPalabra` (boolean) + `hijos` (Map\<Character, NodoTrie\>)

**Operaciones:**
| Método | Qué hace | Retorna |
|--------|----------|---------|
| `insertar(palabra, dato)` | Crea nodos por carácter, marca esPalabra=true | true si era nueva |
| `buscar(palabra)` | Recorre por caracteres | Entry (esPalabra=true/false) o null si no existe ningún nodo |
| `predecir(prefijo)` | Navega al nodo del prefijo, DFS para listar todas | List\<Entry\> |
| `eliminar(palabra)` | Marca esPalabra=false, no borra nodos con hijos | true si existía |

**Restricción:** ninguna string puede ser prefijo de otra → agregar `*` al final.

## Patricia (Trie comprimido)

- Aristas llevan substrings en vez de un carácter
- Todos los nodos internos tienen ≥ 2 hijos
- Tamaño: O(s) en vez de O(n) donde s = cantidad de strings
- Aristas representadas con tríadas (i, j, k) → punteros al array original

---

## Hash (Open Addressing — Sondeo Lineal)

- `h(K) = K.hashCode() % N` — N primo para mejor distribución
- **Colisión:** `h(Kᵢ) = h(Kⱼ)` con Kᵢ ≠ Kⱼ → inevitable
- **Sondeo lineal:** `pos = (h0 + i) % N`
- **loteLibre (tombstone):** posición eliminada ≠ posición vacía (null) — no rompe la cadena de búsqueda

**Factor de carga:** α = M/N — mantener < 0.70

**Complejidades:**
| Operación | Promedio | Peor caso |
|-----------|---------|-----------|
| Búsqueda | O(1) | O(n) |
| Inserción | O(1) | O(n) |
| Eliminación | O(1) | O(n) |

**Desventajas:** tamaño fijo, sin orden (no sirve para range queries), tombstones en open addressing.

**Variantes de sondeo:**
- Lineal: `(h0 + i) % N` → clustering primario
- Cuadrático: `(h0 + i²) % N` → clustering secundario
- Doble hashing: `(h0 + i·c) % N` → mínimo clustering

---

## TDA Mapa vs Diccionario

| TDA | Claves duplicadas | Inserción |
|-----|-----------------|-----------|
| **Mapa** | No — clave única | `poner(k, v)` — reemplaza |
| **Diccionario** | Sí | `insertar(k, v)` — siempre agrega |

**Java:**
- Mapa → `HashMap<K,V>` / `TreeMap<K,V>`
- Diccionario → `Map<K, List<V>>`

---

## Implementaciones Java de Mapa

| Clase | Complejidad | Orden | Clave null |
|-------|------------|-------|-----------|
| `HashMap` | O(1) prom. | Sin orden | ✅ |
| `LinkedHashMap` | O(1) prom. | Inserción | ✅ |
| `TreeMap` | O(log n) | Natural de claves | ❌ |

---

## hashCode / equals — Contrato

**Regla:** si `a.equals(b)` → `a.hashCode() == b.hashCode()` (no al revés).

| Tipo | hashCode() |
|------|-----------|
| `Integer` | El propio valor int |
| `String` | `s[0]*31^(n-1) + s[1]*31^(n-2) + ... + s[n-1]` |
| Objeto propio | `Objects.hash(attr1, attr2, ...)` |

Usar los **mismos atributos** en `equals` y en `hashCode`. No usar atributos mutables.

---

## Collections Framework

| Collection | Ordering | Random Access | Key-Value | Duplicados |
|------------|:--------:|:-------------:|:---------:|:----------:|
| `ArrayList` | ✅ | ✅ | ❌ | ✅ |
| `LinkedList` | ✅ | ❌ | ❌ | ✅ |
| `HashSet` | ❌ | ❌ | ❌ | ❌ |
| `TreeSet` | ✅ | ❌ | ❌ | ❌ |
| `HashMap` | ❌ | ✅ | ✅ | ❌ |
| `TreeMap` | ✅ | ✅ | ✅ | ❌ |

**Pila:** `Deque<T> pila = new ArrayDeque<>()` → `push/pop/peek`
**Cola:** `Queue<T> cola = new ArrayDeque<>()` → `offer/poll/peek`

**Decisión:**
```
¿Pares clave-valor? → Sí: ¿Orden? → No: HashMap / Sí-inserción: LinkedHashMap / Sí-clave: TreeMap
                    → No: ¿Duplicados? → Sí: ArrayList / No: HashSet o TreeSet
```

---

## Métodos de búsqueda — Comparativa

| Método | Estructura | Búsqueda puntual | Prefijo | Orden |
|--------|-----------|-----------------|---------|-------|
| Comparación (ABB/AVL) | Árbol | O(log n) | O(log n + k) | Sí |
| Digital (Trie) | Árbol de prefijos | O(m) | O(m + k) | Sí |
| Transformación (Hash) | Tabla | **O(1) prom.** | No eficiente | No |
