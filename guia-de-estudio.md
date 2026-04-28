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
- Crear las clases nuevas mínimas e indispensables; favorecer siempre la composición sobre la herencia o la proliferación de clases.
- No alterar las interfaces provistas.
- No agregar métodos no solicitados.
- Usar siempre los métodos propios del TDA (los implementados en el curso); no usar métodos de las colecciones de Java (`ArrayList`, `LinkedList`, etc.) ni `java.util.Stack`.
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

   - **Subfoco — eliminación (3 casos):** el algoritmo de eliminación es el más complejo del BST y candidato directo a parcial. Domina los 3 casos y el procedimiento `QUITARELNODO`. Ver `pseudocodigos/eliminacion-abb.md`.
     1. Nodo hoja → desvincularlo directamente
     2. Un hijo → el hijo sube
     3. Dos hijos → reemplazar por el **predecesor inorden** (máximo del subárbol izquierdo)

   - **Subfoco — Java:** en los parciales con código Java se usa `compareTo` para comparar y `Consumer<T>` para los recorridos. Ver `pseudocodigos/arbol-bst.md` sección "Interfaces Java UCU".

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

## Fundamentos: Árboles Binarios

Referencia rápida del TDA base. El parcial asume que dominás esto — es el piso de AVL, BST y cualquier algoritmo recursivo.

### Terminología

| Término | Definición |
|---------|-----------|
| Raíz | Nodo sin padre |
| Hoja (nodo externo) | Nodo sin hijos |
| Nodo interno | Nodo con al menos un hijo |
| Altura de un nodo | Longitud del camino más largo desde ese nodo hasta una hoja |
| Nivel / Profundidad | Longitud del camino desde la raíz hasta el nodo |

### Lleno vs. Completo

| Variante | Definición |
|----------|-----------|
| **Lleno** | Todos los nodos internos tienen exactamente **dos hijos** |
| **Completo** | Todos los niveles llenos excepto el último, que se llena **de izquierda a derecha** |

> Un árbol puede ser completo sin ser lleno. El árbol completo es la forma que toman los heaps.

### Propiedades del árbol lleno (para justificar órdenes)

Notación: *n* = nodos, *e* = hojas, *i* = internos, *h* = altura

| Propiedad | Fórmula |
|-----------|---------|
| Hojas vs. internos | `e = i + 1` |
| Total de nodos | `n = 2e − 1` |
| Altura mínima (árbol balanceado) | `h ≥ log₂(n + 1) − 1` |
| Altura máxima (árbol degenerado) | `h ≤ (n − 1) / 2` |

### Fórmulas recursivas

```
Tamaño:  ST = SL + SR + 1
Altura:  HT = max(HL + 1, HR + 1)
```

Estas fórmulas son la base de casi todo algoritmo recursivo sobre árboles binarios.

### Recorridos — algoritmos

```
preOrden(v):           postOrden(v):          inOrden(v):
    visitar(v)             Para cada hijo w:      Si tiene HijoIzq:
    Para cada hijo w:          postOrden(w)           HijoIzq.inOrden
        preOrden(w)        visitar(v)             visitar(v)
                                                  Si tiene HijoDer:
                                                      HijoDer.inOrden
```

| Recorrido | Orden | Aplicación en parcial |
|-----------|-------|----------------------|
| **Preorden** | raíz → izq → der | Copiar árbol, serializar |
| **Inorden** | izq → raíz → der | BST produce valores ordenados; imprimir expresión aritmética |
| **Postorden** | izq → der → raíz | Calcular tamaño/altura, evaluar expresión aritmética |

**Reglas mnemónicas para el parcial:**
- **Preorden:** los ancestros aparecen ANTES que sus descendientes.
- **Postorden:** los ancestros aparecen DESPUÉS que sus descendientes.
- **Inorden:** depende del subárbol. Si n está a la izquierda de m → n antes; a la derecha → n después.

> "n está a la izquierda de m" → n aparece antes en los 3 recorridos simultáneamente.  
> "n está a la derecha de m" → n aparece después en los 3 recorridos simultáneamente.

**Tabla de posiciones en recorridos** (i = inorden, p = preorden, s = postorden):

| Relación de n respecto a m | `i(n) < i(m)` | `s(n) < s(m)` | `p(n) < p(m)` |
|---------------------------|:---:|:---:|:---:|
| n es descendiente de m | posible* | siempre | nunca |
| n está a la izquierda de m | siempre | siempre | siempre |
| n está a la derecha de m | nunca | nunca | nunca |
| n es ancestro de m | posible* | nunca | siempre |

> *Descendiente en izquierda: `i(n) < i(m)`. Descendiente en derecha: `i(n) > i(m)`.  
> *Ancestro: `i(n) < i(m)` solo si m está en el subárbol derecho de n.

### Árbol de Expresión Aritmética

- Nodos internos = **operadores** (`+`, `-`, `×`, `/`)
- Hojas = **operandos** (números, variables)

**Imprimir expresión** (inorden con paréntesis):
```
Algoritmo printExpression
    Si tiene HijoIzquierdo
        imprimir("(")
        HijoIzquierdo.printExpression
    imprimir(elemento)
    Si tiene HijoDerecho
        HijoDerecho.printExpression
        imprimir(")")
```

**Evaluar expresión** (postorden):
```
Algoritmo evalExpr
    Si esHoja: Devolver elemento
    Sino:
        x ← HijoIzquierdo.evalExpr
        y ← HijoDerecho.evalExpr
        ◊ ← operador contenido
        Devolver x ◊ y
```

### Ejercicios tipo parcial sobre AB

1. Reconstruir árbol desde preorden + inorden.
2. Contar hojas de un árbol binario.
3. Calcular la suma de todos los elementos (árbol de enteros).
4. Contar nodos en el nivel *n*.

> El ejercicio de reconstrucción (preorden + inorden → árbol) es candidato directo a aparecer en el parcial 2026. Verificado en PS-UT02.

**Algoritmo de reconstrucción desde 2 recorridos:**

Para reconstruir, **uno de los recorridos DEBE ser el inorden**. El otro puede ser preorden o postorden.

- **Preorden + Inorden:** raíz = **primer** elemento del preorden. Dividir inorden: lo que está antes de la raíz = subárbol izq; lo que está después = subárbol der. Recursión.
- **Postorden + Inorden:** raíz = **último** elemento del postorden. Misma división.

```
// Reconstruir desde postorden + inorden
// postOrden: R, A, B, J, T, C, Z, W, F, X, M, S
// inOrden:   R, A, T, B, J, S, M, C, W, Z, X, F

raiz = S (último en postorden)
inOrden izq de S: {R,A,T,B,J} → recursión
inOrden der de S: {M,C,W,Z,X,F} → recursión
```

> Inorden + postorden → árbol **único**. Inorden + preorden → árbol **único**. Preorden + postorden solos → árbol NO único en general.

Ver referencia completa en `pseudocodigos/arbol-binario.md`.

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
