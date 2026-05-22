# Trie Comprimido — Patricia

**PATRICIA = Practical Algorithm to Retrieve Information Coded in Alphanumeric**

## Motivación

Un trie estándar puede tener largas cadenas de nodos con un solo hijo (caminos lineales sin ramificaciones) → desperdicio de espacio.

El Patricia trie **comprime esas cadenas**: en lugar de un nodo por carácter, las aristas llevan substrings completas.

## Propiedad clave

Todos los nodos internos tienen **al menos 2 hijos**.

Si hay L hojas (= s strings) → hay como máximo L−1 nodos internos → **tamaño total O(s)** en vez de O(n).

## Representación eficiente con tríadas (i, j, k)

Para no duplicar las substrings en memoria, las aristas se representan como tríadas:
- **i:** índice de la string en el array S
- **j, k:** rango de índices dentro de S[i]
- La arista representa la substring `S[i][j..k]`

```
S = ["bear", "bell", "bid", "bull", "buy", "sell", "stock", "stop"]

Arista (0, 0, 2) → "bea"  (caracteres 0 a 2 de S[0] = "bear")
```

Esto hace al Patricia trie ideal como **estructura de índice auxiliar**: la colección original ya está en memoria; el trie solo almacena referencias.

## Comparación Trie estándar vs Patricia

| Propiedad | Trie estándar | Patricia |
|-----------|--------------|----------|
| Nodos internos | O(n) | O(s) |
| Nodos con 1 hijo | Posibles | Ninguno |
| Complejidad de búsqueda | O(m) | O(m) |
| Implementación | Simple | Más compleja |

## Pseudocódigo de búsqueda

**Lenguaje natural:** La búsqueda en Patricia es igual que en trie estándar en términos de complejidad O(m), pero cada arista puede consumir varios caracteres de la palabra en un solo paso.

```
buscar(raiz: NodoPAT, patron: String): booleano
  nodo ← raiz
  posicion ← 0
  mientras nodo no es hoja:
      arista ← elegirHijo(nodo, patron, posicion)
      si arista = nulo: retornar falso
      longitud ← arista.k - arista.j + 1
      substring ← S[arista.i][arista.j .. arista.k]
      si patron[posicion .. posicion+longitud-1] ≠ substring:
          retornar falso
      posicion ← posicion + longitud
      nodo ← arista.destino
  retornar patron = S[nodo.indice]
```

## En Java

```java
// Apache Commons Collections
org.apache.commons.collections4.trie.PatriciaTrie<V>
```
