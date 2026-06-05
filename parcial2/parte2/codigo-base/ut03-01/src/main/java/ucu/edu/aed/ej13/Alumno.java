package ucu.edu.aed.ej13;

/*
 * Investigación sobre hashCode en Java:
 *
 * Object.hashCode()
 *   La implementación por defecto retorna un número basado en la dirección de
 *   memoria del objeto (identidad). Dos objetos distintos en memoria tendrán
 *   hashCodes distintos aunque tengan los mismos campos.
 *
 * Integer.hashCode()
 *   Retorna directamente el valor int del entero. Así Integer.valueOf(42).hashCode() == 42.
 *
 * String.hashCode()
 *   Usa la fórmula polinomial con base 31:
 *   h = s[0]*31^(n-1) + s[1]*31^(n-2) + ... + s[n-1]
 *   Por ejemplo "abc" = 'a'*31^2 + 'b'*31 + 'c' = 96354.
 *   El 31 se elige porque es primo y ayuda a distribuir bien los valores.
 *
 * Contrato que debe cumplir equals/hashCode:
 *   Si a.equals(b) es true, entonces a.hashCode() == b.hashCode() debe ser true.
 *   Lo contrario NO es obligatorio: dos objetos pueden tener el mismo hashCode
 *   sin ser iguales (eso es una colisión).
 */
public class Alumno {

    private int id;
    private String fullName;
    private String email;

    public Alumno(int id, String fullName, String email) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object otro) {
        if (this == otro) {
            return true;
        }
        if (otro == null) {
            return false;
        }
        if (!(otro instanceof Alumno)) {
            return false;
        }
        Alumno otroAlumno = (Alumno) otro;
        if (this.id != otroAlumno.id) {
            return false;
        }
        if (!this.fullName.equals(otroAlumno.fullName)) {
            return false;
        }
        if (!this.email.equals(otroAlumno.email)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        // Usamos el mismo polinomio que usa String internamente.
        // Combinamos los tres campos para que objetos iguales siempre
        // produzcan el mismo código.
        int resultado = 17;
        resultado = 31 * resultado + id;
        resultado = 31 * resultado + (fullName == null ? 0 : fullName.hashCode());
        resultado = 31 * resultado + (email == null ? 0 : email.hashCode());
        return resultado;
    }

    @Override
    public String toString() {
        return "Alumno{id=" + id + ", fullName='" + fullName + "', email='" + email + "'}";
    }
}
