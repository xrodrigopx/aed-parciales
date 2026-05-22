# AED — Unidad Temática 3: Árboles Genéricos, Hashing, Diccionarios, Mapas y Colecciones

---

## 1. Árboles Genéricos

### Definición recursiva

Un árbol T es un conjunto finito de uno o más nodos tal que:
- Existe un nodo especial llamado **raíz(T)**
- Los restantes nodos están distribuidos en m ≥ 0 conjuntos disjuntos T₁..Tₘ, cada uno de los cuales es también un árbol (**subárboles** de la raíz)

> El árbol binario es un caso particular con m ≤ 2.

### Terminología

| Término | Definición |
|---------|-----------|
| Grado | Número de subárboles (hijos directos) de un nodo |
| Hoja (terminal) | Nodo con grado cero |
| Nodo interno | Nodo que no es hoja |
| Nivel | Raíz = nivel 0; resto = nivel del subárbol contenedor + 1 |

### Recorridos

Se definen recursivamente. La diferencia es el momento en que se visita la raíz relativo a los subárboles A₁..Aₖ:

| Recorrido | Orden de visita | Aplicación típica |
|-----------|----------------|-------------------|
| **Preorden** | Raíz → A₁ → A₂ → ... | Imprimir documentos estructurados |
| **Postorden** | A₁ → A₂ → ... → Raíz | Calcular espacio en disco por directorio |
| **Inorden** | A₁ → Raíz → A₂..Aₖ | Menos natural; rara vez usado en genéricos |

```
preOrden(v):
  visitar(v)
  Para cada hijo w de v: preOrden(w)

postOrden(v):
  Para cada hijo w de v: postOrden(w)
  visitar(v)
```

### Representación: Primer Hijo — Hermano Derecho

Convierte el árbol genérico en un árbol binario reutilizando un nodo de dos punteros:
- Puntero **izquierdo** → primer hijo
- Puntero **derecho** → siguiente hermano

Esto permite usar exactamente la misma infraestructura de árbol binario para árboles con cualquier número de hijos.

```
         A
       / | \
      B  C  D
     / \    |
    E   F   G
```
Se representa como:
```
A.izq = B,  A.der = null
B.izq = E,  B.der = C
E.izq = null, E.der = F
F.izq = null, F.der = null
C.izq = null, C.der = D
D.izq = G,  D.der = null
```

### TDA Árbol — Operaciones

```
Padre(unNodo)
HijoIzquierdo(unNodo)   ← primer hijo en representación primer-hijo/hermano-derecho
HermanoDerecho(unNodo)
Etiqueta(unNodo)
Raiz
Anula
```

### Ejemplos de aplicación

- **Sistema de archivos:** directorio `aed2/` con hijos `trabajos/` y `programas/`
- **Organigramas:** empresa → departamentos → equipos → empleados
- **Árbol de parseo:** programa → sentencias → expresiones

---

## 2. Trie (Árbol de Prefijos)

### Los tres métodos de búsqueda del curso

| Método | Mecanismo | Estructura | Complejidad |
|--------|-----------|-----------|-------------|
| Por comparación de claves | Comparar elementos entre sí | Lista, ABB, AVL | O(n) a O(log n) |
| Digital (por caracteres) | Seguir camino según caracteres de la clave | **Trie** | O(m) |
| Transformación de clave | Calcular posición aritméticamente | **Hash** | O(1) promedio |

### Definición formal

Sea S un conjunto de s strings del alfabeto σ donde **ninguna es prefijo de otra**. Un trie estándar para S es un árbol ordenado T donde:
- Cada nodo (excepto la raíz) tiene una **etiqueta = un carácter de σ**
- Los hijos de un nodo interno están ordenados **alfabéticamente**
- Hay **s nodos externos** (uno por string): la concatenación de etiquetas raíz→nodo externo da la string correspondiente

