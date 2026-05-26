# Métodos Java — Parcial 2 (UT3)

---

## Árbol Genérico

### NodoGenerico.java

```java
public class NodoGenerico<T extends Comparable<T>> {

    private T dato;
    private List<NodoGenerico<T>> hijos;

    public NodoGenerico(T dato) {
        this.dato = dato;
        this.hijos = new ArrayList<>();
    }

    public T getDato() {
        return dato;
    }

    public List<T> getHijos() {
        List<T> resultado = new ArrayList<>();
        for (NodoGenerico<T> hijo : hijos) {
            resultado.add(hijo.dato);
        }
        return resultado;
    }

    public int grado() {
        return hijos.size();
    }

    public int altura() {
        if (hijos.isEmpty()) {
            return 0;
        }
        int max = 0;
        for (NodoGenerico<T> hijo : hijos) {
            int h = hijo.altura();
            if (h > max) {
                max = h;
            }
        }
        return max + 1;
    }

    public boolean agregarHijo(T padre, T hijo) {
        if (padre == null) {
            return false;
        }
        if (hijo == null) {
            return false;
        }
        if (this.dato.compareTo(padre) == 0) {
            for (NodoGenerico<T> h : hijos) {
                if (h.dato.compareTo(hijo) == 0) {
                    return false;
                }
            }
            hijos.add(new NodoGenerico<>(hijo));
            return true;
        }
        for (NodoGenerico<T> h : hijos) {
            if (h.agregarHijo(padre, hijo)) {
                return true;
            }
        }
        return false;
    }

    public NodoGenerico<T> buscar(Comparable<T> criterio) {
        if (criterio == null) {
            return null;
        }
        if (criterio.compareTo(this.dato) == 0) {
            return this;
        }
        for (NodoGenerico<T> hijo : hijos) {
            NodoGenerico<T> encontrado = hijo.buscar(criterio);
            if (encontrado != null) {
                return encontrado;
            }
        }
        return null;
    }

    public void eliminar(Comparable<T> criterio) {
        if (criterio == null) {
            return;
        }
        int i = 0;
        while (i < hijos.size()) {
            if (criterio.compareTo(hijos.get(i).dato) == 0) {
                hijos.remove(i);
                return;
            }
            i++;
        }
        for (NodoGenerico<T> hijo : hijos) {
            hijo.eliminar(criterio);
        }
    }

    public NodoGenerico<T> obtenerPadre(Comparable<T> criterio) {
        if (criterio == null) {
            return null;
        }
        for (NodoGenerico<T> hijo : hijos) {
            if (criterio.compareTo(hijo.dato) == 0) {
                return this;
            }
        }
        for (NodoGenerico<T> hijo : hijos) {
            NodoGenerico<T> resultado = hijo.obtenerPadre(criterio);
            if (resultado != null) {
                return resultado;
            }
        }
        return null;
    }

    public void preOrden(List<T> resultado) {
        resultado.add(this.dato);
        for (NodoGenerico<T> hijo : hijos) {
            hijo.preOrden(resultado);
        }
    }

    public void postOrden(List<T> resultado) {
        for (NodoGenerico<T> hijo : hijos) {
            hijo.postOrden(resultado);
        }
        resultado.add(this.dato);
    }
}
```

### ArbolGenerico.java

