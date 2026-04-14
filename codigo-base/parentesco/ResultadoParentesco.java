package uy.edu.ucu.aed.parcial1;

public class ResultadoParentesco {
    private int grados;
    private String tipoParentesco;

    public ResultadoParentesco(int grados, String tipoParentesco) {
        this.grados = grados;
        this.tipoParentesco = tipoParentesco;
    }

    public int getGrados() {
        return grados;
    }

    public String getTipoParentesco() {
        return tipoParentesco;
    }

    @Override
    public String toString() {
        return "Grados: " + grados + ", Tipo de Parentesco: " + tipoParentesco;
    }
}
