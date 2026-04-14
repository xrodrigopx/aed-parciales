# Guía de Estudio — Primer Parcial AED UCU

Análisis de patrones recurrentes en los parciales 2024–2025 y plan de estudio priorizado.

---

## Estructura del Parcial (desde 2026)

El primer parcial tiene **2 partes** (la Parte 1 de teoría fue retirada del plan de estudios):

- **Parte 2:** Pseudocódigo — Individual — 60 minutos.
- **Parte 3:** Java — Individual — 60 minutos.

---

## Parte 2: Patrón de Ejercicios de Pseudocódigo

### Ejercicio 1 — SIEMPRE sobre Árbol AVL

| Parcial | Tarea |
|---------|-------|
| 2024 S1 Ex1 | Insertar 72, 24, 12, 62, 32, 47, 17, 19, 21, 23, 40, 51, 63 |
| 2024 S1 Ex2 | Insertar 74, 25, 10, 61, 35, 47, 18, 20, 21, 23, 40, 55, 63 |
| 2025 S1 | Insertar 45, 35, 25, 37 en un árbol ya dado (imagen) |
| festivalOtaku | Insertar + eliminar en árbol dado + Árbol Trie |

**Lo que siempre se pide:**
1. Dibujar el árbol paso a paso después de cada inserción.
2. Identificar cuándo hay desbalance y en qué nodo.
3. Nombrar el tipo de rotación (LL, RR, LR, RL).
4. Identificar los nodos k1, k2, k3 involucrados.
5. Dibujar el árbol resultante después de cada rotación.

> **Observación clave:** Las dos variantes del 2024 S1 tienen exactamente el mismo patrón de 6 rotaciones: LL → LL → RL → LR → LR → RR. Los ejercicios están diseñados intencionalmente.

### Ejercicio 2 — Algoritmo sobre ABB o TDA

| Parcial | Ejercicio 2 |
|---------|-------------|
| 2024 S1 Ex1 | LTIM (longitud de trayectoria interna media) en ABB |
| 2024 S1 Ex2 | Separar nodos hoja e internos en dos listas |
| 2024 S2 | calcularParentesco + TDA ListaDePrioridades |
| 2025 S1 | Recomendador de películas con BST; filtrar score > 7.5 |
| festivalOtaku | comboViable (lista blanca + lista negra) |

**Lo que siempre se pide:**
1. Lenguaje Natural
2. Precondiciones y Postcondiciones
3. Pseudocódigo (método de árbol + método de nodo)
4. Análisis del Orden de Tiempo de Ejecución

**Desde 2025** también se pide justificar explícitamente la elección del TDA.

---

## Parte 3: Patrón de Ejercicios Java

Todos los parciales siguen esta estructura:

- **Parte 1 (45%):** Implementar funcionalidad en la clase apropiada.
- **Parte 2 (30%):** Clase Main — leer archivo de entrada, invocar métodos, escribir archivo de salida.
- **Parte 3 (25%):** JUnit tests.

| Parcial | Funcionalidad Java |
|---------|-------------------|
| 2024 S1 Ex1 | `longTrayIntMedia()` en `TArbolDeProductos` |
| 2024 S1 Ex2 | Separar hojas/internos en `TArbolDeProductos` |
| 2025 S1 | Sistema recomendador de películas (BST/AVL) |
| 2025 Recuperatorio | `comboViable` (Festival Otaku) |
| parcial-parentesco | `calcularParentesco` en árbol genealógico invertido |
| 2025 S2 | Organismo ectotermo explorando BST de temperaturas |

**Restricciones consistentes en TODOS los parciales:**
- No crear clases nuevas.
- No alterar las interfaces provistas.
- No agregar métodos no solicitados.
- Entregar proyecto Maven completo en `.zip`.

---

## Temas que SIEMPRE aparecen

| Tema | Parte 2 | Parte 3 |
|------|---------|---------|
| **AVL (insertions)** | ✅ Siempre Ejercicio 1 | Raramente |
| **ABB — métodos recursivos** | ✅ Siempre Ejercicio 2 | ✅ Siempre Parte 1 |
| **Recorridos inorden/preorden/postorden** | Implícito | ✅ Frecuente |
| **JUnit** | ✗ | ✅ Siempre Parte 3 |
| **Lectura/escritura de archivos** | ✗ | ✅ Siempre Parte 2 |
| **TDA Lista (viabilidad)** | Ocasional | Ocasional |

## Temas ocasionales

- Árbol Trie (festivalOtaku) — posiblemente en futuros parciales.
- ListaDePrioridades (2024 S2) — extensión de lista.
- Eliminación en AVL (festivalOtaku).
- LCA / parentesco (2024 S2 y parcial-parentesco).

---

## Plan de Estudio Priorizado

### En orden de importancia:

**1. AVL insertions** — el ejercicio 1 siempre es esto. Practica trazar al menos 5 secuencias distintas. Memoriza los 4 tipos de rotación y cuándo aplicarlos. Ver `soluciones/avl-inserciones.md`.

**2. Métodos recursivos en ABB** — aprende el patrón: el método del árbol llama al método del nodo, y el método del nodo se llama a sí mismo en los hijos. Ver `soluciones/lti-media.md` y `soluciones/separar-hojas-internos.md`.

**3. Proceso 3 pasos UCU** — para pseudocódigo: Lenguaje Natural → Precondiciones/Postcondiciones → Pseudocódigo → Análisis de Orden. **No saltarse ninguno**.

**4. JUnit** — saber escribir `@Test`, `assertEquals`, `assertNull`, `assertThrows`. El parcial siempre tiene tests (25% de la nota).

**5. ManejadorArchivosGenerico** — saber que `leerArchivo(ruta)` retorna `String[]` donde cada elemento es una línea del archivo.

**6. Justificación de TDA** — saber argumentar por qué BST/AVL/Lista para cada problema:
   - Búsqueda eficiente + inserciones frecuentes → AVL (O(log n) garantizado)
   - FIFO → Cola
   - LIFO → Pila
   - Verificar pertenencia → Conjunto/Set
   - Orden por acceso → Lista

**7. comboViable / preparadoViable** — el patrón lista blanca/negra aparece varias veces. Ver `soluciones/combo-viable.md`.

**8. calcularParentesco** — saber implementar `encontrarCamino` recursivo y calcular LCA. Ver `soluciones/calcular-parentesco.md`.

---

## Tipos de Rotaciones AVL (resumen rápido)

| Tipo | BF nodo | BF hijo | Rotación |
|------|---------|---------|----------|
| **LL** | -2 | ≤ 0 | Simple **derecha** en nodo desbalanceado |
| **RR** | +2 | ≥ 0 | Simple **izquierda** en nodo desbalanceado |
| **LR** | -2 | +1 | **Izquierda** en hijo, luego **derecha** en nodo |
| **RL** | +2 | -1 | **Derecha** en hijo, luego **izquierda** en nodo |

BF = h(subárbol derecho) − h(subárbol izquierdo); h(null) = −1.

---

*Material compilado por estudiantes de Ingeniería en Sistemas — UCU 2026.*
