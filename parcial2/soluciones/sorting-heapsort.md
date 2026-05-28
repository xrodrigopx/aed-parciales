---
title: "Solución: Heapsort"
type: solution
tags: [sorting, heapsort, heap, pseudocodigo, java, parcial2]
sources: [2024-1S-parte2-examen2, 2024-2S-parte2]
---

# Solución: Heapsort

**Apareció en:** 2024-1S Parte 2 Examen 2 (Ej 1 — peor caso garantizado, memoria limitada), 2024-2S Parte 2 (Ej 2 — aplicación manual)

**Señal en el enunciado:** "rendimiento constante en el **peor caso**" + memoria limitada → siempre Heapsort.

---

## Problema

Ordenar un arreglo de datos cuando se requiere garantía de O(n log n) en el peor caso y memoria adicional mínima. Mergesort también garantiza O(n log n) pero necesita O(n) espacio extra → descartado para microcontroladores.

---

## Concepto de Max-Heap

Arreglo que satisface la **propiedad de heap**: el elemento en posición `i` tiene hijos en posiciones `2i+1` y `2i+2`. El padre siempre es mayor o igual que sus hijos. El máximo siempre está en la posición 0.

```
Arreglo: [97, 19, 61, 07, 04, 25, 02, 06]

              97         ← pos 0
            /    \
          19      61     ← pos 1, 2
         /  \    /  \
        07   04 25   02  ← pos 3,4,5,6
        |
        06               ← pos 7
```

---

## Lenguaje Natural

**Fase 1 — Construir max-heap:** Recorrer desde el último nodo interior (`n/2 - 1`) hacia la raíz, aplicando `hundir` a cada nodo. Al finalizar, el arreglo cumple la propiedad de heap.

**Fase 2 — Ordenar:** Repetir n-1 veces: intercambiar la raíz (máximo) con el último elemento del heap, reducir el tamaño del heap en 1, aplicar `hundir` a la nueva raíz para restaurar la propiedad.

---

## Pre y Post Condiciones

- **Precondición:** El arreglo `datos` no es nulo. Tiene al menos 1 elemento.
- **Postcondición:** El arreglo `datos` está ordenado en forma ascendente. Opera in-place sin memoria adicional (O(1) espacio).

---

## Pseudocódigo

### Auxiliar: hundir (sift-down)

```
hundir(datos: double[], i: entero, tamanioHeap: entero): void
  izq ← 2 * i + 1
  der ← 2 * i + 2
  maximo ← i
  si izq < tamanioHeap entonces
    si datos[izq] > datos[maximo] entonces
      maximo ← izq
    fin si
  fin si
  si der < tamanioHeap entonces
    si datos[der] > datos[maximo] entonces
      maximo ← der
    fin si
  fin si
  si maximo ≠ i entonces
    aux ← datos[i]
    datos[i] ← datos[maximo]
    datos[maximo] ← aux
    hundir(datos, maximo, tamanioHeap)
  fin si
fin método
```

### Heapsort

```
heapsort(datos: double[], n: entero): void
  // Fase 1: construir max-heap
  i ← n / 2 - 1
  mientras i >= 0 hacer
    hundir(datos, i, n)
    i ← i - 1
  fin mientras

  // Fase 2: extraer máximos sucesivamente
  i ← n - 1
  mientras i > 0 hacer
    aux ← datos[0]
    datos[0] ← datos[i]
    datos[i] ← aux
    hundir(datos, 0, i)
    i ← i - 1
  fin mientras
fin método
```

---

## Ejemplo Manual (examen 2024-2S)

Vector inicial (ya es max-heap válido): `[97, 19, 61, 07, 04, 25, 02, 06]`

La fase 1 (heapify) no modifica nada porque el vector ya satisface la propiedad. Solo se ejecuta la fase 2:

