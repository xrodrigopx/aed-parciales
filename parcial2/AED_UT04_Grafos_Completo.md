# Algoritmos y Estructuras de Datos — Unidad Temática 04
**1er semestre - 2026 · UCU**

---

## 1. Grafos

Los grafos son modelos naturales para representar relaciones entre objetos de datos.

Un grafo consiste de un conjunto finito de vértices V y de un conjunto de arcos A:

**G = (V, A)**

- Sea el conjunto de vértices o nodos V = {v₁, v₂, ..., vₙ}, entonces el conjunto de arcos o aristas es A = {(vᵢ, vⱼ)}: un conjunto de pares de vértices.
- Si las aristas son **no dirigidas**, es decir (vᵢ, vⱼ) = (vⱼ, vᵢ), el grafo se llama **no dirigido**.
- En un **grafo dirigido**, la arista es un par ordenado de vértices.

### Propiedades básicas

- Existe como máximo una arista conectando cualesquiera dos vértices.
- Dos vértices se llaman **adyacentes** si existe una arista que los conecta.
- Se dice que un grafo está **conectado** si existe un camino entre cualquier par de vértices.

> Ejemplo: un grafo con cuatro vértices y cinco aristas. El vértice 4 es adyacente al 3 pero no al 1. El subgrafo compuesto por los vértices 2, 3 y 4 está **conectado**.

---

## 2. Grafos Dirigidos

En un arco dirigido de **v** a **w**:
- El nodo **v** es la **cola**
- El nodo **w** es la **cabeza**
- **w** es adyacente a **v**
- El arco va de v a w

### Ejemplos de uso

- Los vértices pueden representar **ciudades** y los arcos la distancia entre ellas.
- Los vértices pueden representar **bloques de un programa** de computador y los arcos posibles transferencias de flujo de control.
- Los vértices pueden representar **asignaturas** de una carrera universitaria y los arcos la relación de previaturas entre ellas.
- Los vértices pueden representar **estados** (por ejemplo, de un autómata) y los arcos la transición entre ellos.
- Los vértices pueden representar los **eventos de principio y fin de una tarea**, y las aristas las tareas necesarias para la ejecución de un proyecto.

---

## 3. Grafos Dirigidos: Camino

Un **camino** en un grafo dirigido es una secuencia de vértices v₁, v₂, ..., vₙ, tal que:
- (v₁, v₂), (v₂, v₃), ..., (vₙ₋₁, vₙ) son arcos.

Este camino va del vértice v₁ al vértice vₙ, pasando por todos los vértices intermedios.

- La **longitud de un camino** es el número de arcos del camino. Un vértice por sí mismo implica un camino de largo 0.
- Un camino es **simple** si todos sus vértices, excepto tal vez el primero y el último, son distintos.
- Un **ciclo** es un camino simple de longitud por lo menos dos, que empieza y termina en el mismo vértice.

---

## 4. Representaciones de Grafos Dirigidos

### Matriz de adyacencias
Requiere un espacio mínimo del orden de **n²**, siendo n la cantidad de vértices.

### Lista de adyacencias
Requiere una cantidad de espacio proporcional a la **suma de la cantidad de arcos más la cantidad de vértices**.

Las listas pueden implementarse en forma **estática o dinámica**.

#### Ejemplo de grafo dirigido con pesos (vértices V₀ a V₆):

**Matriz de adyacencias:**

|   | 0 | 1 | 2 | 3 | 4 | 5 | 6 |
|---|---|---|---|---|---|---|---|
| **0** |   | 2 |   | 1 |   |   |   |
| **1** |   |   |   | 3 | 10 |   |   |
| **2** | 4 |   |   |   |   | 5 |   |
| **3** |   |   | 2 |   | 2 | 8 | 4 |
| **4** |   |   |   |   |   |   | 6 |
| **5** |   |   |   |   |   |   |   |
| **6** |   |   |   |   |   | 1 |   |

**Lista de adyacencias:**
```
0: 1(2) → 3(1) → ∅
1: 4(10) → 3(3) → ∅
2: 0(4) → 5(5) → ∅
3: 4(2) → 6(4) → 5(8) → 2(2)
4: 6(6) → ∅
5: ∅
6: 5(1) → ∅
```

---

## 5. TDA Grafo

