---
title: "Solución: preparadoViable / comboViable (Farmachop & Festival Otaku)"
type: analysis
tags: [lista, conjunto, bst, pseudocodigo, java, parcial, solucion, farmachop, festivalOtaku]
created: 2026-04-14
updated: 2026-04-14
sources: [parcial-farmachop, parcial-festivalotaku, primer-parcial-p3-2025-recuperatorio]
---

# Solución: preparadoViable / comboViable

**Question / Prompt:** Ejercicio de viabilidad de combinaciones. El mismo patrón aparece en Farmachop (práctico #10), Festival Otaku (examen y recuperatorio), y 2024 S2 (Java recuperatorio).

---

## Problema Genérico

Dado:
- **Lista blanca**: elementos permitidos con cualquier contenedor (suero/bebida).
- **Lista negra**: pares `{contenedor, elemento}` que NO son compatibles.
- Regla: si no está en ninguna lista → NO permitido (por precaución).

Determinar si una combinación `(contenedor, Lista<elementos>)` es viable.

---

## Estructuras de Datos Recomendadas

| Estructura | Búsqueda | Inserción | Mejor para |
|---|---|---|---|
| Lista enlazada | O(n) | O(1) | Implementación simple (UT01) |
| ABB | O(log n) avg | O(log n) avg | Eficiencia media |
| AVL | O(log n) guaranteed | O(log n) | Máxima eficiencia |
| Conjunto (Hash) | O(1) avg | O(1) avg | Máxima velocidad |

**Justificación para el parcial:** Para `listaBlanca`, un **Conjunto/Set** es ideal porque solo necesitamos saber si el ID está o no está (sin datos asociados) → búsqueda en O(1). Para `listaNegra`, un **ABB** ordenado por par `(suero, farmaco)` da búsqueda O(log n).

En el contexto UT01 (solo listas): usar `Lista<Integer>` para la blanca y `Lista<Par>` para la negra → búsqueda O(n) ambas.

---

## Lenguaje Natural

**preparadoViable(suero, farmacos):**
Para cada fármaco en la lista de fármacos del preparado: si el fármaco está en la lista blanca, está habilitado (continuar). Si el par (suero, fármaco) está en la lista negra, el preparado NO es viable. Si no aparece en ninguna lista, el preparado NO es viable (por precaución). Si todos los fármacos pasan la validación, el preparado es viable.

---

## Pre y Post Condiciones

- **Precondición:** `listaBlanca` y `listaNegra` están correctamente inicializadas. `suero` es un identificador de suero válido. `farmacos` es una lista no vacía de identificadores válidos.
- **Postcondición:** Retorna `true` si el preparado es viable; `false` si algún fármaco no es compatible con el suero dado.

---

## Pseudocódigo

```
// Estructuras globales (inicializadas al cargar el sistema):
listaBlanca: Lista o Conjunto de identificadorFarmaco
listaNegra: Lista de Par(identificadorSuero, identificadorFarmaco)

// === ALGORITMO PRINCIPAL ===
preparadoViable(suero: idSuero, farmacos: Lista<idFarmaco>): booleano
  nodo ← farmacos.getPrimero()
  mientras nodo ≠ nulo hacer
    farmaco ← nodo.getDato()
    si estaEnListaBlanca(farmaco) entonces
      // habilitado, continuar con el siguiente
    sino si estaEnListaNegra(suero, farmaco) entonces
      retornar falso     // combinación prohibida
    sino
      retornar falso     // no aparece en ninguna lista → no permitido
    fin si
    nodo ← nodo.getSiguiente()
  fin mientras
  retornar verdadero
fin método

// === AUXILIARES ===
estaEnListaBlanca(farmaco: idFarmaco): booleano
  retornar listaBlanca.buscar(farmaco) ≠ nulo
fin método

estaEnListaNegra(suero: idSuero, farmaco: idFarmaco): booleano
  nodo ← listaNegra.getPrimero()
  mientras nodo ≠ nulo hacer
    par ← nodo.getDato()
    si par.suero = suero Y par.farmaco = farmaco entonces
      retornar verdadero
    fin si
    nodo ← nodo.getSiguiente()
  fin mientras
  retornar falso
fin método
```

**Pseudocódigo simplificado con un solo recorrido de listaNegra:**

```
preparadoViable(suero, farmacos):
  para cada farmaco en farmacos hacer
    si listaBlanca.buscar(farmaco) ≠ nulo entonces
      continuar
    fin si
    encontrado ← falso
    nodoNegra ← listaNegra.getPrimero()
    mientras nodoNegra ≠ nulo hacer
      si nodoNegra.suero = suero Y nodoNegra.farmaco = farmaco entonces
        retornar falso
      fin si
      nodoNegra ← nodoNegra.getSiguiente()
    fin mientras
    // No estaba en blanca ni en negra
    retornar falso
  fin para
  retornar verdadero
```

---

## Análisis del Orden de Tiempo de Ejecución

- Sean:
  - `m` = cantidad de fármacos en el preparado
  - `b` = tamaño de la lista blanca
  - `n` = tamaño de la lista negra

Con listas enlazadas:
- `estaEnListaBlanca`: O(b)
- `estaEnListaNegra`: O(n)
- `preparadoViable`: O(m × (b + n)) = **O(m × (b + n))**

Con Conjunto/BST para la blanca y BST para la negra:
- `preparadoViable`: O(m × log(b + n)) = **O(m × log(b + n))**

---

## Implementación Java (para Parte 3 — Festival Otaku)

```java
// En la clase principal del sistema:
public class SistemaCombo {
    private TDALista<Integer> permitidos;          // lista blanca de IDs
    private TDALista<int[]> prohibidos;            // lista negra de pares [idBebida, idIngrediente]
    
    public boolean comboViable(int idBebida, TDALista<Integer> ingredientes) {
        for (int i = 0; i < ingredientes.tamaño(); i++) {
            int idIngrediente = ingredientes.obtener(i);
            if (estaPermitido(idIngrediente)) continue;
            if (estaProhibido(idBebida, idIngrediente)) return false;
            return false; // no está en ninguna lista
        }
        return true;
    }
    
    private boolean estaPermitido(int idIngrediente) {
        return permitidos.contiene(idIngrediente);
    }
    
    private boolean estaProhibido(int idBebida, int idIngrediente) {
        for (int i = 0; i < prohibidos.tamaño(); i++) {
            int[] par = prohibidos.obtener(i);
            if (par[0] == idBebida && par[1] == idIngrediente) return true;
        }
        return false;
    }
}
```

### Carga de Datos desde Archivos

```java
// Cargar lista blanca (ingredientes_permitidos.txt: una línea = un ID)
FileUtils.leerLineas("ingredientes_permitidos.txt", line -> {
    permitidos.agregar(Integer.parseInt(line.trim()));
});

// Cargar lista negra (combinaciones_prohibidas.txt: "idBebida,idIngrediente")
FileUtils.leerLineas("combinaciones_prohibidas.txt", line -> {
    String[] partes = line.split(",");
    prohibidos.agregar(new int[]{
        Integer.parseInt(partes[0].trim()),
        Integer.parseInt(partes[1].trim())
    });
});
```

### JUnit Test

```java
@Test
public void testComboViable_ingredienteEnBlanca() {
    sistema.cargarPermitido(1);  // ingrediente 1 permitido con todo
    TDALista<Integer> combo = new ListaEnlazada<>();
    combo.agregar(1);
    assertTrue(sistema.comboViable(5, combo));  // bebida 5, ingrediente 1
}

@Test
public void testComboViable_combinacionProhibida() {
    sistema.cargarProhibida(5, 2);  // bebida 5 + ingrediente 2 = prohibido
    TDALista<Integer> combo = new ListaEnlazada<>();
    combo.agregar(2);
    assertFalse(sistema.comboViable(5, combo));
}

@Test
public void testComboViable_ingredienteSinClasificacion() {
    TDALista<Integer> combo = new ListaEnlazada<>();
    combo.agregar(99);  // no está en blanca ni en negra
    assertFalse(sistema.comboViable(5, combo));  // precaución → no viable
}
```

---

## Confianza

Alta — el algoritmo es directo. La lógica de tres casos (blanca → OK, negra → NO, ninguna → NO) es clara.

## Gaps

- La validación de que los IDs existan en los catálogos de sueros/fármacos (mencionada en el práctico farmachop) requiere una estructura adicional de búsqueda de sueros y fármacos.
