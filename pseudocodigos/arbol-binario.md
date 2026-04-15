# Árbol Binario — Referencia de TDA

Referencia rápida del TDA Árbol Binario: definición, terminología, propiedades y algoritmos de recorrido.

---

## Definición

Un **árbol binario** es una estructura jerárquica en la que:
- Cada nodo interno tiene como **máximo dos hijos** ordenados: **hijo izquierdo** e **hijo derecho**
- Puede definirse recursivamente: nodo raíz + subárbol izquierdo (AB) + subárbol derecho (AB), o árbol vacío

---

## Terminología

| Término | Definición |
|---------|-----------|
| Raíz | Nodo sin padre |
| Hoja (nodo externo) | Nodo sin hijos |
| Nodo interno | Nodo con al menos un hijo |
| Altura de un nodo | Longitud del camino más largo hasta una hoja |
| Nivel / Profundidad | Longitud del camino desde la raíz hasta el nodo |
| Subárbol | Un nodo junto con todos sus descendientes |
| Hermanos | Nodos con el mismo padre |

---

## Variantes

### Árbol binario lleno
- Todos los nodos internos tienen exactamente **dos hijos**

### Árbol binario completo
- Todos los niveles completos, excepto eventualmente el último
- El último nivel se llena **de izquierda a derecha**

---

## Propiedades del árbol binario lleno

Notación: *n* = nodos totales, *e* = nodos externos (hojas), *i* = nodos internos, *h* = altura

| Propiedad | Fórmula |
|-----------|---------|
| Hojas vs. internos | `e = i + 1` |
| Total de nodos | `n = 2e − 1` |
| Altura vs. internos | `h ≤ i` |
| Altura máxima | `h ≤ (n − 1) / 2` |
| Hojas vs. altura | `e ≤ 2^h` |
| Altura mínima | `h ≥ log₂(e)` |
| Altura mínima (n) | `h ≥ log₂(n + 1) − 1` |

---

## Fórmulas recursivas

### Tamaño
```
ST = SL + SR + 1
```
El tamaño total es la suma de los subárboles izquierdo y derecho más 1 (la raíz).

### Altura
```
HT = max(HL + 1, HR + 1)
```
La altura es el máximo entre la altura del subárbol izquierdo más 1 y la del derecho más 1.

### Algoritmo para calcular la altura del árbol entero
```
Algoritmo altura(v)
    Si v es nulo entonces
        retornar -1
    Si no
        HL ← altura(izquierdo(v))
        HR ← altura(derecho(v))
        retornar max(HL, HR) + 1
```
Se llama con la raíz: `altura(raiz)`. El caso base retorna `-1` para nodos nulos, de modo que una hoja tiene altura 0.

---

## Recorridos

### Preorden (raíz → izquierdo → derecho)

```
Algoritmo preOrden(v)
    visitar(v)
    Para cada hijo w de v hacer
        preOrden(w)
```

**Aplicación:** imprimir documento estructurado, copiar árbol, serializar.

Ejemplo de orden de visita:
```
      (1)
     /    \
   (2)    (5)
   / \    / \
 (3) (4) (6) (7)
```

---

### Postorden (izquierdo → derecho → raíz)

```
Algoritmo postOrden(v)
    Para cada hijo w de v hacer
        postOrden(w)
    visitar(v)
```

**Aplicación:** calcular espacio de directorios, liberar memoria, evaluar expresiones aritméticas.

Ejemplo de orden de visita:
```
      (7)
     /    \
   (3)    (6)
   / \    / \
 (1) (2) (4) (5)
```

---

### Inorden (izquierdo → raíz → derecho)

```
Algoritmo inOrden(v)
    Si tiene HijoIzquierdo
        HijoIzquierdo.inOrden
    visitar(v)
    Si tiene HijoDerecho
        HijoDerecho.inOrden
```

**Aplicación:** en BST produce valores ordenados; imprimir expresiones aritméticas.

Ejemplo de orden de visita:
```
      (4)
     /    \
   (2)    (6)
   / \    / \
 (1) (3) (5) (7)
```

---

## Árbol de Expresión Aritmética

- Nodos internos = **operadores** (`+`, `-`, `×`, `/`)
- Hojas = **operandos** (números, variables)

Ejemplo: `(2 × (a − 1) + (3 × b))`

```
          (+)
         /    \
       (×)    (×)
       / \    / \
     [2] (-)  [3] [b]
         / \
       [a] [1]
```

### Imprimir expresión (inorden especializado)

```
Algoritmo TNodoAB.printExpression
    Si tiene HijoIzquierdo
        imprimir("(")
        HijoIzquierdo.printExpression
    imprimir
    Si tiene HijoDerecho
        HijoDerecho.printExpression
        imprimir(")")
```

**Salida:** `((2 × (a − 1)) + (3 × b))`

### Evaluar expresión (postorden especializado)

```
Algoritmo TNodoAB.evalExpr
    Si esHoja
        Devolver elemento
    Sino
        x  <-- HijoIzquierdo.evalExpr
        y  <-- HijoDerecho.evalExpr
        ◊  <-- operador contenido
        Devolver x ◊ y
```

---

## Ejercicios tipo parcial

1. **Representar expresión aritmética como árbol:**
   `a - (b * (c + d / (f + g)) + h * (i - j * (k + l)))`

2. **Reconstruir árbol desde recorridos:**
   - Preorden: `A D F G H K L P Q R W Z`
   - Inorden: `G F H K D L A W R Q P Z`

3. Contar las **hojas** de un árbol binario.
4. Calcular la **suma de todos los elementos** de un árbol binario de enteros.
5. Contar los **nodos en el nivel n**.

---

*Fuente: AED UT02-02 Slides — UCU 2026*
