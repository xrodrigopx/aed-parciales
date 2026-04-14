# TDA Pila (Stack) — Pseudocódigo

Implementación: lista enlazada donde el **tope** es el primer nodo (`primero`).  
Insertar y extraer siempre al frente → todas las operaciones principales son O(1).

**Disciplina de acceso:** LIFO — Last In, First Out.

---

## Estructura interna

```
Pila<T>:
  tope: Nodo<T>       ← nulo si la pila está vacía
                        (equivale al "primero" de la lista)
```

---

## esVacia()

**Lenguaje natural:** Retorna verdadero si no hay elementos en la pila.

**Precondición:** ninguna.  
**Postcondición:** retorna `verdadero` si `tope = nulo`.

```
Pila.esVacia(): booleano
  retornar tope = nulo
fin método
```

**Orden:** O(1)

---

## apilar(dato)

**Lenguaje natural:** Crea un nuevo nodo con el dato dado y lo coloca en el tope de la pila.

**Precondición:** ninguna.  
**Postcondición:** el nuevo nodo es el tope; el tamaño aumenta en 1.

```
Pila.apilar(dato: T): void
  nodo ← nuevo Nodo(dato)
  nodo.setSiguiente(tope)
  tope ← nodo
fin método
```

**Orden:** O(1) — siempre inserta al frente.

---

## desapilar()

**Lenguaje natural:** Remueve y retorna el elemento del tope. Lanza error si la pila está vacía.

**Precondición:** `¬esVacia()`.  
**Postcondición:** el nodo del tope es removido y su dato es retornado; el tamaño disminuye en 1.

```
Pila.desapilar(): T
  si esVacia() entonces
    lanzar error "Pila vacía"
  fin si
  dato ← tope.getDato()
  tope ← tope.getSiguiente()
  retornar dato
fin método
```

**Orden:** O(1)

---

## tope()

**Lenguaje natural:** Retorna el dato del elemento en el tope sin modificar la pila.

**Precondición:** `¬esVacia()`.  
**Postcondición:** retorna el dato del tope; la pila no se modifica.

```
Pila.tope(): T
  si esVacia() entonces
    lanzar error "Pila vacía"
  fin si
  retornar tope.getDato()
fin método
```

**Orden:** O(1)

---

## Resumen de Órdenes

| Operación | Orden | Motivo |
|-----------|-------|--------|
| `esVacia()` | O(1) | compara un puntero |
| `apilar(dato)` | O(1) | inserta al frente |
| `desapilar()` | O(1) | remueve al frente |
| `tope()` | O(1) | accede al frente |

> La pila implementada sobre lista enlazada tiene **todas sus operaciones en O(1)**.  
> La pila implementada sobre arreglo también es O(1), pero tiene tamaño máximo fijo.
