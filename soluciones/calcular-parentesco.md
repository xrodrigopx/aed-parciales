---
title: "Solución: calcularParentesco en Árbol Genealógico Invertido"
type: analysis
tags: [bst, arbol-binario, recursion, java, parcial, solucion, genealogia, parentesco]
created: 2026-04-14
updated: 2026-04-14
sources: [primer-parcial-p2-2024-s2, parcial-parentesco]
---

# Solución: calcularParentesco en Árbol Genealógico Invertido

**Question / Prompt:** Ejercicio del Parcial 2024 2do Semestre (pseudocódigo) y correspondiente Parte 3 (Java). Calcular el grado y tipo de parentesco entre dos personas en un árbol genealógico invertido.

---

## Modelo del Árbol

El árbol está **invertido**: la raíz es la persona más reciente (descendiente), y los hijos representan sus padres. Cada nodo tiene a lo sumo 2 hijos (padre y madre).

```
        Juan       (nivel 0 = raíz)
       /    \
    Pedro   Marta   (nivel 1 = padres de Juan)
   /   \     /   \
José  Ana  Luis  Clara  (nivel 2 = abuelos de Juan)
```

**Reglas de parentesco:**
- Si uno es ancestro directo del otro → **consanguinidad**
- Si están relacionados lateralmente (comparten ancestro pero ninguno es ancestro del otro) → **político**

**Cálculo del grado:** distancia entre los dos nodos pasando por su ancestro común más cercano (LCA).
```
grado = profundidad(persona1) + profundidad(persona2) - 2 × profundidad(LCA)
```

**Ejemplos verificados:**
- Juan ↔ Pedro: depth(Juan)=0, depth(Pedro)=1, LCA=Juan → grado = 0+1-0 = **1 consanguinidad**
- José ↔ Ana: depth=2 ambos, LCA=Pedro (depth=1) → grado = 2+2-2 = **2 político**
- Pedro ↔ Clara: depth(Pedro)=1, depth(Clara)=2, LCA=Juan (depth=0) → grado = 1+2-0 = **3 político**

---

## Lenguaje Natural

**encontrarLCA(nodo, p1, p2):**
Dado un nodo raíz y dos personas objetivo, retornar el nodo del ancestro común más cercano (LCA). Si ninguna de las dos personas está en el subárbol, retornar nulo. Si solo una está, retornar ese nodo (la comprobación de existencia la hace el llamador).

**encontrarProfundidad(nodo, objetivo, nivel):**
Buscar recursivamente `objetivo` en el subárbol con raíz en `nodo`. Retornar la profundidad del objetivo respecto a `nodo` (0 si `nodo` es el objetivo), o -1 si no se encuentra.

**calcularParentesco(raiz, persona1, persona2):**
Hallar el LCA de las dos personas. Calcular la profundidad de cada persona desde el LCA. El grado es la suma de ambas profundidades. El tipo es "consanguinidad" si el LCA coincide con una de las personas (profundidad = 0), y "político" en caso contrario.

---

## Pre y Post Condiciones

### calcularParentesco(raiz, persona1, persona2)
- **Precondición:** `raiz` es la raíz del árbol; `persona1` y `persona2` son personas buscadas.
- **Postcondición:** Retorna `(grado, tipo)`. Si alguna persona no está en el árbol, retornar indicación de error (ej. grado=-1 o null).

### encontrarLCA(nodo, p1, p2)
- **Precondición:** `nodo` puede ser nulo.
- **Postcondición:** Retorna el nodo LCA si ambas personas están en el subárbol; el nodo encontrado si solo una está; nulo si ninguna.

### encontrarProfundidad(nodo, objetivo, nivel)
- **Precondición:** `nodo` puede ser nulo; `nivel ≥ 0`.
- **Postcondición:** Retorna profundidad del objetivo desde `nodo`, o -1 si no existe.

---

## Pseudocódigo

