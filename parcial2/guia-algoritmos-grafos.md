# Guía práctica — ¿Qué hace cada algoritmo de grafos?

---

## Dijkstra

**Qué hace:** encuentra el camino de menor costo desde UN nodo origen hacia TODOS los demás.

**Ejemplo real:** el GPS de tu celular. Salís de tu casa y querés saber el camino más rápido a cualquier lugar de la ciudad. Dijkstra calcula, de una sola vez, la ruta óptima desde tu casa a todos los destinos posibles.

**Te da como resultado:**
- La distancia mínima desde el origen a cada vértice
- Los predecesores de cada vértice (para reconstruir el camino)

**Caso típico en parcial:** *"Dado el grafo de aeropuertos con costos de vuelo, ¿cuál es el itinerario más barato de Montevideo a Asunción?"*
→ Aplicás Dijkstra desde Montevideo, y de la tabla de predecesores reconstruís el camino.

**Limitación importante:** no funciona si hay aristas con peso negativo.

---

## Floyd-Warshall

**Qué hace:** encuentra el camino de menor costo entre TODOS los pares de nodos a la vez.

**Ejemplo real:** una aerolínea quiere saber cuánto cuesta ir de cualquier ciudad a cualquier otra (incluyendo escalas). En lugar de correr Dijkstra desde cada ciudad, Floyd lo resuelve todo de una.

**Te da como resultado:**
- Una matriz de costos mínimos entre todos los pares
- Una matriz de predecesores (para reconstruir cualquier camino)

**Caso típico en parcial:** *"¿Cuál es la ciudad más conveniente para instalar el centro de mantenimiento, considerando que debe minimizar la distancia máxima a cualquier otra ciudad?"*
→ Corrés Floyd, obtenés la matriz de costos, calculás la excentricidad de cada nodo (su distancia máxima a los demás) y elegís el de menor excentricidad.

**También te preguntan:** reconstruir el camino mínimo entre dos ciudades concretas usando la matriz de predecesores.

---

## Warshall

**Qué hace:** igual que Floyd pero solo responde "¿existe algún camino?" — no calcula costos, solo conectividad.

**Ejemplo real:** una empresa quiere saber si hay alguna forma de enviar un paquete de ciudad A a ciudad B (no importa cuántas escalas ni cuánto cuesta, solo si es posible).

**Te da como resultado:**
- Una matriz booleana: `verdadero` si existe al menos un camino entre el par, `falso` si no

**Caso típico en parcial:** *"¿Están todas las ciudades conectadas entre sí? ¿Desde qué ciudades no se puede llegar a Montevideo?"*
→ Corrés Warshall, revisás la fila/columna de Montevideo en la matriz de alcanzabilidad.

**Diferencia con Floyd:** Floyd minimiza costos. Warshall solo dice sí o no. Si el enunciado no menciona costos ni distancias, usás Warshall.

---

## DFS (recorrido en profundidad)

**Qué hace:** recorre el grafo yendo tan lejos como puede por un camino antes de retroceder, como explorar un laberinto tomando siempre el primer pasillo disponible hasta llegar a un callejón sin salida.

**Ejemplo real:** buscar si hay una ruta de escape en un laberinto. Vas por un camino hasta el fondo; si no salís, volvés y probás el siguiente.

**Te da como resultado:**
- El orden en que se visitan los vértices
- Un árbol/bosque abarcador (qué aristas se usaron)

**Casos típicos en parcial:**
- Base para detección de ciclos
- Base para clasificación topológica
- Base para puntos de articulación
- *"Recorra el grafo desde el nodo X usando DFS e indique el orden de visita"*

---

## BEA (recorrido en amplitud / BFS)

**Qué hace:** recorre el grafo nivel por nivel — primero todos los vecinos directos, luego los vecinos de los vecinos, y así. Usa una cola internamente.

**Ejemplo real:** en una red social, querés encontrar amigos en común. Primero revisás tus amigos directos (nivel 1), luego los amigos de tus amigos (nivel 2), etc.

**Te da como resultado:**
- El orden de visita por niveles
- El camino más corto en cantidad de saltos (sin considerar pesos) entre el origen y cualquier nodo

**Caso típico en parcial:** *"¿Cuántas escalas mínimas necesita John Goodman para llegar a Kevin Bacon en el grafo de películas?"*
→ BEA desde Kevin Bacon; la distancia en saltos a John Goodman es su número de Bacon.

**Diferencia con DFS:** DFS va profundo primero. BEA va ancho primero. Para "mínimo número de pasos/saltos" sin pesos → siempre BEA.

---

## Todos los caminos (DFS con backtracking)

**Qué hace:** lista absolutamente todos los caminos posibles entre un origen y un destino, sin repetir nodos. Usa DFS pero al retroceder desmarca el nodo como visitado para poder usarlo en otro camino.

**Ejemplo real:** una agencia de viajes quiere mostrarle al cliente todas las combinaciones de vuelos posibles entre dos ciudades.

**Te da como resultado:**
- Una lista de caminos, donde cada camino es una lista de vértices

**Caso típico en parcial:** *"Liste todos los itinerarios posibles de Montevideo a Porto Alegre. ¿Cuál tiene menor costo?"*
→ Corrés el algoritmo, obtenés todos los caminos y comparás sus costos.

