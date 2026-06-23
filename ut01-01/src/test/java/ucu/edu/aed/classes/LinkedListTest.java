package ucu.edu.aed.classes;

import org.junit.Before;
import org.junit.Test;

import java.util.Comparator;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

// tests para la lista enlazada base — cubre todos los metodos del TDALista
public class LinkedListTest {

    private LinkedList<Integer> lista;

    @Before
    public void setUp() {
        lista = new LinkedList<>();
    }

    // ------------------------------------------------------------------
    // Estado inicial
    // ------------------------------------------------------------------

    @Test
    public void testListaNuevaEstaVacia() {
        assertTrue(lista.esVacio());
        assertEquals(0, lista.tamaño());
    }

    // ------------------------------------------------------------------
    // agregar(T) — al final
    // ------------------------------------------------------------------

    @Test
    public void testAgregarUnElemento() {
        lista.agregar(10);
        assertFalse(lista.esVacio());
        assertEquals(1, lista.tamaño());
        assertEquals(Integer.valueOf(10), lista.obtener(0));
    }

    @Test
    public void testAgregarVariosMantieneOrden() {
        lista.agregar(1);
        lista.agregar(2);
        lista.agregar(3);
        assertEquals(Integer.valueOf(1), lista.obtener(0));
        assertEquals(Integer.valueOf(2), lista.obtener(1));
        assertEquals(Integer.valueOf(3), lista.obtener(2));
        assertEquals(3, lista.tamaño());
    }

    // ------------------------------------------------------------------
    // agregar(int, T) — por indice
    // ------------------------------------------------------------------

    @Test
    public void testAgregarEnIndice0Desplaza() {
        lista.agregar(2);
        lista.agregar(3);
        lista.agregar(0, 1);
        assertEquals(Integer.valueOf(1), lista.obtener(0));
        assertEquals(Integer.valueOf(2), lista.obtener(1));
        assertEquals(3, lista.tamaño());
    }

    @Test
    public void testAgregarEnIndiceIntermedio() {
        lista.agregar(1);
        lista.agregar(3);
        lista.agregar(1, 2);
        assertEquals(Integer.valueOf(1), lista.obtener(0));
        assertEquals(Integer.valueOf(2), lista.obtener(1));
        assertEquals(Integer.valueOf(3), lista.obtener(2));
    }

