package ucu.edu.aed.classes.problem_set_01;

import java.util.Objects;

// representacion de un alumno para usar en la lista 
public class Alumno {

    private final int cedula; // de 4 digitos
    private final String nombre;
    private final String apellido;

    public Alumno(int cedula, String nombre, String apellido) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public int getCedula() {
        return cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    // sobreescribo el equals para que dos alumnos sean iguales solo si tienen la misma cedula
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Alumno other = (Alumno) obj;
        return cedula == other.cedula;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cedula);
    }

    @Override
    public String toString() {
        return String.format("Alumno{cedula=%d, nombre='%s %s'}", cedula, nombre, apellido);
    }
}
