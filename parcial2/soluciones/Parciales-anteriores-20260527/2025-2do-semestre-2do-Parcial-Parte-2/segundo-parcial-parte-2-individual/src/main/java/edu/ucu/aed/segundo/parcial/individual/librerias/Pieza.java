package edu.ucu.aed.segundo.parcial.individual.librerias;

import java.util.Objects;

/**
 * Representa una pieza del traje con un identificador
 */
public class Pieza implements Comparable<Pieza> {
    private final String identificador;

    public Pieza(String identificador) {
        this.identificador = identificador;
    }

    @Override
    public int compareTo(Pieza o) {
        return identificador.compareTo(o.identificador);
    }

    @Override
    public String toString() {
        return identificador;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Pieza pieza = (Pieza) o;
        return Objects.equals(identificador, pieza.identificador);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(identificador);
    }
}
