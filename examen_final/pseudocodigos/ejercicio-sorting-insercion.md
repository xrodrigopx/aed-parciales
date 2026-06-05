# Ejercicio de Parcial: Ordenamiento por Inserción

**Apareció en:** 2024-1S Parte 2 Examen 1 (Ej 1 — datos casi ordenados, memoria limitada)

---

## Cuándo elegirlo

El enunciado da **dos pistas juntas**:
- Los datos están **casi ordenados** (lecturas estables año a año, datos temporales, etc.)
- La memoria es **limitada** (microcontrolador, sistema embebido)

Si solo menciona memoria limitada sin "casi ordenado" → Heapsort.

---

## Justificación de la Elección

| Algoritmo | Mejor Caso | Peor Caso | Espacio Extra | Estable |
|-----------|-----------|-----------|---------------|---------|
| Inserción | **O(n)** | O(n²) | **O(1)** | Sí |
| Heapsort | O(n log n) | O(n log n) | O(1) | No |
| Quicksort | O(n log n) | O(n²) | O(log n) | No |
| Mergesort | O(n log n) | O(n log n) | O(n) | Sí |

Inserción es óptimo cuando los datos están casi ordenados: el bucle interno apenas se ejecuta, dando O(n) en la práctica. Además es in-place (O(1) espacio), ideal para microcontroladores.

---

## Lenguaje Natural

Recorrer el arreglo desde la posición 1 hasta el final. Para cada elemento en la posición `i`, guardarlo como `clave`. Desplazar hacia la derecha todos los elementos anteriores que sean mayores que `clave`. Insertar `clave` en la posición vacía resultante. Al finalizar, el arreglo queda ordenado de menor a mayor.

---

## Pre y Post Condiciones

- **Precondición:** El arreglo `datos` no es nulo. Tiene al menos 1 elemento.
- **Postcondición:** El arreglo `datos` está ordenado en forma ascendente. El algoritmo opera in-place sin memoria adicional.

---

## Pseudocódigo

### Versión ascendente (genérica)

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

### Variante descendente (examen 2023-2S: ordenar TDato por medición de mayor a menor)

```
insercionDescendente(datos: TDato[], n: entero): void
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

| Caso | Complejidad | Descripción |
|------|------------|-------------|
| Mejor caso | **O(n)** | Datos ya ordenados: while interno no ejecuta |
| Caso promedio | O(n²) | Datos aleatorios |
| Peor caso | O(n²) | Datos en orden inverso: while interno ejecuta i veces por cada i |
| Espacio | **O(1)** | Solo la variable `clave` — verdaderamente in-place |
