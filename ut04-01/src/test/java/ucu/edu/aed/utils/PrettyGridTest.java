package ucu.edu.aed.utils;

import junit.framework.TestCase;

public class PrettyGridTest extends TestCase {

    public void testPrintMatrixSinErrores() {
        // Prueba sencilla para garantizar que no lance excepción
        String[][] matrix = {
            {"A", "B"},
            {"C", "D"}
        };
        
        try {
            // Imprime algo básico, solo probamos que corra
            PrettyGrid.print(matrix, new PrettyGrid.CellFormat<String>() {
                @Override
                public String format(String value) {
                    return value;
                }
            }, false);
            assertTrue(true);
        } catch (Exception e) {
            fail("Falló PrettyGrid con excepción: " + e.getMessage());
        }
    }
}