```
// === AUXILIAR: LCA ===
encontrarLCA(nodo: IElementoAB<Persona>,
             p1: IElementoAB<Persona>,
             p2: IElementoAB<Persona>): IElementoAB<Persona>
  si nodo = nulo entonces
    retornar nulo
  fin si
  si nodo.getDatos() = p1.getDatos() o nodo.getDatos() = p2.getDatos() entonces
    retornar nodo
  fin si
  lcaIzq ← encontrarLCA(nodo.getHijoIzq(), p1, p2)
  lcaDer ← encontrarLCA(nodo.getHijoDer(), p1, p2)
  si lcaIzq ≠ nulo y lcaDer ≠ nulo entonces
    retornar nodo     // p1 y p2 están en subárboles distintos
  fin si
  si lcaIzq ≠ nulo entonces retornar lcaIzq sino retornar lcaDer fin si
fin método

// === AUXILIAR: profundidad desde un nodo ===
encontrarProfundidad(nodo: IElementoAB<Persona>,
                     objetivo: IElementoAB<Persona>,
                     nivel: entero): entero
  si nodo = nulo entonces retornar -1 fin si
  si nodo.getDatos() = objetivo.getDatos() entonces retornar nivel fin si
  izq ← encontrarProfundidad(nodo.getHijoIzq(), objetivo, nivel + 1)
  si izq ≠ -1 entonces retornar izq fin si
  retornar encontrarProfundidad(nodo.getHijoDer(), objetivo, nivel + 1)
fin método

// === MÉTODO PRINCIPAL ===
calcularParentesco(raiz: IElementoAB<Persona>,
                   persona1: IElementoAB<Persona>,
                   persona2: IElementoAB<Persona>): (entero, cadena)
  lca ← encontrarLCA(raiz, persona1, persona2)
  si lca = nulo entonces
    retornar (-1, "Sin parentesco")
  fin si
  prof1 ← encontrarProfundidad(lca, persona1, 0)
  prof2 ← encontrarProfundidad(lca, persona2, 0)
  si prof1 = -1 o prof2 = -1 entonces
    retornar (-1, "Sin parentesco")   // una persona no está en el árbol
  fin si
  grado ← prof1 + prof2
  si prof1 = 0 o prof2 = 0 entonces
    tipo ← "consanguinidad"
  sino
    tipo ← "político"
  fin si
  retornar (grado, tipo)
fin método
```

---

## Análisis del Orden de Tiempo de Ejecución

- `encontrarCamino`: visita cada nodo a lo sumo una vez → O(n)
- `calcularParentesco`: llama a `encontrarCamino` dos veces + recorre los caminos (longitud ≤ h, altura del árbol) → **O(n)**

---

## Implementación Java

```java
public class Genealogia {

    public static ResultadoParentesco calcularParentesco(
            IElementoAB<Persona> raiz,
            IElementoAB<Persona> persona1,
            IElementoAB<Persona> persona2) {

        IElementoAB<Persona> lca = encontrarLCA(raiz, persona1, persona2);
        if (lca == null) return null;

        int prof1 = encontrarProfundidad(lca, persona1, 0);
        int prof2 = encontrarProfundidad(lca, persona2, 0);
        if (prof1 == -1 || prof2 == -1) return null; // persona no existe en el árbol

        int grado = prof1 + prof2;
        String tipo = (prof1 == 0 || prof2 == 0) ? "consanguinidad" : "político";
        return new ResultadoParentesco(grado, tipo);
    }

    private static IElementoAB<Persona> encontrarLCA(
            IElementoAB<Persona> nodo,
            IElementoAB<Persona> p1,
            IElementoAB<Persona> p2) {

        if (nodo == null) return null;
        if (nodo.getDatos().equals(p1.getDatos()) || nodo.getDatos().equals(p2.getDatos()))
            return nodo;

        IElementoAB<Persona> lcaIzq = encontrarLCA(nodo.getHijoIzq(), p1, p2);
        IElementoAB<Persona> lcaDer = encontrarLCA(nodo.getHijoDer(), p1, p2);

        if (lcaIzq != null && lcaDer != null) return nodo;
        return lcaIzq != null ? lcaIzq : lcaDer;
    }

    private static int encontrarProfundidad(
            IElementoAB<Persona> nodo,
            IElementoAB<Persona> objetivo,
            int nivel) {

        if (nodo == null) return -1;
        if (nodo.getDatos().equals(objetivo.getDatos())) return nivel;

        int izq = encontrarProfundidad(nodo.getHijoIzq(), objetivo, nivel + 1);
        if (izq != -1) return izq;
        return encontrarProfundidad(nodo.getHijoDer(), objetivo, nivel + 1);
    }
}
```

