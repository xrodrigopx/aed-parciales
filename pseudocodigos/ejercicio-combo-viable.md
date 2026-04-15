# Ejercicio de Parcial: preparadoViable / comboViable

**Apareció en:** Práctico #10 Farmachop, Parcial Festival Otaku (Parte 2 y Recuperatorio Parte 3)

---

## Escenario

Dado un conjunto de elementos (fármacos / ingredientes) y un contenedor (suero / bebida), determinar si la combinación es viable según tres reglas:

1. Si el elemento está en la **lista blanca** → habilitado con cualquier contenedor.
2. Si el par `{contenedor, elemento}` está en la **lista negra** → **NO** es viable.
3. Si el elemento **no aparece en ninguna lista** → **NO** es viable (por precaución).

---

## Justificación de Estructuras

| Estructura | `buscar` | Mejor para |
|------------|----------|------------|
| Lista enlazada | O(n) | implementación simple UT01 |
| ABB | O(log n) promedio | eficiencia sin garantía |
| AVL / Conjunto hash | O(log n) / O(1) | máxima eficiencia |

**Elección justificada para el parcial:**
- **Lista blanca:** Conjunto (solo verificamos pertenencia, sin datos extra) → `buscar` O(n) con lista, O(log n) con BST.
- **Lista negra:** Lista de pares `{contenedor, elemento}` → `buscar` O(n); con BST de pares sería O(log n).

---

## Lenguaje Natural

**`preparadoViable(contenedor, elementos)`:**
Para cada elemento de la lista: si está en la lista blanca, continuar al siguiente. Si el par `{contenedor, elemento}` está en la lista negra, retornar falso. Si no está en ninguna lista, retornar falso. Si todos los elementos pasaron la validación, retornar verdadero.

---

## Pre y Post Condiciones

- **Precondición:** `listaBlanca` y `listaNegra` están inicializadas y cargadas. `contenedor` es un identificador válido. `elementos` es una lista no vacía.
- **Postcondición:** retorna `verdadero` si todos los elementos son compatibles con el contenedor; `falso` si alguno no lo es.

---

## Pseudocódigo

```
// Variables globales (inicializadas al cargar el sistema)
listaBlanca: Lista<idElemento>
listaNegra:  Lista<Par(idContenedor, idElemento)>
```

### estaEnListaBlanca (auxiliar)

```
estaEnListaBlanca(elemento: id): booleano
  aux ← listaBlanca.getPrimero()
  mientras aux ≠ nulo hacer
    si aux.getDato() = elemento entonces
      retornar verdadero
    fin si
    aux ← aux.getSiguiente()
  fin mientras
  retornar falso
fin método
```

**Orden:** O(b) — donde b = tamaño de la lista blanca.

### estaEnListaNegra (auxiliar)

```
estaEnListaNegra(contenedor: id, elemento: id): booleano
  aux ← listaNegra.getPrimero()
  mientras aux ≠ nulo hacer
    par ← aux.getDato()
    si par.contenedor = contenedor Y par.elemento = elemento entonces
      retornar verdadero
    fin si
    aux ← aux.getSiguiente()
  fin mientras
  retornar falso
fin método
```

**Orden:** O(n) — donde n = tamaño de la lista negra.

### preparadoViable (método principal)

```
preparadoViable(contenedor: id, elementos: Lista<id>): booleano
  aux ← elementos.getPrimero()
  mientras aux ≠ nulo hacer
    elemento ← aux.getDato()
    si estaEnListaBlanca(elemento) entonces
      // habilitado → continuar con el siguiente
    sino si estaEnListaNegra(contenedor, elemento) entonces
      retornar falso     // par prohibido
    sino
      retornar falso     // no aparece en ninguna lista → no permitido
    fin si
    aux ← aux.getSiguiente()
  fin mientras
  retornar verdadero
fin método
```

**Orden:** O(m × (b + n)) — donde m = elementos del preparado; por cada elemento se llama a `estaEnListaBlanca` O(b) y en el peor caso a `estaEnListaNegra` O(n).

---

## Análisis del Orden de Tiempo de Ejecución

Sean:
- `m` = cantidad de elementos en el preparado/combo
- `b` = tamaño de la lista blanca
- `n` = tamaño de la lista negra

Con listas enlazadas:
- `estaEnListaBlanca`: O(b)
- `estaEnListaNegra`: O(n)
- `preparadoViable`: O(m × (b + n)) = **O(m × (b + n))**

Con Conjunto/BST para ambas listas:
- Cada búsqueda: O(log b) y O(log n)
- `preparadoViable`: **O(m × log(b + n))**
