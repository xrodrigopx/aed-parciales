---
title: "Solución: Ordenamiento por Inserción"
type: solution
tags: [sorting, insercion, pseudocodigo, java, parcial2]
sources: [2024-1S-parte2-examen1, 2023-2S-parte2]
---

# Solución: Ordenamiento por Inserción

**Apareció en:** 2024-1S Parte 2 Examen 1 (Ej 1), 2023-2S Parte 2 (Ej 2 — variante descendente)

**Señal en el enunciado:** datos **casi ordenados** + memoria **limitada** → siempre Inserción.

---

## Problema

Ordenar un arreglo de datos. El contexto indica que los datos están **casi ordenados** (lecturas de temperatura estables año a año) y el sistema tiene **memoria limitada** (microcontrolador). Se requiere especificar el algoritmo apropiado con lenguaje natural, pre/post condiciones, pseudocódigo y análisis de orden.

---

## Estructuras de Datos

Solo se usa el arreglo ya existente. Variable auxiliar `clave` de tipo compatible con el arreglo. No se crean nuevas estructuras — algoritmo in-place.

---

## Lenguaje Natural

Recorrer el arreglo desde la posición 1 hasta el final. Para cada elemento en la posición `i`, guardarlo como `clave`. Desplazar hacia la derecha todos los elementos anteriores que sean mayores que `clave`. Insertar `clave` en la posición vacía resultante. Al finalizar, el arreglo queda ordenado de menor a mayor.

Para la variante descendente: desplazar los elementos que sean **menores** que `clave`.

---

## Pre y Post Condiciones

- **Precondición:** El arreglo `datos` no es nulo. Tiene al menos 1 elemento.
- **Postcondición:** El arreglo `datos` está ordenado en forma ascendente (o descendente en la variante). El algoritmo opera in-place sin memoria adicional.

---

## Pseudocódigo

### Ascendente

```
insercion(datos: double[], n: entero): void
  i ← 1
  mientras i < n hacer
    clave ← datos[i]
    j ← i - 1
    mientras j >= 0 Y datos[j] > clave hacer
      datos[j + 1] ← datos[j]
      j ← j - 1
    fin mientras
    datos[j + 1] ← clave
    i ← i + 1
  fin mientras
fin método
```

### Descendente (examen 2023-2S — TDato por medición)

```
insercionDesc(datos: TDato[], n: entero): void
  i ← 1
  mientras i < n hacer
    clave ← datos[i]
    j ← i - 1
    mientras j >= 0 Y datos[j].medicion < clave.medicion hacer
      datos[j + 1] ← datos[j]
      j ← j - 1
    fin mientras
    datos[j + 1] ← clave
    i ← i + 1
  fin mientras
fin método
```

---

## Análisis del Orden de Tiempo de Ejecución

| Caso | Complejidad | Explicación |
|------|------------|-------------|
| Mejor caso | **O(n)** | Datos ya ordenados: while interno nunca ejecuta |
| Caso promedio | O(n²) | Promedio de inversiones = n²/4 |
| Peor caso | O(n²) | Datos en orden inverso: n(n-1)/2 comparaciones |
| Espacio | **O(1)** | Solo la variable `clave` |

Con datos **casi ordenados** el caso efectivo se acerca al mejor caso: pocas inversiones → while interno ejecuta muy poco → rendimiento cercano a O(n).

---

## Implementación Java

```java
public class OrdenamientoPorInsercion {

    public static void insercion(double[] datos, int n) {
        int i = 1;
        while (i < n) {
            double clave = datos[i];
            int j = i - 1;
            while (j >= 0) {
                if (datos[j] > clave) {
                    datos[j + 1] = datos[j];
                    j--;
                } else {
                    break;
                }
            }
            datos[j + 1] = clave;
            i++;
        }
    }

    public static void insercionDesc(TDato[] datos, int n) {
        int i = 1;
        while (i < n) {
            TDato clave = datos[i];
            int j = i - 1;
            while (j >= 0) {
                if (datos[j].medicion < clave.medicion) {
                    datos[j + 1] = datos[j];
                    j--;
                } else {
                    break;
                }
            }
            datos[j + 1] = clave;
            i++;
        }
    }
}
```

---

## JUnit (style del curso — JUnit 3.8.1)

```java
import junit.framework.TestCase;

public class InsercionTest extends TestCase {

    public void testOrdenadoAscendente() {
        double[] datos = {3.0, 1.0, 4.0, 1.5, 9.0, 2.6};
        OrdenamientoPorInsercion.insercion(datos, datos.length);
        assertEquals(1.0, datos[0]);
        assertEquals(1.5, datos[1]);
        assertEquals(2.6, datos[2]);
        assertEquals(3.0, datos[3]);
        assertEquals(4.0, datos[4]);
        assertEquals(9.0, datos[5]);
    }

    public void testYaOrdenado() {
        double[] datos = {1.0, 2.0, 3.0, 4.0};
        OrdenamientoPorInsercion.insercion(datos, datos.length);
        assertEquals(1.0, datos[0]);
        assertEquals(4.0, datos[3]);
    }

    public void testUnSoloElemento() {
        double[] datos = {5.0};
        OrdenamientoPorInsercion.insercion(datos, datos.length);
        assertEquals(5.0, datos[0]);
    }

    public void testOrdenInverso() {
        double[] datos = {5.0, 4.0, 3.0, 2.0, 1.0};
        OrdenamientoPorInsercion.insercion(datos, datos.length);
        assertEquals(1.0, datos[0]);
        assertEquals(5.0, datos[4]);
    }
}
```
