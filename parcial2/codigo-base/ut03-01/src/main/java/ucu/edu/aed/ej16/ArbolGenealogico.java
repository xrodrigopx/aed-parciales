package ucu.edu.aed.ej16;

import java.util.ArrayList;
import java.util.List;

public class ArbolGenealogico {

    private NodoArbolGenealogico raiz;

    public ArbolGenealogico(Persona raiz) {
        this.raiz = new NodoArbolGenealogico(raiz);
    }

    public NodoArbolGenealogico getRaiz() {
        return raiz;
    }

    // Agrega un hijo a la persona con el nombre dado. Retorna false si el padre no existe.
    public boolean agregarHijo(String nombrePadre, Persona hijo) {
        NodoArbolGenealogico nodoPadre = buscarNodo(raiz, nombrePadre);
        if (nodoPadre == null) {
            return false;
        }
        nodoPadre.agregarHijo(new NodoArbolGenealogico(hijo));
        return true;
    }

    // Retorna todos los descendientes de la persona con ese nombre (sin incluirla a ella).
    public List<Persona> listarDescendientes(String nombre) {
        List<Persona> resultado = new ArrayList<>();
        NodoArbolGenealogico nodo = buscarNodo(raiz, nombre);
        if (nodo == null) {
            return resultado;
        }
        recolectarTodos(nodo, resultado, false);
        return resultado;
    }

    // Retorna la altura del árbol completo.
    public int altura() {
        if (raiz == null) {
            return -1;
        }
        return calcularAltura(raiz);
    }

    // Retorna la cantidad total de personas en el árbol.
    public int cantidadPersonas() {
        if (raiz == null) {
            return 0;
        }
        return contarNodos(raiz);
    }

    // Retorna las personas en la generación indicada (raíz = nivel 0).
    public List<Persona> obtenerGeneracion(int nivel) {
        List<Persona> resultado = new ArrayList<>();
        if (raiz == null) {
            return resultado;
        }
        if (nivel < 0) {
            return resultado;
        }
        recolectarGeneracion(raiz, nivel, 0, resultado);
        return resultado;
    }

    // Retorna el ancestro común más cercano entre dos personas. Null si no existe.
    public Persona ancestroComun(String nombreA, String nombreB) {
        List<NodoArbolGenealogico> caminoA = new ArrayList<>();
        List<NodoArbolGenealogico> caminoB = new ArrayList<>();

        boolean encontroA = buscarCamino(raiz, nombreA, caminoA);
        boolean encontroB = buscarCamino(raiz, nombreB, caminoB);

        if (!encontroA) {
            return null;
        }
        if (!encontroB) {
            return null;
        }

        // El último nodo en común entre los dos caminos es el LCA
        NodoArbolGenealogico ancestro = null;
        int limiteA = caminoA.size();
        int limiteB = caminoB.size();
        int limite = limiteA < limiteB ? limiteA : limiteB;

        for (int i = 0; i < limite; i++) {
            if (caminoA.get(i) == caminoB.get(i)) {
                ancestro = caminoA.get(i);
            } else {
                break;
            }
        }

        if (ancestro == null) {
            return null;
        }
        return ancestro.getPersona();
    }

    // Retorna true si posibleDescendiente está en el subárbol de ancestro.
    public boolean esDescendiente(String posibleDescendiente, String nombreAncestro) {
        NodoArbolGenealogico nodoAncestro = buscarNodo(raiz, nombreAncestro);
        if (nodoAncestro == null) {
            return false;
        }
        for (NodoArbolGenealogico hijo : nodoAncestro.getHijos()) {
            if (buscarNodo(hijo, posibleDescendiente) != null) {
                return true;
            }
        }
        return false;
    }

    // --- métodos privados auxiliares ---

    private NodoArbolGenealogico buscarNodo(NodoArbolGenealogico nodo, String nombre) {
        if (nodo == null) {
            return null;
        }
        if (nombre == null) {
            return null;
        }
        if (nodo.getPersona().getNombre().equals(nombre)) {
            return nodo;
        }
        for (NodoArbolGenealogico hijo : nodo.getHijos()) {
            NodoArbolGenealogico encontrado = buscarNodo(hijo, nombre);
            if (encontrado != null) {
                return encontrado;
            }
        }
        return null;
    }

    private void recolectarTodos(NodoArbolGenealogico nodo, List<Persona> lista, boolean incluirNodo) {
        if (incluirNodo) {
            lista.add(nodo.getPersona());
        }
        for (NodoArbolGenealogico hijo : nodo.getHijos()) {
            recolectarTodos(hijo, lista, true);
        }
    }

    private int calcularAltura(NodoArbolGenealogico nodo) {
        if (!nodo.tieneHijos()) {
            return 0;
        }
        int maxAltura = 0;
        for (NodoArbolGenealogico hijo : nodo.getHijos()) {
            int alturaHijo = calcularAltura(hijo);
            if (alturaHijo > maxAltura) {
                maxAltura = alturaHijo;
            }
        }
        return maxAltura + 1;
    }

    private int contarNodos(NodoArbolGenealogico nodo) {
        int total = 1;
        for (NodoArbolGenealogico hijo : nodo.getHijos()) {
            total += contarNodos(hijo);
        }
        return total;
    }

    private void recolectarGeneracion(NodoArbolGenealogico nodo, int nivelBuscado, int nivelActual, List<Persona> lista) {
        if (nivelActual == nivelBuscado) {
            lista.add(nodo.getPersona());
            return;
        }
        for (NodoArbolGenealogico hijo : nodo.getHijos()) {
            recolectarGeneracion(hijo, nivelBuscado, nivelActual + 1, lista);
        }
    }

    private boolean buscarCamino(NodoArbolGenealogico nodo, String nombre, List<NodoArbolGenealogico> camino) {
        if (nodo == null) {
            return false;
        }
        camino.add(nodo);
        if (nodo.getPersona().getNombre().equals(nombre)) {
            return true;
        }
        for (NodoArbolGenealogico hijo : nodo.getHijos()) {
            if (buscarCamino(hijo, nombre, camino)) {
                return true;
            }
        }
        camino.remove(camino.size() - 1);
        return false;
    }
}
