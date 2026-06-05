package ucu.edu.aed.tda.grafo.main;

import junit.framework.TestCase;

public class MainEjerciciosTest extends TestCase {

    public void testEjecutarMainSinErrores() {
        try {
            // Verifica que los ejercicios con grafos hardcodeados funcionen sin caerse
            MainEjercicios.main(new String[]{});
            assertTrue(true);
        } catch (Exception e) {
            fail("MainEjercicios lanzó excepción: " + e.getMessage());
        }
    }
}
