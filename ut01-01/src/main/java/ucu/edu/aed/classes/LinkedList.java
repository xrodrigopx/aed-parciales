package ucu.edu.aed.classes;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.Predicate;

import ucu.edu.aed.tda.TDALista;

// lista generica que implementa el TDALista
public class LinkedList<T> implements TDALista<T> {

    // nodo simple para encadenar datos genericos
    // no declara su propio <T>: usa el T del LinkedList externo
    protected class Node {
        // dato guardado en el nodo
        private final T data;
        // referencia al siguiente nodo
        private Node next = null;

        // arma el nodo con el dato recibido
        public Node(T data) {
            this.data = data;
        }

        // devuelve el dato del nodo
        public T getData() {
            return data;
        }

        // devuelve el siguiente nodo
        public Node getNext() {
            return this.next;
        }

        // conecta este nodo con otro
        public void setNext(Node next) {
            this.next = next;
        }
    }
    // atributos de la lista
    // tiene un nodo cabeza
    private Node head = null;
    // tiene un nodo de cola al final
    private Node tail = null;
    // tiene un tamaño.
    private int size;


    // metodo agregar
    @Override
    // recibe un elemento
    public void agregar(T elem) {
        // se crea el elemento con lo que recibió
        Node nuevo = new Node(elem);
        // si la cabeza es nula (cosa que en principio es true)
        if (this.head == null) {
            // el nuevo elemento va a ser la cabeza
            this.head = nuevo;
            // y va a ser la cola al mismo tiempo, porque no hay mas elementos
            this.tail = nuevo;
        // si ya hay una cabeza (mas de un elemento en la lista)
        }else {
            // se inserta un nodo nuevo en la posicion siguiente a la cola
            this.tail.setNext(nuevo);
            // ese insertado anteriormente pasa a ser la cola
            this.tail = nuevo;
        }
        // se incrementa el tamaño en uno.
        this.size++;
    }

