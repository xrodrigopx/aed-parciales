# TDA Lista Enlazada — Pseudocódigo

Implementación: lista simplemente enlazada con puntero `primero`.  
Cada nodo tiene `etiqueta` (clave comparable), `dato` (valor genérico) y `siguiente`.

---

## Estructura interna

```
Nodo<T>:
  etiqueta: Comparable
  dato: T
  siguiente: Nodo<T>

Lista<T>:
  primero: Nodo<T>       ← nulo si la lista está vacía
```

---

## esVacia()

**Lenguaje natural:** Retorna verdadero si la lista no tiene elementos.

**Precondición:** ninguna.  
**Postcondición:** retorna `verdadero` si `primero = nulo`, `falso` en caso contrario.

```
Lista.esVacia(): booleano
  retornar primero = nulo
fin método
```

**Orden:** O(1)

---

## insertar(nodo)

**Lenguaje natural:** Inserta el nodo al final de la lista.

**Precondición:** `nodo ≠ nulo`.  
**Postcondición:** el nodo queda como último elemento; el tamaño aumenta en 1.

```
Lista.insertar(nodo: Nodo<T>): void
  si esVacia() entonces
    primero ← nodo
  sino
    aux ← primero
    mientras aux.getSiguiente() ≠ nulo hacer
      aux ← aux.getSiguiente()
    fin mientras
    aux.setSiguiente(nodo)
  fin si
fin método
```

**Orden:** O(n) — recorre toda la lista para encontrar el último nodo.

> **Mejora:** mantener un puntero `ultimo` reduce esta operación a O(1).

---

## insertar(etiqueta, dato)

**Lenguaje natural:** Crea un nodo con la etiqueta y dato dados, y lo inserta al final.

**Precondición:** ninguna.  
**Postcondición:** equivalente al `insertar(nodo)` anterior.

```
Lista.insertar(etiqueta: Comparable, dato: T): void
  nodo ← nuevo Nodo(etiqueta, dato)
  insertar(nodo)
fin método
```

**Orden:** O(n)

---

## buscar(clave)

**Lenguaje natural:** Recorre la lista buscando el nodo cuya etiqueta coincida con la clave. Retorna el nodo si lo encuentra, nulo si no existe.

**Precondición:** ninguna.  
**Postcondición:** retorna el primer nodo con `etiqueta = clave`, o `nulo`.

```
Lista.buscar(clave: Comparable): Nodo<T>
  aux ← primero
  mientras aux ≠ nulo hacer
    si aux.getEtiqueta() = clave entonces
      retornar aux
    fin si
    aux ← aux.getSiguiente()
  fin mientras
  retornar nulo
fin método
```

**Orden:** O(n) — en el peor caso recorre toda la lista.

---

## eliminar(clave)

**Lenguaje natural:** Busca el nodo con la clave dada y lo desvincula de la lista. Retorna verdadero si fue eliminado, falso si no existía.

**Precondición:** ninguna.  
**Postcondición:** si existía un nodo con `etiqueta = clave`, es removido y retorna `verdadero`. Si no, retorna `falso`.

```
Lista.eliminar(clave: Comparable): booleano
  si esVacia() entonces
    retornar falso
  fin si
  si primero.getEtiqueta() = clave entonces
    primero ← primero.getSiguiente()
    retornar verdadero
  fin si
  aux ← primero
  mientras aux.getSiguiente() ≠ nulo hacer
    si aux.getSiguiente().getEtiqueta() = clave entonces
      aux.setSiguiente(aux.getSiguiente().getSiguiente())
      retornar verdadero
    fin si
    aux ← aux.getSiguiente()
  fin mientras
  retornar falso
fin método
```

**Orden:** O(n) — recorre hasta encontrar el nodo o llegar al final.

---

## cantElementos()

**Lenguaje natural:** Cuenta la cantidad de nodos en la lista recorriéndola completa.

**Precondición:** ninguna.  
**Postcondición:** retorna un entero ≥ 0 con la cantidad de nodos.

```
Lista.cantElementos(): entero
  contador ← 0
  aux ← primero
  mientras aux ≠ nulo hacer
    contador ← contador + 1
    aux ← aux.getSiguiente()
  fin mientras
  retornar contador
fin método
```

**Orden:** O(n)

> **Mejora:** mantener un campo `tamaño` actualizado en insertar/eliminar reduce esta operación a O(1).

---

## imprimir()

**Lenguaje natural:** Recorre la lista e imprime cada etiqueta.

**Precondición:** ninguna.  
**Postcondición:** imprime las etiquetas de todos los nodos en orden.

```
Lista.imprimir(): void
  aux ← primero
  mientras aux ≠ nulo hacer
    imprimir(aux.getEtiqueta())
    aux ← aux.getSiguiente()
  fin mientras
fin método
```

**Orden:** O(n)

---

## Resumen de Órdenes

| Operación | Orden |
|-----------|-------|
| `esVacia()` | O(1) |
| `insertar(nodo)` — al final | O(n) |
| `insertar(nodo)` — al frente | O(1) |
| `buscar(clave)` | O(n) |
| `eliminar(clave)` | O(n) |
| `cantElementos()` | O(n) |
