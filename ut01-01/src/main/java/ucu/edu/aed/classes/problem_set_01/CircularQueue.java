package ucu.edu.aed.classes.problem_set_01;

// cola circular usando array o vector (ejercicio 27)
// con esto no tengo que andar moviendo todos los elementos a la izquierda cada que saco algo
public class CircularQueue<T> {

    // el array que funciona de memoria interna
    private final Object[] vector;

    // cuanto banca esto adentro
    private final int capacidad;

    // indice donde esta guardado el primero
    private int frente;

    // indice al lugarcito de adelante que este vacio para el final
    private int fin;

    // para contar facil cuantos objetos meti
    private int cantidad;

    // creo el vector
    public CircularQueue(int capacidad) {
        if (capacidad <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser positiva");
        }
        this.capacidad = capacidad;
        this.vector = new Object[capacidad];
        this.frente = 0;
        this.fin = 0;
        this.cantidad = 0;
    }

    // funcion para mandar algo a la cola
    public boolean poneEnCola(T elemento) {
        if (estaLlena()) {
            throw new IllegalStateException("La cola esta llena chumbo");
        }
        // lo guardo apuntando al final de todos
        vector[fin] = elemento;
        
        // esto es clave: aumento 'fin' pero si supero la capacidad vuelve a cero
        fin = (fin + 1) % capacidad;
        cantidad++;
        return true;
    }

    // sacar a la persona que le toca primero de la fila
    @SuppressWarnings("unchecked")
    public T quitaDeCola() {
        if (estaVacia()) {
            throw new java.util.NoSuchElementException("La cola esta vacia");
        }
        // leo la data de la persona del frente
        T dato = (T) vector[frente];
        // y para evitar cositas raras en memoria la borramos de verdad
        vector[frente] = null;
        
        // le paso el turno al de atras, otra vez usando modulo
        frente = (frente + 1) % capacidad;
        cantidad--;
        return dato;
    }

    // ver el que esta de primero unicamente (sin borrarlo del mapa)
    @SuppressWarnings("unchecked")
    public T frente() {
        if (estaVacia()) {
            throw new java.util.NoSuchElementException("La cola esta vacia");
        }
        return (T) vector[frente];
    }

    // metodos de ayuda general
    public boolean estaVacia() {
        return cantidad == 0;
    }

    public boolean estaLlena() {
        return cantidad == capacidad;
    }

    public int getCantidad() {
        return cantidad;
    }

    public int getCapacidad() {
        return capacidad;
    }

    // es pa poderlo imprimir rapido por consola si andamos debugeando
    @Override
    public String toString() {
        if (estaVacia()) return "CircularQueue[]";
        StringBuilder sb = new StringBuilder("CircularQueue[");
        int idx = frente;
        for (int i = 0; i < cantidad; i++) {
            sb.append(vector[idx]);
            if (i < cantidad - 1) sb.append(", ");
            idx = (idx + 1) % capacidad;
        }
        sb.append("]");
        return sb.toString();
    }
}
