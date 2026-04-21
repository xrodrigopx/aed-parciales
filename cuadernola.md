# Cuadernola — AED Primer Parcial

Material de estudio consolidado para el primer parcial de Algoritmos y Estructuras de Datos — UCU.

---

## Cómo elegir la estructura de datos correcta

La justificación del TDA vale puntos en el parcial. No alcanza con decir "uso ABB porque sí" — hay que razonar. El proceso tiene dos fases: **diagnosticar el problema**, luego **comparar las opciones**.

---

### Fase 1 — Las preguntas que hay que hacerse

**1. ¿Cuál es la operación más crítica del problema?**

Es la pregunta más importante. El problema siempre tiene una operación que se va a repetir muchas veces o que tiene que ser rápida. Identificarla dice qué TDA optimizar.

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

### Cómo redactar la justificación en el parcial

El examinador espera esta estructura:

1. **Nombrar la operación crítica** del problema.
2. **Comparar al menos dos opciones** y descartar la inferior.
3. **Concluir con el orden de tiempo** como argumento.

**Ejemplo (problema de películas con búsquedas por año):**

> "Se elige **ABB** con clave = año de estreno. La operación crítica es la búsqueda y el filtrado por rango cronológico, que se resuelve eficientemente con el recorrido inorden (O(n)), el cual produce los resultados en orden ascendente sin costo adicional de ordenamiento. Una Lista realizaría las mismas operaciones en O(n) pero sin orden implícito, requiriendo un paso extra de ordenamiento. Una Pila o Cola no son adecuadas porque no ofrecen acceso por clave. Como los datos se cargan una sola vez desde el archivo, un ABB simple es suficiente; si hubiera inserciones frecuentes en producción, se preferiría AVL para garantizar O(log n)."

---

### Regla de oro

> **¿El problema me pide encontrar algo específico entre muchos elementos?**
> - Sí → árbol (ABB o AVL según frecuencia de inserción)
> - No, solo procesarlos en orden de llegada → Cola/Pila
> - No, solo guardarlos sin duplicados → Conjunto
> - No, con acceso posicional → Lista

---

## Pseudocódigos de referencia

Operaciones clave de cada TDA, con lenguaje natural, pre/postcondiciones y análisis de orden.

---

### Lista Enlazada

**Casos de uso típicos:**
- Acumular resultados de un recorrido (ej: inOrden llena una lista con los datos del árbol).
- Representar la lista blanca o negra en el patrón `comboViable` / `preparadoViable`.
- Almacenar el camino desde la raíz hasta un nodo (ej: `encontrarCamino` para calcular parentesco).
- Cualquier colección donde el orden de inserción importa y no necesitás búsqueda eficiente.

#### insertar(etiqueta, dato) — al final

**Lenguaje natural:** Crea un nodo con la etiqueta y dato dados. Si la lista está vacía, lo coloca como primero. Si no, recorre hasta el último nodo y lo enlaza al final.

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

---

#### buscar(clave)

**Lenguaje natural:** Recorre la lista comparando la etiqueta de cada nodo con la clave. Retorna el nodo si lo encuentra, nulo si no existe.

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

---

#### eliminar(clave)

**Lenguaje natural:** Busca el nodo con la clave dada y lo desvincula. Si es el primero, actualiza `primero`; si no, conecta el antecesor directamente al sucesor.

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

---

### Pila

**Casos de uso típicos:**
- Simular la recursión de forma iterativa (cada llamada recursiva equivale a un `apilar`).
- Verificar balanceo de paréntesis / expresiones anidadas.
- Historial de operaciones: "deshacer" → desapilar el último estado.
- Evaluar o convertir expresiones aritméticas (notación infija ↔ postfija).

#### apilar(dato)

**Lenguaje natural:** Crea un nuevo nodo con el dato dado y lo coloca en el tope de la pila, enlazándolo con el tope anterior.

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

---

#### desapilar()

**Lenguaje natural:** Guarda el dato del tope, avanza `tope` al siguiente nodo y retorna el dato guardado.

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

---

### Cola

**Casos de uso típicos:**
- Procesar elementos en el orden en que llegaron: sistemas de turnos, colas de atención, colas de impresión.
- Recorrido por niveles (BFS) de un árbol: se encola la raíz, se desencola y se encolan sus hijos, y así sucesivamente.
- Simular flujos donde el primero en entrar es el primero en salir (FIFO estricto).
- Planificación de tareas donde el orden de llegada define la prioridad.

#### encolar(dato)

**Lenguaje natural:** Crea un nuevo nodo y lo enlaza al final mediante el puntero `posterior`. Si la cola estaba vacía, `frente` también apunta al nuevo nodo.

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

---

#### desencolar()

**Lenguaje natural:** Guarda el dato del frente, avanza `frente` al siguiente nodo y, si la cola quedó vacía, también actualiza `posterior` a nulo.

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

---

### Conjunto

**Casos de uso típicos:**
- Representar la lista blanca de elementos permitidos cuando **no puede haber duplicados** y solo importa la pertenencia.
- Calcular unión, intersección o complemento de grupos de elementos.
- Marcar nodos ya visitados en un recorrido (equivalente a un `visited set`).
- Cualquier colección donde la pregunta central es "¿este elemento ya existe?".

#### insertar(dato)

