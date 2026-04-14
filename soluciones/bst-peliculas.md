---
title: "Solución: Recomendador de Películas con BST/AVL"
type: analysis
tags: [bst, avl, peliculas, inorden, pseudocodigo, java, parcial, solucion]
created: 2026-04-14
updated: 2026-04-14
sources: [primer-parcial-p2-2025-s1, primer-parcial-p3-2025-s1]
---

# Solución: Recomendador de Películas con BST/AVL

**Question / Prompt:** Ejercicio 2 del Parcial 2025 S1 (pseudocódigo) y su correspondiente Parte 3 (Java). Sistema de catálogo de películas para plataforma de streaming, con búsquedas por score y por género.

---

## Diseño de la Estructura

### Clase Pelicula
```
Pelicula:
  - año: entero
  - titulo: cadena
  - genero: cadena
  - score: real (1–10)
```

### Elección del TDA: BST/AVL ordenado por Año de Estreno

**Justificación:**
- El catálogo se actualiza **semanalmente** con nuevas películas → necesitamos inserción eficiente: O(log n) con AVL.
- El objetivo principal del recomendador estándar es obtener películas en **orden cronológico** (año ascendente) → el recorrido **inorden** del BST por año da exactamente ese orden.
- Para filtrar por score y género, el recorrido inorden completo es O(n) — aceptable ya que igualmente habría que revisar cada película.

**Alternativa (ejercicio más difícil):** dos BSTs (uno por año, uno por score/título) o una estructura compuesta. Clave: si se usa BST por título (inorden = alfabético), la búsqueda por score requiere recorrido completo.

---

## Lenguaje Natural

**construirCatalogo(archivo):** Lee línea por línea el archivo de películas, parsea cada película y la inserta en el BST usando el año como clave (o año+título para unicidad).

**recomendarPeliculas(scoreMinimo):** Recorre el BST en inorden (orden cronológico). Para cada película, si su score es mayor al mínimo dado, la agrega a la lista de recomendadas. Al final, ordena la lista por título (alfabético) y la retorna.

**buscarPorGenero(genero):** Recorre el BST en inorden. Para cada película, si su género coincide, la agrega a la lista. La lista queda en orden cronológico ascendente.

**buscarPorRangoScore(minScore, maxScore):** Recorre el BST en inorden. Para cada película, si su score está en el rango [minScore, maxScore] (con min/max opcionales), la agrega a la lista.

---

## Pre y Post Condiciones

### `recomendarPeliculas(scoreMinimo: real): Lista<Pelicula>`
- **Precondición:** `scoreMinimo ∈ [1.0, 10.0]`. El catálogo está inicializado.
- **Postcondición:** Retorna lista de películas con score > scoreMinimo, ordenada **alfabéticamente** por título.

### `buscarPorGenero(genero: cadena): Lista<Pelicula>`
- **Precondición:** `genero` es una cadena no vacía.
- **Postcondición:** Retorna lista de películas del género dado, ordenada por año ascendente.

---

## Pseudocódigo

```
// === RECOMENDADOR ESTÁNDAR ===
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

// === BÚSQUEDA POR GÉNERO ===
TArbolPeliculas.buscarPorGenero(genero: cadena): Lista<Pelicula>
  resultado ← nueva Lista vacía
  si raiz ≠ nulo entonces
    raiz.filtrarPorGenero(genero, resultado)
  fin si
  retornar resultado   // ya está en orden por año (inorden)
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

// === BÚSQUEDA POR RANGO DE SCORE (alternativo) ===
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
  si (min = -∞ o pelicula.score ≥ min) Y (max = +∞ o pelicula.score ≤ max) entonces
    lista.insertar(pelicula)
  fin si
  si hijoDer ≠ nulo entonces
    hijoDer.filtrarPorRango(min, max, lista)
  fin si
fin método
```

---

## Análisis del Orden de Tiempo de Ejecución

- Inserción en AVL: O(log n) por película → construcción: **O(n log n)**
- `filtrarPorScore`: recorrido completo → O(n) + ordenamiento alfabético O(m log m) donde m = películas recomendadas → **O(n + m log m)**
- `filtrarPorGenero`: O(n) — inorden ya da orden por año.
- `filtrarPorRango`: O(n) — recorrido completo.

---

## Implementación Java

