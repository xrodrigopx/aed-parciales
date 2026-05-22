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
