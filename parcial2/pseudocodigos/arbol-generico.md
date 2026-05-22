# Árbol Genérico — Pseudocódigos

## Definición

Un árbol T es un conjunto finito de uno o más nodos tal que:
- Existe un nodo especial llamado raíz(T)
- Los restantes nodos están distribuidos en m ≥ 0 subárboles disjuntos

El árbol binario es un caso particular con m ≤ 2.

## Terminología clave

| Término | Definición |
|---------|-----------|
| Grado | Cantidad de hijos directos de un nodo |
| Hoja | Nodo con grado cero |
| Nodo interno | Nodo que no es hoja |
| Nivel | Raíz = nivel 0; resto = nivel padre + 1 |
| Altura | Longitud del camino más largo desde el nodo hasta una hoja |

## Representación: Primer Hijo — Hermano Derecho

Convierte el árbol genérico en un árbol binario reutilizando dos punteros:
- puntero izquierdo → primer hijo
- puntero derecho → siguiente hermano

## Operaciones del TDA

```
Padre(unNodo)
HijoIzquierdo(unNodo)
HermanoDerecho(unNodo)
Etiqueta(unNodo)
Raiz
Anula
```

---

## agregarHijo

**Lenguaje natural:** Busca el nodo padre en el árbol. Si lo encuentra, le agrega un nuevo hijo con el dato indicado. No agrega si el hijo ya existe bajo ese padre.

**Precondición:** el árbol no es vacío, padre y hijo no son nulos.
**Postcondición:** el árbol tiene un nuevo nodo hijo del padre. Retorna verdadero si se agregó, falso si el padre no existe o el hijo ya estaba.

```
agregarHijo(padre: T, hijo: T): booleano
  si raiz = nulo: retornar falso
  si padre = nulo: retornar falso
  si hijo = nulo: retornar falso
  nodoPadre ← buscarNodo(raiz, padre)
  si nodoPadre = nulo: retornar falso
  para cada h en nodoPadre.hijos:
      si h.dato = hijo: retornar falso
  nodoPadre.hijos.agregar(NodoGenerico(hijo))
  retornar verdadero
```

---

## buscarNodo (auxiliar recursivo)

**Lenguaje natural:** Recorre el árbol en preorden buscando el nodo cuyo dato coincide con el criterio.

```
buscarNodo(nodo: NodoGenerico, criterio: T): NodoGenerico
  si nodo = nulo: retornar nulo
  si nodo.dato = criterio: retornar nodo
  para cada hijo en nodo.hijos:
      encontrado ← buscarNodo(hijo, criterio)
      si encontrado ≠ nulo: retornar encontrado
  retornar nulo
```

---

## eliminar

**Lenguaje natural:** Si el criterio es la raíz, vacía el árbol. Si no, busca el nodo que contiene ese criterio como hijo directo y lo elimina (junto con su subárbol completo).

**Precondición:** árbol no vacío, criterio no nulo.
**Postcondición:** el nodo con el criterio y todos sus descendientes son eliminados.

```
eliminar(criterio: T): void
  si raiz = nulo: retornar
  si criterio = nulo: retornar
  si raiz.dato = criterio:
      raiz ← nulo
      retornar
  eliminarEnSubarbol(raiz, criterio)

eliminarEnSubarbol(nodo: NodoGenerico, criterio: T): void
  i ← 0
  mientras i < nodo.hijos.tamaño():
      si nodo.hijos[i].dato = criterio:
          nodo.hijos.eliminar(i)
          retornar
      i ← i + 1
  para cada hijo en nodo.hijos:
      eliminarEnSubarbol(hijo, criterio)
```

---

## obtenerPadre

**Lenguaje natural:** Busca el nodo cuyo criterio coincide y retorna el dato de su nodo padre. La raíz no tiene padre.

```
obtenerPadre(criterio: T): T
  si raiz = nulo: retornar nulo
  si criterio = nulo: retornar nulo
  si raiz.dato = criterio: retornar nulo
  nodoPadre ← obtenerPadreNodo(raiz, criterio)
  si nodoPadre = nulo: retornar nulo
  retornar nodoPadre.dato

obtenerPadreNodo(nodo: NodoGenerico, criterio: T): NodoGenerico
  para cada hijo en nodo.hijos:
      si hijo.dato = criterio: retornar nodo
  para cada hijo en nodo.hijos:
      resultado ← obtenerPadreNodo(hijo, criterio)
      si resultado ≠ nulo: retornar resultado
  retornar nulo
```

---

## preOrden

**Lenguaje natural:** Visita el nodo actual primero, luego recorre cada subárbol de izquierda a derecha.

**Postcondición:** cada nodo es visitado exactamente una vez, raíz antes que sus descendientes.

```
preOrden(nodo: NodoGenerico): void
  visitar(nodo)
  para cada hijo en nodo.hijos:
      preOrden(hijo)
```

---

## postOrden

**Lenguaje natural:** Recorre todos los subárboles primero (de izquierda a derecha), luego visita el nodo actual.

**Postcondición:** cada nodo es visitado exactamente una vez, descendientes antes que su antecesor.

```
postOrden(nodo: NodoGenerico): void
  para cada hijo en nodo.hijos:
      postOrden(hijo)
  visitar(nodo)
```

---

## altura

**Lenguaje natural:** Si el nodo es hoja, su altura es 0. Si no, es 1 más la mayor altura entre todos sus hijos.

**Postcondición:** retorna la longitud del camino más largo desde el nodo hasta una hoja.

```
altura(nodo: NodoGenerico): entero
  si nodo.hijos está vacío: retornar 0
  max ← 0
  para cada hijo en nodo.hijos:
      h ← altura(hijo)
      si h > max:
          max ← h
  retornar max + 1
```

---

## grado

**Lenguaje natural:** Retorna la cantidad de hijos directos del nodo con ese criterio.

```
grado(criterio: T): entero
  si raiz = nulo: retornar 0
  nodo ← buscarNodo(raiz, criterio)
  si nodo = nulo: retornar 0
  retornar nodo.hijos.tamaño()
```