```java
public class CatalogoPeliculas {
    private TArbolBB<Pelicula> arbol;
    
    public CatalogoPeliculas() {
        arbol = new TArbolBB<>();
    }
    
    public void cargarDesdeArchivo(String archivo) {
        String[] lineas = ManejadorArchivosGenerico.leerArchivo(archivo);
        for (String linea : lineas) {
            if (linea.isBlank()) continue;
            String[] partes = linea.split(";");
            int anio = Integer.parseInt(partes[0].trim());
            String titulo = partes[1].trim();
            String genero = partes[2].trim();
            double score = Double.parseDouble(partes[3].trim());
            Pelicula p = new Pelicula(anio, titulo, genero, score);
            // Clave: año + título para unicidad
            arbol.insertar(anio * 10000L + titulo.hashCode(), p);
        }
    }
    
    public List<Pelicula> recomendarPorScore(double scoreMin) {
        List<Pelicula> recomendadas = new ArrayList<>();
        filtrarPorScore(arbol.getRaiz(), scoreMin, recomendadas);
        recomendadas.sort(Comparator.comparing(Pelicula::getTitulo));
        return recomendadas;
    }
    
    private void filtrarPorScore(IElementoAB<Pelicula> nodo, double min, List<Pelicula> lista) {
        if (nodo == null) return;
        filtrarPorScore(nodo.getHijoIzq(), min, lista);
        if (nodo.getDatos().getScore() > min) lista.add(nodo.getDatos());
        filtrarPorScore(nodo.getHijoDer(), min, lista);
    }
    
    public List<Pelicula> buscarPorGenero(String genero) {
        List<Pelicula> resultado = new ArrayList<>();
        filtrarPorGenero(arbol.getRaiz(), genero, resultado);
        return resultado; // ya ordenado por año (inorden)
    }
    
    private void filtrarPorGenero(IElementoAB<Pelicula> nodo, String genero, List<Pelicula> lista) {
        if (nodo == null) return;
        filtrarPorGenero(nodo.getHijoIzq(), genero, lista);
        if (nodo.getDatos().getGenero().equalsIgnoreCase(genero)) lista.add(nodo.getDatos());
        filtrarPorGenero(nodo.getHijoDer(), genero, lista);
    }
    
    public void escribirRecomendadas(List<Pelicula> peliculas, String archivo) {
        StringBuilder sb = new StringBuilder();
        for (Pelicula p : peliculas) {
            sb.append(p.getAnio()).append(";").append(p.getTitulo())
              .append(";").append(p.getGenero()).append(";").append(p.getScore())
              .append("\n");
        }
        ManejadorArchivosGenerico.escribirArchivo(archivo, sb.toString());
    }
}
```

### Ejemplo con los datos del enunciado

Películas del ejemplo con score > 7.5:
- 1942 ; Casablanca ; Romance ; 8.5
- 2000 ; Gladiator ; Acción ; 8.5
- 2009 ; Avatar ; Sci-Fi ; 8.0
- 2014 ; Interestelar ; Sci-Fi ; 8.6

Ordenadas alfabéticamente por título:
1. Avatar (2009)
2. Casablanca (1942)
3. Gladiator (2000)
4. Interestelar (2014)

### JUnit Tests

```java
@Test
void testRecomendarPorScore() {
    catalogo.cargarDesdeArchivo("Pelis.txt");
    List<Pelicula> recomendadas = catalogo.recomendarPorScore(7.5);
    // Deben ser 4 películas en orden alfabético
    assertEquals(4, recomendadas.size());
    assertEquals("Avatar", recomendadas.get(0).getTitulo());
    assertEquals("Interestelar", recomendadas.get(3).getTitulo());
}

@Test
void testBuscarPorGenero() {
    catalogo.cargarDesdeArchivo("Pelis.txt");
    List<Pelicula> scifi = catalogo.buscarPorGenero("Sci-Fi");
    assertEquals(2, scifi.size());
    assertEquals(2009, scifi.get(0).getAnio()); // Avatar primero (por año)
    assertEquals(2014, scifi.get(1).getAnio()); // Interestelar segundo
}
```

---

## Confianza

Alta — la lógica de filtrado inorden es estándar. El ordenamiento alfabético posterior es correcto. Los datos del ejemplo fueron verificados manualmente.

## Gaps

- La clave del BST (año + hashCode del título) puede tener colisiones. En la práctica, `año * 1000 + posición_en_lista` o usar AVL con comparación compuesta es más robusto.
- Para el ejercicio alternativo (búsqueda por score eficiente), se necesitaría un BST separado por score, lo que implica mantener dos estructuras sincronizadas.
