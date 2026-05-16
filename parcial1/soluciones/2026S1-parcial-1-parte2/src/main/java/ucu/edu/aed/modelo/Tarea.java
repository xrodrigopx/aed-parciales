
package ucu.edu.aed.modelo;

/**
 * Representa una tarea operativa que recibe la nave para ser procesada.
 * Criticidad 1 es la más crítica, 4 la menos crítica.
 */
public class Tarea implements Comparable<Tarea> {

    private final int id;
    private final String descripcion;
    private final int criticidad;
    // orden de llegada para desempate FIFO
    private int ordenLlegada;

    public Tarea(int id, String descripcion, int criticidad) {
        if (id <= 0) throw new IllegalArgumentException("El id debe ser positivo");
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new IllegalArgumentException("La descripcion no puede ser nula o vacia");
        }
        if (criticidad < 1 || criticidad > 4) {
            throw new IllegalArgumentException("La criticidad debe estar entre 1 y 4");
        }
        this.id = id;
        this.descripcion = descripcion;
        this.criticidad = criticidad;
        this.ordenLlegada = 0;
    }

    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getCriticidad() {
        return criticidad;
    }

    public int getOrdenLlegada() {
        return ordenLlegada;
    }

    public void setOrdenLlegada(int ordenLlegada) {
        this.ordenLlegada = ordenLlegada;
    }

    /**
     * Ordena por criticidad ascendente (1 primero), luego por orden de llegada (FIFO).
     */
    @Override
    public int compareTo(Tarea otra) {
        if (this.criticidad != otra.criticidad) {
            return Integer.compare(this.criticidad, otra.criticidad);
        }
        return Integer.compare(this.ordenLlegada, otra.ordenLlegada);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Tarea)) return false;
        Tarea otra = (Tarea) obj;
        return this.id == otra.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    @Override
    public String toString() {
        return "Tarea{id=" + id + ", descripcion='" + descripcion + "', criticidad=" + criticidad + "}";
    }
}