Operaciones del TDA Grafo:
- **Grafo (Vértices, Aristas)**
- Dado un vértice origen, indicar los **caminos mínimos** a todos los otros
- **Todos los caminos mínimos**, de todo vértice a todo otro
- **Centro de Grafo**, excentricidad de un vértice
- **Cerradura transitiva**
- **Búsqueda en profundidad** (recorrer sistemáticamente todo el grafo en profundidad)
- **Camino**, Caminos

---

## 6. Algoritmo de Dijkstra

### Problema de los caminos más cortos con un origen

Dado un grafo dirigido G = (V, A) en que cada arco tiene una etiqueta **no negativa**, y donde un vértice se especifica como **origen**:

- **Problema**: determinar el costo del camino más corto desde el origen a cada uno de los demás vértices de V.
- La longitud de un camino es la **suma de los costos** de los arcos del camino.
- **Técnica "ávida"**.
- **S**: conjunto de vértices cuya distancia más corta al origen es conocida. Al principio S sólo contiene el origen.
- En cada paso se agrega algún vértice **v** restante a S, cuya distancia desde el origen es la más corta posible.
- **D**: vector que registra la longitud de camino especial más corta a cada vértice.

### Algoritmos "ávidos" ("avaros", "voraces" – "greedy")

Dado C (entradas), el algoritmo ávido devuelve en cada iteración un conjunto S tal que S ⊆ C y S es "prometedor".

**Elementos de la técnica:**
- **C** – conjunto de candidatos (entradas)
- Función **solución**
- Función de **selección**
- Función de **factibilidad**
- Función **objetivo**

**Funcionamiento básico:**
1. Elegir el mejor elemento de C posible (elemento más prometedor)
2. Retirarlo del conjunto C de candidatos
3. Comprobar si produce una solución factible, y si es así, incluirlo en S
4. Si no es factible, descartar
5. Repetir 1–4 hasta alcanzar la función objetivo o agotar los elementos de C

### Pseudocódigo de Dijkstra

```
dijkstra(origen, costo, V)
    // S => nodos visitados
    // D => vector de costos desde origen
    // V => vértices del grafo

    // inicializar S y D
    S <- {origen}

    Para todo i en V hacer D[i] = costo(origen, i)

    Mientras V <> S hacer
        Elegir w perteneciente a V-S, tal que D[w] sea mínimo
        Agregar w a S
        Para todo v en V-S hacer
            D[v] = min(D[v], D[w] + costo(w, v))
        FinPara
    FinMientras
```

### Ejemplo de ejecución de Dijkstra

Grafo con vértices 1–5 y arcos:
- 1→2 = 10, 1→4 = 30, 1→5 = 100
- 2→3 = 50
- 3→5 = 10
- 4→3 = 20, 4→5 = 60

| Iteration | S           | w | D[2] | D[3] | D[4] | D[5] |
|-----------|-------------|---|------|------|------|------|
| initial   | {1}         | — | 10   | ∞    | 30   | 100  |
| 1         | {1,2}       | 2 | 10   | 60   | 30   | 100  |
| 2         | {1,2,4}     | 4 | 10   | 50   | 30   | 90   |
| 3         | {1,2,4,3}   | 3 | 10   | 50   | 30   | 60   |
| 4         | {1,2,4,3,5} | 5 | 10   | 50   | 30   | 60   |

### Dijkstra: recuperación de caminos

Usar otro array **P** de vértices, tal que **P[v]** contiene el vértice inmediato anterior a v en el camino menor. Inicialmente todos los **P[v] = 1**.

**Pseudocódigo con recuperación:**
```
dijkstra(origen, costo, V)
    // S => nodos visitados
    // D => vector de costos desde origen
    // V => vértices del grafo
    // P => vector de precedencia

    // inicializar S, D y P
    S <- {origen}

    Para todo i en V hacer D[i] = costo(origen, i)

    Mientras V <> S hacer
        Elegir w perteneciente a V-S, tal que D[w] sea mínimo
        Agregar w a S
        Para todo v en V-S hacer
            Si D[w] + costo(w, v) < D[v] entonces
                D[v] = D[w] + costo(w, v) y P[v] = w
            FinSi
        FinPara
    FinMientras
```

**Ejemplo de recuperación:**

Para el grafo anterior, el vector P al final del algoritmo tendrá los valores:
- P[2] = 1, P[3] = 4, P[4] = 1, y P[5] = 3

