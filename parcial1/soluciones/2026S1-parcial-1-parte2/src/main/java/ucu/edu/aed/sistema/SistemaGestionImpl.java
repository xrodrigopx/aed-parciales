
package ucu.edu.aed.sistema;

import ucu.edu.aed.modelo.Tarea;
import ucu.edu.aed.tda.AVLArbol;

/**
 * Implementación del sistema de gestión de tareas operativas de la nave.
 *
 * Estructuras utilizadas:
 * - colaCriticas: min-heap de tareas con criticidad 1 y 2 (prioridad por criticidad luego FIFO)
 * - colaNormales: cola FIFO para tareas con criticidad 3 y 4
 * - colaEspera: cola FIFO de tareas que no pudieron ingresar al pool de pendientes
 * - historial: AVL árbol para búsqueda O(log n) por id
 */
public class SistemaGestionImpl implements SistemaGestion {

    private static final int MAX_PENDIENTES_TOTAL = 25;
    private static final int MAX_CRITICAS_PENDIENTES = 10;

    // Contador global de orden de llegada para desempate FIFO
    private int contadorOrden;

    // Min-heap para tareas críticas (criticidad 1 y 2)
    private MinHeapTareas colaCriticas;

    // Cola FIFO para tareas normales (criticidad 3 y 4)
    private ColaTareas colaNormales;

    // Cola FIFO de espera (tareas que no pudieron ingresar aún)
    private ColaTareas colaEspera;

    // Árbol AVL para historial de tareas procesadas (búsqueda O(log n) por id)
    private AVLArbol<Tarea> historial;

    public SistemaGestionImpl() {
        this.contadorOrden = 0;
        this.colaCriticas = new MinHeapTareas(MAX_PENDIENTES_TOTAL);
        this.colaNormales = new ColaTareas();
        this.colaEspera = new ColaTareas();
        this.historial = new AVLArbol<>();
    }

    /**
     * R1 — Recibe una nueva tarea. Si no puede admitirse inmediatamente, queda en espera.
     */
    @Override
    public boolean recibirTarea(Tarea tarea) {
        if (tarea == null) return false;

        tarea.setOrdenLlegada(contadorOrden++);

        if (puedeAdmitir(tarea)) {
            admitirTarea(tarea);
            return true;
        } else {
            colaEspera.encolar(tarea);
            return false;
        }
    }

    /**
     * R2 — Procesa la tarea de mayor criticidad (1 antes que 2, críticas antes que normales).
     * En empate aplica FIFO. Tras procesarla la registra en el historial.
     * Luego intenta admitir tareas de la cola de espera.
     */
    @Override
    public Tarea procesarTarea() {
        Tarea procesada = null;

        if (!colaCriticas.esVacia()) {
            procesada = colaCriticas.desencolar();
        } else if (!colaNormales.esVacia()) {
            procesada = colaNormales.desencolar();
        }

        if (procesada == null) return null;

        // Registrar en historial AVL
        historial.insertar(new CriterioPorId(procesada));

        // Intentar admitir tareas en espera
        admitirDesdeEspera();

        return procesada;
    }

    /**
     * R3 — Busca una tarea ya procesada por su id en el historial (AVL, O(log n)).
     */
    @Override
    public Tarea buscarTareaProcesada(int id) {
        return historial.buscar(new CriterioBusquedaId(id));
    }

    /**
     * R4 — Cancela una tarea pendiente (no procesada) por su id.
     * Busca primero en colaCriticas, luego en colaNormales, luego en colaEspera.
     */
    @Override
    public Tarea cancelarTarea(int id) {
        // Buscar en cola de críticas
        Tarea cancelada = colaCriticas.eliminarPorId(id);
        if (cancelada != null) {
            admitirDesdeEspera();
            return cancelada;
        }

        // Buscar en cola de normales
        cancelada = colaNormales.eliminarPorId(id);
        if (cancelada != null) {
            admitirDesdeEspera();
            return cancelada;
        }

        // Buscar en cola de espera
        cancelada = colaEspera.eliminarPorId(id);
        return cancelada;
    }


