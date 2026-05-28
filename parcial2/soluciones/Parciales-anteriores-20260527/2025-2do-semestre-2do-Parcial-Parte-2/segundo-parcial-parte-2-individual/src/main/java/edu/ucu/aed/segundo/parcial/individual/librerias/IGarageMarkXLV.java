package edu.ucu.aed.segundo.parcial.individual.librerias;

import edu.ucu.aed.tda.grafo.IUndirectedGraph;

import java.util.Set;

/**
 * Representa el centro de reparación del traje MarkXLV
 */
public interface IGarageMarkXLV {
    /**
     *
     * @param grafo         Representa el traje Mark XLV}
     * @param pecho         Pieza principal del traje
     * @param piezasDañadas Conjunto de piezas dañados
     * @return Devuelve la cantidad de piezas que siguen unidas de forma sólida al Pecho, ignorando piezas dañadas
     */
    int tamañoTrajeFuncional(IUndirectedGraph<Pieza, Conector> grafo, Pieza pecho, Set<Pieza> piezasDañadas);


    /**
     *
     * @param grafo        Representa el traje Mark XLV}
     * @param pecho        Pieza principal del traje
     * @param piezaCrítica Pieza para chequear si es crítica o no
     * @return
     */
    boolean esPiezaCritica(IUndirectedGraph<Pieza, Conector> grafo, Pieza pecho, Pieza piezaCrítica);
}
