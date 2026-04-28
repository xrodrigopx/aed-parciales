---
title: "Referencia de Métodos Java por Estructura"
type: reference
tags: [java, tda, metodos, lista, cola, pila, conjunto, bst, arbol]
created: 2026-04-28
updated: 2026-04-28
---

# Referencia de Métodos Java por Estructura

Catálogo completo de métodos disponibles en cada TDA del curso AED UCU.  
Fuentes: código base 2024-S1, código base festival-otaku, código base farmachop.

> **Restricción del parcial:** solo usar métodos de esta lista. Prohibido `ArrayList`, `LinkedList`, `java.util.Stack`, ni ninguna clase de `java.util.Collections`.

---

## Índice

- [Lista\<T\> (farmachop)](#listat-farmachop)
- [Nodo\<T\> (farmachop)](#nodot-farmachop)
- [Lista Simplemente Enlazada](#lista-simplemente-enlazada)
- [Lista Doblemente Enlazada](#lista-doblemente-enlazada)
- [TArbolBB\<T\> / TArbolDeProductos](#tarbollbbt--tarboldeproductos)
- [TElementoAB\<T\> / IElementoAB\<T\>](#telementoabt--ielementoabt)
- [Cola\<T\> (festival-otaku)](#colat-festival-otaku)
- [Pila\<T\> (festival-otaku)](#pilat-festival-otaku)
- [Lista basada en arreglo con validación de duplicados](#lista-basada-en-arreglo-con-validación-de-duplicados)
- [ManejadorArchivosGenerico](#manejadorarchivosgenerico)
- [Patrones de uso frecuentes](#patrones-de-uso-frecuentes)

---

## Lista\<T\> (farmachop)

Lista simplemente enlazada con puntero `primero`. Sin puntero al último nodo.

| Firma | Retorno | Descripción | Orden |
|-------|---------|-------------|-------|
| `insertar(Nodo<T> nodo)` | `void` | Inserta el nodo al **final** de la lista | O(n) |
| `insertar(Comparable etiqueta, T dato)` | `void` | Crea un nodo y lo inserta al final | O(n) |
| `buscar(Comparable clave)` | `Nodo<T>` | Retorna el nodo con esa etiqueta, o `null` | O(n) |
| `eliminar(Comparable clave)` | `boolean` | Elimina el primer nodo con esa etiqueta; `true` si tuvo éxito | O(n) |
| `cantElementos()` | `int` | Cantidad de nodos en la lista | O(n) |
| `esVacia()` | `boolean` | `true` si la lista no tiene elementos | O(1) |
| `getPrimero()` | `Nodo<T>` | Retorna el primer nodo (o `null` si vacía) | O(1) |
| `setPrimero(Nodo<T> nodo)` | `void` | Fuerza el primer nodo (usar con cuidado) | O(1) |
| `imprimir()` | `String` | Imprime etiquetas en consola, retorna `""` | O(n) |
| `imprimir(String separador)` | `String` | Retorna etiquetas separadas por el separador | O(n) |

> **No existe** `get(int i)`, `add()`, `size()`, `sort()`, ni `insertarAlFrente()`.  
> Para acceder al N-ésimo elemento: encadenar `.getSiguiente()` N veces.  
> Para insertar al frente: `Nodo<T> n = new Nodo<>(etiqueta, dato); n.setSiguiente(lista.getPrimero()); lista.setPrimero(n);`

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

## Lista Simplemente Enlazada

> No tiene implementación Java propia en el código base — **`Lista<T>` de farmachop es la implementación del parcial**. Esta tabla documenta la API conceptual del TDA; los pseudocódigos están en `pseudocodigos/lista-enlazada.md`.

| Método | Retorno | Descripción | Orden |
|--------|---------|-------------|-------|
| `esVacia()` | `boolean` | `true` si no hay elementos | O(1) |
| `insertar(etiqueta, dato)` | `void` | Inserta al **final**; recorre toda la lista | O(n) |
| `insertarAlFrente(etiqueta, dato)` | `void` | Inserta al principio sin recorrer | O(1) |
| `buscar(clave)` | `Nodo<T>` | Primer nodo con esa etiqueta, o `null` | O(n) |
| `eliminar(clave)` | `boolean` | Desvincula el nodo con esa clave | O(n) |
| `cantElementos()` | `int` | Cuenta nodos recorriendo la lista | O(n) |

> `insertarAlFrente` **no existe** en farmachop `Lista<T>`. Workaround Java:
> ```java
> Nodo<T> n = new Nodo<>(etiqueta, dato);
> n.setSiguiente(lista.getPrimero());
> lista.setPrimero(n);
> ```

---

## Lista Doblemente Enlazada

> No tiene implementación Java en el código base del curso. Referencia conceptual; pseudocódigos en `cuadernola.md`. Campo extra por nodo: `anterior`.

| Método | Retorno | Descripción | Orden |
|--------|---------|-------------|-------|
| `esVacia()` | `boolean` | `true` si no hay elementos | O(1) |
| `insertar(etiqueta, dato)` | `void` | Inserta al **final** via puntero `ultimo` | **O(1)** |
| `insertarAlFrente(etiqueta, dato)` | `void` | Inserta al principio; actualiza `primero` | O(1) |
| `buscar(clave)` | `NodoDoble<T>` | Primer nodo con esa etiqueta, o `null` | O(n) |
| `eliminar(clave)` | `boolean` | Desvincula usando `anterior`; no necesita recorrer el antecesor | O(n) ¹ |

> ¹ O(n) por la búsqueda; la desvinculación en sí es O(1) gracias a `anterior`.

### Comparación Simple vs Doble

| Operación | Lista Simple (`Lista<T>`) | Lista Doble |
|-----------|--------------------------|-------------|
| `insertar` al final | O(n) — recorre hasta el último | **O(1)** — puntero `ultimo` |
| `insertarAlFrente` | O(1) | O(1) |
| `buscar` | O(n) | O(n) |
| `eliminar` (por clave) | O(n) | O(n) — búsqueda domina |
| `eliminar` (dado el nodo) | O(n) — necesita antecesor | **O(1)** — usa `anterior` |
| Memoria por nodo | 1 puntero | 2 punteros |

---

## TArbolBB\<T\> / TArbolDeProductos

`TArbolDeProductos` extiende `TArbolBB<Producto>` sin agregar métodos propios.  
Campo interno: `protected TElementoAB<T> raiz`.

| Firma | Retorno | Descripción | Orden |
|-------|---------|-------------|-------|
| `new TArbolBB<>()` | — | Constructor; crea árbol vacío | O(1) |
| `insertar(Comparable etiqueta, T dato)` | `boolean` | Inserta; retorna `false` si la etiqueta ya existe | O(h) |
| `buscar(Comparable etiqueta)` | `T` | Retorna el dato del nodo, o `null` | O(h) |
| `eliminar(Comparable etiqueta)` | `void` | Elimina el nodo (3 casos BST) | O(h) |
| `esVacio()` | `boolean` | `true` si la raíz es `null` | O(1) |
| `vaciar()` | `boolean` | Pone raíz en `null`; `true` si había elementos | O(1) |
| `getRaiz()` | `TElementoAB<T>` | Retorna la raíz (para recorridos manuales) | O(1) |
| `inOrden()` | `List<T>` | Recorrido inorden — usa `LinkedList` internamente ¹ | O(n) |
| `preOrden()` | `List<T>` | Recorrido preorden — usa `LinkedList` internamente ¹ | O(n) |
| `postOrden()` | `List<T>` | Recorrido postorden — usa `LinkedList` internamente ¹ | O(n) |

> ¹ **No usar en el parcial**: retornan `java.util.List<T>` con `LinkedList` internamente. Implementar el recorrido manualmente con `getRaiz()` (ver Patrones).

> h = altura. En ABB degenerado h = n; en AVL h = O(log n).

### Métodos adicionales (no están en el código base — se implementan en el parcial)

| Firma | Descripción | Orden |
|-------|-------------|-------|
| `obtenerTamaño()` | Delega a `raiz.obtenerTamaño()` | O(n) |
| `obtenerAltura()` | Delega a `raiz.obtenerAltura()` | O(n) |
| `obtenerNivel(Comparable criterio)` | Delega a `raiz.obtenerNivel(criterio, 0)` | O(h) |

---

## TElementoAB\<T\> / IElementoAB\<T\>

Nodo del BST. Usar `IElementoAB<T>` en las firmas de los métodos propios.

| Firma | Retorno | Descripción | Orden |
|-------|---------|-------------|-------|
| `new TElementoAB<>(Comparable etiqueta, T dato)` | — | Constructor | O(1) |
| `getDatos()` | `T` | Retorna el dato almacenado | O(1) |
| `getEtiqueta()` | `Comparable` | Retorna la clave del nodo | O(1) |
| `getHijoIzq()` | `TElementoAB<T>` | Hijo izquierdo, o `null` | O(1) |
| `getHijoDer()` | `TElementoAB<T>` | Hijo derecho, o `null` | O(1) |
| `setHijoIzq(TElementoAB<T> elem)` | `void` | Asigna el hijo izquierdo | O(1) |
| `setHijoDer(TElementoAB<T> elem)` | `void` | Asigna el hijo derecho | O(1) |
| `insertar(TElementoAB<T> elem)` | `boolean` | Inserta recursivamente; `false` si ya existe | O(h) |
| `buscar(Comparable etiqueta)` | `TElementoAB<T>` | Busca el nodo en el subárbol | O(h) |
| `eliminar(Comparable etiqueta)` | `TElementoAB<T>` | Elimina y retorna el sucesor estructural | O(h) |
| `obtenerTamaño()` | `int` | Cuenta nodos del subárbol | O(n) |
| `inOrden(LinkedList<T> lista)` | `void` | Agrega datos en inorden a un `LinkedList` ¹ | O(n) |
| `preOrden(LinkedList<T> lista)` | `void` | Agrega datos en preorden a un `LinkedList` ¹ | O(n) |
| `postOrden(LinkedList<T> lista)` | `void` | Agrega datos en postorden a un `LinkedList` ¹ | O(n) |
| `imprimir()` | `String` | Retorna `etiqueta.toString()` | O(1) |
| `inOrden()` | `String` | Retorna string inorden del subárbol con separadores | O(n) |

> ¹ Reciben `LinkedList` de Java — no usar en el parcial. Ver patrón manual en sección Patrones.

---

## Cola\<T\> (festival-otaku)

FIFO. Hereda de `TDALista<T>`.

### Métodos propios

| Firma | Retorno | Descripción | Orden |
|-------|---------|-------------|-------|
| `poneEnCola(T dato)` | `boolean` | Inserta al final | O(1) |
| `quitaDeCola()` | `T` | Remueve y retorna el frente | O(1) |
| `frente()` | `T` | Consulta el frente sin remover | O(1) |
| `esVacio()` | `boolean` | `true` si la cola está vacía | O(1) |
| `vaciar()` | `void` | Elimina todos los elementos | O(1) |

### Equivalencias Java ↔ pseudocódigo

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

LIFO. Hereda de `TDALista<T>`.

### Métodos propios

| Firma | Retorno | Descripción | Orden |
|-------|---------|-------------|-------|
| `mete(T dato)` | `void` | Inserta en el tope | O(1) |
| `saca()` | `T` | Remueve y retorna el tope | O(1) |
| `tope()` | `T` | Consulta el tope sin remover | O(1) |
| `esVacio()` | `boolean` | `true` si la pila está vacía | O(1) |
| `vaciar()` | `void` | Elimina todos los elementos | O(1) |

### Equivalencias Java ↔ pseudocódigo

| Java | Pseudocódigo |
|------|-------------|
| `mete(dato)` | `apilar(dato)` |
| `saca()` | `desapilar()` |
| `tope()` | `tope()` |
| `esVacio()` | `esVacia()` |

> Los métodos heredados de `TDALista<T>` son los mismos que para `Cola<T>`.

---

## Lista basada en arreglo con validación de duplicados

**Clase Java:** `ListaArray<T>` (festival-otaku) — implementa `TDALista<T>`.  
**Estructura interna:** `T[] datos` de capacidad fija + `int tamanio`.  
**Invariante:** `agregar(elem)` llama a `contiene` antes de insertar; si ya existe, no hace nada.

| Firma | Retorno | Descripción | Orden |
|-------|---------|-------------|-------|
| `agregar(T elem)` | `void` | Inserta al final **solo si no existe** | O(n) |
| `agregar(int index, T elem)` | `void` | Inserta en posición, desplaza a la derecha | O(n) |
| `obtener(int index)` | `T` | Acceso directo por índice | **O(1)** |
| `remover(int index)` | `T` | Elimina por posición, desplaza a la izquierda | O(n) |
| `remover(T elem)` | `boolean` | Elimina primera ocurrencia; `true` si existía | O(n) |
| `contiene(T elem)` | `boolean` | Búsqueda lineal | O(n) |
| `indiceDe(T elem)` | `int` | Índice de primera ocurrencia, o `-1` | O(n) |
| `buscar(Predicate<T>)` | `T` | Primer elemento que cumple el criterio | O(n) |
| `ordenar(Comparator<T>)` | `TDALista<T>` | Retorna nueva lista ordenada | O(n²) |
| `tamaño()` | `int` | Cantidad de elementos | **O(1)** |
| `esVacio()` | `boolean` | `true` si `tamanio == 0` | **O(1)** |
| `vaciar()` | `void` | Pone `tamanio = 0` | O(1) |

### Comparación con Lista enlazada (farmachop)

| Operación | `ListaArray<T>` (arreglo) | `Lista<T>` (enlazada) |
|-----------|--------------------------|----------------------|
| `obtener(i)` | **O(1)** — acceso directo | O(n) — recorre hasta i |
| `agregar` al final | O(n) — por `contiene` | O(n) — recorre hasta el último |
| `agregar` al frente | O(n) — desplaza todo | O(1) — actualiza `primero` |
| `contiene` / `buscar` | O(n) | O(n) |
| `remover` | O(n) — desplaza | O(n) — busca antecesor |
| `tamaño()` | **O(1)** — campo directo | O(n) — recorre toda la lista |
| Memoria | Bloque contiguo fijo | Dinámica nodo a nodo |
| Duplicados | Bloqueados | Permitidos |

---

## ManejadorArchivosGenerico

Clase utilitaria estática. Misma API en todos los parciales.

| Firma | Retorno | Descripción |
|-------|---------|-------------|
| `leerArchivo(String ruta)` | `String[]` | Lee el archivo; una línea por elemento del arreglo |
| `escribirArchivo(String ruta, String[] lineas)` | `void` | Escribe el arreglo de líneas (modo append) |

```java
String[] lineas = ManejadorArchivosGenerico.leerArchivo("datos.txt");
for (String linea : lineas) {
    if (linea.isBlank()) continue;
    String[] partes = linea.split(";");
    // partes[0], partes[1], etc.
}
```

---

## Patrones de uso frecuentes

### Recorrer una Lista\<T\>

```java
Nodo<T> aux = lista.getPrimero();
while (aux != null) {
    T dato = aux.getDato();
    // procesar dato
    aux = aux.getSiguiente();
}
```

### Selection sort sobre Lista\<T\> (sin sort() nativo)

```java
Nodo<T> i = lista.getPrimero();
while (i != null) {
    Nodo<T> minNodo = i;
    Nodo<T> j = i.getSiguiente();
    while (j != null) {
        if (j.getDato().getCampo().compareTo(minNodo.getDato().getCampo()) < 0)
            minNodo = j;
        j = j.getSiguiente();
    }
    T tmp = i.getDato(); i.setDato(minNodo.getDato()); minNodo.setDato(tmp);
    i = i.getSiguiente();
}
```

### Recorrido inorden manual sobre árbol

```java
// Nivel árbol
public Lista<T> filtrar() {
    Lista<T> resultado = new Lista<>();
    if (!esVacio()) filtrarNodo(getRaiz(), resultado);
    return resultado;
}

// Nivel nodo
private void filtrarNodo(IElementoAB<T> nodo, Lista<T> lista) {
    if (nodo == null) return;
    filtrarNodo(nodo.getHijoIzq(), lista);
    if (condicion(nodo.getDatos()))
        lista.insertar(nodo.getEtiqueta(), nodo.getDatos());
    filtrarNodo(nodo.getHijoDer(), lista);
}
```

### Acceso al N-ésimo elemento (sin get(i))

```java
// Equivalente a get(3)
T cuarto = lista.getPrimero()
    .getSiguiente()
    .getSiguiente()
    .getSiguiente()
    .getDato();
```

### Cola y Pila

```java
Cola<T> cola = new Cola<>();
cola.poneEnCola(dato);
T frente = cola.frente();
T removido = cola.quitaDeCola();

Pila<T> pila = new Pila<>();
pila.mete(dato);
T tope = pila.tope();
T removido = pila.saca();
```

---

*Referencia compilada a partir del código base AED UCU 2024-S1, festival-otaku, farmachop — 2026.*
