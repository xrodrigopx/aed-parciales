# AED — Material de Parciales

Repositorio de preparación para los parciales de **Algoritmos y Estructuras de Datos** — Universidad Católica del Uruguay, Ingeniería en Sistemas.

---

## Estructura

```
aed-parciales/
├── README.md
├── graphify-out/                ← grafo de conocimiento del repositorio
│   ├── graph.html               ← visualización interactiva (abrir en browser)
│   ├── graph.json               ← datos del grafo (GraphRAG-ready)
│   └── GRAPH_REPORT.md          ← reporte: comunidades, hubs, conexiones
├── parcial1/                    ← material completo del primer parcial
│   ├── cuadernola.md
│   ├── guia-de-estudio.md
│   ├── metodos_java.md
│   ├── parte1/                  ← pseudocódigo en papel
│   │   ├── letras/
│   │   ├── pseudocodigos/
│   │   └── soluciones/
│   └── parte2/                  ← programación Java
│       ├── letras/
│       ├── codigo-base/
│       └── soluciones/
├── parcial2/                    ← material del segundo parcial (UT3 + UT4 grafos)
│   ├── cuadernola.md
│   ├── guia-de-estudio.md
│   ├── metodos_java.md
│   ├── ut3-resumen-completo.md
│   ├── parte1/                  ← pseudocódigo en papel
│   │   ├── letras/
│   │   ├── pseudocodigos/
│   │   └── soluciones/
│   └── parte2/                  ← programación Java
│       ├── letras/
│       ├── codigo-base/
│       │   ├── ut03-01/         ← proyecto Maven UT3 (árboles genéricos, Trie, Hash)
│       │   └── ut04-01/         ← proyecto Maven UT4 (grafos dirigidos y no dirigidos)
│       └── soluciones/
└── examen_final/                ← material del examen final (Sorting)
    ├── parte1/                  ← pseudocódigo en papel
    │   ├── pseudocodigos/       ← ejercicio-sorting-insercion/heapsort/quicksort.md
    │   ├── soluciones/          ← sorting-insercion/heapsort/quicksort.md
    │   └── letras/
    └── parte2/                  ← programación Java
        └── codigo-base/
```

---

## Grafo de conocimiento

