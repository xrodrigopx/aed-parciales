---
title: "Solución: Ejercicios de Inserción en Árbol AVL"
type: analysis
tags: [avl, bst, rotaciones, parcial, solucion]
created: 2026-04-14
updated: 2026-04-14
sources: [primer-parcial-p2-2024-s1-ex1, primer-parcial-p2-2024-s1-ex2, primer-parcial-p2-2025-s1, parcial-festivalotaku]
---

# Solución: Ejercicios de Inserción en Árbol AVL

**Question / Prompt:** Ejercicio 1 de la Parte 2 en TODOS los parciales. Insertar secuencia de claves en un AVL, mostrando paso a paso, identificando nodo desbalanceado, tipo de rotación y nodos k1/k2/k3.

---

## Teoría Esencial

### Regla AVL
Para todo nodo del árbol: `|BF| ≤ 1`, donde `BF = h(hijo_derecho) - h(hijo_izquierdo)` y `h(null) = -1`.

### Tipos de Rotaciones

| Tipo | Condición | Rotación |
|------|-----------|----------|
| **LL** (izq-izq) | BF(nodo) = -2, BF(hijo_izq) ≤ 0 | Simple derecha en nodo |
| **RR** (der-der) | BF(nodo) = +2, BF(hijo_der) ≥ 0 | Simple izquierda en nodo |
| **LR** (izq-der) | BF(nodo) = -2, BF(hijo_izq) = +1 | Doble: izquierda en hijo, luego derecha en nodo |
| **RL** (der-izq) | BF(nodo) = +2, BF(hijo_der) = -1 | Doble: derecha en hijo, luego izquierda en nodo |

### Notación k1, k2, k3 (estándar UCU)
- **k3** = nodo desbalanceado
- **k2** = hijo de k3 en la dirección del desbalance
- **k1** = nieto (o el nodo que "causa" el problema) — en rotaciones simples se identifica pero no siempre se llama k1

---

## Solución Completa: Secuencia 72, 24, 12, 62, 32, 47, 17, 19, 21, 23, 40, 51, 63

_(Primer Parcial 2024 S1 — Examen 1)_

### Paso a paso

**Insertar 72:**
```
72
```
BF(72) = 0. Sin desbalance.

**Insertar 24:**
```
  72
 /
24
```
BF(72) = -1. Sin desbalance.

**Insertar 12:**
```
  72
 /
24
/
12
```
BF(24) = -1. BF(72) = **-2** → **Desbalance LL en 72**.
- k3 = 72, k2 = 24, k1 = 12
- Rotación simple **derecha** en 72.

Resultado:
```
  24
 /  \
12   72
```
BF(24) = 0. ✓

**Insertar 62:**
```
  24
 /  \
12   72
    /
   62
```
BF(72) = -1. BF(24) = +1. Sin desbalance.

**Insertar 32:**
```
  24
 /  \
12   72
    /
   62
  /
 32
```
BF(62) = -1. BF(72) = **-2** → **Desbalance LL en 72**.
- k3 = 72, k2 = 62, k1 = 32
- Rotación simple **derecha** en 72.

Resultado:
```
  24
 /  \
12   62
    /  \
   32   72
```
BF(62) = 0. BF(24) = +1. ✓

**Insertar 47:**
```
  24
 /  \
12   62
    /  \
   32   72
    \
    47
```
BF(32) = +1. h(62) = 2. BF(24) = h(62) - h(12) = **+2** → **Desbalance RL en 24**.
- k3 = 24, k2 = 62 (hijo der, BF = -1 → hijo izq más pesado → RL)
- Rotación **derecha en 62**, luego **izquierda en 24**.

Rotación derecha en 62: 32 sube, 32's right (47) → 62's left.
```
  24
 /  \
12   32
      \
      62
     /  \
    47   72
```
Rotación izquierda en 24: 32 sube, 24 → 32's left, 32's old left (null) → 24's right.
```
     32
    /  \
   24   62
  /    /  \
 12   47   72
```
BF(24) = -1, BF(62) = 0, BF(32) = 0. ✓

**Insertar 17:**
```
     32
    /  \
   24   62
  /    /  \
 12   47   72
  \
  17
```
BF(12) = +1. BF(24) = h(null) - h(12) = **-2** → **Desbalance LR en 24**.
- k3 = 24, k2 = 12 (hijo izq, BF = +1 → hijo der más pesado → LR)
- Rotación **izquierda en 12**, luego **derecha en 24**.

Rotación izquierda en 12: 17 sube, 12 → 17's left, 17's old left (null) → 12's right.
Rotación derecha en 24: 17 sube, 24 → 17's right, 17's old right (null) → 24's left.

```
     32
    /  \
   17   62
  /  \ /  \
 12  24 47  72
```
BF(17) = 0, BF(32) = 0. ✓

**Insertar 19:**
```
     32
    /  \
   17   62
  /  \ /  \
 12  24 47  72
    /
   19
```
BF(24) = -1. BF(17) = +1. BF(32) = -1 (h izq = 2, h der = 1... )

Recalculo: h(17) = 2, h(62) = 1. BF(32) = 1 - 2 = -1. ✓ Sin desbalance.