Para encontrar el camino más corto desde el vértice 1 al vértice 5, recorremos los predecesores en orden inverso, comenzando por el vértice 5:
- 3 es el predecesor de 5, 4 el de 3, y 1 el de 4.

**Entonces el camino más corto de 1 a 5 es: 1, 4, 3, 5**

---

## 7. Caminos más cortos entre todos los pares — Algoritmo de Floyd

### Planteamiento

- **Problema**: obtener una tabla que indique el menor camino entre todos los pares de vértices (ejemplo: tiempos de vuelos entre ciudades).
- Dado un grafo dirigido G = (V, A) en que cada arco tiene un costo no negativo C[v, w].
- Encontrar el camino de longitud más corta para cada par ordenado de vértices (v, w).
- Se podría utilizar Dijkstra, tomando por turno cada vértice como origen...
- Más directo: **algoritmo de Floyd**.

### Descripción del algoritmo

- Usa una **matriz A de n × n** en la que se calculan las longitudes de los caminos más cortos.
- Inicialmente **A[i,j] = C[i,j]** para todo i ≠ j; si no hay arco de i a j se pone **∞**. Los elementos de la diagonal se hacen **0**.
- Se realizan **n iteraciones** en la matriz A.
- Al final de la k-ésima iteración, A[i,j] tendrá por valor la longitud menor de ir de i hasta j y que no pase por un vértice mayor que k.

**Fórmula de recurrencia:**

```
Aₖ[i, j] = min( Aₖ₋₁[i, j],  Aₖ₋₁[i, k] + Aₖ₋₁[k, j] )
```

**Idea clave:** si A[i,k] + A[k,j] < A[i,j], entonces A[i,j] := A[i,k] + A[k,j] y P[i,j] := k.

### Pseudocódigo de Floyd

```
// A y C matriz de costos con tamaño NxN
Para todo i = 1...n hacer
    Para todo j = 1...n hacer
        A[i,j] = C[i,j]
    FinPara
    A[i, i] = 0
FinPara

Para todo k = 1...n hacer
    Para todo i = 1...n hacer
        Para todo j = 1...n hacer
            Si A[i,k]+A[k,j] < A[i,j] entonces
                A[i,j] = A[i,k]+A[k,j]
            FinSi
        FinPara
    FinPara
FinPara
```

### Floyd: recuperación de caminos

Se agrega una **matriz P** donde P[i, j] contiene aquel vértice k que determinó que Floyd encontrara el menor valor para A[i, j].

- Si **P[i, j] = 0**, entonces el camino más corto desde i a j es **directo**, siguiendo el arco de i a j.

**Pseudocódigo de Floyd con recuperación:**

```
// A y C matriz de costos con tamaño NxN
Para todo i = 1...n hacer
    Para todo j = 1...n hacer
        A[i,j] = C[i,j]
        P[i,j] = 0
    FinPara
    A[i, i] = 0
FinPara

Para todo k = 1...n hacer
    Para todo i = 1...n hacer
        Para todo j = 1...n hacer
            Si A[i,k]+A[k,j] < A[i,j] entonces
                A[i,j] = A[i,k]+A[k,j]
                P[i,j] = k
            FinSi
        FinPara
    FinPara
FinPara
```

**Función para recuperar el camino:**

```
camino(i, j)
    k <- P[i,j]
    Si k == 0 entonces salir
    camino(i, k)
    imprimir(k)
    camino(k, j)
fin
```

**Ejemplo de matrices resultantes (grafo de 3 nodos):**

Matriz A (distancias mínimas):

|   | 1 | 2 | 3 |
|---|---|---|---|
| **1** | 0 | 7 | 5 |
| **2** | 3 | 0 | 8 |
| **3** | 5 | 2 | 0 |

Matriz P (predecesores):

|   | 1 | 2 | 3 |
|---|---|---|---|
| **1** | 0 | 3 | 0 |
| **2** | 0 | 0 | 1 |
| **3** | 2 | 0 | 0 |

---

## 8. Ejemplo Paso a Paso de Floyd-Warshall

### Grafo de entrada

Arcos: 1→2 = 8, 2→1 = 3, 3→2 = 2, 1→3 = 5

**Matriz C inicial (= A inicial):**

