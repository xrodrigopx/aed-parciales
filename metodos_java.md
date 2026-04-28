---
title: "Referencia de Métodos Java por Estructura"
type: reference
tags: [java, tda, metodos, lista, cola, pila, conjunto, bst, arbol]
created: 2026-04-28
updated: 2026-04-28
---

# Referencia de Métodos Java por Estructura

Catálogo completo de métodos disponibles en cada TDA del curso AED UCU.  
Fuentes: código base 2024-S1, código base festival-otaku, código base farmachop, pseudocódigos.

> **Restricción del parcial:** solo usar métodos de esta lista. Prohibido `ArrayList`, `LinkedList`, `java.util.Stack`, ni ninguna clase de `java.util.Collections`.

---

## Índice

- [Lista\<T\> (farmachop)](#listat-farmachop)
- [Nodo\<T\> (farmachop)](#nodot-farmachop)
- [Lista Simplemente Enlazada (pseudocódigo)](#lista-simplemente-enlazada-pseudocódigo)
- [Lista Doblemente Enlazada (pseudocódigo)](#lista-doblemente-enlazada-pseudocódigo)
- [TArbolBB\<T\> / TArbolDeProductos](#tarbollbbt--tarboldeproductos)
- [TElementoAB\<T\> / IElementoAB\<T\>](#telementoabt--ielementoabt)
- [Cola\<T\> (festival-otaku)](#colat-festival-otaku)
- [Pila\<T\> (festival-otaku)](#pilat-festival-otaku)
- [Lista basada en arreglo con validación de duplicados](#lista-basada-en-arreglo-con-validación-de-duplicados)
- [ManejadorArchivosGenerico](#manejadorarchivosgenerico)
- [Patrones de uso frecuentes](#patrones-de-uso-frecuentes)

---

## Lista\<T\> (farmachop)

Implementación: lista simplemente enlazada con puntero `primero`. Sin puntero al último nodo.

| Firma | Retorno | Descripción | Orden |
|-------|---------|-------------|-------|
| `insertar(Nodo<T> nodo)` | `void` | Inserta el nodo al **final** de la lista | O(n) |
| `insertar(Comparable etiqueta, T dato)` | `void` | Crea un nodo y lo inserta al final | O(n) |
| `buscar(Comparable clave)` | `Nodo<T>` | Retorna el nodo con esa etiqueta, o `null` si no existe | O(n) |
| `eliminar(Comparable clave)` | `boolean` | Elimina el primer nodo con esa etiqueta; `true` si tuvo éxito | O(n) |
| `cantElementos()` | `int` | Cantidad de nodos en la lista | O(n) |
| `esVacia()` | `boolean` | `true` si la lista no tiene elementos | O(1) |
| `getPrimero()` | `Nodo<T>` | Retorna el primer nodo (o `null` si vacía) | O(1) |
| `setPrimero(Nodo<T> nodo)` | `void` | Fuerza el primer nodo (usar con cuidado) | O(1) |
| `imprimir()` | `String` | Imprime etiquetas en consola, retorna `""` | O(n) |
| `imprimir(String separador)` | `String` | Retorna etiquetas separadas por el separador | O(n) |

> **No existe** `get(int i)`, `add()`, `size()`, `sort()`, ni `insertarAlFrente()`.  
> Para acceder al elemento N-ésimo hay que encadenar `.getSiguiente()` N veces.

---

## Nodo\<T\> (farmachop)

| Firma | Retorno | Descripción | Orden |
|-------|---------|-------------|-------|
| `new Nodo<>(Comparable etiqueta, T dato)` | — | Constructor | O(1) |
| `getDato()` | `T` | Retorna el dato almacenado | O(1) |
| `setDato(T dato)` | `void` | Reemplaza el dato (usado en selection sort) | O(1) |
| `getEtiqueta()` | `Comparable` | Retorna la clave del nodo | O(1) |
| `getSiguiente()` | `Nodo<T>` | Retorna el nodo siguiente, o `null` | O(1) |
| `setSiguiente(Nodo<T> nodo)` | `void` | Enlaza el siguiente nodo | O(1) |
| `imprimir()` | `void` | Imprime `dato.toString()` en consola | O(1) |
| `imprimirEtiqueta()` | `void` | Imprime la etiqueta en consola | O(1) |
| `clonar()` | `Nodo<T>` | Retorna un nodo nuevo con misma etiqueta y dato | O(1) |
| `equals(Nodo otro)` | `boolean` | Compara por `dato.equals()` | O(1) |
| `compareTo(Comparable etiqueta)` | `int` | Compara etiquetas; retorna negativo/0/positivo | O(1) |

---

## Lista Simplemente Enlazada (pseudocódigo)

Estructura conceptual del curso. **La implementación Java del parcial es `Lista<T>` de farmachop** (ver sección anterior), que expone un subconjunto de estas operaciones y **no incluye `insertarAlFrente`**.

### Estructura interna

```
Nodo<T>:
  etiqueta:  Comparable
  dato:      T
  siguiente: Nodo<T>   ← null si es el último

Lista<T>:
  primero: Nodo<T>     ← null si la lista está vacía
```

### Tabla de métodos

| Firma | Retorno | Descripción | Orden |
|-------|---------|-------------|-------|
| `esVacia()` | `booleano` | `verdadero` si `primero = nulo` | O(1) |
| `insertar(etiqueta, dato)` | `void` | Inserta al **final**; recorre toda la lista | O(n) |
| `insertarAlFrente(etiqueta, dato)` | `void` | Inserta al principio sin recorrer | O(1) |
| `buscar(clave)` | `Nodo<T>` | Primer nodo con esa etiqueta, o `nulo` | O(n) |
| `eliminar(clave)` | `booleano` | Desvincula el nodo con esa clave | O(n) |
| `cantElementos()` | `entero` | Cuenta nodos recorriendo la lista | O(n) |
| `imprimir()` | `void` | Imprime etiquetas de todos los nodos | O(n) |

### Pseudocódigo completo

```
// === insertar al final ===
Lista.insertar(etiqueta: Comparable, dato: T): void
  nodo ← nuevo Nodo(etiqueta, dato)
  si esVacia() entonces
    primero ← nodo
  sino
    aux ← primero
    mientras aux.getSiguiente() ≠ nulo hacer
      aux ← aux.getSiguiente()
    fin mientras
    aux.setSiguiente(nodo)
  fin si
fin método

// === insertar al frente — O(1) ===
Lista.insertarAlFrente(etiqueta: Comparable, dato: T): void
  nodo ← nuevo Nodo(etiqueta, dato)
  nodo.setSiguiente(primero)
  primero ← nodo
fin método

// === buscar ===
Lista.buscar(clave: Comparable): Nodo<T>
  aux ← primero
  mientras aux ≠ nulo hacer
    si aux.getEtiqueta() = clave entonces
      retornar aux
    fin si
    aux ← aux.getSiguiente()
  fin mientras
  retornar nulo
fin método

// === eliminar ===
Lista.eliminar(clave: Comparable): booleano
  si esVacia() entonces retornar falso fin si
  si primero.getEtiqueta() = clave entonces
    primero ← primero.getSiguiente()
    retornar verdadero
  fin si
  aux ← primero
  mientras aux.getSiguiente() ≠ nulo hacer
    si aux.getSiguiente().getEtiqueta() = clave entonces
      aux.setSiguiente(aux.getSiguiente().getSiguiente())
      retornar verdadero
    fin si
    aux ← aux.getSiguiente()
  fin mientras
  retornar falso
fin método

// === cantElementos ===
Lista.cantElementos(): entero
  contador ← 0
  aux ← primero
  mientras aux ≠ nulo hacer
    contador ← contador + 1
    aux ← aux.getSiguiente()
  fin mientras
  retornar contador
fin método
```

> **`insertarAlFrente` en Java:** la clase `Lista<T>` de farmachop no lo expone como método público. Workaround: `new Nodo<>(etiqueta, dato)` + `.setSiguiente(lista.getPrimero())` + `lista.setPrimero(nodo)`.

---

## Lista Doblemente Enlazada (pseudocódigo)

No existe implementación Java en el código base del curso. Aparece en la cuadernola como referencia conceptual. Útil para parciales que pidan deque, historial, o eliminación en ambos extremos en O(1).

### Estructura interna

```
NodoDoble<T>:
  etiqueta:  Comparable
  dato:      T
  anterior:  NodoDoble<T>   ← null si es el primero
  siguiente: NodoDoble<T>   ← null si es el último

ListaDoble<T>:
  primero: NodoDoble<T>    ← null si la lista está vacía
  ultimo:  NodoDoble<T>    ← null si la lista está vacía
```

### Tabla de métodos

| Firma | Retorno | Descripción | Orden |
|-------|---------|-------------|-------|
| `esVacia()` | `booleano` | `verdadero` si `primero = nulo` | O(1) |
| `insertar(etiqueta, dato)` | `void` | Inserta al **final**; usa puntero `ultimo` | O(1) |
| `insertarAlFrente(etiqueta, dato)` | `void` | Inserta al principio; actualiza `primero` | O(1) |
| `buscar(clave)` | `NodoDoble<T>` | Primer nodo con esa etiqueta, o `nulo` | O(n) |
| `eliminar(clave)` | `booleano` | Desvincula el nodo sin recorrer el antecesor | O(n) ¹ |

> ¹ O(n) por la búsqueda; la desvinculación en sí es O(1) gracias al puntero `anterior`.  
> Ventaja clave sobre lista simple: si ya tenés referencia al nodo, eliminarlo es O(1).

### Pseudocódigo completo

```
// === insertar al final — O(1) ===
ListaDoble.insertar(etiqueta: Comparable, dato: T): void
  nodo ← nuevo NodoDoble(etiqueta, dato)
  si esVacia() entonces
    primero ← nodo
    ultimo  ← nodo
  sino
    nodo.setAnterior(ultimo)
    ultimo.setSiguiente(nodo)
    ultimo ← nodo
  fin si
fin método

// === insertar al frente — O(1) ===
ListaDoble.insertarAlFrente(etiqueta: Comparable, dato: T): void
  nodo ← nuevo NodoDoble(etiqueta, dato)
  si esVacia() entonces
    primero ← nodo
    ultimo  ← nodo
  sino
    nodo.setSiguiente(primero)
    primero.setAnterior(nodo)
    primero ← nodo
  fin si
fin método

// === buscar ===
ListaDoble.buscar(clave: Comparable): NodoDoble<T>
  aux ← primero
  mientras aux ≠ nulo hacer
    si aux.getEtiqueta() = clave entonces
      retornar aux
    fin si
    aux ← aux.getSiguiente()
  fin mientras
  retornar nulo
fin método

// === eliminar ===
ListaDoble.eliminar(clave: Comparable): booleano
  nodo ← buscar(clave)
  si nodo = nulo entonces
    retornar falso
  fin si
  si nodo.getAnterior() ≠ nulo entonces
    nodo.getAnterior().setSiguiente(nodo.getSiguiente())
  sino
    primero ← nodo.getSiguiente()    // era el primero
  fin si
  si nodo.getSiguiente() ≠ nulo entonces
    nodo.getSiguiente().setAnterior(nodo.getAnterior())
  sino
    ultimo ← nodo.getAnterior()      // era el último
  fin si
  retornar verdadero
fin método
```

### Comparación Simple vs Doble

| Operación | Lista Simple | Lista Doble |
|-----------|-------------|-------------|
| `insertar` al final | O(n) sin puntero `ultimo` | **O(1)** con puntero `ultimo` |
| `insertarAlFrente` | **O(1)** | **O(1)** |
| `buscar` | O(n) | O(n) |
| `eliminar` (dado la clave) | O(n) | O(n) — búsqueda domina |
| `eliminar` (dado el nodo) | O(n) — necesita antecesor | **O(1)** — usa `anterior` |
| Memoria por nodo | 1 puntero (`siguiente`) | 2 punteros (`anterior` + `siguiente`) |

---

## TArbolBB\<T\> / TArbolDeProductos

`TArbolDeProductos` extiende `TArbolBB<Producto>` sin agregar métodos propios.  
El campo interno es `protected TElementoAB<T> raiz`.

| Firma | Retorno | Descripción | Orden |
|-------|---------|-------------|-------|
| `new TArbolBB<>()` | — | Constructor; crea árbol vacío | O(1) |
| `insertar(Comparable etiqueta, T dato)` | `boolean` | Inserta un elemento; `false` si la etiqueta ya existe | O(h) |
| `buscar(Comparable etiqueta)` | `T` | Retorna el dato del nodo con esa etiqueta, o `null` | O(h) |
| `eliminar(Comparable etiqueta)` | `void` | Elimina el nodo con esa etiqueta (3 casos BST) | O(h) |
| `esVacio()` | `boolean` | `true` si la raíz es `null` | O(1) |
| `vaciar()` | `boolean` | Pone raíz en `null`; `true` si había elementos | O(1) |
| `getRaiz()` | `TElementoAB<T>` | Retorna la raíz (para recorridos manuales) | O(1) |
| `inOrden()` | `List<T>` | Recorrido inorden — **usa `LinkedList` internamente** ¹ | O(n) |
| `preOrden()` | `List<T>` | Recorrido preorden — usa `LinkedList` internamente ¹ | O(n) |
| `postOrden()` | `List<T>` | Recorrido postorden — usa `LinkedList` internamente ¹ | O(n) |

> ¹ Los métodos `inOrden()`, `preOrden()`, `postOrden()` retornan `java.util.List<T>` (usa `LinkedList` internamente). **No usarlos en el parcial** si la restricción prohíbe colecciones de Java; en su lugar, implementar el recorrido manualmente con `getRaiz()` y el patrón nodo recursivo.

> h = altura del árbol. En ABB degenerado h = n; en AVL h = O(log n).

---

## TElementoAB\<T\> / IElementoAB\<T\>

Nodo del árbol binario de búsqueda. Usar siempre a través de `IElementoAB<T>` en las firmas de método.

| Firma | Retorno | Descripción | Orden |
|-------|---------|-------------|-------|
| `new TElementoAB<>(Comparable etiqueta, T dato)` | — | Constructor | O(1) |
| `getDatos()` | `T` | Retorna el dato almacenado en el nodo | O(1) |
| `getEtiqueta()` | `Comparable` | Retorna la clave del nodo | O(1) |
| `getHijoIzq()` | `TElementoAB<T>` | Retorna el hijo izquierdo, o `null` | O(1) |
| `getHijoDer()` | `TElementoAB<T>` | Retorna el hijo derecho, o `null` | O(1) |
| `setHijoIzq(TElementoAB<T> elem)` | `void` | Asigna el hijo izquierdo | O(1) |
| `setHijoDer(TElementoAB<T> elem)` | `void` | Asigna el hijo derecho | O(1) |
| `insertar(TElementoAB<T> elem)` | `boolean` | Inserta recursivamente en el subárbol | O(h) |
| `buscar(Comparable etiqueta)` | `TElementoAB<T>` | Busca el nodo con esa etiqueta en el subárbol | O(h) |
| `eliminar(Comparable etiqueta)` | `TElementoAB<T>` | Elimina y retorna el nodo sucesor | O(h) |
| `obtenerTamaño()` | `int` | Cuenta los nodos del subárbol recursivamente | O(n) |
| `inOrden(LinkedList<T> lista)` | `void` | Agrega datos al recorrido inorden ¹ | O(n) |
| `preOrden(LinkedList<T> lista)` | `void` | Agrega datos al recorrido preorden ¹ | O(n) |
| `postOrden(LinkedList<T> lista)` | `void` | Agrega datos al recorrido postorden ¹ | O(n) |
| `imprimir()` | `String` | Retorna `etiqueta.toString()` | O(1) |
| `inOrden()` | `String` | Retorna string inorden del subárbol con separadores | O(n) |

> ¹ Usan `LinkedList` de Java. Para el parcial, implementar recorridos manualmente con el patrón:
> ```java
> private void recorrer(IElementoAB<T> nodo, Lista<T> lista) {
>     if (nodo == null) return;
>     recorrer(nodo.getHijoIzq(), lista);
>     lista.insertar(nodo.getEtiqueta(), nodo.getDatos());
>     recorrer(nodo.getHijoDer(), lista);
> }
> ```

### Métodos adicionales documentados en pseudocódigo (no en código base)

Estos métodos se implementan en los ejercicios del parcial (no están en `TArbolBB.java`):

| Firma (pseudocódigo) | Descripción | Orden |
|----------------------|-------------|-------|
| `TArbolBB.obtenerTamaño()` | Delega a `raiz.obtenerTamaño()` | O(n) |
| `TArbolBB.obtenerAltura()` | Delega a `raiz.obtenerAltura()` | O(n) |
| `TArbolBB.obtenerNivel(Comparable criterio)` | Delega a `raiz.obtenerNivel(criterio, 0)` | O(h) |
| `TElementoAB.obtenerAltura()` | `1 + max(altIzq, altDer)`; nodo nulo = -1 | O(n) |
| `TElementoAB.obtenerNivel(Comparable c, int nivel)` | Busca `c` y retorna el nivel, o -1 si no existe | O(h) |

---

## Cola\<T\> (festival-otaku)

Implementación FIFO. Hereda métodos de `TDALista<T>`.  
**Versión del curso (pseudocódigo):** arreglo circular o lista enlazada con `frente` y `posterior`.

### Métodos propios de Cola (FIFO)

| Firma | Retorno | Descripción | Orden |
|-------|---------|-------------|-------|
| `poneEnCola(T dato)` | `boolean` | Inserta al final de la cola | O(1) |
| `quitaDeCola()` | `T` | Remueve y retorna el elemento del frente | O(1) |
| `frente()` | `T` | Consulta el frente sin remover | O(1) |
| `esVacio()` | `boolean` | `true` si la cola está vacía | O(1) |
| `vaciar()` | `void` | Elimina todos los elementos | O(1) |

### Nombres equivalentes en pseudocódigo del curso

| Java | Pseudocódigo |
|------|-------------|
| `poneEnCola(dato)` | `encolar(dato)` |
| `quitaDeCola()` | `desencolar()` |
| `frente()` | `frente()` |
| `esVacio()` | `esVacia()` |

### Métodos heredados de TDALista\<T\>

| Firma | Retorno | Descripción |
|-------|---------|-------------|
| `agregar(T elem)` | `void` | Agrega al final |
| `agregar(int index, T elem)` | `void` | Agrega en posición |
| `obtener(int index)` | `T` | Acceso por índice |
| `remover(int index)` | `T` | Remueve por índice |
| `remover(T elem)` | `boolean` | Remueve primera ocurrencia |
| `contiene(T elem)` | `boolean` | Verifica pertenencia |
| `indiceDe(T elem)` | `int` | Índice de primera ocurrencia |
| `buscar(Predicate<T>)` | `T` | Busca con criterio lambda |
| `ordenar(Comparator<T>)` | `TDALista<T>` | Retorna lista ordenada |
| `tamaño()` | `int` | Cantidad de elementos |

---

## Pila\<T\> (festival-otaku)

Implementación LIFO. Hereda métodos de `TDALista<T>`.  
**Versión del curso (pseudocódigo):** lista enlazada con `tope` = primer nodo.

### Métodos propios de Pila (LIFO)

| Firma | Retorno | Descripción | Orden |
|-------|---------|-------------|-------|
| `mete(T dato)` | `void` | Inserta en el tope de la pila | O(1) |
| `saca()` | `T` | Remueve y retorna el elemento del tope | O(1) |
| `tope()` | `T` | Consulta el tope sin remover | O(1) |
| `esVacio()` | `boolean` | `true` si la pila está vacía | O(1) |
| `vaciar()` | `void` | Elimina todos los elementos | O(1) |

### Nombres equivalentes en pseudocódigo del curso

| Java | Pseudocódigo |
|------|-------------|
| `mete(dato)` | `apilar(dato)` |
| `saca()` | `desapilar()` |
| `tope()` | `tope()` |
| `esVacio()` | `esVacia()` |

> Los métodos heredados de `TDALista<T>` son los mismos que para `Cola<T>` (ver arriba).

---

## Lista basada en arreglo con validación de duplicados

**Clase Java:** `ListaArray<T>` (festival-otaku) — implementa `TDALista<T>`.  
**Estructura interna:** arreglo `T[]` de capacidad fija + entero `tamanio` con la cantidad actual.  
**Invariante de unicidad:** `insertar` verifica con `contiene` antes de agregar; si el elemento ya existe, no lo duplica.

### Tabla de métodos

| Firma | Retorno | Descripción | Orden |
|-------|---------|-------------|-------|
| `agregar(T elem)` | `void` | Inserta al final **solo si no existe**; ignora silenciosamente si ya está | O(n) |
| `agregar(int index, T elem)` | `void` | Inserta en posición desplazando elementos a la derecha | O(n) |
| `obtener(int index)` | `T` | Acceso directo por índice en tiempo constante | **O(1)** |
| `remover(int index)` | `T` | Elimina por posición, desplaza elementos a la izquierda | O(n) |
| `remover(T elem)` | `boolean` | Elimina primera ocurrencia del elemento; `true` si existía | O(n) |
| `contiene(T elem)` | `boolean` | Búsqueda lineal; `true` si el elemento está presente | O(n) |
| `indiceDe(T elem)` | `int` | Índice de la primera ocurrencia, o `-1` si no existe | O(n) |
| `buscar(Predicate<T>)` | `T` | Retorna el primer elemento que cumple el criterio | O(n) |
| `ordenar(Comparator<T>)` | `TDALista<T>` | Retorna una nueva lista ordenada | O(n²) |
| `tamaño()` | `int` | Cantidad de elementos actuales | **O(1)** |
| `esVacio()` | `boolean` | `true` si `tamanio = 0` | **O(1)** |
| `vaciar()` | `void` | Pone `tamanio = 0` | O(1) |

### Pseudocódigo completo

```
// Estructura interna
ListaArray<T>:
  datos:   T[]     ← arreglo de capacidad MAX
  tamanio: entero  ← cantidad de elementos actuales (0 si vacía)

// === obtener por índice — O(1) ===
ListaArray.obtener(indice: entero): T
  si indice < 0 o indice ≥ tamanio entonces
    lanzar error "Índice fuera de rango"
  fin si
  retornar datos[indice]
fin método

// === insertar al final con validación de duplicados — O(n) ===
ListaArray.agregar(dato: T): void
  si contiene(dato) entonces
    retornar                     // no duplicar
  fin si
  si tamanio = MAX entonces
    lanzar error "Lista llena"
  fin si
  datos[tamanio] ← dato
  tamanio ← tamanio + 1
fin método

// === insertar en posición — O(n) ===
ListaArray.agregar(indice: entero, dato: T): void
  si indice < 0 o indice > tamanio o tamanio = MAX entonces
    lanzar error "Inserción inválida"
  fin si
  i ← tamanio - 1
  mientras i ≥ indice hacer
    datos[i + 1] ← datos[i]
    i ← i - 1
  fin mientras
  datos[indice] ← dato
  tamanio ← tamanio + 1
fin método

// === contiene (búsqueda lineal) — O(n) ===
ListaArray.contiene(dato: T): booleano
  i ← 0
  mientras i < tamanio hacer
    si datos[i] = dato entonces
      retornar verdadero
    fin si
    i ← i + 1
  fin mientras
  retornar falso
fin método

// === indiceDe — O(n) ===
ListaArray.indiceDe(dato: T): entero
  i ← 0
  mientras i < tamanio hacer
    si datos[i] = dato entonces
      retornar i
    fin si
    i ← i + 1
  fin mientras
  retornar -1
fin método

// === remover por índice — O(n) ===
ListaArray.remover(indice: entero): T
  si indice < 0 o indice ≥ tamanio entonces
    lanzar error "Índice fuera de rango"
  fin si
  eliminado ← datos[indice]
  i ← indice
  mientras i < tamanio - 1 hacer
    datos[i] ← datos[i + 1]
    i ← i + 1
  fin mientras
  tamanio ← tamanio - 1
  retornar eliminado
fin método

// === remover por elemento — O(n) ===
ListaArray.remover(dato: T): booleano
  indice ← indiceDe(dato)
  si indice = -1 entonces
    retornar falso
  fin si
  remover(indice)
  retornar verdadero
fin método
```

### Comparación con la Lista enlazada (farmachop)

| Operación | `ListaArray<T>` (arreglo) | `Lista<T>` (enlazada) |
|-----------|--------------------------|----------------------|
| `obtener(i)` | **O(1)** — acceso directo | O(n) — recorre hasta i |
| `agregar` al final | O(n) — por `contiene` | O(n) — por recorrer hasta el último |
| `agregar` al frente | O(n) — desplaza todo | O(1) — actualiza `primero` |
| `contiene` / `buscar` | O(n) | O(n) |
| `remover` | O(n) — desplaza elementos | O(n) — busca antecesor |
| `tamaño()` | **O(1)** — campo directo | O(n) — recorre toda la lista |
| Memoria | Bloque contiguo fijo (MAX) | Dinámica (crece nodo a nodo) |
| Duplicados | Bloqueados por `contiene` | Permitidos (sin validación) |

> Preferir `ListaArray<T>` cuando: el tamaño es acotado, se necesita unicidad de elementos, o se accede frecuentemente por posición. Preferir `Lista<T>` enlazada cuando: el tamaño es variable y no hay un máximo claro, o se inserta mucho al frente.

---

## ManejadorArchivosGenerico

Clase utilitaria estática. Misma API en todos los parciales.

| Firma | Retorno | Descripción |
|-------|---------|-------------|
| `leerArchivo(String ruta)` | `String[]` | Lee el archivo y retorna una línea por elemento del arreglo |
| `escribirArchivo(String ruta, String[] lineas)` | `void` | Escribe el arreglo de líneas en el archivo (modo **append**) |

> **Patrón de uso estándar:**
> ```java
> String[] lineas = ManejadorArchivosGenerico.leerArchivo("datos.txt");
> for (String linea : lineas) {
>     if (linea.isBlank()) continue;
>     String[] partes = linea.split(";");
>     // parsear partes[0], partes[1], etc.
> }
> ```

---

## Patrones de uso frecuentes

### Recorrer una Lista con Nodo

```java
Nodo<T> aux = lista.getPrimero();
while (aux != null) {
    T dato = aux.getDato();
    // ... procesar dato ...
    aux = aux.getSiguiente();
}
```

### Selection sort sobre Lista (sin sort() nativo)

```java
Nodo<T> i = lista.getPrimero();
while (i != null) {
    Nodo<T> minNodo = i;
    Nodo<T> j = i.getSiguiente();
    while (j != null) {
        if (j.getDato().getNombre().compareTo(minNodo.getDato().getNombre()) < 0)
            minNodo = j;
        j = j.getSiguiente();
    }
    T tmp = i.getDato(); i.setDato(minNodo.getDato()); minNodo.setDato(tmp);
    i = i.getSiguiente();
}
```

> Reemplazar `.getNombre()` con el campo de comparación correspondiente al problema.

### Recorrido inorden manual sobre árbol (patrón árbol + nodo)

```java
// Nivel árbol
public Lista<T> filtrar(...) {
    Lista<T> resultado = new Lista<>();
    if (!esVacio()) filtrarNodo(getRaiz(), resultado);
    return resultado;
}

// Nivel nodo (método privado, recursivo)
private void filtrarNodo(IElementoAB<T> nodo, Lista<T> lista) {
    if (nodo == null) return;
    filtrarNodo(nodo.getHijoIzq(), lista);
    if (condicion(nodo.getDatos()))
        lista.insertar(nodo.getEtiqueta(), nodo.getDatos());
    filtrarNodo(nodo.getHijoDer(), lista);
}
```

### Acceso al N-ésimo elemento de una Lista (sin get(i))

```java
// Equivalente a lista.get(3) — cuarto elemento
Nodo<T> nodo = lista.getPrimero()
    .getSiguiente()
    .getSiguiente()
    .getSiguiente();
T cuarto = nodo.getDato();
```

### Usar Cola (FIFO) en Java

```java
Cola<T> cola = new Cola<>();
cola.poneEnCola(dato);          // encolar
T primero = cola.frente();      // ver frente
T removido = cola.quitaDeCola(); // desencolar
boolean vacia = cola.esVacio();
```

### Usar Pila (LIFO) en Java

```java
Pila<T> pila = new Pila<>();
pila.mete(dato);           // apilar
T cima = pila.tope();      // ver tope
T removido = pila.saca();  // desapilar
boolean vacia = pila.esVacio();
```

---

*Referencia compilada a partir del código base AED UCU 2024-S1, festival-otaku, farmachop y pseudocódigos del curso — 2026.*
