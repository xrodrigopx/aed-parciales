package ucu.edu.aed.tda.hash;

public class THashEjercicio {
    private String[] tablaHash;
    private int size;
    private int capacity;

    public THashEjercicio(int capacity) {
        this.capacity = capacity;
        this.tablaHash = new String[capacity];
        this.size = 0;
    }

    // Devuelve la posición generada por la función hash para una clave dada
    public int funcionHashing(String unaClave) {
        if (unaClave == null) return -1;
        
        int sumaAscii = 0;
        for (int i = 0; i < unaClave.length(); i++) {
            sumaAscii += unaClave.charAt(i);
        }
        return sumaAscii % capacity;
    }

    // Inserta usando sondeo lineal, devuelve cantidad de comparaciones/intentos realizados
    public int insertar(String unaClave) {
        if (unaClave == null) {
            return -1;
        }
        if (size >= capacity) {
            return -1; // Retorna -1 si la clave es nula o la tabla está llena
        }

        int comparaciones = 0;
        int h0 = funcionHashing(unaClave);

        if (h0 == -1) return -1; // Seguridad extra

        for (int i = 0; i < capacity; i++) {
            int pos = (h0 + i) % capacity;
            comparaciones++; // Contamos el intento actual
            
            if (tablaHash[pos] == null) {
                tablaHash[pos] = unaClave; // Insertamos en el primer hueco disponible
                size++;
                return comparaciones;
            } else if (tablaHash[pos].equals(unaClave)) {
                return comparaciones; // La palabra ya está en la tabla, evitamos duplicados
            }
        }
        return -1;
    }

    // Devuelve cantidad de comparaciones realizadas hasta encontrar o confirmar ausencia
    public int buscar(String unaClave) {
        if (unaClave == null) return -1;
        
        int comparaciones = 0;
        int h0 = funcionHashing(unaClave);
        
        if (h0 == -1) return -1; // Seguridad extra

        for (int i = 0; i < capacity; i++) {
            int pos = (h0 + i) % capacity;
            comparaciones++; // Contamos la comparación actual
            
            if (tablaHash[pos] == null) {
                return comparaciones; // Si encuentra un hueco nulo, la palabra no está
            } else if (tablaHash[pos].equals(unaClave)) {
                return comparaciones; // Palabra encontrada
            }
        }
        return comparaciones; // Recorrió toda la tabla (llena) y no lo encontró
    }

    public int capacity() {
        return tablaHash.length;
    }

    public static void main(String[] args) {
        THashEjercicio tabla = new THashEjercicio(11);
        String[] palabras = {"if", "int", "for", "static", "class", "new", "this", "add"};

        System.out.println("--- INSERTANDO PALABRAS ---");
        for (String palabra : palabras) {
            int comp = tabla.insertar(palabra);
            System.out.println("Insertada: " + palabra + " | Comparaciones/intentos: " + comp + " | Posición inicial (h0): " + tabla.funcionHashing(palabra));
        }

        System.out.println("\n--- BUSCANDO PALABRAS ---");
        String[] palabrasBuscar = {"for", "this", "while", "add", null};
        for (String palabra : palabrasBuscar) {
            int comp = tabla.buscar(palabra);
            System.out.println("Buscar: " + palabra + " | Comparaciones realizadas: " + comp);
        }
    }
}
