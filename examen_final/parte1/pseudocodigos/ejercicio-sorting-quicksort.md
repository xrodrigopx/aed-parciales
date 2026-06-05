# Ejercicio de Parcial: Quicksort

**Apareció en:** 2023-2S Parte 2 (Ej 2 — memoria limitada, orden descendente), 2025-1S Parte 2 (Ej 2 — buena complejidad práctica, pocas comparaciones en promedio, sin memoria extra significativa)

---

## Cuándo elegirlo

El enunciado menciona:
- Buena complejidad **en promedio** (no exige garantía en peor caso)
- Pocas comparaciones y movimientos en promedio
- Sin memoria adicional significativa (O(log n) de pila se acepta)
- Datos **no** casi ordenados (no hay pista de "casi ordenado")

Si pide garantía en peor caso → Heapsort. Si datos casi ordenados → Inserción.

---

## Justificación de la Elección

| Algoritmo | Promedio | Peor | Espacio | Nota |
|-----------|---------|------|---------|------|
| **Quicksort** | **O(n log n)** | O(n²) | O(log n) | Pocas comparaciones en promedio |
| Heapsort | O(n log n) | O(n log n) | O(1) | Peor garantizado pero más lento en práctica |
| Inserción | O(n²) | O(n²) | O(1) | Solo para casi ordenados |

Quicksort tiene la constante más baja en promedio (pocas operaciones de memoria cache-friendly), aunque su peor caso es O(n²). Para datos aleatorios es el más rápido en la práctica.

---

## Concepto

Elegir un **pivote**, particionar el arreglo en dos mitades (menores al pivote, mayores al pivote), y aplicar recursivamente a cada mitad. El pivote queda en su posición final después de cada partición.

---

## Lenguaje Natural

Elegir como pivote el último elemento del segmento. Recorrer el segmento de izquierda a derecha manteniendo un índice `i` de la "zona de menores". Por cada elemento menor o igual al pivote, incrementar `i` e intercambiarlo con el elemento actual. Al terminar el recorrido, colocar el pivote en la posición `i+1`. El pivote queda en su posición definitiva. Aplicar recursivamente al segmento izquierdo y al derecho.

---

## Pre y Post Condiciones

- **Precondición:** El arreglo `datos` no es nulo. `inicio` y `fin` son índices válidos dentro del arreglo (`0 ≤ inicio ≤ fin < n`).
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
  // colocar pivote en su posición final
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

### Variante descendente (examen 2023-2S: ordenar TDato por medición de mayor a menor)

Cambiar la comparación en `particionar`: `datos[j].medicion >= pivote.medicion`

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
```

---

## Análisis del Orden de Tiempo de Ejecución

| Caso | Complejidad | Descripción |
|------|------------|-------------|
| Mejor caso | O(n log n) | Pivote siempre divide en mitades iguales |
| Caso promedio | **O(n log n)** | Particiones razonablemente balanceadas |
| Peor caso | O(n²) | Pivote siempre el menor o mayor (datos ya ordenados) |
| Espacio | O(log n) | Pila de recursión en caso promedio; O(n) peor caso |

El peor caso O(n²) ocurre cuando los datos están ordenados y se elige siempre el último elemento como pivote. Para mitigarlo: elegir pivote aleatorio o usar la mediana de tres.
