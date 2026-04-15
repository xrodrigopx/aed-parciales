# Pseudocódigos Completos — AED

Referencia unificada de todos los pseudocódigos del curso, organizados por TDA y tipo de operación.

---

## Lista Enlazada

### esVacia()

**Lenguaje natural:** Retorna verdadero si la lista no tiene elementos.

**Precondición:** ninguna.  
**Postcondición:** retorna `verdadero` si `primero = nulo`, `falso` en caso contrario.

```
Lista.esVacia(): booleano
  retornar primero = nulo
fin método
```

**Orden:** O(1)

---

### insertar(nodo) — al final

**Lenguaje natural:** Recorre la lista hasta el último nodo y enlaza el nuevo nodo al final.

**Precondición:** `nodo ≠ nulo`.  
**Postcondición:** el nodo queda como último elemento; el tamaño aumenta en 1.

```
Lista.insertar(nodo: Nodo<T>): void
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

### insertar(etiqueta, dato) — al final

**Lenguaje natural:** Crea un nodo con la etiqueta y dato dados y delega en `insertar(nodo)`.

**Precondición:** ninguna.  
**Postcondición:** equivalente al `insertar(nodo)` anterior.

```
Lista.insertar(etiqueta: Comparable, dato: T): void
  nodo ← nuevo Nodo(etiqueta, dato)
  insertar(nodo)
fin método
```

**Orden:** O(n)

---

### buscar(clave)

**Lenguaje natural:** Recorre la lista buscando el nodo cuya etiqueta coincida con la clave. Retorna el nodo si lo encuentra, nulo si no existe.

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

### eliminar(clave)

**Lenguaje natural:** Busca el nodo con la clave dada y lo desvincula de la lista. Si es el primero, actualiza `primero`; si no, conecta el antecesor directamente al sucesor.

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

### cantElementos()

**Lenguaje natural:** Recorre la lista completa contando cada nodo.

**Precondición:** ninguna.  
**Postcondición:** retorna un entero ≥ 0 con la cantidad de nodos.

```
Lista.cantElementos(): entero
  contador ← 0
  aux ← primero
  mientras aux ≠ nulo hacer
    contador ← contador + 1
    aux ← aux.getSiguiente()
  fin mientras
  retornar contador
fin método
```

**Orden:** O(n)

---

### imprimir()

**Lenguaje natural:** Recorre la lista e imprime la etiqueta de cada nodo.

**Precondición:** ninguna.  
**Postcondición:** imprime las etiquetas de todos los nodos en orden.

```
Lista.imprimir(): void
  aux ← primero
  mientras aux ≠ nulo hacer
    imprimir(aux.getEtiqueta())
    aux ← aux.getSiguiente()
  fin mientras
fin método
```

**Orden:** O(n)

---

## Pila

### esVacia()

**Lenguaje natural:** Retorna verdadero si no hay elementos en la pila.

**Precondición:** ninguna.  
**Postcondición:** retorna `verdadero` si `tope = nulo`.

```
Pila.esVacia(): booleano
  retornar tope = nulo
fin método
```

**Orden:** O(1)

---

### apilar(dato)

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

### desapilar()

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

### tope()

**Lenguaje natural:** Retorna el dato del elemento en el tope sin modificar la pila.

**Precondición:** `¬esVacia()`.  
**Postcondición:** retorna el dato del tope; la pila no se modifica.

```
Pila.tope(): T
  si esVacia() entonces
    lanzar error "Pila vacía"
  fin si
  retornar tope.getDato()
fin método
```

**Orden:** O(1)

---

## Cola — Arreglo Circular

### esVacia()

**Lenguaje natural:** Retorna verdadero si no hay elementos en la cola.

**Precondición:** ninguna.  
**Postcondición:** retorna `verdadero` si `cantidad = 0`.

```
Cola.esVacia(): booleano
  retornar cantidad = 0
fin método
```

**Orden:** O(1)

---

### esLlena()

**Lenguaje natural:** Retorna verdadero si el arreglo está al máximo de su capacidad.

**Precondición:** ninguna.  
**Postcondición:** retorna `verdadero` si `cantidad = MAX`.

```
Cola.esLlena(): booleano
  retornar cantidad = MAX
fin método
```

**Orden:** O(1)

---

### encolar(dato) — arreglo circular

**Lenguaje natural:** Inserta el dato en la posición `posterior` y avanza ese índice de forma circular usando módulo.

**Precondición:** `¬esLlena()`.  
**Postcondición:** el dato queda en la posición `posterior`; `posterior` avanza; `cantidad` aumenta en 1.

```
Cola.encolar(dato: T): void
  si esLlena() entonces
    lanzar error "Cola llena"
  fin si
  arreglo[posterior] ← dato
  posterior ← (posterior + 1) mod MAX
  cantidad ← cantidad + 1