> **Restricción:** ninguna string puede ser prefijo de otra. Solución estándar: agregar carácter especial `*` al final de cada string.

### Estructura de nodo (Java)

```java
class TNodoTrie {
    boolean esPalabra;               // ¿termina aquí una palabra válida?
    Map<Character, TNodoTrie> hijos; // o array[d] según alfabeto
}
```

### Operaciones

#### Búsqueda de palabra completa — O(m)

Seguir el camino desde la raíz indicado por los caracteres del patrón:
- Camino completo + nodo externo (`esPalabra = true`) → **existe**
- Camino roto o termina en nodo interno → **no existe**

```
buscar(raiz, palabra):
  nodoActual ← raiz
  Para cada char c en palabra:
      hijo ← nodoActual.obtenerHijo(c)
      Si hijo es null: devolver false
      nodoActual ← hijo
  devolver nodoActual.esPalabra
```

#### Búsqueda de prefijo — O(m) + tamaño del resultado

Recorrer el camino hasta el nodo correspondiente al prefijo (igual que búsqueda de palabra, sin chequear `esPalabra`), luego DFS sobre todos los subárboles para listar las strings que contienen ese prefijo.

#### Inserción — O(d·m)

1. Intentar recorrer el camino de la string nueva
2. En el nodo donde el camino diverge (hijo null), crear nodos para los caracteres restantes
3. Marcar el nodo final como `esPalabra = true`

Construcción completa: O(d·n) donde n = largo total de todas las strings.

### Tabla de complejidades

| Operación | Tiempo | Notas |
|-----------|--------|-------|
| Búsqueda de palabra | O(d·m) → **O(m)** si d constante | d = tamaño del alfabeto |
| Búsqueda de prefijo | O(m) + tamaño del resultado | — |
| Inserción de string | O(d·m) | — |
| Construcción completa | O(d·n) | n = largo total de todas las strings |
| Altura | Largo de la string más larga | — |
| Número de nodos | O(n) | Puede ser grande |

### Representación de nodos

| Representación | Acceso | Espacio por nodo | Cuándo usar |
|----------------|--------|-----------------|-------------|
| Array de d referencias | O(1) | O(d) siempre | Alfabeto pequeño (ADN: 4, dígitos: 10) |
| Lista encadenada de (char, nodo) | O(grado) | O(grado real) | Trie disperso |
| `HashMap<Character, TNodoTrie>` | O(1) promedio | O(grado real) | Uso general |

### Propiedades estructurales

- Máximo **d hijos** por nodo interno
- Exactamente **s nodos externos**
- Altura = longitud de la string más larga
- Puede usarse para implementar un diccionario

---

## 3. Trie Comprimido — Patricia

**PATRICIA = Practical Algorithm to Retrieve Information Coded in Alphanumeric**

### Motivación

Un trie estándar puede tener largas cadenas de nodos con un solo hijo (caminos lineales sin ramificaciones) → desperdicio de espacio.

El Patricia trie **comprime esas cadenas**: en lugar de un nodo por carácter, las **aristas llevan substrings completas**.

### Propiedad clave

Todos los nodos internos tienen **al menos 2 hijos** y como máximo d.

Si hay L hojas (= s strings) → hay como máximo L−1 nodos internos → **tamaño total O(s)** en vez de O(n).

### Representación eficiente con tríadas (i, j, k)

Para no duplicar las substrings en memoria, las aristas se representan como **tríadas**:
- **i:** índice de la string en el array S
- **j, k:** rango de índices dentro de S[i]
- La arista representa la substring `S[i][j..k]`

```
S = ["bear", "bell", "bid", "bull", "buy", "sell", "stock", "stop"]
S[0] = "bear"
Arista (0, 0, 2) → "bea"  (caracteres 0 a 2 de S[0])
```

Esto hace al Patricia trie ideal como **estructura de índice auxiliar**: la colección original ya está en memoria; el trie solo almacena referencias.

