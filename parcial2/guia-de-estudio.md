# Guía de estudio — Parcial 2 (UT3)

---

## TDAs del Parcial 2

| TDA | Archivo pseudocódigo | Archivo metodos_java |
|-----|---------------------|---------------------|
| Árbol Genérico | `pseudocodigos/arbol-generico.md` | `metodos_java.md` |
| Trie | `pseudocodigos/trie.md` | `metodos_java.md` |
| Patricia (Trie comprimido) | `pseudocodigos/trie-patricia.md` | — |
| Hash (open addressing) | `pseudocodigos/hash.md` | `metodos_java.md` |
| TDA Mapa | `pseudocodigos/mapa.md` | `metodos_java.md` |
| TDA Diccionario | `pseudocodigos/diccionario.md` | `metodos_java.md` |

---

## Patrones recurrentes en parciales

### Patrón 1 — Árbol genealógico / estructura jerárquica

El enunciado describe una jerarquía (empresa, familia, categorías). La solución siempre usa Árbol Genérico.

**Métodos que más aparecen:**
- `listarDescendientes(nombre)` → postOrden o preOrden filtrando subárbol
- `altura()` → recursión sobre hijos
- `obtenerGeneracion(nivel)` → BFS o recursión con nivel como parámetro
- `ancestroComun(a, b)` → LCA: obtener camino raíz→a, raíz→b, encontrar último común
- `esDescendiente(a, b)` → buscar `a` en el subárbol de `b`

**Pista clave:** para `obtenerGeneracion(nivel)` se pasa el nivel como parámetro en la recursión:
```java
void obtenerGeneracion(NodoGenerico<T> nodo, int nivelBuscado, int nivelActual, List<T> resultado) {
    if (nivelActual == nivelBuscado) {
        resultado.add(nodo.getDato());
        return;
    }
    for (NodoGenerico<T> hijo : nodo.getHijosNodos()) {
        obtenerGeneracion(hijo, nivelBuscado, nivelActual + 1, resultado);
    }
}
```

---

### Patrón 2 — Autocompletar / búsqueda de palabras con prefijo

El enunciado pide buscar palabras que empiezan con un prefijo, o verificar si una palabra existe.

**Siempre es Trie.**

Flujo de `predecir`:
1. Navegar hasta el nodo del último carácter del prefijo
2. DFS desde ahí, concatenando caracteres al string acumulado
3. Agregar a resultado cuando `esPalabra = true`

**Error típico:** confundir `buscar` (retorna Entry con esPalabra) con `esPalabra` del nodo. Si la palabra es solo prefijo de otra, `buscar` retorna Entry con `esPalabra=false`, **no retorna null**.

---

### Patrón 3 — Tabla hash y colisiones

El enunciado da un conjunto de claves y pide construir la tabla a mano, calculando posiciones y colisiones.

**Algoritmo manual:**
1. `h0 = clave mod N`
2. Si `tabla[h0]` está ocupada: sondear `(h0 + 1) mod N`, `(h0 + 2) mod N`, ...
3. Contar comparaciones para búsqueda exitosa y no exitosa

**Claves de análisis:**
- Más colisiones → más comparaciones promedio
- Factor de carga alto → muchas colisiones → tabla cuadrática se desempeña mejor
- Open addressing no tolera eliminaciones sin tombstones

---

### Patrón 4 — hashCode / equals

El enunciado pide implementar `equals` y `hashCode` para una clase, o preguntar qué pasa si se viola el contrato.

**Regla:** si `a.equals(b)` → `a.hashCode() == b.hashCode()`.

**Error clásico a identificar:**
> `equals` usa `isbn`, `hashCode` usa `titulo` → dos libros con mismo isbn pero diferente título tienen hashCodes distintos → `HashSet` los trata como elementos distintos → viola el contrato.

---

### Patrón 5 — Elegir la colección correcta

El enunciado describe un problema y pide elegir la colección Java más adecuada.

**Árbol de decisión:**
```
¿Necesito asociar clave→valor?
  Sí → ¿Necesito orden?
         No  → HashMap
         Sí (inserción) → LinkedHashMap
         Sí (clave natural) → TreeMap
  No → ¿Permito duplicados?
         Sí → ArrayList o LinkedList
         No → ¿Necesito orden?
                No → HashSet
                Sí → TreeSet
```

**Justificación esperada en parcial:**
- `HashMap` para conteo de frecuencias (O(1) amortizado)
- `TreeMap` si necesito iterar en orden lexicográfico
- `LinkedHashMap` para LRU cache o cuando el orden de inserción importa

---

## Errores comunes a evitar

| Error | Correcto |
|-------|----------|
| Eliminar en open addressing poniendo `null` | Poner tombstone (`loteLibre = true`) |
| Trie: retornar `null` cuando la palabra es solo prefijo | Retornar Entry con `esPalabra=false` |
| `TreeMap` acepta clave `null` | No acepta — lanza `NullPointerException` |
| Usar `Stack` de Java (legacy) | Usar `ArrayDeque` como pila |
| N = potencia de 2 en función hash con strings | N = número primo |
| `hashCode` con atributos mutables | Solo atributos que definen identidad lógica |

---

## Complejidades para tener en la cabeza

| Estructura | Búsqueda | Inserción | Eliminación |
|------------|---------|-----------|-------------|
| ArrayList | O(n) | O(1) amort. (final) | O(n) |
| LinkedList | O(n) | O(1) (extremos) | O(1) si tengo el nodo |
| HashMap | O(1) prom. | O(1) prom. | O(1) prom. |
| TreeMap | O(log n) | O(log n) | O(log n) |
| Trie | O(m) | O(m) | O(m) |
| Hash open addr. | O(1) prom. | O(1) prom. | O(1) prom. |
| Árbol genérico (buscar) | O(n) | O(n) | O(n) |

m = largo de la palabra/clave

---

## Orden de implementación recomendado (según CLAUDE.md del código base)

```
1. Ej 16  → ArbolGenealogico (árbol genérico desde cero)
2. Ej 5   → eliminar en Trie + tests
3. Ej 13  → Alumno: equals/hashCode
4. Ej 15  → Libro: equals/hashCode
5. Ej 14  → 3 estrategias de colisión
6. Ej 11  → frecuencia de palabras (HashMap/TreeMap)
7. Ej 12  → TTrieHashMap + buscarPatron
8. Ej 7   → mediciones comparativas
9. Ej 9   → factor de carga (depende de Hash implementado)
```

**Ejercicios entregables:** 5, 7, 9, 11, 12, 13, 14, 15, 16
