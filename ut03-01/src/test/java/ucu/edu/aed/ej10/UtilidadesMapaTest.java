package ucu.edu.aed.ej10;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UtilidadesMapaTest extends TestCase {

    // --- Parte 1: eliminarValoresNulos ---

    public void testEliminarValoresNulos_eliminaEntradaNula() {
        Map<String, String> mapa = new HashMap<>();
        mapa.put("a", "valor");
        mapa.put("b", null);
        mapa.put("c", "otro");

        UtilidadesMapa.eliminarValoresNulos(mapa);

        assertEquals(2, mapa.size());
        assertFalse(mapa.containsKey("b"));
        assertTrue(mapa.containsKey("a"));
        assertTrue(mapa.containsKey("c"));
    }

    public void testEliminarValoresNulos_sinNulos_noModifica() {
        Map<String, String> mapa = new HashMap<>();
        mapa.put("a", "valor");
        mapa.put("b", "otro");

        UtilidadesMapa.eliminarValoresNulos(mapa);

        assertEquals(2, mapa.size());
    }

    public void testEliminarValoresNulos_todosNulos_quiedaVacio() {
        Map<String, String> mapa = new HashMap<>();
        mapa.put("a", null);
        mapa.put("b", null);

        UtilidadesMapa.eliminarValoresNulos(mapa);

        assertTrue(mapa.isEmpty());
    }

    public void testEliminarValoresNulos_mapaNull_noExplota() {
        UtilidadesMapa.eliminarValoresNulos(null);
    }

    // --- Parte 2: invertirMapa ---

    public void testInvertirMapa_intercambiaClaveYValor() {
        Map<String, String> original = new HashMap<>();
        original.put("es", "español");
        original.put("en", "inglés");

        Map<String, String> invertido = UtilidadesMapa.invertirMapa(original);

        assertEquals("es", invertido.get("español"));
        assertEquals("en", invertido.get("inglés"));
    }

    public void testInvertirMapa_valorDuplicadoLanzaExcepcion() {
        Map<String, String> original = new HashMap<>();
        original.put("a", "mismo");
        original.put("b", "mismo");

        try {
            UtilidadesMapa.invertirMapa(original);
            fail("Debería haber lanzado IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // esperado
        }
    }

    public void testInvertirMapa_mapaNull_retornaNull() {
        assertNull(UtilidadesMapa.invertirMapa(null));
    }

    public void testInvertirMapa_mapaVacio_retornaVacio() {
        Map<String, String> resultado = UtilidadesMapa.invertirMapa(new HashMap<>());
        assertTrue(resultado.isEmpty());
    }

    // --- Parte 3: ordenarPorLongitud ---

    public void testOrdenarPorLongitud_ordenaCorrectamente() {
        List<String> lista = new ArrayList<>();
        lista.add("banana");
        lista.add("kiwi");
        lista.add("pera");
        lista.add("manzana");
        lista.add("uva");

        UtilidadesMapa.ordenarPorLongitud(lista);

        assertEquals("uva", lista.get(0));
        assertEquals("kiwi", lista.get(1));
        assertEquals("pera", lista.get(2));
        assertEquals("banana", lista.get(3));
        assertEquals("manzana", lista.get(4));
    }

    public void testOrdenarPorLongitud_mismaTamanioOrdenLexicografico() {
        List<String> lista = new ArrayList<>();
        lista.add("ccc");
        lista.add("aaa");
        lista.add("bbb");

        UtilidadesMapa.ordenarPorLongitud(lista);

        assertEquals("aaa", lista.get(0));
        assertEquals("bbb", lista.get(1));
        assertEquals("ccc", lista.get(2));
    }

    public void testOrdenarPorLongitud_listaVacia_noExplota() {
        List<String> lista = new ArrayList<>();
        UtilidadesMapa.ordenarPorLongitud(lista);
        assertTrue(lista.isEmpty());
    }

    public void testOrdenarPorLongitud_null_noExplota() {
        UtilidadesMapa.ordenarPorLongitud(null);
    }
}
