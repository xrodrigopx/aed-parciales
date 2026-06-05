# Trie (Árbol de Prefijos) — Pseudocódigos

## Definición

Sea S un conjunto de strings del alfabeto σ donde ninguna es prefijo de otra. Un trie para S es un árbol ordenado T donde:
- Cada nodo (excepto la raíz) tiene una etiqueta = un carácter de σ
- Los hijos de un nodo interno están ordenados alfabéticamente
- Hay un nodo externo por string: la concatenación raíz→nodo da la string

**Restricción importante:** ninguna string puede ser prefijo de otra.  
Solución: agregar carácter especial `*` al final de cada string si es necesario.

## Estructura del nodo

```
NodoTrie:
  esPalabra: booleano          ← ¿termina aquí una palabra válida?
  hijos: Mapa<Carácter, NodoTrie>
  dato: T                      ← dato asociado a la palabra (si esPalabra)
```

## Complejidades

| Operación | Tiempo | Notas |
|-----------|--------|-------|
| Búsqueda de palabra | O(m) | m = largo de la palabra |
| Búsqueda de prefijo | O(m) + tamaño del resultado | |
| Inserción | O(m) | |
| Altura | largo de la string más larga | |

---

## insertar

**Lenguaje natural:** Recorre el trie siguiendo cada carácter de la palabra. Si falta un nodo para algún carácter, lo crea. Al terminar la palabra, marca el nodo final como `esPalabra = verdadero`.

**Precondición:** palabra no nula, trie inicializado.
**Postcondición:** la palabra existe en el trie con `esPalabra = verdadero`. Retorna verdadero si fue nueva, falso si ya existía.

```
insertar(palabra: String, dato: T): booleano
  si palabra = nulo: retornar falso
  si raiz = nulo: raiz ← NodoTrie()
  nodo ← raiz
  para cada carácter c en palabra:
      si nodo.hijos no contiene c:
          nodo.hijos.poner(c, NodoTrie())
      nodo ← nodo.hijos.obtener(c)
  esPalabra ← nodo.esPalabra
  nodo.esPalabra ← verdadero
  nodo.dato ← dato
  retornar no esPalabra   ← verdadero si era nueva
```

---

## buscar

**Lenguaje natural:** Recorre el trie siguiendo cada carácter de la palabra. Si en algún punto no existe el nodo hijo esperado, la palabra no está. Al terminar, retorna un Entry con `esPalabra` según si la palabra es completa o solo un prefijo.

**Precondición:** palabra no nula.
**Postcondición:** retorna Entry con `esPalabra=verdadero` si es palabra completa; `esPalabra=falso` si es prefijo; `nulo` si no existe ningún nodo.

```
buscar(palabra: String): Entry
  si raiz = nulo: retornar nulo
  si palabra = nulo: retornar nulo
  nodo ← raiz
  para cada carácter c en palabra:
      si nodo.hijos no contiene c: retornar nulo
      nodo ← nodo.hijos.obtener(c)
  retornar Entry(nodo.dato, nodo.esPalabra, palabra)
```

---

## predecir (búsqueda de prefijo)

**Lenguaje natural:** Navega hasta el nodo correspondiente al último carácter del prefijo. Desde ahí, hace un DFS recolectando todas las palabras del subárbol.

**Precondición:** prefijo no nulo.
**Postcondición:** retorna lista con todos los Entry cuya palabra comienza con el prefijo. Lista vacía si no hay coincidencias.

```
predecir(prefijo: String): Lista<Entry>
  resultado ← Lista vacía
  si raiz = nulo: retornar resultado
  si prefijo = nulo: retornar resultado
  nodo ← raiz
  para cada carácter c en prefijo:
      si nodo.hijos no contiene c: retornar resultado
      nodo ← nodo.hijos.obtener(c)
  recolectarPalabras(nodo, prefijo, resultado)
  retornar resultado

recolectarPalabras(nodo: NodoTrie, palabraActual: String, resultado: Lista): void
  si nodo.esPalabra:
      resultado.agregar(Entry(nodo.dato, verdadero, palabraActual))
  para cada (carácter c, hijo nodoHijo) en nodo.hijos:
      recolectarPalabras(nodoHijo, palabraActual + c, resultado)
```

---

## eliminar

**Lenguaje natural:** Navega hasta el nodo final de la palabra y marca `esPalabra = falso`. No elimina nodos que tengan hijos activos (solo desmarca).

**Precondición:** palabra no nula.
**Postcondición:** si la palabra existía, su nodo tiene `esPalabra = falso`. Retorna verdadero si se eliminó, falso si la palabra no existía.

```
eliminar(palabra: String): booleano
  si raiz = nulo: retornar falso
  si palabra = nulo: retornar falso
  nodo ← raiz
  para cada carácter c en palabra:
      si nodo.hijos no contiene c: retornar falso
      nodo ← nodo.hijos.obtener(c)
  si no nodo.esPalabra: retornar falso
  nodo.esPalabra ← falso
  nodo.dato ← nulo
  retornar verdadero
```

---

## Representaciones del nodo

| Representación | Acceso | Espacio | Cuándo usar |
|----------------|--------|---------|-------------|
| Array de d referencias | O(1) | O(d) siempre | Alfabeto pequeño (ADN: 4, dígitos: 10) |
| Lista encadenada | O(grado) | O(grado real) | Trie disperso |
| `HashMap<Character, NodoTrie>` | O(1) promedio | O(grado real) | Uso general |
