---
title: "Solución: Longitud de Trayectoria Interna Media (LTIM)"
type: analysis
tags: [bst, lti, ltim, pseudocodigo, java, parcial, solucion]
created: 2026-04-14
updated: 2026-04-14
sources: [primer-parcial-p2-2024-s1-ex1, primer-parcial-p3-2024-s1-ex1]
---

# Solución: Longitud de Trayectoria Interna Media (LTIM)

**Question / Prompt:** Ejercicio 2 del Parcial 2024 S1 Examen 1 (pseudocódigo) y Parte 3 del mismo parcial (Java). Calcular la Longitud de Trayectoria Interna Media de un árbol binario de búsqueda.

---

## Definición

```
LTI = Σ h(i)  para todo nodo i del árbol

LTIM = LTI / n   donde n = cantidad de nodos
```

Donde `h(i)` es el nivel (profundidad) del nodo `i`, siendo el nivel de la raíz = 0.

**Ejemplo:** Para el árbol `24 → {12, 72 → {62}}`:
- Nivel de 24 = 0, nivel de 12 = 1, nivel de 72 = 1, nivel de 62 = 2
- LTI = 0 + 1 + 1 + 2 = 4
- LTIM = 4 / 4 = 1.0

---

## Lenguaje Natural

**TArbolBB.longTrayIntMedia():**
Dado un árbol binario de búsqueda, calcular la suma de los niveles de todos sus nodos (LTI) y dividirla por la cantidad de nodos para obtener la longitud de trayectoria interna media. Si el árbol está vacío, retornar 0.

**TElementoAB.calcularLTI(nivel):**
Dado un nodo y su nivel actual en el árbol, retornar la suma del nivel actual más las sumas de niveles de todos los nodos en el subárbol izquierdo y derecho, pasando nivel+1 a los hijos.

---

## Pre y Post Condiciones

### TArbolBB.longTrayIntMedia()
- **Precondición:** `this` es un árbol binario de búsqueda (puede estar vacío).
- **Postcondición:** Retorna LTIM = LTI / n. Si el árbol está vacío, retorna 0.

### TElementoAB.calcularLTI(nivel: entero)
- **Precondición:** `this` es un nodo no nulo; `nivel ≥ 0`.
- **Postcondición:** Retorna la suma de los niveles de todos los nodos del subárbol con raíz en `this`, contando el nivel de la raíz como `nivel`.

---

## Pseudocódigo

```
// === MÉTODO DE ÁRBOL ===
TArbolBB.longTrayIntMedia(): real
  si raiz = nulo entonces
    retornar 0
  fin si
  n ← raiz.obtenerTamaño()
  lti ← raiz.calcularLTI(0)
  retornar lti / n
fin método

// === MÉTODO DE NODO ===
TElementoAB.calcularLTI(nivel: entero): entero
  suma ← nivel                          // sumo mi propio nivel
  si hijoIzq ≠ nulo entonces
    suma ← suma + hijoIzq.calcularLTI(nivel + 1)
  fin si
  si hijoDer ≠ nulo entonces
    suma ← suma + hijoDer.calcularLTI(nivel + 1)
  fin si
  retornar suma
fin método
```

**Nota:** `obtenerTamaño()` ya está implementado en `TElementoAB` y recorre el árbol en O(n).

---

## Análisis del Orden de Tiempo de Ejecución

- `calcularLTI(nivel)`: visita **cada nodo exactamente una vez** → O(n)
- `obtenerTamaño()`: visita **cada nodo exactamente una vez** → O(n)
- `longTrayIntMedia()`: llama a ambos → O(n) + O(n) = **O(n)**

**Versión mejorada (un solo recorrido):** Se puede calcular LTI y tamaño en un único recorrido O(n) retornando un par (lti, tamaño):

