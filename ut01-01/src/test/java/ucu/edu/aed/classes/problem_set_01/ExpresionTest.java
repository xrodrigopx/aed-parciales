package ucu.edu.aed.classes.problem_set_01;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

// pruebas locas para ver que el codig de los corchetes ande bien 
// (ejercicio 26 completo probando casos q andan y casos turbios)
public class ExpresionTest {

    // ------------------------------------------------------------------
    // Casos bien formados
    // ------------------------------------------------------------------

    @Test
    public void testSecuenciaVaciaEsBienFormada() {
        // Pre: lista vacía → no hay llaves desequilibradas
        List<Character> entrada = chars("");
        assertTrue("La secuencia vacía debe ser bien formada", controlCorchetes(entrada));
    }

    @Test
    public void testUnParEquilibrado() {
        List<Character> entrada = chars("{}");
        assertTrue(controlCorchetes(entrada));
    }

    @Test
    public void testMultiplesParesAnidados() {
        // Caso del enunciado: {}{{}}
        List<Character> entrada = chars("{}{{}}");
        assertTrue("{}{{}} debe ser bien formada", controlCorchetes(entrada));
    }

    @Test
    public void testAnidamientoDeep() {
        List<Character> entrada = chars("{{{}}}");
        assertTrue(controlCorchetes(entrada));
    }

    @Test
    public void testCaracteresIntermediosIgnorados() {
        List<Character> entrada = chars("{a+b}");
        assertTrue("Los caracteres no-llave se ignoran", controlCorchetes(entrada));
    }

    @Test
    public void testSecuenciaConCodigoFuente() {
        // Simula una línea de código: if (x > 0) { return true; }
        List<Character> entrada = chars("if (x > 0) { return true; }");
        assertTrue(controlCorchetes(entrada));
    }

    // ------------------------------------------------------------------
    // Casos mal formados
    // ------------------------------------------------------------------

    @Test
    public void testMalFormadaDelEnunciado() {
        // Caso del enunciado: {{}{{}
        List<Character> entrada = chars("{{}{{}");
        assertFalse("{{}{{}  debe ser mal formada", controlCorchetes(entrada));
    }

    @Test
    public void testSoloCerrandoEsMalFormada() {
        List<Character> entrada = chars("}");
        assertFalse(controlCorchetes(entrada));
    }

    @Test
    public void testSoloAbriendo() {
        List<Character> entrada = chars("{");
        assertFalse(controlCorchetes(entrada));
    }

    @Test
    public void testOrdenInvertido() {
        List<Character> entrada = chars("}{");
        assertFalse(controlCorchetes(entrada));
    }

    @Test
    public void testMasAperturasQueCierres() {
        List<Character> entrada = chars("{{}");
        assertFalse(controlCorchetes(entrada));
    }

    @Test
    public void testMasCierresQueAperturas() {
        List<Character> entrada = chars("{}}");
        assertFalse(controlCorchetes(entrada));
    }

    // ------------------------------------------------------------------
    // Helpers
    // ------------------------------------------------------------------

    /** Convierte un String en lista de Character. */
    private List<Character> chars(String s) {
        Character[] arr = new Character[s.length()];
        for (int i = 0; i < s.length(); i++) arr[i] = s.charAt(i);
        return Arrays.asList(arr);
    }

    /** Invoca controlCorchetes a través de la clase Expresion. */
    private boolean controlCorchetes(List<Character> entrada) {
        Expresion exp = new Expresion(entrada);
        return exp.controlCorchetes(entrada);
    }
}
