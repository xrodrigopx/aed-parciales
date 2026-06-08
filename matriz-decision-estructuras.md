# Matriz de decisión — ¿Cuándo usar cada estructura?

---

## Paso 1 — ¿Los datos tienen relaciones entre sí?

```
¿Los elementos se conectan entre sí (no solo padre-hijo)?
    SÍ → GRAFO  (ver sección grafos abajo)
    NO → ¿Hay jerarquía padre-hijo?
            SÍ → ÁRBOL  (ver sección árboles abajo)
            NO → LINEAL / ACCESO POR CLAVE  (ver sección lineal abajo)
```

---

## Estructuras lineales y acceso por clave

| Señal en el enunciado | Estructura | Por qué |
|---|---|---|
| "atender en orden de llegada", "fila", "cola de espera", "primero en llegar primero en salir" | **Cola** | FIFO — el primero que entra es el primero que sale |
| "deshacer", "historial", "pila de llamadas", "último en entrar primero en salir", "retroceder" | **Pila** | LIFO — el último que entra es el primero que sale |
| "sin duplicados", "¿pertenece o no?", "membresía" | **Conjunto** | Solo importa si el elemento está o no; no permite repetidos |
| "secuencia", "lista de elementos", "insertar en cualquier posición", "recorrer en orden" | **Lista enlazada** | Secuencia flexible; inserción/eliminación O(1) si tenés el nodo |
| "buscar por clave rápido", "O(1)", "no importa el orden", "diccionario de acceso directo" | **Hash / Diccionario** | Acceso por clave en O(1) promedio; sin orden garantizado |
| "clave-valor con orden", "iterar ordenado por clave" | **Mapa** | Como diccionario pero mantiene orden (generalmente por clave) |
| "buscar palabras por prefijo", "autocompletado", "¿existe la palabra?", "string como clave" | **Trie** | Cada letra es un nodo; búsqueda O(longitud de la palabra) |
| "Trie pero con muchos nodos vacíos", "memoria limitada", "strings largos con prefijos comunes" | **Trie Patricia** | Comprime cadenas de nodos con un solo hijo; ahorra memoria |

---

## Árboles

| Señal en el enunciado | Estructura | Por qué |
|---|---|---|
| "jerarquía", "padre con N hijos", "árbol de directorios", "organigrama", "taxonomía" | **Árbol genérico** | Cada nodo puede tener cualquier cantidad de hijos |
| "árbol con máximo 2 hijos", "subárbol izquierdo y derecho", "expresión aritmética" | **Árbol binario** | Estructura con exactamente 0, 1 o 2 hijos por nodo |
| "buscar eficientemente", "datos comparables", "insertar y buscar en O(log n) promedio" | **ABB** | Los menores van a la izquierda, los mayores a la derecha; O(log n) si está balanceado |
| "insertions y eliminaciones frecuentes", "siempre O(log n) garantizado", "árbol siempre balanceado" | **AVL** | Se rebalancea automáticamente; garantiza O(log n) en el peor caso |

**¿ABB o AVL?**
- Si los datos llegan en orden aleatorio → **ABB** alcanza
- Si los datos pueden llegar en orden creciente/decreciente (degeneraría en lista) → **AVL**

---

## Grafos

### Primero: ¿dirigido o no dirigido?

| Señal | Tipo |
|---|---|
| "vuelos", "dependencias", "seguir a alguien", "relación asimétrica" | **Dirigido** — A→B no implica B→A |
| "amistades", "caminos bidireccionales", "red eléctrica", "relación simétrica" | **No dirigido** — A-B implica B-A |

### Segundo: ¿qué algoritmo?

| Pregunta del enunciado | Algoritmo | Cuándo |
|---|---|---|
| "camino más corto entre X e Y" | **Dijkstra** | Un solo origen, pesos positivos |
| "camino más corto entre todos los pares", "mejor nodo central", "excentricidad" | **Floyd** | Todos contra todos, pesos positivos o negativos (sin ciclos negativos) |
| "¿existe camino entre X e Y?", "¿están conectados?", solo conectividad sin costos | **Warshall** | Solo booleano: sí/no; más simple que Floyd |
| "recorrer todo el grafo en profundidad", "detectar ciclos", "clasificación topológica" | **DFS** | Explora tan lejos como puede antes de retroceder |
| "recorrer nivel por nivel", "camino más corto sin pesos", "número de saltos" | **BEA** | Explora vecinos antes de avanzar más lejos; usa cola |
| "todos los itinerarios posibles", "listar todos los caminos de X a Y" | **DFS con backtracking** | DFS que desmarca al retroceder |
| "¿el grafo tiene ciclos?" | **DFS con 3 estados** | NO_VISITADO / EN_PILA / TERMINADO |
| "orden de ejecución de tareas", "dependencias sin ciclo", "orden válido" | **Clasificación topológica** | DFS; al terminar un nodo se agrega al frente de la lista |
| "árbol de expansión mínima", "red de menor costo total", "conectar todos los nodos" | **Prim o Kruskal** | Prim: crece desde un nodo. Kruskal: ordena aristas y agrega sin ciclos |
| "si se elimina un nodo, ¿el grafo se desconecta?", "punto crítico de la red" | **Puntos de articulación** | DFS con tiempo de descubrimiento y valor `low` |

### Prim vs Kruskal

| Señal | Algoritmo |
|---|---|
| "partir de un nodo específico", grafo denso (muchas aristas) | **Prim** |
| "ordenar aristas", grafo disperso (pocas aristas) | **Kruskal** |

---

## Resumen rápido (para cuadernola)

```
¿Conexiones arbitrarias?          → Grafo
¿Jerarquía padre-hijo?
    N hijos                       → Árbol genérico
    2 hijos, sin orden            → Árbol binario
    2 hijos, ordenado, O(log n)   → ABB
    ABB + siempre balanceado      → AVL
¿Orden de llegada importa?
    FIFO                          → Cola
    LIFO                          → Pila
¿Sin duplicados?                  → Conjunto
¿Secuencia flexible?              → Lista enlazada
¿Clave → valor, O(1)?             → Hash / Diccionario
¿Clave → valor, ordenado?         → Mapa
¿Buscar por prefijo de string?    → Trie / Trie Patricia
```
