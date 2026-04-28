---
title: "Solución: Separar Nodos Hoja e Internos en BST"
type: analysis
tags: [bst, hojas, internos, pseudocodigo, java, parcial, solucion]
created: 2026-04-14
updated: 2026-04-14
sources: [primer-parcial-p2-2024-s1-ex2, primer-parcial-p3-2024-s1-ex2]
---

# Solución: Separar Nodos Hoja e Internos en BST

**Question / Prompt:** Ejercicio 2 del Parcial 2024 S1 Examen 2 (pseudocódigo) y Parte 3 (Java). Dado un ABB, separar los nodos en dos listas: nodos hoja (sin hijos) y nodos internos (con al menos un hijo), manteniendo el orden relativo.

---

## Definición

- **Nodo hoja:** nodo sin hijos izquierdo ni derecho.
- **Nodo interno:** nodo con al menos un hijo.
- **Orden relativo:** los nodos en cada lista deben aparecer en el mismo orden relativo que en un recorrido del árbol. Se usa **inorden** para mantener el orden del BST (orden ascendente de claves).

---

## Lenguaje Natural

**TArbolBB.separarNodos():**
Dado un árbol binario de búsqueda, retornar dos listas: una con los datos de los nodos hoja (en orden inorden) y otra con los datos de los nodos internos (en orden inorden). Si el árbol está vacío, retornar dos listas vacías.

**TElementoAB.separarNodos(listaHojas, listaInternos):**
Recorre el subárbol con raíz en `this` en inorden. Para cada nodo: si es hoja, inserta sus datos en `listaHojas`; si es interno, inserta en `listaInternos`. Luego procesa recursivamente los hijos.

---

## Pre y Post Condiciones

### TArbolBB.separarNodos()
- **Precondición:** Ninguna (puede estar vacío).
- **Postcondición:** Retorna par `(listaHojas, listaInternos)`. Cada lista contiene los datos en inorden. El árbol no es modificado.

### TElementoAB.separarNodos(listaHojas: Lista, listaInternos: Lista)
- **Precondición:** `this` no es nulo. Las listas son referencias válidas.
- **Postcondición:** Los datos de los nodos del subárbol son insertados en las listas correspondientes en inorden.

---

## Pseudocódigo

```
// === MÉTODO DE ÁRBOL ===
TArbolBB.separarNodos(): (Lista hojas, Lista internos)
  hojas ← nueva Lista vacía
  internos ← nueva Lista vacía
  si raiz ≠ nulo entonces
    raiz.separarNodos(hojas, internos)
  fin si
  retornar (hojas, internos)
fin método

// === MÉTODO DE NODO ===
TElementoAB.separarNodos(hojas: Lista, internos: Lista): void
  // Inorden: primero hijo izquierdo
  si hijoIzq ≠ nulo entonces
    hijoIzq.separarNodos(hojas, internos)
  fin si
  // Clasificar el nodo actual
  si hijoIzq = nulo Y hijoDer = nulo entonces
    hojas.insertar(this.getDatos())     // es hoja
  sino
    internos.insertar(this.getDatos()) // es interno
  fin si
  // Luego hijo derecho
  si hijoDer ≠ nulo entonces
    hijoDer.separarNodos(hojas, internos)
  fin si
fin método
```

**Demostración de invocación:**
```
arbol ← nuevo TArbolDeProductos
// ... insertar productos ...
(hojas, internos) ← arbol.separarNodos()
// hojas.imprimir() e internos.imprimir()
```

---

## Análisis del Orden de Tiempo de Ejecución

- `separarNodos` en el nodo: visita cada nodo **exactamente una vez** → O(n)
- `separarNodos` en el árbol: O(n) + O(1) = **O(n)**
- Insertar al final de la lista (si es lista enlazada sin puntero al último): O(n) por inserción → total O(n²).
  - **Mejora:** si la lista tiene `insertarAlFinal` en O(1) (mantiene puntero al último), el total es O(n).

---

## Implementación Java (para Parte 3)