    // metodos para testing
    public int getCantidadPendientesTotal() {
        return colaCriticas.tamanio() + colaNormales.tamanio();
    }

    public int getCantidadCriticasPendientes() {
        return colaCriticas.tamanio();
    }

    public int getCantidadEnEspera() {
        return colaEspera.tamanio();
    }

    public int getCantidadHistorial() {
        return historial.cantidadNodos();
    }

    // Verifica si una tarea puede admitirse inmediatamente al pool de pendientes.
    private boolean puedeAdmitir(Tarea tarea) {
        int totalPendientes = getCantidadPendientesTotal();
        if (totalPendientes >= MAX_PENDIENTES_TOTAL) return false;
        if (esCritica(tarea) && colaCriticas.tamanio() >= MAX_CRITICAS_PENDIENTES) return false;
        return true;
    }

    // 
    private void admitirTarea(Tarea tarea) {
        if (esCritica(tarea)) {
            colaCriticas.encolar(tarea);
        } else {
            colaNormales.encolar(tarea);
        }
    }

    // Intenta admitir tareas desde la cola de espera al pool de pendientes
    private void admitirDesdeEspera() {
        // Recorremos la cola de espera en orden e intentamos admitir
        // Usamos un nodo auxiliar para recorrer sin modificar la cola directamente
        boolean admitioAlguna = true;
        while (admitioAlguna && !colaEspera.esVacia()) {
            admitioAlguna = false;
            // Intentamos admitir la primera tarea que pueda entrar
            Tarea candidata = colaEspera.buscarPrimeroAdmisible(this);
            if (candidata != null) {
                colaEspera.eliminarPorId(candidata.getId());
                admitirTarea(candidata);
                admitioAlguna = true;
            }
        }
    }

    /** Retorna true si la tarea es crítica (criticidad 1 o 2). */
    boolean esCritica(Tarea tarea) {
        return tarea.getCriticidad() <= 2;
    }

    /**
     * Criterio para insertar una Tarea en el AVL usando su id como clave.
     * Implementa Comparable<Tarea> para que el AVL la use como clave de ordenación.
     */
    static class CriterioPorId implements Comparable<Tarea> {
        private final Tarea tarea;

        CriterioPorId(Tarea tarea) {
            this.tarea = tarea;
        }

        @Override
        public int compareTo(Tarea otra) {
            return Integer.compare(tarea.getId(), otra.getId());
        }

        // El AVL castea este objeto a T (Tarea) gracias a comoT().
        // Para que eso funcione, necesitamos que este objeto sea una Tarea.
        // Solución: usar la Tarea directamente como Comparable<Tarea>.
    }

    /**
     * Criterio de búsqueda en el AVL por id (sin necesitar una instancia de Tarea).
     */
    static class CriterioBusquedaId implements Comparable<Tarea> {
        private final int id;

        CriterioBusquedaId(int id) {
            this.id = id;
        }

        @Override
        public int compareTo(Tarea tarea) {
            return Integer.compare(id, tarea.getId());
        }
    }

    /**
     * Min-heap de tareas (prioridad: criticidad ascendente, luego orden de llegada).
     * Implementado con arreglo de tamaño fijo.
     */
    static class MinHeapTareas {
        private Tarea[] datos;
        private int tamanio;

        MinHeapTareas(int capacidad) {
            datos = new Tarea[capacidad + 1];
            tamanio = 0;
        }

        void encolar(Tarea tarea) {
            if (tamanio >= datos.length - 1) throw new IllegalStateException("Heap lleno");
            tamanio++;
            datos[tamanio] = tarea;
            subirUltimo();
        }