```java
public class ArbolGenerico<T extends Comparable<T>> {

    private NodoGenerico<T> raiz;

    public ArbolGenerico(T raiz) {
        this.raiz = new NodoGenerico<>(raiz);
    }

    public boolean agregarHijo(T padre, T hijo) {
        if (raiz == null) {
            return false;
        }
        if (padre == null) {
            return false;
        }
        if (hijo == null) {
            return false;
        }
        NodoGenerico<T> nodoPadre = raiz.buscar(padre);
        if (nodoPadre == null) {
            return false;
        }
        return nodoPadre.agregarHijo(padre, hijo);
    }

    public void eliminar(T criterio) {
        if (raiz == null) {
            return;
        }
        if (criterio == null) {
            return;
        }
        if (raiz.getDato().compareTo(criterio) == 0) {
            raiz = null;
            return;
        }
        raiz.eliminar(criterio);
    }

    public T obtenerPadre(T criterio) {
        if (raiz == null) {
            return null;
        }
        if (criterio == null) {
            return null;
        }
        if (raiz.getDato().compareTo(criterio) == 0) {
            return null;
        }
        NodoGenerico<T> nodo = raiz.obtenerPadre(criterio);
        if (nodo == null) {
            return null;
        }
        return nodo.getDato();
    }

    public T buscar(T criterio) {
        if (raiz == null) {
            return null;
        }
        if (criterio == null) {
            return null;
        }
        NodoGenerico<T> nodo = raiz.buscar(criterio);
        if (nodo == null) {
            return null;
        }
        return nodo.getDato();
    }

    public List<T> preOrden() {
        List<T> resultado = new ArrayList<>();
        if (raiz != null) {
            raiz.preOrden(resultado);
        }
        return resultado;
    }

    public List<T> postOrden() {
        List<T> resultado = new ArrayList<>();
        if (raiz != null) {
            raiz.postOrden(resultado);
        }
        return resultado;
    }

    public int altura() {
        if (raiz == null) {
            return -1;
        }
        return raiz.altura();
    }

    public int grado(T criterio) {
        if (raiz == null) {
            return 0;
        }
        NodoGenerico<T> nodo = raiz.buscar(criterio);
        if (nodo == null) {
            return 0;
        }
        return nodo.grado();
    }

    public void vaciar() {
        raiz = null;
    }
}
```

---

## Trie

### NodoTrie.java

```java
public class NodoTrie<T> {

    private Map<Character, NodoTrie<T>> hijos;
    private boolean esPalabra;
    private T dato;

    public NodoTrie() {
        this.hijos = new HashMap<>();
        this.esPalabra = false;
        this.dato = null;
    }

    public boolean insertar(String palabra, T dato) {
        if (palabra == null) {
            return false;
        }
        NodoTrie<T> nodo = this;
        for (int i = 0; i < palabra.length(); i++) {
            char c = palabra.charAt(i);
            if (!nodo.hijos.containsKey(c)) {
                nodo.hijos.put(c, new NodoTrie<>());
            }
            nodo = nodo.hijos.get(c);
        }
        boolean esPalabraNueva = !nodo.esPalabra;
        nodo.esPalabra = true;
        nodo.dato = dato;
        return esPalabraNueva;
    }

    public boolean buscar(String palabra) {
        if (palabra == null) {
            return false;
        }
        NodoTrie<T> nodo = this;
        for (int i = 0; i < palabra.length(); i++) {
            char c = palabra.charAt(i);
            if (!nodo.hijos.containsKey(c)) {
                return false;
            }
            nodo = nodo.hijos.get(c);
        }
        return nodo.esPalabra;
    }

    public boolean esSoloPrefijo(String prefijo) {
        NodoTrie<T> nodo = this;
        for (int i = 0; i < prefijo.length(); i++) {
            char c = prefijo.charAt(i);
            if (!nodo.hijos.containsKey(c)) {
                return false;
            }
            nodo = nodo.hijos.get(c);
        }
        return true;
    }

    public List<String> predecir(String prefijo) {
        List<String> resultado = new ArrayList<>();
        if (prefijo == null) {
            return resultado;
        }
        NodoTrie<T> nodo = this;
        for (int i = 0; i < prefijo.length(); i++) {
            char c = prefijo.charAt(i);
            if (!nodo.hijos.containsKey(c)) {
                return resultado;
            }
            nodo = nodo.hijos.get(c);
        }
        recolectarPalabras(nodo, prefijo, resultado);
        return resultado;
    }

    private void recolectarPalabras(NodoTrie<T> nodo, String palabraActual, List<String> resultado) {
        if (nodo.esPalabra) {
            resultado.add(palabraActual);
        }
        for (Map.Entry<Character, NodoTrie<T>> entry : nodo.hijos.entrySet()) {
            char c = entry.getKey();
            NodoTrie<T> hijo = entry.getValue();
            recolectarPalabras(hijo, palabraActual + c, resultado);
        }
    }

    public boolean eliminar(String palabra) {
        if (palabra == null) {
            return false;
        }
        NodoTrie<T> nodo = this;
        for (int i = 0; i < palabra.length(); i++) {
            char c = palabra.charAt(i);
            if (!nodo.hijos.containsKey(c)) {
                return false;
            }
            nodo = nodo.hijos.get(c);
        }
        if (!nodo.esPalabra) {
            return false;
        }
        nodo.esPalabra = false;
        nodo.dato = null;
        return true;
    }

    public boolean esPalabra() {
        return esPalabra;
    }

    public T getDato() {
        return dato;
    }
}
```

