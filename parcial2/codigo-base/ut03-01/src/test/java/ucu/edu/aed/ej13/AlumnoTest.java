package ucu.edu.aed.ej13;

import junit.framework.TestCase;
import java.util.HashSet;

public class AlumnoTest extends TestCase {

    // --- equals ---

    public void testDosAlumnosConMismosDataSonIguales() {
        Alumno a1 = new Alumno(1, "Juan Perez", "juan@ucudal.edu.uy");
        Alumno a2 = new Alumno(1, "Juan Perez", "juan@ucudal.edu.uy");
        assertTrue(a1.equals(a2));
    }

    public void testAlumnoConDistintoIdNoEsIgual() {
        Alumno a1 = new Alumno(1, "Juan Perez", "juan@ucudal.edu.uy");
        Alumno a2 = new Alumno(2, "Juan Perez", "juan@ucudal.edu.uy");
        assertFalse(a1.equals(a2));
    }

    public void testAlumnoConDistintoNombreNoEsIgual() {
        Alumno a1 = new Alumno(1, "Juan Perez", "juan@ucudal.edu.uy");
        Alumno a2 = new Alumno(1, "Maria Lopez", "juan@ucudal.edu.uy");
        assertFalse(a1.equals(a2));
    }

    public void testAlumnoConDistintoEmailNoEsIgual() {
        Alumno a1 = new Alumno(1, "Juan Perez", "juan@ucudal.edu.uy");
        Alumno a2 = new Alumno(1, "Juan Perez", "otro@ucudal.edu.uy");
        assertFalse(a1.equals(a2));
    }

    public void testEqualsConNullRetornaFalse() {
        Alumno a1 = new Alumno(1, "Juan Perez", "juan@ucudal.edu.uy");
        assertFalse(a1.equals(null));
    }

    public void testEqualsConOtroTipoRetornaFalse() {
        Alumno a1 = new Alumno(1, "Juan Perez", "juan@ucudal.edu.uy");
        assertFalse(a1.equals("un string cualquiera"));
    }

    public void testEqualsEsReflexivo() {
        Alumno a1 = new Alumno(1, "Juan Perez", "juan@ucudal.edu.uy");
        assertTrue(a1.equals(a1));
    }

    public void testEqualsEsSimetrico() {
        Alumno a1 = new Alumno(1, "Juan Perez", "juan@ucudal.edu.uy");
        Alumno a2 = new Alumno(1, "Juan Perez", "juan@ucudal.edu.uy");
        assertTrue(a1.equals(a2));
        assertTrue(a2.equals(a1));
    }

    // --- hashCode ---

    public void testAlumnosIgualesTienenMismoHashCode() {
        Alumno a1 = new Alumno(1, "Juan Perez", "juan@ucudal.edu.uy");
        Alumno a2 = new Alumno(1, "Juan Perez", "juan@ucudal.edu.uy");
        assertEquals(a1.hashCode(), a2.hashCode());
    }

    public void testHashCodeEsConsistente() {
        Alumno a1 = new Alumno(1, "Juan Perez", "juan@ucudal.edu.uy");
        int primerHash = a1.hashCode();
        int segundoHash = a1.hashCode();
        assertEquals(primerHash, segundoHash);
    }

    // --- integración con HashSet ---

    public void testHashSetTrataDosAlumnosIgualesComoUno() {
        Alumno a1 = new Alumno(1, "Juan Perez", "juan@ucudal.edu.uy");
        Alumno a2 = new Alumno(1, "Juan Perez", "juan@ucudal.edu.uy");

        HashSet<Alumno> set = new HashSet<>();
        set.add(a1);
        set.add(a2);

        assertEquals(1, set.size());
    }

    public void testHashSetTrataDosAlumnosDistintosComoDosSeparados() {
        Alumno a1 = new Alumno(1, "Juan Perez", "juan@ucudal.edu.uy");
        Alumno a2 = new Alumno(2, "Maria Lopez", "maria@ucudal.edu.uy");

        HashSet<Alumno> set = new HashSet<>();
        set.add(a1);
        set.add(a2);

        assertEquals(2, set.size());
    }
}
