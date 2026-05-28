---
title: "Solución: Quicksort"
type: solution
tags: [sorting, quicksort, pseudocodigo, java, parcial2]
sources: [2023-2S-parte2, 2025-1S-parte2]
---

# Solución: Quicksort

**Apareció en:** 2023-2S Parte 2 (Ej 2 — memoria limitada, orden descendente), 2025-1S Parte 2 (Ej 2 — buena complejidad práctica, pocas comparaciones en promedio)

**Señal en el enunciado:** "pocas comparaciones **en promedio**" + "sin memoria adicional significativa" + datos no casi ordenados → Quicksort.

---

## Problema

Ordenar un arreglo con buen rendimiento en promedio y mínima memoria adicional. El contexto no exige garantía en el peor caso (si la exigiera → Heapsort). Los datos no están casi ordenados (si lo estuvieran → Inserción).

---

## Concepto

Elegir un **pivote**, particionar el arreglo de modo que los elementos menores queden a la izquierda del pivote y los mayores a la derecha. El pivote queda en su posición definitiva. Aplicar recursivamente a cada mitad.

---

## Lenguaje Natural

Elegir como pivote el último elemento del segmento a ordenar. Mantener un índice `i` iniciado en `inicio - 1` que marca el límite de la "zona de menores". Recorrer el segmento con `j` desde `inicio` hasta `fin - 1`. Por cada elemento `datos[j]` menor o igual al pivote, incrementar `i` e intercambiar `datos[i]` con `datos[j]`. Al terminar el recorrido, colocar el pivote en la posición `i + 1` intercambiándolo con `datos[i + 1]`. El pivote queda en su lugar definitivo. Aplicar recursivamente al segmento `[inicio, posPivote-1]` y `[posPivote+1, fin]`.

---

## Pre y Post Condiciones

- **Precondición:** El arreglo `datos` no es nulo. `inicio` y `fin` son índices válidos: `0 ≤ inicio ≤ fin < n`.
- **Postcondición:** El segmento `datos[inicio..fin]` está ordenado en forma ascendente. Opera in-place.

---

## Pseudocódigo

### Auxiliar: particionar

```
particionar(datos: double[], inicio: entero, fin: entero): entero
  pivote ← datos[fin]
  i ← inicio - 1
  j ← inicio
  mientras j < fin hacer
    si datos[j] <= pivote entonces
      i ← i + 1
      aux ← datos[i]
      datos[i] ← datos[j]
      datos[j] ← aux
    fin si
    j ← j + 1
  fin mientras
  aux ← datos[i + 1]
  datos[i + 1] ← datos[fin]
  datos[fin] ← aux
  retornar i + 1
fin método
```

### Quicksort principal

```
quicksort(datos: double[], inicio: entero, fin: entero): void
  si inicio < fin entonces
    posPivote ← particionar(datos, inicio, fin)
    quicksort(datos, inicio, posPivote - 1)
    quicksort(datos, posPivote + 1, fin)
  fin si
fin método
```

**Llamada inicial:** `quicksort(datos, 0, n - 1)`

### Variante descendente (examen 2023-2S — TDato por medición de mayor a menor)

Solo cambia la comparación en `particionar`: `datos[j].medicion >= pivote.medicion`

```
particionarDesc(datos: TDato[], inicio: entero, fin: entero): entero
  pivote ← datos[fin]
  i ← inicio - 1
  j ← inicio
  mientras j < fin hacer
    si datos[j].medicion >= pivote.medicion entonces
      i ← i + 1
      aux ← datos[i]
      datos[i] ← datos[j]
      datos[j] ← aux
    fin si
    j ← j + 1
  fin mientras
  aux ← datos[i + 1]
  datos[i + 1] ← datos[fin]
  datos[fin] ← aux
  retornar i + 1
fin método

quicksortDesc(datos: TDato[], inicio: entero, fin: entero): void
  si inicio < fin entonces
    posPivote ← particionarDesc(datos, inicio, fin)
    quicksortDesc(datos, inicio, posPivote - 1)
    quicksortDesc(datos, posPivote + 1, fin)
  fin si
fin método
```

---

## Análisis del Orden de Tiempo de Ejecución

| Caso | Complejidad | Descripción |
|------|------------|-------------|
| Mejor caso | O(n log n) | Pivote siempre divide en mitades iguales |
| Caso promedio | **O(n log n)** | Particiones razonablemente equilibradas |
| Peor caso | O(n²) | Pivote siempre el mínimo o máximo (datos ya ordenados) |
| Espacio | O(log n) | Pila de recursión en caso promedio |

El peor caso O(n²) ocurre cuando los datos están ordenados y el pivote es siempre el menor o mayor. Para el examen: si el enunciado menciona que los datos pueden estar casi ordenados, preferir Heapsort.

---

## Implementación Java

```java
public class Quicksort {

    public static void quicksort(double[] datos, int inicio, int fin) {
        if (inicio < fin) {
            int posPivote = particionar(datos, inicio, fin);
            quicksort(datos, inicio, posPivote - 1);
            quicksort(datos, posPivote + 1, fin);
        }
    }

    private static int particionar(double[] datos, int inicio, int fin) {
        double pivote = datos[fin];
        int i = inicio - 1;
        int j = inicio;
        while (j < fin) {
            if (datos[j] <= pivote) {
                i++;
                double aux = datos[i];
                datos[i] = datos[j];
                datos[j] = aux;
            }
            j++;
        }
        double aux = datos[i + 1];
        datos[i + 1] = datos[fin];
        datos[fin] = aux;
        return i + 1;
    }

    public static void quicksortDesc(TDato[] datos, int inicio, int fin) {
        if (inicio < fin) {
            int posPivote = particionarDesc(datos, inicio, fin);
            quicksortDesc(datos, inicio, posPivote - 1);
            quicksortDesc(datos, posPivote + 1, fin);
        }
    }

    private static int particionarDesc(TDato[] datos, int inicio, int fin) {
        TDato pivote = datos[fin];
        int i = inicio - 1;
        int j = inicio;
        while (j < fin) {
            if (datos[j].medicion >= pivote.medicion) {
                i++;
                TDato aux = datos[i];
                datos[i] = datos[j];
                datos[j] = aux;
            }
            j++;
        }
        TDato aux = datos[i + 1];
        datos[i + 1] = datos[fin];
        datos[fin] = aux;
        return i + 1;
    }
}
```

---

## JUnit (style del curso — JUnit 3.8.1)

```java
import junit.framework.TestCase;

public class QuicksortTest extends TestCase {

    public void testOrdenadoAscendente() {
        double[] datos = {3.0, 6.0, 8.0, 10.0, 1.0, 2.0, 1.0};
        Quicksort.quicksort(datos, 0, datos.length - 1);
        assertEquals(1.0, datos[0]);
        assertEquals(1.0, datos[1]);
        assertEquals(2.0, datos[2]);
        assertEquals(10.0, datos[6]);
    }

    public void testUnSoloElemento() {
        double[] datos = {5.0};
        Quicksort.quicksort(datos, 0, 0);
        assertEquals(5.0, datos[0]);
    }

    public void testOrdenInverso() {
        double[] datos = {5.0, 4.0, 3.0, 2.0, 1.0};
        Quicksort.quicksort(datos, 0, datos.length - 1);
        assertEquals(1.0, datos[0]);
        assertEquals(5.0, datos[4]);
    }

    public void testDosElementos() {
        double[] datos = {2.0, 1.0};
        Quicksort.quicksort(datos, 0, 1);
        assertEquals(1.0, datos[0]);
        assertEquals(2.0, datos[1]);
    }
}
```