El repositorio incluye un grafo de conocimiento generado con [graphify](https://github.com/safishamsi/graphify) sobre todos los archivos del repo.

| Archivo | Descripción |
|---------|-------------|
| [`graphify-out/graph.html`](graphify-out/graph.html) | Visualización interactiva — abrir directamente en el browser, sin servidor |
| [`graphify-out/GRAPH_REPORT.md`](graphify-out/GRAPH_REPORT.md) | Reporte: comunidades detectadas, nodos hub, conexiones sorpresivas |
| [`graphify-out/graph.json`](graphify-out/graph.json) | Datos crudos del grafo (1539 nodos, 2698 aristas, 115 comunidades) |

El grafo conecta conceptos teóricos (pseudocódigos, TDAs), implementaciones Java, tests y enunciados de parciales anteriores. Útil para entender qué temas están relacionados y cómo.

### Si usás Claude Code

Podés agregar esto a tu `CLAUDE.md` local para que Claude use el grafo antes de leer archivos uno por uno, ahorrando tokens en preguntas sobre el repositorio:

```markdown
## Graphify — fast path para preguntas sobre el repositorio

Si `graphify-out/graph.json` existe en el directorio actual, usar `graphify query "<pregunta>"` como primera fuente antes de hacer reads masivos o greps cuando la pregunta es sobre relaciones entre componentes, qué implementa qué, o cómo están conectados dos conceptos. Solo ir directo a archivos cuando la pregunta requiere ver código concreto.

\`\`\`bash
graphify query "<pregunta>"        # BFS — contexto amplio
graphify query "<pregunta>" --dfs  # DFS — trazar un camino específico
\`\`\`
```

El `CLAUDE.md` va en la raíz del repo y no se sube a git (está en `.gitignore`).

---

## Parcial 1

Contiene enunciados de parciales anteriores (2024–2025), soluciones completas con pseudocódigo y Java, y el código base provisto por la cátedra.

### Estructura del Parcial 1 (desde 2026)

El parcial tiene **2 partes** (anteriormente eran 3).

| Parte | Contenido | Duración | Modalidad |
|-------|-----------|----------|-----------|
| **Parte 1** | Ejercicios de pseudocódigo | 60 min | Individual |
| **Parte 2** | Programación Java + JUnit | 60 min | Individual |

### Cuadernola

**[`parcial1/cuadernola.md`](parcial1/cuadernola.md)** — referencia completa para la Parte 1 del parcial.

Contiene: guía de elección de TDA (ABB vs AVL vs Lista vs Cola vs Pila vs Conjunto), pseudocódigos completos con lenguaje natural, pre/postcondiciones, implementaciones Java y análisis de orden para todos los TDAs del parcial.

### Letras disponibles

**`parte1/letras/`** — pseudocódigo en papel:

| Archivo | Descripción |
|---------|-------------|
| `2024-S1-parte2-examen1.md` | 11 mayo 2024, variante 1: AVL (72,24,12,...) + LTIM |
| `2024-S1-parte2-examen2.md` | 11 mayo 2024, variante 2: AVL (74,25,10,...) + Hojas/Internos |
| `2024-S2-parte2.md` | 2do sem 2024: Árbol genealógico + ListaDePrioridades |
| `2025-S1-parte2.md` | 17 mayo 2025: AVL (45,35,...) + Recomendador películas ABB |
| `2025-S2-parte2.md` | 18 oct 2025: Organismo ectotermo + ABB (solo Java) |
| `practico10-farmachop.md` | Práctico #10 UT01: preparadoViable (lista blanca/negra) |
| `festivalOtaku-completo.md` | Parcial completo: AVL + Trie + comboViable + Java |

**`parte2/letras/`** — programación Java:

| Archivo | Descripción |
|---------|-------------|
| `2024-S1-parte3-examen1.md` | 11 mayo 2024, variante 1: Java LTIM en TArbolDeProductos |
| `2024-S1-parte3-examen2.md` | 11 mayo 2024, variante 2: Java Hojas/Internos |
| `2025-S1-parte3.md` | 17 mayo 2025: Java Recomendador películas |
| `2025-S1-parte3-recuperatorio.md` | Recuperatorio 2025 S1: Java comboViable Festival Otaku |
| `parentesco-parte3.md` | 2024 S2: Java calcularParentesco árbol genealógico |

### Pseudocódigos

#### TDAs

| Archivo | Descripción |
|---------|-------------|
| `arbol-binario.md` | AB — definición, terminología, recorridos, tabla de posiciones simultáneas |
| `arbol-avl.md` | AVL — propiedad, rotaciones simples y dobles, `insertar`, `eliminar` |
| `arbol-bst.md` | ABB — insertar, buscar, eliminar, tamaño, altura, nivel, recorridos |
| `cola.md` | Cola — arreglo circular y lista enlazada |
| `conjunto.md` | Conjunto — lista enlazada sin duplicados |
| `lista-enlazada.md` | Lista simplemente enlazada con puntero `primero` |
| `pila.md` | Pila — lista enlazada con tope al frente |

#### Ejercicios de parcial

| Archivo | Ejercicio | Apareció en |
|---------|-----------|-------------|
| `ejercicio-ltim.md` | Longitud de Trayectoria Interna Media (LTIM) | 2024 S1 examen 1 |
| `ejercicio-separar-hojas-internos.md` | Separar nodos hoja e internos de un ABB | 2024 S1 examen 2 |
| `ejercicio-calcular-parentesco.md` | calcularParentesco en árbol genealógico invertido | 2024 S2 |
| `ejercicio-bst-peliculas.md` | Recomendador de películas con ABB/AVL | 2025 S1 |
| `ejercicio-combo-viable.md` | preparadoViable / comboViable (lista blanca + negra) | Práctico #10, Festival Otaku |

### Soluciones

| Archivo | Ejercicio | Incluye |
|---------|-----------|---------|
| `avl-inserciones.md` | AVL insertions — traza completa de 2 secuencias | Paso a paso, rotaciones, árbol final |
| `lti-media.md` | Longitud de Trayectoria Interna Media | Lenguaje Natural, Pre/Post, Pseudocódigo, Java, JUnit |
| `separar-hojas-internos.md` | Separar nodos hoja/internos de un ABB | Lenguaje Natural, Pre/Post, Pseudocódigo, Java, JUnit |
| `combo-viable.md` | preparadoViable / comboViable (lista blanca + negra) | Lenguaje Natural, Pre/Post, Pseudocódigo, Java, JUnit |
| `calcular-parentesco.md` | calcularParentesco en árbol genealógico invertido | Lenguaje Natural, Pre/Post, Pseudocódigo, Java, JUnit |
| `bst-peliculas.md` | Recomendador de películas con ABB/AVL | Lenguaje Natural, Pre/Post, Pseudocódigo, Java, JUnit |

### Código Base

En `parte2/codigo-base/`:

| Directorio | Contenido |
|------------|-----------|
| `2024-S1/` | `TArbolBB`, `TElementoAB`, `TArbolDeProductos`, `Producto`, tests |
| `farmachop/` | `Lista`, `Nodo`, `ILista`, `INodo`, archivos `.txt` de datos |
| `festival-otaku/` | `TDALista`, `TDACola`, `TDAPila`, `TDAConjunto` + implementaciones |
| `parentesco/` | `TArbolBB`, `TElementoAB`, `Genealogia` (stub), `Persona`, `ResultadoParentesco`, tests |

### Referencia de Métodos Java

**[`parcial1/metodos_java.md`](parcial1/metodos_java.md)** — implementaciones Java completas de todas las clases TDA del curso.

Incluye: `Nodo<T>`, `Lista<T>`, `ListaEnlazada<T>`, `ListaArray<T>`, `Cola<T>`, `Pila<T>`, `TElementoAB<T>`, `TArbolBB<T>`, `TElementoAVL<T>`, `TArbolAVL<T>`, `ManejadorArchivosGenerico`.

### Lo más importante para el parcial

El **Ejercicio 1 de la Parte 1** es casi siempre **inserciones en AVL**. Leer `guia-de-estudio.md` para el análisis completo de patrones y el plan de estudio priorizado.

---

## Parcial 2

Material basado en la **Unidad Temática 3 (UT3)** y **Unidad Temática 4 (UT4)**: Árboles Genéricos, Trie, Patricia, Hashing, TDA Mapa, TDA Diccionario, Java Collections Framework, Grafos Dirigidos (Dijkstra, Floyd, Warshall, DFS, BEA, clasificación topológica, ciclos, todos los caminos) y Grafos No Dirigidos (BEA, Prim, Kruskal, Puntos de articulación).

### Cuadernola

**[`parcial2/cuadernola.md`](parcial2/cuadernola.md)** — referencia completa para el parcial.

Contiene: guía de elección de estructura para UT3, pseudocódigos completos con pre/postcondiciones e implementaciones Java para todos los TDAs, patrones de uso de colecciones Java, contrato `hashCode`/`equals`, ejercicios típicos de árbol genérico, y algoritmos de grafos.

### Letras disponibles

**`parte1/letras/`** — pseudocódigo en papel:

| Archivo | Descripción |
|---------|-------------|
| `2023-2S-parte2.md` | nov 2023: conectividad grafo |
| `2024-1S-parte2-examen1.md` | jun 2024 v1: todos los caminos (switches) |
| `2024-1S-parte2-examen2.md` | jun 2024 v2: todos los caminos (trenes) |
| `2024-2S-parte2.md` | nov 2024: Dijkstra / MST |
| `2025-1S-parte2.md` | jul 2025: Dijkstra manual (A→E) |
| `2025-2S-parte2.md` | nov 2025: grafo no dirigido (traje Iron Man, BFS) |

**`parte2/letras/`** — programación Java:

| Archivo | Descripción |
|---------|-------------|
| `2023-2S-parte3.md` | nov 2023: Java conectividad + obtener mayor medición |
| `2024-1S-parte3-examen1.md` | jun 2024 v1: Java todos los caminos (switches) |
| `2024-1S-parte3-examen2.md` | jun 2024 v2: Java todos los caminos (trenes) |
| `2024-2S-parte3.md` | nov 2024: Java Dijkstra / MST sistema de transporte |

### Pseudocódigos

#### TDAs (UT3)

| Archivo | Descripción |
|---------|-------------|
| `arbol-generico.md` | Árbol genérico — `agregarHijo`, `eliminar`, `buscar`, `obtenerPadre`, recorridos, `altura`, `grado` |
| `trie.md` | Trie — `insertar`, `buscar`, `predecir`, `eliminar` |
| `trie-patricia.md` | Patricia (Trie comprimido) — motivación, tríadas (i,j,k), comparativa |
| `hash.md` | Hash — función de hash, chaining, open addressing (sondeo lineal), tombstones |
| `mapa.md` | TDA Mapa — operaciones, `HashMap`/`LinkedHashMap`/`TreeMap`, patrones Java |
| `diccionario.md` | TDA Diccionario — diferencia con Mapa, `Map<K, List<V>>` |

#### TDAs (UT4)

| Archivo | Descripción |
|---------|-------------|
| `grafo-dirigido.md` | Grafos — Dijkstra, Floyd, Warshall, DFS, BEA, clasificación topológica, ciclos, todos los caminos, **Prim**, **Kruskal**, **Puntos de articulación** |

### Soluciones

#### UT4 — Grafos (patrones de examen)

| Archivo | Patrón de examen | Apareció en |
|---------|-----------------|-------------|
| `ejercicio-warshall-conectividad.md` | "¿existe camino entre X e Y?" — BFS + Warshall | 2023-2S |
| `ejercicio-todos-los-caminos.md` | DFS backtracking con restricción de tipo de nodo | 2024-1S (ambas variantes) |
| `ejercicio-dijkstra-ruta-optima.md` | Dijkstra — tiempo mínimo + recuperar camino + variante por camión | 2024-2S P1, 2025-1S |
| `ejercicio-agm-prim.md` | Prim — árbol generador mínimo para red de mantenimiento | 2024-2S P2 |
| `ejercicio-componente-con-remocion.md` | BFS con remoción — tamaño de componente + pieza crítica | 2025-2S |
| `AlgoritmosCaminos.java` | Helper Java — `todosLosCaminos`, `caminoCritico`, `holgura` | UT4 general |

### Código Base — UT03-01

Proyecto Maven provisto por la cátedra en `parte2/codigo-base/ut03-01/`.

**TDAs implementados:**

| Paquete | Interfaz | Implementación |
|---------|----------|----------------|
| `tda.generic_tree` | `TArbolGenerico`, `TNodoGenerico` | `ArbolGenerico`, `NodoGenerico` |
| `tda.trie` | `TTrie`, `TNodoTrie` | `Trie`, `NodoTrie` |
| `tda.hash` | `THash` (abstracta) | `Hash` (open addressing, sondeo lineal) |

**Ejercicios entregables:** 5, 7, 9, 11, 12, 13, 14, 15, 16

**Comandos (dentro de `parte2/codigo-base/ut03-01/`):**
```bash
mvn compile
mvn test
mvn test -Dtest=NombreTest
```

### Código Base — UT04-01

Proyecto Maven provisto por la cátedra en `parte2/codigo-base/ut04-01/`. Contiene las interfaces, implementaciones y tests para todos los ejercicios de grafos de UT4 (Ej 1–12).

**Estructura de paquetes:**

| Paquete | Contenido |
|---------|-----------|
| `tda.grafo` | Interfaces `IDirectedIGraph`, `IUndirectedGraph`, `IDirectedGraphAlgorithms`, `IUndirectedGraphAlgorithm` |
| `tda.grafo.model` | `IGraph`, edges (`Edge`, `DirectedEdge`, `UndirectedEdge`, `WeightedEdge`), results (`IDijkstraResult`, `IFloydWarshallResult`, `Path`) |
| `tda.grafo.impl` | `GrafoDirigido`, `GrafoNoDirigido`, `AlgoritmosGrafoDirigido`, `AlgoritmosGrafoNoDirigido`, `DijkstraResult`, `FloydWarshallResult`, `WarshallResult` |
| `utils` | `PrettyGrid` (imprimir matrices), `AlgoritmosCaminos` (helper todos los caminos) |
| `ej03` | `MainFloyd` — programa interactivo carga desde archivos y responde consultas Floyd |

**Algoritmos implementados en `AlgoritmosGrafoDirigido`:** Dijkstra, Floyd, Warshall, DFS, BEA, clasificación topológica, todos los caminos.

**Algoritmos implementados en `AlgoritmosGrafoNoDirigido`:** BEA, Prim, Kruskal, Puntos de articulación.

**Comandos (dentro de `parte2/codigo-base/ut04-01/`):**
```bash
mvn compile
mvn test
mvn test -Dtest=NombreTest
```

### Referencia de Métodos Java

**[`parcial2/metodos_java.md`](parcial2/metodos_java.md)** — implementaciones Java completas en estilo estudiante.

Incluye: `NodoGenerico<T>`, `ArbolGenerico<T>`, `NodoTrie<T>`, `Hash<K,V>`, `TNodoHash<K,V>`, patrones de `hashCode`/`equals`, y referencia de Collections Framework.

### Resúmenes de unidades

| Archivo | Descripción |
|---------|-------------|
| [`parcial2/ut3-resumen-completo.md`](parcial2/ut3-resumen-completo.md) | Resumen teórico UT3 (cátedra): árboles genéricos, hashing, diccionarios, mapas |
| [`parcial2/AED_UT04_Grafos_Completo.md`](parcial2/AED_UT04_Grafos_Completo.md) | Resumen completo UT4: grafos dirigidos, algoritmos, representaciones |

---

## Examen Final

Material de **Ordenamiento** (Sorting): Inserción, Heapsort, Quicksort.

### Pseudocódigos

| Archivo | Ejercicio | Apareció en |
|---------|-----------|-------------|
| `ejercicio-sorting-insercion.md` | Inserción — datos casi ordenados, memoria limitada | 2024-1S examen 1, 2023-2S |
| `ejercicio-sorting-heapsort.md` | Heapsort — peor caso garantizado, ejemplo manual paso a paso | 2024-1S examen 2, 2024-2S |
| `ejercicio-sorting-quicksort.md` | Quicksort — buen promedio, variante descendente | 2023-2S, 2025-1S |

### Guía para elegir algoritmo de sorting

| Señal en el enunciado | Algoritmo |
|----------------------|-----------|
| "casi ordenado" + memoria limitada | Inserción |
| "peor caso garantizado" + memoria limitada | Heapsort |
| "buen promedio" + "pocas comparaciones" | Quicksort |

### Soluciones

| Archivo | Ejercicio | Incluye |
|---------|-----------|---------|
| `sorting-insercion.md` | Ordenamiento por inserción | Lenguaje Natural, Pre/Post, Pseudocódigo, Java, JUnit |
| `sorting-heapsort.md` | Heapsort | Lenguaje Natural, Pre/Post, Pseudocódigo, ejemplo manual 2024-2S, Java, JUnit |
| `sorting-quicksort.md` | Quicksort (asc. y desc.) | Lenguaje Natural, Pre/Post, Pseudocódigo, Java, JUnit |

---

*Material compilado por estudiantes de Ingeniería en Sistemas — UCU 2026.*