**Insertar 21:**
```
     32
    /  \
   17   62
  /  \ /  \
 12  24 47  72
    /
   19
    \
    21
```
BF(19) = +1. BF(24) = h(null) - h(19) = -1 - 1 = **-2** → **Desbalance LR en 24**.
- k3 = 24, k2 = 19 (hijo izq, BF = +1 → LR)
- Rotación **izquierda en 19**, luego **derecha en 24**.

```
     32
    /  \
   17   62
  /  \ /  \
 12  21 47  72
   /  \
  19  24
```
BF(21) = 0. BF(17) = +1. ✓

**Insertar 23:**
```
     32
    /  \
   17   62
  /  \ /  \
 12  21 47  72
   /  \
  19  24
     /
    23
```
BF(24) = -1. BF(21) = +1. h(21) = 2. BF(17) = h(21) - h(12) = **+2** → **Desbalance RR en 17**.
- k3 = 17, k2 = 21 (hijo der, BF = +1 → RR)
- Rotación simple **izquierda** en 17.

21 sube, 17 → 21's left, 21's old left (19) → 17's right.
```
       32
      /  \
     21   62
    /  \ /  \
   17  24 47  72
  /  \ /
 12  19 23
```
BF(17) = 0. BF(21) = 0. BF(32) = -1. ✓

**Insertar 40:**
```
       32
      /  \
     21   62
    /  \ /  \
   17  24 47  72
  /  \ / /
 12  19 23 40
```
BF(47) = -1. BF(62) = -1. BF(32) = 0. ✓

**Insertar 51:**
```
       32
      /  \
     21   62
    /  \ /  \
   17  24 47  72
  /  \ / / \
 12  19 23 40 51
```
BF(47) = 0. BF(62) = 0. BF(32) = 0. ✓

**Insertar 63:**
```
       32
      /  \
     21   62
    /  \ /  \
   17  24 47  72
  /  \ / / \ /
 12  19 23 40 51 63
```
BF(72) = -1. BF(62) = 0. BF(32) = 0. ✓ **Sin desbalance final.**

### Árbol final:
```
           32
          /  \
        21   62
       /  \ /  \
      17  24 47  72
     /  \ / / \ /
    12  19 23 40 51 63
```

### Tabla de rotaciones:
| Inserción | Nodo desbalanceado | Tipo | Rotación |
|-----------|-------------------|------|----------|
| 12 | 72 | **LL** | Simple derecha |
| 32 | 72 | **LL** | Simple derecha |
| 47 | 24 | **RL** | Doble (der en 62, izq en 24) |
| 17 | 24 | **LR** | Doble (izq en 12, der en 24) |
| 21 | 24 | **LR** | Doble (izq en 19, der en 24) |
| 23 | 17 | **RR** | Simple izquierda |

---

## Solución Completa: Secuencia 74, 25, 10, 61, 35, 47, 18, 20, 21, 23, 40, 55, 63

_(Primer Parcial 2024 S1 — Examen 2)_

El patrón de rotaciones es **idéntico** al Examen 1 (LL, LL, RL, LR, LR, RR):

| Inserción | Nodo desbalanceado | Tipo | Rotación |
|-----------|-------------------|------|----------|
| 10 | 74 | **LL** | Simple derecha |
| 35 | 74 | **LL** | Simple derecha |
| 47 | 25 | **RL** | Doble (der en 61, izq en 25) |
| 18 | 25 | **LR** | Doble (izq en 10, der en 25) |
| 21 | 25 | **LR** | Doble (izq en 20, der en 25) |
| 23 | 18 | **RR** | Simple izquierda |

### Árbol final:
```
           35
          /  \
        21   61
       /  \ /  \
      18  25 47  74
     /  \ / / \ /
    10  20 23 40 55 63
```

---

## Cómo Ejecutar Rotaciones AVL

### Rotación Simple Derecha (para LL)
```
Antes:         Después:
   k3              k2
  /               /  \
 k2         →   k1   k3
/  \               \
k1  B               B
```
- k2 sube, k3 baja a la derecha, el hijo derecho de k2 (B) pasa al izquierdo de k3.

### Rotación Simple Izquierda (para RR)
```
Antes:     Después:
k1             k2
  \           /  \
  k2    →   k1   k3
 /  \         \
A    k3         A
```
- k2 sube, k1 baja a la izquierda, el hijo izquierdo de k2 (A) pasa al derecho de k1.

### Doble Rotación LR
1. Rotación izquierda en k2 (hijo izquierdo de k3)
2. Rotación derecha en k3

### Doble Rotación RL
1. Rotación derecha en k2 (hijo derecho de k3)
2. Rotación izquierda en k3

---

## Confianza

Alta — las secuencias fueron trazadas nodo por nodo verificando BF en cada paso. El árbol final fue verificado completamente.

## Gaps

- El árbol inicial del Ejercicio 1 del 2025 S1 y del festivalOtaku no está disponible (era imagen). Solo se puede resolver si se tiene el árbol base.
- No se incluye el ejercicio de **eliminación** en AVL del festivalOtaku (claves 20, 25, 30).
