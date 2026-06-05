# Ejercicio de Parcial: Recomendador de Películas con BST

**Apareció en:** Parcial 2025 1er Semestre (Parte 2 y Parte 3)

---

## Escenario

Sistema de catálogo de películas para una plataforma de streaming. Cada película tiene: año de estreno, título, género y score (1–10). Se requiere:

1. Obtener películas con score mayor a un mínimo dado, ordenadas **alfabéticamente** por título.
2. Obtener películas de un género dado, en **orden cronológico** (año ascendente).

---

## Justificación de Estructuras

**Elección: BST/AVL con clave = año de estreno**

| Criterio | Justificación |
|----------|---------------|
| Orden cronológico | El recorrido **inorden** del BST por año da exactamente el orden por año ascendente, sin costo adicional |
| Inserción frecuente | El catálogo se actualiza semanalmente → AVL garantiza O(log n) en inserción siempre |
| Filtrado por score/género | Requiere revisar todas las películas de todas formas → recorrido inorden completo O(n) es aceptable |

**Alternativa rechazada: BST por score**
- Inorden daría orden por score, no por año.
- Para el filtro por género seguiría siendo O(n).
- Pierde la ventaja del orden cronológico directo.

---

## Lenguaje Natural

**`recomendarPorScore(scoreMin)`:**
Recorrer el árbol en inorden. Para cada película, si su score es mayor al mínimo, agregarla a la lista de recomendadas. Al terminar el recorrido, ordenar la lista alfabéticamente por título y retornarla.

**`buscarPorGenero(genero)`:**
Recorrer el árbol en inorden. Para cada película, si su género coincide con el buscado, agregarla a la lista resultado. Retornar la lista (ya queda en orden cronológico por el inorden).

**`buscarPorRangoScore(minScore, maxScore)`:**
Recorrer el árbol en inorden. Para cada película, si su score está en el rango `[minScore, maxScore]`, agregarla a la lista. Retornar la lista en orden cronológico.

---

## Pre y Post Condiciones

**`recomendarPorScore(scoreMin: real)`**
- **Precondición:** `scoreMin ∈ [1.0, 10.0]`. El catálogo está inicializado.
- **Postcondición:** Retorna lista de películas con `score > scoreMin`, ordenada **alfabéticamente** por título.

**`buscarPorGenero(genero: cadena)`**
- **Precondición:** `genero` es una cadena no vacía.
- **Postcondición:** Retorna lista de películas del género dado, en orden cronológico (año ascendente).

**`buscarPorRangoScore(minScore: real, maxScore: real)`**
- **Precondición:** `minScore ≤ maxScore`.
- **Postcondición:** Retorna lista de películas con score en `[minScore, maxScore]`, en orden cronológico.

---

## Pseudocódigo

### recomendarPorScore

```
TArbolPeliculas.recomendarPorScore(scoreMin: real): Lista<Pelicula>
  recomendadas ← nueva Lista vacía
  si raiz ≠ nulo entonces
    raiz.filtrarPorScore(scoreMin, recomendadas)
  fin si
  recomendadas ← ordenarAlfabeticamente(recomendadas)
  retornar recomendadas
fin método

TElementoAB.filtrarPorScore(scoreMin: real, lista: Lista<Pelicula>): void
  si hijoIzq ≠ nulo entonces
    hijoIzq.filtrarPorScore(scoreMin, lista)
  fin si
  si this.getDatos().score > scoreMin entonces
    lista.insertar(this.getDatos())
  fin si
  si hijoDer ≠ nulo entonces
    hijoDer.filtrarPorScore(scoreMin, lista)
  fin si
fin método
```

**Orden:** O(n + m log m) — recorrido inorden completo O(n) + ordenamiento alfabético O(m log m), donde m = películas que pasan el filtro.

### buscarPorGenero

```
TArbolPeliculas.buscarPorGenero(genero: cadena): Lista<Pelicula>
  resultado ← nueva Lista vacía
  si raiz ≠ nulo entonces
    raiz.filtrarPorGenero(genero, resultado)
  fin si
  retornar resultado    // inorden → ya está ordenado por año
fin método

TElementoAB.filtrarPorGenero(genero: cadena, lista: Lista<Pelicula>): void
  si hijoIzq ≠ nulo entonces
    hijoIzq.filtrarPorGenero(genero, lista)
  fin si
  si this.getDatos().genero = genero entonces
    lista.insertar(this.getDatos())
  fin si
  si hijoDer ≠ nulo entonces
    hijoDer.filtrarPorGenero(genero, lista)
  fin si
fin método
```

**Orden:** O(n) — recorrido inorden completo; sin ordenamiento extra.

### buscarPorRangoScore

```
TArbolPeliculas.buscarPorRangoScore(minScore: real, maxScore: real): Lista<Pelicula>
  resultado ← nueva Lista vacía
  si raiz ≠ nulo entonces
    raiz.filtrarPorRango(minScore, maxScore, resultado)
  fin si
  retornar resultado
fin método

TElementoAB.filtrarPorRango(min: real, max: real, lista: Lista<Pelicula>): void
  si hijoIzq ≠ nulo entonces
    hijoIzq.filtrarPorRango(min, max, lista)
  fin si
  pelicula ← this.getDatos()
  si pelicula.score ≥ min Y pelicula.score ≤ max entonces
    lista.insertar(pelicula)
  fin si
  si hijoDer ≠ nulo entonces
    hijoDer.filtrarPorRango(min, max, lista)
  fin si
fin método
```

**Orden:** O(n) — recorrido inorden completo; sin ordenamiento extra.

**Nota sobre `ordenarAlfabeticamente`:** corresponde a un algoritmo de ordenamiento sobre la lista resultado. En Java se resuelve con `Collections.sort()` o `List.sort(Comparator.comparing(Pelicula::getTitulo))`.

---

## Análisis del Orden de Tiempo de Ejecución

Sea `n` = cantidad de películas en el catálogo y `m` = cantidad de películas que pasan el filtro.

| Operación | Orden | Motivo |
|-----------|-------|--------|
| Construcción del árbol (n inserciones) | **O(n log n)** | AVL: O(log n) por inserción |
| `recomendarPorScore` | **O(n + m log m)** | recorrido inorden O(n) + ordenamiento alfabético O(m log m) |
| `buscarPorGenero` | **O(n)** | recorrido inorden completo; sin ordenamiento extra |
| `buscarPorRangoScore` | **O(n)** | recorrido inorden completo; sin ordenamiento extra |

> El costo extra de `recomendarPorScore` (el `m log m`) viene del ordenamiento alfabético posterior al recorrido. Si `m` es pequeño respecto a `n`, este costo es despreciable.
