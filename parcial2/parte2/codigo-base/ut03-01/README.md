# Algoritmos y Estructuras de Datos — Problem Sets UT3: Árbol Genérico y Tries

**Universidad Católica del Uruguay — Facultad de Ingeniería y Tecnologías**

---

> **Ejercicios entregables para corrección:** 5, 7, 9, 11, 12, 13, 14, 15, 16

---

## Ejercicio 1

La Universidad Católica del Uruguay necesita desarrollar una aplicación informática para mantener su organigrama. El organigrama se representa mediante el TDA árbol, donde cada nodo tiene:

- Referencia a su hermano derecho
- Referencia a su primer hijo
- Una etiqueta que lo distingue
- Referencia a los datos almacenados en ese nodo

Se solicita desarrollar en pseudocódigo el siguiente algoritmo:

```
buscar(Comparable<T> criterio): devuelve el nodo que satisface el criterio;
                                en caso contrario devuelve el nodo nulo.
```

Para cada caso se debe escribir el método del árbol y el método del nodo. Se requiere:

1. Descripción en lenguaje natural del algoritmo solicitado.
2. Identificación de precondiciones y postcondiciones.
3. Escritura del algoritmo en pseudocódigo formal.
4. Análisis detallado del orden del tiempo de ejecución.
5. Ilustrar el funcionamiento buscando divisiones/departamentos que existan y que no existan.
6. Calcular la cantidad de comparaciones en cada caso.
7. Hallar el orden del tiempo de ejecución de cada algoritmo escrito.

---

## Ejercicio 2

Se desea construir un índice de palabras de un libro usando un Trie. Se solicita:

1. Descripción (esquema) de las estructuras de datos necesarias para construir un Trie para el alfabeto `a..z`. Dibujarla en un **POSTER**.
2. Dibujo del Trie correspondiente al siguiente conjunto de datos de prueba (**POSTER**):

| Palabra | Páginas |
|---|---|
| Ala | 1, 3, 88 |
| Alimaña | 11, 22 |
| Alabastro | 4 |
| Perro | 5, 8 |
| Pera | 7, 12 |
| Alimento | 9 |
| Casa | 11, 13 |
| Casada | 1 |
| Cazar | 33 |
| Programa | 22, 67 |
| Programación | 15 |
| Programar | 15, 16 |

3. Un algoritmo para, a partir de un texto, construir el índice en el Trie indicando las páginas en que aparecen las palabras.
4. Un algoritmo para, dado el Trie, buscar una cadena y devolver los números de páginas del libro en los que esa palabra se encuentra.

---

## Ejercicio 3

**Escenario:** Se desea crear un programa que permita indexar todas las palabras almacenadas en múltiples textos digitales, con funcionalidad de búsqueda parcial e incremental (similar al autocompletar). Se utiliza un **Trie** para representar el problema.

Se requiere:

1. Implementar el método `insertar` de Trie.
2. Implementar el método `buscar` de Trie.
3. Definir los casos de prueba.

---

## Ejercicio 4

Describir en lenguaje natural, pre y postcondiciones, pseudocódigo y análisis de órdenes de las siguientes operaciones de `TArbolGenerico` y `TNodoGenerico`:

1. Agregar un hijo a un padre dado:
   ```java
   boolean agregarHijo(Comparable<T> padre, T hijo)
   ```
2. Eliminar los nodos que satisfacen un criterio:
   ```java
   void eliminar(Comparable<T> criterio)
   ```
3. Buscar un dato según un criterio de búsqueda:
   ```java
   T buscar(Comparable<T> criterio)
   ```
4. Obtener el grado de un nodo según un criterio de búsqueda:
   ```java
   int grado(Comparable<T> nodo)
   ```

---

## Ejercicio 5

Describir en lenguaje natural, pre y postcondiciones, pseudocódigo y análisis de órdenes de las siguientes operaciones de `Trie` y `NodoTrie`:

1. Operación buscar palabra completa.
2. Obtener la lista de palabras por un prefijo dado.
3. Insertar una palabra con un dato asociado.
4. Eliminar una palabra del Trie.

---

## Ejercicio 6

### Parte 1

Dado el siguiente conjunto de palabras: `if, int, for, static, class, new, this, add`.

Insertarlas en el orden dado en una tabla hash, usando:

1. Sondeo lineal
2. Sondeo cuadrático

Para cada caso, calcular el tamaño de tabla adecuado y usar una función sencilla, rápida y adecuada a la cantidad de claves.

### Parte 2

Implementar un `THash` en Java con las siguientes operaciones:

```java
// Devuelve la cantidad de comparaciones realizadas
public int buscar(int unaClave)

// Devuelve la cantidad de comparaciones realizadas
public int insertar(int unaClave)

// Devuelve la posición generada por la función
public int funcionHashing(int unaClave)
```

Se solicita:

