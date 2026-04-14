# Ejercicio de Parcial: Longitud de Trayectoria Interna Media (LTIM)

**Apareció en:** Parcial 2024 1er Semestre — Examen 1 (Parte 2 y Parte 3)

---

## Definición

```
LTI  = Σ h(i)  para todo nodo i    donde h(i) = nivel del nodo (raíz = nivel 0)

LTIM = LTI / n                      donde n = cantidad de nodos
```

**Ejemplo:** árbol con raíz 24, hijos 12 y 72, nieto 62 (hijo izquierdo de 72):
- Niveles: 24→0, 12→1, 72→1, 62→2
- LTI = 0+1+1+2 = 4, n = 4, **LTIM = 1.0**

---

## Lenguaje Natural

**`TArbolBB.longTrayIntMedia()`:**
Si el árbol está vacío, retornar 0. Si no, calcular la suma de los niveles de todos los nodos (LTI) y la cantidad de nodos (n), y retornar LTI/n.

**`TElementoAB.calcularLTI(nivel)`:**
Retornar la suma del nivel actual más las sumas de niveles de todos los nodos del subárbol izquierdo y derecho, pasando `nivel+1` a cada hijo.

---

## Pre y Post Condiciones

**`TArbolBB.longTrayIntMedia()`**
- **Precondición:** ninguna.
- **Postcondición:** retorna LTIM = LTI / n. Si el árbol está vacío, retorna 0.

**`TElementoAB.calcularLTI(nivel: entero)`**
- **Precondición:** `this ≠ nulo`; `nivel ≥ 0`.
- **Postcondición:** retorna la suma de los niveles de todos los nodos del subárbol con raíz en `this`, donde `this` está en el nivel `nivel`.

---

## Pseudocódigo — Versión con dos recorridos

```
// Método del árbol
TArbolBB.longTrayIntMedia(): real
  si esVacio() entonces
    retornar 0
  fin si
  n   ← raiz.obtenerTamaño()     // O(n)
  lti ← raiz.calcularLTI(0)      // O(n)
  retornar lti / n
fin método

// Método del nodo
TElementoAB.calcularLTI(nivel: entero): entero
  suma ← nivel
  si hijoIzq ≠ nulo entonces
    suma ← suma + hijoIzq.calcularLTI(nivel + 1)
  fin si
  si hijoDer ≠ nulo entonces
    suma ← suma + hijoDer.calcularLTI(nivel + 1)
  fin si
  retornar suma
fin método
```

**Orden:** O(n) + O(n) = **O(n)**

---

## Pseudocódigo — Versión optimizada (un solo recorrido)

Calcula LTI y tamaño en el mismo recorrido, evitando recorrer el árbol dos veces.

```
// Método del árbol
TArbolBB.longTrayIntMedia(): real
  si esVacio() entonces
    retornar 0
  fin si
  (lti, n) ← raiz.calcularLTIyTamaño(0)
  retornar lti / n
fin método

// Método del nodo — retorna par (lti, tamaño)
TElementoAB.calcularLTIyTamaño(nivel: entero): (entero, entero)
  lti ← nivel
  tam ← 1
  si hijoIzq ≠ nulo entonces
    (ltiIzq, tamIzq) ← hijoIzq.calcularLTIyTamaño(nivel + 1)
    lti ← lti + ltiIzq
    tam ← tam + tamIzq
  fin si
  si hijoDer ≠ nulo entonces
    (ltiDer, tamDer) ← hijoDer.calcularLTIyTamaño(nivel + 1)
    lti ← lti + ltiDer
    tam ← tam + tamDer
  fin si
  retornar (lti, tam)
fin método
```

**Orden:** **O(n)** — un único recorrido completo del árbol.

> Esta versión califica mejor en el parcial por mayor eficiencia (la cátedra explícitamente premia los algoritmos más eficientes).