fin método
```

**Orden:** O(1)

---

### desencolar() — arreglo circular

**Lenguaje natural:** Guarda el dato de la posición `frente`, avanza `frente` de forma circular y retorna el dato guardado.

**Precondición:** `¬esVacia()`.  
**Postcondición:** el elemento del frente es retornado; `frente` avanza; `cantidad` disminuye en 1.

```
Cola.desencolar(): T
  si esVacia() entonces
    lanzar error "Cola vacía"
  fin si
  dato ← arreglo[frente]
  frente ← (frente + 1) mod MAX
  cantidad ← cantidad - 1
  retornar dato
fin método
```

**Orden:** O(1)

---

### frente()

**Lenguaje natural:** Retorna el elemento del frente sin modificar la cola.

**Precondición:** `¬esVacia()`.  
**Postcondición:** retorna el dato del frente; la cola no se modifica.

```
Cola.frente(): T
  si esVacia() entonces
    lanzar error "Cola vacía"
  fin si
  retornar arreglo[frente]
fin método
```

**Orden:** O(1)

---

## Cola — Lista Enlazada

### encolar(dato) — lista enlazada

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

### desencolar() — lista enlazada

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

## Conjunto

### esVacio()

**Lenguaje natural:** Retorna verdadero si el conjunto no tiene elementos.

**Precondición:** ninguna.  
**Postcondición:** retorna `verdadero` si `primero = nulo`.

```
Conjunto.esVacio(): booleano
  retornar primero = nulo
fin método
```

**Orden:** O(1)

---

### buscar(dato)

**Lenguaje natural:** Recorre el conjunto buscando el dato. Retorna el nodo si existe, nulo si no.

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

### insertar(dato)

**Lenguaje natural:** Verifica primero con `buscar` que el dato no exista. Si no existe, crea un nodo y lo inserta al frente en O(1).

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

### eliminar(dato)

**Lenguaje natural:** Busca y elimina el dato del conjunto. Si es el primero, actualiza `primero`; si no, conecta el antecesor al sucesor.

**Precondición:** ninguna.  
**Postcondición:** si `dato` existía, es removido y retorna `verdadero`; si no, retorna `falso`.

```
Conjunto.eliminar(dato: T): booleano
  si esVacio() entonces
    retornar falso
  fin si
  si primero.getDato() = dato entonces
    primero ← primero.getSiguiente()
    retornar verdadero
  fin si
  aux ← primero
  mientras aux.getSiguiente() ≠ nulo hacer
    si aux.getSiguiente().getDato() = dato entonces
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

### esSubconjunto(otroConjunto)

**Lenguaje natural:** Recorre `otro` y para cada elemento verifica con `buscar` que esté en `this`. Si alguno no está, retorna falso.

**Precondición:** `otroConjunto ≠ nulo`.  
**Postcondición:** retorna `verdadero` si todo elemento de `otro` está en `this`.

```
Conjunto.esSubconjunto(otro: Conjunto<T>): booleano
  aux ← otro.getPrimero()
  mientras aux ≠ nulo hacer
    si buscar(aux.getDato()) = nulo entonces
      retornar falso
    fin si
    aux ← aux.getSiguiente()
  fin mientras
  retornar verdadero
fin método
```

**Orden:** O(n × m) — n = tamaño de `this`, m = tamaño de `otro`.

---

### union(otroConjunto)

**Lenguaje natural:** Crea un conjunto resultado, inserta todos los elementos de `this` y luego los de `otro`; `insertar` ignora los duplicados automáticamente.

**Precondición:** `otroConjunto ≠ nulo`.  
**Postcondición:** retorna un conjunto nuevo con A ∪ B.

```
Conjunto.union(otro: Conjunto<T>): Conjunto<T>
  resultado ← nuevo Conjunto vacío
  aux ← primero
  mientras aux ≠ nulo hacer
    resultado.insertar(aux.getDato())
    aux ← aux.getSiguiente()
  fin mientras
  aux ← otro.getPrimero()
  mientras aux ≠ nulo hacer
    resultado.insertar(aux.getDato())
    aux ← aux.getSiguiente()
  fin mientras
  retornar resultado
fin método
```

**Orden:** O(n × (n + m))

---

### interseccion(otroConjunto)

**Lenguaje natural:** Recorre `this` y solo inserta en el resultado los elementos que también están en `otro`.

**Precondición:** `otroConjunto ≠ nulo`.  
**Postcondición:** retorna un conjunto nuevo con A ∩ B.

```
Conjunto.interseccion(otro: Conjunto<T>): Conjunto<T>
  resultado ← nuevo Conjunto vacío
  aux ← primero
  mientras aux ≠ nulo hacer
    si otro.buscar(aux.getDato()) ≠ nulo entonces
      resultado.insertar(aux.getDato())
    fin si
    aux ← aux.getSiguiente()
  fin mientras
  retornar resultado
fin método
```

**Orden:** O(n × m)

---

### complemento(conjuntoUniversal)

