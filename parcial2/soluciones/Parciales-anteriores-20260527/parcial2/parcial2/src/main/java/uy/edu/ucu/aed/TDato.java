package uy.edu.ucu.aed;

public class TDato {
    private double valor;
    private int fecha;

    public TDato(double valor, int fecha) {
        this.valor = valor;
        this.fecha = fecha;
    }

    public double getValor() {
        return valor;
    }

    public int getFecha() {
        return fecha;
    }
}