        Tarea desencolar() {
            if (tamanio == 0) return null;
            Tarea min = datos[1];
            datos[1] = datos[tamanio];
            datos[tamanio] = null;
            tamanio--;
            if (tamanio > 0) bajarRaiz();
            return min;
        }

        /** Elimina una tarea por id del heap. Retorna la tarea o null si no existe. */
        Tarea eliminarPorId(int id) {
            int pos = -1;
            for (int i = 1; i <= tamanio; i++) {
                if (datos[i].getId() == id) {
                    pos = i;
                    break;
                }
            }
            if (pos == -1) return null;
            Tarea encontrada = datos[pos];
            datos[pos] = datos[tamanio];
            datos[tamanio] = null;
            tamanio--;
            if (pos <= tamanio) {
                subirDesde(pos);
                bajarDesde(pos);
            }
            return encontrada;
        }

        int tamanio() { return tamanio; }

        boolean esVacia() { return tamanio == 0; }

        private void subirUltimo() {
            subirDesde(tamanio);
        }

        private void subirDesde(int i) {
            while (i > 1 && menor(datos[i], datos[i / 2])) {
                intercambiar(i, i / 2);
                i = i / 2;
            }
        }

        private void bajarRaiz() {
            bajarDesde(1);
        }

        private void bajarDesde(int i) {
            while (true) {
                int menor = i;
                int izq = 2 * i;
                int der = 2 * i + 1;
                if (izq <= tamanio && menor(datos[izq], datos[menor])) menor = izq;
                if (der <= tamanio && menor(datos[der], datos[menor])) menor = der;
                if (menor == i) break;
                intercambiar(i, menor);
                i = menor;
            }
        }

        private boolean menor(Tarea a, Tarea b) {
            return a.compareTo(b) < 0;
        }

        private void intercambiar(int i, int j) {
            Tarea tmp = datos[i];
            datos[i] = datos[j];
            datos[j] = tmp;
        }
    }

    /**
     * Cola FIFO de tareas implementada con nodos enlazados.
     */
    static class ColaTareas {
        private Nodo frente;
        private Nodo fin;
        private int tamanio;

        void encolar(Tarea tarea) {
            Nodo nuevo = new Nodo(tarea);
            if (fin == null) {
                frente = nuevo;
                fin = nuevo;
            } else {
                fin.siguiente = nuevo;
                fin = nuevo;
            }
            tamanio++;
        }

        Tarea desencolar() {
            if (frente == null) return null;
            Tarea dato = frente.tarea;
            frente = frente.siguiente;
            if (frente == null) fin = null;
            tamanio--;
            return dato;
        }

        /** Elimina la primera tarea con el id dado. Retorna la tarea o null. */
        Tarea eliminarPorId(int id) {
            if (frente == null) return null;
            // Caso especial: es el primero
            if (frente.tarea.getId() == id) {
                return desencolar();
            }
            Nodo anterior = frente;
            Nodo actual = frente.siguiente;
            while (actual != null) {
                if (actual.tarea.getId() == id) {
                    anterior.siguiente = actual.siguiente;
                    if (actual == fin) fin = anterior;
                    tamanio--;
                    return actual.tarea;
                }
                anterior = actual;
                actual = actual.siguiente;
            }
            return null;
        }

        /**
         * Busca la primera tarea en la cola que el sistema pueda admitir.
         * Mantiene el orden FIFO de la cola de espera.
         */
        Tarea buscarPrimeroAdmisible(SistemaGestionImpl sistema) {
            Nodo actual = frente;
            while (actual != null) {
                if (sistema.puedeAdmitir(actual.tarea)) {
                    return actual.tarea;
                }
                actual = actual.siguiente;
            }
            return null;
        }

        int tamanio() { return tamanio; }

        boolean esVacia() { return frente == null; }

        static class Nodo {
            Tarea tarea;
            Nodo siguiente;

            Nodo(Tarea tarea) {
                this.tarea = tarea;
            }
        }
    }
}