|   | 1 | 2 | 3 |
|---|---|---|---|
| **1** | 0 | 8 | 5 |
| **2** | 3 | 0 | ∞ |
| **3** | ∞ | 2 | 0 |

### Iteración k = 1 (vértice 1 como intermedio)

Se evalúa si ir i → 1 → j mejora el costo actual i → j.

**Cambio detectado:**
- A[2,3] actual = ∞
- A[2,1] + A[1,3] = 3 + 5 = 8
- Como 8 < ∞, entonces A[2,3] := 8

**Nuevo camino encontrado: 2 → 1 → 3, con costo 8.**

**Después de k = 1:**

|   | 1 | 2 | 3 |
|---|---|---|---|
| **1** | 0 | 8 | 5 |
| **2** | 3 | 0 | **8** |
| **3** | ∞ | 2 | 0 |

### Iteración k = 2 (vértice 2 como intermedio)

Se evalúa si ir i → 2 → j mejora el costo actual i → j.

**Cambio detectado:**
- A[3,1] actual = ∞
- A[3,2] + A[2,1] = 2 + 3 = 5
- Como 5 < ∞, entonces A[3,1] := 5

**Nuevo camino encontrado: 3 → 2 → 1, con costo 5.**

**Después de k = 2:**

|   | 1 | 2 | 3 |
|---|---|---|---|
| **1** | 0 | 8 | 5 |
| **2** | 3 | 0 | 8 |
| **3** | **5** | 2 | 0 |

### Iteración k = 3 (vértice 3 como intermedio)

Se evalúa si ir i → 3 → j mejora el costo actual i → j.

**Cambio detectado:**
- A[1,2] actual = 8
- A[1,3] + A[3,2] = 5 + 2 = 7
- Como 7 < 8, entonces A[1,2] := 7

**Mejora encontrada: 1 → 3 → 2, con costo 7.**

**Después de k = 3 (Matriz final):**

|   | 1 | 2 | 3 |
|---|---|---|---|
| **1** | 0 | **7** | 5 |
| **2** | 3 | 0 | 8 |
| **3** | 5 | 2 | 0 |

**Interpretación por fila:**
- Desde 1: a 2 cuesta 7 y a 3 cuesta 5.
- Desde 2: a 1 cuesta 3 y a 3 cuesta 8.
- Desde 3: a 1 cuesta 5 y a 2 cuesta 2.

> Observación: A[1,2] no quedó con el arco directo 8, sino con el camino mejorado 1 → 3 → 2, que cuesta 7.

---

## 9. Localización del Centro de un Grafo: Excentricidad

Dado G = (V, A), la **excentricidad** de un nodo v se define como la **máxima** de todas las longitudes mínimas de los caminos entre cada uno de los otros nodos y el nodo v:

**e(v) = max(d(u, v))**, siendo d las distancias desde los nodos u hacia v

El **centro de G** es un vértice de **mínima excentricidad**.

**Para obtener el centro de un grafo:**
1. Aplicar Floyd para obtener el largo de los caminos.
2. Encontrar el **máximo** valor en cada columna i, y con ello se obtiene la excentricidad de i.
3. Encontrar el vértice con **excentricidad mínima**: el centro de G.

### Ejercicio Floyd y excentricidad

Grafo con vértices A, B, C, D, E:

**Matriz de distancias mínimas (resultado de Floyd):**

| D/H | A | B | C | D | E |
|-----|---|---|---|---|---|
| **A** | 0 | 3 | 1 | 4 | 2 |
| **B** | 6 | 0 | 7 | 10 | 3 |
| **C** | 4 | 2 | 0 | 8 | 1 |
| **D** | 9 | 7 | 5 | 0 | 6 |
| **E** | 3 | 6 | 4 | 7 | 0 |

**Tabla de excentricidades:**

| Nodo | Distancias mínimas hacia los demás | Excentricidad |
|------|------------------------------------|---------------|
| A    | 0, 6, 4, 9, 3                      | 9             |
| B    | 3, 0, 2, 7, 6                      | 7             |
| C    | 1, 7, 0, 5, 4                      | 7             |
| D    | 4, 10, 8, 0, 7                     | 10            |
| **E** | 2, 3, 1, 6, 0                    | **6** ← Centro |

> El **Centro del Grafo** es **E**, con excentricidad **6**.

---

## 10. Cerradura Transitiva: Algoritmo de Warshall

