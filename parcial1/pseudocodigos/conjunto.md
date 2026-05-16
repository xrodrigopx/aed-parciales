# TDA Conjunto (Set) — Pseudocódigo

Implementación: lista enlazada **sin duplicados**.  
Un conjunto almacena elementos únicos; no hay orden definido.

---

## Estructura interna

```
Conjunto<T>:
  primero: Nodo<T>    ← nulo si el conjunto está vacío
```

---

## esVacio()

**Precondición:** ninguna.  
**Postcondición:** retorna `verdadero` si el conjunto no tiene elementos.

```
Conjunto.esVacio(): booleano
  retornar primero = nulo
fin método
```

**Orden:** O(1)

---

## buscar(dato)

**Lenguaje natural:** Recorre el conjunto buscando el dato. Retorna el nodo si existe, nulo si no.

**Precondición:** ninguna.  
**Postcondición:** retorna el nodo que contiene `dato`, o `nulo`.

```
Conjunto.buscar(dato: T): Nodo<T>
  aux ← primero
  mientras aux ≠ nulo hacer
    si aux.getDato() = dato entonces
      retornar aux
    fin si
    aux ← aux.getSiguiente()
  fin mientras
  retornar nulo
fin método
```

**Orden:** O(n)

---

## insertar(dato)

**Lenguaje natural:** Inserta el dato en el conjunto solo si no existe ya. Garantiza unicidad.

**Precondición:** ninguna.  
**Postcondición:** si `dato` no estaba en el conjunto, se agrega y retorna `verdadero`; si ya existía, retorna `falso`.

```
Conjunto.insertar(dato: T): booleano
  si buscar(dato) ≠ nulo entonces
    retornar falso      // ya existe, no se inserta
  fin si
  nodo ← nuevo Nodo(dato)
  nodo.setSiguiente(primero)
  primero ← nodo        // inserta al frente en O(1)
  retornar verdadero
fin método
```

**Orden:** O(n) — dominado por `buscar`.

---

## eliminar(dato)

**Lenguaje natural:** Busca y elimina el dato del conjunto. Retorna verdadero si fue eliminado.

**Precondición:** ninguna.  
**Postcondición:** si `dato` existía, es removido y retorna `verdadero`; si no, retorna `falso`.

```
Conjunto.eliminar(dato: T): booleano
  si esVacio() entonces
    retornar falso
  fin si
  si primero.getDato() = dato entonces
    primero ← primero.getSiguiente()
    retornar verdadero
  fin si
  aux ← primero
  mientras aux.getSiguiente() ≠ nulo hacer
    si aux.getSiguiente().getDato() = dato entonces
      aux.setSiguiente(aux.getSiguiente().getSiguiente())
      retornar verdadero
    fin si
    aux ← aux.getSiguiente()
  fin mientras
  retornar falso
fin método
```

**Orden:** O(n)

---

## esSubconjunto(otroConjunto)

**Lenguaje natural:** Retorna verdadero si todos los elementos de `otroConjunto` están en `this`.

**Precondición:** `otroConjunto ≠ nulo`.  
**Postcondición:** retorna `verdadero` si ∀ elemento de `otroConjunto`, `buscar(elemento) ≠ nulo`.

```
Conjunto.esSubconjunto(otro: Conjunto<T>): booleano
  aux ← otro.getPrimero()
  mientras aux ≠ nulo hacer
    si buscar(aux.getDato()) = nulo entonces
      retornar falso
    fin si
    aux ← aux.getSiguiente()
  fin mientras
  retornar verdadero
fin método
```

**Orden:** O(n × m) — donde n = tamaño de `this`, m = tamaño de `otro`. Cada `buscar` es O(n).

---

## union(otroConjunto)

**Lenguaje natural:** Retorna un nuevo conjunto con todos los elementos de ambos conjuntos, sin duplicados.

**Precondición:** `otroConjunto ≠ nulo`.  
**Postcondición:** retorna un Conjunto nuevo con la unión A ∪ B.

```
Conjunto.union(otro: Conjunto<T>): Conjunto<T>
  resultado ← nuevo Conjunto vacío
  // Agregar todos los elementos de this
  aux ← primero
  mientras aux ≠ nulo hacer
    resultado.insertar(aux.getDato())
    aux ← aux.getSiguiente()
  fin mientras
  // Agregar elementos de otro (insertar ignorará duplicados)
  aux ← otro.getPrimero()
  mientras aux ≠ nulo hacer
    resultado.insertar(aux.getDato())
    aux ← aux.getSiguiente()
  fin mientras
  retornar resultado
fin método
```

**Orden:** O(n × (n + m)) — cada inserción llama a `buscar` en el resultado, que crece hasta n+m.

---

## interseccion(otroConjunto)

**Lenguaje natural:** Retorna un nuevo conjunto con solo los elementos presentes en ambos conjuntos.

**Precondición:** `otroConjunto ≠ nulo`.  
**Postcondición:** retorna un Conjunto nuevo con la intersección A ∩ B.

```
Conjunto.interseccion(otro: Conjunto<T>): Conjunto<T>
  resultado ← nuevo Conjunto vacío
  aux ← primero
  mientras aux ≠ nulo hacer
    si otro.buscar(aux.getDato()) ≠ nulo entonces
      resultado.insertar(aux.getDato())
    fin si
    aux ← aux.getSiguiente()
  fin mientras
  retornar resultado
fin método
```

**Orden:** O(n × m) — para cada elemento de `this` (n), se busca en `otro` (O(m)).

---

## complemento(conjuntoUniversal)

**Lenguaje natural:** Retorna los elementos del conjunto universal que NO están en `this`.  
Es decir, U − A.

**Precondición:** `conjuntoUniversal ≠ nulo`.  
**Postcondición:** retorna un Conjunto nuevo = conjuntoUniversal − this.

```
Conjunto.complemento(universal: Conjunto<T>): Conjunto<T>
  resultado ← nuevo Conjunto vacío
  aux ← universal.getPrimero()
  mientras aux ≠ nulo hacer
    si buscar(aux.getDato()) = nulo entonces
      resultado.insertar(aux.getDato())
    fin si
    aux ← aux.getSiguiente()
  fin mientras
  retornar resultado
fin método
```

**Orden:** O(u × n) — para cada elemento del universal (u), se busca en `this` (O(n)).

---

## Resumen de Órdenes

| Operación | Orden |
|-----------|-------|
| `esVacio()` | O(1) |
| `buscar(dato)` | O(n) |
| `insertar(dato)` | O(n) — por el buscar previo |
| `eliminar(dato)` | O(n) |
| `esSubconjunto(otro)` | O(n × m) |
| `union(otro)` | O(n × (n + m)) |
| `interseccion(otro)` | O(n × m) |
| `complemento(universal)` | O(u × n) |
