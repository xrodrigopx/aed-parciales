package ucu.edu.aed.tda.trie;

import junit.framework.TestCase;
import ucu.edu.aed.tda.trie.impl.Trie;

import java.util.List;

public class TrieTest extends TestCase {

    // --- insertar ---

    public void testInsertarPalabraNueva() {
        TTrie<String> trie = new Trie<>();
        assertTrue(trie.insertar("casa", "dato"));
    }

    public void testInsertarPalabraDuplicadaRetornaFalse() {
        TTrie<String> trie = new Trie<>();
        trie.insertar("casa", "dato");
        assertFalse(trie.insertar("casa", "otro"));
    }

    public void testInsertarNull() {
        TTrie<String> trie = new Trie<>();
        assertFalse(trie.insertar(null, "dato"));
    }

    // --- buscar ---

    public void testBuscarPalabraExistente() {
        TTrie<String> trie = new Trie<>();
        trie.insertar("casa", "dato");
        Entry<String> entry = trie.buscar("casa");
        assertNotNull(entry);
        assertTrue(entry.esPalabra());
        assertEquals("casa", entry.getPalabra());
    }

    public void testBuscarPrefijoRetornaEsPalabraFalse() {
        TTrie<String> trie = new Trie<>();
        trie.insertar("casamiento", "dato");
        Entry<String> entry = trie.buscar("casa");
        assertNotNull(entry);
        assertFalse(entry.esPalabra());
    }

    public void testBuscarPalabraInexistente() {
        TTrie<String> trie = new Trie<>();
        trie.insertar("casa", "dato");
        assertNull(trie.buscar("perro"));
    }

    public void testBuscarEnTrieVacio() {
        TTrie<String> trie = new Trie<>();
        assertNull(trie.buscar("casa"));
    }

    public void testBuscarNull() {
        TTrie<String> trie = new Trie<>();
        assertNull(trie.buscar(null));
    }

    // --- predecir ---

    public void testPredecirRetornaPalabrasConPrefijo() {
        TTrie<String> trie = new Trie<>();
        trie.insertar("casa", "1");
        trie.insertar("casamiento", "2");
        trie.insertar("casco", "3");
        trie.insertar("perro", "4");

        List<Entry<String>> resultado = trie.predecir("cas");
        assertEquals(3, resultado.size());
    }

    public void testPredecirPrefijoSinResultados() {
        TTrie<String> trie = new Trie<>();
        trie.insertar("casa", "dato");
        List<Entry<String>> resultado = trie.predecir("xyz");
        assertTrue(resultado.isEmpty());
    }

    public void testPredecirNull() {
        TTrie<String> trie = new Trie<>();
        trie.insertar("casa", "dato");
        List<Entry<String>> resultado = trie.predecir(null);
        assertTrue(resultado.isEmpty());
    }

    // --- eliminar ---

    public void testEliminarPalabraExistente() {
        TTrie<String> trie = new Trie<>();
        trie.insertar("casa", "dato");
        assertTrue(trie.eliminar("casa"));
    }

    public void testEliminarMarcaEsPalabraFalse() {
        TTrie<String> trie = new Trie<>();
        trie.insertar("casa", "dato");
        trie.eliminar("casa");
        Entry<String> entry = trie.buscar("casa");
        // el nodo sigue existiendo porque "casamiento" podría existir, pero esPalabra=false
        // en este caso sin hijos el nodo igual queda, pero esPalabra debe ser false
        assertFalse(entry.esPalabra());
    }

    public void testEliminarPalabraQueEsPrefijoDeOtra() {
        TTrie<String> trie = new Trie<>();
        trie.insertar("casa", "1");
        trie.insertar("casamiento", "2");

        assertTrue(trie.eliminar("casa"));

        // "casa" ya no es palabra completa
        Entry<String> entryCasa = trie.buscar("casa");
        assertNotNull(entryCasa);
        assertFalse(entryCasa.esPalabra());

        // "casamiento" sigue intacta
        Entry<String> entryCasamiento = trie.buscar("casamiento");
        assertNotNull(entryCasamiento);
        assertTrue(entryCasamiento.esPalabra());
    }

    public void testEliminarPalabraInexistente() {
        TTrie<String> trie = new Trie<>();
        trie.insertar("casa", "dato");
        assertFalse(trie.eliminar("perro"));
    }

    public void testEliminarEnTrieVacio() {
        TTrie<String> trie = new Trie<>();
        assertFalse(trie.eliminar("casa"));
    }

    public void testEliminarNull() {
        TTrie<String> trie = new Trie<>();
        assertFalse(trie.eliminar(null));
    }

    public void testEliminarDosVecesRetornaFalseSegundaVez() {
        TTrie<String> trie = new Trie<>();
        trie.insertar("casa", "dato");
        assertTrue(trie.eliminar("casa"));
        assertFalse(trie.eliminar("casa"));
    }
}