### Comparación Trie vs Patricia

| Propiedad | Trie estándar | Patricia |
|-----------|--------------|----------|
| Nodos internos | O(n) | O(s) |
| Nodos con 1 hijo | Posibles | Ninguno |
| Complejidad de búsqueda | O(m) | O(m) |
| Implementación | Simple | Más compleja |

### Implementación Java

```
org.apache.commons.collections4.trie
    Class PatriciaTrie<E>
```

---

## 4. Hashing (Transformación de Claves)

### Concepto

Método de búsqueda que calcula la posición de almacenamiento de un elemento aplicando una función aritmética a su clave:

```
h: U (universo de claves) → {0, ..., m-1} (tabla T de tamaño m)
```

**Objetivo:** mapear un universo de claves potencialmente enorme a un espacio de almacenamiento manejable, logrando búsqueda, inserción y eliminación en **O(1) promedio** sin comparar claves entre sí.

**Colisión:** `h(Kᵢ) = h(Kⱼ)` con `Kᵢ ≠ Kⱼ`. Prácticamente inevitable: 26¹⁶ posibles nombres de 16 letras → 1000 posiciones disponibles.

Dos problemas a resolver: **elegir h(K)** y **resolver colisiones**.

### Función de hash — Criterios de diseño

1. Distribución lo más **aleatoria** posible
2. Tener en cuenta **agrupamientos** en las claves (ej. muchas palabras con el mismo prefijo)
3. Debe ser **rápida** de calcular

**Esquema de división (el más común):**
```
h(K) = K mod N
```
- Usar **N primo** para mejor distribución
- N = potencia de 2 falla con strings (los agrupamientos de prefijos degradan la distribución)

### Resolución de colisiones

#### Encadenamiento directo (Chaining)

- N listas enlazadas; cada posición de la tabla es la cabeza de una lista
- Inserción: siempre **O(1)** — se agrega al frente de la lista del bucket
- Búsqueda y eliminación: **O(1 + α)** promedio
- **Ventaja:** sencillo; la eliminación es trivial; funciona bien aunque la tabla esté muy llena

```
Tabla:   [0] → TO → null
         [1] → FIRE → null
         [2] → null
         ...   (TO y FIRE colisionan en posición 1)
```

#### Encadenamiento abierto (Open Addressing)

Buscar posición libre **dentro de la misma tabla** sin estructuras auxiliares:

| Variante | Fórmula de sonda | Problema |
|----------|-----------------|----------|
| **Lineal** | `hᵢ = (h0 + i) mod N` | Clustering primario — los elementos se agrupan |
| **Cuadrático** | `hᵢ = (h0 + i²) mod N` | Clustering secundario (menor) |
| **Hashing doble** | `hᵢ = (h0 + c) mod N`, c primo con N | Mínimo clustering |

**Pseudocódigo insertar (open addressing lineal):**
```
insertar(clave, valor):
    i ← 0; h0 ← h(clave)
    Repetir:
        j ← (h0 + i) mod m
        Si T[j] vacío → T[j] ← valor; devolver true
        Sino → i ← i + 1
    Hasta i == m
    devolver false   ← tabla llena
```

**Pseudocódigo buscar (open addressing lineal):**
```
buscar(clave):
    i ← 0; h0 ← h(clave)
    Repetir:
        j ← (h0 + i) mod m
        Si T[j] vacío → devolver null
        Si T[j].clave == clave → devolver T[j].valor
        Sino → i ← i + 1
    Hasta i == m
    devolver null
```

### Factor de Carga

```
a = M / (N+1)     M = claves insertadas,  N = tamaño de la tabla
```

| a | Significado |
|---|-------------|
| 0 | Tabla vacía |
| ≈ N/(N+1) | Tabla prácticamente llena |

### Análisis de rendimiento

**Distribución uniforme (open addressing ideal):**
```
E = −ln(1 − a) / a
```

