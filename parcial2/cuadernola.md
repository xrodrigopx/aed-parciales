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
- [TDA Mapa — Patrones Java](#tda-mapa--patrones-java)
- [TDA Diccionario — Patrones Java](#tda-diccionario--patrones-java)
- [hashCode / equals — Contrato](#hashcode--equals--contrato)
- [Collections Framework](#collections-framework)

### Ordenamiento (aparece en todos los exámenes)
- [Elegir el algoritmo correcto — Sorting](#elegir-el-algoritmo-correcto--sorting)
- [Inserción](#inserción) — datos casi ordenados + memoria limitada → O(n) mejor caso
- [Heapsort](#heapsort) — peor caso garantizado + memoria limitada → O(n log n) siempre
- [Quicksort](#quicksort) — buen promedio en práctica → O(n log n) promedio

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
- [Todos los caminos posibles](#todos-los-caminos-posibles)
- [BEA — búsqueda en amplitud / número de saltos](#bea--búsqueda-en-amplitud--número-de-saltos)
- [Prim — árbol generador mínimo](#prim--árbol-generador-mínimo)
- [Kruskal — árbol generador mínimo](#kruskal--árbol-generador-mínimo)
- [Puntos de articulación](#puntos-de-articulación)
- [Variantes de Dijkstra para el parcial](#variantes-de-dijkstra-para-el-parcial)
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
  frecuencias.put(palabra, frecuencias.getOrDefault(palabra, 0) + 1)
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

**Estructura interna:**

```
NodoGenerico<T>:
  dato:  T
  hijos: List<NodoGenerico<T>>

ArbolGenerico<T>:
  raiz: NodoGenerico<T>  ← nulo si el árbol está vacío
```

*Implementación en Java:*

`NodoGenerico`
```java
public class NodoGenerico<T extends Comparable<T>> {
    private T dato;
    private List<NodoGenerico<T>> hijos;

    public NodoGenerico(T dato) {
        this.dato = dato;
        this.hijos = new ArrayList<>();
    }
}
```

`ArbolGenerico`
```java
public class ArbolGenerico<T extends Comparable<T>> {
    private NodoGenerico<T> raiz;

    public ArbolGenerico(T raiz) {
        this.raiz = new NodoGenerico<>(raiz);
    }
}
```

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

*Implementación en Java:*

`NodoGenerico`
```java
public boolean agregarHijo(T padre, T hijo) {
    if (this.dato.compareTo(padre) == 0) {
        for (NodoGenerico<T> h : hijos) {
            if (h.dato.compareTo(hijo) == 0) return false; // ya existe
        }
        hijos.add(new NodoGenerico<>(hijo));
        return true;
    }
    for (NodoGenerico<T> h : hijos) {
        if (h.agregarHijo(padre, hijo)) return true;
    }
    return false;
}
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

*Implementación en Java:*

`NodoGenerico`
```java
public NodoGenerico<T> buscarNodo(Comparable<T> criterio) {
    if (criterio.compareTo(this.dato) == 0) return this;
    for (NodoGenerico<T> hijo : hijos) {
        NodoGenerico<T> encontrado = hijo.buscarNodo(criterio);
        if (encontrado != null) return encontrado;
    }
    return null;
}
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

*Implementación en Java:*

`NodoGenerico`
```java
public void eliminar(Comparable<T> criterio) {
    int i = 0;
    while (i < hijos.size()) {
        if (criterio.compareTo(hijos.get(i).dato) == 0) {
            hijos.remove(i);
            return;
        }
        i++;
    }
    for (NodoGenerico<T> hijo : hijos) {
        hijo.eliminar(criterio);
    }
}
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

*Implementación en Java:*

`NodoGenerico`
```java
public NodoGenerico<T> obtenerPadreNodo(Comparable<T> criterio) {
    for (NodoGenerico<T> hijo : hijos) {
        if (criterio.compareTo(hijo.dato) == 0) return this;
    }
    for (NodoGenerico<T> hijo : hijos) {
        NodoGenerico<T> resultado = hijo.obtenerPadreNodo(criterio);
        if (resultado != null) return resultado;
    }
    return null;
}
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

*Implementación en Java:*

`NodoGenerico`
```java
public void preOrden(List<T> resultado) {
    resultado.add(this.dato);
    for (NodoGenerico<T> hijo : hijos) {
        hijo.preOrden(resultado);
    }
}

public void postOrden(List<T> resultado) {
    for (NodoGenerico<T> hijo : hijos) {
        hijo.postOrden(resultado);
    }
    resultado.add(this.dato);
}
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

*Implementación en Java:*

`NodoGenerico`
```java
public int altura() {
    if (hijos.isEmpty()) return 0;
    int max = 0;
    for (NodoGenerico<T> hijo : hijos) {
        int h = hijo.altura();
        if (h > max) max = h;
    }
    return max + 1;
}
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

---

## Ejercicios típicos de árbol genérico

Estos métodos aparecen frecuentemente en parciales con el árbol genealógico (Ej 16).

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
      preOrden(hijo, resultado)   ← agrega todos los del subárbol
  retornar resultado
```

*Implementación en Java:*

```java
public List<T> listarDescendientes(T nombre) {
    List<T> resultado = new ArrayList<>();
    NodoGenerico<T> nodo = raiz.buscarNodo(nombre);
    if (nodo == null) return resultado;
    for (NodoGenerico<T> hijo : nodo.getHijosNodos()) {
        hijo.preOrden(resultado);
    }
    return resultado;
}
```

---

### obtenerGeneracion(nivel)

**Lenguaje natural:** Recorre el árbol pasando el nivel actual como parámetro. Cuando el nivel actual coincide con el buscado, agrega el dato al resultado. No sigue bajando más allá del nivel buscado.

**Precondición:** nivel ≥ 0. Raíz está en nivel 0.
**Postcondición:** lista con todos los nodos en la generación indicada.

```
obtenerGeneracion(nivelBuscado: entero): Lista<T>
  resultado ← Lista vacía
  si raiz = nulo: retornar resultado
  obtenerGeneracionRec(raiz, 0, nivelBuscado, resultado)
  retornar resultado

obtenerGeneracionRec(nodo: NodoGenerico, nivelActual: entero, nivelBuscado: entero, resultado: Lista): void
  si nivelActual = nivelBuscado:
      resultado.agregar(nodo.dato)
      retornar
  para cada hijo en nodo.hijos:
      obtenerGeneracionRec(hijo, nivelActual + 1, nivelBuscado, resultado)
```

*Implementación en Java:*

```java
public List<T> obtenerGeneracion(int nivelBuscado) {
    List<T> resultado = new ArrayList<>();
    if (raiz == null) return resultado;
    obtenerGeneracionRec(raiz, 0, nivelBuscado, resultado);
    return resultado;
}

private void obtenerGeneracionRec(NodoGenerico<T> nodo, int nivelActual, int nivelBuscado, List<T> resultado) {
    if (nivelActual == nivelBuscado) {
        resultado.add(nodo.getDato());
        return;
    }
    for (NodoGenerico<T> hijo : nodo.getHijosNodos()) {
        obtenerGeneracionRec(hijo, nivelActual + 1, nivelBuscado, resultado);
    }
}
```

---

### esDescendiente(posibleDesc, ancestro)

**Lenguaje natural:** Busca el nodo ancestro y luego busca el posibleDescendiente dentro de su subárbol. Si se encuentra, es descendiente.

**Postcondición:** verdadero si posibleDesc está en el subárbol de ancestro (sin incluir al ancestro mismo).

```
esDescendiente(posibleDesc: T, ancestro: T): booleano
  nodoAnc ← buscarNodo(raiz, ancestro)
  si nodoAnc = nulo: retornar falso
  para cada hijo en nodoAnc.hijos:
      si buscarNodo(hijo, posibleDesc) ≠ nulo: retornar verdadero
  retornar falso
```

*Implementación en Java:*

```java
public boolean esDescendiente(T posibleDesc, T ancestro) {
    NodoGenerico<T> nodoAnc = raiz.buscarNodo(ancestro);
    if (nodoAnc == null) return false;
    for (NodoGenerico<T> hijo : nodoAnc.getHijosNodos()) {
        if (hijo.buscarNodo(posibleDesc) != null) return true;
    }
    return false;
}
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

obtenerCamino(nodo: NodoGenerico, objetivo: T): Lista<T>
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

*Implementación en Java:*

```java
public T ancestroComun(T a, T b) {
    List<T> caminoA = obtenerCamino(raiz, a);
    List<T> caminoB = obtenerCamino(raiz, b);
    if (caminoA == null) return null;
    if (caminoB == null) return null;
    T ancestro = null;
    int i = 0;
    while (i < caminoA.size() && i < caminoB.size()) {
        if (caminoA.get(i).equals(caminoB.get(i))) {
            ancestro = caminoA.get(i);
        }
        i++;
    }
    return ancestro;
}

private List<T> obtenerCamino(NodoGenerico<T> nodo, T objetivo) {
    if (nodo == null) return null;
    if (nodo.getDato().compareTo(objetivo) == 0) {
        List<T> camino = new ArrayList<>();
        camino.add(nodo.getDato());
        return camino;
    }
    for (NodoGenerico<T> hijo : nodo.getHijosNodos()) {
        List<T> camino = obtenerCamino(hijo, objetivo);
        if (camino != null) {
            camino.add(0, nodo.getDato()); // inserta al frente
            return camino;
        }
    }
    return null;
}
```

---

## Trie

**Casos de uso típicos:**
- Autocompletar palabras: buscar todas las palabras que empiezan con un prefijo dado.
- Verificar si una palabra existe en un diccionario.
- Predecir texto en teclados o buscadores.

**Estructura interna:**

```
NodoTrie<T>:
  hijos:    Map<Character, NodoTrie<T>>
  esPalabra: booleano   ← ¿termina aquí una palabra válida?
  dato:      T

Trie<T>:
  raiz: NodoTrie<T>  ← nulo si el trie está vacío
```

*Implementación en Java:*

`NodoTrie`
```java
public class NodoTrie<T> {
    private Map<Character, NodoTrie<T>> hijos;
    private boolean esPalabra;
    private T dato;

    public NodoTrie() {
        this.hijos = new HashMap<>();
        this.esPalabra = false;
        this.dato = null;
    }
}
```

---

### insertar(palabra, dato)

**Lenguaje natural:** Recorre el trie siguiendo cada carácter de la palabra. Si falta un nodo para algún carácter, lo crea. Al terminar la palabra, marca el nodo final como `esPalabra = verdadero`.

**Precondición:** palabra no nula.
**Postcondición:** la palabra existe en el trie marcada como `esPalabra = verdadero`. Retorna verdadero si fue nueva, falso si ya existía.

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

*Implementación en Java:*

`NodoTrie`
```java
public boolean insertar(String palabra, T dato) {
    NodoTrie<T> nodo = this;
    for (int i = 0; i < palabra.length(); i++) {
        char c = palabra.charAt(i);
        if (!nodo.hijos.containsKey(c)) {
            nodo.hijos.put(c, new NodoTrie<>());
        }
        nodo = nodo.hijos.get(c);
    }
    boolean esPalabraNueva = !nodo.esPalabra;
    nodo.esPalabra = true;
    nodo.dato = dato;
    return esPalabraNueva;
}
```

---

### buscar(palabra)

**Lenguaje natural:** Recorre el trie siguiendo cada carácter. Si en algún punto no existe el nodo hijo esperado, retorna nulo. Al terminar, retorna un Entry indicando si la palabra es completa (`esPalabra = verdadero`) o solo un prefijo (`esPalabra = falso`).

**Precondición:** palabra no nula.
**Postcondición:** retorna Entry con `esPalabra=verdadero` si es palabra completa; `esPalabra=falso` si es prefijo pero no palabra; `nulo` si no existe ningún nodo para esa secuencia.

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
buscar("cas"):
  c → a → s   →   nodo existe, esPalabra=false
  Retorna Entry(null, false, "cas")   ← es prefijo, no palabra completa

buscar("casa"):
  c → a → s → a  →  nodo existe, esPalabra=true
  Retorna Entry(dato, true, "casa")

buscar("col"):
  c → o   → nodo.hijos no contiene 'o'   → Retorna null
```

*Implementación en Java:*

`NodoTrie`
```java
public boolean buscar(String palabra) {
    NodoTrie<T> nodo = this;
    for (int i = 0; i < palabra.length(); i++) {
        char c = palabra.charAt(i);
        if (!nodo.hijos.containsKey(c)) return false;
        nodo = nodo.hijos.get(c);
    }
    return nodo.esPalabra;
}
```

---

### predecir(prefijo)

**Lenguaje natural:** Navega hasta el nodo correspondiente al último carácter del prefijo. Desde ahí hace un DFS sobre el subárbol, recolectando todas las palabras (nodos con `esPalabra = verdadero`).

**Precondición:** prefijo no nulo.
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

recolectarPalabras(nodo: NodoTrie, palabraActual: String, resultado: Lista): void
  si nodo.esPalabra:
      resultado.agregar(palabraActual)
  para cada (c, hijo) en nodo.hijos:
      recolectarPalabras(hijo, palabraActual + c, resultado)
```

**Orden:** O(m) + tamaño del resultado

*Implementación en Java:*

`NodoTrie`
```java
public List<String> predecir(String prefijo) {
    List<String> resultado = new ArrayList<>();
    NodoTrie<T> nodo = this;
    for (int i = 0; i < prefijo.length(); i++) {
        char c = prefijo.charAt(i);
        if (!nodo.hijos.containsKey(c)) return resultado;
        nodo = nodo.hijos.get(c);
    }
    recolectarPalabras(nodo, prefijo, resultado);
    return resultado;
}

private void recolectarPalabras(NodoTrie<T> nodo, String palabraActual, List<String> resultado) {
    if (nodo.esPalabra) {
        resultado.add(palabraActual);
    }
    for (Map.Entry<Character, NodoTrie<T>> entry : nodo.hijos.entrySet()) {
        char c = entry.getKey();
        NodoTrie<T> hijo = entry.getValue();
        recolectarPalabras(hijo, palabraActual + c, resultado);
    }
}
```

---

### eliminar(palabra)

**Lenguaje natural:** Navega hasta el nodo final de la palabra y marca `esPalabra = falso`. No elimina nodos que tengan hijos activos (solo desmarca), para no romper otras palabras que comparten ese prefijo.

**Precondición:** palabra no nula.
**Postcondición:** si la palabra existía, su nodo tiene `esPalabra = falso`. Retorna verdadero si se eliminó, falso si la palabra no existía.

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

*Implementación en Java:*

`NodoTrie`
```java
public boolean eliminar(String palabra) {
    NodoTrie<T> nodo = this;
    for (int i = 0; i < palabra.length(); i++) {
        char c = palabra.charAt(i);
        if (!nodo.hijos.containsKey(c)) return false;
        nodo = nodo.hijos.get(c);
    }
    if (!nodo.esPalabra) return false;
    nodo.esPalabra = false;
    nodo.dato = null;
    return true;
}
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
- Cuando la memoria contigua (sin punteros a listas externas) es preferible.
- Ejercicios de análisis de colisiones y factor de carga.

**Conceptos clave:**
- `h(K) = K.hashCode() % N` — N debe ser primo para mejor distribución
- **Colisión:** `h(Kᵢ) = h(Kⱼ)` con Kᵢ ≠ Kⱼ → prácticamente inevitable
- **Sondeo lineal:** `pos = (h0 + i) % N` — busca la siguiente posición libre
- **loteLibre (tombstone):** una posición eliminada se marca como `loteLibre = verdadero`, no como `null`. Esto distingue "nunca hubo nada aquí" (null) de "hubo algo pero se eliminó" (tombstone), lo cual es crítico para no romper la cadena de búsqueda
- **Factor de carga:** α = M/N — mantener < 0.70 para buen rendimiento

**Estructura interna:**

```
TNodoHash<K, V>:
  clave:     K
  valor:     V
  loteLibre: booleano   ← tombstone: fue eliminado pero no es null

Hash<K, V>:
  tabla:             TNodoHash<K, V>[]
  cantidadElementos: entero
```

---

### insertar(clave, valor)

**Lenguaje natural:** Calcula `h0`. Recorre linealmente buscando la primera posición vacía (null) o tombstone. Si la clave ya existe, no inserta. Si el factor de carga supera 0.70, redimensiona antes de insertar.

**Precondición:** clave no nula.
**Postcondición:** el par (clave, valor) queda en la tabla. Retorna verdadero si se insertó, falso si la clave ya existía.

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
          retornar falso   ← ya existe
      i ← i + 1
  si primerLibre = -1: retornar falso
  tabla[primerLibre] ← TNodoHash(clave, valor)
  cantidadElementos ← cantidadElementos + 1
  retornar verdadero
```

**Orden:** O(1) promedio, O(n) peor caso.

*Implementación en Java:*

```java
public boolean insertar(K clave, V valor) {
    if (clave == null) return false;
    double factorCarga = (double) cantidadElementos / tabla.length;
    if (factorCarga > 0.70) redimensionar();
    int h0 = funcionHash(clave);
    int primerLibre = -1;
    for (int i = 0; i < tabla.length; i++) {
        int pos = (h0 + i) % tabla.length;
        if (tabla[pos] == null) {
            if (primerLibre == -1) primerLibre = pos;
            break;
        }
        if (tabla[pos].isLoteLibre()) {
            if (primerLibre == -1) primerLibre = pos;
        } else if (tabla[pos].getClave().equals(clave)) {
            return false; // clave duplicada
        }
    }
    if (primerLibre == -1) return false;
    tabla[primerLibre] = new TNodoHash<>(clave, valor);
    cantidadElementos++;
    return true;
}
```

---

### buscar(clave)

**Lenguaje natural:** Calcula `h0` y recorre linealmente. Detiene la búsqueda si encuentra `null` (nunca hubo nada ahí). Salta posiciones con tombstone. Retorna el valor si encuentra la clave.

**Postcondición:** retorna el valor si la clave existe, nulo si no.

```
Hash.buscar(clave: K): V
  si clave = nulo: retornar nulo
  h0 ← funcionHash(clave)
  i ← 0
  mientras i < tabla.tamaño():
      pos ← (h0 + i) mod tabla.tamaño()
      si tabla[pos] = nulo: retornar nulo   ← nunca hubo nada, fin de la cadena
      si no tabla[pos].loteLibre:
          si tabla[pos].clave = clave: retornar tabla[pos].valor
      i ← i + 1
  retornar nulo
```

**Traza (tabla de tamaño 7, h(K) = K mod 7):**

```
Insertadas: 15→"A", 8→"B", 22→"C"
h(15)=1, h(8)=1 (colisión → pos 2), h(22)=1 (colisión → pos 3)

buscar(8):
  h0=1, pos=1 → tabla[1]={15,"A"} ≠ 8, continuar
  pos=2 → tabla[2]={8,"B"} = 8 → retornar "B"  ✓
```

*Implementación en Java:*

```java
public V buscar(K clave) {
    if (clave == null) return null;
    int h0 = funcionHash(clave);
    for (int i = 0; i < tabla.length; i++) {
        int pos = (h0 + i) % tabla.length;
        if (tabla[pos] == null) return null;
        if (!tabla[pos].isLoteLibre()) {
            if (tabla[pos].getClave().equals(clave)) return tabla[pos].getValor();
        }
    }
    return null;
}
```

---

### eliminar(clave)

**Lenguaje natural:** Igual que buscar, pero al encontrar la clave marca el nodo como `loteLibre = verdadero` (tombstone) en lugar de poner null. Esto preserva la cadena de búsqueda para otras claves que colisionaron.

**Por qué no se puede poner null:** si hubiéramos insertado A, B, C en posiciones 1, 2, 3 (por colisiones) y eliminamos B poniendo null en posición 2, buscar(C) se detendría en posición 2 y retornaría nulo sin llegar a C.

**Postcondición:** si la clave existía, su nodo queda marcado como loteLibre. Retorna verdadero si se eliminó.

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

*Implementación en Java:*

```java
public boolean eliminar(K clave) {
    if (clave == null) return false;
    int h0 = funcionHash(clave);
    for (int i = 0; i < tabla.length; i++) {
        int pos = (h0 + i) % tabla.length;
        if (tabla[pos] == null) return false;
        if (!tabla[pos].isLoteLibre()) {
            if (tabla[pos].getClave().equals(clave)) {
                tabla[pos].setLoteLibre(true);
                cantidadElementos--;
                return true;
            }
        }
    }
    return false;
}
```

---

### redimensionar()

**Lenguaje natural:** Crea una tabla nueva con capacidad primo ≥ 2×tabla_actual. Reinserta todos los elementos activos (que no son tombstone ni null).

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

**Por qué primo:** reduce la probabilidad de clustering primario (agrupamiento de claves en el mismo sector).

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
  tabla[pos].agregarAlFrente(clave, valor)   ← O(1)

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

---

## TDA Mapa — Patrones Java

**Definición:** almacena pares (k, v) con clave única. `put(k, v)` reemplaza si k ya existe.

### Contar frecuencias

```java
Map<String, Integer> frecuencias = new HashMap<>();
for (String palabra : palabras) {
    Integer actual = frecuencias.get(palabra);
    if (actual == null) {
        frecuencias.put(palabra, 1);
    } else {
        frecuencias.put(palabra, actual + 1);
    }
}
```

### Agrupar elementos por clave

```java
Map<String, List<String>> grupos = new HashMap<>();
for (String elemento : elementos) {
    String clave = calcularClave(elemento);
    List<String> lista = grupos.get(clave);
    if (lista == null) {
        lista = new ArrayList<>();
        grupos.put(clave, lista);
    }
    lista.add(elemento);
}
```

### Iterar un mapa

```java
for (Map.Entry<String, Integer> entry : mapa.entrySet()) {
    String clave = entry.getKey();
    Integer valor = entry.getValue();
    // usar clave y valor
}
```

### Buscar el máximo por valor

```java
String claveMax = null;
int valorMax = Integer.MIN_VALUE;
for (Map.Entry<String, Integer> entry : mapa.entrySet()) {
    if (entry.getValue() > valorMax) {
        valorMax = entry.getValue();
        claveMax = entry.getKey();
    }
}
```

### Implementaciones Java de Mapa

| Clase | Complejidad | Orden de iteración | Clave null |
|-------|------------|-------------------|-----------|
| `HashMap` | O(1) prom. | Sin orden | ✅ |
| `LinkedHashMap` | O(1) prom. | Orden de inserción | ✅ |
| `TreeMap` | O(log n) | Natural de claves | ❌ |

---

## TDA Diccionario — Patrones Java

**Definición:** como el Mapa, pero permite múltiples valores por clave. `insertar(k, v)` nunca reemplaza.

| TDA | Claves duplicadas | Método |
|-----|-----------------|--------|
| **Mapa** | No | `put(k, v)` — reemplaza |
| **Diccionario** | Sí | `insertar(k, v)` — siempre agrega |

### Implementación con Map<K, List<V>>

```java
Map<String, List<String>> diccionario = new HashMap<>();

// insertar
public void insertar(String clave, String valor) {
    List<String> lista = diccionario.get(clave);
    if (lista == null) {
        lista = new ArrayList<>();
        diccionario.put(clave, lista);
    }
    lista.add(valor);
}

// buscarTodos
public List<String> buscarTodos(String clave) {
    List<String> lista = diccionario.get(clave);
    if (lista == null) return new ArrayList<>();
    return lista;
}

// eliminar un valor específico (no todos los de la clave)
public boolean eliminar(String clave, String valor) {
    List<String> lista = diccionario.get(clave);
    if (lista == null) return false;
    return lista.remove(valor);
}
```

---

## hashCode / equals — Contrato

**Regla fundamental:** si `a.equals(b)` → `a.hashCode() == b.hashCode()` (no al revés).

**Error clásico en parcial:** si `equals` compara por `isbn` pero `hashCode` usa `titulo`, dos libros con mismo isbn pero diferente título tendrán hashCodes distintos → `HashSet` los trata como distintos → viola el contrato.

### hashCode estándar por tipo

| Tipo | hashCode() |
|------|-----------|
| `Integer` | El propio valor int |
| `String` | `s[0]*31^(n-1) + s[1]*31^(n-2) + ... + s[n-1]` |
| Objeto propio | `Objects.hash(attr1, attr2, ...)` |

### Un solo atributo identifica al objeto

```java
public class Libro {
    private String isbn;
    private String titulo;
    private int anio;

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof Libro)) return false;
        Libro otro = (Libro) o;
        return this.isbn.equals(otro.isbn);  // identidad lógica = isbn
    }

    @Override
    public int hashCode() {
        return isbn.hashCode();              // mismos atributos que equals
    }
}
```

### Varios atributos identifican al objeto

```java
@Override
public boolean equals(Object o) {
    if (o == null) return false;
    if (!(o instanceof Alumno)) return false;
    Alumno otro = (Alumno) o;
    boolean mismoId = this.id == otro.id;
    boolean mismoNombre = this.nombre.equals(otro.nombre);
    if (mismoId) {
        if (mismoNombre) return true;
    }
    return false;
}

@Override
public int hashCode() {
    return Objects.hash(id, nombre);
}
```

**Advertencia:** no usar atributos mutables (email, dirección) en `hashCode()`/`equals()`. Si la clave cambia mientras el objeto está en un HashMap, el objeto se "pierde" en la tabla.

---

## Collections Framework

### Pila (ArrayDeque como Stack)

```java
Deque<String> pila = new ArrayDeque<>();
pila.push("A");           // inserta al frente — O(1)
String tope = pila.pop(); // extrae del frente — O(1)
String ver  = pila.peek();// mira sin extraer  — O(1)
```

### Cola (ArrayDeque como Queue)

```java
Queue<String> cola = new ArrayDeque<>();
cola.offer("A");            // encola al final    — O(1)
String frente = cola.poll();// desencola al frente — O(1)
String ver    = cola.peek();// mira sin extraer    — O(1)
```

### Ordenar una lista

```java
List<Integer> lista = new ArrayList<>();
Collections.sort(lista);             // orden natural, estable
Collections.sort(lista, comparador); // con comparador

// Comparador manual (sin lambda)
Comparator<Alumno> porNombre = new Comparator<Alumno>() {
    public int compare(Alumno a, Alumno b) {
        return a.getNombre().compareTo(b.getNombre());
    }
};
Collections.sort(lista, porNombre);
```

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
| `ConcurrentHashMap` | ❌ | ✅ | ✅ | ❌ | ❌ | ✅ |

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

**Java:**
```java
public static void insercion(double[] datos, int n) {
    int i = 1;
    while (i < n) {
        double clave = datos[i];
        int j = i - 1;
        while (j >= 0) {
            if (datos[j] > clave) {
                datos[j + 1] = datos[j];
                j--;
            } else {
                break;
            }
        }
        datos[j + 1] = clave;
        i++;
    }
}
```

---

### Heapsort

**Cuándo:** se exige **O(n log n) en el peor caso** + sin memoria adicional. Mergesort también da O(n log n) garantizado pero necesita O(n) espacio → descartado en microcontroladores.

**Dos fases:** (1) construir max-heap en O(n), (2) extraer máximos sucesivamente en O(n log n).

```
heapsort(datos: double[], n: entero): void
  // Fase 1: construir max-heap
  i ← n / 2 - 1
  mientras i >= 0 hacer
    hundir(datos, i, n)
    i ← i - 1
  fin mientras
  // Fase 2: ordenar
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
→ **Prim** (crece desde un origen) o **Kruskal** (ordena aristas globalmente). Ambos producen el Árbol Generador Mínimo (AGM). O(V·E) naive / O(E log E) Kruskal.

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

**Lista de adyacencias en Java:**
```java
HashMap<V, List<Edge<V, D>>> adyacencias = new HashMap<>();
```

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

```java
Map<V, Integer> distancias = new HashMap<>();
Queue<V> cola = new ArrayDeque<>();
distancias.put(origen, 0);
cola.offer(origen);
while (!cola.isEmpty()) {
    V actual = cola.poll();
    for (Edge<V, D> arista : grafo.adyacencias(grafo.construirComparable(actual))) {
        V vecino = arista.target();
        if (!distancias.containsKey(vecino)) {
            distancias.put(vecino, distancias.get(actual) + 1);
            cola.offer(vecino);
        }
    }
}
// numBacon = distancias.get("Kevin_Bacon")
```

**Error típico:** usar DFS para calcular número de Bacon — DFS no garantiza el camino mínimo, puede devolver un número mayor al correcto.

---

### Prim — árbol generador mínimo

**Cuándo:** grafo **no dirigido** con pesos, conectar todos los vértices con menor costo total. Prim crece desde un origen eligiendo siempre la arista más barata que sale del árbol hacia fuera.

```
prim(G, origen):
  U ← {origen};  noU ← G.vertices() \ {origen}
  arbol ← grafo vacío con todos los vértices

  Mientras noU no vacío:
    minArista ← arista de menor peso con source en U y target en noU
    mover target de noU a U
    agregar minArista al arbol
  retornar arbol
```

**Java (esqueleto):**
```java
Set<V> U = new HashSet<>();
Set<V> noU = new HashSet<>(grafo.vertices());
U.add(origen);
noU.remove(origen);

while (!noU.isEmpty()) {
    Edge<V, D> minArista = searchMinEdge(grafo, U, noU);
    if (minArista == null) break;   // grafo no conexo
    U.add(minArista.target());
    noU.remove(minArista.target());
    arbol.agregarArista(minArista.source(), minArista.target(), minArista.dato());
}
```

**`searchMinEdge`:** recorre todos los vértices de U y sus adyacencias, devuelve la arista con `arista.dato().getWeight()` mínimo cuyo target está en noU.

**Error típico:** olvidar que el grafo resultado debe tener **todos** los vértices del original (no solo los que tienen aristas en el AGM).

---

### Kruskal — árbol generador mínimo

**Cuándo:** igual que Prim (grafo no dirigido con pesos). Kruskal ordena todas las aristas por peso y las agrega una a una, saltando las que formarían ciclo.

**Detectar ciclos con union-find de conjuntos:** cada vértice empieza en su propio grupo. Al aceptar una arista, se fusionan los dos grupos. Si source y target ya están en el mismo grupo → habría ciclo → rechazar.

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

**Ordenar sin lambdas (selection sort):**
```java
for (int i = 0; i < aristas.size(); i++) {
    int minIdx = i;
    for (int j = i + 1; j < aristas.size(); j++) {
        if (aristas.get(j).dato().getWeight() < aristas.get(minIdx).dato().getWeight()) {
            minIdx = j;
        }
    }
    Edge<V, D> temp = aristas.get(i);
    aristas.set(i, aristas.get(minIdx));
    aristas.set(minIdx, temp);
}
```

**Prim vs Kruskal:**

| | Prim | Kruskal |
|--|------|---------|
| Estrategia | Crece desde un vértice | Agrega aristas globalmente |
| Necesita origen | Sí | No |
| Mejor en grafos | Densos | Dispersos |
| Complejidad | O(V·E) naive | O(E log E) |

---

### Puntos de articulación

**Cuándo:** grafo **no dirigido**, encontrar los vértices cuya eliminación desconecta el grafo.

**Dos valores por vértice:**
- `disc[v]` — tiempo en que DFS descubrió v (orden de visita).
- `low[v]` — el menor `disc` alcanzable desde el subárbol de v usando aristas de retroceso (aristas que no son del árbol DFS).

**Reglas:**
1. `u` es punto de articulación si es **raíz del DFS** y tiene **≥ 2 hijos** en el árbol DFS.
2. `u` es punto de articulación si **no es raíz** y tiene algún hijo `v` con `low[v] >= disc[u]`.

```
dfsArticulacion(actual, disc, low, padres, visitados, tiempo, resultado):
  visitados.add(actual)
  tiempo++; disc[actual] = time; low[actual] = time
  hijosEnArbol ← 0

  Para cada vecino de actual:
    Si vecino no visitado:
      hijosEnArbol++
      padres[vecino] = actual
      dfsArticulacion(vecino, ...)
      low[actual] = min(low[actual], low[vecino])
      Si actual no es raíz Y low[vecino] >= disc[actual]:
          resultado.agregar(actual)
    Sino si vecino ≠ padre[actual]:
      low[actual] = min(low[actual], disc[vecino])   // arista de retroceso

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

**Error típico:** confundir la condición `>=` con `>`. Si `low[v] = disc[u]` exactamente, u sigue siendo punto de articulación porque el hijo v puede llegar exactamente hasta u (no puede subir más).

---

### Variantes de Dijkstra para el parcial

| Variante | Modificación clave |
|----------|-------------------|
| Aristas bloqueadas | Agregar condición `H[w,v] = verdadero` antes de relajar |
| Parada obligatoria en v | Ejecutar Dijkstra dos veces: origen→v, luego v→destino |
| Aristas con horario | Condición `H[hora][w,v]` en la relajación |

---

### Variantes de Floyd para el parcial

| Variante | Modificación clave |
|----------|-------------------|
| Contar caminos alternativos | Matriz Q: `Q[i,j]++` cuando `A[i,k]+A[k,j] = A[i,j]` |
| Nodos críticos | Ejecutar Floyd excluyendo cada nodo y comparar matrices |
| Arista que más impacta al eliminarse | Eliminar aristas una a una y comparar matrices |
