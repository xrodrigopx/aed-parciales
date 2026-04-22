# Ejercicio de Parcial: Calcular la Altura de un Árbol Binario

---

## Definición

La **altura** de un árbol es la longitud del camino más largo desde la raíz hasta una hoja.

- Árbol vacío → altura = **-1** (convenio)
- Un único nodo (hoja) → altura = **0**
- En general: `altura = max(alturaIzq, alturaDer) + 1`

**Ejemplo:**

```
        10          ← nivel 0
       /   \
      5    20       ← nivel 1
     / \
    2   7           ← nivel 2
```

- Altura del subárbol izquierdo de 10 = 2
- Altura del subárbol derecho de 10 = 1
- Altura del árbol = max(2, 1) + 1 = **2** (se sube un nivel desde los hijos)

---

## Lenguaje Natural

**`TArbolBB.calcularAltura()`:**
Si el árbol está vacío, retornar -1. Si no, delegar al nodo raíz, que calculará la altura recursivamente.

**`TElementoAB.calcularAltura()`:**
Calcular la altura del subárbol izquierdo y la del subárbol derecho (caso base: hijo nulo = -1). Retornar el máximo de ambas alturas más 1.

---

## Pre y Post Condiciones

**`TArbolBB.calcularAltura()`**
- **Precondición:** ninguna.
- **Postcondición:** retorna la altura del árbol. Si el árbol está vacío, retorna -1.

**`TElementoAB.calcularAltura()`**
- **Precondición:** `this ≠ nulo`.
- **Postcondición:** retorna la longitud del camino más largo desde `this` hasta una hoja de su subárbol.

---

## Pseudocódigo

```
// Método del árbol
TArbolBB.calcularAltura(): entero
  si esVacio() entonces
    retornar -1
  fin si
  retornar raiz.calcularAltura()
fin método

// Método del nodo (recursivo)
TElementoAB.calcularAltura(): entero
  alturaIzq ← -1
  alturaDer ← -1
  si hijoIzq ≠ nulo entonces
    alturaIzq ← hijoIzq.calcularAltura()
  fin si
  si hijoDer ≠ nulo entonces
    alturaDer ← hijoDer.calcularAltura()
  fin si
  retornar max(alturaIzq, alturaDer) + 1
fin método
```

**Demostración de invocación:**
```
altura ← arbol.calcularAltura()
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

| Llamada | alturaIzq | alturaDer | retorna |
|---------|-----------|-----------|---------|
| nodo 2  | -1        | -1        | 0       |
| nodo 7  | -1        | -1        | 0       |
| nodo 5  | 0 (de 2)  | 0 (de 7)  | 1       |
| nodo 20 | -1        | -1        | 0       |
| nodo 10 | 1 (de 5)  | 0 (de 20) | **2**   |

---

## Análisis del Orden de Tiempo de Ejecución

- `calcularAltura` en el nodo visita cada nodo exactamente una vez → **O(n)**
- No hay estructuras auxiliares; la pila de recursión ocupa **O(h)** espacio, donde `h` es la altura del árbol.

**Orden final: O(n)**