Puede ser interesante saber sólo si existe un camino que vaya del vértice i al j:
- La matriz de costos indicará **1** si hay arco, o **0** si no lo hay.
- Se desea obtener la matriz A tal que **A[i,j] = 1** si hay camino de i a j, o **0** si no lo hay.
- Se conoce como **cerradura transitiva** de la matriz de adyacencia.

### Pseudocódigo de Warshall

```pascal
procedure Warshall (A : array[1..n,1..n] of boolean;
                    C : array[1..n,1..n] of boolean);
i, j, k : integer;
COM
  for i:= 1 to n do
    for j:= 1 to n do
      A[i,j]:= C[i,j];
  for k:= 1 to n do
    for i:= 1 to n do
      for j:= 1 to n do
        if A[i,j] = false
          then A[i,j]:= A[i,k] and A[k,j];
FIN;
```

**Versión en pseudocódigo general:**

```
// A y C matrices de booleanas con tamaño NxN
Para todo i = 1...n hacer
    Para todo j = 1...n hacer
        A[i,j] = C[i,j]
    FinPara
    A[i, i] = 0
FinPara

Para todo k = 1...n hacer
    Para todo i = 1...n hacer
        Para todo j = 1...n hacer
            Si A[i,j] = false entonces
                A[i,j] = A[i,k] and A[k,j]
            FinSi
        FinPara
    FinPara
FinPara
```

---

## 11. Recorridos de Grafos Dirigidos

Es necesario visitar los vértices y los arcos de forma sistemática.

La **búsqueda en profundidad (BPF)** es una generalización del recorrido en preorden de árboles.

**Procedimiento:**
1. Dado un grafo G, inicialmente todos los vértices están marcados como **no visitados**.
2. Se selecciona un vértice **v** como vértice de partida y se marca como visitado.
3. Luego se recorre cada vértice no visitado adyacente a v usando recursivamente la búsqueda en profundidad.
4. Una vez visitados todos los vértices que se pueden alcanzar desde v, la búsqueda está completa.
5. Si quedan vértices sin visitar, se selecciona otro como partida y se repite el procedimiento.

### Pseudocódigo BPF

```
bpf(vértice, visitados)

    Si vértice está en visitados salir
    Sino
        agregar "vértice" a visitados
        // hacer algo con vértice
        Para cada w adyacente de vértice hacer
            bpf(w, visitados)
        FinPara
    FinSi
Fin
```

**Orden de ejecución:** O(a), donde a es la cantidad de arcos.

- No se llama a bpf en ningún vértice más de una vez.
- El tiempo consumido en recorrer los adyacentes es proporcional a la suma de las longitudes de las listas: O(a).

### Bosque abarcador en profundidad

Los arcos que llevan a vértices nuevos se conocen como **"arcos de árbol"** y forman un **"bosque abarcador en profundidad"**.

Existen otros tres tipos de arcos:
- **Arco de retroceso**: va de un vértice a uno de sus antecesores en el árbol.
- **Arco de avance**: va de un vértice a un descendiente propio.
- **Arco cruzado**: va de un vértice a otro que no es ni antecesor ni descendiente.

### Identificación de los tipos de arco

- Numerar en profundidad.
- Si el arco es de **avance**: va de un vértice de baja numeración a uno de alta numeración (que ya fue visitado).
- Si es de **retroceso**: a la inversa (y el destino es un ancestro).
- Los **arcos cruzados** van de alta numeración a baja numeración (pero el destino no es un ancestro).
- w es un descendiente de v si y sólo si: **Num(v) < Num(w) ≤ Num(v) + Cantidad de descendientes de v**

---

## 12. Obtención de un Camino

### Versión orientada a objetos

```
De TVertice
método Camino (Destino : Tvértice; ElCamino: TCamino);
w : Tvértice;
COM
  Visitar(); Agregar("this", ElCamino)
  Para cada adyacente w
    Si w = Destino entonces Guardar(ElCamino+Destino)
    Sino
      Si no(visitado(w)) entonces w.Camino(Destino,ElCamino)
    Fin si
  Fin para cada
  Quitar("this", ElCamino)
FIN
```

### Versión funcional con stack

```
// estamos bajo un grafo
obtenerCamino(origen, destino){
    visitados <- crear conjunto de visitados
    stack <- crear stack de nodos
    devolver obtenerCaminoAux(origen, destino, visitados, stack)
}
```