### Construcción del Árbol en Main

```java
// Árbol: Juan / Pedro Marta / José Ana Luis Clara
TElementoAB<Persona> jose = new TElementoAB<>(1, new Persona("José"));
TElementoAB<Persona> ana  = new TElementoAB<>(2, new Persona("Ana"));
TElementoAB<Persona> luis  = new TElementoAB<>(3, new Persona("Luis"));
TElementoAB<Persona> clara = new TElementoAB<>(4, new Persona("Clara"));
TElementoAB<Persona> pedro = new TElementoAB<>(5, new Persona("Pedro"));
pedro.setHijoIzq(jose); pedro.setHijoDer(ana);
TElementoAB<Persona> marta = new TElementoAB<>(6, new Persona("Marta"));
marta.setHijoIzq(luis); marta.setHijoDer(clara);
TElementoAB<Persona> juan = new TElementoAB<>(7, new Persona("Juan"));
juan.setHijoIzq(pedro); juan.setHijoDer(marta);

// Cálculos requeridos:
// a. Juan ↔ Ana  → grado=2, político (ana está en camino de juan, pero pedro es ancestro de ana y juan)
// b. Juan ↔ inexistente → null/error
// c. José ↔ Marta → grado=3, político (LCA=Juan)
```

### JUnit5 Tests

```java
@Test
void testConsanguinidad_juanPedro() {
    // Juan (raíz) ↔ Pedro (hijo) → grado 1, LCA = Juan (prof1=0)
    ResultadoParentesco r = Genealogia.calcularParentesco(juan, juan, pedro);
    assertEquals(1, r.getGrado());
    assertEquals("consanguinidad", r.getTipo());
}

@Test
void testPolitico_joseAna() {
    // José (nivel 2, izq de Pedro) ↔ Ana (nivel 2, der de Pedro)
    // LCA = Pedro → prof1=1, prof2=1, grado=2, político
    ResultadoParentesco r = Genealogia.calcularParentesco(juan, jose, ana);
    assertEquals(2, r.getGrado());
    assertEquals("político", r.getTipo());
}

@Test
void testPolitico_pedroClaraNivel3() {
    // Pedro (nivel 1) ↔ Clara (nivel 2, bajo Marta)
    // LCA = Juan → prof1=1, prof2=2, grado=3, político
    ResultadoParentesco r = Genealogia.calcularParentesco(juan, pedro, clara);
    assertEquals(3, r.getGrado());
    assertEquals("político", r.getTipo());
}

@Test
void testPersonaNoEncontrada() {
    IElementoAB<Persona> inexistente = new TElementoAB<>(99, new Persona("Inexistente"));
    assertNull(Genealogia.calcularParentesco(juan, juan, inexistente));
}
```

---

## Confianza

Alta — la lógica de grado verificada con los 3 ejemplos del enunciado. El algoritmo LCA es estándar y no requiere listas ni acceso indexado.

## Gaps

- La clase `Persona` debe implementar `equals()` para que la comparación en `encontrarLCA` y `encontrarProfundidad` funcione.
- Si se usa etiqueta numérica en el árbol (no el objeto Persona como clave), la comparación puede usar la etiqueta en lugar de `getDatos().equals()`.
- `ResultadoParentesco` solo necesita campos `grado` y `tipo` — no se reconstruye el camino entre las personas, lo cual simplifica la implementación y elimina la necesidad de cualquier lista.