---

## Hash (Open Addressing — Sondeo Lineal)

### TNodoHash.java

```java
public class TNodoHash<K, V> {
    private K clave;
    private V valor;
    private boolean loteLibre;  // tombstone: fue eliminado, pero no null

    public TNodoHash(K clave, V valor) {
        this.clave = clave;
        this.valor = valor;
        this.loteLibre = false;
    }

    public K getClave() { return clave; }
    public V getValor() { return valor; }
    public boolean isLoteLibre() { return loteLibre; }
    public void setLoteLibre(boolean loteLibre) { this.loteLibre = loteLibre; }
}
```

### Hash.java

```java
public class Hash<K, V> {

    private TNodoHash<K, V>[] tabla;
    private int cantidadElementos;

    @SuppressWarnings("unchecked")
    public Hash(int elementosEsperados) {
        int capacidad = siguientePrimo(elementosEsperados * 2);
        this.tabla = new TNodoHash[capacidad];
        this.cantidadElementos = 0;
    }

    private int funcionHash(K clave) {
        int h = clave.hashCode() % tabla.length;
        if (h < 0) {
            h += tabla.length;
        }
        return h;
    }

    public boolean insertar(K clave, V valor) {
        if (clave == null) {
            return false;
        }
        double factorCarga = (double) cantidadElementos / tabla.length;
        if (factorCarga > 0.70) {
            redimensionar();
        }
        int h0 = funcionHash(clave);
        int primerLibre = -1;
        for (int i = 0; i < tabla.length; i++) {
            int pos = (h0 + i) % tabla.length;
            if (tabla[pos] == null) {
                if (primerLibre == -1) {
                    primerLibre = pos;
                }
                break;
            }
            if (tabla[pos].isLoteLibre()) {
                if (primerLibre == -1) {
                    primerLibre = pos;
                }
            } else if (tabla[pos].getClave().equals(clave)) {
                return false;
            }
        }
        if (primerLibre == -1) {
            return false;
        }
        tabla[primerLibre] = new TNodoHash<>(clave, valor);
        cantidadElementos++;
        return true;
    }

    public V buscar(K clave) {
        if (clave == null) {
            return null;
        }
        int h0 = funcionHash(clave);
        for (int i = 0; i < tabla.length; i++) {
            int pos = (h0 + i) % tabla.length;
            if (tabla[pos] == null) {
                return null;
            }
            if (!tabla[pos].isLoteLibre()) {
                if (tabla[pos].getClave().equals(clave)) {
                    return tabla[pos].getValor();
                }
            }
        }
        return null;
    }

    public boolean eliminar(K clave) {
        if (clave == null) {
            return false;
        }
        int h0 = funcionHash(clave);
        for (int i = 0; i < tabla.length; i++) {
            int pos = (h0 + i) % tabla.length;
            if (tabla[pos] == null) {
                return false;
            }
            if (!tabla[pos].isLoteLibre()) {
                if (tabla[pos].getClave().equals(clave)) {
                    tabla[pos].setLoteLibre(true);
                    cantidadElementos--;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean esVacio() {
        return cantidadElementos == 0;
    }

    @SuppressWarnings("unchecked")
    private void redimensionar() {
        TNodoHash<K, V>[] tablaVieja = tabla;
        tabla = new TNodoHash[siguientePrimo(tablaVieja.length * 2)];
        cantidadElementos = 0;
        for (int i = 0; i < tablaVieja.length; i++) {
            if (tablaVieja[i] != null) {
                if (!tablaVieja[i].isLoteLibre()) {
                    insertar(tablaVieja[i].getClave(), tablaVieja[i].getValor());
                }
            }
        }
    }

    private boolean esPrimo(int n) {
        if (n < 2) {
            return false;
        }
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    private int siguientePrimo(int n) {
        int candidato = n;
        while (!esPrimo(candidato)) {
            candidato++;
        }
        return candidato;
    }
}
```

