# TDA Árbol Binario de Búsqueda (ABB / BST) — Pseudocódigo

Implementación: árbol binario donde para todo nodo N:
- todos los nodos del subárbol izquierdo tienen etiqueta **< N.etiqueta**
- todos los nodos del subárbol derecho tienen etiqueta **> N.etiqueta**

Las operaciones se implementan con un método en el árbol que delega en un método recursivo del nodo.

---

## Estructura interna

```
TElementoAB<T>:
  etiqueta: Comparable
  dato:     T
  hijoIzq:  TElementoAB<T>   ← nulo si no tiene hijo izquierdo
  hijoDer:  TElementoAB<T>   ← nulo si no tiene hijo derecho

TArbolBB<T>:
  raiz: TElementoAB<T>        ← nulo si el árbol está vacío
```

---

## esVacio()

```
TArbolBB.esVacio(): booleano
  retornar raiz = nulo
fin método
```

**Orden:** O(1)

---

## insertar(etiqueta, dato)

**Lenguaje natural:** Crea un nuevo elemento con la etiqueta y dato dados. Si el árbol está vacío, lo coloca como raíz. Si no, delega la inserción recursiva al nodo raíz respetando la propiedad BST.

**Precondición:** `etiqueta ≠ nulo`.  
**Postcondición:** el elemento queda insertado en el lugar correcto. Si ya existía una etiqueta igual, no se inserta (retorna `falso`).

```
// Método del árbol
TArbolBB.insertar(etiqueta: Comparable, dato: T): booleano
  elemento ← nuevo TElementoAB(etiqueta, dato)
  si esVacio() entonces
    raiz ← elemento
    retornar verdadero
  sino
    retornar raiz.insertar(elemento)
  fin si
fin método

// Método del nodo (recursivo)
TElementoAB.insertar(elemento: TElementoAB<T>): booleano
  si elemento.etiqueta < this.etiqueta entonces
    si hijoIzq = nulo entonces
      hijoIzq ← elemento
      retornar verdadero
    sino
      retornar hijoIzq.insertar(elemento)
    fin si
  sino si elemento.etiqueta > this.etiqueta entonces
    si hijoDer = nulo entonces
      hijoDer ← elemento
      retornar verdadero
    sino
      retornar hijoDer.insertar(elemento)
    fin si
  sino
    retornar falso    // etiqueta duplicada, no se inserta
  fin si
fin método
```

**Orden:** O(h) donde h = altura del árbol.  
- Caso promedio (árbol balanceado): O(log n)  
- Peor caso (árbol degenerado / secuencia ordenada): O(n)

---

## buscar(etiqueta)

**Lenguaje natural:** Recorre el árbol comparando la etiqueta buscada con la del nodo actual, bajando a izquierda o derecha según corresponda.

**Precondición:** ninguna.  
**Postcondición:** retorna el dato del elemento con esa etiqueta, o `nulo` si no existe.

```
// Método del árbol
TArbolBB.buscar(etiqueta: Comparable): T
  si esVacio() entonces
    retornar nulo
  fin si
  nodo ← raiz.buscar(etiqueta)
  si nodo ≠ nulo entonces
    retornar nodo.getDato()
  sino
    retornar nulo
  fin si
fin método

// Método del nodo (recursivo)
TElementoAB.buscar(etiqueta: Comparable): TElementoAB<T>
  si etiqueta = this.etiqueta entonces
    retornar this
  sino si etiqueta < this.etiqueta entonces
    si hijoIzq ≠ nulo entonces
      retornar hijoIzq.buscar(etiqueta)
    sino
      retornar nulo
    fin si
  sino
    si hijoDer ≠ nulo entonces
      retornar hijoDer.buscar(etiqueta)
    sino
      retornar nulo
    fin si
  fin si
fin método
```

**Orden:** O(h) — igual que insertar.

---

## eliminar(etiqueta)

**Lenguaje natural:** Elimina el nodo con la etiqueta dada. Hay tres casos según los hijos del nodo a eliminar:
1. **Nodo hoja** (sin hijos): simplemente se desvincula.
2. **Un solo hijo**: se reemplaza el nodo por ese hijo.
3. **Dos hijos**: se reemplaza el nodo por el **predecesor inorden** (máximo del subárbol izquierdo), luego se elimina ese predecesor.

**Precondición:** ninguna.  
**Postcondición:** si existía un nodo con esa etiqueta, es eliminado manteniendo la propiedad BST.

