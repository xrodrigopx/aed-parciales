# Cuadernola — AED Segundo Parcial

Material de estudio consolidado para el segundo parcial de Algoritmos y Estructuras de Datos — UCU.

---

## Índice

### Elegir la estructura correcta (UT3)
- [Preguntas clave — UT3](#preguntas-clave--ut3)
- [Tabla de decisión](#tabla-de-decisión)
- [Trie vs HashMap para búsqueda de strings](#trie-vs-hashmap-para-búsqueda-de-strings)
- [HashMap vs TreeMap](#hashmap-vs-treemap)
- [Cuando hay dos operaciones incompatibles](#cuando-hay-dos-operaciones-incompatibles)
- [Cómo redactar la justificación en el parcial](#cómo-redactar-la-justificación-en-el-parcial)

### Pseudocódigos de referencia (UT3)
- [Árbol Genérico](#árbol-genérico) — `agregarHijo`, `eliminar`, `buscar`, `obtenerPadre`, `preOrden`, `postOrden`, `altura`, `grado`
  - [Ejercicios típicos de árbol genérico](#ejercicios-típicos-de-árbol-genérico) — `listarDescendientes`, `obtenerGeneracion`, `esDescendiente`, `ancestroComun`
- [Trie](#trie) — `insertar`, `buscar`, `predecir`, `eliminar`
- [Patricia (Trie comprimido)](#patricia-trie-comprimido)
- [Hash — Open Addressing (Sondeo Lineal)](#hash--open-addressing-sondeo-lineal) — `insertar`, `buscar`, `eliminar`, `redimensionar`
- [Hash — Encadenamiento directo](#hash--encadenamiento-directo)
- [hashCode / equals — Contrato](#hashcode--equals--contrato)
- [Collections Framework](#collections-framework)

### Ordenamiento (aparece en todos los exámenes)
- [Elegir el algoritmo correcto — Sorting](#elegir-el-algoritmo-correcto--sorting)
- [Inserción](#inserción) — datos casi ordenados + memoria limitada → O(n) mejor caso
- [Heapsort](#heapsort) — peor caso garantizado + memoria limitada → O(n log n) siempre
- [Quicksort](#quicksort) — buen promedio en práctica → O(n log n) promedio, con traza `[88,44,77,33,99,22,66]`
- [Análisis de orden de tiempo por línea/bloque](#análisis-de-orden-de-tiempo-por-líneabloque) — formato exigido en múltiples exámenes

### Grafos (UT4)
- [Elegir el algoritmo correcto — UT4](#elegir-el-algoritmo-correcto--ut4)
- [Tabla de decisión — UT4](#tabla-de-decisión--ut4)
- [Representaciones de grafos](#representaciones-de-grafos)
- [Dijkstra — caminos mínimos desde un origen](#dijkstra--caminos-mínimos-desde-un-origen)
- [Floyd — caminos mínimos entre todos los pares](#floyd--caminos-mínimos-entre-todos-los-pares)
- [Warshall — cerradura transitiva](#warshall--cerradura-transitiva)
- [DFS — búsqueda en profundidad](#dfs--búsqueda-en-profundidad)
- [Clasificación topológica](#clasificación-topológica)
- [Excentricidad y centro del grafo](#excentricidad-y-centro-del-grafo)
- [Detección de ciclos](#detección-de-ciclos)
- [Todos los caminos posibles](#todos-los-caminos-posibles) — con variante de filtro por tipo de vértice (switches/trenes)
- [BEA — búsqueda en amplitud / número de saltos](#bea--búsqueda-en-amplitud--número-de-saltos)
- [Prim — árbol generador mínimo](#prim--árbol-generador-mínimo)
- [Kruskal — árbol generador mínimo](#kruskal--árbol-generador-mínimo)
- [Puntos de articulación](#puntos-de-articulación)
- [Variantes de Dijkstra para el parcial](#variantes-de-dijkstra-para-el-parcial) — incl. costo transformado (distancia/velocidad)
- [Variantes de Floyd para el parcial](#variantes-de-floyd-para-el-parcial)

---

## Cómo elegir la estructura de datos correcta (UT3)

---

### Preguntas clave — UT3

**1. ¿El problema involucra strings con búsqueda por prefijo o autocompletar?**
→ **Trie**. El Hash no sirve para prefijos (no hay forma eficiente de encontrar todas las claves que empiezan con "cas").

**2. ¿El problema pide búsqueda exacta en O(1) y no necesita orden ni prefijos?**
→ **HashMap**. Es la estructura de búsqueda más rápida en promedio para claves exactas.

**3. ¿El problema necesita iteración en orden de clave o range queries?**
→ **TreeMap**. HashMap no preserva orden; para recorrer claves ordenadas o buscar rangos se necesita un árbol rojo-negro.

**4. ¿El problema modela una jerarquía (familia, empresa, categorías, sistema de archivos)?**
→ **Árbol Genérico**. Los árboles binarios no modelan bien nodos con número variable de hijos.

**5. ¿Puede haber múltiples valores para la misma clave?**
→ **Diccionario**: `Map<K, List<V>>`. El Mapa rechaza duplicados; el Diccionario los acepta todos.

---

### Tabla de decisión

| Si el problema requiere… | Estructura | Complejidad clave |
|--------------------------|------------|-------------------|
| Búsqueda exacta de string/clave, sin orden | **HashMap** | O(1) prom. |
| Búsqueda por prefijo, autocompletar | **Trie** | O(m) |
| Iteración ordenada por clave, range queries | **TreeMap** | O(log n) |
| Modelar jerarquía (hijos variables) | **Árbol Genérico** | O(n) buscar |
| Contar colisiones, comparar estrategias de hash | **Hash a mano** (open addr. o chaining) | — |
| Múltiples valores por clave | **Map<K, List<V>>** | O(1) prom. |

---

### Trie vs HashMap para búsqueda de strings

| Capacidad | Trie | HashMap |
|-----------|------|---------|
| Búsqueda exacta | O(m) | O(1) prom. |
| Búsqueda por prefijo | O(m) + tamaño resultado | ❌ no eficiente |
| Orden alfabético | Sí (DFS) | ❌ sin orden |
| Memoria | O(total de chars) | O(n × largo promedio) |

**Regla:** si el enunciado menciona "autocompletar", "palabras que empiezan con", "predecir" → Trie. Si solo necesita "buscar si existe" → HashMap.

---

### HashMap vs TreeMap

| Necesidad | Ganador |
|-----------|---------|
| Velocidad pura (conteo, frecuencia) | HashMap — O(1) |
| Iteración en orden lexicográfico | TreeMap — O(n log n) total |
| Range queries ("claves entre X e Y") | TreeMap — navega eficientemente |
| LRU Cache / orden de inserción | LinkedHashMap |
| Clave puede ser null | Solo HashMap (TreeMap lanza NPE) |

---

### Cuando hay dos operaciones incompatibles

Cuando el enunciado tiene dos operaciones que favorecen estructuras distintas, se mantienen **dos estructuras en paralelo**.

**Ejemplo:**
- Buscar palabras por prefijo → Trie
- Contar frecuencia de cada palabra → HashMap

```
al insertar una palabra:
  trie.insertar(palabra, dato)
  frecuencias.poner(palabra, frecuencias.obtener(palabra) + 1)
```

**Costo:** inserción doble, doble de memoria. **Beneficio:** ambas operaciones críticas en su complejidad óptima.

---

### Cómo redactar la justificación en el parcial

**Estructura esperada:**
1. Nombrar la operación crítica del problema.
2. Comparar al menos dos opciones y descartar la inferior con argumento de complejidad.
3. Concluir con el orden de tiempo como argumento.

**Ejemplo (problema de autocompletar):**

> "Se elige **Trie** porque la operación crítica es la búsqueda por prefijo. Un HashMap permite búsqueda exacta en O(1) pero no puede listar eficientemente todas las claves que comienzan con un prefijo dado sin recorrer toda la tabla (O(n)). El Trie resuelve la búsqueda por prefijo en O(m + k) donde m es el largo del prefijo y k el tamaño del resultado, sin necesidad de recorrer claves irrelevantes."

**Ejemplo (problema de frecuencia de palabras con iteración ordenada):**

> "Se elige **TreeMap** porque además de contar frecuencias se requiere iterar las palabras en orden alfabético. Un HashMap daría O(1) para el conteo pero no garantiza orden en la iteración, requiriendo un paso extra de ordenamiento O(n log n). El TreeMap da O(log n) por inserción y el recorrido en orden es gratuito."

---

## Pseudocódigos de referencia

---

## Árbol Genérico

**Casos de uso típicos:**
- Modelar jerarquías donde cada nodo puede tener cualquier número de hijos: árboles genealógicos, organigramas, sistemas de archivos, categorías de productos.
- El árbol binario es un caso particular de árbol genérico con máximo 2 hijos.

**Terminología:**

| Término | Definición |
|---------|-----------|
| Grado | Cantidad de hijos directos de un nodo |
| Hoja | Nodo con grado 0 |
| Nodo interno | Nodo que no es hoja |
| Nivel | Raíz = nivel 0; resto = nivel padre + 1 |
| Altura | Longitud del camino más largo desde el nodo hasta una hoja |

---

### agregarHijo(padre, hijo)

**Lenguaje natural:** Busca el nodo padre en el árbol. Si lo encuentra, le agrega un nuevo hijo con el dato indicado. No agrega si el hijo ya existe bajo ese padre.

**Precondición:** padre y hijo no son nulos.
**Postcondición:** el árbol tiene un nuevo nodo hijo del padre. Retorna verdadero si se agregó, falso si el padre no existe o el hijo ya estaba.

```
ArbolGenerico.agregarHijo(padre: T, hijo: T): booleano
  si raiz = nulo: retornar falso
  si padre = nulo: retornar falso
  si hijo = nulo: retornar falso
  nodoPadre ← buscarNodo(raiz, padre)
  si nodoPadre = nulo: retornar falso
  retornar nodoPadre.agregarHijo(padre, hijo)

NodoGenerico.agregarHijo(padre: T, hijo: T): booleano
  si this.dato = padre:
      para cada h en this.hijos:
          si h.dato = hijo: retornar falso
      this.hijos.agregar(NodoGenerico(hijo))
      retornar verdadero
  para cada h en this.hijos:
      si h.agregarHijo(padre, hijo): retornar verdadero
  retornar falso
```

**Orden:** O(n)

**Ejemplo (árbol: Abuelo → {Padre → {Hijo1, Hijo2}, Tío}):**
```
agregarHijo("Padre", "Hijo3") → agrega, retorna verdadero
agregarHijo("Padre", "Hijo1") → Hijo1 ya existe bajo Padre → retorna falso
agregarHijo("X", "Hijo3")    → "X" no está en el árbol → retorna falso
```

---

### buscarNodo (auxiliar recursivo)

**Lenguaje natural:** Recorre el árbol en preorden. Si el dato del nodo actual coincide con el criterio, lo retorna. Si no, busca en cada hijo.

**Precondición:** criterio no nulo.
**Postcondición:** retorna el nodo cuyo dato coincide con el criterio, o nulo si no existe.

```
buscarNodo(nodo: NodoGenerico, criterio: T): NodoGenerico
  si nodo.dato = criterio: retornar nodo
  para cada hijo en nodo.hijos:
      encontrado ← buscarNodo(hijo, criterio)
      si encontrado ≠ nulo: retornar encontrado
  retornar nulo
```

**Orden:** O(n)

**Ejemplo:**
```
buscarNodo(raiz, "Hijo1") → Abuelo≠ → busca en hijos → Padre≠ → busca en hijos → Hijo1= → retorna nodo Hijo1
buscarNodo(raiz, "X")     → recorre todos los nodos → retorna nulo
```

---

### eliminar(criterio)

**Lenguaje natural:** Si el criterio es la raíz, vacía el árbol. Si no, busca el nodo que contiene ese criterio como hijo directo y lo elimina junto con su subárbol completo.

**Precondición:** árbol no vacío, criterio no nulo.
**Postcondición:** el nodo con el criterio y todos sus descendientes son eliminados.

```
ArbolGenerico.eliminar(criterio: T): void
  si raiz = nulo: retornar
  si raiz.dato = criterio:
      raiz ← nulo
      retornar
  raiz.eliminar(criterio)

NodoGenerico.eliminar(criterio: T): void
  i ← 0
  mientras i < hijos.tamaño():
      si hijos[i].dato = criterio:
          hijos.eliminar(i)
          retornar
      i ← i + 1
  para cada hijo en hijos:
      hijo.eliminar(criterio)
```

**Orden:** O(n)

**Ejemplo:**
```
Árbol: Abuelo → {Padre → {Hijo1, Hijo2}, Tío}
eliminar("Padre") → Abuelo busca hijo con dato "Padre" → encontrado en pos 0 → eliminar
resultado: Abuelo → {Tío}   (Padre, Hijo1, Hijo2 eliminados junto con su subárbol)
eliminar("Abuelo") → es la raíz → raiz ← nulo
```

---

### obtenerPadre(criterio)

**Lenguaje natural:** Busca el nodo cuyo criterio coincide y retorna el dato de su nodo padre. La raíz no tiene padre.

**Postcondición:** retorna el dato del padre, o nulo si el criterio es la raíz o no existe.

```
ArbolGenerico.obtenerPadre(criterio: T): T
  si raiz = nulo: retornar nulo
  si raiz.dato = criterio: retornar nulo
  nodo ← raiz.obtenerPadreNodo(criterio)
  si nodo = nulo: retornar nulo
  retornar nodo.dato

NodoGenerico.obtenerPadreNodo(criterio: T): NodoGenerico
  para cada hijo en hijos:
      si hijo.dato = criterio: retornar this
  para cada hijo en hijos:
      resultado ← hijo.obtenerPadreNodo(criterio)
      si resultado ≠ nulo: retornar resultado
  retornar nulo
```

**Orden:** O(n)

**Ejemplo:**
```
Árbol: Abuelo → {Padre → {Hijo1, Hijo2}, Tío}
obtenerPadre("Hijo1")  → "Padre"
obtenerPadre("Padre")  → "Abuelo"
obtenerPadre("Abuelo") → nulo  (es la raíz, no tiene padre)
```

---

### preOrden / postOrden

**Lenguaje natural:** preOrden visita el nodo actual primero, luego cada hijo de izquierda a derecha. postOrden recorre todos los hijos primero, luego visita el nodo actual.

| Recorrido | Orden | Cuándo usarlo |
|-----------|-------|---------------|
| **Preorden** | Raíz → A₁ → A₂ → ... | Procesar padres antes que hijos (imprimir jerarquía) |
| **Postorden** | A₁ → A₂ → ... → Raíz | Calcular desde hojas hacia la raíz (tamaño, altura) |

```
preOrden(nodo: NodoGenerico, resultado: Lista): void
  resultado.agregar(nodo.dato)
  para cada hijo en nodo.hijos:
      preOrden(hijo, resultado)

postOrden(nodo: NodoGenerico, resultado: Lista): void
  para cada hijo en nodo.hijos:
      postOrden(hijo, resultado)
  resultado.agregar(nodo.dato)
```

**Orden:** O(n) ambos.

**Ejemplo (árbol: Abuelo → {Padre → {Hijo1, Hijo2}, Tío}):**
```
preOrden:  [Abuelo, Padre, Hijo1, Hijo2, Tío]
postOrden: [Hijo1, Hijo2, Padre, Tío, Abuelo]
```

---

### altura(criterio)

**Lenguaje natural:** Si el nodo es hoja, su altura es 0. Si no, es 1 más la mayor altura entre todos sus hijos.

**Postcondición:** retorna la longitud del camino más largo desde el nodo hasta una hoja.

```
NodoGenerico.altura(): entero
  si hijos está vacío: retornar 0
  max ← 0
  para cada hijo en hijos:
      h ← hijo.altura()
      si h > max: max ← h
  retornar max + 1
```

**Orden:** O(n)

**Ejemplo:**
```
Árbol: Abuelo → {Padre → {Hijo1, Hijo2}, Tío}
altura(Hijo1)  = 0   (hoja, sin hijos)
altura(Padre)  = 1   (1 + max{altura(Hijo1)=0, altura(Hijo2)=0})
altura(Abuelo) = 2   (1 + max{altura(Padre)=1, altura(Tío)=0})
```

---

### grado(criterio)

**Lenguaje natural:** Retorna la cantidad de hijos directos del nodo con ese criterio.

```
ArbolGenerico.grado(criterio: T): entero
  nodo ← buscarNodo(raiz, criterio)
  si nodo = nulo: retornar 0
  retornar nodo.hijos.tamaño()
```

**Orden:** O(n)

**Ejemplo:**
```
Árbol: Abuelo → {Padre → {Hijo1, Hijo2}, Tío}
grado("Abuelo") = 2   (hijos: Padre, Tío)
grado("Padre")  = 2   (hijos: Hijo1, Hijo2)
grado("Hijo1")  = 0   (es hoja)
```

---

## Ejercicios típicos de árbol genérico

Estos métodos aparecen frecuentemente en parciales con el árbol genealógico.

---

### listarDescendientes(nombre)

**Lenguaje natural:** Busca el nodo con ese nombre y retorna todos los nodos de su subárbol, excluyendo al propio nodo.

**Postcondición:** lista con todos los descendientes (no incluye al nodo con ese nombre).

```
listarDescendientes(nombre: T): Lista<T>
  resultado ← Lista vacía
  nodo ← buscarNodo(raiz, nombre)
  si nodo = nulo: retornar resultado
  para cada hijo en nodo.hijos:
      preOrden(hijo, resultado)
  retornar resultado
```

**Ejemplo:**
```
Árbol: Abuelo → {Padre → {Hijo1, Hijo2}, Tío}
listarDescendientes("Padre")  → [Hijo1, Hijo2]
listarDescendientes("Abuelo") → [Padre, Hijo1, Hijo2, Tío]
listarDescendientes("Hijo1")  → []   (hoja, sin descendientes)
```

---

### obtenerGeneracion(nivel)

**Lenguaje natural:** Recorre el árbol pasando el nivel actual como parámetro. Cuando el nivel actual coincide con el buscado, agrega el dato al resultado.

**Precondición:** nivel ≥ 0. Raíz está en nivel 0.
**Postcondición:** lista con todos los nodos en la generación indicada.

```
obtenerGeneracion(nivelBuscado: entero): Lista<T>
  resultado ← Lista vacía
  si raiz = nulo: retornar resultado
  obtenerGeneracionRec(raiz, 0, nivelBuscado, resultado)
  retornar resultado

obtenerGeneracionRec(nodo, nivelActual, nivelBuscado, resultado): void
  si nivelActual = nivelBuscado:
      resultado.agregar(nodo.dato)
      retornar
  para cada hijo en nodo.hijos:
      obtenerGeneracionRec(hijo, nivelActual + 1, nivelBuscado, resultado)
```

**Ejemplo:**
```
Árbol: Abuelo → {Padre → {Hijo1, Hijo2}, Tío}
obtenerGeneracion(0) → [Abuelo]
obtenerGeneracion(1) → [Padre, Tío]
obtenerGeneracion(2) → [Hijo1, Hijo2]
obtenerGeneracion(3) → []
```

---

### esDescendiente(posibleDesc, ancestro)

**Lenguaje natural:** Busca el nodo ancestro y luego busca el posibleDescendiente dentro de su subárbol.

**Postcondición:** verdadero si posibleDesc está en el subárbol de ancestro (sin incluir al ancestro mismo).

```
esDescendiente(posibleDesc: T, ancestro: T): booleano
  nodoAnc ← buscarNodo(raiz, ancestro)
  si nodoAnc = nulo: retornar falso
  para cada hijo en nodoAnc.hijos:
      si buscarNodo(hijo, posibleDesc) ≠ nulo: retornar verdadero
  retornar falso
```

**Ejemplo:**
```
Árbol: Abuelo → {Padre → {Hijo1, Hijo2}, Tío}
esDescendiente("Hijo1", "Abuelo") → verdadero  (Hijo1 está en subárbol de Abuelo)
esDescendiente("Tío", "Padre")    → falso       (Tío no está en subárbol de Padre)
esDescendiente("Abuelo", "Hijo1") → falso       (Abuelo es ancestro, no descendiente)
```

---

### ancestroComun(a, b)

**Lenguaje natural:** Obtiene el camino desde la raíz hasta cada nodo. El ancestro común más cercano es el último nodo que aparece en ambos caminos.

**Postcondición:** retorna el dato del ancestro común más cercano (LCA), o nulo si alguno no existe.

```
ancestroComun(a: T, b: T): T
  caminoA ← obtenerCamino(raiz, a)
  caminoB ← obtenerCamino(raiz, b)
  si caminoA = nulo: retornar nulo
  si caminoB = nulo: retornar nulo
  ancestro ← nulo
  i ← 0
  mientras i < caminoA.tamaño() y i < caminoB.tamaño():
      si caminoA[i] = caminoB[i]:
          ancestro ← caminoA[i]
      i ← i + 1
  retornar ancestro

obtenerCamino(nodo, objetivo: T): Lista<T>
  si nodo = nulo: retornar nulo
  si nodo.dato = objetivo:
      camino ← Lista vacía
      camino.agregar(nodo.dato)
      retornar camino
  para cada hijo en nodo.hijos:
      camino ← obtenerCamino(hijo, objetivo)
      si camino ≠ nulo:
          camino.insertarAlFrente(nodo.dato)
          retornar camino
  retornar nulo
```

**Ejemplo:**
```
Árbol: Abuelo → {Padre → {Hijo1, Hijo2}, Tío}
ancestroComun("Hijo1", "Tío"):
  caminoA = [Abuelo, Padre, Hijo1]
  caminoB = [Abuelo, Tío]
  [0]: Abuelo=Abuelo → ancestro=Abuelo; [1]: Padre≠Tío → fin
  retorna "Abuelo"

ancestroComun("Hijo1", "Hijo2") → retorna "Padre"
```

---

## Trie

**Casos de uso típicos:**
- Autocompletar palabras: buscar todas las palabras que empiezan con un prefijo dado.
- Verificar si una palabra existe en un diccionario.
- Predecir texto en teclados o buscadores.

---

### insertar(palabra, dato)

**Lenguaje natural:** Recorre el trie siguiendo cada carácter de la palabra. Si falta un nodo para algún carácter, lo crea. Al terminar la palabra, marca el nodo final como `esPalabra = verdadero`.

**Precondición:** palabra no nula.
**Postcondición:** la palabra existe en el trie. Retorna verdadero si fue nueva, falso si ya existía.

```
NodoTrie.insertar(palabra: String, dato: T): booleano
  nodo ← this
  para cada carácter c en palabra:
      si nodo.hijos no contiene c:
          nodo.hijos.poner(c, NodoTrie())
      nodo ← nodo.hijos.obtener(c)
  esPalabraNueva ← no nodo.esPalabra
  nodo.esPalabra ← verdadero
  nodo.dato ← dato
  retornar esPalabraNueva
```

**Orden:** O(m) donde m = largo de la palabra.

**Ejemplo:**
```
Trie vacío. insertar("car", dato):
  c → nodo nuevo; a → nodo nuevo; r → nodo nuevo, esPalabra=true → retorna verdadero
insertar("car", dato) de nuevo → nodo r ya tiene esPalabra=true → retorna falso
insertar("ca", dato):
  c → ya existe; a → ya existe; marcar esPalabra=true en 'a'
  (ahora "ca" y "car" coexisten compartiendo c→a)
```

---

### buscar(palabra)

**Lenguaje natural:** Recorre el trie siguiendo cada carácter. Si en algún punto no existe el nodo hijo esperado, retorna nulo.

**Postcondición:** retorna Entry con `esPalabra=verdadero` si es palabra completa; `esPalabra=falso` si es prefijo; `nulo` si no existe.

```
NodoTrie.buscar(palabra: String): Entry
  nodo ← this
  para cada carácter c en palabra:
      si nodo.hijos no contiene c: retornar nulo
      nodo ← nodo.hijos.obtener(c)
  retornar Entry(nodo.dato, nodo.esPalabra, palabra)
```

**Orden:** O(m)

**Traza (palabras insertadas: "casa", "caso", "cama"):**

```
buscar("cas"):  c → a → s → nodo existe, esPalabra=false → prefijo, no palabra
buscar("casa"): c → a → s → a → esPalabra=true → palabra completa
buscar("col"):  c → o → no existe hijo 'o' → retorna nulo
```

---

### predecir(prefijo)

**Lenguaje natural:** Navega hasta el nodo del último carácter del prefijo. Desde ahí hace DFS recolectando todos los nodos con `esPalabra = verdadero`.

**Postcondición:** lista con todos los strings que comienzan con el prefijo. Lista vacía si no hay coincidencias.

```
NodoTrie.predecir(prefijo: String): Lista<String>
  resultado ← Lista vacía
  nodo ← this
  para cada carácter c en prefijo:
      si nodo.hijos no contiene c: retornar resultado
      nodo ← nodo.hijos.obtener(c)
  recolectarPalabras(nodo, prefijo, resultado)
  retornar resultado

recolectarPalabras(nodo, palabraActual: String, resultado: Lista): void
  si nodo.esPalabra:
      resultado.agregar(palabraActual)
  para cada (c, hijo) en nodo.hijos:
      recolectarPalabras(hijo, palabraActual + c, resultado)
```

**Orden:** O(m) + tamaño del resultado

**Ejemplo:**
```
Trie con: "casa", "caso", "cama"
predecir("ca")  → navega c→a → recolecta todo el subárbol → ["casa", "caso", "cama"]
predecir("cas") → navega c→a→s → recolecta: ["casa", "caso"]
predecir("col") → 'o' no existe como hijo de 'c' → retorna []
```

---

### eliminar(palabra)

**Lenguaje natural:** Navega hasta el nodo final de la palabra y marca `esPalabra = falso`. No elimina nodos con hijos activos para no romper otras palabras que comparten el prefijo.

**Postcondición:** si existía, su nodo tiene `esPalabra = falso`. Retorna verdadero si se eliminó.

```
NodoTrie.eliminar(palabra: String): booleano
  nodo ← this
  para cada carácter c en palabra:
      si nodo.hijos no contiene c: retornar falso
      nodo ← nodo.hijos.obtener(c)
  si no nodo.esPalabra: retornar falso
  nodo.esPalabra ← falso
  nodo.dato ← nulo
  retornar verdadero
```

**Orden:** O(m)

**Ejemplo:**
```
Trie con: "casa", "caso"
eliminar("casa") → navega c→a→s→a, pone esPalabra=false → retorna verdadero
  (nodo 'a' final sigue existiendo porque "caso" comparte c→a→s)
eliminar("xyz")  → 'x' no existe → retorna falso
eliminar("casa") nuevamente → esPalabra ya es false → retorna falso
```

---

## Patricia (Trie comprimido)

**Motivación:** un trie estándar con largas cadenas de nodos de un solo hijo desperdicia espacio. Patricia comprime esas cadenas: las aristas llevan substrings completas en vez de un solo carácter.

**Propiedad clave:** todos los nodos internos tienen **al menos 2 hijos**. Si hay L hojas (= s strings) → máximo L−1 nodos internos → **tamaño total O(s)** en vez de O(n).

**Representación de aristas con tríadas (i, j, k):**
- **i:** índice de la string en el array S
- **j, k:** rango de índices dentro de S[i]
- La arista representa `S[i][j..k]`

```
S = ["bear", "bell", "bid"]
Arista (0, 0, 1) → "be"   (caracteres 0 a 1 de S[0] = "bear")
```

| Propiedad | Trie estándar | Patricia |
|-----------|--------------|----------|
| Nodos internos | O(n) | O(s) |
| Nodos con 1 hijo | Posibles | Ninguno |
| Complejidad de búsqueda | O(m) | O(m) |
| Implementación | Simple | Más compleja |

---

## Hash — Open Addressing (Sondeo Lineal)

**Casos de uso típicos:**
- Cuando se necesita O(1) para búsqueda, inserción y eliminación de pares clave-valor.
- Ejercicios de análisis de colisiones y factor de carga.

**Conceptos clave:**
- `h(K) = K.hashCode() % N` — N debe ser primo para mejor distribución
- **Colisión:** `h(Kᵢ) = h(Kⱼ)` con Kᵢ ≠ Kⱼ → prácticamente inevitable
- **Sondeo lineal:** `pos = (h0 + i) % N` — busca la siguiente posición libre
- **loteLibre (tombstone):** posición eliminada marcada como `loteLibre = verdadero`, no `null`. Distingue "nunca hubo nada" (null) de "hubo algo y se eliminó" (tombstone), crítico para no romper la cadena de búsqueda
- **Factor de carga:** α = M/N — mantener < 0.70 para buen rendimiento

---

### insertar(clave, valor)

**Lenguaje natural:** Calcula `h0`. Recorre linealmente buscando la primera posición vacía o tombstone. Si la clave ya existe, no inserta. Si el factor de carga supera 0.70, redimensiona antes.

**Precondición:** clave no nula.
**Postcondición:** el par queda en la tabla. Retorna verdadero si se insertó, falso si ya existía.

```
Hash.insertar(clave: K, valor: V): booleano
  si clave = nulo: retornar falso
  si factorCarga > 0.70: redimensionar()
  h0 ← funcionHash(clave)
  primerLibre ← -1
  i ← 0
  mientras i < tabla.tamaño():
      pos ← (h0 + i) mod tabla.tamaño()
      si tabla[pos] = nulo:
          si primerLibre = -1: primerLibre ← pos
          salir del bucle
      sino si tabla[pos].loteLibre:
          si primerLibre = -1: primerLibre ← pos
      sino si tabla[pos].clave = clave:
          retornar falso
      i ← i + 1
  si primerLibre = -1: retornar falso
  tabla[primerLibre] ← TNodoHash(clave, valor)
  cantidadElementos ← cantidadElementos + 1
  retornar verdadero
```

**Orden:** O(1) promedio, O(n) peor caso.

**Ejemplo (tabla tamaño 7, h(K) = K mod 7):**
```
insertar(8,  "X"): h=1 → pos 1 libre → tabla[1]={8,"X"}
insertar(15, "Y"): h=1 → pos 1 ocupada (8≠15) → pos 2 libre → tabla[2]={15,"Y"}
insertar(8,  "Z"): h=1 → pos 1: clave=8 ya existe → retorna falso (no reemplaza)
```

---

### buscar(clave)

**Lenguaje natural:** Calcula `h0` y recorre linealmente. Detiene si encuentra `null`. Salta tombstones. Retorna el valor si encuentra la clave.

**Postcondición:** retorna el valor si la clave existe, nulo si no.

```
Hash.buscar(clave: K): V
  si clave = nulo: retornar nulo
  h0 ← funcionHash(clave)
  i ← 0
  mientras i < tabla.tamaño():
      pos ← (h0 + i) mod tabla.tamaño()
      si tabla[pos] = nulo: retornar nulo
      si no tabla[pos].loteLibre:
          si tabla[pos].clave = clave: retornar tabla[pos].valor
      i ← i + 1
  retornar nulo
```

**Traza (tabla tamaño 7, h(K) = K mod 7):**

```
Insertadas: 15→"A", 8→"B", 22→"C"
h(15)=1, h(8)=1 (colisión → pos 2), h(22)=1 (colisión → pos 3)

buscar(8):
  h0=1, pos=1 → {15,"A"} ≠ 8
  pos=2 → {8,"B"} = 8 → retornar "B"  ✓
```

---

### eliminar(clave)

**Lenguaje natural:** Igual que buscar, pero al encontrar la clave marca el nodo como `loteLibre = verdadero` en lugar de poner null. Preserva la cadena de búsqueda.

**Por qué no se puede poner null:** si A, B, C colisionan en posiciones 1, 2, 3 y eliminamos B poniendo null, buscar(C) se detiene en posición 2 sin llegar a C.

```
Hash.eliminar(clave: K): booleano
  si clave = nulo: retornar falso
  h0 ← funcionHash(clave)
  i ← 0
  mientras i < tabla.tamaño():
      pos ← (h0 + i) mod tabla.tamaño()
      si tabla[pos] = nulo: retornar falso
      si no tabla[pos].loteLibre:
          si tabla[pos].clave = clave:
              tabla[pos].loteLibre ← verdadero
              cantidadElementos ← cantidadElementos - 1
              retornar verdadero
      i ← i + 1
  retornar falso
```

**Ejemplo:**
```
Estado: tabla[1]={8,"X"}, tabla[2]={15,"Y"}
eliminar(8):
  h=1 → pos 1: clave=8 → tabla[1].loteLibre=true   (tombstone, no null)
eliminar(15):
  h=1 → pos 1: loteLibre → saltar → pos 2: clave=15 → tabla[2].loteLibre=true
  (si pos 1 fuera null, buscar(15) se detendría ahí sin encontrar 15)
```

---

### redimensionar()

**Lenguaje natural:** Crea tabla nueva con capacidad primo ≥ 2×actual. Reinserta todos los elementos activos (no tombstone ni null).

```
Hash.redimensionar(): void
  tablaVieja ← tabla
  tabla ← nueva tabla de tamaño siguientePrimo(tablaVieja.tamaño() × 2)
  cantidadElementos ← 0
  para cada posición en tablaVieja:
      si posición ≠ nulo:
          si no posición.loteLibre:
              insertar(posición.clave, posición.valor)
```

**Por qué primo:** reduce la probabilidad de clustering primario.

**Ejemplo:**
```
Tabla tamaño 7 con 5 elementos activos → α = 5/7 ≈ 0.71 > 0.70 → redimensionar
siguientePrimo(14) = 17 → nueva tabla tamaño 17
Reinsertar solo los activos (no tombstones) con h(K) = K mod 17:
  {8,"X"} → h=8 → nueva[8]
  {15,"Y"} → h=15 → nueva[15]
  (tombstones se descartan — el redimensionamiento los limpia)
```

---

### Variantes de sondeo

| Variante | Fórmula | Problema |
|----------|---------|----------|
| **Lineal** | `(h0 + i) % N` | Clustering primario — se forman grupos |
| **Cuadrático** | `(h0 + i²) % N` | Clustering secundario (menor) |
| **Doble hashing** | `(h0 + i·c) % N`, c primo con N | Mínimo clustering |

---

## Hash — Encadenamiento directo

Cada posición de la tabla tiene una lista enlazada de elementos que colisionaron ahí.

**Ventaja:** eliminación trivial, funciona bien aunque α > 1.
**Desventaja:** punteros adicionales, peor localidad de memoria.

```
insertar_chaining(clave: K, valor: V): void
  pos ← h(clave)
  tabla[pos].agregarAlFrente(clave, valor)

buscar_chaining(clave: K): V
  pos ← h(clave)
  para cada nodo en tabla[pos]:
      si nodo.clave = clave: retornar nodo.valor
  retornar nulo

eliminar_chaining(clave: K): booleano
  pos ← h(clave)
  para cada nodo en tabla[pos]:
      si nodo.clave = clave:
          tabla[pos].eliminar(nodo)
          retornar verdadero
  retornar falso
```

**Complejidades:**

| Operación | Promedio | Peor caso |
|-----------|---------|-----------|
| Inserción | O(1) | O(1) |
| Búsqueda | O(1 + α) | O(n) |
| Eliminación | O(1 + α) | O(n) |

**Ejemplo (tabla tamaño 5, h(K) = K mod 5):**
```
insertar(3,"A"): h=3 → lista[3] = [(3,"A")]
insertar(8,"B"): h=3 → lista[3] = [(8,"B"),(3,"A")]   ← colisión, agregar al frente
insertar(1,"C"): h=1 → lista[1] = [(1,"C")]
buscar(8):   h=3 → recorrer lista[3]: 8≠3, 8=8 → retorna "B"
eliminar(3): h=3 → recorrer lista[3]: 3≠8, 3=3 → eliminar → lista[3]=[(8,"B")]
```

---

## hashCode / equals — Contrato

**Regla fundamental:** si `a.equals(b)` → `a.hashCode() == b.hashCode()` (no al revés).

**Error clásico en parcial:** si `equals` compara por `isbn` pero `hashCode` usa `titulo`, dos libros con mismo isbn pero diferente título tendrán hashCodes distintos → `HashSet` los trata como distintos → viola el contrato.

**Regla:** los mismos atributos usados en `equals` deben usarse en `hashCode`.

### hashCode estándar por tipo

| Tipo | hashCode() |
|------|-----------|
| `Integer` | El propio valor int |
| `String` | `s[0]*31^(n-1) + s[1]*31^(n-2) + ... + s[n-1]` |
| Objeto propio | combinar hashCodes de los atributos identificadores |

**Advertencia:** no usar atributos mutables en `hashCode()`/`equals()`. Si la clave cambia mientras el objeto está en un HashMap, el objeto se "pierde" en la tabla.

---

## Collections Framework

### Tabla comparativa de implementaciones

| Collection | Ordering | Random Access | Key-Value | Duplicados | Null | Thread-safe |
|------------|:--------:|:-------------:|:---------:|:----------:|:----:|:-----------:|
| `ArrayList` | ✅ | ✅ | ❌ | ✅ | ✅ | ❌ |
| `LinkedList` | ✅ | ❌ | ❌ | ✅ | ✅ | ❌ |
| `HashSet` | ❌ | ❌ | ❌ | ❌ | ✅ | ❌ |
| `TreeSet` | ✅ | ❌ | ❌ | ❌ | ❌ | ❌ |
| `HashMap` | ❌ | ✅ | ✅ | ❌ | ✅ | ❌ |
| `LinkedHashMap` | ✅ (inserción) | ✅ | ✅ | ❌ | ✅ | ❌ |
| `TreeMap` | ✅ (clave) | ✅ | ✅ | ❌ | ❌ | ❌ |

### Diagrama de decisión

```
¿Pares clave-valor?
├── Sí → ¿Orden importa?
│         ├── No            → HashMap
│         ├── Sí (inserción) → LinkedHashMap
│         └── Sí (clave)    → TreeMap
└── No → ¿Duplicados?
          ├── Sí → ArrayList (acceso por índice) / LinkedList (extremos)
          └── No → ¿Orden?
                    ├── No → HashSet
                    └── Sí → TreeSet
```

### Relación TDAs del curso ↔ Java API

| TDA del curso | Implementación Java recomendada |
|---------------|--------------------------------|
| `TDALista<T>` | `List<E>` → `ArrayList` / `LinkedList` |
| `TDAPila<T>` | `Deque<E>` → `ArrayDeque` (no `Stack`) |
| `TDACola<T>` | `Queue<E>` → `ArrayDeque` / `LinkedList` |
| `TDAMapa<K,V>` | `Map<K,V>` → `HashMap` / `TreeMap` |
| `TDADiccionario<K,V>` | `Map<K, List<V>>` |

---

## Métodos de búsqueda — Comparativa final

| Método | Estructura | Búsqueda puntual | Búsqueda por prefijo | Orden preservado | Eliminación |
|--------|-----------|-----------------|---------------------|-----------------|-------------|
| Comparación (ABB/AVL) | Árbol balanceado | O(log n) | O(log n + k) | Sí (inorden) | O(log n) |
| Digital (Trie) | Árbol de prefijos | O(m) | O(m + k) | Sí (alfabético) | O(m) |
| Transformación (Hash) | Tabla dispersa | **O(1) prom.** | No eficiente | No | O(1) chaining |

> k = tamaño del resultado; m = largo del patrón/palabra

---

## Ordenamiento

Aparece en **todos los exámenes del segundo parcial** como ejercicio autónomo. La clave es leer las señales del enunciado para elegir el algoritmo correcto.

---

### Elegir el algoritmo correcto — Sorting

| Señal en el enunciado | Algoritmo | Orden mejor | Orden peor | Espacio |
|----------------------|-----------|------------|------------|---------|
| "casi ordenados" + memoria limitada | **Inserción** | **O(n)** | O(n²) | O(1) |
| "rendimiento constante en el peor caso" + memoria limitada | **Heapsort** | O(n log n) | **O(n log n)** | O(1) |
| "pocas comparaciones en promedio" / general | **Quicksort** | O(n log n) | O(n²) | O(log n) |

**Regla de oro:** si menciona **peor caso garantizado** → Heapsort. Si menciona **casi ordenados** → Inserción. Si no dice nada especial → Quicksort.

---

### Inserción

**Cuándo:** datos **casi ordenados** + memoria **limitada** (microcontrolador, IoT).

**Por qué:** cuando los datos están casi ordenados, el while interno casi no ejecuta → O(n) efectivo.

```
insercion(datos: double[], n: entero): void
  i ← 1
  mientras i < n hacer
    clave ← datos[i]
    j ← i - 1
    mientras j >= 0 Y datos[j] > clave hacer
      datos[j + 1] ← datos[j]
      j ← j - 1
    fin mientras
    datos[j + 1] ← clave
    i ← i + 1
  fin mientras
fin método
```

**Variante descendente** (cambiar `>` por `<` en la comparación):
```
mientras j >= 0 Y datos[j].medicion < clave.medicion hacer
```

**Traza paso a paso (vector `[5, 2, 4, 1, 3]`):**

| i | clave | desplazamientos | resultado |
|---|-------|----------------|-----------|
| 1 | 2 | 5>2 → desplazar | [2, 5, 4, 1, 3] |
| 2 | 4 | 5>4 → desplazar; 2<4 → detener | [2, 4, 5, 1, 3] |
| 3 | 1 | 5>1, 4>1, 2>1 → desplazar todo | [1, 2, 4, 5, 3] |
| 4 | 3 | 5>3, 4>3 → desplazar; 2<3 → detener | [1, 2, 3, 4, 5] ✓ |

---

### Heapsort

**Cuándo:** se exige **O(n log n) en el peor caso** + sin memoria adicional. Mergesort también da O(n log n) garantizado pero necesita O(n) espacio → descartado en microcontroladores.

**Dos fases:** (1) construir max-heap en O(n), (2) extraer máximos sucesivamente en O(n log n).

```
heapsort(datos: double[], n: entero): void
  i ← n / 2 - 1
  mientras i >= 0 hacer
    hundir(datos, i, n)
    i ← i - 1
  fin mientras
  i ← n - 1
  mientras i > 0 hacer
    aux ← datos[0]
    datos[0] ← datos[i]
    datos[i] ← aux
    hundir(datos, 0, i)
    i ← i - 1
  fin mientras
fin método

hundir(datos: double[], i: entero, tamanio: entero): void
  izq ← 2 * i + 1
  der ← 2 * i + 2
  maximo ← i
  si izq < tamanio entonces
    si datos[izq] > datos[maximo] entonces maximo ← izq fin si
  fin si
  si der < tamanio entonces
    si datos[der] > datos[maximo] entonces maximo ← der fin si
  fin si
  si maximo ≠ i entonces
    aux ← datos[i]; datos[i] ← datos[maximo]; datos[maximo] ← aux
    hundir(datos, maximo, tamanio)
  fin si
fin método
```

**Propiedad max-heap en arreglo:** posición `i` → hijos en `2i+1` y `2i+2`. El máximo siempre está en posición 0.

**Ejemplo manual (examen 2024-2S):** vector inicial `[97,19,61,07,04,25,02,06]` ya es max-heap. Solo fase 2:

| swap | resultado después del swap y hundir |
|------|-------------------------------------|
| pos 0↔7 | → [61,19,25,07,04,06,02,**97**] |
| pos 0↔6 | → [25,19,06,07,04,02,**61**,97] |
| pos 0↔5 | → [19,07,06,02,04,**25**,61,97] |
| pos 0↔4 | → [07,04,06,02,**19**,25,61,97] |
| pos 0↔3 | → [06,04,02,**07**,19,25,61,97] |
| pos 0↔2 | → [04,02,**06**,07,19,25,61,97] |
| pos 0↔1 | → [**02,04**,06,07,19,25,61,97] ✓ |

---

### Quicksort

**Cuándo:** buen rendimiento **en promedio**, datos no casi ordenados, sin restricción extrema de memoria.

**Pivote:** último elemento del segmento. Particionar → colocar pivote en posición definitiva → recursión.

```
quicksort(datos: double[], inicio: entero, fin: entero): void
  si inicio < fin entonces
    posPivote ← particionar(datos, inicio, fin)
    quicksort(datos, inicio, posPivote - 1)
    quicksort(datos, posPivote + 1, fin)
  fin si
fin método

particionar(datos: double[], inicio: entero, fin: entero): entero
  pivote ← datos[fin]
  i ← inicio - 1
  j ← inicio
  mientras j < fin hacer
    si datos[j] <= pivote entonces
      i ← i + 1
      aux ← datos[i]; datos[i] ← datos[j]; datos[j] ← aux
    fin si
    j ← j + 1
  fin mientras
  aux ← datos[i + 1]; datos[i + 1] ← datos[fin]; datos[fin] ← aux
  retornar i + 1
fin método
```

**Llamada inicial:** `quicksort(datos, 0, n - 1)`

**Variante descendente** (examen 2023-2S): cambiar `datos[j] <= pivote` por `datos[j].medicion >= pivote.medicion`.

**Traza paso a paso (vector `[88, 44, 77, 33, 99, 22, 66]`, pivote = último elemento):**

| Llamada | Segmento | Pivote | Resultado tras particionar | Pos pivote |
|---------|----------|--------|--------------------------|------------|
| `quicksort(0,6)` | [88,44,77,33,99,22,66] | 66 | [44,33,22,**66**,99,77,88] | 3 |
| `quicksort(0,2)` | [44,33,22] | 22 | [**22**,33,44] | 0 |
| `quicksort(1,2)` | [33,44] | 44 | [33,**44**] | 2 |
| `quicksort(4,6)` | [99,77,88] | 88 | [77,**88**,99] | 5 |

Resultado final: `[22, 33, 44, 66, 77, 88, 99]`

**Cómo particionar a mano (paso a paso):**
1. `i = inicio - 1`. `pivote = datos[fin]`.
2. `j` recorre desde `inicio` hasta `fin-1`:
   - si `datos[j] <= pivote`: `i++`, intercambiar `datos[i]` ↔ `datos[j]`
3. Intercambiar `datos[i+1]` ↔ `datos[fin]` → el pivote queda en posición `i+1`, que se retorna.

**Ejemplo de particionar `[88, 44, 77, 33, 99, 22, 66]`, pivote=66:**
- `i=-1`, `j=0`: 88>66 → saltar
- `j=1`: 44≤66 → `i=0`, swap(0,1) → [44,88,77,33,99,22,66]
- `j=2`: 77>66 → saltar
- `j=3`: 33≤66 → `i=1`, swap(1,3) → [44,33,77,88,99,22,66]
- `j=4`: 99>66 → saltar
- `j=5`: 22≤66 → `i=2`, swap(2,5) → [44,33,22,88,99,77,66]
- swap(i+1,fin) = swap(3,6) → [44,33,22,**66**,99,77,88]

---

## Análisis de orden de tiempo por línea/bloque

Varios exámenes piden explícitamente: _"detallando por línea o bloque relevante, y concluyendo con el análisis global."_ Se espera este formato:

```
metodo(parametros):
  operacion simple          // O(1)
  bucle sobre V vértices:   // O(V) iteraciones
    operacion simple        // O(1) por iteración
  bucle sobre E aristas:    // O(E) iteraciones
    operacion simple        // O(1) por iteración
  // Total: O(V + E)
```

**Reglas para calcular el orden:**

| Patrón | Orden |
|--------|-------|
| Una asignación o comparación | O(1) |
| Bucle de n iteraciones con O(1) adentro | O(n) |
| Bucle anidado n × m con O(1) adentro | O(n·m) |
| Llamada recursiva que se divide en k subproblemas de n/2 | O(n log n) |
| BFS/DFS sobre grafo | O(V+E) |
| Floyd / Warshall (triple bucle sobre V) | O(V³) |
| Dijkstra naive (buscar mínimo iterando) | O(V²) |

**Ejemplo de análisis (Dijkstra):**

```
dijkstra(origen, G):
  inicializar D[v]=∞ para todo v    // O(V)
  D[origen] = 0                     // O(1)
  S ← {}                            // O(1)
  Mientras V ≠ S:                   // O(V) iteraciones
    w ← min en V-S                  //   O(V) — buscar mínimo iterando
    agregar w a S                   //   O(1)
    Para cada v adyacente a w:      //   O(grado(w)) ≤ O(V)
      relajar D[v]                  //     O(1)
// Total: O(V) × O(V) = O(V²)
```

**Ejemplo de análisis (BFS):**

```
bea(origen, G):
  inicializar visitados y cola      // O(V)
  mientras cola no vacía:           // O(V) iteraciones en total
    v ← desencolar                  //   O(1)
    para cada w adyacente a v:      //   O(E/V) promedio, O(E) en total
      si w no visitado: encolar     //     O(1)
// Total: O(V + E)
```

---

## Grafos (UT4)

---

### Elegir el algoritmo correcto — UT4

**1. ¿Caminos más cortos desde UN origen a todos los demás? (con pesos)**
→ **Dijkstra**. Técnica ávida. Solo funciona con pesos no negativos. O(V²).

**2. ¿Caminos más cortos entre TODOS los pares?**
→ **Floyd**. Triple bucle anidado. O(V³). Recupera caminos con matriz P.

**3. ¿Solo saber si existe camino entre cada par (sí/no)?**
→ **Warshall**. Igual que Floyd pero con booleanos: `A[i,j] = A[i,j] OR (A[i,k] AND A[k,j])`.

**4. ¿Recorrer sistemáticamente todos los vértices?**
→ **DFS**. Recursivo. O(V+E).

**5. ¿Ordenar vértices respetando dependencias (sin ciclos)?**
→ **Clasificación topológica**. DFS, insertar al frente en la salida recursiva.

**6. ¿Saber si el grafo tiene ciclos?**
→ **DFS con conjunto activo**: ciclo ↔ se encuentra un nodo que ya está en la recursión activa.

**7. ¿Centro del grafo?**
→ **Floyd** + excentricidad: max de cada columna = e(v). Centro = vértice con e mínima.

**8. ¿Distancia mínima en saltos (sin pesos) o número de Bacon?**
→ **BEA**. Cola FIFO. El primer camino encontrado a cada vértice es el más corto en cantidad de aristas. O(V+E).

**9. ¿Conectar todos los vértices de un grafo no dirigido con el menor costo total?**
→ **Prim** (crece desde un origen) o **Kruskal** (ordena aristas globalmente). Ambos producen el Árbol Generador Mínimo (AGM).

**10. ¿Qué vértice, si se elimina, desconecta la red?**
→ **Puntos de articulación**. DFS con disc/low. O(V+E).

---

### Tabla de decisión — UT4

| Problema | Algoritmo | Complejidad |
|----------|-----------|-------------|
| Camino mínimo desde 1 origen (con pesos) | Dijkstra | O(V²) |
| Caminos mínimos todos los pares | Floyd | O(V³) |
| Cerradura transitiva | Warshall | O(V³) |
| Recorrido sistemático | DFS | O(V+E) |
| Distancia mínima en saltos / número de Bacon | BEA | O(V+E) |
| Orden topológico | DFS (salida recursiva) | O(V+E) |
| Detección de ciclos | DFS con conjunto activo | O(V+E) |
| Centro del grafo | Floyd + excentricidad | O(V³) |
| Todos los caminos posibles | DFS + backtracking | exponencial |
| Árbol generador mínimo (AGM) | Prim / Kruskal | O(V·E) / O(E log E) |
| Nodo cuya eliminación desconecta la red | Puntos de articulación | O(V+E) |

---

### Representaciones de grafos

| Representación | Espacio | Buscar arista | Adyacentes de v | Cuándo usar |
|----------------|---------|---------------|-----------------|-------------|
| Matriz adyacencia | O(V²) | O(1) | O(V) | Grafos densos, Floyd/Warshall |
| Lista adyacencia | O(V+E) | O(grado) | O(grado) | Grafos dispersos, DFS |

---

### Dijkstra — caminos mínimos desde un origen

```
dijkstra(origen, C, V):
  D[origen] = 0; D[v] = ∞ para el resto
  P[v] = origen para todo v
  S ← {origen}

  Mientras V ≠ S:
    w ← vértice en V-S con D[w] mínimo
    Agregar w a S
    Para todo v en V-S:
      Si D[w] + C[w,v] < D[v]:
        D[v] = D[w] + C[w,v]
        P[v] = w
```

**Recuperar camino origen→t:** recorrer P desde t hacia atrás hasta el origen, luego invertir.

**Ejemplo (vértices 1–5, origen = 1):**

| Iteración | S | w | D[2] | D[3] | D[4] | D[5] |
|-----------|---|---|------|------|------|------|
| inicial | {1} | — | 10 | ∞ | 30 | 100 |
| 1 | {1,2} | 2 | 10 | 60 | 30 | 100 |
| 2 | {1,2,4} | 4 | 10 | 50 | 30 | 90 |
| 3 | {1,2,4,3} | 3 | 10 | 50 | 30 | 60 |
| 4 | {1,2,4,3,5} | 5 | 10 | 50 | 30 | 60 |

---

### Floyd — caminos mínimos entre todos los pares

```
floyd(C, n):
  A[i,j] = C[i,j] para i≠j;  A[i,i] = 0;  P[i,j] = 0

  Para k = 1..n:
    Para i = 1..n:
      Para j = 1..n:
        Si A[i,k] + A[k,j] < A[i,j]:
          A[i,j] = A[i,k] + A[k,j]
          P[i,j] = k
```

**Recuperar camino(i, j):**
```
camino(i, j):
  k = P[i,j]
  Si k = 0: el arco i→j es directo, no hay intermedio
  Sino: camino(i, k)  →  imprimir k  →  camino(k, j)
```

**Excentricidad de v:** máximo valor en la columna v de la matriz A final.
**Centro del grafo:** vértice con excentricidad mínima.

**Ejemplo (grafo: 1→2 peso 3, 2→3 peso 2, 1→3 peso 8):**
```
Inicial:        k=2 (pasar por 2):
  1  2  3       A[1,3]: 3+2=5 < 8 → A[1,3]=5, P[1,3]=2
1 0  3  8   →
2 ∞  0  2     Final:  1  2  3
3 ∞  ∞  0             1  0  3  5
                      2  ∞  0  2
                      3  ∞  ∞  0

Camino 1→3: P[1,3]=2 → 1→2→3, costo 5
Excentricidad de v2 = max(col 2) = max(3, 0, ∞) = ∞  (nadie puede llegar a 2 desde 3)
```

---

### Warshall — cerradura transitiva

```
warshall(C, n):
  A[i,j] = C[i,j]   // booleano: verdadero si hay arco directo

  Para k = 1..n:
    Para i = 1..n:
      Para j = 1..n:
        Si A[i,j] = falso:
          A[i,j] = A[i,k] AND A[k,j]
```

**Diferencia clave con Floyd:** Warshall responde "¿existe camino?". Floyd responde "¿cuánto cuesta el camino mínimo?".

**Ejemplo (grafo: 1→2, 2→3, 3→4 — cadena lineal):**
```
Inicial (booleano):          Tras k=2:          Tras k=3:          Final (k=4):
  1 2 3 4                    1 2 3 4            1 2 3 4            1 2 3 4
1 F T F F                  1 F T T F          1 F T T T          1 F T T T
2 F F T F        →         2 F F T F    →     2 F F T T    →     2 F F T T
3 F F F T                  3 F F F T          3 F F F T          3 F F F T
4 F F F F                  4 F F F F          4 F F F F          4 F F F F

Desde 1 se puede llegar a 2, 3 y 4. Desde 2 se puede llegar a 3 y 4.
```

**Cuándo NO usar Warshall:** si el enunciado solo pide saber si **un par específico (X, Y)** está conectado, usar BEA/DFS desde X y verificar si Y fue alcanzado → O(V+E). Warshall computa todos los V² pares en O(V³) — innecesario para una sola consulta. Firma típica del patrón: `Boolean conectados(Vertice X, Vertice Y)`.

```
conectados(X, Y, G):
  visitados ← conjunto vacío
  dfs(X, visitados, G)
  retornar Y en visitados

dfs(actual, visitados, G):
  agregar actual a visitados
  Para cada w adyacente a actual:
    Si w no en visitados:
      dfs(w, visitados, G)
```

**Orden:** O(V+E). Si el enunciado pide "optimizar memoria" → prefiere lista de adyacencias como representación.

---

### DFS — búsqueda en profundidad

```
main(G):
  visitados ← conjunto vacío
  Para cada v en G:
    Si v no en visitados: bpf(v, visitados)

bpf(v, visitados):
  agregar v a visitados
  Para cada w adyacente a v:
    Si w no en visitados: bpf(w, visitados)
```

**Tipos de arcos en DFS:**

| Tipo | Descripción | Implicación |
|------|-------------|-------------|
| Árbol | Lleva a vértice nuevo | Construye el árbol DFS |
| Retroceso | Va a un antecesor activo | Indica ciclo |
| Avance | Va a un descendiente ya terminado | Solo en dirigidos |
| Cruzado | Va a otro nodo sin relación ancestral | Solo en dirigidos |

**Ejemplo (grafo: A→B, A→C, B→D, C→D, D→E):**
```
DFS desde A:
  visitar A → ir a B → ir a D → ir a E
  E sin salida → retrocede a D → retrocede a B → retrocede a A → ir a C
  C → D ya visitado → retrocede
Orden de visita: A, B, D, E, C
Arcos: A-B árbol; B-D árbol; D-E árbol; A-C árbol; C-D cruzado
```

---

### Clasificación topológica

Solo válida en grafos acíclicos dirigidos (GDA).

```
clasificacionTopologica(G):
  visitados ← ∅
  lista ← []
  Para cada v en G:
    clasificacionTopologicaAux(v, visitados, lista)
  retornar lista

clasificacionTopologicaAux(nodo, visitados, lista):
  Si nodo no en visitados:
    agregar nodo a visitados
    Para cada w adyacente a nodo:
      clasificacionTopologicaAux(w, visitados, lista)
    agregar nodo AL PRINCIPIO de lista   ← en la salida recursiva
```

**Ejemplo (mismo grafo acíclico: A→B, A→C, B→D, C→D, D→E):**
```
DFS, insertar al frente al terminar:
  E termina → [E]
  D termina → [D, E]
  B termina → [B, D, E]
  C termina → [C, B, D, E]
  A termina → [A, C, B, D, E]

Orden topológico: A C B D E
Verificación: A→B ✓, A→C ✓, B→D ✓, C→D ✓, D→E ✓
```

---

### Excentricidad y centro del grafo

```
e(v) = max{ d(u, v) }  para todo u ∈ V
```

**Pasos:**
1. Aplicar Floyd → matriz A de distancias mínimas.
2. Para cada vértice v: `e(v) = max valor en la columna v`.
3. Centro = vértice con menor e(v).

---

### Detección de ciclos

```
tieneCiclos(G):
  enRecursion ← ∅
  Para cada v en G:
    Si tieneCiclosAux(v, enRecursion): retornar verdadero
  retornar falso

tieneCiclosAux(v, enRecursion):
  agregar v a enRecursion
  Para cada w adyacente a v:
    Si w en enRecursion: retornar verdadero   ← arco de retroceso → ciclo
    Si tieneCiclosAux(w, enRecursion): retornar verdadero
  remover v de enRecursion
  retornar falso
```

**Ejemplo:**
```
Con ciclo: A→B, B→C, C→A
  dfsAux(A): enRec={A} → dfsAux(B): enRec={A,B} → dfsAux(C): enRec={A,B,C}
    vecino A → A en enRec → retorna VERDADERO ✓

Sin ciclo: A→B, B→C
  dfsAux(A)→dfsAux(B)→dfsAux(C)→sin vecinos→remover C→remover B→remover A → falso ✓
```

---

### Todos los caminos posibles

```
todosLosCaminos(origen, destino, G):
  visitados ← ∅; camino ← []; resultado ← []
  todosLosCaminosAux(origen, destino, visitados, camino, resultado)
  retornar resultado

todosLosCaminosAux(actual, destino, visitados, camino, resultado):
  agregar actual a visitados
  camino.push(actual)
  Si actual = destino:
    resultado.agregar(copia de camino)
  Sino:
    Para cada w adyacente a actual:
      Si w no en visitados:
        todosLosCaminosAux(w, destino, visitados, camino, resultado)
  camino.pop()
  remover actual de visitados   ← backtracking: desmarcar para otros caminos
```

**Ejemplo (grafo: A→B, A→C, B→D, C→D. Caminos de A hasta D):**
```
todosLosCaminosAux(A,D):
  camino=[A], visitados={A}
  → explorar B: camino=[A,B], visitados={A,B}
    → explorar D: D=destino → resultado=[[A,B,D]]
    pop D, desmarcar D → camino=[A,B]
  pop B, desmarcar B → camino=[A]
  → explorar C: camino=[A,C], visitados={A,C}
    → explorar D: D=destino → resultado=[[A,B,D],[A,C,D]]
    pop D, desmarcar D → camino=[A,C]
  pop C, desmarcar C

Resultado: [[A,B,D], [A,C,D]]
```

---

#### Variante: filtrar por tipo de vértice

**Cuándo:** el enunciado restringe por qué nodos puede pasar el camino. Ejemplos: "solo a través de switches", "solo por estaciones de tren", "no pasar por nodos de procesamiento directamente". Apareció en **2024-1S examen 1 y examen 2**.

**Modificación:** agregar una condición de tipo en el momento de explorar vecinos. El destino siempre se puede visitar aunque sea del tipo "incorrecto" — solo los nodos intermedios tienen restricción.

```
todosLosCaminosAux(actual, destino, visitados, camino, resultado):
  agregar actual a visitados
  camino.push(actual)
  Si actual = destino:
    resultado.agregar(copia de camino)
  Sino:
    Para cada w adyacente a actual:
      Si w no en visitados:
        Si w = destino O w.tipo = SWITCH:   ← única línea que cambia
          todosLosCaminosAux(w, destino, visitados, camino, resultado)
  camino.pop()
  remover actual de visitados
```

**Error típico:** bloquear también el destino por tipo. Siempre hay que dejar pasar al destino aunque no sea del tipo "permitido".

---

### BEA — búsqueda en amplitud / número de saltos

**Cuándo:** distancia mínima en cantidad de aristas (sin pesos), recorrido por niveles, número de Bacon, grados de separación.

**Diferencia clave con DFS/Dijkstra:**
- DFS va profundo → no garantiza el camino más corto en saltos.
- Dijkstra minimiza costo → necesita pesos.
- BEA garantiza el camino más corto en **saltos** → ideal cuando todas las aristas "cuestan" 1.

```
bea(origen, G):
  visitados ← {}; cola ← []
  marcar origen como visitado; encolar origen
  Mientras cola no vacía:
    v ← desencolar
    Para cada w adyacente a v:
      Si w no visitado:
        marcar w como visitado; encolar w
```

**Para calcular distancias (número de saltos):**

```
bea con distancias(origen, G):
  distancias ← mapa vacío
  cola ← cola vacía
  distancias[origen] ← 0
  encolar origen
  Mientras cola no vacía:
    actual ← desencolar
    Para cada w adyacente a actual:
      Si w no en distancias:
          distancias[w] ← distancias[actual] + 1
          encolar w
```

**Error típico:** usar DFS para calcular número de Bacon — DFS no garantiza el camino mínimo.

**Ejemplo (grafo no dirigido: A-B, A-C, B-D, C-D, D-E. BEA con distancias desde A):**
```
cola=[A], distancias={A:0}
desencolar A → B(dist=1), C(dist=1) encolar → cola=[B,C]
desencolar B → D(dist=2) encolar → cola=[C,D]
desencolar C → D ya visitado → cola=[D]
desencolar D → E(dist=3) encolar → cola=[E]
desencolar E → sin nuevos → cola vacía

Distancias: A=0, B=1, C=1, D=2, E=3
```

---

### Prim — árbol generador mínimo

**Cuándo:** grafo **no dirigido** con pesos, conectar todos los vértices con menor costo total. Prim crece desde un origen eligiendo siempre la arista más barata que sale del árbol hacia fuera.

```
prim(G, origen):
  U ← {origen};  noU ← G.vertices() \ {origen}
  arbol ← grafo con todos los vértices, sin aristas

  Mientras noU no vacío:
    minArista ← arista de menor peso con source en U y target en noU
    Si minArista = nulo: detener   // grafo no conexo
    mover minArista.target() de noU a U
    agregar minArista al arbol
  retornar arbol

searchMinEdge(G, U, noU):
  minArista ← nulo
  minPeso ← ∞
  Para cada u en U:
    Para cada arista en G.adyacencias(u):
      Si arista.target() en noU Y arista.peso < minPeso:
          minPeso ← arista.peso
          minArista ← arista
  retornar minArista
```

**Error típico:** olvidar que el grafo resultado debe tener **todos** los vértices del original.

**Ejemplo (grafo: A-B(4), A-C(2), B-C(1), B-D(5), C-D(8). Desde A):**
```
U={A}, noU={B,C,D}
  aristas→noU: A-B(4), A-C(2) → mínima=A-C(2) → agregar, U={A,C}
U={A,C}, noU={B,D}
  aristas→noU: A-B(4), C-B(1), C-D(8) → mínima=C-B(1) → agregar, U={A,C,B}
U={A,C,B}, noU={D}
  aristas→noU: B-D(5), C-D(8) → mínima=B-D(5) → agregar, U={A,C,B,D}
AGM: {A-C(2), C-B(1), B-D(5)}, costo total = 8
```

---

### Kruskal — árbol generador mínimo

**Cuándo:** igual que Prim. Kruskal ordena todas las aristas por peso y las agrega una a una, saltando las que formarían ciclo.

**Detectar ciclos con union-find:** cada vértice empieza en su propio grupo. Al aceptar una arista, se fusionan los dos grupos. Si source y target ya están en el mismo grupo → ciclo → rechazar.

```
kruskal(G):
  arbol ← grafo con todos los vértices, sin aristas
  aristas ← todas las aristas de G ordenadas por peso ascendente
  grupos ← lista de conjuntos, uno por vértice

  Para cada arista (u, v, peso) en aristas:
    grupoU ← grupo que contiene u
    grupoV ← grupo que contiene v
    Si grupoU ≠ grupoV:
      arbol.agregarArista(u, v, peso)
      fusionar grupoU con grupoV
  retornar arbol
```

**Ordenar por peso (selection sort):**
```
Para i = 0 .. n-1:
  minIdx ← i
  Para j = i+1 .. n-1:
    Si aristas[j].peso < aristas[minIdx].peso:
        minIdx ← j
  intercambiar aristas[i] con aristas[minIdx]
```

**Prim vs Kruskal:**

| | Prim | Kruskal |
|--|------|---------|
| Estrategia | Crece desde un vértice | Agrega aristas globalmente |
| Necesita origen | Sí | No |
| Mejor en grafos | Densos | Dispersos |
| Complejidad | O(V·E) naive | O(E log E) |

**Ejemplo Kruskal (mismo grafo: A-B(4), A-C(2), B-C(1), B-D(5), C-D(8)):**
```
Aristas ordenadas: B-C(1), A-C(2), A-B(4), B-D(5), C-D(8)
Grupos: {A},{B},{C},{D}
  B-C(1): B≠C → agregar → {A},{B,C},{D}
  A-C(2): A≠BC → agregar → {A,B,C},{D}
  A-B(4): mismo grupo {A,B,C} → ciclo → rechazar
  B-D(5): ABC≠D → agregar → {A,B,C,D}
AGM: {B-C(1), A-C(2), B-D(5)}, costo total = 8  ✓ (igual que Prim)
```

---

### Puntos de articulación

**Cuándo:** grafo **no dirigido**, encontrar los vértices cuya eliminación desconecta el grafo.

**Dos valores por vértice:**
- `disc[v]` — tiempo en que DFS descubrió v (orden de visita).
- `low[v]` — el menor `disc` alcanzable desde el subárbol de v usando aristas de retroceso.

**Reglas:**
1. `u` es punto de articulación si es **raíz del DFS** y tiene **≥ 2 hijos** en el árbol DFS.
2. `u` es punto de articulación si **no es raíz** y tiene algún hijo `v` con `low[v] >= disc[u]`.

```
puntosDeArticulacion(G):
  disc, low, padres ← mapas vacíos
  visitados ← conjunto vacío
  tiempo ← 0
  resultado ← lista vacía
  Para cada v en G.vertices():
    Si v no en visitados:
      dfsArticulacion(G, v, disc, low, padres, visitados, tiempo, resultado)
  retornar resultado

dfsArticulacion(G, actual, disc, low, padres, visitados, tiempo, resultado):
  visitados.agregar(actual)
  tiempo ← tiempo + 1
  disc[actual] ← tiempo
  low[actual] ← tiempo
  hijosEnArbol ← 0

  Para cada vecino de actual:
    Si vecino no visitado:
      hijosEnArbol ← hijosEnArbol + 1
      padres[vecino] ← actual
      dfsArticulacion(G, vecino, ...)
      Si low[vecino] < low[actual]:
        low[actual] ← low[vecino]
      Si actual no es raíz Y low[vecino] >= disc[actual]:
          resultado.agregar(actual)
    Sino si vecino ≠ padres[actual]:
      Si disc[vecino] < low[actual]:
        low[actual] ← disc[vecino]   // arista de retroceso

  Si actual es raíz Y hijosEnArbol > 1:
      resultado.agregar(actual)
```

**Ejemplo (grafo lineal A-B-C):**
```
disc: A=1, B=2, C=3
low:  A=1, B=1, C=2

B no es raíz. Hijo C tiene low[C]=2 >= disc[B]=2 → B es punto de articulación ✓
A es raíz con 1 solo hijo B → NO es punto de articulación
```

**Error típico:** confundir `>=` con `>`. Si `low[v] = disc[u]` exactamente, u sigue siendo punto de articulación.

---

### Variantes de Dijkstra para el parcial

| Variante | Modificación clave |
|----------|-------------------|
| Aristas bloqueadas | Agregar condición `H[w,v] = verdadero` antes de relajar |
| Parada obligatoria en v | Ejecutar Dijkstra dos veces: origen→v, luego v→destino |
| Aristas con horario | Condición `H[hora][w,v]` en la relajación |
| Costo transformado por consulta | Calcular el peso al momento de relajar: `costo = C[w,v] / velocidad` |

**Dijkstra con costo transformado (examen 2025-1S):** el peso real no está guardado en el grafo — se calcula en el momento de relajar. Patrón: para cada camión con velocidad `vel`, ejecutar Dijkstra donde el costo de cada arista es `distancia / vel`.

```
dijkstraTiempo(origen, G, velocidad):
  D[origen] = 0; D[v] = ∞ para el resto
  S ← {}
  Mientras V ≠ S:
    w ← vértice en V-S con D[w] mínimo
    agregar w a S
    Para cada arista (w→v) con distancia dist en G:
      tiempoArista ← dist / velocidad          ← transformación del costo
      Si D[w] + tiempoArista < D[v]:
          D[v] ← D[w] + tiempoArista
          P[v] ← w
```

**Cómo presentarlo en el parcial:** describir la transformación en lenguaje natural ("el tiempo de viaje por cada tramo es la distancia dividida la velocidad del camión") y luego en la línea de relajación. El resto del algoritmo es Dijkstra estándar.

---

### Variantes de Floyd para el parcial

| Variante | Modificación clave |
|----------|-------------------|
| Contar caminos alternativos | Matriz Q: `Q[i,j]++` cuando `A[i,k]+A[k,j] = A[i,j]` |
| Nodos críticos | Ejecutar Floyd excluyendo cada nodo y comparar matrices |
| Arista que más impacta al eliminarse | Eliminar aristas una a una y comparar matrices |
