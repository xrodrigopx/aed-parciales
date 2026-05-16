# Ejercicio de Parcial: Calcular el Tamaño de un Árbol Binario

---

## Definición

El **tamaño** de un árbol es la cantidad total de nodos que contiene.

- Árbol vacío → tamaño = **0**
- En general: `tamaño = tamaño subárbol izquierdo + tamaño subárbol derecho + 1`

El `+1` cuenta el nodo actual (la raíz del subárbol).

**Ejemplo:**

```
        10          ← cuenta
       /   \
      5    20       ← cuentan
     / \
    2   7           ← cuentan
```

Tamaño = 5 nodos.

---

## Lenguaje Natural

**`TArbolBB.calcularTamanio()`:**
Si el árbol está vacío, retornar 0. Si no, delegar al nodo raíz, que contará los nodos recursivamente.

**`TElementoAB.calcularTamanio()`:**
Retornar 1 (este nodo) más el tamaño del subárbol izquierdo (0 si es nulo) más el tamaño del subárbol derecho (0 si es nulo).

---

## Pre y Post Condiciones

**`TArbolBB.calcularTamanio()`**
- **Precondición:** ninguna.
- **Postcondición:** retorna la cantidad de nodos del árbol. Si el árbol está vacío, retorna 0.

**`TElementoAB.calcularTamanio()`**
- **Precondición:** `this ≠ nulo`.
- **Postcondición:** retorna la cantidad de nodos del subárbol con raíz en `this`.

---

## Pseudocódigo

```
// Método del árbol
TArbolBB.calcularTamanio(): entero
  si esVacio() entonces
    retornar 0
  fin si
  retornar raiz.calcularTamanio()
fin método

// Método del nodo (recursivo)
TElementoAB.calcularTamanio(): entero
  tamanio ← 1
  si hijoIzq ≠ nulo entonces
    tamanio ← tamanio + hijoIzq.calcularTamanio()
  fin si
  si hijoDer ≠ nulo entonces
    tamanio ← tamanio + hijoDer.calcularTamanio()
  fin si
  retornar tamanio
fin método
```

**Demostración de invocación:**
```
cantidad ← arbol.calcularTamanio()
```

---

## Traza sobre el ejemplo

```
        10
       /   \
      5    20
     / \
    2   7
```

| Llamada | tamanio inicial | + hijoIzq | + hijoDer | retorna |
|---------|-----------------|-----------|-----------|---------|
| nodo 2  | 1               | —         | —         | 1       |
| nodo 7  | 1               | —         | —         | 1       |
| nodo 5  | 1               | + 1 (2)   | + 1 (7)   | 3       |
| nodo 20 | 1               | —         | —         | 1       |
| nodo 10 | 1               | + 3 (5)   | + 1 (20)  | **5**   |

---

## Análisis del Orden de Tiempo de Ejecución

- `calcularTamanio` en el nodo visita cada nodo exactamente una vez → **O(n)**
- No hay estructuras auxiliares; la pila de recursión ocupa **O(h)** espacio, donde `h` es la altura del árbol.

**Orden final: O(n)**
