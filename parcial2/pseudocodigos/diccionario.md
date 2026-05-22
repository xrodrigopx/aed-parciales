# TDA Diccionario — Pseudocódigos y Referencia Java

## Definición

Como el TDA Mapa, almacena pares clave-valor, pero **permite múltiples entradas con la misma clave**. `insertar(k, v)` **nunca reemplaza**; siempre agrega.

## Diferencia fundamental con Mapa

| TDA | Claves duplicadas | Método de inserción |
|-----|-----------------|---------------------|
| **Mapa** | No — clave única | `poner(k, v)` — reemplaza |
| **Diccionario** | Sí — múltiples valores por clave | `insertar(k, v)` — siempre agrega |

## Operaciones del TDA

```
tamaño(): entero
estaVacio(): booleano
buscar(k: K): Entry<K,V>              ← primer elemento con clave k
buscarTodos(k: K): Colección<Entry>   ← todos con clave k
insertar(k: K, v: V): void            ← agrega (incluso si k ya existe)
eliminar(e: Entry): void              ← elimina la entrada específica (no por clave)
elementos(): Colección<Entry<K,V>>
```

## Casos de uso

- Índice de libro: término → lista de páginas donde aparece
- Historial de precios: fecha → múltiples precios registrados ese día
- Grupos de estudiantes: curso → lista de estudiantes

---

## Implementación en Java

Java no tiene un `Diccionario` estándar. Las alternativas son:

### Opción 1: Map<K, List<V>> (más común)

```java
Map<String, List<String>> diccionario = new HashMap<>();

// insertar
public void insertar(String clave, String valor) {
    List<String> lista = diccionario.get(clave);
    if (lista == null) {
        lista = new ArrayList<>();
        diccionario.put(clave, lista);
    }
    lista.add(valor);
}

// buscarTodos
public List<String> buscarTodos(String clave) {
    List<String> lista = diccionario.get(clave);
    if (lista == null) {
        return new ArrayList<>();
    }
    return lista;
}

// eliminar una entrada específica (no todas las de la clave)
public boolean eliminar(String clave, String valor) {
    List<String> lista = diccionario.get(clave);
    if (lista == null) {
        return false;
    }
    return lista.remove(valor);
}
```

### Opción 2: Map<K, Set<V>> (cuando los valores no se repiten dentro de una clave)

```java
Map<String, Set<String>> diccionario = new HashMap<>();
```

---

## Pseudocódigo insertar

**Lenguaje natural:** Si la clave ya existe en el mapa, agrega el valor a su lista. Si no existe, crea una lista nueva con ese valor y la asocia a la clave.

**Precondición:** clave y valor no nulos.
**Postcondición:** el par (clave, valor) existe en el diccionario. Pueden existir múltiples valores para la misma clave.

```
insertar(clave: K, valor: V): void
  si diccionario.contiene(clave):
      lista ← diccionario.obtener(clave)
      lista.agregar(valor)
  sino:
      listaNueva ← Lista vacía
      listaNueva.agregar(valor)
      diccionario.poner(clave, listaNew)
```

## Pseudocódigo buscarTodos

**Lenguaje natural:** Busca la clave en el mapa y retorna todos los valores asociados. Si la clave no existe, retorna lista vacía.

```
buscarTodos(clave: K): Lista<V>
  si no diccionario.contiene(clave):
      retornar Lista vacía
  retornar diccionario.obtener(clave)
```