1. Desarrollar en pseudocódigo las operaciones de inserción y búsqueda.
2. Definir el tamaño de la tabla de acuerdo con las mejores prácticas, considerando las claves del archivo `claves_insertar.txt`.
3. Diseñar una función hash eficiente (presentar también en pseudocódigo).
4. Implementar las operaciones de inserción y búsqueda en Java, registrando la cantidad de comparaciones efectuadas.
5. Preparar el programa para realizar inserciones y búsquedas masivas, utilizando los archivos `claves_insertar.txt` y `claves_buscar.txt`.

**Consideraciones:**

- Método de resolución de colisiones: **direccionamiento abierto lineal**, con la fórmula:
  `h(i) = (h(0) + i) mod M`, donde M es el tamaño de la tabla.
- El programa principal debe:
  - Crear la tabla hash.
  - Leer las claves del archivo de entrada.
  - Establecer el tamaño de la tabla como: `M = cantidadClaves / 0.9`.
  - Ejecutar las operaciones de inserción y búsqueda y mostrar los resultados.

**Dinámica del trabajo:**

- **Paso 1:**
  - Subequipo A: desarrolla el pseudocódigo de inserción y búsqueda.
  - Subequipo B: desarrolla el pseudocódigo de la función hash más eficiente posible.
- **Paso 2:** Intercambiar pseudocódigos, realizar correcciones y elaborar un poster final.

### Parte 3

A partir de los pseudocódigos, implementar el programa completo:

- **Paso 1:**
  - Subequipo A: implementa la función hash y la prueba con datos pequeños.
  - Subequipo B: implementa inserción y búsqueda, y diseña el programa principal.
- **Paso 2:** Integrar el código de ambos subequipos, realizar pruebas y reportar resultados.

---

## Ejercicio 7

### Parte 1

Instrucciones de uso de las clases para medición.

### Parte 2

Dado el archivo `listado-general-desordenado.txt`, cargar las palabras en las siguientes estructuras de datos:

- `java.util.LinkedList`
- `java.util.ArrayList`
- `TTrie`
- `java.util.HashMap`
- `java.util.TreeMap`

### Parte 3

Cargar en memoria las claves de `listado-general-palabrasBuscar.txt` y ejecutar las búsquedas **20 veces**. Implementar la clase de medición para:

- `ArrayList`
- `TTrie`
- `HashMap`
- `TreeMap`

### Parte 4

Consolidar resultados en una tabla con, para cada estructura:
- Consumo de memoria (en MB)
- Tiempo de ejecución (en milisegundos)

### Parte 5

Comparar implementaciones del programa de **autocompletar/predecir palabras** usando:

- `Trie`
- `LinkedList`
- `HashMap`

Efectuar **20 mediciones** de cada alternativa para predecir todas las palabras que comiencen con `"cas"`. Consolidar resultados en tabla con memoria (MB) y tiempo (ms).

---

## Ejercicio 8

Reimplementar el Trie usando `HashMap` dentro de `TNodoTrie` en lugar del vector fijo, para mayor flexibilidad con diferentes alfabetos.

Se requiere:

- Desarrollar nuevamente el Trie utilizando `HashMap` en `TNodoTrie`.
- Insertar las strings indicadas y probar el programa.
- Comparar las dos implementaciones en cuanto a consumo de memoria y tiempo de ejecución.

---

## Ejercicio 9

Evaluar la evolución del rendimiento de la tabla hash a medida que se incrementa el factor de carga. Elaborar una tabla con las siguientes columnas:

| Factor de carga % | Prom. Comp. Inserción | Prom. Comp. Búsqueda exitosa | Prom. Comp. Búsqueda sin éxito |
|---|---|---|---|
| 70 | | | |
| 75 | | | |
| 80 | | | |
| 85 | | | |
| 90 | | | |
| 91 | | | |
| 92 | | | |
| 93 | | | |
| 94 | | | |
| 95 | | | |
| 96 | | | |
| 97 | | | |
| 98 | | | |
| 99 | | | |

- Graficar los resultados.
- Analizar si los resultados se condicen con los conceptos teóricos.

---

## Ejercicio 10

### Parte 1

Escribir, en el menor número de líneas de código posible, una funcionalidad que elimine todas las entradas de un `Map` cuyo valor sea `null`.

### Parte 2

Escribir un método que tome como parámetro un `Map<String, String>` y devuelva un nuevo `Map<String, String>` con claves y valores intercambiados. Generar una excepción si hay valores duplicados.

### Parte 3

Escribir un programa que lea cadenas de caracteres y las imprima ordenadas según su longitud (de menor a mayor). Si hay varias cadenas con la misma longitud, imprimirlas en orden lexicográfico.

### Parte 4

Escribir un programa que:
- Lea un archivo de texto (pasado como primer parámetro) en una `List`.
- Imprima en forma aleatoria líneas del archivo.
- El número de líneas a imprimir se especifica como segundo parámetro.
- Asignar una colección del tamaño correcto desde el inicio (usar `java.io.File.length` para estimar la cantidad de líneas).

---

## Ejercicio 11

Implementar un programa que obtenga las **frecuencias de ocurrencias** de las palabras de un libro.

1. Seleccionar la(s) clase(s) de la API de colecciones de Java más apropiada(s). Justificar la elección.
2. Dado un archivo `libro.txt`, desarrollar un programa Java usando la API de colecciones para calcular las frecuencias de ocurrencias.
3. Graficar los resultados de las **10 palabras que más ocurren**.