```java
// En TArbolDeProductos:
public Lista<Producto> separarHojas() {
    Lista<Producto> hojas    = new Lista<>();
    Lista<Producto> internos = new Lista<>();
    if (!esVacio()) {
        ((TElementoAB<Producto>) getRaiz()).separarNodos(hojas, internos);
    }
    return hojas;
}
```

```java
// En TElementoAB:
public void separarNodos(Lista<T> hojas, Lista<T> internos) {
    if (hijoIzq != null) ((TElementoAB<T>) hijoIzq).separarNodos(hojas, internos);
    if (hijoIzq == null && hijoDer == null) {
        hojas.insertar(getEtiqueta(), getDatos());
    } else {
        internos.insertar(getEtiqueta(), getDatos());
    }
    if (hijoDer != null) ((TElementoAB<T>) hijoDer).separarNodos(hojas, internos);
}
```

### Restricción de Altura (Examen 2 Parte 3)

El enunciado exige que el árbol resultante no tenga altura comparable al tamaño. Para evitar árbol degenerado, **NO insertar los productos en orden secuencial**. Estrategia: mezclar aleatoriamente antes de insertar (shuffle), o leer del archivo y usar una inserción intercalada.

```java
// Estrategia: mezcla manual (Fisher-Yates) para evitar árbol degenerado
String[] lineas = ManejadorArchivosGenerico.leerArchivo("productos.txt");
Random rnd = new Random();
for (int k = lineas.length - 1; k > 0; k--) {
    int idx = rnd.nextInt(k + 1);
    String tmp = lineas[k]; lineas[k] = lineas[idx]; lineas[idx] = tmp;
}
for (String linea : lineas) {
    // parsear e insertar
}
```

### Programa Principal (salida.txt con productos hojas en orden por nombre)

```java
Lista<Producto> hojas = arbol.separarHojas();
// Ordenar por nombre — selection sort con métodos del TDA (Lista no tiene sort)
Nodo<Producto> i = hojas.getPrimero();
while (i != null) {
    Nodo<Producto> minNodo = i;
    Nodo<Producto> j = i.getSiguiente();
    while (j != null) {
        if (j.getDato().getNombre().compareTo(minNodo.getDato().getNombre()) < 0) minNodo = j;
        j = j.getSiguiente();
    }
    Producto tmp = i.getDato(); i.setDato(minNodo.getDato()); minNodo.setDato(tmp);
    i = i.getSiguiente();
}
// Escribir
StringBuilder sb = new StringBuilder();
Nodo<Producto> aux = hojas.getPrimero();
while (aux != null) {
    sb.append(aux.getDato().getId()).append(", ").append(aux.getDato().getNombre()).append("\n");
    aux = aux.getSiguiente();
}
ManejadorArchivosGenerico.escribirArchivo("salida.txt", sb.toString());
```

### JUnit Test

```java
@Test
public void testSepararHojas() {
    TArbolDeProductos arbol = new TArbolDeProductos();
    arbol.insertar(5, new Producto(5, "C"));
    arbol.insertar(3, new Producto(3, "A"));
    arbol.insertar(7, new Producto(7, "D"));
    arbol.insertar(1, new Producto(1, "B"));
    // Árbol:   5
    //         / \
    //        3   7
    //       /
    //      1
    // Hojas: 1, 7 (inorden: 1, 7)
    // Internos: 3, 5 (inorden: 3, 5)
    Lista<Producto> hojas = arbol.separarHojas();
    assertEquals(2, hojas.cantElementos());
    assertEquals(1, hojas.getPrimero().getDato().getId());
    assertEquals(7, hojas.getPrimero().getSiguiente().getDato().getId());
}
```

---

## Confianza

Alta — el algoritmo es una variante directa del recorrido inorden estándar.

## Gaps

- Se usa `Lista<T>` del curso. La inserción al final es O(n) sin puntero al último nodo → total O(n²). Si `Lista` mantuviera un puntero `ultimo`, sería O(n) total.
- `Lista` no tiene `sort()`. El ordenamiento del archivo de salida se implementa como selection sort O(n²) usando `getPrimero()`, `getSiguiente()`, `getDato()` y `setDato()`.