```
// estamos bajo un grafo
obtenerCaminoAux(nodo, destino, visitados, stack){
    Si nodo en visitados entonces devolver nulo
    Si nodo == destino
        devolver copia de stack
    Sino
        visitados.add(nodo)
        stack.push(nodo)

        Para cada w adyacente de nodo hacer
            resultado <- obtenerCaminoAux(w, destino, visitados, stack)
            Si resultado no es nulo entonces devolver resultado
        FinPara

        visitados.remove(nodo)
        stack.pop
        devolver nulo
    }
```

### Formas de recuperar caminos

- El algoritmo presentado es una propuesta que admite variantes, por ejemplo, en qué lugar verificar si se llega al Destino. Continúa realizando una "bpf" luego de haber encontrado un camino.
- "El Camino" que se define es en realidad una colección de vértices que se maneja con disciplina **LIFO**, y que contiene todos los vértices que todavía están pendientes en la recursión.
- Para recuperar **todos** los caminos posibles entre un par de vértices: reflexionar sobre el hecho de estar marcado como visitado.
- Para ayudar a clasificar los tipos de arcos de una recorrida: notar la diferencia entre **estar en el camino** y haber sido **visitado**.

---

## 13. Grafos Dirigidos Acíclicos (GDA)

El GDA es un grafo dirigido **sin ciclos**.

- Son más generales que los árboles, pero menos que los grafos dirigidos arbitrarios.
- Útiles para representar **expresiones aritméticas** con subexpresiones comunes.
- También son apropiados para representar **órdenes parciales**.

**Ejemplo de expresión aritmética:**
```
((a + b)* c + ((a + b) + e) * (e + f)) * ((a + b) * c)
```

### Prueba de Aciclidad

Se realiza búsqueda en profundidad y **si se encuentra un arco de retroceso, el grafo tiene un ciclo**.

Si un grafo dirigido tiene un ciclo, siempre habrá un arco de retroceso en la búsqueda en profundidad.

---

## 14. Clasificación Topológica

Es un proceso de asignación de un **orden lineal** a los vértices de un grafo dirigido acíclico tal que, si existe un arco del vértice i al vértice j, **i aparece antes que j** en el ordenamiento lineal de todos los vértices ("orden parcial").

**Ejemplos:**
- Proyecto que se divide en tareas, con relaciones de órdenes específicos de ejecución.
- Previaturas de cursos.

Los GDA pueden usarse para modelar de forma natural estas situaciones.

### Ejemplo

Estructura de previaturas de 5 cursos: **C1, C2, C3, C4, C5** es una clasificación topológica del grafo.

### Algoritmo de Clasificación Topológica

**Estrategia:**
1. Invertir los arcos, indicando entonces las dependencias (ej., C5 depende de C3 y de C4).
2. Ejecutar una búsqueda en profundidad, con procesamiento en la **salida recursiva**.
3. Ejecutar para cada vértice que no tenga arcos incidentes (vértices "finales").

**Pseudocódigo auxiliar:**

```
// estamos en grafo
clasificacionTopologicaAux(nodo, visitados, lista){
    Si nodo NO está en visitados entonces
        agregar nodo a visitados

        Para cada w adyacente de nodo hacer
            clasificacionTopologicaAux(w, visitados, lista)
        FinPara

        agregar nodo al principio de lista
        // se pueden realizar otras tareas
    FinSi
}
```

**Pseudocódigo principal:**

```
// estamos en grafo
clasificacionTopologica(){
    visitados <- crear conjunto de visitados
    listaNodos <- []

    Para cada vertice en el grafo hacer
        clasificacionTopologicaAux(vertice, visitados, listaNodos)
    FinPara

    devolver listaNodos
}
```

---

## 15. Ejercicios del Curso

### Ejercicio: Floyd y excentricidad (grafo A–F)

Dado el grafo con vértices A, B, C, D, E, F y arcos:
- A→B = 4, A→C = 3, A→D = 1
- C→B = 2, C→E = 7
- D→E = 5
- E→F = 3, E→C = 7 (arco desde F→C = 2)
- B→F = 4

Tareas:
1. Aplique el algoritmo de Warshall para calcular la cerradura transitiva.
2. Calcule los caminos mínimos, excentricidad y centro del grafo.

### Ejercicio: Floyd con iteraciones (grafo A–D)

