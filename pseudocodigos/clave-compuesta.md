# Clave Compuesta en ABB/AVL — Pseudocódigo

Una **clave compuesta** se usa cuando el campo natural de la clave puede tener duplicados, pero la combinación de dos campos es única. Se modela como un tipo propio que implementa `Comparable`.

**Ejemplo base:** catálogo de películas donde puede haber varias del mismo año. La clave es `(año, título)`.

---

## Tipo ClavePelicula

**Lenguaje natural:** Dos películas se comparan primero por año; si el año coincide, se desempata por título alfabético. Solo son "iguales" si ambos campos coinciden.

```
ClavePelicula:
  anio:   entero
  titulo: cadena
```

### compareTo

**Precondición:** `otra ≠ nulo`.  
**Postcondición:** retorna negativo si `this < otra`, 0 si son iguales, positivo si `this > otra`.

```
ClavePelicula.compareTo(otra: ClavePelicula): entero
  si this.anio < otra.anio entonces
    retornar -1
  fin si
  si this.anio > otra.anio entonces
    retornar +1
  fin si
  // años iguales → desempate por título
  retornar this.titulo.compareTo(otra.titulo)
fin método
```

**Orden:** O(L) donde L = longitud máxima del título; en la práctica O(1).

---

## Árbol con clave compuesta

El árbol es un ABB o AVL estándar — no cambia ningún método del árbol ni de los nodos. El único cambio es la etiqueta que se le pasa. El árbol llama a `compareTo` de la etiqueta, que ahora es un `ClavePelicula`.

```
TArbolPeliculas<Pelicula>:
  raiz: TElementoAB<Pelicula>   // o TElementoAVL si se necesita balanceo
```

---

## insertar(pelicula)

**Lenguaje natural:** Construye la clave compuesta con el año y el título de la película, luego delega en el insertar estándar del árbol.

**Precondición:** `pelicula ≠ nulo`. No existe otra película con el mismo año y título.  
**Postcondición:** la película queda insertada con clave `(año, título)`; el árbol mantiene la propiedad ABB.

```
TArbolPeliculas.insertar(pelicula: Pelicula): booleano
  clave ← nueva ClavePelicula(pelicula.anio, pelicula.titulo)
  retornar raiz_arbol.insertar(clave, pelicula)   // delega en ABB/AVL estándar
fin método
```

**Orden:** O(log n) promedio (ABB) / O(log n) garantizado (AVL).

---

## buscar(anio, titulo)

**Lenguaje natural:** Para buscar un elemento exacto se construye la misma clave compuesta y se usa el `buscar` estándar del árbol.

**Precondición:** ninguna.  
**Postcondición:** retorna la película con esa clave, o `nulo` si no existe.

```
TArbolPeliculas.buscar(anio: entero, titulo: cadena): Pelicula
  clave ← nueva ClavePelicula(anio, titulo)
  retornar raiz_arbol.buscar(clave)   // delega en ABB/AVL estándar
fin método
```

**Orden:** O(log n).

---

## listarPorAnio() — inOrden

**Lenguaje natural:** El recorrido inorden del árbol produce las películas ordenadas por año ascendente; dentro del mismo año, por título alfabético. No requiere ningún cambio respecto al inOrden estándar.

**Precondición:** ninguna.  
**Postcondición:** retorna una lista con todas las películas ordenadas por `(año, título)`.

```
TArbolPeliculas.listarPorAnio(): Lista<Pelicula>
  resultado ← nueva Lista vacía
  si raiz ≠ nulo entonces
    raiz.inOrden(resultado)
  fin si
  retornar resultado
fin método

TElementoAB.inOrden(lista: Lista<Pelicula>): void
  si hijoIzq ≠ nulo entonces hijoIzq.inOrden(lista)
  lista.insertar(this.getDato())
  si hijoDer ≠ nulo entonces hijoDer.inOrden(lista)
fin método
```

**Orden:** O(n)

---

## filtrarPorRangoAnio(anioMin, anioMax)

**Lenguaje natural:** Con clave compuesta no se puede saltar directamente al primer nodo del rango (porque la clave tiene dos campos y `buscar(año)` no existe). Se recorre el árbol en inOrden completo y se incluyen solo las películas cuyo año cae en el rango. El inOrden garantiza que la lista de resultados queda en orden `(año, título)`.

> **Nota:** si el árbol fuese de clave simple (solo año), se podría podar la búsqueda y hacer el filtrado en O(log n + k). Con clave compuesta el costo es O(n) porque no se puede podar por año solamente.

**Precondición:** `anioMin ≤ anioMax`.  
**Postcondición:** retorna lista de películas con `anioMin ≤ año ≤ anioMax`, ordenada por `(año, título)`.

```
TArbolPeliculas.filtrarPorRangoAnio(anioMin: entero, anioMax: entero): Lista<Pelicula>
  resultado ← nueva Lista vacía
  si raiz ≠ nulo entonces
    raiz.filtrarRango(anioMin, anioMax, resultado)
  fin si
  retornar resultado
fin método

TElementoAB.filtrarRango(anioMin: entero, anioMax: entero, lista: Lista<Pelicula>): void
  si hijoIzq ≠ nulo entonces
    hijoIzq.filtrarRango(anioMin, anioMax, lista)
  fin si
  si this.getDato().anio >= anioMin y this.getDato().anio <= anioMax entonces
    lista.insertar(this.getDato())
  fin si
  si hijoDer ≠ nulo entonces
    hijoDer.filtrarRango(anioMin, anioMax, lista)
  fin si
fin método
```

**Orden:** O(n) — recorrido inorden completo.

---

## Traza de inserción

Insertando las películas:

| Año | Título |
|-----|--------|
| 2010 | "Inception" |
| 2008 | "The Dark Knight" |
| 2010 | "Toy Story 3" |
| 2012 | "Avengers" |

Claves compuestas (año, título) comparadas con `compareTo`:

```
Insertar (2010, "Inception")      → raíz
Insertar (2008, "The Dark Knight") → izq de raíz  (2008 < 2010)
Insertar (2010, "Toy Story 3")    → der de raíz  (2010 = 2010, "Toy Story 3" > "Inception")
Insertar (2012, "Avengers")       → der de (2010, "Toy Story 3")  (2012 > 2010)
```

Árbol resultante:

```
              (2010, "Inception")
             /                   \
(2008, "The Dark Knight")   (2010, "Toy Story 3")
                                         \
                                   (2012, "Avengers")
```

**inOrden:** (2008, TDK) → (2010, Inception) → (2010, Toy Story 3) → (2012, Avengers)

Orden correcto: por año ascendente, desempatado por título alfabético.

---

## Resumen de Órdenes

| Operación | Orden | Condición |
|-----------|-------|-----------|
| `insertar` | O(log n) / O(log n) garantizado | ABB promedio / AVL siempre |
| `buscar(anio, titulo)` | O(log n) | búsqueda exacta por clave compuesta |
| `listarPorAnio` | O(n) | inOrden completo |
| `filtrarPorRangoAnio` | O(n) | inOrden completo con filtro — no se puede podar |

> **Costo de la clave compuesta:** se pierde la poda de rango (que sería O(log n + k) con clave simple por año). Se gana unicidad y orden correcto. Si el rango por año es la operación más crítica y hay pocas colisiones en años, es mejor tolerar duplicados con una política explícita o usar dos estructuras.