---

## hashCode / equals — Patrones

### Un solo atributo identifica al objeto

```java
public class Alumno {
    private int id;
    private String nombre;
    private String email;

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof Alumno)) {
            return false;
        }
        Alumno otro = (Alumno) o;
        return this.id == otro.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
```

### Varios atributos identifican al objeto

```java
@Override
public int hashCode() {
    return Objects.hash(cedula, codigoCurso);
}

@Override
public boolean equals(Object o) {
    if (o == null) {
        return false;
    }
    if (!(o instanceof Persona)) {
        return false;
    }
    Persona p = (Persona) o;
    boolean mismaCedula = this.cedula.equals(p.cedula);
    boolean mismoCurso = this.codigoCurso.equals(p.codigoCurso);
    if (mismaCedula) {
        if (mismoCurso) {
            return true;
        }
    }
    return false;
}
```

**Regla del contrato:** si `a.equals(b)` entonces `a.hashCode() == b.hashCode()`.

**Advertencia:** no usar atributos cambiantes (email, dirección) en `hashCode()`/`equals()`. Si la clave cambia mientras el objeto está en un HashMap, el objeto se "pierde" en la tabla.

---

## Collections Framework — Referencia rápida

### Pila (ArrayDeque como Stack)

```java
Deque<String> pila = new ArrayDeque<>();
pila.push("A");          // inserta al frente
String tope = pila.pop();  // extrae del frente
String ver = pila.peek();  // mira sin extraer
```

### Cola (ArrayDeque como Queue)

```java
Queue<String> cola = new ArrayDeque<>();
cola.offer("A");           // encola
String frente = cola.poll(); // desencola
String ver = cola.peek();    // mira sin extraer
```

### Ordenar una lista

```java
List<Integer> lista = new ArrayList<>();
Collections.sort(lista);                // orden natural
Collections.sort(lista, comparador);    // con comparador
```

### Comparador manual (sin lambda)

```java
Comparator<Alumno> porNombre = new Comparator<Alumno>() {
    public int compare(Alumno a, Alumno b) {
        return a.getNombre().compareTo(b.getNombre());
    }
};
Collections.sort(lista, porNombre);
```

### Tabla comparativa de Collections

| Collection | Ordering | Random Access | Key-Value | Duplicados |
|------------|:--------:|:-------------:|:---------:|:----------:|
| ArrayList | ✅ | ✅ | ❌ | ✅ |
| LinkedList | ✅ | ❌ | ❌ | ✅ |
| HashSet | ❌ | ❌ | ❌ | ❌ |
| TreeSet | ✅ | ❌ | ❌ | ❌ |
| HashMap | ❌ | ✅ | ✅ | ❌ |
| TreeMap | ✅ | ✅ | ✅ | ❌ |

---

## Grafos Dirigidos (UT4)

Los algoritmos se implementan sobre una interfaz de grafo. Los tipos genéricos `V` (vértice) y `D` (dato de arista) varían según el enunciado. Para el parcial se asume que el grafo tiene un método `vertices()` que retorna todos los vértices y un método `adyacencias(v)` que retorna los arcos salientes de v.

---

### Dijkstra

