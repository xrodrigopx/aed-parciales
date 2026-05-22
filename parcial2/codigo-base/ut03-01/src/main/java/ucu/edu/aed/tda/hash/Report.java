package ucu.edu.aed.tda.hash;

/**
 * Clase utilitaria para guardar reporte de las operaciones definidas en THash
 */
public class Report {
    /**
     * Cantidad de comparaciones realizadas en buscar o insertar de un THash
     */
    private int cantidadComparaciones = 0;

    public int getCantidadComparaciones() {
        return cantidadComparaciones;
    }

    public void setCantidadComparaciones(int cantidadComparaciones) {
        this.cantidadComparaciones = cantidadComparaciones;
    }
}