**Lenguaje natural:** Recorre el universal e inserta en el resultado solo los elementos que NO están en `this`.

**Precondición:** `conjuntoUniversal ≠ nulo`.  
**Postcondición:** retorna un conjunto nuevo = U − A.

```
Conjunto.complemento(universal: Conjunto<T>): Conjunto<T>
  resultado ← nuevo Conjunto vacío
  aux ← universal.getPrimero()
  mientras aux ≠ nulo hacer
    si buscar(aux.getDato()) = nulo entonces
      resultado.insertar(aux.getDato())
    fin si
    aux ← aux.getSiguiente()
  fin mientras
  retornar resultado
fin método
```

**Orden:** O(u × n) — u = tamaño del universal, n = tamaño de `this`.

---

## Árbol Binario

### Calcular la altura

**Lenguaje natural:** La altura de un nodo es el máximo entre la altura del subárbol izquierdo y la del derecho, más 1. Un nodo nulo tiene altura −1 (por lo que una hoja tiene altura 0).

**Precondición:** ninguna.  
**Postcondición:** retorna −1 para árbol vacío; 0 para una hoja; h para lo demás.

```
Algoritmo altura(v)
    Si v es nulo entonces
        retornar -1
    Si no
        HL ← altura(izquierdo(v))
        HR ← altura(derecho(v))
        retornar max(HL, HR) + 1
```

**Orden:** O(n) — visita cada nodo exactamente una vez.

---

### Recorrido Preorden (raíz → izquierdo → derecho)

**Lenguaje natural:** Visita el nodo actual primero, luego recorre el subárbol izquierdo y luego el derecho.

**Precondición:** ninguna.  
**Postcondición:** todos los nodos son visitados en orden raíz-izquierdo-derecho.

```
Algoritmo preOrden(v)
    visitar(v)
    Para cada hijo w de v hacer
        preOrden(w)
```

**Orden:** O(n)

---

### Recorrido Postorden (izquierdo → derecho → raíz)

**Lenguaje natural:** Recorre primero los subárboles hijos (izquierdo y derecho) y luego visita el nodo actual.

**Precondición:** ninguna.  
**Postcondición:** todos los nodos son visitados en orden izquierdo-derecho-raíz.

```
Algoritmo postOrden(v)
    Para cada hijo w de v hacer
        postOrden(w)
    visitar(v)
```

**Orden:** O(n)

---

### Recorrido Inorden (izquierdo → raíz → derecho)

**Lenguaje natural:** Recorre el subárbol izquierdo, visita el nodo actual, y luego recorre el subárbol derecho. En un BST produce los valores en orden ascendente.

**Precondición:** ninguna.  
**Postcondición:** todos los nodos son visitados en orden izquierdo-raíz-derecho.

```
Algoritmo inOrden(v)
    Si tiene HijoIzquierdo
        HijoIzquierdo.inOrden
    visitar(v)
    Si tiene HijoDerecho
        HijoDerecho.inOrden
```

**Orden:** O(n)

---

### Imprimir expresión aritmética

**Lenguaje natural:** Recorre el árbol de expresión en inorden. Antes de bajar a la izquierda imprime "(", después de bajar a la derecha imprime ")". Una hoja imprime directamente su valor.

**Precondición:** el árbol representa una expresión aritmética válida (operadores en nodos internos, operandos en hojas).  
**Postcondición:** imprime la expresión completamente parentizada.

```
Algoritmo TNodoAB.printExpression
    Si tiene HijoIzquierdo
        imprimir("(")
        HijoIzquierdo.printExpression
    imprimir
    Si tiene HijoDerecho
        HijoDerecho.printExpression
        imprimir(")")
```

**Orden:** O(n)

---

### Evaluar expresión aritmética

**Lenguaje natural:** Si el nodo es hoja, devuelve su valor. Si es operador, evalúa recursivamente los dos subárboles y aplica el operador a los resultados.

**Precondición:** el árbol representa una expresión aritmética válida.  
**Postcondición:** retorna el valor numérico de la expresión.

```
Algoritmo TNodoAB.evalExpr
    Si esHoja
        Devolver elemento
    Sino
        x  <-- HijoIzquierdo.evalExpr
        y  <-- HijoDerecho.evalExpr
        ◊  <-- operador contenido
        Devolver x ◊ y
```

**Orden:** O(n)

---

### Contar las hojas de un árbol binario

**Lenguaje natural:** Un nodo es hoja si no tiene hijos. Se recorre el árbol: si el nodo actual es hoja, retorna 1; si no, retorna la suma de hojas del subárbol izquierdo más el derecho.

**Precondición:** ninguna.  
**Postcondición:** retorna la cantidad de nodos hoja del árbol.

