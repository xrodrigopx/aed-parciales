# TDA Cola (Queue) — Pseudocódigo

Implementación A: **arreglo circular** (la vista en clase).  
Implementación B: **lista enlazada** con punteros `frente` y `posterior`.

**Disciplina de acceso:** FIFO — First In, First Out.

---

## Implementación A: Arreglo Circular

### Estructura interna

```
Cola<T>:
  arreglo: arreglo[0..MAX-1] de T
  frente:    entero ← 0       ← índice del primer elemento
  posterior: entero ← 0       ← índice donde se insertará el próximo
  cantidad:  entero ← 0       ← cantidad de elementos actuales
  MAX:       constante entera  ← tamaño máximo del arreglo
```

### esVacia()

**Precondición:** ninguna.  
**Postcondición:** retorna `verdadero` si `cantidad = 0`.

```
Cola.esVacia(): booleano
  retornar cantidad = 0
fin método
```

**Orden:** O(1)

### esLlena()

```
Cola.esLlena(): booleano
  retornar cantidad = MAX
fin método
```

**Orden:** O(1)

### encolar(dato)

**Lenguaje natural:** Inserta el dato al final lógico de la cola. El índice `posterior` avanza de forma circular.

**Precondición:** `¬esLlena()`.  
**Postcondición:** el dato queda en la posición `posterior`; `posterior` avanza; `cantidad` aumenta en 1.

```
Cola.encolar(dato: T): void
  si esLlena() entonces
    lanzar error "Cola llena"
  fin si
  arreglo[posterior] ← dato
  posterior ← (posterior + 1) mod MAX
  cantidad ← cantidad + 1
fin método
```

**Orden:** O(1)

### desencolar()

**Lenguaje natural:** Remueve y retorna el elemento del frente. El índice `frente` avanza de forma circular.

**Precondición:** `¬esVacia()`.  
**Postcondición:** el elemento del frente es retornado; `frente` avanza; `cantidad` disminuye en 1.

```
Cola.desencolar(): T
  si esVacia() entonces
    lanzar error "Cola vacía"
  fin si
  dato ← arreglo[frente]
  frente ← (frente + 1) mod MAX
  cantidad ← cantidad - 1
  retornar dato
fin método
```

**Orden:** O(1)

### frente()

**Precondición:** `¬esVacia()`.  
**Postcondición:** retorna el dato del frente sin modificar la cola.

```
Cola.frente(): T
  si esVacia() entonces
    lanzar error "Cola vacía"
  fin si
  retornar arreglo[frente]
fin método
```

**Orden:** O(1)

---

## Implementación B: Lista Enlazada con dos punteros

### Estructura interna

```
Cola<T>:
  frente:    Nodo<T>   ← primer elemento (se extrae aquí)
  posterior: Nodo<T>   ← último elemento (se inserta aquí)
```

### encolar(dato)

```
Cola.encolar(dato: T): void
  nodo ← nuevo Nodo(dato)
  si esVacia() entonces
    frente    ← nodo
    posterior ← nodo
  sino
    posterior.setSiguiente(nodo)
    posterior ← nodo
  fin si
fin método
```

**Orden:** O(1) — gracias al puntero `posterior`, no hay que recorrer la lista.

### desencolar()

```
Cola.desencolar(): T
  si esVacia() entonces
    lanzar error "Cola vacía"
  fin si
  dato   ← frente.getDato()
  frente ← frente.getSiguiente()
  si frente = nulo entonces
    posterior ← nulo    // la cola quedó vacía
  fin si
  retornar dato
fin método
```

**Orden:** O(1)

---

## Resumen de Órdenes

| Operación | Orden | Motivo |
|-----------|-------|--------|
| `esVacia()` | O(1) | compara contador o puntero |
| `encolar(dato)` | O(1) | inserta al posterior |
| `desencolar()` | O(1) | remueve del frente |
| `frente()` | O(1) | accede al frente |

> Ambas implementaciones tienen **todas sus operaciones en O(1)**.  
> El arreglo circular tiene tamaño fijo; la lista enlazada es dinámica pero usa más memoria por nodo.
