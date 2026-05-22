# TDA Mapa — Pseudocódigos y Referencia Java

## Definición

Estructura que almacena pares **(k, v)** con **clave única**: cada clave k mapea a exactamente un valor v. `poner(k, v)` **reemplaza** el valor si k ya existe.

## Operaciones del TDA

```
tamaño(): entero
estaVacio(): booleano
recuperar(k: K): V              ← nulo si no existe
poner(k: K, v: V): void         ← reemplaza si k ya existe
eliminar(k: K): V               ← retorna el valor eliminado
claves(): Colección<K>
valores(): Colección<V>
elementos(): Colección<Par(K,V)>
```

**Diferencia fundamental con Diccionario:**
- Mapa: clave única → `poner` reemplaza
- Diccionario: claves duplicadas → `insertar` siempre agrega

---

## Implementaciones Java

| Clase | Estructura interna | Complejidad | Orden de iteración | Clave null |
|-------|-------------------|-------------|--------------------|----|
| `HashMap` | Array + árbol rojo-negro (>8 elem.) | O(1) prom. | Sin orden | ✅ |
| `LinkedHashMap` | HashMap + lista doblemente enlazada | O(1) prom. | Orden de inserción | ✅ |
| `TreeMap` | Árbol rojo-negro | O(log n) | Natural de claves | ❌ |

**¿Cuándo elegir qué?**
- `HashMap`: caso general, O(1), sin necesidad de orden
- `LinkedHashMap`: cuando el orden de inserción importa (LRU cache)
- `TreeMap`: range queries, iteración ordenada por clave

---

## Patrones de uso en Java (estilo estudiante)

### Contar frecuencias

```java
Map<String, Integer> frecuencias = new HashMap<>();
for (String palabra : palabras) {
    Integer actual = frecuencias.get(palabra);
    if (actual == null) {
        frecuencias.put(palabra, 1);
    } else {
        frecuencias.put(palabra, actual + 1);
    }
}
```

### Agrupar elementos

```java
Map<String, List<String>> grupos = new HashMap<>();
for (String elemento : elementos) {
    String clave = calcularClave(elemento);
    List<String> lista = grupos.get(clave);
    if (lista == null) {
        lista = new ArrayList<>();
        grupos.put(clave, lista);
    }
    lista.add(elemento);
}
```

### Iterar un mapa

```java
for (Map.Entry<String, Integer> entry : mapa.entrySet()) {
    String clave = entry.getKey();
    Integer valor = entry.getValue();
    // usar clave y valor
}
```

### Buscar el máximo por valor

```java
String claveMax = null;
int valorMax = Integer.MIN_VALUE;
for (Map.Entry<String, Integer> entry : mapa.entrySet()) {
    if (entry.getValue() > valorMax) {
        valorMax = entry.getValue();
        claveMax = entry.getKey();
    }
}
```

---

## Constructor de HashMap

```java
new HashMap<>()                                    // capacidad=16, loadFactor=0.75
new HashMap<>(int initialCapacity)
new HashMap<>(int initialCapacity, float loadFactor)
```

Al superar `capacidad × factor de carga` → rehashing automático.