```
TArbolBB.contarHojas(): entero
  si esVacio() entonces
    retornar 0
  fin si
  retornar raiz.contarHojas()
fin método

TElementoAB.contarHojas(): entero
  si hijoIzq = nulo Y hijoDer = nulo entonces
    retornar 1
  fin si
  contador ← 0
  si hijoIzq ≠ nulo entonces
    contador ← contador + hijoIzq.contarHojas()
  fin si
  si hijoDer ≠ nulo entonces
    contador ← contador + hijoDer.contarHojas()
  fin si
  retornar contador
fin método
```

**Orden:** O(n)

---

### Calcular la suma de todos los elementos de un árbol binario de enteros

**Lenguaje natural:** La suma es el valor del nodo actual más la suma del subárbol izquierdo más la suma del subárbol derecho. Un árbol vacío contribuye 0.

**Precondición:** ninguna.  
**Postcondición:** retorna la suma de los datos de todos los nodos.

```
TArbolBB.sumarElementos(): entero
  si esVacio() entonces
    retornar 0
  fin si
  retornar raiz.sumarElementos()
fin método

TElementoAB.sumarElementos(): entero
  suma ← this.getDato()
  si hijoIzq ≠ nulo entonces
    suma ← suma + hijoIzq.sumarElementos()
  fin si
  si hijoDer ≠ nulo entonces
    suma ← suma + hijoDer.sumarElementos()
  fin si
  retornar suma
fin método
```

**Orden:** O(n)

---

### Contar los nodos en el nivel n

**Lenguaje natural:** Se recorre el árbol llevando el nivel actual como parámetro. Cuando el nivel actual coincide con el objetivo, el nodo cuenta como 1. Si se supera el nivel objetivo, se detiene la recursión.

**Precondición:** `nivel ≥ 0`.  
**Postcondición:** retorna la cantidad de nodos que están exactamente en el nivel indicado (raíz = nivel 0).

```
TArbolBB.contarEnNivel(nivel: entero): entero
  si esVacio() entonces
    retornar 0
  fin si
  retornar raiz.contarEnNivel(nivel, 0)
fin método

TElementoAB.contarEnNivel(objetivo: entero, actual: entero): entero
  si actual = objetivo entonces
    retornar 1
  fin si
  si actual > objetivo entonces
    retornar 0
  fin si
  contador ← 0
  si hijoIzq ≠ nulo entonces
    contador ← contador + hijoIzq.contarEnNivel(objetivo, actual + 1)
  fin si
  si hijoDer ≠ nulo entonces
    contador ← contador + hijoDer.contarEnNivel(objetivo, actual + 1)
  fin si
  retornar contador
fin método
```

**Orden:** O(n)

---

## BST — Árbol Binario de Búsqueda

### esVacio()

**Lenguaje natural:** Retorna verdadero si el árbol no tiene nodos.

**Precondición:** ninguna.  
**Postcondición:** retorna `verdadero` si `raiz = nulo`.

```
TArbolBB.esVacio(): booleano
  retornar raiz = nulo
fin método
```

**Orden:** O(1)

---

### insertar(etiqueta, dato)

**Lenguaje natural:** Crea un nuevo elemento. Si el árbol está vacío, lo coloca como raíz. Si no, delega recursivamente al nodo raíz: menores van a la izquierda, mayores a la derecha, duplicados se rechazan.

**Precondición:** `etiqueta ≠ nulo`.  
**Postcondición:** el elemento queda insertado respetando la propiedad BST, o retorna `falso` si la etiqueta ya existía.

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

### buscar(etiqueta)

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

### eliminar(etiqueta)

**Lenguaje natural:** Localiza el nodo y lo elimina según tres casos: (1) sin hijos → se desvincula; (2) un hijo → el hijo lo reemplaza; (3) dos hijos → se reemplaza por el predecesor inorden (máximo del subárbol izquierdo) y se elimina ese predecesor.

**Precondición:** ninguna.  
**Postcondición:** si existía un nodo con esa etiqueta, es eliminado manteniendo la propiedad BST.

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
  // Caso 3: dos hijos → predecesor inorden
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

### obtenerTamaño()

**Lenguaje natural:** Cuenta todos los nodos recursivamente: 1 (nodo actual) + tamaño(izq) + tamaño(der).

**Precondición:** ninguna.  
**Postcondición:** retorna la cantidad total de nodos.

```
TArbolBB.obtenerTamaño(): entero
  si esVacio() entonces retornar 0
  retornar raiz.obtenerTamaño()
fin método

TElementoAB.obtenerTamaño(): entero
  tamaño ← 1
  si hijoIzq ≠ nulo entonces
    tamaño ← tamaño + hijoIzq.obtenerTamaño()
  fin si
  si hijoDer ≠ nulo entonces
    tamaño ← tamaño + hijoDer.obtenerTamaño()
  fin si
  retornar tamaño
fin método
```

**Orden:** O(n)

---

### obtenerAltura()

**Lenguaje natural:** La altura de un nodo es 1 + el máximo entre la altura de sus subárboles. Un nodo nulo tiene altura −1.