    @Test
    public void testAgregarEnUltimoIndiceEsEquivalenteAlFinal() {
        lista.agregar(1);
        lista.agregar(2);
        lista.agregar(lista.tamaño(), 3);
        assertEquals(Integer.valueOf(3), lista.obtener(2));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testAgregarIndiceNegativoLanzaExcepcion() {
        lista.agregar(-1, 5);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testAgregarIndiceMayorAlTamañoLanzaExcepcion() {
        lista.agregar(10, 5);
    }

    // ------------------------------------------------------------------
    // obtener
    // ------------------------------------------------------------------

    @Test(expected = IndexOutOfBoundsException.class)
    public void testObtenerEnListaVaciaLanzaExcepcion() {
        lista.obtener(0);
    }

    // ------------------------------------------------------------------
    // remover(int)
    // ------------------------------------------------------------------

    @Test
    public void testRemoverCabeza() {
        lista.agregar(1);
        lista.agregar(2);
        assertEquals(Integer.valueOf(1), lista.remover(0));
        assertEquals(1, lista.tamaño());
        assertEquals(Integer.valueOf(2), lista.obtener(0));
    }

    @Test
    public void testRemoverCola() {
        lista.agregar(1);
        lista.agregar(2);
        lista.agregar(3);
        assertEquals(Integer.valueOf(3), lista.remover(2));
        assertEquals(2, lista.tamaño());
    }

    @Test
    public void testRemoverIntermedio() {
        lista.agregar(1);
        lista.agregar(2);
        lista.agregar(3);
        assertEquals(Integer.valueOf(2), lista.remover(1));
        assertEquals(Integer.valueOf(1), lista.obtener(0));
        assertEquals(Integer.valueOf(3), lista.obtener(1));
    }

    @Test
    public void testRemoverUnicoElementoDejalistaVacia() {
        lista.agregar(99);
        lista.remover(0);
        assertTrue(lista.esVacio());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoverIndiceFueraDeRango() {
        lista.agregar(1);
        lista.remover(5);
    }

    // ------------------------------------------------------------------
    // remover(T)
    // ------------------------------------------------------------------

    @Test
    public void testRemoverPorValorEncuentraYBorra() {
        lista.agregar(10);
        lista.agregar(20);
        lista.agregar(30);
        assertTrue(lista.remover(Integer.valueOf(20)));
        assertEquals(2, lista.tamaño());
        assertFalse(lista.contiene(20));
    }

    @Test
    public void testRemoverPorValorNoExistenteDevuelveFalse() {
        lista.agregar(1);
        assertFalse(lista.remover(Integer.valueOf(999)));
    }

    @Test
    public void testRemoverPorValorCabeza() {
        lista.agregar(1);
        lista.agregar(2);
        assertTrue(lista.remover(Integer.valueOf(1)));
        assertEquals(Integer.valueOf(2), lista.obtener(0));
    }

    // ------------------------------------------------------------------
    // contiene / indiceDe
    // ------------------------------------------------------------------

    @Test
    public void testContieneEncuentraElemento() {
        lista.agregar(42);
        assertTrue(lista.contiene(42));
        assertFalse(lista.contiene(0));
    }

    @Test
    public void testIndiceDeRetornaIndiceCorrector() {
        lista.agregar(10);
        lista.agregar(20);
        lista.agregar(30);
        assertEquals(1, lista.indiceDe(20));
        assertEquals(-1, lista.indiceDe(999));
    }

    // ------------------------------------------------------------------
    // buscar
    // ------------------------------------------------------------------

    @Test
    public void testBuscarPorPredicado() {
        lista.agregar(3);
        lista.agregar(7);
        lista.agregar(11);
        Integer encontrado = lista.buscar(new java.util.function.Predicate<Integer>() {
            public boolean test(Integer n) {
                return n > 5;
            }
        });
        assertEquals(Integer.valueOf(7), encontrado);
    }

    @Test
    public void testBuscarEnListaVaciaDevuelveNull() {
        assertNull(lista.buscar(new java.util.function.Predicate<Integer>() {
            public boolean test(Integer n) {
                return true;
            }
        }));
    }

    @Test
    public void testBuscarSinCoincidenciaDevuelveNull() {
        lista.agregar(1);
        lista.agregar(2);
        assertNull(lista.buscar(new java.util.function.Predicate<Integer>() {
            public boolean test(Integer n) {
                return n > 100;
            }
        }));
    }

    // ------------------------------------------------------------------
    // ordenar
    // ------------------------------------------------------------------

    @Test
    public void testOrdenarAscendente() {
        lista.agregar(3);
        lista.agregar(1);
        lista.agregar(2);
        ucu.edu.aed.tda.TDALista<Integer> ordenada = lista.ordenar(Comparator.naturalOrder());
        assertEquals(Integer.valueOf(1), ordenada.obtener(0));
        assertEquals(Integer.valueOf(2), ordenada.obtener(1));
        assertEquals(Integer.valueOf(3), ordenada.obtener(2));
    }

    @Test
    public void testOrdenarNoModificaOriginal() {
        lista.agregar(3);
        lista.agregar(1);
        lista.agregar(2);
        lista.ordenar(Comparator.naturalOrder());
        // la lista original debe seguir en el orden original
        assertEquals(Integer.valueOf(3), lista.obtener(0));
    }

    @Test
    public void testOrdenarListaVaciaDevuelveListaVacia() {
        ucu.edu.aed.tda.TDALista<Integer> resultado = lista.ordenar(Comparator.naturalOrder());
        assertTrue(resultado.esVacio());
    }

    // ------------------------------------------------------------------
    // vaciar
    // ------------------------------------------------------------------

    @Test
    public void testVaciarDejalistaVacia() {
        lista.agregar(1);
        lista.agregar(2);
        lista.vaciar();
        assertTrue(lista.esVacio());
        assertEquals(0, lista.tamaño());
    }

    @Test
    public void testVaciarPermiteAgregarDeNuevo() {
        lista.agregar(1);
        lista.vaciar();
        lista.agregar(99);
        assertEquals(Integer.valueOf(99), lista.obtener(0));
    }
}
