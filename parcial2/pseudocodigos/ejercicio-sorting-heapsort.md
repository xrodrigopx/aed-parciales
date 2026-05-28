# Ejercicio de Parcial: Heapsort

**Apareció en:** 2024-1S Parte 2 Examen 2 (Ej 1 — peor caso garantizado, memoria limitada), 2024-2S Parte 2 (Ej 2 — aplicación manual sobre vector dado)

---

## Cuándo elegirlo

El enunciado pide explícitamente **rendimiento constante en el peor caso** con **memoria limitada**:
- "garantice un rendimiento constante en el peor de los casos"
- "sin memoria adicional significativa"
- Combinado: O(n log n) siempre, O(1) espacio

Si los datos están casi ordenados → Inserción. Si no importa el peor caso → Quicksort.

---

## Justificación de la Elección

| Algoritmo | Mejor | Promedio | Peor | Espacio |
|-----------|-------|---------|------|---------|
| Quicksort | O(n log n) | O(n log n) | **O(n²)** | O(log n) |
| Mergesort | O(n log n) | O(n log n) | O(n log n) | **O(n)** |
| **Heapsort** | O(n log n) | O(n log n) | **O(n log n)** | **O(1)** |

Heapsort es el único con O(n log n) garantizado **y** O(1) espacio. Mergesort tiene O(n) espacio → descartado en microcontroladores.

---

## Concepto de Heap (Max-Heap)

Un **max-heap** representado en arreglo: el elemento en posición `i` tiene hijos en `2i+1` y `2i+2`. El padre siempre es mayor o igual que sus hijos. El máximo siempre está en posición 0.

```
Arreglo: [97, 19, 61, 07, 04, 25, 02, 06]

              97         ← posición 0
            /    \
          19      61     ← posiciones 1, 2
         /  \    /  \
        07   04 25   02  ← posiciones 3,4,5,6
        |
        06               ← posición 7
```

---

## Lenguaje Natural

**Fase 1 — Construir el max-heap (heapify):**
Recorrer el arreglo desde el último nodo interior (posición `n/2 - 1`) hasta la raíz (posición 0), aplicando `hundir` a cada nodo. Esto convierte el arreglo en un max-heap en O(n).

**Fase 2 — Ordenar:**
Repetir n-1 veces: intercambiar la raíz (máximo actual) con el último elemento del heap, reducir el tamaño del heap en 1, y aplicar `hundir` a la nueva raíz para restaurar la propiedad de heap.

---

## Pre y Post Condiciones

- **Precondición:** El arreglo `datos` no es nulo. Tiene al menos 1 elemento.
- **Postcondición:** El arreglo `datos` está ordenado en forma ascendente. Opera in-place sin memoria adicional.

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

### Heapsort principal

```
heapsort(datos: double[], n: entero): void
  // Fase 1: construir max-heap
  i ← n / 2 - 1
  mientras i >= 0 hacer
    hundir(datos, i, n)
    i ← i - 1
  fin mientras

  // Fase 2: ordenar extrayendo el máximo
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

Vector inicial (ya es un max-heap válido):

| pos | 0  | 1  | 2  | 3  | 4  | 5  | 6  | 7  |
|-----|----|----|----|----|----|----|----|----|
|     | 97 | 19 | 61 | 07 | 04 | 25 | 02 | 06 |

**Iteración 1** (i=7): swap pos 0↔7 → [06,19,61,07,04,25,02,**97**], hundir 06 con heap size 7:
- 06 vs hijos 19,61 → máximo=61 (pos 2), swap → [61,19,06,07,04,25,02,97]
- 06 vs hijos 25,02 → máximo=25 (pos 5), swap → [61,19,25,07,04,06,02,97]

**Iteración 2** (i=6): swap pos 0↔6 → [02,19,25,07,04,06,**61**,97], hundir 02 con size 6:
- 02 vs 19,25 → máximo=25, swap → [25,19,02,07,04,06,61,97]
- 02 vs hijos 06 (pos 5, der=6 fuera) → máximo=06, swap → [25,19,06,07,04,02,61,97]

**Iteración 3** (i=5): swap pos 0↔5 → [02,19,06,07,04,**25**,61,97], hundir 02 con size 5:
- 02 vs 19,06 → máximo=19, swap → [19,02,06,07,04,25,61,97]
- 02 vs 07,04 → máximo=07, swap → [19,07,06,02,04,25,61,97]

**Iteración 4** (i=4): swap pos 0↔4 → [04,07,06,02,**19**,25,61,97], hundir 04 con size 4:
- 04 vs 07,06 → máximo=07, swap → [07,04,06,02,19,25,61,97]
- 04 vs 02 (único hijo) → 04>02, fin

**Iteración 5** (i=3): swap pos 0↔3 → [02,04,06,**07**,19,25,61,97], hundir 02 con size 3:
- 02 vs 04,06 → máximo=06, swap → [06,04,02,07,19,25,61,97]

**Iteración 6** (i=2): swap pos 0↔2 → [02,04,**06**,07,19,25,61,97], hundir 02 con size 2:
- 02 vs 04 → máximo=04, swap → [04,02,06,07,19,25,61,97]

**Iteración 7** (i=1): swap pos 0↔1 → [**02**,**04**,06,07,19,25,61,97], size=1, fin.

**Resultado final:** [02, 04, 06, 07, 19, 25, 61, 97] ✓

---

## Análisis del Orden de Tiempo de Ejecución

| Fase | Complejidad |
|------|------------|
| Construir heap (heapify) | O(n) |
| Fase de ordenamiento (n-1 extracciones × hundir) | O(n log n) |
| **Total** | **O(n log n)** |
| Espacio | **O(1)** — in-place, sin estructuras auxiliares |

El orden es O(n log n) en **todos los casos** (mejor, promedio y peor).