**Precondición:** ninguna.  
**Postcondición:** retorna −1 para árbol vacío; 0 para un único nodo.

```
TArbolBB.obtenerAltura(): entero
  si esVacio() entonces retornar -1
  retornar raiz.obtenerAltura()
fin método

TElementoAB.obtenerAltura(): entero
  alturaIzq ← -1
  alturaDer ← -1
  si hijoIzq ≠ nulo entonces
    alturaIzq ← hijoIzq.obtenerAltura()
  fin si
  si hijoDer ≠ nulo entonces
    alturaDer ← hijoDer.obtenerAltura()
  fin si
  retornar 1 + max(alturaIzq, alturaDer)
fin método
```

**Orden:** O(n)

---

### inOrden()

**Lenguaje natural:** Recorre izquierda→raíz→derecha. En un BST produce los valores en orden ascendente.

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

### preOrden()

**Lenguaje natural:** Recorre raíz→izquierda→derecha.

**Precondición:** ninguna.  
**Postcondición:** retorna una lista con los datos en orden raíz-izquierdo-derecho.

```
TElementoAB.preOrden(lista: Lista<T>): void
  lista.insertar(this.getDato())
  si hijoIzq ≠ nulo entonces hijoIzq.preOrden(lista)
  si hijoDer ≠ nulo entonces hijoDer.preOrden(lista)
fin método
```

**Orden:** O(n)

---

### postOrden()

**Lenguaje natural:** Recorre izquierda→derecha→raíz.

**Precondición:** ninguna.  
**Postcondición:** retorna una lista con los datos en orden izquierdo-derecho-raíz.

```
TElementoAB.postOrden(lista: Lista<T>): void
  si hijoIzq ≠ nulo entonces hijoIzq.postOrden(lista)
  si hijoDer ≠ nulo entonces hijoDer.postOrden(lista)
  lista.insertar(this.getDato())
fin método
```

**Orden:** O(n)

---

## AVL — Árbol Binario de Búsqueda Autobalanceado

### altura / actualizarAltura / factorBalance (auxiliares)

**Lenguaje natural:** `altura` retorna la altura guardada en el nodo (o −1 si nulo). `actualizarAltura` recalcula la altura del nodo a partir de sus hijos. `factorBalance` retorna la diferencia altura(der) − altura(izq).

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

### Rotación Simple Derecha — caso LL

**Lenguaje natural:** El nodo desbalanceado `k2` tiene su subárbol izquierdo `k1` más pesado. `k1` sube y pasa a ser la raíz del subárbol: el hijo derecho de `k1` se convierte en el hijo izquierdo de `k2`, y `k2` pasa a ser el hijo derecho de `k1`.

**Precondición:** `k2` tiene factor de balance −2 y su hijo izquierdo tiene factor ≤ 0 (caso LL).  
**Postcondición:** `k1` es la nueva raíz del subárbol con alturas actualizadas.

```
rotacionDerecha(k2: TElementoAVL): TElementoAVL
  k1         ← k2.hijoIzq
  k2.hijoIzq ← k1.hijoDer
  k1.hijoDer ← k2
  actualizarAltura(k2)
  actualizarAltura(k1)
  retornar k1
fin método
```

**Orden:** O(1)

---

### Rotación Simple Izquierda — caso RR

**Lenguaje natural:** Simétrico a la rotación derecha. `k2` sube y `k1` baja a la izquierda.

**Precondición:** `k1` tiene factor de balance +2 y su hijo derecho tiene factor ≥ 0 (caso RR).  
**Postcondición:** `k2` es la nueva raíz del subárbol con alturas actualizadas.

```
rotacionIzquierda(k1: TElementoAVL): TElementoAVL
  k2         ← k1.hijoDer
  k1.hijoDer ← k2.hijoIzq
  k2.hijoIzq ← k1
  actualizarAltura(k1)
  actualizarAltura(k2)
  retornar k2
fin método
```

**Orden:** O(1)

---

### Rotación Doble LR — caso LR

**Lenguaje natural:** El subárbol izquierdo de `k3` tiene su parte derecha más pesada. Se resuelve con rotación izquierda en el hijo izquierdo y luego rotación derecha en `k3`.

**Precondición:** `k3` tiene factor −2 y su hijo izquierdo tiene factor +1 (caso LR).  
**Postcondición:** el nodo intermedio es la nueva raíz del subárbol.

```
rotacionDobleIzquierdaDerecha(k3: TElementoAVL): TElementoAVL
  k3.hijoIzq ← rotacionIzquierda(k3.hijoIzq)
  retornar rotacionDerecha(k3)
fin método
```

**Orden:** O(1)

---

### Rotación Doble RL — caso RL

**Lenguaje natural:** Simétrico al LR. Rotación derecha en el hijo derecho, luego rotación izquierda en el nodo.

