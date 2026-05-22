# Hash (Transformación de Claves) — Pseudocódigos

## Concepto

Calcula la posición de almacenamiento de un elemento aplicando una función aritmética a su clave:

```
h: U (universo de claves) → {0, ..., m-1}
```

**Objetivo:** búsqueda, inserción y eliminación en O(1) promedio.

**Colisión:** `h(Kᵢ) = h(Kⱼ)` con `Kᵢ ≠ Kⱼ`. Prácticamente inevitable.

## Función de hash

**Esquema de división (el más común):**
```
h(K) = K mod N
```
- Usar N primo para mejor distribución
- N potencia de 2 falla con strings

## Factor de carga

```
α = M / N     M = claves insertadas,  N = tamaño de la tabla
```

Recomendación: dimensionar la tabla para que α no supere 0.70.

---

## Encadenamiento directo (Chaining)

Cada posición de la tabla tiene una lista enlazada de elementos que colisionaron ahí.

- Inserción: siempre O(1) — agrega al frente
- Búsqueda y eliminación: O(1 + α) promedio
- Ventaja: eliminación trivial, funciona bien aunque la tabla esté llena

```
insertar_chaining(clave: K, valor: V): void
  pos ← h(clave)
  tabla[pos].agregarAlFrente(clave, valor)

buscar_chaining(clave: K): V
  pos ← h(clave)
  para cada nodo en tabla[pos]:
      si nodo.clave = clave: retornar nodo.valor
  retornar nulo

eliminar_chaining(clave: K): booleano
  pos ← h(clave)
  para cada nodo en tabla[pos]:
      si nodo.clave = clave:
          tabla[pos].eliminar(nodo)
          retornar verdadero
  retornar falso
```

---

## Encadenamiento abierto (Open Addressing) — Sondeo Lineal

Busca posición libre dentro de la misma tabla. Usa **tombstones** (loteLibre) para no romper la cadena de sondas al eliminar.

**Fórmula:** `hᵢ = (h0 + i) mod N`

**Problema:** clustering primario — los elementos se agrupan.

### insertar (sondeo lineal)

**Lenguaje natural:** Calcula la posición inicial. Recorre linealmente hasta encontrar una posición vacía (null) o marcada como loteLibre. Antes de insertar verifica que la clave no exista. Si la tabla supera 70% de ocupación, redimensiona.

**Precondición:** clave no nula, tabla no llena.
**Postcondición:** el par (clave, valor) queda en la tabla. Retorna verdadero si se insertó, falso si la clave ya existía.

```
insertar(clave: K, valor: V): booleano
  si clave = nulo: retornar falso
  si factorDeCarga > 0.70: redimensionar()
  h0 ← funcionHash(clave)
  primerLibre ← -1
  i ← 0
  mientras i < tabla.tamaño():
      pos ← (h0 + i) mod tabla.tamaño()
      si tabla[pos] = nulo:
          si primerLibre = -1: primerLibre ← pos
          salir del bucle
      sino si tabla[pos].loteLibre:
          si primerLibre = -1: primerLibre ← pos
      sino si tabla[pos].clave = clave:
          retornar falso   ← ya existe
      i ← i + 1
  si primerLibre = -1: retornar falso   ← tabla llena
  tabla[primerLibre] ← NodoHash(clave, valor)
  retornar verdadero
```

### buscar (sondeo lineal)

**Lenguaje natural:** Calcula la posición inicial y recorre linealmente. Detiene la búsqueda si encuentra una posición null (nunca hubo nada ahí). Salta posiciones loteLibre (hubo algo pero fue eliminado).

**Postcondición:** retorna el valor si la clave existe, nulo si no.

```
buscar(clave: K): V
  si clave = nulo: retornar nulo
  h0 ← funcionHash(clave)
  i ← 0
  mientras i < tabla.tamaño():
      pos ← (h0 + i) mod tabla.tamaño()
      si tabla[pos] = nulo: retornar nulo
      si no tabla[pos].loteLibre:
          si tabla[pos].clave = clave: retornar tabla[pos].valor
      i ← i + 1
  retornar nulo
```

### eliminar (sondeo lineal)

**Lenguaje natural:** Igual que buscar, pero al encontrar la clave marca el nodo como loteLibre (tombstone) en lugar de poner null, para no romper la cadena de búsqueda.

**Postcondición:** si la clave existía, su nodo queda marcado como loteLibre. Retorna verdadero si se eliminó.

```
eliminar(clave: K): booleano
  si clave = nulo: retornar falso
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

---

## Sondeo Cuadrático y Doble Hashing

| Variante | Fórmula | Problema |
|----------|---------|----------|
| Lineal | `hᵢ = (h0 + i) mod N` | Clustering primario |
| Cuadrático | `hᵢ = (h0 + i²) mod N` | Clustering secundario (menor) |
| Doble hashing | `hᵢ = (h0 + i·c) mod N`, c primo con N | Mínimo clustering |

---

## Redimensionar

**Lenguaje natural:** Crea una tabla nueva con capacidad primo ≥ 2×tabla_actual. Reinserta todos los elementos activos (que no son loteLibre).

```
redimensionar(): void
  tablaVieja ← tabla
  tabla ← nueva tabla de tamaño siguientePrimo(tablaVieja.tamaño() * 2)
  para cada posición en tablaVieja:
      si posición ≠ nulo:
          si no posición.loteLibre:
              insertar(posición.clave, posición.valor)
```

---

## Complejidades resumen

| Operación | Promedio | Peor caso |
|-----------|---------|-----------|
| Búsqueda | O(1) | O(n) |
| Inserción | O(1) | O(n) |
| Eliminación chaining | O(1) | O(n) |
| Eliminación open addressing | O(1) promedio | O(n) |

## Desventajas del Hash

1. **Tamaño fijo:** requiere estimación a priori
2. **Eliminación costosa** en open addressing (tombstones)
3. **Sin orden:** no sirve para range queries — usar TreeMap
