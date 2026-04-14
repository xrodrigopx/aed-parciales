# Ejercicio de Parcial: Separar Nodos Hoja e Internos de un BST

**Apareció en:** Parcial 2024 1er Semestre — Examen 2 (Parte 2 y Parte 3)

---

## Definición

- **Nodo hoja:** nodo sin hijos izquierdo ni derecho.
- **Nodo interno:** nodo con al menos un hijo.
- **Orden relativo:** los nodos en cada lista deben respetar el orden inorden del BST (orden ascendente de etiquetas).

---

## Lenguaje Natural

**`TArbolBB.separarNodos()`:**
Si el árbol está vacío, retornar dos listas vacías. Si no, delegar el recorrido al nodo raíz, que clasificará cada nodo en la lista de hojas o de internos según corresponda.

**`TElementoAB.separarNodos(hojas, internos)`:**
Recorrer el subárbol en inorden. Para cada nodo: si es hoja (no tiene hijos), insertar su dato en `hojas`; si tiene al menos un hijo, insertar en `internos`.

---

## Pre y Post Condiciones

**`TArbolBB.separarNodos()`**
- **Precondición:** ninguna.
- **Postcondición:** retorna el par `(listaHojas, listaInternos)` con los datos de los nodos en inorden. El árbol no es modificado.

**`TElementoAB.separarNodos(hojas: Lista, internos: Lista)`**
- **Precondición:** `this ≠ nulo`; `hojas` e `internos` son listas inicializadas.
- **Postcondición:** todos los nodos del subárbol quedan clasificados e insertados en la lista correspondiente, en inorden.

---

## Pseudocódigo

```
// Método del árbol
TArbolBB.separarNodos(): (Lista hojas, Lista internos)
  hojas    ← nueva Lista vacía
  internos ← nueva Lista vacía
  si ¬esVacio() entonces
    raiz.separarNodos(hojas, internos)
  fin si
  retornar (hojas, internos)
fin método

// Método del nodo (recorrido inorden)
TElementoAB.separarNodos(hojas: Lista, internos: Lista): void
  // Primero el subárbol izquierdo (inorden)
  si hijoIzq ≠ nulo entonces
    hijoIzq.separarNodos(hojas, internos)
  fin si
  // Clasificar el nodo actual
  si hijoIzq = nulo Y hijoDer = nulo entonces
    hojas.insertar(this.getDato())         // es hoja
  sino
    internos.insertar(this.getDatos())     // es interno
  fin si
  // Luego el subárbol derecho (inorden)
  si hijoDer ≠ nulo entonces
    hijoDer.separarNodos(hojas, internos)
  fin si
fin método
```

**Demostración de invocación:**
```
(hojas, internos) ← arbol.separarNodos()
hojas.imprimir()
internos.imprimir()
```

---

## Análisis del Orden de Tiempo de Ejecución

- `separarNodos` en nodo: visita cada nodo exactamente una vez → O(n)
- Cada `lista.insertar` al final de la lista: O(longitud de la lista) si no hay puntero al último → en total O(n²) si se usa Lista simple.
- Si la lista tiene `insertarAlFinal` en O(1), el total es **O(n)**.

**Orden final: O(n)** (con lista que soporte inserción O(1) al final), o O(n²) con lista simple sin puntero al último.