```java
public HashMap<V, Double> dijkstra(V origen, IGraph<V, D> grafo) {
    HashMap<V, Double> distancias = new HashMap<>();
    HashMap<V, V> predecesores = new HashMap<>();
    HashSet<V> visitados = new HashSet<>();

    for (V v : grafo.vertices()) {
        distancias.put(v, Double.MAX_VALUE);
        predecesores.put(v, null);
    }
    distancias.put(origen, 0.0);

    while (visitados.size() < grafo.vertices().size()) {
        V minVertice = null;
        double minDist = Double.MAX_VALUE;
        for (V v : distancias.keySet()) {
            if (!visitados.contains(v)) {
                if (distancias.get(v) < minDist) {
                    minDist = distancias.get(v);
                    minVertice = v;
                }
            }
        }
        if (minVertice == null) {
            break;
        }
        visitados.add(minVertice);

        for (Edge<V, D> arista : grafo.adyacencias(minVertice)) {
            V vecino = arista.target();
            if (!visitados.contains(vecino)) {
                double peso = arista.weight();
                double nuevaDist = distancias.get(minVertice) + peso;
                if (nuevaDist < distancias.get(vecino)) {
                    distancias.put(vecino, nuevaDist);
                    predecesores.put(vecino, minVertice);
                }
            }
        }
    }
    return distancias;
}

public List<V> recuperarCamino(V origen, V destino, HashMap<V, V> predecesores) {
    List<V> camino = new ArrayList<>();
    V actual = destino;
    while (actual != null) {
        camino.add(0, actual);
        if (actual.equals(origen)) {
            break;
        }
        actual = predecesores.get(actual);
    }
    return camino;
}
```

---

### Floyd

```java
public double[][] floyd(double[][] C, int n, int[][] predecesores) {
    double[][] A = new double[n][n];

    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            A[i][j] = C[i][j];
            predecesores[i][j] = -1;
        }
        A[i][i] = 0;
    }

    for (int k = 0; k < n; k++) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (A[i][k] != Double.MAX_VALUE) {
                    if (A[k][j] != Double.MAX_VALUE) {
                        double nuevoDist = A[i][k] + A[k][j];
                        if (nuevoDist < A[i][j]) {
                            A[i][j] = nuevoDist;
                            predecesores[i][j] = k;
                        }
                    }
                }
            }
        }
    }
    return A;
}

public void imprimirCamino(int i, int j, int[][] P) {
    int k = P[i][j];
    if (k == -1) {
        return;
    }
    imprimirCamino(i, k, P);
    System.out.print(k + " ");
    imprimirCamino(k, j, P);
}
```

---

### Warshall

```java
public boolean[][] warshall(boolean[][] C, int n) {
    boolean[][] A = new boolean[n][n];

    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            A[i][j] = C[i][j];
        }
    }

    for (int k = 0; k < n; k++) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (!A[i][j]) {
                    if (A[i][k]) {
                        if (A[k][j]) {
                            A[i][j] = true;
                        }
                    }
                }
            }
        }
    }
    return A;
}
```

---

### DFS (búsqueda en profundidad)

```java
public void dfs(IGraph<V, D> grafo, Consumer<V> consumer) {
    HashSet<V> visitados = new HashSet<>();
    for (V v : grafo.vertices()) {
        if (!visitados.contains(v)) {
            dfsAux(grafo, v, visitados, consumer);
        }
    }
}

private void dfsAux(IGraph<V, D> grafo, V actual, HashSet<V> visitados, Consumer<V> consumer) {
    visitados.add(actual);
    consumer.accept(actual);
    for (Edge<V, D> arista : grafo.adyacencias(actual)) {
        V vecino = arista.target();
        if (!visitados.contains(vecino)) {
            dfsAux(grafo, vecino, visitados, consumer);
        }
    }
}
```

---

### Clasificación topológica

```java
public List<V> clasificacionTopologica(IGraph<V, D> grafo) {
    HashSet<V> visitados = new HashSet<>();
    List<V> lista = new ArrayList<>();
    for (V v : grafo.vertices()) {
        clasificacionTopologicaAux(grafo, v, visitados, lista);
    }
    return lista;
}

private void clasificacionTopologicaAux(IGraph<V, D> grafo, V nodo, HashSet<V> visitados, List<V> lista) {
    if (!visitados.contains(nodo)) {
        visitados.add(nodo);
        for (Edge<V, D> arista : grafo.adyacencias(nodo)) {
            V vecino = arista.target();
            clasificacionTopologicaAux(grafo, vecino, visitados, lista);
        }
        lista.add(0, nodo);
    }
}
```

