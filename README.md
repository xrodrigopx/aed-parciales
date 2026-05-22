# AED — Material de Parciales

Repositorio de preparación para los parciales de **Algoritmos y Estructuras de Datos** — Universidad Católica del Uruguay, Ingeniería en Sistemas.

---

## Estructura

```
aed-parciales/
├── README.md
├── parcial1/                    ← material completo del primer parcial
│   ├── cuadernola.md
│   ├── guia-de-estudio.md
│   ├── metodos_java.md
│   ├── letras/
│   ├── pseudocodigos/
│   ├── soluciones/
│   └── codigo-base/
└── parcial2/                    ← material del segundo parcial (UT3)
    ├── cuadernola.md
    ├── guia-de-estudio.md
    ├── metodos_java.md
    ├── ut3-resumen-completo.md
    ├── letras/
    ├── pseudocodigos/
    ├── soluciones/
    └── codigo-base/
        └── ut03-01/             ← proyecto Maven provisto por la cátedra
```

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

| Archivo | Descripción |
|---------|-------------|
| `2024-S1-parte2-examen1.md` | Parte 1 (ex-Parte 2) — 11 mayo 2024, variante 1: AVL (72,24,12,...) + LTIM |
| `2024-S1-parte2-examen2.md` | Parte 1 (ex-Parte 2) — 11 mayo 2024, variante 2: AVL (74,25,10,...) + Hojas/Internos |
| `2024-S2-parte2.md` | Parte 1 (ex-Parte 2) — 2do sem 2024: Árbol genealógico + ListaDePrioridades |
| `2025-S1-parte2.md` | Parte 1 (ex-Parte 2) — 17 mayo 2025: AVL (45,35,...) + Recomendador películas ABB |
| `2025-S2-parte2.md` | Parte 1 (ex-Parte 2) — 18 oct 2025: Organismo ectotermo + ABB (solo Java) |
| `2024-S1-parte3-examen1.md` | Parte 2 (ex-Parte 3) — 11 mayo 2024, variante 1: Java LTIM en TArbolDeProductos |
| `2024-S1-parte3-examen2.md` | Parte 2 (ex-Parte 3) — 11 mayo 2024, variante 2: Java Hojas/Internos |
| `2025-S1-parte3.md` | Parte 2 (ex-Parte 3) — 17 mayo 2025: Java Recomendador películas |
| `2025-S1-parte3-recuperatorio.md` | Recuperatorio 2025 S1: Java comboViable Festival Otaku |
| `practico10-farmachop.md` | Práctico #10 UT01: preparadoViable (lista blanca/negra) |
| `festivalOtaku-completo.md` | Parcial completo: AVL + Trie + comboViable + Java |
| `parentesco-parte3.md` | Parte 2 (ex-Parte 3) 2024 S2: Java calcularParentesco árbol genealógico |

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

| Directorio | Contenido |
|------------|-----------|
| `codigo-base/2024-S1/` | `TArbolBB`, `TElementoAB`, `TArbolDeProductos`, `Producto`, tests |
| `codigo-base/farmachop/` | `Lista`, `Nodo`, `ILista`, `INodo`, archivos `.txt` de datos |
| `codigo-base/festival-otaku/` | `TDALista`, `TDACola`, `TDAPila`, `TDAConjunto` + implementaciones |
| `codigo-base/parentesco/` | `TArbolBB`, `TElementoAB`, `Genealogia` (stub), `Persona`, `ResultadoParentesco`, tests |

### Referencia de Métodos Java

**[`parcial1/metodos_java.md`](parcial1/metodos_java.md)** — implementaciones Java completas de todas las clases TDA del curso.

Incluye: `Nodo<T>`, `Lista<T>`, `ListaEnlazada<T>`, `ListaArray<T>`, `Cola<T>`, `Pila<T>`, `TElementoAB<T>`, `TArbolBB<T>`, `TElementoAVL<T>`, `TArbolAVL<T>`, `ManejadorArchivosGenerico`.

### Lo más importante para el parcial

El **Ejercicio 1 de la Parte 1** es casi siempre **inserciones en AVL**. Leer `guia-de-estudio.md` para el análisis completo de patrones y el plan de estudio priorizado.

---

## Parcial 2

Material basado en la **Unidad Temática 3 (UT3)**: Árboles Genéricos, Trie, Patricia, Hashing, TDA Mapa, TDA Diccionario y Java Collections Framework.

### Cuadernola

**[`parcial2/cuadernola.md`](parcial2/cuadernola.md)** — referencia completa para el parcial.

Contiene: guía de elección de estructura para UT3, pseudocódigos completos con pre/postcondiciones e implementaciones Java para todos los TDAs, patrones de uso de colecciones Java, contrato `hashCode`/`equals`, y ejercicios típicos de árbol genérico (`listarDescendientes`, `obtenerGeneracion`, `esDescendiente`, `ancestroComun`).

### Pseudocódigos

| Archivo | Descripción |
|---------|-------------|
| `arbol-generico.md` | Árbol genérico — `agregarHijo`, `eliminar`, `buscar`, `obtenerPadre`, recorridos, `altura`, `grado` |
| `trie.md` | Trie — `insertar`, `buscar`, `predecir`, `eliminar` |
| `trie-patricia.md` | Patricia (Trie comprimido) — motivación, tríadas (i,j,k), comparativa |
| `hash.md` | Hash — función de hash, chaining, open addressing (sondeo lineal), tombstones |
| `mapa.md` | TDA Mapa — operaciones, `HashMap`/`LinkedHashMap`/`TreeMap`, patrones Java |
| `diccionario.md` | TDA Diccionario — diferencia con Mapa, `Map<K, List<V>>` |

### Código Base — UT03-01

Proyecto Maven provisto por la cátedra en `codigo-base/ut03-01/`.

**TDAs implementados:**

| Paquete | Interfaz | Implementación |
|---------|----------|----------------|
| `tda.generic_tree` | `TArbolGenerico`, `TNodoGenerico` | `ArbolGenerico`, `NodoGenerico` |
| `tda.trie` | `TTrie`, `TNodoTrie` | `Trie`, `NodoTrie` |
| `tda.hash` | `THash` (abstracta) | `Hash` (open addressing, sondeo lineal) |

**Ejercicios entregables:** 5, 7, 9, 11, 12, 13, 14, 15, 16

**Comandos (dentro de `codigo-base/ut03-01/`):**
```bash
mvn compile
mvn test
mvn test -Dtest=NombreTest
```

### Referencia de Métodos Java

**[`parcial2/metodos_java.md`](parcial2/metodos_java.md)** — implementaciones Java completas en estilo estudiante.

Incluye: `NodoGenerico<T>`, `ArbolGenerico<T>`, `NodoTrie<T>`, `Hash<K,V>`, `TNodoHash<K,V>`, patrones de `hashCode`/`equals`, y referencia de Collections Framework.

### Resumen de la unidad

**[`parcial2/ut3-resumen-completo.md`](parcial2/ut3-resumen-completo.md)** — resumen teórico completo de la UT3 tal como viene de la cátedra.

---

*Material compilado por estudiantes de Ingeniería en Sistemas — UCU 2026.*