**Precondición:** `k1` tiene factor +2 y su hijo derecho tiene factor −1 (caso RL).  
**Postcondición:** el nodo intermedio es la nueva raíz del subárbol.

```
rotacionDobleDerechaIzquierda(k1: TElementoAVL): TElementoAVL
  k1.hijoDer ← rotacionDerecha(k1.hijoDer)
  retornar rotacionIzquierda(k1)
fin método
```

**Orden:** O(1)

---

### balancear(nodo)

**Lenguaje natural:** Actualiza la altura del nodo, calcula su factor de balance y aplica la rotación correspondiente (LL, LR, RR o RL) si está desbalanceado. Retorna la nueva raíz del subárbol.

**Precondición:** ninguna; se invoca al regresar de cada inserción/eliminación recursiva.  
**Postcondición:** retorna el nodo raíz del subárbol ya balanceado con altura actualizada.

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

### insertar(etiqueta, dato) — AVL

**Lenguaje natural:** Igual que en el BST, pero al regresar de cada llamada recursiva se invoca `balancear()`, corrigiendo cualquier desbalance introducido en el camino hacia la raíz.

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

**Orden:** O(log n) garantizado — la altura del AVL es siempre O(log n).

---

## Ejercicios de Parcial

### Filtrar películas por score mínimo

**Lenguaje natural:** Recorrer el BST en inorden. Para cada película, si su score es mayor al mínimo dado, agregarla a la lista. Al finalizar, ordenar la lista alfabéticamente por título y retornarla.

**Precondición:** `scoreMin ∈ [1.0, 10.0]`. El árbol está inicializado.  
**Postcondición:** retorna una lista de películas con `score > scoreMin`, ordenada alfabéticamente por título.

```
TArbolPeliculas.recomendarPorScore(scoreMin: real): Lista<Pelicula>
  recomendadas ← nueva Lista vacía
  si raiz ≠ nulo entonces
    raiz.filtrarPorScore(scoreMin, recomendadas)
  fin si
  recomendadas ← ordenarAlfabeticamente(recomendadas)
  retornar recomendadas
fin método

TElementoAB.filtrarPorScore(scoreMin: real, lista: Lista<Pelicula>): void
  si hijoIzq ≠ nulo entonces
    hijoIzq.filtrarPorScore(scoreMin, lista)
  fin si
  si this.getDatos().score > scoreMin entonces
    lista.insertar(this.getDatos())
  fin si
  si hijoDer ≠ nulo entonces
    hijoDer.filtrarPorScore(scoreMin, lista)
  fin si
fin método
```

**Orden:** O(n + m log m) — inorden O(n) + ordenamiento alfabético O(m log m), donde m = películas que pasan el filtro.

---

### Filtrar películas por género

**Lenguaje natural:** Recorrer el BST en inorden. Para cada película, si su género coincide con el buscado, agregarla a la lista. El inorden garantiza que el resultado queda en orden cronológico (año ascendente).

**Precondición:** `genero` es una cadena no vacía. El árbol está inicializado.  
**Postcondición:** retorna una lista de películas del género dado en orden cronológico.

```
TArbolPeliculas.buscarPorGenero(genero: cadena): Lista<Pelicula>
  resultado ← nueva Lista vacía
  si raiz ≠ nulo entonces
    raiz.filtrarPorGenero(genero, resultado)
  fin si
  retornar resultado
fin método

TElementoAB.filtrarPorGenero(genero: cadena, lista: Lista<Pelicula>): void
  si hijoIzq ≠ nulo entonces
    hijoIzq.filtrarPorGenero(genero, lista)
  fin si
  si this.getDatos().genero = genero entonces
    lista.insertar(this.getDatos())
  fin si
  si hijoDer ≠ nulo entonces
    hijoDer.filtrarPorGenero(genero, lista)
  fin si
fin método
```

**Orden:** O(n)

---

### Filtrar películas por rango de score

**Lenguaje natural:** Recorrer el BST en inorden. Para cada película, si su score está en el rango `[minScore, maxScore]`, agregarla a la lista. El resultado queda en orden cronológico.

**Precondición:** `minScore ≤ maxScore`. El árbol está inicializado.  
**Postcondición:** retorna una lista de películas con score en `[minScore, maxScore]` en orden cronológico.

```
TArbolPeliculas.buscarPorRangoScore(minScore: real, maxScore: real): Lista<Pelicula>
  resultado ← nueva Lista vacía
  si raiz ≠ nulo entonces
    raiz.filtrarPorRango(minScore, maxScore, resultado)
  fin si
  retornar resultado
fin método

TElementoAB.filtrarPorRango(min: real, max: real, lista: Lista<Pelicula>): void
  si hijoIzq ≠ nulo entonces
    hijoIzq.filtrarPorRango(min, max, lista)
  fin si
  pelicula ← this.getDatos()
  si pelicula.score ≥ min Y pelicula.score ≤ max entonces
    lista.insertar(pelicula)
  fin si
  si hijoDer ≠ nulo entonces
    hijoDer.filtrarPorRango(min, max, lista)
  fin si
fin método
```

