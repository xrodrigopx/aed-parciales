---
title: "Implementaciones Java de TDAs"
type: reference
tags: [java, tda, implementacion, lista, cola, pila, bst, arbol]
created: 2026-04-28
updated: 2026-04-28
---

# Implementaciones Java de TDAs

Código Java completo de cada estructura. Para pseudocódigos ver `pseudocodigos/`.

> **Restricción del parcial:** solo usar estas clases. Prohibido `ArrayList`, `LinkedList`, `java.util.Stack`, ni ninguna clase de `java.util.Collections`.

---

## Índice

- [Nodo\<T\>](#nodot)
- [Lista\<T\>](#listat)
- [ListaEnlazada\<T\>](#listaenlazadat)
- [ListaArray\<T\>](#listaarrayt)
- [Cola\<T\>](#colat)
- [Pila\<T\>](#pilat)
- [TElementoAB\<T\>](#telementoabt)
- [TArbolBB\<T\>](#tarbollbbt)
- [TElementoAVL\<T\>](#telementoavlt)
- [TArbolAVL\<T\>](#tarbollавlt)
- [ManejadorArchivosGenerico](#manejadorarchivosgenerico)

---

## Nodo\<T\>

Nodo de la lista enlazada (farmachop). Clave `Comparable` + dato genérico + puntero al siguiente.

```java
public class Nodo<T> implements INodo<T> {

    private final Comparable etiqueta;
    private T dato;
    private Nodo<T> siguiente = null;

    public Nodo(Comparable etiqueta, T dato) {
        this.etiqueta = etiqueta;
        this.dato = dato;
    }

    public T getDato()                  { return dato; }
    public void setDato(T dato)         { this.dato = dato; }
    public Comparable getEtiqueta()     { return etiqueta; }
    public Nodo<T> getSiguiente()       { return siguiente; }
    public void setSiguiente(Nodo<T> n) { this.siguiente = n; }
    public void imprimir()              { System.out.println(dato.toString()); }
    public void imprimirEtiqueta()      { System.out.println(etiqueta); }
    public Nodo<T> clonar()             { return new Nodo<>(etiqueta, dato); }
    public boolean equals(Nodo otro)    { return dato.equals(otro.getDato()); }

    @Override
    public int compareTo(Comparable etiqueta) {
        return this.etiqueta.compareTo(etiqueta);
    }
}
```

---

## Lista\<T\>

Lista simplemente enlazada con puntero `primero` (farmachop). Sin puntero al último — insertar al final es O(n).

```java
public class Lista<T> implements ILista<T> {

    private Nodo<T> primero;

    public Lista() { primero = null; }

    @Override
    public void insertar(Nodo<T> unNodo) {
        if (esVacia()) {
            primero = unNodo;
        } else {
            Nodo<T> aux = primero;
            while (aux.getSiguiente() != null)
                aux = aux.getSiguiente();
            aux.setSiguiente(unNodo);
        }
    }

    @Override
    public void insertar(Comparable etiqueta, T dato) {
        insertar(new Nodo<>(etiqueta, dato));
    }

    @Override
    public Nodo<T> buscar(Comparable clave) {
        Nodo<T> aux = primero;
        while (aux != null) {
            if (aux.getEtiqueta().equals(clave)) return aux;
            aux = aux.getSiguiente();
        }
        return null;
    }

    @Override
    public boolean eliminar(Comparable clave) {
        if (esVacia()) return false;
        if (primero.getEtiqueta().compareTo(clave) == 0) {
            primero = primero.getSiguiente();
            return true;
        }
        Nodo<T> aux = primero;
        while (aux.getSiguiente() != null) {
            if (aux.getSiguiente().getEtiqueta().equals(clave)) {
                aux.setSiguiente(aux.getSiguiente().getSiguiente());
                return true;
            }
            aux = aux.getSiguiente();
        }
        return false;
    }

    @Override
    public int cantElementos() {
        int c = 0;
        Nodo<T> aux = primero;
        while (aux != null) { c++; aux = aux.getSiguiente(); }
        return c;
    }

    @Override
    public boolean esVacia() { return primero == null; }

    public Nodo<T> getPrimero()          { return primero; }

    @Override
    public void setPrimero(Nodo<T> n)    { primero = n; }

    @Override
    public String imprimir() {
        Nodo<T> aux = primero;
        while (aux != null) { aux.imprimirEtiqueta(); aux = aux.getSiguiente(); }
        return "";
    }

    @Override
    public String imprimir(String sep) {
        if (esVacia()) return "";
        Nodo<T> aux = primero;
        StringBuilder sb = new StringBuilder(aux.getEtiqueta().toString());
        while (aux.getSiguiente() != null) {
            aux = aux.getSiguiente();
            sb.append(sep).append(aux.getEtiqueta());
        }
        return sb.toString();
    }
}
```

---

## ListaEnlazada\<T\>

Lista simplemente enlazada (festival-otaku). Implementa `TDALista<T>` — acceso por índice O(n), sin restricción de duplicados.

```java
public class ListaEnlazada<T> implements TDALista<T> {

    private static class Nodo<T> {
        T dato;
        Nodo<T> siguiente;
        Nodo(T dato) { this.dato = dato; }
    }

    private Nodo<T> cabeza;
    private int tamanio;

    public ListaEnlazada() { cabeza = null; tamanio = 0; }

    @Override
    public void agregar(T elem) {
        Nodo<T> nuevo = new Nodo<>(elem);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            Nodo<T> aux = cabeza;
            while (aux.siguiente != null) aux = aux.siguiente;
            aux.siguiente = nuevo;
        }
        tamanio++;
    }

    @Override
    public void agregar(int index, T elem) {
        if (index < 0 || index > tamanio) throw new IndexOutOfBoundsException();
        Nodo<T> nuevo = new Nodo<>(elem);
        if (index == 0) {
            nuevo.siguiente = cabeza;
            cabeza = nuevo;
        } else {
            Nodo<T> aux = nodoEn(index - 1);
            nuevo.siguiente = aux.siguiente;
            aux.siguiente = nuevo;
        }
        tamanio++;
    }

    @Override
    public T obtener(int index) {
        if (index < 0 || index >= tamanio) throw new IndexOutOfBoundsException();
        return nodoEn(index).dato;
    }

    @Override
    public T remover(int index) {
        if (index < 0 || index >= tamanio) throw new IndexOutOfBoundsException();
        T dato;
        if (index == 0) {
            dato = cabeza.dato;
            cabeza = cabeza.siguiente;
        } else {
            Nodo<T> anterior = nodoEn(index - 1);
            dato = anterior.siguiente.dato;
            anterior.siguiente = anterior.siguiente.siguiente;
        }
        tamanio--;
        return dato;
    }

    @Override
    public boolean remover(T elem) {
        int idx = indiceDe(elem);
        if (idx == -1) return false;
        remover(idx);
        return true;
    }

    @Override
    public boolean contiene(T elem) { return indiceDe(elem) != -1; }

    @Override
    public int indiceDe(T elem) {
        Nodo<T> aux = cabeza;
        int i = 0;
        while (aux != null) {
            if (aux.dato.equals(elem)) return i;
            aux = aux.siguiente;
            i++;
        }
        return -1;
    }

    @Override
    public T buscar(Predicate<T> criterio) {
        Nodo<T> aux = cabeza;
        while (aux != null) {
            if (criterio.test(aux.dato)) return aux.dato;
            aux = aux.siguiente;
        }
        return null;
    }

    @Override
    public TDALista<T> ordenar(Comparator<T> comparator) {
        ListaEnlazada<T> copia = new ListaEnlazada<>();
        Nodo<T> aux = cabeza;
        while (aux != null) { copia.agregar(aux.dato); aux = aux.siguiente; }
        // bubble sort sobre los datos
        boolean cambio = true;
        while (cambio) {
            cambio = false;
            Nodo<T> cur = copia.cabeza;
            while (cur != null && cur.siguiente != null) {
                if (comparator.compare(cur.dato, cur.siguiente.dato) > 0) {
                    T tmp = cur.dato; cur.dato = cur.siguiente.dato; cur.siguiente.dato = tmp;
                    cambio = true;
                }
                cur = cur.siguiente;
            }
        }
        return copia;
    }

    @Override
    public int tamaño()       { return tamanio; }

    @Override
    public boolean esVacio()  { return tamanio == 0; }

    @Override
    public void vaciar()      { cabeza = null; tamanio = 0; }

    private Nodo<T> nodoEn(int index) {
        Nodo<T> aux = cabeza;
        for (int i = 0; i < index; i++) aux = aux.siguiente;
        return aux;
    }
}
```

---

## ListaArray\<T\>

Lista basada en arreglo (festival-otaku). `agregar(T)` bloquea duplicados. `obtener(i)` y `tamaño()` son O(1).

```java
public class ListaArray<T> implements TDALista<T> {

    private static final int MAX = 100;
    private Object[] datos;
    private int tamanio;

    public ListaArray() { datos = new Object[MAX]; tamanio = 0; }

    @Override
    public void agregar(T elem) {
        if (contiene(elem)) return;          // no duplicar
        if (tamanio == MAX) throw new RuntimeException("Lista llena");
        datos[tamanio++] = elem;
    }

    @Override
    public void agregar(int index, T elem) {
        if (index < 0 || index > tamanio || tamanio == MAX)
            throw new IndexOutOfBoundsException();
        for (int i = tamanio; i > index; i--)
            datos[i] = datos[i - 1];
        datos[index] = elem;
        tamanio++;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T obtener(int index) {
        if (index < 0 || index >= tamanio) throw new IndexOutOfBoundsException();
        return (T) datos[index];
    }

    @Override
    @SuppressWarnings("unchecked")
    public T remover(int index) {
        if (index < 0 || index >= tamanio) throw new IndexOutOfBoundsException();
        T elem = (T) datos[index];
        for (int i = index; i < tamanio - 1; i++)
            datos[i] = datos[i + 1];
        tamanio--;
        return elem;
    }

    @Override
    public boolean remover(T elem) {
        int idx = indiceDe(elem);
        if (idx == -1) return false;
        remover(idx);
        return true;
    }

    @Override
    public boolean contiene(T elem) { return indiceDe(elem) != -1; }

    @Override
    @SuppressWarnings("unchecked")
    public int indiceDe(T elem) {
        for (int i = 0; i < tamanio; i++)
            if (((T) datos[i]).equals(elem)) return i;
        return -1;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T buscar(Predicate<T> criterio) {
        for (int i = 0; i < tamanio; i++)
            if (criterio.test((T) datos[i])) return (T) datos[i];
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public TDALista<T> ordenar(Comparator<T> comparator) {
        ListaArray<T> copia = new ListaArray<>();
        for (int i = 0; i < tamanio; i++) copia.datos[i] = datos[i];
        copia.tamanio = tamanio;
        // insertion sort
        for (int i = 1; i < copia.tamanio; i++) {
            T key = (T) copia.datos[i];
            int j = i - 1;
            while (j >= 0 && comparator.compare((T) copia.datos[j], key) > 0) {
                copia.datos[j + 1] = copia.datos[j];
                j--;
            }
            copia.datos[j + 1] = key;
        }
        return copia;
    }

    @Override
    public int tamaño()      { return tamanio; }

    @Override
    public boolean esVacio() { return tamanio == 0; }

    @Override
    public void vaciar()     { tamanio = 0; }
}
```

---

## Cola\<T\>

FIFO (festival-otaku). Lista enlazada interna con punteros `frente` y `posterior` — `poneEnCola` y `quitaDeCola` son O(1).

```java
public class Cola<T> implements TDACola<T> {

    private static class Nodo<T> {
        T dato;
        Nodo<T> siguiente;
        Nodo(T dato) { this.dato = dato; }
    }

    private Nodo<T> frente;
    private Nodo<T> posterior;
    private int tamanio;

    public Cola() { frente = null; posterior = null; tamanio = 0; }

    // --- operaciones FIFO ---

    @Override
    public boolean poneEnCola(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        if (esVacio()) {
            frente = nuevo;
            posterior = nuevo;
        } else {
            posterior.siguiente = nuevo;
            posterior = nuevo;
        }
        tamanio++;
        return true;
    }

    @Override
    public T quitaDeCola() {
        if (esVacio()) throw new java.util.NoSuchElementException("Cola vacía");
        T dato = frente.dato;
        frente = frente.siguiente;
        if (frente == null) posterior = null;
        tamanio--;
        return dato;
    }

    @Override
    public T frente() {
        if (esVacio()) throw new java.util.NoSuchElementException("Cola vacía");
        return frente.dato;
    }

    // --- operaciones TDALista ---

    @Override
    public void agregar(T elem)                  { poneEnCola(elem); }

    @Override
    public void agregar(int index, T elem) {
        if (index < 0 || index > tamanio) throw new IndexOutOfBoundsException();
        if (index == tamanio) { poneEnCola(elem); return; }
        Nodo<T> nuevo = new Nodo<>(elem);
        if (index == 0) { nuevo.siguiente = frente; frente = nuevo; }
        else {
            Nodo<T> aux = nodoEn(index - 1);
            nuevo.siguiente = aux.siguiente;
            aux.siguiente = nuevo;
        }
        tamanio++;
    }

    @Override
    public T obtener(int index) {
        if (index < 0 || index >= tamanio) throw new IndexOutOfBoundsException();
        return nodoEn(index).dato;
    }

    @Override
    public T remover(int index) {
        if (index < 0 || index >= tamanio) throw new IndexOutOfBoundsException();
        if (index == 0) return quitaDeCola();
        Nodo<T> anterior = nodoEn(index - 1);
        T dato = anterior.siguiente.dato;
        if (anterior.siguiente == posterior) posterior = anterior;
        anterior.siguiente = anterior.siguiente.siguiente;
        tamanio--;
        return dato;
    }

    @Override
    public boolean remover(T elem) {
        int idx = indiceDe(elem);
        if (idx == -1) return false;
        remover(idx);
        return true;
    }

    @Override
    public boolean contiene(T elem)  { return indiceDe(elem) != -1; }

    @Override
    public int indiceDe(T elem) {
        Nodo<T> aux = frente;
        int i = 0;
        while (aux != null) {
            if (aux.dato.equals(elem)) return i;
            aux = aux.siguiente; i++;
        }
        return -1;
    }

    @Override
    public T buscar(Predicate<T> criterio) {
        Nodo<T> aux = frente;
        while (aux != null) {
            if (criterio.test(aux.dato)) return aux.dato;
            aux = aux.siguiente;
        }
        return null;
    }

    @Override
    public TDALista<T> ordenar(Comparator<T> comparator) {
        Cola<T> copia = new Cola<>();
        Nodo<T> aux = frente;
        while (aux != null) { copia.poneEnCola(aux.dato); aux = aux.siguiente; }
        boolean cambio = true;
        while (cambio) {
            cambio = false;
            Nodo<T> cur = copia.frente;
            while (cur != null && cur.siguiente != null) {
                if (comparator.compare(cur.dato, cur.siguiente.dato) > 0) {
                    T tmp = cur.dato; cur.dato = cur.siguiente.dato; cur.siguiente.dato = tmp;
                    cambio = true;
                }
                cur = cur.siguiente;
            }
        }
        return copia;
    }

    @Override
    public int tamaño()       { return tamanio; }

    @Override
    public boolean esVacio()  { return tamanio == 0; }

    @Override
    public void vaciar()      { frente = null; posterior = null; tamanio = 0; }

    private Nodo<T> nodoEn(int index) {
        Nodo<T> aux = frente;
        for (int i = 0; i < index; i++) aux = aux.siguiente;
        return aux;
    }
}
```

---

## Pila\<T\>

LIFO (festival-otaku). Lista enlazada interna con puntero `tope` — `mete`, `saca` y `tope()` son O(1).

```java
public class Pila<T> implements TDAPila<T> {

    private static class Nodo<T> {
        T dato;
        Nodo<T> siguiente;
        Nodo(T dato) { this.dato = dato; }
    }

    private Nodo<T> tope;
    private int tamanio;

    public Pila() { tope = null; tamanio = 0; }

    // --- operaciones LIFO ---

    @Override
    public void mete(T dato) {
        Nodo<T> nuevo = new Nodo<>(dato);
        nuevo.siguiente = tope;
        tope = nuevo;
        tamanio++;
    }

    @Override
    public T saca() {
        if (esVacio()) throw new java.util.NoSuchElementException("Pila vacía");
        T dato = tope.dato;
        tope = tope.siguiente;
        tamanio--;
        return dato;
    }

    @Override
    public T tope() {
        if (esVacio()) throw new java.util.NoSuchElementException("Pila vacía");
        return tope.dato;
    }

    // --- operaciones TDALista ---

    @Override
    public void agregar(T elem)   { mete(elem); }

    @Override
    public void agregar(int index, T elem) {
        if (index < 0 || index > tamanio) throw new IndexOutOfBoundsException();
        if (index == 0) { mete(elem); return; }
        Nodo<T> nuevo = new Nodo<>(elem);
        Nodo<T> aux = nodoEn(index - 1);
        nuevo.siguiente = aux.siguiente;
        aux.siguiente = nuevo;
        tamanio++;
    }

    @Override
    public T obtener(int index) {
        if (index < 0 || index >= tamanio) throw new IndexOutOfBoundsException();
        return nodoEn(index).dato;
    }

    @Override
    public T remover(int index) {
        if (index < 0 || index >= tamanio) throw new IndexOutOfBoundsException();
        if (index == 0) return saca();
        Nodo<T> anterior = nodoEn(index - 1);
        T dato = anterior.siguiente.dato;
        anterior.siguiente = anterior.siguiente.siguiente;
        tamanio--;
        return dato;
    }

    @Override
    public boolean remover(T elem) {
        int idx = indiceDe(elem);
        if (idx == -1) return false;
        remover(idx);
        return true;
    }

    @Override
    public boolean contiene(T elem)  { return indiceDe(elem) != -1; }

    @Override
    public int indiceDe(T elem) {
        Nodo<T> aux = tope;
        int i = 0;
        while (aux != null) {
            if (aux.dato.equals(elem)) return i;
            aux = aux.siguiente; i++;
        }
        return -1;
    }

    @Override
    public T buscar(Predicate<T> criterio) {
        Nodo<T> aux = tope;
        while (aux != null) {
            if (criterio.test(aux.dato)) return aux.dato;
            aux = aux.siguiente;
        }
        return null;
    }

    @Override
    public TDALista<T> ordenar(Comparator<T> comparator) {
        Pila<T> copia = new Pila<>();
        Nodo<T> aux = tope;
        while (aux != null) { copia.mete(aux.dato); aux = aux.siguiente; }
        boolean cambio = true;
        while (cambio) {
            cambio = false;
            Nodo<T> cur = copia.tope;
            while (cur != null && cur.siguiente != null) {
                if (comparator.compare(cur.dato, cur.siguiente.dato) > 0) {
                    T tmp = cur.dato; cur.dato = cur.siguiente.dato; cur.siguiente.dato = tmp;
                    cambio = true;
                }
                cur = cur.siguiente;
            }
        }
        return copia;
    }

    @Override
    public int tamaño()       { return tamanio; }

    @Override
    public boolean esVacio()  { return tamanio == 0; }

    @Override
    public void vaciar()      { tope = null; tamanio = 0; }

    private Nodo<T> nodoEn(int index) {
        Nodo<T> aux = tope;
        for (int i = 0; i < index; i++) aux = aux.siguiente;
        return aux;
    }
}
```

---

## TElementoAB\<T\>

Nodo del BST (2024-S1). Usar `IElementoAB<T>` en las firmas de métodos propios.

```java
public class TElementoAB<T> implements IElementoAB<T> {

    private Comparable etiqueta;
    private TElementoAB<T> hijoIzq;
    private TElementoAB<T> hijoDer;
    private T datos;

    public TElementoAB(Comparable etiqueta, T datos) {
        this.etiqueta = etiqueta;
        this.datos = datos;
    }

    public T getDatos()                        { return datos; }
    public Comparable getEtiqueta()            { return etiqueta; }
    public TElementoAB<T> getHijoIzq()        { return hijoIzq; }
    public TElementoAB<T> getHijoDer()        { return hijoDer; }
    public void setHijoIzq(TElementoAB<T> e)  { hijoIzq = e; }
    public void setHijoDer(TElementoAB<T> e)  { hijoDer = e; }
    public String imprimir()                   { return etiqueta.toString(); }

    @SuppressWarnings("unchecked")
    public boolean insertar(TElementoAB<T> elem) {
        if (elem.getEtiqueta().compareTo(etiqueta) < 0) {
            if (hijoIzq != null) return hijoIzq.insertar(elem);
            hijoIzq = elem; return true;
        } else if (elem.getEtiqueta().compareTo(etiqueta) > 0) {
            if (hijoDer != null) return hijoDer.insertar(elem);
            hijoDer = elem; return true;
        }
        return false; // duplicado — no inserta
    }

    @SuppressWarnings("unchecked")
    public TElementoAB<T> buscar(Comparable clave) {
        if (clave.equals(etiqueta))          return this;
        if (clave.compareTo(etiqueta) < 0)   return hijoIzq != null ? hijoIzq.buscar(clave) : null;
        return hijoDer != null ? hijoDer.buscar(clave) : null;
    }

    @SuppressWarnings("unchecked")
    public TElementoAB<T> eliminar(Comparable clave) {
        if (clave.compareTo(etiqueta) < 0) {
            if (hijoIzq != null) hijoIzq = hijoIzq.eliminar(clave);
            return this;
        }
        if (clave.compareTo(etiqueta) > 0) {
            if (hijoDer != null) hijoDer = hijoDer.eliminar(clave);
            return this;
        }
        return quitaElNodo();
    }

    private TElementoAB<T> quitaElNodo() {
        if (hijoIzq == null) return hijoDer;
        if (hijoDer == null) return hijoIzq;
        TElementoAB<T> hijo = hijoIzq, padre = this;
        while (hijo.getHijoDer() != null) { padre = hijo; hijo = hijo.getHijoDer(); }
        if (padre != this) { padre.setHijoDer(hijo.getHijoIzq()); hijo.setHijoIzq(hijoIzq); }
        hijo.setHijoDer(hijoDer);
        setHijoIzq(null); setHijoDer(null);
        return hijo;
    }

    public int obtenerTamaño() {
        int t = 1;
        if (hijoIzq != null) t += hijoIzq.obtenerTamaño();
        if (hijoDer != null) t += hijoDer.obtenerTamaño();
        return t;
    }

    public void inOrden(java.util.LinkedList<T> lista) {
        if (hijoIzq != null) hijoIzq.inOrden(lista);
        lista.add(datos);
        if (hijoDer != null) hijoDer.inOrden(lista);
    }

    public void preOrden(java.util.LinkedList<T> lista) {
        lista.add(datos);
        if (hijoIzq != null) hijoIzq.preOrden(lista);
        if (hijoDer != null) hijoDer.preOrden(lista);
    }

    public void postOrden(java.util.LinkedList<T> lista) {
        if (hijoIzq != null) hijoIzq.postOrden(lista);
        if (hijoDer != null) hijoDer.postOrden(lista);
        lista.add(datos);
    }

    public String inOrden() {
        StringBuilder sb = new StringBuilder();
        if (hijoIzq != null) sb.append(hijoIzq.inOrden()).append(TArbolBB.SEPARADOR_ELEMENTOS_IMPRESOS);
        sb.append(imprimir());
        if (hijoDer != null) sb.append(TArbolBB.SEPARADOR_ELEMENTOS_IMPRESOS).append(hijoDer.inOrden());
        return sb.toString();
    }

    public int obtenerAltura() {
        int altIzq = (hijoIzq != null) ? hijoIzq.obtenerAltura() : -1;
        int altDer = (hijoDer != null) ? hijoDer.obtenerAltura() : -1;
        return 1 + Math.max(altIzq, altDer);
    }

    @SuppressWarnings("unchecked")
    public int obtenerNivel(Comparable criterio, int nivelActual) {
        if (criterio.compareTo(etiqueta) == 0) return nivelActual;
        if (criterio.compareTo(etiqueta) < 0)
            return hijoIzq != null ? hijoIzq.obtenerNivel(criterio, nivelActual + 1) : -1;
        return hijoDer != null ? hijoDer.obtenerNivel(criterio, nivelActual + 1) : -1;
    }
}
```

---

## TArbolBB\<T\>

BST genérico (2024-S1). `TArbolDeProductos` extiende esta clase sin agregar métodos.

```java
public class TArbolBB<T> implements IArbolBB<T> {

    protected TElementoAB<T> raiz;
    public static final String SEPARADOR_ELEMENTOS_IMPRESOS = "-";

    public TArbolBB() { raiz = null; }

    public boolean insertar(Comparable etiqueta, T dato) {
        TElementoAB<T> elem = new TElementoAB<>(etiqueta, dato);
        if (esVacio()) { raiz = elem; return true; }
        return raiz.insertar(elem);
    }

    public T buscar(Comparable etiqueta) {
        if (esVacio()) return null;
        TElementoAB<T> e = raiz.buscar(etiqueta);
        return e != null ? e.getDatos() : null;
    }

    public void eliminar(Comparable etiqueta) {
        if (!esVacio()) raiz = raiz.eliminar(etiqueta);
    }

    public boolean esVacio()  { return raiz == null; }

    public boolean vaciar() {
        if (!esVacio()) { raiz = null; return true; }
        return false;
    }

    public TElementoAB<T> getRaiz() { return raiz; }

    public int obtenerAltura() {
        if (esVacio()) return -1;
        return raiz.obtenerAltura();
    }

    public int obtenerNivel(Comparable criterio) {
        if (esVacio()) return -1;
        return raiz.obtenerNivel(criterio, 0);
    }

    // inOrden / preOrden / postOrden usan LinkedList internamente.
    // En el parcial: implementar recorridos manualmente con getRaiz() (ver Patrones).
    public java.util.List<T> inOrden() {
        if (esVacio()) return null;
        java.util.LinkedList<T> lista = new java.util.LinkedList<>();
        raiz.inOrden(lista);
        return lista;
    }

    public java.util.List<T> preOrden() {
        if (esVacio()) return null;
        java.util.LinkedList<T> lista = new java.util.LinkedList<>();
        raiz.preOrden(lista);
        return lista;
    }

    public java.util.List<T> postOrden() {
        if (esVacio()) return null;
        java.util.LinkedList<T> lista = new java.util.LinkedList<>();
        raiz.postOrden(lista);
        return lista;
    }
}
```

---

## TElementoAVL\<T\>

Nodo del AVL. Extiende `TElementoAB<T>` agregando el campo `altura` almacenado — esto permite que `obtenerAltura`, `factorBalance` y todas las rotaciones sean **O(1)**.

```java
public class TElementoAVL<T> extends TElementoAB<T> {

    private int altura;

    public TElementoAVL(Comparable etiqueta, T datos) {
        super(etiqueta, datos);
        this.altura = 0;
    }

    public int getAltura() { return altura; }

    // --- auxiliares O(1) ---

    private static int altura(TElementoAVL<?> nodo) {
        return nodo == null ? -1 : nodo.altura;
    }

    private void actualizarAltura() {
        this.altura = 1 + Math.max(
            altura((TElementoAVL<?>) getHijoIzq()),
            altura((TElementoAVL<?>) getHijoDer())
        );
    }

    private int factorBalance() {
        return altura((TElementoAVL<?>) getHijoDer())
             - altura((TElementoAVL<?>) getHijoIzq());
    }

    // --- rotaciones O(1) ---

    private TElementoAVL<T> rotacionDerecha() {          // LL
        TElementoAVL<T> k1 = (TElementoAVL<T>) getHijoIzq();
        setHijoIzq(k1.getHijoDer());
        k1.setHijoDer(this);
        this.actualizarAltura();
        k1.actualizarAltura();
        return k1;
    }

    private TElementoAVL<T> rotacionIzquierda() {        // RR
        TElementoAVL<T> k2 = (TElementoAVL<T>) getHijoDer();
        setHijoDer(k2.getHijoIzq());
        k2.setHijoIzq(this);
        this.actualizarAltura();
        k2.actualizarAltura();
        return k2;
    }

    private TElementoAVL<T> rotacionDobleIzquierdaDerecha() {  // LR
        setHijoIzq(((TElementoAVL<T>) getHijoIzq()).rotacionIzquierda());
        return rotacionDerecha();
    }

    private TElementoAVL<T> rotacionDobleDerechaIzquierda() {  // RL
        setHijoDer(((TElementoAVL<T>) getHijoDer()).rotacionDerecha());
        return rotacionIzquierda();
    }

    // --- balancear O(1) ---

    private TElementoAVL<T> balancear() {
        actualizarAltura();
        int bf = factorBalance();
        if (bf == -2) {
            TElementoAVL<T> hijoIzq = (TElementoAVL<T>) getHijoIzq();
            return hijoIzq.factorBalance() <= 0
                ? rotacionDerecha()                   // LL
                : rotacionDobleIzquierdaDerecha();    // LR
        }
        if (bf == 2) {
            TElementoAVL<T> hijoDer = (TElementoAVL<T>) getHijoDer();
            return hijoDer.factorBalance() >= 0
                ? rotacionIzquierda()                 // RR
                : rotacionDobleDerechaIzquierda();    // RL
        }
        return this;
    }

    // --- insertar con balanceo O(log n) ---

    @SuppressWarnings("unchecked")
    public TElementoAVL<T> insertarAVL(TElementoAVL<T> elemento) {
        if (elemento.getEtiqueta().compareTo(getEtiqueta()) < 0) {
            if (getHijoIzq() == null)
                setHijoIzq(elemento);
            else
                setHijoIzq(((TElementoAVL<T>) getHijoIzq()).insertarAVL(elemento));
        } else if (elemento.getEtiqueta().compareTo(getEtiqueta()) > 0) {
            if (getHijoDer() == null)
                setHijoDer(elemento);
            else
                setHijoDer(((TElementoAVL<T>) getHijoDer()).insertarAVL(elemento));
        }
        // si la etiqueta ya existe, no inserta (duplicado ignorado)
        return balancear();
    }

    // --- eliminar con balanceo O(log n) ---
    // En Java los métodos private son accesibles en todas las instancias de la misma clase,
    // por eso pred.balancear() compila aunque balancear() sea private.

    @SuppressWarnings("unchecked")
    public TElementoAVL<T> eliminarAVL(Comparable clave) {
        if (clave.compareTo(getEtiqueta()) < 0) {
            if (getHijoIzq() != null)
                setHijoIzq(((TElementoAVL<T>) getHijoIzq()).eliminarAVL(clave));
        } else if (clave.compareTo(getEtiqueta()) > 0) {
            if (getHijoDer() != null)
                setHijoDer(((TElementoAVL<T>) getHijoDer()).eliminarAVL(clave));
        } else {
            // Encontrado — 3 casos idénticos al BST, pero el caso 2-hijos rebalancea
            if (getHijoIzq() == null) return (TElementoAVL<T>) getHijoDer(); // caso 0 o 1 hijo (der)
            if (getHijoDer() == null) return (TElementoAVL<T>) getHijoIzq(); // caso 1 hijo (izq)
            // Caso 2 hijos: predecesor inorden = máximo del subárbol izquierdo
            TElementoAVL<T> pred = (TElementoAVL<T>) getHijoIzq();
            while (pred.getHijoDer() != null) pred = (TElementoAVL<T>) pred.getHijoDer();
            // Eliminar el predecesor del subárbol izquierdo (con rebalanceo recursivo)
            TElementoAVL<T> nuevoIzq = ((TElementoAVL<T>) getHijoIzq()).eliminarAVL(pred.getEtiqueta());
            // Promover predecesor al lugar de this
            pred.setHijoIzq(nuevoIzq);
            pred.setHijoDer((TElementoAVL<T>) getHijoDer());
            setHijoIzq(null); setHijoDer(null); // limpiar punteros para GC
            return pred.balancear();
        }
        return balancear();
    }
}
```

---

## TArbolAVL\<T\>

AVL completo. Extiende `TArbolBB<T>` y sobreescribe `insertar` y `eliminar` para mantener el balance. Hereda `buscar`, `getRaiz`, `esVacio`, `obtenerAltura`, `obtenerNivel` sin cambios.

```java
public class TArbolAVL<T> extends TArbolBB<T> {

    public TArbolAVL() { super(); }

    @Override
    public boolean insertar(Comparable etiqueta, T dato) {
        TElementoAVL<T> elemento = new TElementoAVL<>(etiqueta, dato);
        if (esVacio()) {
            raiz = elemento;
            return true;
        }
        int tamAntes = raiz.obtenerTamaño();
        raiz = ((TElementoAVL<T>) raiz).insertarAVL(elemento);
        return raiz.obtenerTamaño() > tamAntes;   // false si fue duplicado
    }

    @Override
    public void eliminar(Comparable etiqueta) {
        if (!esVacio()) raiz = ((TElementoAVL<T>) raiz).eliminarAVL(etiqueta);
    }
}
```

---

## ManejadorArchivosGenerico

Clase utilitaria estática (2024-S1). Misma API en todos los parciales.

```java
public class ManejadorArchivosGenerico {

    public static void escribirArchivo(String ruta, String[] lineas) {
        try (java.io.FileWriter fw = new java.io.FileWriter(ruta, true);
             java.io.BufferedWriter bw = new java.io.BufferedWriter(fw)) {
            for (String linea : lineas) { bw.write(linea); bw.newLine(); }
        } catch (java.io.IOException e) { e.printStackTrace(); }
    }

    public static String[] leerArchivo(String ruta) {
        java.util.ArrayList<String> lineas = new java.util.ArrayList<>();
        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(ruta))) {
            String linea;
            while ((linea = br.readLine()) != null) lineas.add(linea);
        } catch (java.io.IOException e) { e.printStackTrace(); }
        return lineas.toArray(new String[0]);
    }
}
```


---

*Código base AED UCU 2024-S1, festival-otaku, farmachop — 2026.*
