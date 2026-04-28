# AED — Primer Parcial: Material de Estudio

Repositorio de preparación para el **Primer Parcial de Algoritmos y Estructuras de Datos** — Universidad Católica del Uruguay, Ingeniería en Sistemas.

Contiene enunciados de parciales anteriores (2024–2025), soluciones completas con pseudocódigo y Java, y el código base provisto por la cátedra.

---

## Índice

- [Estructura](#estructura)
- [Cuadernola](#cuadernola)
- [Estructura del Parcial](#estructura-del-parcial-desde-2026)
- [Letras disponibles](#letras-disponibles)
- [Pseudocódigos](#pseudocódigos)
  - [TDAs](#tdas)
  - [Ejercicios de parcial](#ejercicios-de-parcial)
- [Soluciones](#soluciones)
- [Código Base](#código-base)
- [Referencia de Métodos Java](#referencia-de-métodos-java)
- [Lo más importante para el parcial](#lo-más-importante-para-el-parcial)

---

## Estructura

```
aed-parciales/
├── README.md                    ← este archivo
├── cuadernola.md                ← todo lo que necesitas para la primera parte del parcial
├── guia-de-estudio.md           ← análisis de patrones + plan de estudio priorizado
├── letras/                      ← enunciados originales de los parciales
├── pseudocodigos/               ← pseudocódigo de TDAs y ejercicios de parcial
├── soluciones/                  ← soluciones con pseudocódigo + Java + JUnit
└── codigo-base/                 ← código Java provisto por la cátedra en cada parcial
```

---

## Cuadernola

**[`cuadernola.md`](cuadernola.md)** — todo lo que necesitas en la cuadernola para cumplir con la primera parte del parcial (antiguamente parte 2, ahora la parte 1).

Contiene: guía de elección de TDA (ABB vs AVL vs Lista vs Cola vs Pila vs Conjunto), pseudocódigos completos con lenguaje natural, pre/postcondiciones y análisis de orden para todos los TDAs del parcial.

---

## Estructura del Parcial (desde 2026)

El primer parcial tiene **2 partes**:

| Parte | Contenido | Duración | Modalidad |
|-------|-----------|----------|-----------|
| **Parte 2** | Ejercicios de pseudocódigo | 60 min | Individual |
| **Parte 3** | Programación Java + JUnit | 60 min | Individual |

---

## Letras disponibles

| Archivo | Descripción |
|---------|-------------|
| `2024-S1-parte2-examen1.md` | Parte 2 — 11 mayo 2024, variante 1: AVL (72,24,12,...) + LTIM |
| `2024-S1-parte2-examen2.md` | Parte 2 — 11 mayo 2024, variante 2: AVL (74,25,10,...) + Hojas/Internos |
| `2024-S2-parte2.md` | Parte 2 — 2do sem 2024: Árbol genealógico + ListaDePrioridades |
| `2025-S1-parte2.md` | Parte 2 — 17 mayo 2025: AVL (45,35,...) + Recomendador películas ABB |
| `2025-S2-parte2.md` | Parte 2 — 18 oct 2025: Organismo ectotermo + ABB (solo Java) |
| `2024-S1-parte3-examen1.md` | Parte 3 — 11 mayo 2024, variante 1: Java LTIM en TArbolDeProductos |
| `2024-S1-parte3-examen2.md` | Parte 3 — 11 mayo 2024, variante 2: Java Hojas/Internos |
| `2025-S1-parte3.md` | Parte 3 — 17 mayo 2025: Java Recomendador películas |
| `2025-S1-parte3-recuperatorio.md` | Recuperatorio 2025 S1: Java comboViable Festival Otaku |
| `practico10-farmachop.md` | Práctico #10 UT01: preparadoViable (lista blanca/negra) |
| `festivalOtaku-completo.md` | Parcial completo: AVL + Trie + comboViable + Java |
| `parentesco-parte3.md` | Parte 3 2024 S2: Java calcularParentesco árbol genealógico |

---

## Pseudocódigos

Referencia rápida de TDAs y pseudocódigo de los ejercicios recurrentes en parciales.

### TDAs

| Archivo | Descripción |
|---------|-------------|
| `arbol-binario.md` | AB — definición, terminología, propiedades, recorridos, árbol de expresión, reconstrucción (pre+in y post+in), tabla de posiciones simultáneas |
| `arbol-avl.md` | AVL — propiedad, rotaciones simples y dobles, `insertar`, `eliminar` |
| `arbol-bst.md` | ABB — insertar, buscar, eliminar, tamaño, altura, nivel, recorridos |
| `cola.md` | Cola — arreglo circular y lista enlazada |
| `conjunto.md` | Conjunto — lista enlazada sin duplicados |
| `lista-enlazada.md` | Lista simplemente enlazada con puntero `primero` |
| `pila.md` | Pila — lista enlazada con tope al frente |

### Ejercicios de parcial

| Archivo | Ejercicio | Apareció en |
|---------|-----------|-------------|
| `ejercicio-ltim.md` | Longitud de Trayectoria Interna Media (LTIM) | 2024 S1 examen 1 |
| `ejercicio-separar-hojas-internos.md` | Separar nodos hoja e internos de un ABB | 2024 S1 examen 2 |
| `ejercicio-calcular-parentesco.md` | calcularParentesco en árbol genealógico invertido | 2024 S2 |
| `ejercicio-bst-peliculas.md` | Recomendador de películas con ABB/AVL | 2025 S1 |
| `ejercicio-combo-viable.md` | preparadoViable / comboViable (lista blanca + negra) | Práctico #10, Festival Otaku |

---

## Soluciones

| Archivo | Ejercicio | Incluye |
|---------|-----------|---------|
| `avl-inserciones.md` | **AVL insertions** — traza completa de 2 secuencias | Paso a paso, rotaciones, árbol final verificado |
| `lti-media.md` | **Longitud de Trayectoria Interna Media** | Lenguaje Natural, Pre/Post, Pseudocódigo, Java, JUnit |
| `separar-hojas-internos.md` | **Separar nodos hoja/internos** de un ABB | Lenguaje Natural, Pre/Post, Pseudocódigo, Java, JUnit |
| `combo-viable.md` | **preparadoViable / comboViable** (lista blanca + negra) | Lenguaje Natural, Pre/Post, Pseudocódigo, Java, JUnit |
| `calcular-parentesco.md` | **calcularParentesco** en árbol genealógico invertido | Lenguaje Natural, Pre/Post, Pseudocódigo, Java, JUnit |
| `bst-peliculas.md` | **Recomendador de películas** con ABB/AVL | Lenguaje Natural, Pre/Post, Pseudocódigo, Java, JUnit |

---

## Código Base

Código Java provisto por la cátedra en cada parcial:

| Directorio | Parcial | Contenido |
|------------|---------|-----------|
| `codigo-base/2024-S1/` | Parcial 2024 S1 | `TArbolBB`, `TElementoAB`, `TArbolDeProductos`, `Producto`, `ManejadorArchivosGenerico`, tests |
| `codigo-base/farmachop/` | Práctico #10 | `Lista`, `Nodo`, `ILista`, `INodo`, archivos `.txt` de datos |
| `codigo-base/festival-otaku/` | Recuperatorio 2025 S1 | `TDALista`, `TDACola`, `TDAPila`, `TDAConjunto` + implementaciones |
| `codigo-base/parentesco/` | Parcial 2024 S2 | `TArbolBB`, `TElementoAB`, `Genealogia` (stub), `Persona`, `ResultadoParentesco`, tests |

---

## Referencia de Métodos Java

**[`metodos_java.md`](metodos_java.md)** — implementaciones Java completas de todas las clases TDA del curso.

Incluye: `Nodo<T>`, `Lista<T>`, `ListaEnlazada<T>`, `ListaArray<T>` (sin duplicados), `Cola<T>`, `Pila<T>`, `TElementoAB<T>`, `TArbolBB<T>`, `TElementoAVL<T>`, `TArbolAVL<T>`, `ManejadorArchivosGenerico`.

---

## Lo más importante para el parcial

El **Ejercicio 1 de la Parte 2** es casi siempre **inserciones en AVL**. Lee `guia-de-estudio.md` para el análisis completo de patrones y el plan de estudio priorizado.

**Algoritmos nuevos (PS-UT02):** `obtenerNivel(criterio)` en `arbol-bst.md` y `pseudos-completo.md`. Tabla de posiciones simultáneas de recorridos en `arbol-binario.md` y `cuadernola.md`.

---

*Material compilado por estudiantes de Ingeniería en Sistemas — UCU 2026.*
