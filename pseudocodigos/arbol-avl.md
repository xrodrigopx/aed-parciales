# TDA Árbol AVL — Pseudocódigo

El AVL es un ABB **autobalanceado**: después de cada inserción o eliminación, el árbol verifica y restaura la propiedad AVL.

**Propiedad AVL:** Para todo nodo N, `|h(N.hijoIzq) - h(N.hijoDer)| ≤ 1`

Esto garantiza que la altura del árbol es siempre **O(log n)**, lo que da O(log n) garantizado para búsqueda, inserción y eliminación.

---

## Definiciones clave

```
// Factor de Balance (BF) de un nodo:
BF(nodo) = h(nodo.hijoDer) - h(nodo.hijoIzq)
           donde h(nulo) = -1

// Nodo balanceado: BF ∈ {-1, 0, +1}
// Nodo desbalanceado: |BF| = 2  → hay que rotar
```

---

## Tipos de Desbalance y Rotación

| BF(nodo) | BF(hijo afectado) | Tipo | Solución |
|----------|-------------------|------|----------|
| -2 | ≤ 0 | **LL** (izq-izq) | Rotación simple **derecha** en nodo |
| -2 | +1 | **LR** (izq-der) | Rotación **izquierda** en hijo izq, luego **derecha** en nodo |
| +2 | ≥ 0 | **RR** (der-der) | Rotación simple **izquierda** en nodo |
| +2 | -1 | **RL** (der-izq) | Rotación **derecha** en hijo der, luego **izquierda** en nodo |

---

## Rotación Simple Derecha (para LL)

**Lenguaje natural:** El nodo desbalanceado `k2` tiene su subárbol izquierdo `k1` más pesado. Se hace rotar `k1` hacia arriba: `k1` pasa a ser la nueva raíz del subárbol, `k2` pasa a ser el hijo derecho de `k1`, y el antiguo hijo derecho de `k1` pasa al izquierdo de `k2`.

```
rotacionDerecha(k2: TElementoAVL): TElementoAVL
  k1       ← k2.hijoIzq
  k2.hijoIzq ← k1.hijoDer    // el hijo derecho de k1 pasa al izquierdo de k2
  k1.hijoDer ← k2             // k2 pasa a ser hijo derecho de k1
  // actualizar alturas (k2 primero, luego k1)
  actualizarAltura(k2)
  actualizarAltura(k1)
  retornar k1                 // k1 es la nueva raíz del subárbol
fin método
```

**Antes:**         **Después:**
```
    k2                 k1
   /                  /  \
  k1         →      A    k2
 /  \                    /
A    B                  B
```

**Orden:** O(1)

---

## Rotación Simple Izquierda (para RR)

**Lenguaje natural:** Simétrico a la rotación derecha. El nodo `k1` sube, `k2` (desbalanceado) baja a la izquierda.

```
rotacionIzquierda(k1: TElementoAVL): TElementoAVL
  k2       ← k1.hijoDer
  k1.hijoDer ← k2.hijoIzq    // el hijo izquierdo de k2 pasa al derecho de k1
  k2.hijoIzq ← k1             // k1 pasa a ser hijo izquierdo de k2
  actualizarAltura(k1)
  actualizarAltura(k2)
  retornar k2
fin método
```

**Antes:**     **Después:**
```
k1                  k2
  \                /  \
  k2      →      k1    B
 /  \              \
A    B              A
```

**Orden:** O(1)

---

## Rotación Doble LR (izquierda-derecha)

**Lenguaje natural:** El subárbol izquierdo de `k3` tiene su parte derecha más pesada. Se resuelve con dos rotaciones: primero izquierda en el hijo izquierdo `k1`, luego derecha en `k3`.

```
rotacionDobleIzquierdaDerecha(k3: TElementoAVL): TElementoAVL
  k3.hijoIzq ← rotacionIzquierda(k3.hijoIzq)   // paso 1: izquierda en hijo
  retornar rotacionDerecha(k3)                    // paso 2: derecha en nodo
fin método
```

**Antes:**       **Después:**
```
    k3                k2
   /                 /  \
  k1      →        k1   k3
   \
   k2
```

**Orden:** O(1)

---

## Rotación Doble RL (derecha-izquierda)

**Lenguaje natural:** Simétrico al LR. Se resuelve con derecha en el hijo derecho, luego izquierda en el nodo.