**Diferencia con Dijkstra:** Dijkstra da el mejor camino. Este algoritmo da todos los caminos (podés usarlo para encontrar el mejor, pero es más lento).

---

## Detección de ciclos

**Qué hace:** determina si el grafo tiene algún ciclo (un camino que vuelve a un nodo ya visitado). Usa DFS con tres estados: NO_VISITADO, EN_PILA, TERMINADO. Si durante el DFS encontrás un nodo que está EN_PILA, hay ciclo.

**Ejemplo real:** en un sistema de compilación, el módulo A depende de B, B depende de C, y C depende de A — eso es un ciclo y el sistema nunca puede compilar.

**Te da como resultado:**
- Verdadero o falso

**Caso típico en parcial:** se pregunta antes de aplicar clasificación topológica, o directamente *"¿el grafo de dependencias tiene ciclos?"*

---

## Clasificación topológica

**Qué hace:** ordena los vértices de un grafo acíclico dirigido (DAG) de forma que todas las aristas vayan de izquierda a derecha — es decir, si hay una arista de A a B, A aparece antes que B en el orden.

**Ejemplo real:** el plan de estudios de la facultad. "Algoritmos" requiere haber aprobado "Programación 1". La clasificación topológica te da un orden válido en el que podés cursar todas las materias sin violar prerequisitos.

**Te da como resultado:**
- Una lista de vértices en orden topológico válido (puede haber más de uno válido)

**Caso típico en parcial:** *"Dado el grafo de tareas de un proyecto donde las aristas indican dependencias, ¿en qué orden se deben ejecutar las tareas?"*
→ Clasificación topológica. Primero verificás que no haya ciclos.

---

## Prim

**Qué hace:** construye el árbol de expansión mínima creciendo desde un nodo inicial — en cada paso agrega la arista más barata que conecta un nodo ya incluido con uno que todavía no está.

**Ejemplo real:** una empresa de telecomunicaciones quiere tender cables para conectar 10 edificios entre sí. No necesitan conectar todos directamente, solo que haya un camino entre cualquier par. Prim encuentra la red de cables de menor costo total.

**Te da como resultado:**
- Un nuevo grafo (el árbol de expansión mínima) que conecta todos los vértices con el menor costo total posible de aristas

**Caso típico en parcial:** *"Se quieren conectar todas las ciudades con rutas pavimentadas con el menor presupuesto posible. No es necesario conectar cada par directamente. ¿Qué rutas se construyen?"*

---

## Kruskal

**Qué hace:** construye el mismo árbol de expansión mínima que Prim pero desde otro ángulo — ordena todas las aristas de menor a mayor peso y las va agregando una a una, saltando las que formarían un ciclo.

**Ejemplo real:** misma empresa de telecomunicaciones, pero ahora un ingeniero lista todas las posibles conexiones ordenadas por costo y va aprobando las más baratas siempre que no formen un anillo redundante.

**Te da como resultado:**
- El mismo resultado que Prim: el árbol de expansión mínima

**Prim vs Kruskal — ¿cuándo usar cada uno?**
- Si el enunciado da un nodo de inicio → **Prim** (crece desde ese nodo)
- Si el enunciado habla de ordenar o priorizar aristas → **Kruskal**
- En la práctica del parcial, ambos dan el mismo árbol; la diferencia es el proceso

---

## Puntos de articulación

**Qué hace:** encuentra los vértices cuya eliminación desconecta el grafo en dos o más partes. Usa DFS registrando el tiempo de descubrimiento de cada nodo y el valor `low` (el nodo más antiguo al que puede llegar ese subárbol).

**Ejemplo real:** en una red de internet, ¿qué routers son críticos? Si uno falla y la red se parte en dos mitades que no pueden comunicarse, ese router es un punto de articulación.

**Te da como resultado:**
- Una lista de vértices que son puntos de articulación

**Caso típico en parcial:** *"En la red de ciudades, ¿qué ciudad, si se cierra por una crisis, impide la comunicación entre el resto?"* o *"¿Cuáles son los nodos críticos de la red?"*

---

## Resumen de un vistazo

| Algoritmo | Origen → Destino | Qué calcula | Resultado |
|---|---|---|---|
| Dijkstra | 1 origen → todos | Menor costo (pesos positivos) | Mapa de distancias + predecesores |
| Floyd | Todos → todos | Menor costo entre todos los pares | Matriz de costos + predecesores |
| Warshall | Todos → todos | ¿Existe camino? (sin costos) | Matriz booleana |
| DFS | Desde un origen | Orden de visita en profundidad | Árbol abarcador / base para otros |
| BEA | Desde un origen | Mínimo número de saltos | Orden por niveles |
| Todos los caminos | 1 origen → 1 destino | Lista de todos los caminos | Lista de listas de vértices |
| Detección de ciclos | Todo el grafo | ¿Hay ciclo? | Verdadero / falso |
| Clasificación topológica | Todo el grafo (DAG) | Orden válido de ejecución | Lista ordenada de vértices |
| Prim | Desde un nodo | Árbol de expansión mínima | Nuevo grafo (subconjunto de aristas) |
| Kruskal | Todo el grafo | Árbol de expansión mínima | Mismo que Prim |
| Puntos de articulación | Todo el grafo | Nodos cuya remoción desconecta | Lista de vértices críticos |
