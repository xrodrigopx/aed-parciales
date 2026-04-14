# Ejercicio de Parcial: calcularParentesco en Árbol Genealógico Invertido

**Apareció en:** Parcial 2024 2do Semestre (Parte 2 y Parte 3)

---

## Escenario

Dado un árbol genealógico **invertido** (la raíz es el descendiente más reciente; los hijos son los padres), calcular el grado y tipo de parentesco entre dos personas.

```
        Juan       (nivel 0 = raíz)
       /    \
    Pedro   Marta   (nivel 1 = padres de Juan)
   /   \     /   \
José  Ana  Luis  Clara  (nivel 2 = abuelos de Juan)
```

**Reglas:**
- Si uno de los nodos es ancestro del otro → **consanguinidad**
- Si comparten un ancestro pero ninguno es ancestro del otro → **político**

**Fórmula del grado:**
```
grado = profundidad(persona1) + profundidad(persona2) - 2 × profundidad(LCA)
```

**Ejemplos verificados:**
- Juan ↔ Pedro: prof=0, prof=1, LCA=Juan (prof=0) → grado = 1, consanguinidad
- José ↔ Ana: prof=2, prof=2, LCA=Pedro (prof=1) → grado = 2, político
- Pedro ↔ Clara: prof=1, prof=2, LCA=Juan (prof=0) → grado = 3, político

---

## Justificación de Estructuras

Se usa un **árbol binario no BST** (cada nodo tiene hijoIzq = padre, hijoDer = madre).

Para encontrar caminos: DFS recursivo → O(n) por búsqueda.  
No se puede usar la propiedad BST porque la clave (nombre/id de persona) no define la posición del árbol — la estructura genealógica es la que define la posición.

---

## Lenguaje Natural

**`encontrarCamino(nodo, objetivo)`:**
Si el nodo es nulo, retornar nulo. Si el nodo es el objetivo, retornar una lista con solo ese nodo. Buscar recursivamente en hijoIzq: si se encuentra, agregar el nodo actual al frente de la lista y retornar. Hacer lo mismo con hijoDer. Si no se encuentra en ningún subárbol, retornar nulo.

**`calcularParentesco(raiz, persona1, persona2)`:**
Encontrar los caminos desde la raíz hasta cada persona. Si alguno no existe, no hay parentesco. Recorrer ambos caminos juntos para encontrar el último nodo compartido (LCA). Calcular el grado con la fórmula. Determinar el tipo: si el nivel del LCA coincide con la profundidad de alguna persona, es consanguinidad; si no, es político.

---

## Pre y Post Condiciones

**`encontrarCamino(nodo, objetivo)`**
- **Precondición:** `objetivo ≠ nulo`. `nodo` puede ser nulo.
- **Postcondición:** Retorna la lista de nodos del camino desde `nodo` hasta `objetivo` (el primero es la raíz del subárbol, el último es `objetivo`), o nulo si `objetivo` no está en el subárbol.

**`calcularParentesco(raiz, persona1, persona2)`**
- **Precondición:** `raiz` es la raíz del árbol. `persona1` y `persona2` son distintas.
- **Postcondición:** Retorna `(grado, tipo)`. Si alguna persona no se encuentra en el árbol, retorna `(-1, "Sin parentesco")`.

---

## Pseudocódigo

```
// === AUXILIAR: encontrar camino desde nodo hasta objetivo ===
encontrarCamino(nodo: IElementoAB, objetivo: IElementoAB): Lista<IElementoAB>
  si nodo = nulo entonces
    retornar nulo
  fin si
  si nodo.getDatos() = objetivo.getDatos() entonces
    camino ← nueva Lista
    camino.insertar(nodo)
    retornar camino
  fin si
  // Buscar en subárbol izquierdo
  caminoIzq ← encontrarCamino(nodo.getHijoIzq(), objetivo)
  si caminoIzq ≠ nulo entonces
    caminoIzq.insertarAlFrente(nodo)    // nodo es ancestro de objetivo
    retornar caminoIzq
  fin si
  // Buscar en subárbol derecho
  caminoDer ← encontrarCamino(nodo.getHijoDer(), objetivo)
  si caminoDer ≠ nulo entonces
    caminoDer.insertarAlFrente(nodo)
    retornar caminoDer
  fin si
  retornar nulo    // objetivo no está en este subárbol
fin método

// === MÉTODO PRINCIPAL ===
calcularParentesco(raiz: IElementoAB,
                   persona1: IElementoAB,
                   persona2: IElementoAB): (entero, cadena)

  camino1 ← encontrarCamino(raiz, persona1)
  camino2 ← encontrarCamino(raiz, persona2)

  si camino1 = nulo o camino2 = nulo entonces
    retornar (-1, "Sin parentesco")
  fin si

  // Encontrar el nivel del LCA (último nodo compartido)
  lcaNivel ← 0
  para i desde 0 hasta min(longitud(camino1), longitud(camino2)) - 1 hacer
    si camino1[i].getDatos() = camino2[i].getDatos() entonces
      lcaNivel ← i
    sino
      salir del bucle     // los caminos divergen aquí
    fin si
  fin para

  prof1 ← longitud(camino1) - 1
  prof2 ← longitud(camino2) - 1
  grado ← prof1 + prof2 - 2 × lcaNivel

  // Determinar tipo de parentesco
  si lcaNivel = prof1 o lcaNivel = prof2 entonces
    tipo ← "consanguinidad"    // LCA es una de las personas (relación directa)
  sino
    tipo ← "político"          // LCA es un ancestro común
  fin si

  retornar (grado, tipo)
fin método
```

---

## Análisis del Orden de Tiempo de Ejecución

Sea `n` = cantidad de nodos en el árbol y `h` = altura del árbol.

- `encontrarCamino`: en el peor caso visita todos los nodos → **O(n)**
- `calcularParentesco`:
  - Dos llamadas a `encontrarCamino` → O(n) cada una
  - Comparación de caminos: longitud de cada camino ≤ h ≤ n → O(h)
  - Total: **O(n)**

> En un árbol genealógico con pocas generaciones, `h` es pequeño pero `n` puede crecer. El cuello de botella es la búsqueda DFS.