**Búsqueda lineal (con clustering):**
```
E = (1 − a/2) / (1 − a)
```

| a | E (uniforme) | E (lineal) |
|---|-------------|-----------|
| 0.50 | 1.39 | 1.50 |
| 0.75 | 1.85 | 2.50 |
| 0.90 | 2.56 | 5.50 |
| 0.95 | 3.15 | 10.5 |

Con distribución uniforme, incluso al 90% de ocupación se necesitan ~2.56 búsquedas — **superior a cualquier árbol balanceado** para acceso puntual.

### Complejidades resumen

| Operación | Promedio | Peor caso |
|-----------|---------|-----------|
| Búsqueda | O(1) | O(n) |
| Inserción | O(1) | O(n) |
| Eliminación | O(1) con chaining | O(n) / complejo con open addressing |

### Desventajas

1. **Tamaño fijo:** requiere estimación a priori. Recomendación: dimensionar la tabla 10% más grande que lo necesario
2. **Eliminación costosa** en open addressing: requiere "tombstones" (marcar posición como eliminada, no vacía) para no romper la cadena de sondas; chaining lo resuelve trivialmente
3. **Sin orden:** no preserva el orden de las claves; para range queries usar ABB/TreeMap

---

## 5. TDA Mapa

### Definición

Estructura que almacena pares **(k, v)** con **clave única**: cada clave k mapea a exactamente un valor v. `poner(k, v)` **reemplaza** el valor si k ya existe.

### Operaciones del TDA

```
tamaño()         → int
estaVacio()      → boolean
recuperar(k)     → v (o null si no existe)
poner(k, v)      → (reemplaza si k ya existe)
eliminar(k)      → v eliminado
claves()         → colección de claves
valores()        → colección de valores
elementos()      → colección de pares (k, v)
```

### Implementaciones Java

| Clase | Implementación interna | Complejidad | Orden de iteración | Clave null |
|-------|----------------------|-------------|-------|-------------|
| `HashMap` | Array de buckets + árbol rojo-negro (>8 elem.) | O(1) prom. | Sin orden | ✅ |
| `LinkedHashMap` | HashMap + lista doblemente enlazada | O(1) prom. | Inserción o acceso | ✅ |
| `TreeMap` | Árbol rojo-negro | O(log n) | Natural de claves | ❌ |

**Constructor HashMap:**
```java
new HashMap<>()                                    // capacidad=16, loadFactor=0.75
new HashMap<>(int initialCapacity)
new HashMap<>(int initialCapacity, float loadFactor)
```
Al superar `capacidad × factor de carga` → rehashing automático.

**¿Cuándo elegir qué?**
- `HashMap`: caso general, O(1), sin necesidad de orden
- `LinkedHashMap`: LRU cache, cuando el orden de inserción importa
- `TreeMap`: range queries, iteración ordenada por clave, `NavigableMap`

---

## 6. TDA Diccionario

### Definición

Como el TDA Mapa, almacena pares clave-valor, pero **permite múltiples entradas con la misma clave**. `insertar(k, v)` **nunca reemplaza**; siempre agrega.

### Diferencia fundamental

| TDA | Claves duplicadas | Método de inserción |
|-----|-----------------|---------------------|
| **Mapa** | No — clave única | `poner(k, v)` — reemplaza |
| **Diccionario** | Sí — múltiples valores por clave | `insertar(k, v)` — siempre agrega |

Dos variantes: diccionario **ordenado** (claves en orden) y **desordenado**.

### Operaciones del TDA

```
tamaño()          → int
estaVacio()       → boolean
buscar(k)         → primer elemento con clave k
buscarTodos(k)    → todos los elementos con clave k
insertar(k, v)    → agrega nuevo par (incluso si k ya existe)
eliminar(e)       → elimina la entrada e específica (no por clave)
elementos()       → colección de pares; cada uno tiene getKey() y getValue()
```