| Iter | swap | Arreglo después del swap | hundir resultado |
|------|------|--------------------------|-----------------|
| i=7 | pos 0↔7 | [06,19,61,07,04,25,02,**97**] | → [61,19,25,07,04,06,02,97] |
| i=6 | pos 0↔6 | [02,19,25,07,04,06,**61**,97] | → [25,19,06,07,04,02,61,97] |
| i=5 | pos 0↔5 | [02,19,06,07,04,**25**,61,97] | → [19,07,06,02,04,25,61,97] |
| i=4 | pos 0↔4 | [04,07,06,02,**19**,25,61,97] | → [07,04,06,02,19,25,61,97] |
| i=3 | pos 0↔3 | [02,04,06,**07**,19,25,61,97] | → [06,04,02,07,19,25,61,97] |
| i=2 | pos 0↔2 | [02,04,**06**,07,19,25,61,97] | → [04,02,06,07,19,25,61,97] |
| i=1 | pos 0↔1 | [**02**,**04**,06,07,19,25,61,97] | fin |

**Resultado:** `[02, 04, 06, 07, 19, 25, 61, 97]` ✓

---

## Análisis del Orden de Tiempo de Ejecución

| Fase | Complejidad |
|------|------------|
| Construir heap | O(n) |
| n-1 extracciones × hundir O(log n) | O(n log n) |
| **Total** | **O(n log n)** en todos los casos |
| Espacio | **O(1)** — in-place |

El O(n log n) es garantizado en el **mejor, promedio y peor caso**.

---

## Implementación Java

```java
public class Heapsort {

    public static void heapsort(double[] datos, int n) {
        int i = n / 2 - 1;
        while (i >= 0) {
            hundir(datos, i, n);
            i--;
        }

        i = n - 1;
        while (i > 0) {
            double aux = datos[0];
            datos[0] = datos[i];
            datos[i] = aux;
            hundir(datos, 0, i);
            i--;
        }
    }

    private static void hundir(double[] datos, int i, int tamanioHeap) {
        int izq = 2 * i + 1;
        int der = 2 * i + 2;
        int maximo = i;

        if (izq < tamanioHeap) {
            if (datos[izq] > datos[maximo]) {
                maximo = izq;
            }
        }
        if (der < tamanioHeap) {
            if (datos[der] > datos[maximo]) {
                maximo = der;
            }
        }
        if (maximo != i) {
            double aux = datos[i];
            datos[i] = datos[maximo];
            datos[maximo] = aux;
            hundir(datos, maximo, tamanioHeap);
        }
    }
}
```

---

## JUnit (style del curso — JUnit 3.8.1)

```java
import junit.framework.TestCase;

public class HeapsortTest extends TestCase {

    public void testOrdenadoAscendente() {
        double[] datos = {97, 19, 61, 7, 4, 25, 2, 6};
        Heapsort.heapsort(datos, datos.length);
        assertEquals(2.0, datos[0]);
        assertEquals(4.0, datos[1]);
        assertEquals(6.0, datos[2]);
        assertEquals(7.0, datos[3]);
        assertEquals(19.0, datos[4]);
        assertEquals(25.0, datos[5]);
        assertEquals(61.0, datos[6]);
        assertEquals(97.0, datos[7]);
    }

    public void testYaOrdenado() {
        double[] datos = {1.0, 2.0, 3.0, 4.0, 5.0};
        Heapsort.heapsort(datos, datos.length);
        assertEquals(1.0, datos[0]);
        assertEquals(5.0, datos[4]);
    }

    public void testOrdenInverso() {
        double[] datos = {5.0, 4.0, 3.0, 2.0, 1.0};
        Heapsort.heapsort(datos, datos.length);
        assertEquals(1.0, datos[0]);
        assertEquals(5.0, datos[4]);
    }

    public void testUnSoloElemento() {
        double[] datos = {42.0};
        Heapsort.heapsort(datos, datos.length);
        assertEquals(42.0, datos[0]);
    }
}
```