---

## Ejercicio 12

Mejorar la implementación de Tries usando `HashMap` para el almacenamiento de los enlaces a subárboles.

Se solicita:

- Crear nuevas clases `TTrieHashMap` y `TNodoTrieHashMap` (esta última usa `HashMap` en lugar del vector fijo).
- Adaptar los métodos de: inserción, búsqueda, búsqueda de prefijos ("predecir") y búsqueda de patrones.
- Reimplementar la aplicación de **autocompletar** con las nuevas clases y realizar pruebas.
- Implementar la aplicación para **búsqueda de patrones** en un texto, indicando las posiciones en que ocurren.

---

## Ejercicio 13

### Parte 1

Investigar cómo está implementado `hashCode` en Java para:
- La clase `Object`
- La clase `Integer`
- La clase `String`

Explicar por qué la implementación es diferente en cada caso.

### Parte 2

Investigar y diagramar las estructuras internas de un `HashMap`. Diagramar el estado de las estructuras luego de insertar las siguientes strings:

- `Hola`
- `HolaMundo`
- `HashMap`
- `Colecciones`

### Parte 3

Implementar los métodos `equals` y `hashCode` para la siguiente clase:

```java
public class Alumno {
    private int id;
    private String fullName;
    private String email;
    // ...
}
```

Indicar qué características deben tener las implementaciones para mantener el contrato general del método `hashCode`.

---

## Ejercicio 14

Dado el siguiente conjunto de claves enteras (en el orden indicado):

```
45, 12, 37, 82, 29, 54, 31, 76, 18, 93, 11, 68
```

Insertar las claves en tablas hash usando tres estrategias de resolución de colisiones:

1. Sondeo lineal
2. Sondeo cuadrático
3. Encadenamiento separado

Se pide:

1. Definir un tamaño de tabla adecuado y justificar la decisión.
2. Definir una función hash sencilla para las claves dadas.
3. Mostrar el estado final de la tabla para cada estrategia.
4. Calcular la cantidad total de colisiones en cada caso.
5. Calcular el promedio de comparaciones para búsquedas exitosas.
6. Calcular el promedio de comparaciones para búsquedas no exitosas, indicando claramente qué claves se utilizaron.
7. Comparar los resultados y concluir cuál estrategia funcionó mejor para este conjunto de datos.

---

## Ejercicio 15

Dada la siguiente clase Java:

```java
public class Libro {
    private String isbn;
    private String titulo;
    private String autor;
    private int anio;
    // constructores, getters y setters
}
```

Se pide:

1. Definir cuál o cuáles atributos deberían determinar la **identidad lógica** de un libro. Justificar.
2. Implementar los métodos `equals` y `hashCode` respetando el contrato general de Java.
3. Explicar el error conceptual que surge si `equals` compara por `isbn` pero `hashCode` se calcula usando `titulo`.
4. Construir un ejemplo en el que dos objetos `Libro` distintos representen el mismo libro y sean insertados en un `HashSet`.
5. Explicar qué debería ocurrir en ese `HashSet` si `equals` y `hashCode` están correctamente implementados.
6. Proponer casos de prueba para verificar que la implementación cumple el contrato de `equals` y `hashCode`.

---

## Ejercicio 16

Modelar un **árbol genealógico** donde cada nodo representa una persona.

**Información mínima de cada persona:** Nombre y Año de nacimiento.

**Reglas del modelo:**
- Cada nodo puede tener cero o más hijos.
- No se consideran padres explícitos (el árbol se construye desde un ancestro común).

### Parte 1: Construcción

- Definir la clase `Persona`.
- Definir la estructura de nodo para el árbol genérico.
- Construir manualmente un árbol con al menos **3 generaciones** y **10 personas en total**.

Ejemplo conceptual:

```
Abuela
|-- Hijo1
|   |-- Nieto1
|   |-- Nieto2
|-- Hijo2
    |-- Nieto3
```

### Parte 2: Métodos a implementar

1. Listar todos los descendientes de una persona dada.
2. Calcular la altura del árbol.
3. Contar la cantidad total de personas.
4. Obtener todas las personas de una generación dada (generación 0 = raíz, generación 1 = hijos, etc.).
5. Encontrar el **ancestro común más cercano** entre dos personas.
6. Determinar si una persona es descendiente de otra.

---

## Ejercicio 17

### Parte 1

- Investigar qué es una **función de hash criptográfica** y cuáles son sus propiedades.
- Buscar una implementación de SHA-256.
- Implementar la siguiente función:

```java
byte[] calcularHash(byte[] archivo)
```

Que reciba el contenido de un archivo como array de bytes y devuelva el hash SHA-256 correspondiente.

### Parte 2

Dados dos archivos (uno original y otro con una modificación mínima, por ejemplo un solo byte cambiado), responder:

1. ¿Cuál es la probabilidad de que dos archivos diferentes colisionen?
2. ¿Qué herramienta de uso cotidiano utiliza esto? ¿Para qué?



[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/fwQGHmgs)
