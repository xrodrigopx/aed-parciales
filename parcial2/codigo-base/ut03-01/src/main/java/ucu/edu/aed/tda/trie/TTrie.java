package ucu.edu.aed.tda.trie;


import java.util.List;
import java.util.function.Consumer;

public interface TTrie<T> {

    void recorrer(Consumer<Entry<T>> consumer);

    /**
     * Retorna
     * -1 si no se encuentra "palabra" en el trie
     * 1 si existe y es una palabra completa
     * 0 si existe y NO es una palabra completa
     */
    Entry<T> buscar(String palabra);

    /**
     * retorna true si se agregó la palabra
     */
    boolean insertar(String palabra, T dato);

    /**
     * retorna todas las palabras en el trie que comienzan con prefijo
     */
    List<Entry<T>> predecir(String prefijo);

    /**
     * Retorna true si la palabra existía y fue eliminada.
     * Solo marca esPalabra=false; no borra nodos que tengan hijos activos.
     */
    boolean eliminar(String palabra);

}
