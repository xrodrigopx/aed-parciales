package ucu.edu.aed.ej16;

public class Persona {

    private String nombre;
    private int anioNacimiento;

    public Persona(String nombre, int anioNacimiento) {
        this.nombre = nombre;
        this.anioNacimiento = anioNacimiento;
    }

    public String getNombre() {
        return nombre;
    }

    public int getAnioNacimiento() {
        return anioNacimiento;
    }

    @Override
    public String toString() {
        return nombre + " (" + anioNacimiento + ")";
    }
}