```
TElementoAB.calcularLTIyTam(nivel: entero): (entero, entero)
  lti ← nivel
  tam ← 1
  si hijoIzq ≠ nulo entonces
    (ltiIzq, tamIzq) ← hijoIzq.calcularLTIyTam(nivel + 1)
    lti ← lti + ltiIzq
    tam ← tam + tamIzq
  fin si
  si hijoDer ≠ nulo entonces
    (ltiDer, tamDer) ← hijoDer.calcularLTIyTam(nivel + 1)
    lti ← lti + ltiDer
    tam ← tam + tamDer
  fin si
  retornar (lti, tam)
fin método

TArbolBB.longTrayIntMedia(): real
  si raiz = nulo entonces retornar 0
  (lti, n) ← raiz.calcularLTIyTam(0)
  retornar lti / n
fin método
```

Esta versión también es O(n) pero con la **mitad de recorridos** — califica mejor en el parcial por eficiencia.

---

## Implementación Java (para Parte 3)

```java
// En TArbolDeProductos (o TArbolBB):
public double longTrayIntMedia() {
    if (esVacio()) return 0;
    int n = raiz.obtenerTamaño();
    int lti = raiz.calcularLTI(0);
    return (double) lti / n;
}
```

```java
// En TElementoAB:
public int calcularLTI(int nivel) {
    int suma = nivel;
    if (hijoIzq != null) suma += hijoIzq.calcularLTI(nivel + 1);
    if (hijoDer != null) suma += hijoDer.calcularLTI(nivel + 1);
    return suma;
}
```

### Programa Principal (Main / Parcial1)

```java
public static void main(String[] args) {
    TArbolDeProductos arbol = new TArbolDeProductos();
    // Leer datos del archivo
    String[] lineas = ManejadorArchivosGenerico.leerArchivo("src/productos.txt");
    for (String linea : lineas) {
        String[] partes = linea.split(",");
        int id = Integer.parseInt(partes[0].trim());
        String nombre = partes[1].trim();
        arbol.insertar(id, new Producto(id, nombre));
    }
    // Calcular y escribir resultados
    int lti = arbol.getRaiz().calcularLTI(0);
    // altura del árbol: (requiere método adicional)
    double ltim = arbol.longTrayIntMedia();
    // Escribir salida.txt con LTI, altura, LTIM
    String salida = lti + "\n" + /* altura */ + "\n" + ltim;
    ManejadorArchivosGenerico.escribirArchivo("salida.txt", salida);
}
```

### JUnit Test

```java
@Test
public void testLongTrayIntMediaArbolCompleto() {
    TArbolDeProductos arbol = new TArbolDeProductos();
    // Árbol: 24 (raiz) → 12 (izq), 72 (der) → 62 (izq)
    arbol.insertar(24, new Producto(24, "A"));
    arbol.insertar(12, new Producto(12, "B"));
    arbol.insertar(72, new Producto(72, "C"));
    arbol.insertar(62, new Producto(62, "D"));
    // LTI = 0+1+1+2 = 4, n=4, LTIM=1.0
    assertEquals(1.0, arbol.longTrayIntMedia(), 0.001);
}

@Test
public void testLongTrayIntMediaArbolVacio() {
    TArbolDeProductos arbol = new TArbolDeProductos();
    assertEquals(0.0, arbol.longTrayIntMedia(), 0.001);
}

@Test
public void testLongTrayIntMediaUnSoloNodo() {
    TArbolDeProductos arbol = new TArbolDeProductos();
    arbol.insertar(10, new Producto(10, "A"));
    assertEquals(0.0, arbol.longTrayIntMedia(), 0.001);
}
```

---

## Confianza

Alta — el algoritmo es directo: DFS pasando el nivel actual, acumulando suma. Verificado con ejemplo concreto.

## Gaps

- Requiere acceso a `calcularLTI` en `TElementoAB`, que no está en la interfaz `IArbolBB` ni en la clase base provista. Debe agregarse al `TElementoAB` o trabajarse desde `TArbolDeProductos` usando `getRaiz()`.