Dado el grafo con vértices A, B, C, D y arcos:
- A→B = 7, A→D = 1
- B→D = 2, B→C = 4 (o hacia D=4)
- C→A = 4, C→D = 3
- D→B = 2

Tareas:
1. Aplique el algoritmo de Floyd mostrando **cada iteración**.
2. ¿Cuál es el Centro del Grafo? ¿Por qué?
3. Escriba un algoritmo que implemente una **BÚSQUEDA EN AMPLITUD** de un grafo y exprese el orden del tiempo de ejecución del mismo.

### Ejercicio: Orden topológico (grafo A–F)

Dado el grafo con vértices A, B, C, D, E, F:
- A→B = 4, A→C = 3, A→D = 1
- C→B = 2, C→E = 7
- D→E = 5
- E→F = 3
- B→F = 4

Halle uno o más **órdenes topológicos** para la tarea "F".

### Ejercicio: Ciclos y componentes fuertes

Dado el mismo grafo anterior:
- Determine si **contiene ciclos**.
- Encuentre los **componentes fuertes** y el **grafo reducido**.

---

## 16. Ejemplos Prácticos: Variantes de Dijkstra

### Problema 1 — Dijkstra con aristas bloqueadas

**Enunciado:** Dado un grafo dirigido con pesos positivos, se bloquea temporalmente un conjunto de aristas. Calcula el camino más corto desde un nodo fuente a todos los nodos, ignorando las aristas bloqueadas.

**Se necesita:** Una matriz de booleanos H que defina para cada par de vértices si la arista está disponible.

**Modificación:** Agregar la condición `H[w, v]` al `if` de relajación:

```
procedure DIJKSTRA(origen, G, C, H)
    for v ∈ vertices(G) do
        D[v] ← Inf
        P[v] ← ∅
    end for

    NoVisitados ← vertices(G)
    D[origen] ← 0

    while NoVisitados ≠ ∅ do
        elegir w en NoVisitados tal que D[w] sea mínimo
        remover w de NoVisitados
        for v ∈ NoVisitados do
            if D[w] + C[w,v] < D[v] y H[w,v] then
                D[v] ← D[w] + C[w,v]
                P[v] ← w
            end if
        end for
    end while
end procedure
```

### Problema 2 — Dijkstra con parada obligatoria

**Enunciado:** Desde un nodo fuente s hasta un destino t, se debe pasar obligatoriamente por un nodo intermedio v.

**Solución:** Ejecutar Dijkstra **dos veces**: de s a v y luego de v a t. Combinar los resultados.

### Problema 3 — Dijkstra con horarios

**Enunciado:** Algunas rutas están disponibles solo en ciertos horarios. Dado un grafo y una hora, encontrar el camino más corto respetando esos horarios.

**Modelado:** Un vector de 0 a 23, donde cada entrada contiene una matriz indicando si está disponible o no la arista en esa hora.

```
procedure DIJKSTRA(origen, G, C, H, hora)
    for v ∈ vertices(G) do
        D[v] ← Inf
        P[v] ← ∅
    end for

    NoVisitados ← vertices(G)
    D[origen] ← 0

    while NoVisitados ≠ ∅ do
        elegir w en NoVisitados tal que D[w] sea mínimo
        remover w de NoVisitados
        for v ∈ NoVisitados do
            if D[w] + C[w,v] < D[v] y H[hora][w,v] then
                D[v] ← D[w] + C[w,v]
                P[v] ← w
            end if
        end for
    end while
end procedure
```

---

## 17. Ejemplos Prácticos: Variantes de Floyd-Warshall

### Algoritmos de referencia

**Floyd (con recuperación):**

```
procedure FLOYD(A, C, P, n)
    for i ← 0; i < n do
        for j ← 0; j < n do
            A[i,j] ← C[i,j]
            P[i,j] ← 0
        end for
    end for

    for k ← 0; k < n do
        for i ← 0; i < n do
            for j ← 0; j < n do
                if A[i,k] + A[k,j] < A[i,j] then
                    A[i,j] ← A[i,k] + A[k,j]
                    P[i,j] ← k
                end if
            end for
        end for
    end for
end procedure
```

**Warshall (cerradura transitiva):**

