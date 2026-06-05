package ucu.edu.aed.ej03;

import junit.framework.TestCase;

public class MainFloydTest extends TestCase {

    public void testEjecutarMainSinErrores() {
        try {
            // Verifica que no se rompa nada al correr el ejemplo
            MainFloyd.main(new String[]{});
            assertTrue(true);
        } catch (Exception e) {
            fail("MainFloyd lanzó excepción: " + e.getMessage());
        }
    }
}