    // metodo para insertar en la lista dado un indice
    @Override
    public void agregar(int index, T elem) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Fuera de rango");
        }
        if (index > size) {
            throw new IndexOutOfBoundsException("Fuera de rango");
        }
        // creamos un nodo nuevo para insertar
        Node nuevo = new Node(elem);
        // si el indice que le voy a pasar es 0
        if (index == 0) {
            // inserto uno nuevo
            nuevo.setNext(head);
            // ese insertado, ahora es la cabeza
            this.head = nuevo;
            // si no tiene cola
            if (tail == null) {
                // ese nuevo insertado, es la cola
                this.tail = nuevo;
            }
        // si el indice es igual al largo de la lista, despreciando la cantidad de elementos
        } else if (index == size) {
            // lo inserto al lado de la cola
            this.tail.setNext(nuevo); 
            // le asigno el puntero, el nuevo pasa a ser la cola
            this.tail = nuevo;
        // si el indice es menor que el tamaño de la lista, pero no es 0
        } else {
            // genero un nodo llamado anterior, que va a ir en la posicion anterior al indice dado
            Node anterior = nodeAt(index -1);
            // este nodo llamado anterior va a ser el nuevo que voy a insertar al lado del que era el anterior
            nuevo.setNext(anterior.getNext());
            // cambio el puntero
            anterior.setNext(nuevo); 
        }
        // aumento el tamaño siempre que inserto en todos los casos
        this.size++;
    }
    // metodo para recorrer la lista devolviendo un nodo
    private Node nodeAt(int index){
        if (index < 0) {
            throw new IndexOutOfBoundsException("Indice fuera de rango: " + index);
        }
        if (index >= size) {
            throw new IndexOutOfBoundsException("Indice fuera de rango: " + index);
        }
        Node actual = head;
        for (int i=0; i < index; i++){
            actual = actual.getNext();
        }
    return actual;
    } 
    

    // metodo para obtener dato del nodo dado un indice
    @Override
    public T obtener(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (index > size) {
            throw new IndexOutOfBoundsException();
        }
        return nodeAt(index).getData();
    }


    // metodo para borrar un nodo dado un indice
    @Override
    public T remover(int index){
        // en el caso de que me ponga cualquier indice
        if (index < 0) {
            throw new IndexOutOfBoundsException("fuera de rango");
        }
        if (index >= size) {
            throw new IndexOutOfBoundsException("fuera de rango");
        // si voy a deletear la cabeza
        } else if (index == 0) {
            // en una variable guardo el dato de la cabeza
            T dato = head.getData();
            // la cabeza ahora será el siguiente nodo después de la cabeza
            head = head.getNext();
            // si la cabeza no existe
            if (head == null) {
                // tampoco la cola
                tail = null;
            }
            // disminuyo el tamaño de la cola
            size--;
            // devuelvo el nodo que voy a borrar
            return dato;
        }
    // creo un nodo nuevo para almacenar el anterior
    Node anterior = nodeAt(index -1);
    // creo otro nodo para almacenar el nodo que viene después del anterior, que es el nodo que quiero borrar
    Node objetivo = anterior.getNext();
    // aca es donde cambiamos el puntero del anterior al del siguiente.
    anterior.setNext(objetivo.getNext());
    // si el objetivo es el ultimo
    if (objetivo == tail) {
        // dejamos el puntero de la cola en el nodo anterior.
        tail = anterior;
    }
    // decrementamos la longitud de la lista
    size--;
    return objetivo.getData();
    }

    // Remueve la primera ocurrencia del elemento indicado en la lista.
    @Override
    public boolean remover(T elem) {
        // en el caso de que la lista esté vacía
        if (head == null) {
            return false;
        }
        // si el elemento a remover es el de la cabeza
        if (Objects.equals(head.getData(), elem)) {
            // la cabeza ahora será el siguiente nodo después de la cabeza
            head = head.getNext();
            // si la cabeza no existe
            if (head == null) {
                // tampoco la cola
                tail = null;
            }
            // disminuyo el tamaño de la cola
            size--;
            return true;
        }
        // creo un nodo nuevo para almacenar el anterior
        Node anterior = head;
        // creo otro nodo para almacenar el nodo que viene después del anterior, que es el nodo que quiero borrar
        Node objetivo = head.getNext();
        // mientras el objetivo no sea nulo
        while (objetivo != null) {
            // si el dato del objetivo es igual al elemento a remover
            if (Objects.equals(objetivo.getData(), elem)) {
                // aca es donde cambiamos el puntero del anterior al del siguiente.
                anterior.setNext(objetivo.getNext()); 
                // si el objetivo es el ultimo
                if (objetivo == tail) {
                    // dejamos el puntero de la cola en el nodo anterior.
                    tail = anterior;
                }
                // decrementamos la longitud de la lista
                size--;
                return true;
            }
            // avanzamos el nodo anterior y el objetivo
            anterior = objetivo;
            objetivo = objetivo.getNext();
        }
        return false;
    }

    // Retorna el índice de la primera ocurrencia del elemento indicado.
    @Override
    public boolean contiene(T elem) {
        return indiceDe(elem) != -1;
    }

    @Override
    // Retorna el índice de la primera ocurrencia del elemento indicado.
    public int indiceDe(T elem) {
        Node actual = head;
        int indice = 0;
        while (actual != null) {
            if (Objects.equals(actual.getData(), elem)) {
                return indice;
            }
            actual = actual.getNext();
            indice++;
        }
        return -1;
    }

    @Override
    // metodo buscar por predicado, siendo predicado una función que recibe un elemento y devuelve un booleano, para encontrar un elemento que cumpla esa condición
    public T buscar(Predicate<T> criterio) {
        // revisamos si la lista está vacía, en cuyo caso no hay nada que buscar
        if (esVacio()) {
            return null;
            // cuando no está vacía, recorremos la lista buscando un elemento que cumpla el criterio dado
        } else{ 
            // recorremos la lista desde la cabeza, mientras el nodo actual no sea nulo
            Node actual = head;
            while (actual != null) {
                if (criterio.test(actual.getData())) {
                    return actual.getData();
                }
                // avanzamos al siguiente nodo
                actual = actual.getNext();
            }
            return null;

        }
    }

    @Override
    // metodo para ordenar la lista dado un comparador, que es una función que recibe dos elementos y devuelve un entero negativo, cero o positivo dependiendo de si el primer elemento es menor, igual o mayor que el segundo
    public TDALista<T> ordenar(Comparator<T> comparator) {
        if (comparator == null) {
            throw new IllegalArgumentException("El comparador no puede ser nulo");
        }
        LinkedList<T> copia = new LinkedList<>();
        if (esVacio()) {
            return copia;
        }

        Node actual = head;
        while (actual != null) {
            T dato = actual.getData();
            int posicion = 0;
            Node cursor = copia.head;

            boolean seguir = cursor != null;
            while (seguir) {
                if (comparator.compare(dato, cursor.getData()) >= 0) {
                    posicion++;
                    cursor = cursor.getNext();
                    seguir = cursor != null;
                } else {
                    seguir = false;
                }
            }

            copia.agregar(posicion, dato);
            actual = actual.getNext();
        }
        return copia;
    }

    public Object[] aArreglo() {
        Object[] copia = new Object[size];
        Node actual = head;
        int i = 0;
        while (actual != null) {
            copia[i++] = actual.getData();
            actual = actual.getNext();
        }
        return copia;
    }

    @SuppressWarnings("unchecked")
    public T[] aArreglo(java.util.function.IntFunction<T[]> generador) {
        Object[] base = aArreglo();
        T[] typed = generador.apply(base.length);
        for (int i = 0; i < base.length; i++) {
            typed[i] = (T) base[i];
        }
        return typed;
    }

    @Override
    public int tamaño(){
        return this.size;
    }

    @Override
    public boolean esVacio(){
        return tamaño() == 0;
    }

    @Override
    public void vaciar(){
        head = null;
        tail = null;
        size = 0;
    }


}
