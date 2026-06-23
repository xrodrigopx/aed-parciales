package ucu.edu.aed.classes.problem_set_01;

import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

// tests para la cola circular con array (ejercicio 27)
public class CircularQueueTest {

    private CircularQueue<Integer> cola;

    @Before
    public void setUp() {
        cola = new CircularQueue<>(5);
    }

    // ------------------------------------------------------------------
    // Estado inicial
    // ------------------------------------------------------------------

    @Test
    public void testColaRecienCreadaEstaVacia() {
        assertTrue(cola.estaVacia());
        assertFalse(cola.estaLlena());
        assertEquals(0, cola.getCantidad());
    }

    @Test
    public void testCapacidadInvalidaLanzaExcepcion() {
        try {
            new CircularQueue<>(0);
            fail("Debia lanzar IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // esperado
        }
    }

    // ------------------------------------------------------------------
    // poneEnCola / frente
    // ------------------------------------------------------------------

    @Test
    public void testPoneUnElementoYLoVeEnFrente() {
        cola.poneEnCola(10);
        assertFalse(cola.estaVacia());
        assertEquals(Integer.valueOf(10), cola.frente());
        assertEquals(1, cola.getCantidad());
    }

    @Test
    public void testPoneVariosMantieneOrdenFIFO() {
        cola.poneEnCola(1);
        cola.poneEnCola(2);
        cola.poneEnCola(3);
        assertEquals(Integer.valueOf(1), cola.frente());
        assertEquals(3, cola.getCantidad());
    }

    @Test
    public void testFrenteNoEliminaElemento() {
        cola.poneEnCola(42);
        cola.frente();
        assertEquals(1, cola.getCantidad());
    }

    @Test(expected = NoSuchElementException.class)
    public void testFrenteEnVaciaLanzaExcepcion() {
        cola.frente();
    }

    @Test(expected = IllegalStateException.class)
    public void testPoneEnColaLlenaLanzaExcepcion() {
        for (int i = 0; i < 5; i++) {
            cola.poneEnCola(i);
        }
        cola.poneEnCola(99); // debe lanzar
    }

    // ------------------------------------------------------------------
    // quitaDeCola
    // ------------------------------------------------------------------

    @Test
    public void testQuitaDeColaDevuelvePrimeroEnOrdenFIFO() {
        cola.poneEnCola(1);
        cola.poneEnCola(2);
        cola.poneEnCola(3);
        assertEquals(Integer.valueOf(1), cola.quitaDeCola());
        assertEquals(Integer.valueOf(2), cola.quitaDeCola());
        assertEquals(Integer.valueOf(3), cola.quitaDeCola());
        assertTrue(cola.estaVacia());
    }

    @Test
    public void testQuitaDeColaDecrementaCantidad() {
        cola.poneEnCola(5);
        cola.poneEnCola(6);
        cola.quitaDeCola();
        assertEquals(1, cola.getCantidad());
    }

    @Test(expected = NoSuchElementException.class)
    public void testQuitaDeColaVaciaLanzaExcepcion() {
        cola.quitaDeCola();
    }

    // ------------------------------------------------------------------
    // comportamiento circular
    // ------------------------------------------------------------------

    @Test
    public void testComportamientoCircular() {
        // llena la cola
        for (int i = 0; i < 5; i++) {
            cola.poneEnCola(i);
        }
        // saca dos del frente para liberar espacio
        cola.quitaDeCola();
        cola.quitaDeCola();
        // ahora puede meter dos mas usando los slots liberados al inicio del array
        cola.poneEnCola(10);
        cola.poneEnCola(11);
        assertEquals(5, cola.getCantidad());
        // el frente debe ser el 2 (primer elemento que quedo)
        assertEquals(Integer.valueOf(2), cola.frente());
    }

    @Test
    public void testLlenaVaciaYVuelveALlenar() {
        for (int i = 0; i < 5; i++) {
            cola.poneEnCola(i);
        }
        for (int i = 0; i < 5; i++) {
            cola.quitaDeCola();
        }
        assertTrue(cola.estaVacia());
        // puede volver a usarse sin problema
        cola.poneEnCola(99);
        assertEquals(Integer.valueOf(99), cola.frente());
    }

    // ------------------------------------------------------------------
    // estaLlena
    // ------------------------------------------------------------------

    @Test
    public void testEstaLlenaCuandoAlcanzaCapacidad() {
        for (int i = 0; i < 5; i++) {
            cola.poneEnCola(i);
        }
        assertTrue(cola.estaLlena());
        assertEquals(5, cola.getCapacidad());
    }
}
