# Cuadernola — AED Primer Parcial

Material de estudio consolidado para el primer parcial de Algoritmos y Estructuras de Datos — UCU.

---

## Índice

### Elegir la estructura correcta
- [Mentalidad del ingeniero — antes de arrancar](#mentalidad-del-ingeniero--antes-de-arrancar)
- [Fase 1 — preguntas clave](#fase-1--las-preguntas-que-hay-que-hacerse)
- [Fase 2 — tabla de decisión](#fase-2--la-tabla-de-decisión)
- [La trampa más común: ABB vs AVL](#la-trampa-más-común-abb-vs-avl)
- [Cuando hay dos operaciones críticas incompatibles](#cuando-hay-dos-operaciones-críticas-incompatibles)
  - [Caso modelo — Buscador de películas en streaming](#caso-modelo--buscador-de-películas-en-streaming)
  - [Otros casos](#otros-casos-de-dos-operaciones-incompatibles)
- [Cómo redactar la justificación en el parcial](#cómo-redactar-la-justificación-en-el-parcial)

### Pseudocódigos de referencia
- [Lista (basada en arreglo)](#lista-basada-en-arreglo) — `obtener`, `insertar`, `buscar`, `eliminar`
- [Lista Enlazada Simple](#lista-enlazada-simple) — `insertar`, `buscar`, `eliminar`
- [Lista Doblemente Enlazada](#lista-doblemente-enlazada) — `insertar`, `insertarAlFrente`, `buscar`, `eliminar`
- [Pila](#pila) — `apilar`, `desapilar`
- [Cola](#cola) — `encolar`, `desencolar`
- [Cola de Prioridad](#cola-de-prioridad-variante-de-cola) — `encolar(dato, prioridad)`, `desencolar`
- [Conjunto](#conjunto) — `insertar`, `buscar`
- [ABB — Árbol Binario de Búsqueda](#abb--árbol-binario-de-búsqueda) — `insertar`, `buscar`, `eliminar`, recorridos, `calcularAltura`, `calcularTamanio`
  - [Recorridos del árbol](#recorridos-del-árbol) — inOrden, preOrden, postOrden, Recorrido en Anchura
  - [Tabla de posiciones en recorridos (tipo parcial)](#tabla-de-posiciones-en-recorridos-tipo-parcial)
- [AVL — Árbol Binario de Búsqueda Autobalanceado](#avl--árbol-binario-de-búsqueda-autobalanceado) — auxiliares, rotaciones, `balancear`, `insertar`, `eliminar`
- [Clave Compuesta en ABB/AVL](#clave-compuesta-en-abbavl) — cuándo usarla, `compareTo`, `insertar` con clave compuesta

---

## Cómo elegir la estructura de datos correcta

La justificación del TDA vale puntos en el parcial. No alcanza con decir "uso ABB porque sí" — hay que razonar. El proceso tiene dos fases: **diagnosticar el problema**, luego **comparar las opciones**.

---

### Mentalidad del ingeniero — antes de arrancar

**La pregunta que importa es una sola: ¿qué operación se va a ejecutar millones de veces?**

Todo lo demás es secundario. El problema no elige la estructura — la *operación dominante* elige la estructura. Si esa operación es O(n) en vez de O(log n), a escala duele.

**Elegí la estructura más simple que no se rompe.**

Si una Lista alcanza, usá Lista. No uses AVL "por las dudas". La complejidad innecesaria tiene costo real: más código, más bugs, más tiempo de onboarding. La pregunta no es "¿qué estructura aguanta más?" sino "¿qué estructura es suficiente para este problema?"

**Pensá en el tamaño real de los datos.**

Un ABB que degenera a O(n) con 50 elementos es irrelevante. Con 10 millones de registros ordenados secuencialmente es un desastre. El orden asintótico importa a escala — a escala chica, casi cualquier estructura funciona.

**Consultá documentación, no la memoria.**

Ningún ingeniero tiene en la cabeza los casos borde de todas las estructuras. Lo que diferencia al junior del senior no es que el senior recuerda más — es que el senior sabe exactamente qué buscar.

**En dudas reales, prototipá y medí.**

¿HashMap o TreeMap? ¿ArrayList o LinkedList? En problemas reales se escribe un benchmark con datos reales. La intuición teórica a veces se equivoca cuando entran en juego el caché del CPU, la localidad de memoria, o el tamaño real del dataset. En el parcial no vas a hacer benchmarks, pero sí tenés que poder justificar con complejidad teórica por qué una estructura gana a otra para el caso específico del problema.

---

### Fase 1 — Las preguntas que hay que hacerse

**1. ¿Cuál es la operación más crítica del problema?**

- "buscar si un elemento existe" → priorizar búsqueda eficiente
- "procesar elementos en el orden en que llegaron" → priorizar FIFO
- "deshacer operaciones / historia de pasos" → priorizar LIFO
- "recorrer todos los elementos en orden ascendente" → priorizar árbol con inorden
- "insertar y que quede ordenado automáticamente" → árbol

**2. ¿Importa el orden de los elementos?**

- ¿El problema pide resultados ordenados (por año, alfabéticamente, por score)? → Estructura con orden implícito (ABB/AVL con la clave correcta) o estructura + sorting al final.
- ¿El orden de llegada importa? → Lista, Cola, Pila según FIFO/LIFO.
- ¿El orden no importa para nada? → Conjunto o Lista simple.

**3. ¿Qué tan frecuentes son las inserciones?**

- Inserciones frecuentes + búsquedas frecuentes → AVL (O(log n) garantizado, no se degrada)
- Pocas inserciones o datos ya cargados una vez → ABB simple puede ser suficiente
- Solo se inserta una vez al principio → da igual, elegí lo más simple

**4. ¿Puede haber duplicados?**

- No puede haber duplicados → Conjunto (su invariante es unicidad)
- Puede haber duplicados → Lista o árbol (con política definida: insertar igual o ignorar)
- Pueden existir múltiples elementos con el mismo valor en el campo elegido como clave (ej: dos películas del mismo año), pero cada elemento es único por la combinación de dos campos → **ABB/AVL con clave compuesta**. Ver [Clave Compuesta en ABB/AVL](#clave-compuesta-en-abbavl).

**5. ¿Necesito acceder a ambos extremos?**

- Solo al frente (consumir) y al fondo (insertar) → Cola
- Solo a un extremo (apilar y desapilar) → Pila
- Acceso a cualquier posición → Lista

---

### Fase 2 — La tabla de decisión

| Si el problema requiere… | Estructura | Orden de operación clave |
|--------------------------|------------|--------------------------|
| Búsquedas rápidas, inserciones frecuentes, datos crecen | **AVL** | O(log n) garantizado |
| Búsquedas + inserciones, datos relativamente estables | **ABB** | O(log n) promedio, O(n) peor caso |
| Recorrer en orden ascendente automáticamente | **ABB o AVL** (inorden) | O(n) |
| Procesar en orden de llegada (primero en entrar, primero en salir) | **Cola** | O(1) encolar/desencolar |
| Deshacer, historial, recursión simulada | **Pila** | O(1) apilar/desapilar |
| Verificar pertenencia, sin duplicados | **Conjunto** | O(n) buscar |
| Acceso secuencial, orden de inserción importa | **Lista** | O(n) buscar, O(1) insertar al frente |

---

### La trampa más común: ABB vs AVL

La pregunta que separa uno del otro es:

> ¿El conjunto de datos crece continuamente en tiempo de uso?

- **Datos que se cargan una sola vez y luego solo se consultan** → ABB puede servir, si la secuencia de inserción no lo degenera. Pero si no se controla el orden de inserción (datos de un archivo externo), puede degenerar a O(n).
- **Datos que se insertan frecuentemente durante la ejecución** → AVL obligatorio. Garantiza O(log n) sin importar el orden de llegada.

En los parciales, si el enunciado dice cosas como "el catálogo se actualiza con nuevas entradas frecuentemente" → **AVL**.

---

### Cuando hay dos operaciones críticas incompatibles

A veces el enunciado tiene **dos operaciones críticas que favorecen claves distintas**. Una sola estructura no puede optimizar ambas. La solución es mantener **dos estructuras en paralelo**, cada una indexada por la clave que necesita.

**El patrón:**
- Identificás las dos operaciones críticas y la clave que cada una requiere.
- Creás una estructura por clave.
- Cada inserción/eliminación actualiza **ambas estructuras**.
- Costo: O(log n) × 2 en inserción, el doble de memoria. Beneficio: ambas operaciones críticas quedan en O(log n).

> Regla de justificación: "Se acepta el costo de mantener dos estructuras porque las operaciones críticas [A] y [B] requieren claves distintas, y degradar cualquiera de ellas a O(n) es inaceptable dado el volumen y frecuencia de uso."

---

#### Caso modelo — Buscador de películas en streaming

**Enunciado:** Catálogo de 15.000 películas. Se actualiza varias veces al día. Los usuarios hacen búsquedas miles de veces por día. Operaciones requeridas:
1. Buscar por nombre exacto
2. Filtrar por rango de años (ej: 2000–2010)
3. Listar todas ordenadas por año ascendente
4. Agregar películas frecuentemente
5. Eliminar películas (raramente)

**Análisis de operaciones críticas:**

| Operación | Frecuencia | Clave que necesita | Estructura ideal |
|-----------|-----------|-------------------|-----------------|
| Buscar por nombre | Miles/día | nombre | AVL clave=nombre |
| Filtrar por rango de años + listar ordenado | Alta | año | AVL clave=año |
| Insertar | Varias veces/día | — | actualiza ambos AVL |

**¿Por qué no un HashMap por año para el filtrado?**
Un HashMap resuelve búsqueda exacta en O(1) pero **no sirve para rangos ni para orden**: para filtrar 2000–2010 necesitaría recorrer todos los años posibles uno a uno. Un AVL por año resuelve el rango en O(log n + k) y el listado ordenado con inorden en O(n), sin costo extra.

**Solución: dos AVL**

```
AVL_nombre   → clave = nombre de la película    (búsqueda exacta O(log n))
AVL_año      → clave = año de estreno           (rango O(log n + k), listado inorden O(n))
```

**Al insertar una película:**
```
catalogo.agregar(pelicula):
  AVL_nombre.insertar(pelicula.nombre, pelicula)
  AVL_año.insertar(pelicula.año, pelicula)
```

**Al eliminar:**
```
catalogo.eliminar(nombre, año):
  AVL_nombre.eliminar(nombre)
  AVL_año.eliminar(año)
```

**Trade-off explícito para el parcial:**
> "Se mantienen dos AVL: uno indexado por nombre para búsqueda exacta en O(log n), otro indexado por año para filtrado por rango e inorden en O(n). La inserción cuesta O(log n) en cada estructura (doble de lo que costaría con una sola), y la memoria se duplica. Esto se justifica porque la frecuencia de búsqueda (miles/día) supera ampliamente la de inserción (varias/día), y degradar la búsqueda a O(n) sobre 15.000 registros sería inaceptable."

---

#### Otros casos de dos operaciones incompatibles

**Caso: sistema de soporte técnico**

Tickets que se atienden en orden de llegada, pero cualquier agente puede buscar un ticket por ID en cualquier momento.

- Operación crítica 1: procesar en orden FIFO → **Cola**
- Operación crítica 2: buscar por ID → **AVL clave=id**
- Al crear un ticket: encolar en Cola + insertar en AVL.
- Trade-off: inserción doble; a cambio, atención FIFO O(1) y búsqueda O(log n).

---

**Caso: historial de navegación con búsqueda**

Navegador que permite "atrás/adelante" (LIFO) y también buscar cualquier URL visitada.

- Operación crítica 1: deshacer/rehacer → **Pila**
- Operación crítica 2: buscar URL → **AVL clave=url** o **Conjunto** si solo importa pertenencia
- Al visitar una página: apilar en Pila + insertar en AVL.
- Trade-off: inserción doble; deshacer O(1) y búsqueda O(log n).

---

**Caso: catálogo con dos criterios de búsqueda independientes**

Productos que se buscan tanto por código interno como por nombre comercial.

- Operación crítica 1: buscar por código → **AVL clave=código**
- Operación crítica 2: buscar por nombre → **AVL clave=nombre**
- Al insertar un producto: insertar en ambos AVL.
- Nota: si los nombres tienen duplicados (ej: varios productos "Teclado"), el AVL por nombre necesita política para duplicados o se reemplaza por una Lista asociada a esa clave.

---

**Señales en el enunciado de que necesitás dos estructuras:**
- "Se puede buscar por X **o** por Y" con X e Y siendo claves distintas
- "Filtrar por rango de [clave A] **y** buscar exacto por [clave B]"
- "Los usuarios buscan por nombre; el sistema también los procesa en orden de llegada"
- Cualquier combinación de árbol + Cola/Pila (clave de búsqueda + orden temporal)

---

### Cómo redactar la justificación en el parcial

El examinador espera esta estructura:

1. **Nombrar la operación crítica** del problema.
2. **Comparar al menos dos opciones** y descartar la inferior.
3. **Concluir con el orden de tiempo** como argumento.

**Ejemplo (problema de películas con búsquedas por año):**

> "Se elige **ABB** con clave = año de estreno. La operación crítica es la búsqueda y el filtrado por rango cronológico, que se resuelve eficientemente con el recorrido inorden (O(n)), el cual produce los resultados en orden ascendente sin costo adicional de ordenamiento. Una Lista realizaría las mismas operaciones en O(n) pero sin orden implícito, requiriendo un paso extra de ordenamiento. Una Pila o Cola no son adecuadas porque no ofrecen acceso por clave. Como los datos se cargan una sola vez desde el archivo, un ABB simple es suficiente; si hubiera inserciones frecuentes en producción, se preferiría AVL para garantizar O(log n)."

---

## Pseudocódigos de referencia

---

### Lista (basada en arreglo)

**Casos de uso típicos:**
- Acceso directo por índice en O(1): cuando se sabe la posición del elemento.
- Colecciones de tamaño acotado donde las lecturas superan a las inserciones.
- Recorrido posicional cuando el orden numérico de los elementos importa.

**Estructura interna:**

```
Lista<T>:
  datos:   T[]     ← arreglo de capacidad máxima
  tamanio: entero  ← cantidad de elementos actuales (0 si vacía)
```

*Implementación en Java:*

`Lista`
```java
public class Lista<T> {
    private T[] datos;
    private int tamanio;
}
```

#### obtener(indice)

**Lenguaje natural:** Retorna el dato en la posición indicada sin modificar la lista.

**Precondición:** `0 ≤ indice < tamanio`.  
**Postcondición:** retorna `datos[indice]` sin modificar la lista.

```
Lista.obtener(indice: entero): T
  si indice < 0 o indice ≥ tamanio entonces
    lanzar error "Índice fuera de rango"
  fin si
  retornar datos[indice]
fin método
```

**Orden:** O(1)

*Implementación en Java:*

`Lista`
```java
public T obtener(int indice) {
    if (indice < 0 || indice >= tamanio) throw new RuntimeException("Índice fuera de rango");
    return datos[indice]; // acceso directo por índice
}
```

---

#### insertar(dato) — al final

**Lenguaje natural:** Agrega el dato al final del arreglo e incrementa el tamaño.

**Precondición:** `tamanio < capacidadMaxima`.  
**Postcondición:** el nuevo dato queda en `datos[tamanio - 1]`; el tamaño aumenta en 1.

```
Lista.insertar(dato: T): void
  si tamanio = capacidadMaxima entonces
    lanzar error "Lista llena"
  fin si
  datos[tamanio] ← dato
  tamanio ← tamanio + 1
fin método
```

**Orden:** O(1)

*Implementación en Java:*

`Lista`
```java
public void insertar(T dato) {
    if (tamanio == datos.length) throw new RuntimeException("Lista llena");
    datos[tamanio] = dato; // agrega al final
    tamanio++;
}
```

---

#### insertar(indice, dato) — en posición

**Lenguaje natural:** Desplaza los elementos desde `indice` en adelante una posición a la derecha para abrir hueco, luego coloca el dato.

**Precondición:** `0 ≤ indice ≤ tamanio` y `tamanio < capacidadMaxima`.  
**Postcondición:** `datos[indice]` contiene el nuevo dato; los elementos que estaban desde `indice` en adelante se desplazan una posición a la derecha.

```
Lista.insertar(indice: entero, dato: T): void
  si indice < 0 o indice > tamanio o tamanio = capacidadMaxima entonces
    lanzar error "Inserción inválida"
  fin si
  i ← tamanio - 1
  mientras i ≥ indice hacer
    datos[i + 1] ← datos[i]
    i ← i - 1
  fin mientras
  datos[indice] ← dato
  tamanio ← tamanio + 1
fin método
```

**Orden:** O(n) — desplazamiento de hasta n elementos.

*Implementación en Java:*

`Lista`
```java
public void insertar(int indice, T dato) {
    if (indice < 0 || indice > tamanio || tamanio == datos.length) throw new RuntimeException("Inserción inválida");
    for (int i = tamanio - 1; i >= indice; i--) datos[i + 1] = datos[i]; // desplaza a la derecha
    datos[indice] = dato;
    tamanio++;
}
```

---

#### buscar(dato)

**Lenguaje natural:** Recorre el arreglo comparando elementos; retorna el índice del primero que coincide o −1.

**Precondición:** ninguna.  
**Postcondición:** retorna el índice del primer elemento igual a `dato`, o `−1` si no existe.

```
Lista.buscar(dato: T): entero
  i ← 0
  mientras i < tamanio hacer
    si datos[i] = dato entonces
      retornar i
    fin si
    i ← i + 1
  fin mientras
  retornar -1
fin método
```

**Orden:** O(n)

*Implementación en Java:*

`Lista`
```java
public int buscar(T dato) {
    for (int i = 0; i < tamanio; i++) {
        if (datos[i].equals(dato)) return i; // retorna índice del primero que coincide
    }
    return -1;
}
```

---

#### eliminar(indice)

**Lenguaje natural:** Desplaza los elementos desde `indice + 1` en adelante una posición a la izquierda para cubrir el hueco, luego decrementa el tamaño.

**Precondición:** `0 ≤ indice < tamanio`.  
**Postcondición:** el elemento en `indice` es eliminado; los elementos posteriores se desplazan una posición a la izquierda; el tamaño disminuye en 1.

```
Lista.eliminar(indice: entero): void
  si indice < 0 o indice ≥ tamanio entonces
    lanzar error "Índice fuera de rango"
  fin si
  i ← indice
  mientras i < tamanio - 1 hacer
    datos[i] ← datos[i + 1]
    i ← i + 1
  fin mientras
  tamanio ← tamanio - 1
fin método
```

**Orden:** O(n) — desplazamiento de hasta n−1 elementos.

*Implementación en Java:*

`Lista`
```java
public void eliminar(int indice) {
    if (indice < 0 || indice >= tamanio) throw new RuntimeException("Índice fuera de rango");
    for (int i = indice; i < tamanio - 1; i++) datos[i] = datos[i + 1]; // desplaza a la izquierda
    tamanio--;
}
```

---

### Lista Enlazada Simple

**Casos de uso típicos:**
- Acumular resultados de un recorrido (ej: inOrden llena una lista con los datos del árbol).
- Lista blanca/negra en el patrón `comboViable` / `preparadoViable`.
- Camino desde la raíz hasta un nodo (ej: `encontrarCamino` para parentesco).
- Inserción al frente en O(1) cuando el orden no importa y el tamaño varía dinámicamente.

**Estructura interna:**

```
Nodo<T>:
  etiqueta:  Comparable
  dato:      T
  siguiente: Nodo<T>

Lista<T>:
  primero: Nodo<T>  ← nulo si la lista está vacía
```

*Implementación en Java:*

`Nodo`
```java
public class Nodo<T> {
    private Comparable etiqueta;
    private T dato;
    private Nodo<T> siguiente;
}
```

`Lista`
```java
public class Lista<T> {
    private Nodo<T> primero;
}
```

#### insertar(etiqueta, dato) — al final

**Lenguaje natural:** Recorre hasta el último nodo y lo enlaza al final; si está vacía, coloca el nodo como primero.

**Precondición:** ninguna.  
**Postcondición:** el nodo queda como último elemento; el tamaño aumenta en 1.

```
Lista.insertar(etiqueta: Comparable, dato: T): void
  nodo ← nuevo Nodo(etiqueta, dato)
  si esVacia() entonces
    primero ← nodo
  sino
    aux ← primero
    mientras aux.getSiguiente() ≠ nulo hacer
      aux ← aux.getSiguiente()
    fin mientras
    aux.setSiguiente(nodo)
  fin si
fin método
```

**Orden:** O(n)

*Implementación en Java:*

`Lista`
```java
public void insertar(Comparable etiqueta, T dato) {
    Nodo<T> nodo = new Nodo<>(etiqueta, dato);
    if (primero == null) {
        primero = nodo;
    } else {
        Nodo<T> aux = primero;
        while (aux.getSiguiente() != null) aux = aux.getSiguiente(); // llega al último
        aux.setSiguiente(nodo);
    }
}
```

---

#### buscar(clave)

**Lenguaje natural:** Recorre comparando etiquetas; retorna el primer nodo que coincide o nulo.

**Precondición:** ninguna.  
**Postcondición:** retorna el primer nodo con `etiqueta = clave`, o `nulo`.

```
Lista.buscar(clave: Comparable): Nodo<T>
  aux ← primero
  mientras aux ≠ nulo hacer
    si aux.getEtiqueta() = clave entonces
      retornar aux
    fin si
    aux ← aux.getSiguiente()
  fin mientras
  retornar nulo
fin método
```

**Orden:** O(n)

*Implementación en Java:*

`Lista`
```java
public Nodo<T> buscar(Comparable clave) {
    Nodo<T> aux = primero;
    while (aux != null) {
        if (aux.getEtiqueta().equals(clave)) return aux; // encontrado
        aux = aux.getSiguiente();
    }
    return null;
}
```

---

#### eliminar(clave)

**Lenguaje natural:** Desvincula el nodo con esa clave; si es el primero actualiza `primero`, si no conecta el antecesor al sucesor.

**Precondición:** ninguna.  
**Postcondición:** si existía un nodo con `etiqueta = clave`, es removido y retorna `verdadero`. Si no, retorna `falso`.

```
Lista.eliminar(clave: Comparable): booleano
  si esVacia() entonces
    retornar falso
  fin si
  si primero.getEtiqueta() = clave entonces
    primero ← primero.getSiguiente()
    retornar verdadero
  fin si
  aux ← primero
  mientras aux.getSiguiente() ≠ nulo hacer
    si aux.getSiguiente().getEtiqueta() = clave entonces
      aux.setSiguiente(aux.getSiguiente().getSiguiente())
      retornar verdadero
    fin si
    aux ← aux.getSiguiente()
  fin mientras
  retornar falso
fin método
```

**Orden:** O(n)

*Implementación en Java:*

`Lista`
```java
public boolean eliminar(Comparable clave) {
    if (primero == null) return false;
    if (primero.getEtiqueta().equals(clave)) { primero = primero.getSiguiente(); return true; } // era el primero
    Nodo<T> aux = primero;
    while (aux.getSiguiente() != null) {
        if (aux.getSiguiente().getEtiqueta().equals(clave)) {
            aux.setSiguiente(aux.getSiguiente().getSiguiente()); // desvincula el nodo
            return true;
        }
        aux = aux.getSiguiente();
    }
    return false;
}
```

---

### Lista Doblemente Enlazada

**Casos de uso típicos:**
- Recorrido en ambas direcciones: historial de navegación (atrás/adelante), editor de texto con cursor.
- Eliminación eficiente cuando se tiene referencia directa al nodo: no se necesita rastrear el antecesor por separado.
- Implementación de deque (cola doble): insertar y eliminar en ambos extremos en O(1).

**Estructura interna:**

```
NodoDoble<T>:
  etiqueta:  Comparable
  dato:      T
  anterior:  NodoDoble<T>
  siguiente: NodoDoble<T>

ListaDoble<T>:
  primero: NodoDoble<T>  ← nulo si la lista está vacía
  ultimo:  NodoDoble<T>  ← nulo si la lista está vacía
```

*Implementación en Java:*

`NodoDoble`
```java
public class NodoDoble<T> {
    private Comparable etiqueta;
    private T dato;
    private NodoDoble<T> anterior;
    private NodoDoble<T> siguiente;
}
```

`ListaDoble`
```java
public class ListaDoble<T> {
    private NodoDoble<T> primero;
    private NodoDoble<T> ultimo;
}
```

#### insertar(etiqueta, dato) — al final

**Lenguaje natural:** Crea el nodo, lo enlaza como sucesor del último y actualiza `ultimo`; si la lista estaba vacía inicializa también `primero`. El nuevo nodo apunta de vuelta al ex-último con su puntero `anterior`.

**Precondición:** ninguna.  
**Postcondición:** el nodo queda como último elemento; `ultimo` apunta a él; el puntero `anterior` del nuevo nodo apunta al ex-último.

```
ListaDoble.insertar(etiqueta: Comparable, dato: T): void
  nodo ← nuevo NodoDoble(etiqueta, dato)
  si esVacia() entonces
    primero ← nodo
    ultimo  ← nodo
  sino
    nodo.setAnterior(ultimo)
    ultimo.setSiguiente(nodo)
    ultimo ← nodo
  fin si
fin método
```

**Orden:** O(1) — gracias al puntero `ultimo`.

*Implementación en Java:*

`ListaDoble`
```java
public void insertar(Comparable etiqueta, T dato) {
    NodoDoble<T> nodo = new NodoDoble<>(etiqueta, dato);
    if (primero == null) {
        primero = nodo;
        ultimo = nodo;
    } else {
        nodo.setAnterior(ultimo);    // apunta hacia atrás
        ultimo.setSiguiente(nodo);   // apunta hacia adelante
        ultimo = nodo;
    }
}
```

---

#### insertarAlFrente(etiqueta, dato)

**Lenguaje natural:** Crea el nodo, lo coloca antes del `primero` actual y actualiza `primero`; el ex-primero enlaza de vuelta con su puntero `anterior`.

**Precondición:** ninguna.  
**Postcondición:** el nodo queda como primer elemento; `primero` apunta a él; el ex-primero tiene su `anterior` apuntando al nuevo nodo.

```
ListaDoble.insertarAlFrente(etiqueta: Comparable, dato: T): void
  nodo ← nuevo NodoDoble(etiqueta, dato)
  si esVacia() entonces
    primero ← nodo
    ultimo  ← nodo
  sino
    nodo.setSiguiente(primero)
    primero.setAnterior(nodo)
    primero ← nodo
  fin si
fin método
```

**Orden:** O(1)

*Implementación en Java:*

`ListaDoble`
```java
public void insertarAlFrente(Comparable etiqueta, T dato) {
    NodoDoble<T> nodo = new NodoDoble<>(etiqueta, dato);
    if (primero == null) {
        primero = nodo;
        ultimo = nodo;
    } else {
        nodo.setSiguiente(primero);  // apunta hacia adelante
        primero.setAnterior(nodo);   // ex-primero apunta hacia atrás
        primero = nodo;
    }
}
```

---

#### buscar(clave)

**Lenguaje natural:** Recorre desde `primero` comparando etiquetas; retorna el nodo que coincide o nulo. También puede recorrerse desde `ultimo` hacia atrás usando `anterior`.

**Precondición:** ninguna.  
**Postcondición:** retorna el primer nodo con `etiqueta = clave`, o `nulo`.

```
ListaDoble.buscar(clave: Comparable): NodoDoble<T>
  aux ← primero
  mientras aux ≠ nulo hacer
    si aux.getEtiqueta() = clave entonces
      retornar aux
    fin si
    aux ← aux.getSiguiente()
  fin mientras
  retornar nulo
fin método
```

**Orden:** O(n)

*Implementación en Java:*

`ListaDoble`
```java
public NodoDoble<T> buscar(Comparable clave) {
    NodoDoble<T> aux = primero;
    while (aux != null) {
        if (aux.getEtiqueta().equals(clave)) return aux; // encontrado
        aux = aux.getSiguiente();
    }
    return null;
}
```

---

#### eliminar(clave)

**Lenguaje natural:** Localiza el nodo con la clave; lo desvincula enlazando su antecesor con su sucesor directamente a través del puntero `anterior`, sin necesitar una variable extra para el antecesor. Actualiza `primero` o `ultimo` si el nodo era un extremo.

**Precondición:** ninguna.  
**Postcondición:** si existía un nodo con `etiqueta = clave`, es removido y retorna `verdadero`; `primero` y `ultimo` se actualizan si el nodo era un extremo.

```
ListaDoble.eliminar(clave: Comparable): booleano
  nodo ← buscar(clave)
  si nodo = nulo entonces
    retornar falso
  fin si
  si nodo.getAnterior() ≠ nulo entonces
    nodo.getAnterior().setSiguiente(nodo.getSiguiente())
  sino
    primero ← nodo.getSiguiente()
  fin si
  si nodo.getSiguiente() ≠ nulo entonces
    nodo.getSiguiente().setAnterior(nodo.getAnterior())
  sino
    ultimo ← nodo.getAnterior()
  fin si
  retornar verdadero
fin método
```

**Orden:** O(n) — dominado por `buscar`.

*Implementación en Java:*

`ListaDoble`
```java
public boolean eliminar(Comparable clave) {
    NodoDoble<T> nodo = buscar(clave);
    if (nodo == null) return false;
    if (nodo.getAnterior() != null) nodo.getAnterior().setSiguiente(nodo.getSiguiente()); // desvincula hacia adelante
    else primero = nodo.getSiguiente();                                                    // era el primero
    if (nodo.getSiguiente() != null) nodo.getSiguiente().setAnterior(nodo.getAnterior()); // desvincula hacia atrás
    else ultimo = nodo.getAnterior();                                                      // era el último
    return true;
}
```

---

### Pila

**Casos de uso típicos:**
- Simular recursión de forma iterativa (cada llamada equivale a un `apilar`).
- Verificar balanceo de paréntesis / expresiones anidadas.
- Historial de operaciones: "deshacer" → desapilar el último estado.

**Estructura interna:**

```
Nodo<T>:
  dato:      T
  siguiente: Nodo<T>

Pila<T>:
  tope: Nodo<T>  ← nulo si la pila está vacía
```

*Implementación en Java:*

`Nodo`
```java
public class Nodo<T> {
    private T dato;
    private Nodo<T> siguiente;
}
```

`Pila`
```java
public class Pila<T> {
    private Nodo<T> tope;
}
```

#### apilar(dato)

**Lenguaje natural:** Coloca el nuevo nodo al tope y lo enlaza con el tope anterior.

**Precondición:** ninguna.  
**Postcondición:** el nuevo nodo es el tope; el tamaño aumenta en 1.

```
Pila.apilar(dato: T): void
  nodo ← nuevo Nodo(dato)
  nodo.setSiguiente(tope)
  tope ← nodo
fin método
```

**Orden:** O(1)

*Implementación en Java:*

`Pila`
```java
public void apilar(T dato) {
    Nodo<T> nodo = new Nodo<>(dato);
    nodo.setSiguiente(tope); // enlaza al tope actual
    tope = nodo;
}
```

---

#### desapilar()

**Lenguaje natural:** Retorna el dato del tope y avanza `tope` al siguiente nodo.

**Precondición:** `¬esVacia()`.  
**Postcondición:** el nodo del tope es removido y su dato es retornado; el tamaño disminuye en 1.

```
Pila.desapilar(): T
  si esVacia() entonces
    lanzar error "Pila vacía"
  fin si
  dato ← tope.getDato()
  tope ← tope.getSiguiente()
  retornar dato
fin método
```

**Orden:** O(1)

*Implementación en Java:*

`Pila`
```java
public T desapilar() {
    if (tope == null) throw new RuntimeException("Pila vacía");
    T dato = tope.getDato();
    tope = tope.getSiguiente(); // avanza el tope
    return dato;
}
```

---

### Cola

**Casos de uso típicos:**
- Procesar en orden de llegada: turnos, colas de atención, colas de impresión.
- Recorrido en Anchura de árboles (ver sección de árboles).

**Estructura interna:**

```
Nodo<T>:
  dato:      T
  siguiente: Nodo<T>

Cola<T>:
  frente:    Nodo<T>  ← nulo si la cola está vacía
  posterior: Nodo<T>  ← nulo si la cola está vacía
```

*Implementación en Java:*

`Nodo`
```java
public class Nodo<T> {
    private T dato;
    private Nodo<T> siguiente;
}
```

`Cola`
```java
public class Cola<T> {
    private Nodo<T> frente;
    private Nodo<T> posterior;
}
```

#### encolar(dato)

**Lenguaje natural:** Enlaza el nuevo nodo al final; si la cola estaba vacía inicializa también `frente`.

**Precondición:** ninguna.  
**Postcondición:** el nuevo nodo queda al final; `posterior` apunta a él.

```
Cola.encolar(dato: T): void
  nodo ← nuevo Nodo(dato)
  si esVacia() entonces
    frente    ← nodo
    posterior ← nodo
  sino
    posterior.setSiguiente(nodo)
    posterior ← nodo
  fin si
fin método
```

**Orden:** O(1)

*Implementación en Java:*

`Cola`
```java
public void encolar(T dato) {
    Nodo<T> nodo = new Nodo<>(dato);
    if (frente == null) {
        frente = nodo;
        posterior = nodo;
    } else {
        posterior.setSiguiente(nodo); // enlaza al final
        posterior = nodo;
    }
}
```

---

#### desencolar()

**Lenguaje natural:** Retorna el dato del frente, avanza `frente`; si la cola quedó vacía actualiza `posterior ← nulo`.

**Precondición:** `¬esVacia()`.  
**Postcondición:** el nodo del frente es removido; si la cola quedó vacía, `posterior ← nulo`.

```
Cola.desencolar(): T
  si esVacia() entonces
    lanzar error "Cola vacía"
  fin si
  dato   ← frente.getDato()
  frente ← frente.getSiguiente()
  si frente = nulo entonces
    posterior ← nulo
  fin si
  retornar dato
fin método
```

**Orden:** O(1)

*Implementación en Java:*

`Cola`
```java
public T desencolar() {
    if (frente == null) throw new RuntimeException("Cola vacía");
    T dato = frente.getDato();
    frente = frente.getSiguiente();       // avanza el frente
    if (frente == null) posterior = null; // cola quedó vacía
    return dato;
}
```

---

### Cola de Prioridad (variante de Cola)

**Cuándo usar esta variante en lugar de Cola simple:** cuando los elementos no son equivalentes entre sí y algunos deben atenderse antes independientemente del orden de llegada. Ejemplos: sistema operativo que prioriza procesos críticos, sala de emergencias, impresión urgente.

**Diferencia con Cola:** `encolar` inserta en la posición correcta según prioridad en vez de siempre al final. El invariante es que la lista siempre está ordenada: el frente tiene siempre el elemento de mayor prioridad. `desencolar` no cambia.

**Convención:** número menor = mayor prioridad (prioridad 1 se atiende antes que prioridad 2).

**Estructura interna:**

```
Nodo<T>:
  dato:      T
  prioridad: entero
  siguiente: Nodo<T>

ColaPrioridad<T>:
  frente: Nodo<T>  ← siempre el de mayor prioridad; nulo si vacía
```

*Implementación en Java:*

`Nodo`
```java
public class Nodo<T> {
    private T dato;
    private int prioridad;
    private Nodo<T> siguiente;
}
```

`ColaPrioridad`
```java
public class ColaPrioridad<T> {
    private Nodo<T> frente;
}
```

#### encolar(dato, prioridad)

**Lenguaje natural:** Recorre desde el frente hasta encontrar el primer nodo con prioridad menor al nuevo; inserta antes de él. Si la cola está vacía o el nuevo tiene mayor prioridad que el frente, inserta al principio.

**Precondición:** ninguna.  
**Postcondición:** el nodo queda en la posición que mantiene el orden por prioridad ascendente.

```
ColaPrioridad.encolar(dato: T, prioridad: entero): void
  nodo ← nuevo Nodo(dato, prioridad)
  si esVacia() o prioridad < frente.prioridad entonces
    nodo.setSiguiente(frente)
    frente ← nodo
  sino
    aux ← frente
    mientras aux.getSiguiente() ≠ nulo y
             aux.getSiguiente().prioridad ≤ prioridad hacer
      aux ← aux.getSiguiente()
    fin mientras
    nodo.setSiguiente(aux.getSiguiente())
    aux.setSiguiente(nodo)
  fin si
fin método
```

**Orden:** O(n) — hay que encontrar la posición correcta.

*Implementación en Java:*

`ColaPrioridad`
```java
public void encolar(T dato, int prioridad) {
    Nodo<T> nodo = new Nodo<>(dato, prioridad);
    if (frente == null || prioridad < frente.getPrioridad()) {
        nodo.setSiguiente(frente); // inserta al frente
        frente = nodo;
    } else {
        Nodo<T> aux = frente;
        while (aux.getSiguiente() != null && aux.getSiguiente().getPrioridad() <= prioridad)
            aux = aux.getSiguiente(); // busca la posición correcta
        nodo.setSiguiente(aux.getSiguiente());
        aux.setSiguiente(nodo);
    }
}
```

---

#### desencolar()

Idéntico al de Cola simple: retorna el dato del frente y avanza `frente`. El frente es siempre el de mayor prioridad por el invariante que mantiene `encolar`.

**Orden:** O(1)

---

**Trade-off respecto a Cola simple:**

| Operación | Cola simple | Cola de Prioridad |
|-----------|------------|-------------------|
| `encolar` | O(1) | O(n) |
| `desencolar` | O(1) | O(1) |

Se paga O(n) al insertar para garantizar O(1) al sacar. Si las inserciones fueran muy frecuentes con n grande, la alternativa eficiente es un Heap (O(log n) en ambas operaciones), pero es una estructura más compleja fuera del alcance del primer parcial.

---

### Conjunto

**Casos de uso típicos:**
- Lista blanca cuando **no puede haber duplicados** y solo importa la pertenencia.
- Calcular unión, intersección o complemento de grupos.
- Marcar nodos ya visitados en un recorrido (`visited set`).

**Estructura interna:**

```
Nodo<T>:
  dato:      T
  siguiente: Nodo<T>

Conjunto<T>:
  primero: Nodo<T>  ← nulo si el conjunto está vacío
```

*Implementación en Java:*

`Nodo`
```java
public class Nodo<T> {
    private T dato;
    private Nodo<T> siguiente;
}
```

`Conjunto`
```java
public class Conjunto<T> {
    private Nodo<T> primero;
}
```

#### insertar(dato)

**Lenguaje natural:** Verifica con `buscar` que no exista; si no, inserta al frente. El invariante es que no hay duplicados.

**Precondición:** ninguna.  
**Postcondición:** si `dato` no estaba en el conjunto, se agrega y retorna `verdadero`; si ya existía, retorna `falso`.

```
Conjunto.insertar(dato: T): booleano
  si buscar(dato) ≠ nulo entonces
    retornar falso
  fin si
  nodo ← nuevo Nodo(dato)
  nodo.setSiguiente(primero)
  primero ← nodo
  retornar verdadero
fin método
```

**Orden:** O(n) — dominado por `buscar`.

*Implementación en Java:*

`Conjunto`
```java
public boolean insertar(T dato) {
    if (buscar(dato) != null) return false; // ya existe, no se inserta
    Nodo<T> nodo = new Nodo<>(dato);
    nodo.setSiguiente(primero); // inserta al frente
    primero = nodo;
    return true;
}
```

---

#### buscar(dato)

**Lenguaje natural:** Recorre comparando cada elemento; retorna el nodo o nulo.

**Precondición:** ninguna.  
**Postcondición:** retorna el nodo que contiene `dato`, o `nulo`.

```
Conjunto.buscar(dato: T): Nodo<T>
  aux ← primero
  mientras aux ≠ nulo hacer
    si aux.getDato() = dato entonces
      retornar aux
    fin si
    aux ← aux.getSiguiente()
  fin mientras
  retornar nulo
fin método
```

**Orden:** O(n)

*Implementación en Java:*

`Conjunto`
```java
public Nodo<T> buscar(T dato) {
    Nodo<T> aux = primero;
    while (aux != null) {
        if (aux.getDato().equals(dato)) return aux; // encontrado
        aux = aux.getSiguiente();
    }
    return null;
}
```

---

### ABB — Árbol Binario de Búsqueda

**Casos de uso típicos:**
- Catálogos con búsqueda por clave (películas por año, productos por código) cuando los datos se cargan una sola vez y no crecen dinámicamente.
- Obtener elementos en orden ascendente sin costo extra de ordenamiento (inOrden).
- Filtrar por rango de valores: recorrer solo la rama relevante según la clave.
- Base para cualquier algoritmo recursivo árbol/nodo pedido en el Ejercicio 2 del parcial.
- **Limitación:** si el orden de inserción es secuencial (ej: 1, 2, 3, 4…) el árbol se degenera a una lista y todas las operaciones pasan a O(n). En ese caso usar AVL.

**Estructura interna:**

```
TElementoAB<T>:
  etiqueta: Comparable
  dato:     T
  hijoIzq:  TElementoAB<T>  ← nulo si no tiene hijo izquierdo
  hijoDer:  TElementoAB<T>  ← nulo si no tiene hijo derecho

TArbolBB<T>:
  raiz: TElementoAB<T>  ← nulo si el árbol está vacío
```

*Implementación en Java:*

`TElementoAB`
```java
public class TElementoAB<T> {
    private Comparable etiqueta;
    private T dato;
    private TElementoAB<T> hijoIzq;
    private TElementoAB<T> hijoDer;
}
```

`TArbolBB`
```java
public class TArbolBB<T> {
    private TElementoAB<T> raiz;
}
```

#### insertar(etiqueta, dato)

**Lenguaje natural:** Si está vacío coloca como raíz; si no, delega recursivamente: menores a izquierda, mayores a derecha, duplicados se rechazan.

**Precondición:** `etiqueta ≠ nulo`.  
**Postcondición:** el elemento queda insertado respetando la propiedad ABB, o retorna `falso` si la etiqueta ya existía.

```
TArbolBB.insertar(etiqueta: Comparable, dato: T): booleano
  elemento ← nuevo TElementoAB(etiqueta, dato)
  si esVacio() entonces
    raiz ← elemento
    retornar verdadero
  sino
    retornar raiz.insertar(elemento)
  fin si
fin método

TElementoAB.insertar(elemento: TElementoAB<T>): booleano
  si elemento.etiqueta < this.etiqueta entonces
    si hijoIzq = nulo entonces
      hijoIzq ← elemento
      retornar verdadero
    sino
      retornar hijoIzq.insertar(elemento)
    fin si
  sino si elemento.etiqueta > this.etiqueta entonces
    si hijoDer = nulo entonces
      hijoDer ← elemento
      retornar verdadero
    sino
      retornar hijoDer.insertar(elemento)
    fin si
  sino
    retornar falso
  fin si
fin método
```

**Orden:** O(h). Promedio O(log n), peor caso O(n).

*Implementación en Java:*

`TArbolBB`
```java
public boolean insertar(Comparable etiqueta, T dato) {
    TElementoAB<T> elemento = new TElementoAB<>(etiqueta, dato);
    if (raiz == null) { raiz = elemento; return true; } // árbol vacío
    return raiz.insertar(elemento);
}
```

`TElementoAB`
```java
public boolean insertar(TElementoAB<T> elemento) {
    if (elemento.getEtiqueta().compareTo(this.etiqueta) < 0) {
        if (hijoIzq == null) { hijoIzq = elemento; return true; } // inserta a la izquierda
        return hijoIzq.insertar(elemento);
    } else if (elemento.getEtiqueta().compareTo(this.etiqueta) > 0) {
        if (hijoDer == null) { hijoDer = elemento; return true; } // inserta a la derecha
        return hijoDer.insertar(elemento);
    }
    return false; // duplicado, no se inserta
}
```

---

#### buscar(etiqueta)

**Lenguaje natural:** Baja a izquierda si es menor, a derecha si es mayor, hasta encontrar la etiqueta o llegar a nulo.

**Precondición:** ninguna.  
**Postcondición:** retorna el dato del elemento con esa etiqueta, o `nulo` si no existe.

```
TArbolBB.buscar(etiqueta: Comparable): T
  si esVacio() entonces
    retornar nulo
  fin si
  nodo ← raiz.buscar(etiqueta)
  si nodo ≠ nulo entonces
    retornar nodo.getDato()
  sino
    retornar nulo
  fin si
fin método

TElementoAB.buscar(etiqueta: Comparable): TElementoAB<T>
  si etiqueta = this.etiqueta entonces
    retornar this
  sino si etiqueta < this.etiqueta entonces
    si hijoIzq ≠ nulo entonces
      retornar hijoIzq.buscar(etiqueta)
    sino
      retornar nulo
    fin si
  sino
    si hijoDer ≠ nulo entonces
      retornar hijoDer.buscar(etiqueta)
    sino
      retornar nulo
    fin si
  fin si
fin método
```

**Orden:** O(h). Promedio O(log n), peor caso O(n).

*Implementación en Java:*

`TArbolBB`
```java
public T buscar(Comparable etiqueta) {
    if (raiz == null) return null;
    TElementoAB<T> nodo = raiz.buscar(etiqueta);
    return nodo != null ? nodo.getDato() : null;
}
```

`TElementoAB`
```java
public TElementoAB<T> buscar(Comparable etiqueta) {
    int cmp = etiqueta.compareTo(this.etiqueta);
    if (cmp == 0) return this;                                              // encontrado
    if (cmp < 0) return hijoIzq != null ? hijoIzq.buscar(etiqueta) : null; // busca a la izquierda
    return hijoDer != null ? hijoDer.buscar(etiqueta) : null;               // busca a la derecha
}
```

---

#### eliminar(etiqueta)

**Lenguaje natural:** Tres casos: sin hijos → desvincula; un hijo → el hijo reemplaza; dos hijos → reemplaza por el predecesor inorden (máximo del subárbol izquierdo).

**Precondición:** ninguna.  
**Postcondición:** si existía un nodo con esa etiqueta, es eliminado manteniendo la propiedad ABB.

```
TArbolBB.eliminar(etiqueta: Comparable): void
  si ¬esVacio() entonces
    raiz ← raiz.eliminar(etiqueta)
  fin si
fin método

TElementoAB.eliminar(etiqueta: Comparable): TElementoAB<T>
  si etiqueta < this.etiqueta entonces
    si hijoIzq ≠ nulo entonces
      hijoIzq ← hijoIzq.eliminar(etiqueta)
    fin si
    retornar this
  sino si etiqueta > this.etiqueta entonces
    si hijoDer ≠ nulo entonces
      hijoDer ← hijoDer.eliminar(etiqueta)
    fin si
    retornar this
  sino
    retornar quitarNodo()
  fin si
fin método

TElementoAB.quitarNodo(): TElementoAB<T>
  // Caso 1 y 2: cero o un hijo
  si hijoIzq = nulo entonces retornar hijoDer
  si hijoDer = nulo entonces retornar hijoIzq
  // Caso 3: dos hijos → predecesor inorden (máximo del subárbol izquierdo)
  hijo  ← hijoIzq
  padre ← this
  mientras hijo.hijoDer ≠ nulo hacer
    padre ← hijo
    hijo  ← hijo.hijoDer
  fin mientras
  si padre ≠ this entonces
    padre.hijoDer ← hijo.hijoIzq
    hijo.hijoIzq  ← hijoIzq
  fin si
  hijo.hijoDer ← hijoDer
  retornar hijo
fin método
```

**Orden:** O(h). Promedio O(log n), peor caso O(n).

*Implementación en Java:*

`TArbolBB`
```java
public void eliminar(Comparable etiqueta) {
    if (raiz != null) raiz = raiz.eliminar(etiqueta);
}
```

`TElementoAB`
```java
public TElementoAB<T> eliminar(Comparable etiqueta) {
    int cmp = etiqueta.compareTo(this.etiqueta);
    if (cmp < 0) { if (hijoIzq != null) hijoIzq = hijoIzq.eliminar(etiqueta); return this; }
    if (cmp > 0) { if (hijoDer != null) hijoDer = hijoDer.eliminar(etiqueta); return this; }
    return quitarNodo(); // encontrado, elimina
}

public TElementoAB<T> quitarNodo() {
    if (hijoIzq == null) return hijoDer; // caso 0 o 1 hijo derecho
    if (hijoDer == null) return hijoIzq; // caso 1 hijo izquierdo
    TElementoAB<T> hijo = hijoIzq, padre = this;
    while (hijo.hijoDer != null) { padre = hijo; hijo = hijo.hijoDer; } // predecesor inorden
    if (padre != this) { padre.hijoDer = hijo.hijoIzq; hijo.hijoIzq = hijoIzq; }
    hijo.hijoDer = hijoDer;
    return hijo;
}
```

---

#### Recorridos del árbol

Los tres recorridos recursivos visitan todos los nodos exactamente una vez — todos son O(n). La diferencia es el **momento** en que se visita la raíz respecto a los subárboles. El Recorrido en Anchura también es O(n) pero opera nivel por nivel usando una Cola auxiliar.

| Recorrido | Orden | Cuándo usarlo |
|-----------|-------|---------------|
| **Inorden** | izq → raíz → der | ABB produce valores en orden ascendente; filtrar y recolectar resultados ordenados |
| **Preorden** | raíz → izq → der | Copiar o serializar el árbol; procesar el nodo antes de sus hijos |
| **Postorden** | izq → der → raíz | Calcular tamaño/altura/suma; liberar memoria; necesitás los hijos antes que el padre |
| **Recorrido en Anchura** | nivel por nivel | Imprimir el árbol por niveles; encontrar el camino más corto; procesar por cercanía a la raíz |

---

#### inOrden()

**Lenguaje natural:** izq → raíz → der; produce los valores en orden ascendente.

**Precondición:** ninguna.  
**Postcondición:** retorna una lista con los datos en orden ascendente de etiqueta.

```
TArbolBB.inOrden(): Lista<T>
  lista ← nueva Lista vacía
  si ¬esVacio() entonces
    raiz.inOrden(lista)
  fin si
  retornar lista
fin método

TElementoAB.inOrden(lista: Lista<T>): void
  si hijoIzq ≠ nulo entonces hijoIzq.inOrden(lista)
  lista.insertar(this.getDato())
  si hijoDer ≠ nulo entonces hijoDer.inOrden(lista)
fin método
```

**Orden:** O(n)

*Implementación en Java:*

`TArbolBB`
```java
public Lista<T> inOrden() {
    Lista<T> lista = new Lista<>();
    if (raiz != null) raiz.inOrden(lista);
    return lista;
}
```

`TElementoAB`
```java
public void inOrden(Lista<T> lista) {
    if (hijoIzq != null) hijoIzq.inOrden(lista); // 1. subárbol izquierdo
    lista.insertar(this.getDato());               // 2. nodo actual
    if (hijoDer != null) hijoDer.inOrden(lista);  // 3. subárbol derecho
}
```

---

#### preOrden()

**Lenguaje natural:** raíz → izq → der; procesa el nodo antes de sus hijos.

**Precondición:** ninguna.  
**Postcondición:** retorna una lista con los datos en orden raíz → izquierdo → derecho.

```
TArbolBB.preOrden(): Lista<T>
  lista ← nueva Lista vacía
  si ¬esVacio() entonces
    raiz.preOrden(lista)
  fin si
  retornar lista
fin método

TElementoAB.preOrden(lista: Lista<T>): void
  lista.insertar(this.getDato())
  si hijoIzq ≠ nulo entonces hijoIzq.preOrden(lista)
  si hijoDer ≠ nulo entonces hijoDer.preOrden(lista)
fin método
```

**Orden:** O(n)

*Implementación en Java:*

`TArbolBB`
```java
public Lista<T> preOrden() {
    Lista<T> lista = new Lista<>();
    if (raiz != null) raiz.preOrden(lista);
    return lista;
}
```

`TElementoAB`
```java
public void preOrden(Lista<T> lista) {
    lista.insertar(this.getDato());                // 1. nodo actual (primero)
    if (hijoIzq != null) hijoIzq.preOrden(lista); // 2. subárbol izquierdo
    if (hijoDer != null) hijoDer.preOrden(lista);  // 3. subárbol derecho
}
```

---

#### postOrden()

**Lenguaje natural:** izq → der → raíz; el resultado del nodo depende de sus hijos (altura, tamaño, suma).

**Precondición:** ninguna.  
**Postcondición:** retorna una lista con los datos en orden izquierdo → derecho → raíz.

```
TArbolBB.postOrden(): Lista<T>
  lista ← nueva Lista vacía
  si ¬esVacio() entonces
    raiz.postOrden(lista)
  fin si
  retornar lista
fin método

TElementoAB.postOrden(lista: Lista<T>): void
  si hijoIzq ≠ nulo entonces hijoIzq.postOrden(lista)
  si hijoDer ≠ nulo entonces hijoDer.postOrden(lista)
  lista.insertar(this.getDato())
fin método
```

**Orden:** O(n)

*Implementación en Java:*

`TArbolBB`
```java
public Lista<T> postOrden() {
    Lista<T> lista = new Lista<>();
    if (raiz != null) raiz.postOrden(lista); // delega al nodo raíz
    return lista;
}
```

`TElementoAB`
```java
public void postOrden(Lista<T> lista) {
    if (hijoIzq != null) hijoIzq.postOrden(lista); // 1. subárbol izquierdo
    if (hijoDer != null) hijoDer.postOrden(lista);  // 2. subárbol derecho
    lista.insertar(this.getDato());                 // 3. nodo actual (último)
}
```

---

#### recorridoEnAnchura()

**Lenguaje natural:** Visita los nodos nivel por nivel, de arriba hacia abajo y de izquierda a derecha. Usa una Cola interna: encola la raíz, y en cada iteración desencola un nodo, lo procesa, y encola sus hijos. La Cola garantiza que los nodos del nivel actual se procesen antes que los del siguiente.

**Por qué Cola y no recursión:** Los recorridos inorden/preorden/postorden usan la pila de llamadas (recursión), que naturalmente va en profundidad. El Recorrido en Anchura necesita procesar niveles completos antes de bajar, lo que requiere una Cola explícita.

**Precondición:** ninguna.  
**Postcondición:** retorna una lista con los datos en orden de nivel (nivel 0, nivel 1, nivel 2…).

```
TArbolBB.recorridoEnAnchura(): Lista<T>
  lista ← nueva Lista vacía
  si esVacio() entonces
    retornar lista
  fin si
  cola ← nueva Cola vacía
  cola.encolar(raiz)
  mientras ¬cola.esVacia() hacer
    nodo ← cola.desencolar()
    lista.insertar(nodo.getDato())
    si nodo.hijoIzq ≠ nulo entonces cola.encolar(nodo.hijoIzq)
    si nodo.hijoDer ≠ nulo entonces cola.encolar(nodo.hijoDer)
  fin mientras
  retornar lista
fin método
```

**Traza:**

```
        10
       /   \
      5    20
     / \
    2   7
```

| Paso | Cola al inicio | Desencola | Encola |
|------|---------------|-----------|--------|
| 1 | [10] | 10 | 5, 20 |
| 2 | [5, 20] | 5 | 2, 7 |
| 3 | [20, 2, 7] | 20 | — |
| 4 | [2, 7] | 2 | — |
| 5 | [7] | 7 | — |

**Resultado:** 10 → 5 → 20 → 2 → 7

**Orden:** O(n) — cada nodo se encola y desencola exactamente una vez.

*Implementación en Java:*

`TArbolBB`
```java
public Lista<T> recorridoEnAnchura() {
    Lista<T> lista = new Lista<>();
    if (raiz == null) return lista;
    Cola<TElementoAB<T>> cola = new Cola<>();
    cola.encolar(raiz);
    while (!cola.esVacia()) {
        TElementoAB<T> nodo = cola.desencolar();
        lista.insertar(nodo.getDato());                                      // procesa el nodo
        if (nodo.getHijoIzq() != null) cola.encolar(nodo.getHijoIzq());     // encola hijo izquierdo
        if (nodo.getHijoDer() != null) cola.encolar(nodo.getHijoDer());     // encola hijo derecho
    }
    return lista;
}
```

---

#### calcularAltura()

La **altura** es la longitud del camino más largo desde la raíz hasta una hoja. Árbol vacío → −1. Hoja → 0. En general: `max(alturaIzq, alturaDer) + 1`.

**Recorrido:** postorden — necesitás saber la altura de los hijos antes de calcular la del padre.

**Precondición:** ninguna.  
**Postcondición:** retorna la altura del árbol. Si está vacío, retorna −1.

```
TArbolBB.calcularAltura(): entero
  si esVacio() entonces
    retornar -1
  fin si
  retornar raiz.calcularAltura()
fin método

TElementoAB.calcularAltura(): entero
  alturaIzq ← -1
  alturaDer ← -1
  si hijoIzq ≠ nulo entonces
    alturaIzq ← hijoIzq.calcularAltura()
  fin si
  si hijoDer ≠ nulo entonces
    alturaDer ← hijoDer.calcularAltura()
  fin si
  retornar max(alturaIzq, alturaDer) + 1
fin método
```

*Implementación en Java:*

`TArbolBB`
```java
public int calcularAltura() {
    if (raiz == null) return -1;
    return raiz.calcularAltura();
}
```

`TElementoAB`
```java
public int calcularAltura() {
    int alturaIzq = hijoIzq != null ? hijoIzq.calcularAltura() : -1; // altura del subárbol izquierdo
    int alturaDer = hijoDer != null ? hijoDer.calcularAltura() : -1; // altura del subárbol derecho
    return Math.max(alturaIzq, alturaDer) + 1;
}
```

**Traza:**

```
        10
       /   \
      5    20
     / \
    2   7
```

| Llamada | alturaIzq | alturaDer | retorna |
|---------|-----------|-----------|---------|
| nodo 2  | -1        | -1        | 0       |
| nodo 7  | -1        | -1        | 0       |
| nodo 5  | 0 (de 2)  | 0 (de 7)  | 1       |
| nodo 20 | -1        | -1        | 0       |
| nodo 10 | 1 (de 5)  | 0 (de 20) | **2**   |

**Orden:** O(n)

---

#### calcularTamanio()

El **tamaño** es la cantidad total de nodos. Árbol vacío → 0. En general: `1 + tamanioIzq + tamanioDer`.

**Recorrido:** postorden — contás los hijos antes de sumar el nodo actual.

**Precondición:** ninguna.  
**Postcondición:** retorna la cantidad de nodos. Si está vacío, retorna 0.

```
TArbolBB.calcularTamanio(): entero
  si esVacio() entonces
    retornar 0
  fin si
  retornar raiz.calcularTamanio()
fin método

TElementoAB.calcularTamanio(): entero
  tamanio ← 1
  si hijoIzq ≠ nulo entonces
    tamanio ← tamanio + hijoIzq.calcularTamanio()
  fin si
  si hijoDer ≠ nulo entonces
    tamanio ← tamanio + hijoDer.calcularTamanio()
  fin si
  retornar tamanio
fin método
```

*Implementación en Java:*

`TArbolBB`
```java
public int calcularTamanio() {
    if (raiz == null) return 0;
    return raiz.calcularTamanio();
}
```

`TElementoAB`
```java
public int calcularTamanio() {
    int tamanio = 1;
    if (hijoIzq != null) tamanio += hijoIzq.calcularTamanio(); // suma subárbol izquierdo
    if (hijoDer != null) tamanio += hijoDer.calcularTamanio(); // suma subárbol derecho
    return tamanio;
}
```

**Traza:**

```
        10
       /   \
      5    20
     / \
    2   7
```

| Llamada | tamanio inicial | + hijoIzq | + hijoDer | retorna |
|---------|-----------------|-----------|-----------|---------|
| nodo 2  | 1               | —         | —         | 1       |
| nodo 7  | 1               | —         | —         | 1       |
| nodo 5  | 1               | + 1 (2)   | + 1 (7)   | 3       |
| nodo 20 | 1               | —         | —         | 1       |
| nodo 10 | 1               | + 3 (5)   | + 1 (20)  | **5**   |

**Orden:** O(n)

---

### Tabla de posiciones en recorridos (tipo parcial)

Para dos nodos **n** y **m**: ¿cuándo puede ser cierto simultáneamente que `i(n) < i(m)` (inorden), `s(n) < s(m)` (postorden) o `p(n) < p(m)` (preorden)?

| Relación (n respecto a m) | `i(n) < i(m)` | `s(n) < s(m)` | `p(n) < p(m)` |
|---------------------------|:---:|:---:|:---:|
| n es **descendiente** de m | ✓ posible¹ | ✓ siempre | ✗ nunca |
| n está **a la izquierda** de m | ✓ siempre | ✓ siempre | ✓ siempre |
| n está **a la derecha** de m | ✗ nunca | ✗ nunca | ✗ nunca |
| n es **ancestro** de m | ✓ posible² | ✗ nunca | ✓ siempre |

> ¹ Solo si n está en el subárbol **izquierdo** de m.  
> ² Solo si m está en el subárbol **derecho** de n.

**Para memorizar:**
- Preorden: el ancestro va **antes** → `p(ancestro) < p(desc.)` siempre.
- Postorden: el ancestro va **después** → `s(ancestro) > s(desc.)` siempre.
- Inorden: depende de izquierda/derecha. "A la izquierda" → menor en los 3 recorridos. "A la derecha" → mayor en los 3.

---

### AVL — Árbol Binario de Búsqueda Autobalanceado

**Casos de uso típicos:**
- Catálogos que **crecen continuamente** con inserciones frecuentes (nuevas películas, usuarios, registros).
- Orden de inserción no controlado (datos de archivo externo) y se necesita O(log n) garantizado.
- **Regla del parcial:** si el enunciado dice "actualizaciones frecuentes" o "datos en tiempo de ejecución" → AVL.

**Estructura interna:**

```
TElementoAVL<T>:
  etiqueta: Comparable
  dato:     T
  hijoIzq:  TElementoAVL<T>  ← nulo si no tiene hijo izquierdo
  hijoDer:  TElementoAVL<T>  ← nulo si no tiene hijo derecho
  altura:   entero            ← 0 en una hoja; −1 se usa para nulos

TArbolAVL<T>:
  raiz: TElementoAVL<T>  ← nulo si el árbol está vacío
```

*Implementación en Java:*

`TElementoAVL`
```java
public class TElementoAVL<T> {
    private Comparable etiqueta;
    private T dato;
    private TElementoAVL<T> hijoIzq;
    private TElementoAVL<T> hijoDer;
    private int altura;
}
```

`TArbolAVL`
```java
public class TArbolAVL<T> {
    private TElementoAVL<T> raiz;
}
```

#### Auxiliares: altura, actualizarAltura, factorBalance

**Lenguaje natural:** `altura` retorna la altura del nodo (−1 si nulo); `actualizarAltura` la recalcula desde los hijos; `factorBalance` retorna `h(der) − h(izq)` — si es ±2, hay desbalance.

**Precondición:** ninguna.  
**Postcondición:** valores calculados en O(1) a partir de la altura almacenada en cada nodo.

```
altura(nodo: TElementoAVL): entero
  si nodo = nulo entonces retornar -1
  retornar nodo.altura
fin método

actualizarAltura(nodo: TElementoAVL): void
  nodo.altura ← 1 + max(altura(nodo.hijoIzq), altura(nodo.hijoDer))
fin método

factorBalance(nodo: TElementoAVL): entero
  retornar altura(nodo.hijoDer) - altura(nodo.hijoIzq)
fin método
```

**Orden:** O(1)

*Implementación en Java:*

`TElementoAVL`
```java
private int altura(TElementoAVL<T> nodo) {
    return nodo == null ? -1 : nodo.altura; // -1 para nulos
}

private void actualizarAltura(TElementoAVL<T> nodo) {
    nodo.altura = 1 + Math.max(altura(nodo.hijoIzq), altura(nodo.hijoDer)); // recalcula desde hijos
}

private int factorBalance(TElementoAVL<T> nodo) {
    return altura(nodo.hijoDer) - altura(nodo.hijoIzq); // positivo = carga a la derecha
}
```

---

#### Rotaciones

| Tipo | BF nodo | BF hijo | Operación |
|------|---------|---------|-----------|
| **LL** | −2 | ≤ 0 | Rotación simple **derecha** en el nodo desbalanceado |
| **RR** | +2 | ≥ 0 | Rotación simple **izquierda** en el nodo desbalanceado |
| **LR** | −2 | +1 | Rotación **izquierda** en el hijo izquierdo, luego **derecha** en el nodo |
| **RL** | +2 | −1 | Rotación **derecha** en el hijo derecho, luego **izquierda** en el nodo |

> **Los nombres LL/RR/LR/RL describen el problema, no la solución.** LL significa "el peso cuelga por Izq→Izq", y se resuelve con una rotación derecha (no izquierda-izquierda). Esto importa al leer código que nombra los métodos por problema: en ese estilo `rotacionLL` = rotación derecha y `rotacionRR` = rotación izquierda. Consecuencia: `rotacionLR` llama a `rotacionRR` primero y `rotacionLL` después — parece RL, pero las rotaciones físicas siguen siendo izquierda→derecha, que es la solución correcta al problema LR.

```
// LL — rotación simple derecha
rotacionDerecha(k2): TElementoAVL
  k1         ← k2.hijoIzq
  k2.hijoIzq ← k1.hijoDer
  k1.hijoDer ← k2
  actualizarAltura(k2)
  actualizarAltura(k1)
  retornar k1
fin método

// RR — rotación simple izquierda
rotacionIzquierda(k1): TElementoAVL
  k2         ← k1.hijoDer
  k1.hijoDer ← k2.hijoIzq
  k2.hijoIzq ← k1
  actualizarAltura(k1)
  actualizarAltura(k2)
  retornar k2
fin método

// LR — rotación doble izquierda-derecha
rotacionDobleIzquierdaDerecha(k3): TElementoAVL
  k3.hijoIzq ← rotacionIzquierda(k3.hijoIzq)
  retornar rotacionDerecha(k3)
fin método

// RL — rotación doble derecha-izquierda
rotacionDobleDerechaIzquierda(k1): TElementoAVL
  k1.hijoDer ← rotacionDerecha(k1.hijoDer)
  retornar rotacionIzquierda(k1)
fin método
```

**Orden:** O(1) todas las rotaciones.

*Implementación en Java:*

`TElementoAVL`
```java
private TElementoAVL<T> rotacionDerecha(TElementoAVL<T> k2) { // LL
    TElementoAVL<T> k1 = k2.hijoIzq;
    k2.hijoIzq = k1.hijoDer; // k2 adopta el hijo derecho de k1
    k1.hijoDer = k2;          // k1 sube, k2 baja a la derecha
    actualizarAltura(k2); actualizarAltura(k1);
    return k1;
}

private TElementoAVL<T> rotacionIzquierda(TElementoAVL<T> k1) { // RR
    TElementoAVL<T> k2 = k1.hijoDer;
    k1.hijoDer = k2.hijoIzq; // k1 adopta el hijo izquierdo de k2
    k2.hijoIzq = k1;          // k2 sube, k1 baja a la izquierda
    actualizarAltura(k1); actualizarAltura(k2);
    return k2;
}

private TElementoAVL<T> rotacionDobleIzquierdaDerecha(TElementoAVL<T> k3) { // LR
    k3.hijoIzq = rotacionIzquierda(k3.hijoIzq); // primero izquierda en el hijo
    return rotacionDerecha(k3);                   // luego derecha en el nodo
}

private TElementoAVL<T> rotacionDobleDerechaIzquierda(TElementoAVL<T> k1) { // RL
    k1.hijoDer = rotacionDerecha(k1.hijoDer); // primero derecha en el hijo
    return rotacionIzquierda(k1);              // luego izquierda en el nodo
}
```

---

#### balancear(nodo)

**Lenguaje natural:** Actualiza la altura, calcula el factor de balance y aplica la rotación correspondiente; se llama al regresar de cada inserción recursiva.

**Precondición:** ninguna.  
**Postcondición:** retorna la raíz del subárbol ya balanceado con altura actualizada.

```
balancear(nodo: TElementoAVL): TElementoAVL
  actualizarAltura(nodo)
  bf ← factorBalance(nodo)

  si bf = -2 entonces
    si factorBalance(nodo.hijoIzq) ≤ 0 entonces
      retornar rotacionDerecha(nodo)               // LL
    sino
      retornar rotacionDobleIzquierdaDerecha(nodo) // LR
    fin si
  sino si bf = +2 entonces
    si factorBalance(nodo.hijoDer) ≥ 0 entonces
      retornar rotacionIzquierda(nodo)             // RR
    sino
      retornar rotacionDobleDerechaIzquierda(nodo) // RL
    fin si
  fin si

  retornar nodo
fin método
```

**Orden:** O(1)

*Implementación en Java:*

`TElementoAVL`
```java
private TElementoAVL<T> balancear(TElementoAVL<T> nodo) {
    actualizarAltura(nodo);
    int bf = factorBalance(nodo);
    if (bf == -2) return factorBalance(nodo.hijoIzq) <= 0
        ? rotacionDerecha(nodo)                // LL
        : rotacionDobleIzquierdaDerecha(nodo); // LR
    if (bf == +2) return factorBalance(nodo.hijoDer) >= 0
        ? rotacionIzquierda(nodo)              // RR
        : rotacionDobleDerechaIzquierda(nodo); // RL
    return nodo; // ya balanceado, no se rota
}
```

---

#### insertar(etiqueta, dato) — AVL

**Lenguaje natural:** Igual que ABB, pero llama a `balancear()` al regresar de cada llamada recursiva.

**Precondición:** `etiqueta ≠ nulo`.  
**Postcondición:** el elemento queda insertado y el árbol mantiene la propiedad AVL en todos sus nodos.

```
TArbolAVL.insertar(etiqueta: Comparable, dato: T): void
  elemento ← nuevo TElementoAVL(etiqueta, dato)
  si esVacio() entonces
    raiz ← elemento
  sino
    raiz ← raiz.insertarAVL(elemento)
  fin si
fin método

TElementoAVL.insertarAVL(elemento: TElementoAVL): TElementoAVL
  si elemento.etiqueta < this.etiqueta entonces
    si hijoIzq = nulo entonces
      hijoIzq ← elemento
    sino
      hijoIzq ← hijoIzq.insertarAVL(elemento)
    fin si
  sino si elemento.etiqueta > this.etiqueta entonces
    si hijoDer = nulo entonces
      hijoDer ← elemento
    sino
      hijoDer ← hijoDer.insertarAVL(elemento)
    fin si
  fin si
  retornar balancear(this)
fin método
```

**Orden:** O(log n) garantizado.

*Implementación en Java:*

`TArbolAVL`
```java
public void insertar(Comparable etiqueta, T dato) {
    TElementoAVL<T> elemento = new TElementoAVL<>(etiqueta, dato);
    if (raiz == null) { raiz = elemento; return; }
    raiz = raiz.insertarAVL(elemento); // la raíz puede cambiar tras el balanceo
}
```

`TElementoAVL`
```java
public TElementoAVL<T> insertarAVL(TElementoAVL<T> elemento) {
    if (elemento.getEtiqueta().compareTo(this.etiqueta) < 0) {
        if (hijoIzq == null) hijoIzq = elemento;            // inserta a la izquierda
        else hijoIzq = hijoIzq.insertarAVL(elemento);
    } else if (elemento.getEtiqueta().compareTo(this.etiqueta) > 0) {
        if (hijoDer == null) hijoDer = elemento;             // inserta a la derecha
        else hijoDer = hijoDer.insertarAVL(elemento);
    }
    return balancear(this); // rebalancea al regresar
}
```

---

#### eliminar(etiqueta) — AVL

**Lenguaje natural:** Navega igual que en el ABB y aplica los mismos 3 casos. La diferencia es que `balancear()` se llama al regresar de cada llamada recursiva. En el caso de 2 hijos: el predecesor inorden (máximo del subárbol izquierdo) se elimina del subárbol izquierdo con rebalanceo recursivo, y sube a ocupar el lugar del nodo eliminado.

**Precondición:** ninguna.  
**Postcondición:** si existía un nodo con esa etiqueta, es eliminado y el árbol mantiene la propiedad AVL.

```
TArbolAVL.eliminar(etiqueta: Comparable): void
  si ¬esVacio() entonces
    raiz ← raiz.eliminarAVL(etiqueta)
  fin si
fin método

TElementoAVL.eliminarAVL(etiqueta: Comparable): TElementoAVL
  si etiqueta < this.etiqueta entonces
    si hijoIzq ≠ nulo entonces
      hijoIzq ← hijoIzq.eliminarAVL(etiqueta)
    fin si
  sino si etiqueta > this.etiqueta entonces
    si hijoDer ≠ nulo entonces
      hijoDer ← hijoDer.eliminarAVL(etiqueta)
    fin si
  sino
    si hijoIzq = nulo entonces retornar hijoDer   // caso 0 o 1 hijo (derecho)
    si hijoDer = nulo entonces retornar hijoIzq   // caso 1 hijo (izquierdo)
    // Caso 2 hijos: predecesor = máximo del subárbol izquierdo
    pred ← hijoIzq
    mientras pred.hijoDer ≠ nulo hacer
      pred ← pred.hijoDer
    fin mientras
    nuevoIzq ← hijoIzq.eliminarAVL(pred.etiqueta)
    pred.hijoIzq ← nuevoIzq
    pred.hijoDer ← hijoDer
    retornar balancear(pred)
  fin si
  retornar balancear(this)
fin método
```

**Orden:** O(log n) garantizado.

*Implementación en Java:*

`TArbolAVL`
```java
public void eliminar(Comparable etiqueta) {
    if (raiz != null) raiz = raiz.eliminarAVL(etiqueta);
}
```

`TElementoAVL`
```java
public TElementoAVL<T> eliminarAVL(Comparable etiqueta) {
    int cmp = etiqueta.compareTo(this.etiqueta);
    if (cmp < 0) { if (hijoIzq != null) hijoIzq = hijoIzq.eliminarAVL(etiqueta); }
    else if (cmp > 0) { if (hijoDer != null) hijoDer = hijoDer.eliminarAVL(etiqueta); }
    else {
        if (hijoIzq == null) return hijoDer; // caso 0 o 1 hijo
        if (hijoDer == null) return hijoIzq;
        TElementoAVL<T> pred = hijoIzq;
        while (pred.hijoDer != null) pred = pred.hijoDer; // predecesor inorden
        TElementoAVL<T> nuevoIzq = hijoIzq.eliminarAVL(pred.getEtiqueta());
        pred.hijoIzq = nuevoIzq;
        pred.hijoDer = hijoDer;
        return balancear(pred); // rebalancea el predecesor que sube
    }
    return balancear(this); // rebalancea al regresar
}
```

---

### Clave Compuesta en ABB/AVL

**¿Cuándo se necesita?**

Cuando la clave natural de los elementos puede repetirse, pero la *combinación* de dos campos es única. El árbol requiere que cada nodo tenga una etiqueta distinta; si se insertan dos películas con el mismo año, el ABB/AVL rechazaría la segunda (duplicado). La solución es definir una **clave compuesta** que combine los dos campos en un tipo que implementa `Comparable`.

**Señales en el enunciado:**
- "Puede haber varias películas del mismo año" (clave por año puede colisionar)
- "Ordenar por año; si coincide el año, ordenar por título" (desempate explícito)
- "Cada película se identifica unívocamente por año + título" (unicidad compuesta)

**Cómo funciona:**

La clave compuesta implementa `compareTo` con prioridad de campos:
1. Comparar por el **campo primario** (ej: año).
2. Si son iguales, comparar por el **campo secundario** (ej: título).
3. Si ambos son iguales, son el mismo elemento → retornar 0.

Esto convierte al árbol en un índice ordenado primero por año, luego por título dentro del mismo año. El recorrido inorden produce los elementos en ese orden exacto.

**Estructura de la clave:**

```
ClavePelicula:
  anio:   entero
  titulo: cadena

ClavePelicula.compareTo(otra: ClavePelicula): entero
  si this.anio < otra.anio entonces retornar -1
  si this.anio > otra.anio entonces retornar +1
  // años iguales → desempate por título
  retornar this.titulo.compareTo(otra.titulo)
fin método
```

*Implementación en Java:*

`ClavePelicula`
```java
public int compareTo(ClavePelicula otra) {
    if (this.anio < otra.anio) return -1; // compara por año primero
    if (this.anio > otra.anio) return 1;
    return this.titulo.compareTo(otra.titulo); // desempate por título
}
```

**Uso en el árbol:**

```
// Insertar
clave ← nueva ClavePelicula(pelicula.anio, pelicula.titulo)
arbol.insertar(clave, pelicula)

// Buscar exacto (requiere año + título)
clave ← nueva ClavePelicula(anio, titulo)
resultado ← arbol.buscar(clave)
```

*Implementación en Java:*

```java
// Insertar
ClavePelicula clave = new ClavePelicula(pelicula.getAnio(), pelicula.getTitulo());
arbol.insertar(clave, pelicula); // la clave garantiza unicidad año + título

// Buscar exacto
ClavePelicula clave = new ClavePelicula(anio, titulo);
Pelicula resultado = arbol.buscar(clave);
```

El árbol usa `compareTo` internamente en cada nodo — no necesita saber que la etiqueta tiene dos campos. Solo requiere que `Comparable` esté implementado.

**Consecuencias sobre los recorridos:**

- **inOrden:** produce los elementos ordenados por año ascendente; dentro del mismo año, por título alfabético.
- **buscarPorRangoAnio(anioMin, anioMax):** no se puede usar directamente `buscar(año)` porque la clave tiene dos campos. Para el rango se recorre inorden y se filtra por año. Ver pseudocódigo completo en `pseudocodigos/clave-compuesta.md`.

**Trade-off:**

| Enfoque | Ventaja | Limitación |
|---------|---------|-----------|
| Clave simple (solo año) | Más simple, O(log n) por año | No tolera años duplicados sin política de duplicados |
| Clave compuesta (año + título) | Unicidad garantizada; inorden da orden por año luego título | `buscar(anio)` ya no funciona — necesitás año + título; el rango por año requiere recorrido inorden completo |
| Truco `año * 10000L + hashCode` | No requiere clase extra | Colisiones de hash posibles; frágil |

> **Regla del parcial:** si el enunciado garantiza que la clave principal es única (ej: "el año de estreno es único por película"), usá clave simple. Si puede haber duplicados en esa clave, definí una clave compuesta con `compareTo` en dos campos.