**Lenguaje natural:** Verifica primero con `buscar` que el dato no exista. Si no existe, crea un nodo y lo inserta al frente en O(1). El invariante del conjunto es que no hay duplicados.

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

---

#### buscar(dato)

**Lenguaje natural:** Recorre el conjunto comparando cada elemento con el dato buscado. Retorna el nodo si existe, nulo si no.

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

---

### ABB — Árbol Binario de Búsqueda

**Casos de uso típicos:**
- Catálogos con búsqueda por clave (películas por año, productos por código) cuando los datos se cargan una sola vez y no crecen dinámicamente.
- Obtener elementos en orden ascendente sin costo extra de ordenamiento (inOrden).
- Filtrar por rango de valores: recorrer solo la rama relevante según la clave.
- Base para cualquier algoritmo recursivo árbol/nodo pedido en el Ejercicio 2 del parcial.
- **Limitación:** si el orden de inserción es secuencial (ej: 1, 2, 3, 4…) el árbol se degenera a una lista y todas las operaciones pasan a O(n). En ese caso usar AVL.

#### insertar(etiqueta, dato)

**Lenguaje natural:** Crea un nuevo elemento. Si el árbol está vacío, lo coloca como raíz. Si no, delega recursivamente al nodo raíz: menores van a la izquierda, mayores a la derecha, duplicados se rechazan.

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

---

#### buscar(etiqueta)

**Lenguaje natural:** Recorre el árbol comparando la etiqueta buscada con la del nodo actual; baja a izquierda si es menor, a derecha si es mayor, hasta encontrarla o llegar a nulo.

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

---

#### eliminar(etiqueta)

**Lenguaje natural:** Localiza el nodo y lo elimina según tres casos: (1) sin hijos → se desvincula directamente; (2) un hijo → el hijo lo reemplaza; (3) dos hijos → se reemplaza por el predecesor inorden (máximo del subárbol izquierdo) y se elimina ese predecesor.

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

---

#### Recorridos del árbol

Los tres recorridos visitan todos los nodos exactamente una vez — todos son O(n). La diferencia es el **momento** en que se visita la raíz respecto a los subárboles.

| Recorrido | Orden | Cuándo usarlo |
|-----------|-------|---------------|
| **Inorden** | izq → raíz → der | ABB produce valores en orden ascendente; filtrar y recolectar resultados ordenados |
| **Preorden** | raíz → izq → der | Copiar o serializar el árbol; procesar el nodo antes de sus hijos |
| **Postorden** | izq → der → raíz | Calcular tamaño/altura/suma; liberar memoria; necesitás los hijos antes que el padre |

---

#### inOrden()

**Lenguaje natural:** Recorre izquierda → raíz → derecha. En un ABB produce los valores en orden ascendente de etiqueta. Es el recorrido más usado en los parciales para obtener resultados ordenados.

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

---

#### preOrden()

**Lenguaje natural:** Visita el nodo actual primero, luego recorre el subárbol izquierdo y luego el derecho. Útil cuando necesitás procesar o guardar el nodo antes de conocer sus hijos.

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

---

#### postOrden()

**Lenguaje natural:** Recorre primero el subárbol izquierdo, luego el derecho, y recién entonces visita el nodo actual. Útil cuando el resultado del nodo depende del resultado de sus hijos (altura, tamaño, suma, LTIM).

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

---

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
- Catálogos que **crecen continuamente** con inserciones frecuentes (ej: nuevas películas cada semana, nuevos usuarios, nuevas temperaturas).
- Cualquier escenario donde no se controla el orden de inserción y se necesita garantizar O(log n) en búsquedas.
- Sistemas en tiempo real donde una degradación a O(n) es inaceptable.
- **Regla práctica del parcial:** si el enunciado menciona actualizaciones frecuentes o datos que llegan en tiempo de ejecución → AVL sobre ABB.

#### Auxiliares: altura, actualizarAltura, factorBalance

**Lenguaje natural:** `altura` retorna la altura guardada en el nodo (o −1 si nulo). `actualizarAltura` recalcula la altura a partir de los hijos. `factorBalance` retorna la diferencia `h(der) − h(izq)` — si es −2 o +2, hay desbalance.

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

---

#### Rotaciones

| Tipo | BF nodo | BF hijo | Operación |
|------|---------|---------|-----------|
| **LL** | −2 | ≤ 0 | Rotación simple **derecha** en el nodo desbalanceado |
| **RR** | +2 | ≥ 0 | Rotación simple **izquierda** en el nodo desbalanceado |
| **LR** | −2 | +1 | Rotación **izquierda** en el hijo izquierdo, luego **derecha** en el nodo |
| **RL** | +2 | −1 | Rotación **derecha** en el hijo derecho, luego **izquierda** en el nodo |

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

---

#### balancear(nodo)

**Lenguaje natural:** Actualiza la altura del nodo, calcula su factor de balance y aplica la rotación correspondiente si está desbalanceado. Se invoca al regresar de cada inserción recursiva.

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

---

#### insertar(etiqueta, dato) — AVL

**Lenguaje natural:** Igual que en el ABB, pero al regresar de cada llamada recursiva se invoca `balancear()`, corrigiendo cualquier desbalance introducido en el camino de vuelta hacia la raíz.

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
