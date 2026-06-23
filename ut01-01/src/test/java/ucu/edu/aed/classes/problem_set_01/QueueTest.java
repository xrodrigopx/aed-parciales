package ucu.edu.aed.classes.problem_set_01;

import org.junit.Before;
import org.junit.Test;

import ucu.edu.aed.classes.Queue;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

// ej 28 - probando que la cola ande de vuelo
public class QueueTest {

    private Queue<String> cola;

    @Before
    public void setUp() {
        cola = new Queue<>();
    }

    // ------------------------------------------------------------------
    // Estado inicial
    // ------------------------------------------------------------------

    @Test
    public void testColaRecienCreadaEstaVacia() {
        assertTrue("La cola nueva debe estar vacía", cola.esVacio());
        assertEquals(0, cola.tamaño());
    }

    // ------------------------------------------------------------------
    // poneEnCola / frente
    // ------------------------------------------------------------------

    @Test
    public void testPoneEnColaUnElemento() {
        cola.poneEnCola("primero");
        assertFalse(cola.esVacio());
        assertEquals(1, cola.tamaño());
        assertEquals("primero", cola.frente());
    }

    @Test
    public void testPoneEnColaMantieneFIFO() {
        cola.poneEnCola("A");
        cola.poneEnCola("B");
        cola.poneEnCola("C");
        // El primer ingresado (A) debe ser el frente
        assertEquals("A", cola.frente());
        assertEquals(3, cola.tamaño());
    }

    @Test
    public void testFrenteNoEliminaElemento() {
        cola.poneEnCola("X");
        cola.frente();
        assertEquals(1, cola.tamaño());
    }

    @Test(expected = NoSuchElementException.class)
    public void testFrenteEnColaVaciaLanzaExcepcion() {
        cola.frente();
    }

    @Test
    public void testPoneEnColaDevuelveTrue() {
        assertTrue(cola.poneEnCola("elemento"));
    }

    // ------------------------------------------------------------------
    // quitaDeCola
    // ------------------------------------------------------------------

    @Test
    public void testQuitaDeColaDevuelvePrimeroIngresado() {
        cola.poneEnCola("uno");
        cola.poneEnCola("dos");
        cola.poneEnCola("tres");
        assertEquals("uno",  cola.quitaDeCola());
        assertEquals("dos",  cola.quitaDeCola());
        assertEquals("tres", cola.quitaDeCola());
        assertTrue(cola.esVacio());
    }

    @Test
    public void testQuitaDeColaDecrementaTamaño() {
        cola.poneEnCola("a");
        cola.poneEnCola("b");
        cola.quitaDeCola();
        assertEquals(1, cola.tamaño());
    }

    @Test(expected = NoSuchElementException.class)
    public void testQuitaDeColaVaciaLanzaExcepcion() {
        cola.quitaDeCola();
    }

    // ------------------------------------------------------------------
    // vaciar
    // ------------------------------------------------------------------

    @Test
    public void testVaciarDejaCola() {
        cola.poneEnCola("x");
        cola.poneEnCola("y");
        cola.vaciar();
        assertTrue(cola.esVacio());
        assertEquals(0, cola.tamaño());
    }

    // ------------------------------------------------------------------
    // contiene / buscar
    // ------------------------------------------------------------------

    @Test
    public void testContieneEncuentraElemento() {
        cola.poneEnCola("hola");
        assertTrue(cola.contiene("hola"));
        assertFalse(cola.contiene("adios"));
    }

    @Test
    public void testBuscarPorLongitud() {
        cola.poneEnCola("ab");
        cola.poneEnCola("abcde");
        String encontrado = cola.buscar(new java.util.function.Predicate<String>() {
            public boolean test(String s) {
                return s.length() > 3;
            }
        });
        assertNotNull(encontrado);
        assertEquals("abcde", encontrado);
    }
}
