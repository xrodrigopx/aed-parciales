package ucu.edu.aed.classes.problem_set_01;

import org.junit.Before;
import org.junit.Test;

import ucu.edu.aed.classes.Stack;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

// ej 28 - testeo de la pila para que no se rompa nada
public class StackTest {

    private Stack<Integer> pila;

    @Before
    public void setUp() {
        pila = new Stack<>();
    }

    // ------------------------------------------------------------------
    // Estado inicial
    // ------------------------------------------------------------------

    @Test
    public void testPilaRecienCreadaEstaVacia() {
        assertTrue("La pila nueva debe estar vacía", pila.esVacio());
        assertEquals(0, pila.tamaño());
    }

    // ------------------------------------------------------------------
    // mete / tope
    // ------------------------------------------------------------------

    @Test
    public void testMeteUnElemento() {
        pila.mete(10);
        assertFalse(pila.esVacio());
        assertEquals(1, pila.tamaño());
        assertEquals(Integer.valueOf(10), pila.tope());
    }

    @Test
    public void testMeteMantieneLIFO() {
        pila.mete(1);
        pila.mete(2);
        pila.mete(3);
        // El último ingresado (3) debe aparecer en el tope
        assertEquals(Integer.valueOf(3), pila.tope());
        assertEquals(3, pila.tamaño());
    }

    @Test
    public void testTopeNoEliminaElemento() {
        pila.mete(5);
        pila.tope();
        assertEquals(1, pila.tamaño());
    }

    @Test(expected = NoSuchElementException.class)
    public void testTopeEnPilaVaciaLanzaExcepcion() {
        pila.tope();
    }

    // ------------------------------------------------------------------
    // saca
    // ------------------------------------------------------------------

    @Test
    public void testSacaDevuelveUltimoIngresado() {
        pila.mete(10);
        pila.mete(20);
        assertEquals(Integer.valueOf(20), pila.saca());
        assertEquals(Integer.valueOf(10), pila.saca());
        assertTrue(pila.esVacio());
    }

    @Test
    public void testSacaDecrementaTamaño() {
        pila.mete(7);
        pila.mete(8);
        pila.saca();
        assertEquals(1, pila.tamaño());
    }

    @Test(expected = NoSuchElementException.class)
    public void testSacaEnPilaVaciaLanzaExcepcion() {
        pila.saca();
    }

    // ------------------------------------------------------------------
    // vaciar
    // ------------------------------------------------------------------

    @Test
    public void testVaciarDejaPilaVacia() {
        pila.mete(1);
        pila.mete(2);
        pila.vaciar();
        assertTrue(pila.esVacio());
        assertEquals(0, pila.tamaño());
    }

    // ------------------------------------------------------------------
    // contiene / indiceDe / buscar
    // ------------------------------------------------------------------

    @Test
    public void testContieneEncuentraElemento() {
        pila.mete(100);
        assertTrue(pila.contiene(100));
        assertFalse(pila.contiene(999));
    }

    @Test
    public void testBuscarPorCriterio() {
        pila.mete(3);
        pila.mete(7);
        Integer encontrado = pila.buscar(new java.util.function.Predicate<Integer>() {
            public boolean test(Integer n) {
                return n > 5;
            }
        });
        assertNotNull(encontrado);
        assertEquals(Integer.valueOf(7), encontrado);
    }
}