---

### Detección de ciclos

```java
public boolean tieneCiclos(IGraph<V, D> grafo) {
    HashSet<V> enRecursion = new HashSet<>();
    for (V v : grafo.vertices()) {
        if (tieneCiclosAux(grafo, v, enRecursion)) {
            return true;
        }
    }
    return false;
}

private boolean tieneCiclosAux(IGraph<V, D> grafo, V v, HashSet<V> enRecursion) {
    enRecursion.add(v);
    for (Edge<V, D> arista : grafo.adyacencias(v)) {
        V vecino = arista.target();
        if (enRecursion.contains(vecino)) {
            return true;
        }
        if (tieneCiclosAux(grafo, vecino, enRecursion)) {
            return true;
        }
    }
    enRecursion.remove(v);
    return false;
}
```

---

### Todos los caminos posibles

```java
public List<List<V>> todosLosCaminos(IGraph<V, D> grafo, V origen, V destino) {
    HashSet<V> visitados = new HashSet<>();
    List<V> camino = new ArrayList<>();
    List<List<V>> resultado = new ArrayList<>();
    todosLosCaminosAux(grafo, origen, destino, visitados, camino, resultado);
    return resultado;
}

private void todosLosCaminosAux(IGraph<V, D> grafo, V actual, V destino,
        HashSet<V> visitados, List<V> camino, List<List<V>> resultado) {
    visitados.add(actual);
    camino.add(actual);
    if (actual.equals(destino)) {
        resultado.add(new ArrayList<>(camino));
    } else {
        for (Edge<V, D> arista : grafo.adyacencias(actual)) {
            V vecino = arista.target();
            if (!visitados.contains(vecino)) {
                todosLosCaminosAux(grafo, vecino, destino, visitados, camino, resultado);
            }
        }
    }
    camino.remove(camino.size() - 1);
    visitados.remove(actual);
}
```

---

### Excentricidad y centro del grafo

```java
public int centroDelGrafo(double[][] A, int n) {
    double[] excentricidades = new double[n];
    for (int v = 0; v < n; v++) {
        double max = 0;
        for (int u = 0; u < n; u++) {
            if (A[u][v] > max) {
                max = A[u][v];
            }
        }
        excentricidades[v] = max;
    }
    int centro = 0;
    for (int v = 1; v < n; v++) {
        if (excentricidades[v] < excentricidades[centro]) {
            centro = v;
        }
    }
    return centro;
}
```

---

### BEA (búsqueda en amplitud)

```java
public void bea(IGraph<V, D> grafo, V origen, Consumer<V> consumer) {
    HashSet<V> visitados = new HashSet<>();
    ArrayDeque<V> cola = new ArrayDeque<>();
    visitados.add(origen);
    cola.offer(origen);

    while (!cola.isEmpty()) {
        V actual = cola.poll();
        consumer.accept(actual);
        for (Edge<V, D> arista : grafo.adyacencias(actual)) {
            V vecino = arista.target();
            if (!visitados.contains(vecino)) {
                visitados.add(vecino);
                cola.offer(vecino);
            }
        }
    }
}
```

**Número de Bacon / distancia en saltos:**

```java
public int numeroDeDistancia(IGraph<V, D> grafo, V origen, V destino) {
    HashSet<V> visitados = new HashSet<>();
    ArrayDeque<V> cola = new ArrayDeque<>();
    HashMap<V, Integer> distancia = new HashMap<>();
    visitados.add(origen);
    cola.offer(origen);
    distancia.put(origen, 0);

    while (!cola.isEmpty()) {
        V actual = cola.poll();
        if (actual.equals(destino)) {
            return distancia.get(actual);
        }
        for (Edge<V, D> arista : grafo.adyacencias(actual)) {
            V vecino = arista.target();
            if (!visitados.contains(vecino)) {
                visitados.add(vecino);
                distancia.put(vecino, distancia.get(actual) + 1);
                cola.offer(vecino);
            }
        }
    }
    return -1;
}
```