```
// Método del árbol
TArbolBB.eliminar(etiqueta: Comparable): void
  si ¬esVacio() entonces
    raiz ← raiz.eliminar(etiqueta)
  fin si
fin método

// Método del nodo (recursivo) — retorna el nodo que ocupa su posición tras la eliminación
TElementoAB.eliminar(etiqueta: Comparable): TElementoAB<T>
  si etiqueta < this.etiqueta entonces
    si hijoIzq ≠ nulo entonces
      hijoIzq ← hijoIzq.eliminar(etiqueta)
    fin si
    retornar this
  sino si etiqueta > this.etiqueta entonces
    si hijoDer ≠ nulo entonces
      hijoDer ← hijoDer.eliminar(etiqueta)
    fin si
    retornar this
  sino
    // Encontré el nodo a eliminar
    retornar quitarNodo()
  fin si
fin método

// Auxiliar: desvincula this y retorna su reemplazo
TElementoAB.quitarNodo(): TElementoAB<T>
  // Caso 1 y 2: un solo hijo o ninguno
  si hijoIzq = nulo entonces retornar hijoDer
  si hijoDer = nulo entonces retornar hijoIzq

  // Caso 3: dos hijos → buscar predecesor inorden (máximo del subárbol izquierdo)
  hijo  ← hijoIzq
  padre ← this
  mientras hijo.hijoDer ≠ nulo hacer
    padre ← hijo
    hijo  ← hijo.hijoDer
  fin mientras
  // "hijo" es ahora el predecesor inorden
  si padre ≠ this entonces
    padre.hijoDer ← hijo.hijoIzq   // desvinculamos el predecesor
    hijo.hijoIzq  ← hijoIzq        // el predecesor adopta el subárbol izquierdo
  fin si
  hijo.hijoDer ← hijoDer            // el predecesor adopta el subárbol derecho
  retornar hijo                     // el predecesor ocupa la posición del eliminado
fin método
```

**Orden:** O(h) — buscar el nodo + bajar hasta el predecesor.

---

## obtenerTamaño()

**Lenguaje natural:** Cuenta todos los nodos del árbol recursivamente: 1 (nodo actual) + tamaño(izq) + tamaño(der).

**Precondición:** ninguna.  
**Postcondición:** retorna la cantidad total de nodos.

```
// Método del árbol
TArbolBB.obtenerTamaño(): entero
  si esVacio() entonces retornar 0
  retornar raiz.obtenerTamaño()
fin método

// Método del nodo
TElementoAB.obtenerTamaño(): entero
  tamaño ← 1
  si hijoIzq ≠ nulo entonces
    tamaño ← tamaño + hijoIzq.obtenerTamaño()
  fin si
  si hijoDer ≠ nulo entonces
    tamaño ← tamaño + hijoDer.obtenerTamaño()
  fin si
  retornar tamaño
fin método
```

**Orden:** O(n) — visita cada nodo exactamente una vez.

---

## obtenerAltura()

**Lenguaje natural:** La altura de un nodo es 1 + el máximo entre la altura del subárbol izquierdo y la del derecho. La altura de un nodo nulo es -1 (o 0 según la convención).

**Precondición:** ninguna.  
**Postcondición:** retorna la altura del árbol. Un árbol vacío retorna -1; un árbol de un solo nodo retorna 0.

```
// Método del árbol
TArbolBB.obtenerAltura(): entero
  si esVacio() entonces retornar -1
  retornar raiz.obtenerAltura()
fin método

// Método del nodo
TElementoAB.obtenerAltura(): entero
  alturaIzq ← -1
  alturaDer ← -1
  si hijoIzq ≠ nulo entonces
    alturaIzq ← hijoIzq.obtenerAltura()
  fin si
  si hijoDer ≠ nulo entonces
    alturaDer ← hijoDer.obtenerAltura()
  fin si
  retornar 1 + max(alturaIzq, alturaDer)
fin método
```

**Orden:** O(n) — visita cada nodo exactamente una vez.

---

## Recorridos

### inOrden() — izquierda → raíz → derecha

Resultado: nodos en **orden ascendente** de etiqueta.

```
TArbolBB.inOrden(): Lista<T>
  lista ← nueva Lista vacía
  si ¬esVacio() entonces
    raiz.inOrden(lista)
  fin si
  retornar lista
fin método

TElementoAB.inOrden(lista: Lista<T>): void
  si hijoIzq ≠ nulo entonces hijoIzq.inOrden(lista)
  lista.insertar(this.getDato())
  si hijoDer ≠ nulo entonces hijoDer.inOrden(lista)
fin método
```

**Orden:** O(n)

### preOrden() — raíz → izquierda → derecha

```
TElementoAB.preOrden(lista: Lista<T>): void
  lista.insertar(this.getDato())
  si hijoIzq ≠ nulo entonces hijoIzq.preOrden(lista)
  si hijoDer ≠ nulo entonces hijoDer.preOrden(lista)
fin método
```

**Orden:** O(n)

### postOrden() — izquierda → derecha → raíz

```
TElementoAB.postOrden(lista: Lista<T>): void
  si hijoIzq ≠ nulo entonces hijoIzq.postOrden(lista)
  si hijoDer ≠ nulo entonces hijoDer.postOrden(lista)
  lista.insertar(this.getDato())
fin método
```

**Orden:** O(n)

---

## Resumen de Órdenes

| Operación | Orden promedio | Orden peor caso |
|-----------|----------------|-----------------|
| `esVacio()` | O(1) | O(1) |
| `insertar` | O(log n) | O(n) |
| `buscar` | O(log n) | O(n) |
| `eliminar` | O(log n) | O(n) |
| `obtenerTamaño()` | O(n) | O(n) |
| `obtenerAltura()` | O(n) | O(n) |
| `inOrden / preOrden / postOrden` | O(n) | O(n) |

> El peor caso O(n) ocurre cuando el árbol está **degenerado** (insertado en orden creciente o decreciente), lo que lo convierte esencialmente en una lista enlazada. El AVL elimina este problema.