```
procedure WARSHALL(A, C, n)
    // A, C matrices booleanas
    for i ← 0; i < n do
        for j ← 0; j < n do
            A[i,j] ← C[i,j]
        end for
    end for

    for k ← 0; k < n do
        for i ← 0; i < n do
            for j ← 0; j < n do
                if A[i,j] = false then
                    A[i,j] ← A[i,k] and A[k,j]
                end if
            end for
        end for
    end for
end procedure
```

### Problema 1 — Contar caminos mínimos alternativos

**Enunciado:** Además de obtener la matriz de distancias mínimas entre todos los pares, contar cuántos caminos más cortos (de igual costo mínimo) existen entre cada par.

**Modificación:** Agregar una matriz de conteos Q y actualizarla según se encuentren caminos alternativos con la misma distancia mínima.

```
procedure FLOYD(A, C, P, Q, n)
    // Q cuenta los caminos alternativos
    for i ← 0; i < n do
        for j ← 0; j < n do
            A[i,j] ← C[i,j]
            P[i,j] ← 0
            Q[i,j] ← 0
        end for
    end for

    for k ← 0; k < n do
        for i ← 0; i < n do
            for j ← 0; j < n do
                if A[i,k] + A[k,j] < A[i,j] then
                    A[i,j] ← A[i,k] + A[k,j]
                    P[i,j] ← k
                end if
                if A[i,k] + A[k,j] = A[i,j] then
                    Q[i,j] ← Q[i,j] + 1
                end if
            end for
        end for
    end for
end procedure
```

### Problema 2 — Nodos críticos

**Enunciado:** Determinar qué nodos son críticos, es decir, si al quitarlos aumentan las distancias mínimas entre algún par de nodos. Utilizar Floyd.

**Estrategia:** Ejecutar el algoritmo sin cada nodo y comparar las matrices de distancias.

### Problema 3 — Arista cuya eliminación maximiza el incremento

**Enunciado:** Averiguar cuál es el mayor incremento de distancia mínima entre pares si se quita temporalmente una sola arista.

**Estrategia:** Eliminar aristas una a una y comparar las matrices de distancias.

---

## 18. Ejemplos Prácticos: Variantes de BPF

### Referencia: BPF base

```
procedure MAIN(G)
    Visitados ← ∅
    for v ∈ vertices(G) do
        if v ∉ Visitados then
            BPF(v, Visitados)
        end if
    end for
end procedure

procedure BPF(v, Visitado)
    agregar v a Visitados
    for w ∈ adyacente(v) do
        if w no visitado then
            BPS(w, Visitados)
        end if
    end for
end procedure
```

### Problema 1 — Detección de ciclos

**Enunciado:** Determinar si hay algún ciclo en el grafo dirigido.

**Estrategia:** Usar marcas temporales: visitado, en recursión, no visitado. Si se visita un nodo que ya está en la llamada recursiva activa, hay un ciclo.

```
procedure MAIN(G)
    Visitados ← ∅
    for v ∈ vertices(G) do
        if TieneCiclo(v, Visitados) then
            return true
        end if
    end for
    return false
end procedure

procedure TIENECICLO(v, Visitado)
    agregar v a Visitados
    for w ∈ adyacente(v) do
        if w ∈ Visitados then
            return true
        else if TieneCiclo(w, Visitados) then
            return true
        end if
    end for
    remover v de Visitados
    return false
end procedure
```

### Problema 2 — Todos los caminos excluyendo nodos

**Enunciado:** Desde un nodo s, encontrar todos los caminos posibles hasta t sin pasar por un conjunto de nodos P.

**Estrategia:** En BPF, verificar que el nodo no esté en P antes de continuar.

```
procedure MAIN(G, P, origen, destino)
    // P son los vértices que no se deben considerar
    Caminos ← ∅
    ArmarCaminos(origen, destino, ∅, Caminos, P)
    return Caminos
end procedure

procedure ARMARCAMINOS(v, destino, camino, Caminos, P)
    // camino se comporta como una pila
    camino.push(v)
    if v = destino then
        copia ← copiar camino
        agregar copia a Caminos
    else
        agregar v a Visitados
        for w ∈ adyacente(v) do
            if w ∉ Visitados and w ∉ P then
                ArmarCaminos(w, destino, camino, Caminos, P)
            end if
        end for
        remover v de Visitados
    end if
    camino.pop()
end procedure
```

---

*Fin del documento — AED UT04 Grafos Dirigidos, UCU 2026*