### Casos de uso

- Índice de libro: término → lista de páginas donde aparece
- Historial de precios: fecha → múltiples precios registrados ese día
- Grupos de estudiantes: curso → lista de estudiantes

### Implementación en Java

Java no tiene un `Diccionario` estándar. Las alternativas son:

```java
Map<K, List<V>>        // clave mapea a lista de valores
Map<K, Set<V>>         // si los valores no se repiten dentro de una clave
Multimap<K, V>         // librería Guava
```

---

## 7. Contrato `hashCode()` / `equals()`

Fundamental para usar correctamente `HashMap` y `HashSet`.

**Regla:** si `a.equals(b)` entonces `a.hashCode() == b.hashCode()` (no al revés).

Adicionalmente: `hashCode()` debe retornar el mismo valor mientras el objeto no cambie (no necesita ser consistente entre distintas ejecuciones).

### Implementaciones estándar

| Tipo | `hashCode()` |
|------|-------------|
| `Integer` | Devuelve el valor entero directamente |
| `String` | `s[0]*31^(n-1) + s[1]*31^(n-2) + ... + s[n-1]` |
| `Float` | `Float.floatToIntBits(value)` — bits IEEE 754 |
| Objeto propio | `Objects.hash(attr1, attr2, ...)` con los mismos atributos que `equals()` |

### Ejemplos en Java

```java
// Un solo atributo identifica al objeto
@Override public int hashCode() { return cedula.hashCode(); }
@Override public boolean equals(Object o) { return ((Persona)o).cedula.equals(cedula); }

// Varios atributos identifican al objeto
@Override public int hashCode() { return Objects.hash(cedula, codigoCurso); }
@Override public boolean equals(Object o) {
    Persona p = (Persona) o;
    return cedula.equals(p.cedula) && codigoCurso.equals(p.codigoCurso);
}
```

> **Advertencia:** evitar atributos cambiantes (email, dirección) en `hashCode()`/`equals()` — un objeto en un `HashMap` cuya clave cambia se "pierde" en la tabla.

---

## 8. Java Collections Framework

### Jerarquía de interfaces

```
Collection
├── Set
│   └── SortedSet
├── List
├── Queue
└── Deque

Map
└── SortedMap
```

### Implementaciones de listas y estructuras lineales

#### `ArrayList`
- Array dinámico, se redimensiona automáticamente
- Acceso por índice: **O(1)**; inserción al final: **O(1) amortizado**; inserción en el medio: **O(n)**
- Preferir cuando: acceso frecuente por índice

#### `LinkedList`
- Lista doblemente enlazada que implementa `List` y `Deque`
- Acceso por índice: **O(n)**; inserción/eliminación en extremos: **O(1)**
- Sirve como `Queue` básica o `Deque`

#### `Stack` (legacy) / `ArrayDeque` (recomendado)
- `Stack` hereda de `Vector` (sincronizado) — **en desuso**
- `ArrayDeque` como **pila:** `push()` / `pop()` / `peek()`
- `ArrayDeque` como **cola:** `offer()` / `poll()` / `peek()`

```java
Deque<T> pila = new ArrayDeque<>();   // forma recomendada
Queue<T> cola = new ArrayDeque<>();   // más eficiente que LinkedList
```

#### `PriorityQueue`
- No FIFO puro — atiende según prioridad (min-heap por defecto)
- Requiere que los elementos sean `Comparable` o se provea un `Comparator`

### Implementaciones de mapas (resumen ampliado)

| Clase | Estructura interna | Complejidad | Orden | Clave null | Thread-safe |
|-------|-------------------|-------------|-------|------------|-------------|
| `HashMap` | Array de buckets + árbol rojo-negro (>8) | O(1) prom. | Sin orden | ✅ | ❌ |
| `LinkedHashMap` | HashMap + lista doblemente enlazada | O(1) prom. | Inserción o acceso | ✅ | ❌ |
| `TreeMap` | Árbol rojo-negro | O(log n) | Natural de claves | ❌ | ❌ |
| `ConcurrentHashMap` | Segmentado para concurrencia | O(1) prom. | Sin orden | ❌ | ✅ |