```
rotacionDobleDerechaIzquierda(k1: TElementoAVL): TElementoAVL
  k1.hijoDer ← rotacionDerecha(k1.hijoDer)   // paso 1: derecha en hijo
  retornar rotacionIzquierda(k1)               // paso 2: izquierda en nodo
fin método
```

**Antes:**    **Después:**
```
k1                k2
  \              /  \
  k3    →      k1   k3
 /
k2
```

**Orden:** O(1)

---

## balancear(nodo)

**Lenguaje natural:** Calcula el BF del nodo. Si está desbalanceado, identifica el tipo de rotación necesaria y la aplica. Retorna la nueva raíz del subárbol (puede ser el mismo nodo o el que sube por la rotación).

```
balancear(nodo: TElementoAVL): TElementoAVL
  actualizarAltura(nodo)
  bf ← factorBalance(nodo)

  si bf = -2 entonces                           // desbalance a la izquierda
    si factorBalance(nodo.hijoIzq) ≤ 0 entonces
      retornar rotacionDerecha(nodo)            // LL
    sino
      retornar rotacionDobleIzquierdaDerecha(nodo) // LR
    fin si
  sino si bf = +2 entonces                      // desbalance a la derecha
    si factorBalance(nodo.hijoDer) ≥ 0 entonces
      retornar rotacionIzquierda(nodo)          // RR
    sino
      retornar rotacionDobleDerechaIzquierda(nodo) // RL
    fin si
  fin si

  retornar nodo    // ya balanceado
fin método
```

**Orden:** O(1) — las rotaciones son O(1) y el cálculo de BF es O(1) si se guarda la altura.

---

## insertar(etiqueta, dato) — con balanceo AVL

**Lenguaje natural:** Igual que en el BST, pero después de cada inserción recursiva se llama a `balancear()` para verificar y corregir el balance en el camino de regreso hasta la raíz.

**Precondición:** `etiqueta ≠ nulo`.  
**Postcondición:** el elemento queda insertado y el árbol mantiene la propiedad AVL.

```
// Método del árbol
TArbolAVL.insertar(etiqueta: Comparable, dato: T): void
  elemento ← nuevo TElementoAVL(etiqueta, dato)
  si esVacio() entonces
    raiz ← elemento
  sino
    raiz ← raiz.insertarAVL(elemento)
  fin si
fin método

// Método del nodo (recursivo + balanceo)
TElementoAVL.insertarAVL(elemento: TElementoAVL): TElementoAVL
  si elemento.etiqueta < this.etiqueta entonces
    si hijoIzq = nulo entonces
      hijoIzq ← elemento
    sino
      hijoIzq ← hijoIzq.insertarAVL(elemento)
    fin si
  sino si elemento.etiqueta > this.etiqueta entonces
    si hijoDer = nulo entonces
      hijoDer ← elemento
    sino
      hijoDer ← hijoDer.insertarAVL(elemento)
    fin si
  fin si
  // Al retornar de la recursión, verificar y balancear
  retornar balancear(this)
fin método
```

**Orden:** O(log n) — la altura del AVL es siempre O(log n), por lo tanto insertar nunca baja más de O(log n) niveles. Las rotaciones son O(1) y se aplican a lo sumo una vez por inserción.

---

## Auxiliares

```
altura(nodo: TElementoAVL): entero
  si nodo = nulo entonces retornar -1
  retornar nodo.altura
fin método

actualizarAltura(nodo: TElementoAVL): void
  nodo.altura ← 1 + max(altura(nodo.hijoIzq), altura(nodo.hijoDer))
fin método

factorBalance(nodo: TElementoAVL): entero
  retornar altura(nodo.hijoDer) - altura(nodo.hijoIzq)
fin método
```

Todos O(1) si la altura se almacena en el nodo.

---

## Resumen de Órdenes

| Operación | Orden garantizado | Motivo |
|-----------|-------------------|--------|
| `buscar` | **O(log n)** | h = O(log n) siempre |
| `insertar` | **O(log n)** | inserción BST + O(1) rotaciones |
| `eliminar` | **O(log n)** | idem |
| `obtenerAltura()` | O(1) | altura almacenada en el nodo |
| `rotacionDerecha / Izquierda` | O(1) | solo reasigna punteros |
| `rotacionDoble LR / RL` | O(1) | dos rotaciones simples |

> **Diferencia clave con BST:** el AVL paga un pequeño costo extra en inserción/eliminación (rotaciones, actualizar alturas) pero **garantiza** O(log n) en todo momento. El BST puede degradarse a O(n) con datos ordenados.