**Orden:** O(n)

---

### Encontrar camino desde nodo hasta objetivo

**Lenguaje natural:** Si el nodo es nulo, retornar nulo. Si es el objetivo, retornar una lista con ese nodo. Buscar recursivamente en subárbol izquierdo; si se encuentra, agregar el nodo actual al frente y retornar. Repetir con subárbol derecho. Si no se encontró en ninguno, retornar nulo.

**Precondición:** `objetivo ≠ nulo`. `nodo` puede ser nulo.  
**Postcondición:** retorna la lista de nodos desde `nodo` hasta `objetivo` (raíz primero), o `nulo` si el objetivo no está en el subárbol.

```
encontrarCamino(nodo: IElementoAB, objetivo: IElementoAB): Lista<IElementoAB>
  si nodo = nulo entonces
    retornar nulo
  fin si
  si nodo.getDatos() = objetivo.getDatos() entonces
    camino ← nueva Lista
    camino.insertar(nodo)
    retornar camino
  fin si
  caminoIzq ← encontrarCamino(nodo.getHijoIzq(), objetivo)
  si caminoIzq ≠ nulo entonces
    caminoIzq.insertarAlFrente(nodo)
    retornar caminoIzq
  fin si
  caminoDer ← encontrarCamino(nodo.getHijoDer(), objetivo)
  si caminoDer ≠ nulo entonces
    caminoDer.insertarAlFrente(nodo)
    retornar caminoDer
  fin si
  retornar nulo
fin método
```

**Orden:** O(n)

---

### Calcular parentesco en árbol genealógico invertido

**Lenguaje natural:** Encontrar los caminos desde la raíz hasta cada persona. Recorrer ambos caminos para encontrar el último nodo compartido (LCA). Calcular el grado con `prof1 + prof2 − 2×profLCA`. Si el LCA es una de las personas, el parentesco es consanguíneo; si no, político.

**Precondición:** `raiz` es la raíz del árbol. `persona1 ≠ persona2`.  
**Postcondición:** retorna `(grado, tipo)`. Si alguna persona no existe en el árbol, retorna `(−1, "Sin parentesco")`.

```
calcularParentesco(raiz: IElementoAB,
                   persona1: IElementoAB,
                   persona2: IElementoAB): (entero, cadena)

  camino1 ← encontrarCamino(raiz, persona1)
  camino2 ← encontrarCamino(raiz, persona2)

  si camino1 = nulo o camino2 = nulo entonces
    retornar (-1, "Sin parentesco")
  fin si

  lcaNivel ← 0
  para i desde 0 hasta min(longitud(camino1), longitud(camino2)) - 1 hacer
    si camino1[i].getDatos() = camino2[i].getDatos() entonces
      lcaNivel ← i
    sino
      salir del bucle
    fin si
  fin para

  prof1 ← longitud(camino1) - 1
  prof2 ← longitud(camino2) - 1
  grado ← prof1 + prof2 - 2 × lcaNivel

  si lcaNivel = prof1 o lcaNivel = prof2 entonces
    tipo ← "consanguinidad"
  sino
    tipo ← "político"
  fin si

  retornar (grado, tipo)
fin método
```

**Orden:** O(n) — dos llamadas a `encontrarCamino` O(n) + comparación de caminos O(h).

---

### Verificar si un elemento está en la lista blanca

**Lenguaje natural:** Recorre la lista blanca comparando cada elemento con el dado. Retorna verdadero si lo encuentra.

**Precondición:** `listaBlanca` está inicializada.  
**Postcondición:** retorna `verdadero` si `elemento` está en la lista blanca, `falso` si no.

```
estaEnListaBlanca(elemento: id): booleano
  aux ← listaBlanca.getPrimero()
  mientras aux ≠ nulo hacer
    si aux.getDato() = elemento entonces
      retornar verdadero
    fin si
    aux ← aux.getSiguiente()
  fin mientras
  retornar falso
fin método
```

**Orden:** O(b) — b = tamaño de la lista blanca.

---

### Verificar si un par está en la lista negra

**Lenguaje natural:** Recorre la lista negra buscando el par `{contenedor, elemento}`. Retorna verdadero si lo encuentra.

**Precondición:** `listaNegra` está inicializada.  
**Postcondición:** retorna `verdadero` si el par `{contenedor, elemento}` está en la lista negra, `falso` si no.

```
estaEnListaNegra(contenedor: id, elemento: id): booleano
  aux ← listaNegra.getPrimero()
  mientras aux ≠ nulo hacer
    par ← aux.getDato()
    si par.contenedor = contenedor Y par.elemento = elemento entonces
      retornar verdadero
    fin si
    aux ← aux.getSiguiente()
  fin mientras
  retornar falso
fin método
```

**Orden:** O(n) — n = tamaño de la lista negra.

