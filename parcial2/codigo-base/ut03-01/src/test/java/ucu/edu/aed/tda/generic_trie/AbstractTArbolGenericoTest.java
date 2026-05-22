package ucu.edu.aed.tda.generic_trie;

import junit.framework.TestCase;
import ucu.edu.aed.tda.generic_tree.TArbolGenerico;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractTArbolGenericoTest extends TestCase {
    protected abstract <T extends Comparable<T>> TArbolGenerico<T> crearArbol(T raiz);

    public void testBuscarRetornaRaiz() {
        TArbolGenerico<Integer> arbol = crearArbol(1);

        assertEquals(Integer.valueOf(1), arbol.buscar(1));
    }

    public void testAgregarHijoAlPadreExistente() {
        TArbolGenerico<Integer> arbol = crearArbol(1);

        assertTrue(arbol.agregarHijo(1, 2));
        assertTrue(arbol.agregarHijo(1, 3));

        assertEquals(Integer.valueOf(2), arbol.buscar(2));
        assertEquals(Integer.valueOf(3), arbol.buscar(3));
        assertEquals(2, arbol.grado(1));
    }

    public void testAgregarHijoConPadreInexistenteRetornaFalse() {
        TArbolGenerico<Integer> arbol = crearArbol(1);

        assertFalse(arbol.agregarHijo(99, 2));
        assertNull(arbol.buscar(2));
    }

    public void testAgregarHijoDuplicadoRetornaFalse() {
        TArbolGenerico<Integer> arbol = crearArbol(1);

        assertTrue(arbol.agregarHijo(1, 2));
        assertFalse(arbol.agregarHijo(1, 2));
    }

    public void testObtenerPadre() {
        TArbolGenerico<Integer> arbol = crearArbolDeEjemplo();

        assertNull(arbol.obtenerPadre(1));
        assertEquals(Integer.valueOf(1), arbol.obtenerPadre(2));
        assertEquals(Integer.valueOf(2), arbol.obtenerPadre(5));
        assertNull(arbol.obtenerPadre(99));
    }

    public void testGrado() {
        TArbolGenerico<Integer> arbol = crearArbolDeEjemplo();

        assertEquals(3, arbol.grado(1));
        assertEquals(2, arbol.grado(2));
        assertEquals(0, arbol.grado(4));
    }

    public void testAltura() {
        TArbolGenerico<Integer> arbol = crearArbolDeEjemplo();

        assertEquals(2, arbol.altura(1));
        assertEquals(1, arbol.altura(2));
        assertEquals(0, arbol.altura(5));
    }

    public void testRecorridos() {
        TArbolGenerico<Integer> arbol = crearArbolDeEjemplo();

        assertEquals(Arrays.asList(1, 2, 5, 6, 3, 4), recorrerPreOrden(arbol));
        assertEquals(Arrays.asList(5, 2, 6, 1, 3, 4), recorrerInOrden(arbol));
        assertEquals(Arrays.asList(5, 6, 2, 3, 4, 1), recorrerPostOrden(arbol));
    }

    public void testEliminarNodoEliminaSubarbol() {
        TArbolGenerico<Integer> arbol = crearArbolDeEjemplo();

        arbol.eliminar(2);

        assertNull(arbol.buscar(2));
        assertNull(arbol.buscar(5));
        assertNull(arbol.buscar(6));
        assertEquals(Arrays.asList(1, 3, 4), recorrerPreOrden(arbol));
        assertEquals(2, arbol.grado(1));
    }

    public void testEliminarRaizVaciaArbol() {
        TArbolGenerico<Integer> arbol = crearArbolDeEjemplo();

        arbol.eliminar(1);

        assertNull(arbol.buscar(1));
        assertTrue(recorrerPreOrden(arbol).isEmpty());
    }

    public void testVaciarEliminaTodosLosNodos() {
        TArbolGenerico<Integer> arbol = crearArbolDeEjemplo();

        arbol.vaciar();

        assertNull(arbol.buscar(1));
        assertTrue(recorrerPreOrden(arbol).isEmpty());
    }

    private TArbolGenerico<Integer> crearArbolDeEjemplo() {
        TArbolGenerico<Integer> arbol = crearArbol(1);
        arbol.agregarHijo(1, 2);
        arbol.agregarHijo(1, 3);
        arbol.agregarHijo(1, 4);
        arbol.agregarHijo(2, 5);
        arbol.agregarHijo(2, 6);
        return arbol;
    }

    private List<Integer> recorrerPreOrden(TArbolGenerico<Integer> arbol) {
        List<Integer> resultado = new ArrayList<>();
        arbol.preOrden(resultado::add);
        return resultado;
    }

    private List<Integer> recorrerInOrden(TArbolGenerico<Integer> arbol) {
        List<Integer> resultado = new ArrayList<>();
        arbol.inOrden(resultado::add);
        return resultado;
    }

    private List<Integer> recorrerPostOrden(TArbolGenerico<Integer> arbol) {
        List<Integer> resultado = new ArrayList<>();
        arbol.postOrden(resultado::add);
        return resultado;
    }
}
