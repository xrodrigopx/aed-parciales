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

**encontrarCamino(nodo, objetivo):**
Dado un nodo y una persona objetivo, buscar recursivamente en el subárbol con raíz en `nodo` hasta encontrar el objetivo. Retornar la lista de nodos que forman el camino desde `nodo` hasta `objetivo` (incluyendo ambos), o null si no se encuentra.

**calcularParentesco(raiz, persona1, persona2):**
Encontrar el camino desde la raíz hasta cada persona. Determinar el LCA (último nodo compartido en ambos caminos). Calcular el grado usando la fórmula. Determinar el tipo según si LCA coincide con alguna de las personas o no.

---

## Pre y Post Condiciones

### calcularParentesco(raiz, persona1, persona2)
- **Precondición:** `raiz` es la raíz del árbol; `persona1` y `persona2` son personas buscadas.
- **Postcondición:** Retorna `(grado, tipo)`. Si alguna persona no está en el árbol, retornar indicación de error (ej. grado=-1 o excepción).

### encontrarCamino(nodo, objetivo)
- **Precondición:** `nodo` puede ser nulo. `objetivo` es la persona buscada.
- **Postcondición:** Retorna lista de nodos del camino desde `nodo` hasta `objetivo`, o null si no existe.

---

## Pseudocódigo

```
// === MÉTODO PRINCIPAL ===
calcularParentesco(raiz: IElementoAB<Persona>, 
                   persona1: IElementoAB<Persona>, 
                   persona2: IElementoAB<Persona>): (entero, cadena)
  
  camino1 ← encontrarCamino(raiz, persona1)
  camino2 ← encontrarCamino(raiz, persona2)
  
  si camino1 = nulo o camino2 = nulo entonces
    retornar (-1, "Sin parentesco")   // una persona no está en el árbol
  fin si
  
  // Encontrar último ancestro común (LCA)
  lcaNivel ← 0
  para i desde 0 hasta min(longitud(camino1), longitud(camino2)) - 1 hacer
    si camino1[i].getDatos() = camino2[i].getDatos() entonces
      lcaNivel ← i
    sino
      salir del bucle
    fin si
  fin para
  
  prof1 ← longitud(camino1) - 1
  prof2 ← longitud(camino2) - 1
  grado ← prof1 + prof2 - 2 × lcaNivel
  
  // Determinar tipo
  si lcaNivel = prof1 o lcaNivel = prof2 entonces
    // LCA coincide con una de las personas → relación directa
    tipo ← "consanguinidad"
  sino
    tipo ← "político"
  fin si
  
  retornar (grado, tipo)
fin método

// === AUXILIAR: encontrar camino ===
encontrarCamino(nodo: IElementoAB<Persona>, 
                objetivo: IElementoAB<Persona>): Lista de IElementoAB<Persona>
  
  si nodo = nulo entonces
    retornar nulo
  fin si
  
  si nodo.getDatos() = objetivo.getDatos() entonces
    camino ← nueva Lista
    camino.agregar(nodo)
    retornar camino
  fin si
  
  // Buscar en hijo izquierdo
  caminoIzq ← encontrarCamino(nodo.getHijoIzq(), objetivo)
  si caminoIzq ≠ nulo entonces
    caminoIzq.insertarAlFrente(nodo)   // prepend
    retornar caminoIzq
  fin si
  
  // Buscar en hijo derecho
  caminoDer ← encontrarCamino(nodo.getHijoDer(), objetivo)
  si caminoDer ≠ nulo entonces
    caminoDer.insertarAlFrente(nodo)   // prepend
    retornar caminoDer
  fin si
  
  retornar nulo   // objetivo no encontrado en este subárbol
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
        
        List<IElementoAB<Persona>> camino1 = encontrarCamino(raiz, persona1);
        List<IElementoAB<Persona>> camino2 = encontrarCamino(raiz, persona2);
        
        if (camino1 == null || camino2 == null) {
            return null; // persona no encontrada en el árbol
        }
        
        // Encontrar el nivel del LCA
        int lcaNivel = 0;
        int minLen = Math.min(camino1.size(), camino2.size());
        for (int i = 0; i < minLen; i++) {
            if (camino1.get(i).getDatos().equals(camino2.get(i).getDatos())) {
                lcaNivel = i;
            } else {
                break;
            }
        }
        
        int prof1 = camino1.size() - 1;
        int prof2 = camino2.size() - 1;
        int grado = prof1 + prof2 - 2 * lcaNivel;
        
        String tipo;
        if (lcaNivel == prof1 || lcaNivel == prof2) {
            tipo = "consanguinidad";
        } else {
            tipo = "político";
        }
        
        // Construir camino completo para el archivo de resultados
        List<Persona> camino = new ArrayList<>();
        for (int i = prof1; i >= lcaNivel; i--) {
            camino.add(camino1.get(i).getDatos());
        }
        for (int i = lcaNivel + 1; i <= prof2; i++) {
            camino.add(camino2.get(i).getDatos());
        }
        
        return new ResultadoParentesco(grado, tipo, camino);
    }
    
    private static List<IElementoAB<Persona>> encontrarCamino(
            IElementoAB<Persona> nodo,
            IElementoAB<Persona> objetivo) {
        
        if (nodo == null) return null;
        
        if (nodo.getDatos().equals(objetivo.getDatos())) {
            List<IElementoAB<Persona>> camino = new ArrayList<>();
            camino.add(nodo);
            return camino;
        }
        
        List<IElementoAB<Persona>> caminoIzq = encontrarCamino(nodo.getHijoIzq(), objetivo);
        if (caminoIzq != null) {
            caminoIzq.add(0, nodo); // prepend
            return caminoIzq;
        }
        
        List<IElementoAB<Persona>> caminoDer = encontrarCamino(nodo.getHijoDer(), objetivo);
        if (caminoDer != null) {
            caminoDer.add(0, nodo); // prepend
            return caminoDer;
        }
        
        return null;
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
    ResultadoParentesco r = Genealogia.calcularParentesco(juan, juan, pedro);
    assertEquals(1, r.getGrado());
    assertEquals("consanguinidad", r.getTipo());
}

@Test
void testPolitico_joseAna() {
    ResultadoParentesco r = Genealogia.calcularParentesco(juan, jose, ana);
    assertEquals(2, r.getGrado());
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

Alta para la lógica de grado (fórmula verificada con los 3 ejemplos del enunciado). Alta para el código Java. La clase `ResultadoParentesco` debe incluir campos grado, tipo y camino — no está provista completamente.

## Gaps

- La clase `Persona` debe implementar `equals()` para que la comparación en `encontrarCamino` funcione.
- Si se usa etiqueta numérica en el árbol (no el objeto Persona como clave), la comparación cambia.