---

### Verificar si un preparado / combo es viable

**Lenguaje natural:** Para cada elemento: si está en la lista blanca, continúa; si el par está en la lista negra, retorna falso; si no aparece en ninguna lista, retorna falso. Si todos pasan, retorna verdadero.

**Precondición:** `listaBlanca` y `listaNegra` están inicializadas. `contenedor` es válido. `elementos` es una lista no vacía.  
**Postcondición:** retorna `verdadero` si todos los elementos son compatibles; `falso` si alguno no lo es.

```
preparadoViable(contenedor: id, elementos: Lista<id>): booleano
  aux ← elementos.getPrimero()
  mientras aux ≠ nulo hacer
    elemento ← aux.getDato()
    si estaEnListaBlanca(elemento) entonces
      // habilitado → continuar
    sino si estaEnListaNegra(contenedor, elemento) entonces
      retornar falso
    sino
      retornar falso
    fin si
    aux ← aux.getSiguiente()
  fin mientras
  retornar verdadero
fin método
```

**Orden:** O(m × (b + n)) — m = elementos del preparado; por cada uno se consulta lista blanca O(b) y en el peor caso lista negra O(n).

---

### Longitud de Trayectoria Interna Media — LTIM (dos recorridos)

**Lenguaje natural:** Calcula la suma de los niveles de todos los nodos (LTI) con un recorrido, y el tamaño con otro. Retorna LTI / n.

**Precondición:** ninguna.  
**Postcondición:** retorna LTIM = LTI / n. Si el árbol está vacío, retorna 0.

```
TArbolBB.longTrayIntMedia(): real
  si esVacio() entonces
    retornar 0
  fin si
  n   ← raiz.obtenerTamaño()
  lti ← raiz.calcularLTI(0)
  retornar lti / n
fin método

TElementoAB.calcularLTI(nivel: entero): entero
  suma ← nivel
  si hijoIzq ≠ nulo entonces
    suma ← suma + hijoIzq.calcularLTI(nivel + 1)
  fin si
  si hijoDer ≠ nulo entonces
    suma ← suma + hijoDer.calcularLTI(nivel + 1)
  fin si
  retornar suma
fin método
```

**Orden:** O(n)

---

### Longitud de Trayectoria Interna Media — LTIM (un solo recorrido)

**Lenguaje natural:** Calcula LTI y tamaño en el mismo recorrido, retornando ambos como par `(lti, tamaño)`. Más eficiente que la versión con dos recorridos.

**Precondición:** ninguna.  
**Postcondición:** retorna LTIM = LTI / n. Si el árbol está vacío, retorna 0.

```
TArbolBB.longTrayIntMedia(): real
  si esVacio() entonces
    retornar 0
  fin si
  (lti, n) ← raiz.calcularLTIyTamaño(0)
  retornar lti / n
fin método

TElementoAB.calcularLTIyTamaño(nivel: entero): (entero, entero)
  lti ← nivel
  tam ← 1
  si hijoIzq ≠ nulo entonces
    (ltiIzq, tamIzq) ← hijoIzq.calcularLTIyTamaño(nivel + 1)
    lti ← lti + ltiIzq
    tam ← tam + tamIzq
  fin si
  si hijoDer ≠ nulo entonces
    (ltiDer, tamDer) ← hijoDer.calcularLTIyTamaño(nivel + 1)
    lti ← lti + ltiDer
    tam ← tam + tamDer
  fin si
  retornar (lti, tam)
fin método
```

**Orden:** O(n) — un único recorrido del árbol.

---

### Separar nodos hoja e internos de un BST

**Lenguaje natural:** Recorrer el árbol en inorden. Para cada nodo: si no tiene hijos, insertar su dato en la lista de hojas; si tiene al menos uno, insertar en la lista de internos. El resultado respeta el orden ascendente de etiquetas.

**Precondición:** ninguna.  
**Postcondición:** retorna el par `(listaHojas, listaInternos)` en inorden. El árbol no es modificado.

```
TArbolBB.separarNodos(): (Lista hojas, Lista internos)
  hojas    ← nueva Lista vacía
  internos ← nueva Lista vacía
  si ¬esVacio() entonces
    raiz.separarNodos(hojas, internos)
  fin si
  retornar (hojas, internos)
fin método

TElementoAB.separarNodos(hojas: Lista, internos: Lista): void
  si hijoIzq ≠ nulo entonces
    hijoIzq.separarNodos(hojas, internos)
  fin si
  si hijoIzq = nulo Y hijoDer = nulo entonces
    hojas.insertar(this.getDato())
  sino
    internos.insertar(this.getDatos())
  fin si
  si hijoDer ≠ nulo entonces
    hijoDer.separarNodos(hojas, internos)
  fin si
fin método
```

**Orden:** O(n) si la lista tiene inserción O(1) al final; O(n²) con lista simple sin puntero al último.