### `ListIterator`

```java
ListIterator<String> it = lista.listIterator();
while (it.hasNext())     { String x = it.next(); }
while (it.hasPrevious()) { String x = it.previous(); }  // recorre hacia atrás
```

`subList(from, to)` — vista de subrango; equivalente a iterar `i` desde `from` hasta `to-1`.

### Algoritmos de Colecciones

```java
Collections.sort(lista)              // ordenamiento estable
Collections.shuffle(lista)           // desorden aleatorio
Collections.reverse(lista)           // inversión
Collections.rotate(lista, distancia) // rotación
Collections.binarySearch(lista, key) // búsqueda binaria (lista ordenada)
```

### Tabla comparativa de implementaciones

| Collection | Ordering | Random Access | Key-Value | Duplicados | Null | Thread Safe |
|------------|:--------:|:-------------:|:---------:|:----------:|:----:|:-----------:|
| ArrayList | ✅ | ✅ | ❌ | ✅ | ✅ | ❌ |
| LinkedList | ✅ | ❌ | ❌ | ✅ | ✅ | ❌ |
| HashSet | ❌ | ❌ | ❌ | ❌ | ✅ | ❌ |
| TreeSet | ✅ | ❌ | ❌ | ❌ | ❌ | ❌ |
| HashMap | ❌ | ✅ | ✅ | ❌ | ✅ | ❌ |
| TreeMap | ✅ | ✅ | ✅ | ❌ | ❌ | ❌ |
| Stack | ✅ | ❌ | ❌ | ✅ | ✅ | ✅ |
| ConcurrentHashMap | ❌ | ✅ | ✅ | ❌ | ❌ | ✅ |

### Diagrama de decisión para elegir colección

```
¿Pares clave-valor?
├── Sí → ¿El orden importa?
│         ├── No  → HashMap
│         └── Sí  → ¿Orden de inserción o de clave?
│                   ├── Inserción → LinkedHashMap
│                   └── Clave     → TreeMap
└── No → ¿Duplicados?
          ├── Sí  → ArrayList
          └── No  → ¿Búsqueda frecuente (contains/remove)?
                    ├── Sí → ArrayList
                    └── No → ¿Orden?
                              ├── No  → HashSet
                              └── Sí  → TreeSet / LinkedHashSet
```

### Relación TDAs del curso ↔ Java API

| TDA del curso | Implementación Java recomendada |
|---------------|--------------------------------|
| `TDALista<T>` | `List<E>` → `ArrayList` / `LinkedList` |
| `TDAPila<T>` | `Deque<E>` → `ArrayDeque` (no `Stack`) |
| `TDACola<T>` | `Queue<E>` → `ArrayDeque` / `LinkedList` |
| `TDAMapa<K,V>` | `Map<K,V>` → `HashMap` / `TreeMap` |
| `TDADiccionario<K,V>` | `Map<K, List<V>>` / Guava `Multimap` |

---

## 9. Cuadro comparativo de métodos de búsqueda (UT03)

| Método | Estructura | Búsqueda puntual | Búsqueda por prefijo | Orden preservado | Eliminación |
|--------|-----------|-----------------|---------------------|-----------------|-------------|
| Comparación (ABB/AVL) | Árbol balanceado | O(log n) | O(log n + k) | Sí (inorden) | O(log n) |
| Digital (Trie) | Árbol de prefijos | O(m) | O(m + k) | Sí (alfabético) | O(m) |
| Transformación (Hash) | Tabla dispersa | **O(1) prom.** | No eficiente | No | O(1) chaining |

> k = tamaño del resultado; m = largo del patrón
