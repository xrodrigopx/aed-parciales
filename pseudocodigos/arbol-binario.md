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

**Orden:** O(n) — visita cada nodo exactamente una vez.

---

## Recorridos

### Preorden (raíz → izquierdo → derecho)

```
Algoritmo preOrden(v)
    visitar(v)
    Para cada hijo w de v hacer
        preOrden(w)
```

**Orden:** O(n) — visita cada nodo exactamente una vez.

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

**Orden:** O(n) — visita cada nodo exactamente una vez.

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

**Orden:** O(n) — visita cada nodo exactamente una vez.

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

**Orden:** O(n) — visita cada nodo exactamente una vez.

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

**Orden:** O(n) — visita cada nodo exactamente una vez.

---

## Ejercicios tipo parcial

---

### 1. Representar expresión aritmética como árbol

`a - (b * (c + d / (f + g)) + h * (i - j * (k + l)))`

**Análisis:** La raíz es el operador de menor precedencia más externo. Dentro del paréntesis mayor, `+` separa `b*(...)` de `h*(...)`. La división y la multiplicación tienen mayor precedencia que la suma/resta.

```
                        (-)
                       /    \
                     [a]    (+)
                           /    \
                         (*)    (*)
                        /   \  /   \
                      [b]  (+)[h]  (-)
                          /  \   /    \
                        [c]  (/) [i]  (*)
                            /  \    /   \
                          [d]  (+)[j]  (+)
                              /  \   /   \
                            [f] [g][k]  [l]
```

**Verificación con inorden:** `a - (b * (c + d / (f + g)) + h * (i - j * (k + l)))` ✓

---

### 2. Reconstruir árbol desde recorridos

- Preorden: `A D F G H K L P Q R W Z`
- Inorden:  `G F H K D L A W R Q P Z`

**Método:** el primer elemento del preorden es siempre la raíz. En el inorden, todo lo que está a su izquierda es el subárbol izquierdo y lo que está a su derecha es el subárbol derecho. Se aplica recursivamente.

| Paso | Raíz | Inorden izquierdo | Inorden derecho | Preorden izquierdo | Preorden derecho |
|------|------|-------------------|-----------------|--------------------|------------------|
| 1 | **A** | G F H K D L | W R Q P Z | D F G H K L | P Q R W Z |
| 2 | **D** | G F H K | L | F G H K | L |
| 3 | **F** | G | H K | G | H K |
| 4 | **G** | — | — | — | — |
| 5 | **H** | — | K | — | K |
| 6 | **K** | — | — | — | — |
| 7 | **L** | — | — | — | — |
| 8 | **P** | W R Q | Z | Q R W | Z |
| 9 | **Q** | W R | — | R W | — |
| 10 | **R** | W | — | W | — |
| 11 | **W** | — | — | — | — |
| 12 | **Z** | — | — | — | — |

**Árbol resultante:**

```
              A
            /   \
          D       P
         / \     / \
        F   L   Q   Z
       / \     /
      G   H   R
           \  /
            K W
```

```
A
├─ D (izquierdo)
│  ├─ F (izquierdo)
│  │  ├─ G (izquierdo, hoja)
│  │  └─ H (derecho)
│  │     └─ K (derecho, hoja)
│  └─ L (derecho, hoja)
└─ P (derecho)
   ├─ Q (izquierdo)
   │  └─ R (izquierdo)
   │     └─ W (izquierdo, hoja)
   └─ Z (derecho, hoja)
```

**Verificación:** preorden de este árbol → A D F G H K L P Q R W Z ✓  
Inorden → G F H K D L A W R Q P Z ✓

---

### 3. Contar las hojas de un árbol binario

**Lenguaje natural:** Un nodo es hoja si no tiene hijos. Se recorre el árbol completo: si el nodo actual es hoja, retorna 1; si no, retorna la suma de hojas del subárbol izquierdo más el derecho.

```
TArbolBB.contarHojas(): entero
  si esVacio() entonces
    retornar 0
  fin si
  retornar raiz.contarHojas()
fin método

TElementoAB.contarHojas(): entero
  si hijoIzq = nulo Y hijoDer = nulo entonces
    retornar 1         // nodo hoja
  fin si
  contador ← 0
  si hijoIzq ≠ nulo entonces
    contador ← contador + hijoIzq.contarHojas()
  fin si
  si hijoDer ≠ nulo entonces
    contador ← contador + hijoDer.contarHojas()
  fin si
  retornar contador
fin método
```

**Orden:** O(n) — visita cada nodo exactamente una vez.

---

### 4. Calcular la suma de todos los elementos de un árbol binario de enteros

**Lenguaje natural:** La suma del árbol es el valor del nodo actual más la suma de su subárbol izquierdo más la suma de su subárbol derecho. Caso base: árbol vacío retorna 0.

```
TArbolBB.sumarElementos(): entero
  si esVacio() entonces
    retornar 0
  fin si
  retornar raiz.sumarElementos()
fin método

TElementoAB.sumarElementos(): entero
  suma ← this.getDato()
  si hijoIzq ≠ nulo entonces
    suma ← suma + hijoIzq.sumarElementos()
  fin si
  si hijoDer ≠ nulo entonces
    suma ← suma + hijoDer.sumarElementos()
  fin si
  retornar suma
fin método
```

**Orden:** O(n) — visita cada nodo exactamente una vez.

---

### 5. Contar los nodos en el nivel n

**Lenguaje natural:** Se recorre el árbol pasando el nivel actual como parámetro. Cuando el nivel actual coincide con el objetivo, el nodo cuenta como 1. Si el nivel actual es mayor al objetivo, se detiene la recursión (los nodos más profundos no interesan).

```
TArbolBB.contarEnNivel(nivel: entero): entero
  si esVacio() entonces
    retornar 0
  fin si
  retornar raiz.contarEnNivel(nivel, 0)
fin método

TElementoAB.contarEnNivel(objetivo: entero, actual: entero): entero
  si actual = objetivo entonces
    retornar 1          // este nodo está exactamente en el nivel buscado
  fin si
  si actual > objetivo entonces
    retornar 0          // pasamos del nivel, no hay nada aquí
  fin si
  contador ← 0
  si hijoIzq ≠ nulo entonces
    contador ← contador + hijoIzq.contarEnNivel(objetivo, actual + 1)
  fin si
  si hijoDer ≠ nulo entonces
    contador ← contador + hijoDer.contarEnNivel(objetivo, actual + 1)
  fin si
  retornar contador
fin método
```

**Orden:** O(n) — en el peor caso (nivel = hoja) visita todos los nodos. Para niveles altos el corte `actual > objetivo` puede ahorrar visitas, pero el peor caso sigue siendo O(n).

---

*Fuente: AED UT02-02 Slides — UCU 2026*
