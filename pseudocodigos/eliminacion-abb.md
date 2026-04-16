# Eliminación en ABB — Referencia Visual de los 3 Casos

La eliminación es el algoritmo más complejo del BST. Siempre navega en dos fases: primero localiza el nodo (usando `ELIMINAR` recursivo), luego lo desvincula (usando `QUITARELNODO`).

---

## Pseudocódigo completo (referencia)

```
// Nivel árbol
TArbolBB.eliminar(etiqueta: Comparable): void
  si ¬esVacio() entonces
    raiz ← raiz.eliminar(etiqueta)
  fin si
fin método

// Nivel nodo — navega y reemplaza la referencia al eliminar
TElementoAB.eliminar(etiqueta: Comparable): TElementoAB<T>
  si etiqueta < this.etiqueta entonces
    si hijoIzq ≠ nulo entonces hijoIzq ← hijoIzq.eliminar(etiqueta)
    retornar this
  sino si etiqueta > this.etiqueta entonces
    si hijoDer ≠ nulo entonces hijoDer ← hijoDer.eliminar(etiqueta)
    retornar this
  sino
    retornar quitarNodo()   // este nodo es el que se elimina
  fin si
fin método

// Auxiliar: desvincula this y retorna su reemplazo
TElementoAB.quitarNodo(): TElementoAB<T>
  si hijoIzq = nulo entonces retornar hijoDer   // caso 1 y 2a
  si hijoDer = nulo entonces retornar hijoIzq   // caso 2b
  // caso 3: dos hijos → buscar predecesor inorden
  hijo  ← hijoIzq
  padre ← this
  mientras hijo.hijoDer ≠ nulo hacer
    padre ← hijo
    hijo  ← hijo.hijoDer
  fin mientras
  si padre ≠ this entonces
    padre.hijoDer ← hijo.hijoIzq
    hijo.hijoIzq  ← hijoIzq
  fin si
  hijo.hijoDer ← hijoDer
  retornar hijo
fin método
```

---

## Caso 1 — Nodo hoja

El nodo a eliminar no tiene hijos. `quitarNodo` retorna `null` (porque `hijoIzq = null` → retorna `hijoDer = null`). El padre simplemente pierde la referencia.

```
Eliminar nodo 4:

Antes:                    Después:
        5                         5
       / \                       / \
      1   6                     1   6
       \   \                     \   \
        3   7                     3   7
       / \                       /
      2  [4]                    2
```

---

## Caso 2 — Nodo con un solo hijo

El nodo a eliminar tiene exactamente un hijo. `quitarNodo` retorna ese único hijo directamente, que "sube" a ocupar la posición del eliminado.

**2a — solo hijo derecho:**
```
Antes:              Después:
        7                   7
       / \                 / \
      2   9               2   9
     / \                 / \
    1  [5]              1   3
       /
      3
```
> `5` tiene solo hijo izquierdo (`3`). Se elimina `5`, `3` sube como hijo derecho de `2`.

**2b — solo hijo izquierdo:** simétrico; `quitarNodo` retorna `hijoIzq`.

---

## Caso 3 — Nodo con dos hijos (el más importante)

El nodo a eliminar tiene ambos hijos. No se puede simplemente quitar — se reemplaza su contenido por el **predecesor inorden**: el nodo con la clave más grande del subárbol izquierdo (el que está más "a la derecha" dentro del subárbol izquierdo).

**Por qué el predecesor inorden:**
- Es el mayor de los menores → mantiene la propiedad BST al reemplazar.
- Siempre tiene **a lo sumo un hijo izquierdo** (nunca hijo derecho, o ya no sería el máximo del subárbol) → su propia eliminación cae en Caso 1 o Caso 2.

```
Eliminar nodo 2:

Antes:              Después:
        7                   7
       / \                 / \
     [2]  9               3   9
     / \                 / \
    1   5               1   5
       /                   /
      3                   4
       \
        4
```

**Pasos:**
1. Buscar el predecesor inorden de `2`: bajar a `hijoIzq` (nodo `1`), luego ir siempre a `hijoDer` → llegamos a `3` (no tiene `hijoDer`).
2. Desconectar `3` de su posición: el padre de `3` (que es `1`) apunta al hijo izquierdo de `3` (nulo en este caso).
3. `3` adopta el subárbol izquierdo de `2` (nodo `1`) y el subárbol derecho de `2` (nodo `5`).
4. `3` ocupa la posición de `2`.

---

## Truco para memorizar `QUITARELNODO`

```
¿hijoIzq es nulo?  → retornar hijoDer   (puede ser nulo → Caso 1, o nodo → Caso 2a)
¿hijoDer es nulo?  → retornar hijoIzq   (Caso 2b)
Ambos existen:
  → bajar por hijoIzq, luego ir todo a la derecha
  → ese nodo (el más derecho del subárbol izq) es el predecesor
  → desconectarlo, ponerlo en lugar del eliminado
```

---

## Análisis de Orden

- Localizar el nodo: O(h)
- Encontrar el predecesor (en Caso 3): O(h) adicional dentro del subárbol izquierdo
- Total: O(h)
  - Árbol balanceado (AVL): O(log n)
  - Árbol degenerado: O(n)
