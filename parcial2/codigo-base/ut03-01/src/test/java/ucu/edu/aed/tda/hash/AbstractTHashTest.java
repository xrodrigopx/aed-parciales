package ucu.edu.aed.tda.hash;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractTHashTest extends TestCase {
    protected abstract <K, V> THash<K, V> crearHash(int elementosEsperados);

    public void testHashNuevoEstaVacio() {
        THash<Integer, String> hash = crearHash(10);

        assertTrue(hash.esVacio());
        assertNull(hash.buscar(1));
        assertFalse(hash.delete(1));
        assertTrue(toList(hash.entries()).isEmpty());
        assertTrue(toList(hash.keys()).isEmpty());
        assertTrue(toList(hash.values()).isEmpty());
    }

    public void testInsertarYBuscar() {
        THash<Integer, String> hash = crearHash(10);

        assertTrue(hash.insertar(1, "uno"));
        assertTrue(hash.insertar(2, "dos"));
        assertTrue(hash.insertar(3, "tres"));

        assertFalse(hash.esVacio());
        assertEquals("uno", hash.buscar(1));
        assertEquals("dos", hash.buscar(2));
        assertEquals("tres", hash.buscar(3));
        assertNull(hash.buscar(99));
    }

    public void testInsertarClaveDuplicadaNoAgregaNuevoValor() {
        THash<Integer, String> hash = crearHash(10);

        assertTrue(hash.insertar(1, "uno"));
        assertFalse(hash.insertar(1, "otro"));

        assertEquals("uno", hash.buscar(1));
        assertEquals(1, toList(hash.entries()).size());
    }

    public void testDeleteEliminaClaveExistente() {
        THash<Integer, String> hash = crearHash(10);
        hash.insertar(1, "uno");
        hash.insertar(2, "dos");
        hash.insertar(3, "tres");

        assertTrue(hash.delete(2));

        assertNull(hash.buscar(2));
        assertEquals("uno", hash.buscar(1));
        assertEquals("tres", hash.buscar(3));
        assertFalse(hash.delete(2));
    }

    public void testDeleteUltimoElementoDejaHashVacio() {
        THash<Integer, String> hash = crearHash(10);
        hash.insertar(1, "uno");

        assertTrue(hash.delete(1));

        assertTrue(hash.esVacio());
        assertNull(hash.buscar(1));
    }

    public void testVaciarEliminaTodasLasEntradas() {
        THash<Integer, String> hash = crearHash(10);
        hash.insertar(1, "uno");
        hash.insertar(2, "dos");

        hash.vaciar();

        assertTrue(hash.esVacio());
        assertNull(hash.buscar(1));
        assertNull(hash.buscar(2));
        assertTrue(toList(hash.entries()).isEmpty());
    }

    public void testEntriesKeysYValuesRetornanContenido() {
        THash<Integer, String> hash = crearHash(10);
        hash.insertar(1, "uno");
        hash.insertar(2, "dos");
        hash.insertar(3, "tres");

        List<Entry<Integer, String>> entries = toList(hash.entries());
        List<Integer> keys = toList(hash.keys());
        List<String> values = toList(hash.values());

        assertEquals(3, entries.size());
        assertTrue(keys.contains(1));
        assertTrue(keys.contains(2));
        assertTrue(keys.contains(3));
        assertTrue(values.contains("uno"));
        assertTrue(values.contains("dos"));
        assertTrue(values.contains("tres"));
        assertTrue(contieneEntry(entries, 1, "uno"));
        assertTrue(contieneEntry(entries, 2, "dos"));
        assertTrue(contieneEntry(entries, 3, "tres"));
    }

    public void testOperacionesConReport() {
        THash<Integer, String> hash = crearHash(10);
        Report insertReport = new Report();
        Report buscarReport = new Report();
        Report deleteReport = new Report();

        assertTrue(hash.insertar(1, "uno", insertReport));
        assertEquals("uno", hash.buscar(1, buscarReport));
        assertTrue(hash.delete(1, deleteReport));

        assertTrue(insertReport.getCantidadComparaciones() >= 0);
        assertTrue(buscarReport.getCantidadComparaciones() >= 0);
        assertTrue(deleteReport.getCantidadComparaciones() >= 0);
    }

    public void testSoportaColisionesSinPerderDatos() {
        THash<Integer, String> hash = crearHash(3);

        for (int i = 0; i < 20; i++) {
            assertTrue(hash.insertar(i, "valor-" + i));
        }

        for (int i = 0; i < 20; i++) {
            assertEquals("valor-" + i, hash.buscar(i));
        }
        assertEquals(20, toList(hash.entries()).size());
    }

    private <T> List<T> toList(Iterable<T> iterable) {
        List<T> resultado = new ArrayList<>();
        for (T elemento : iterable) {
            resultado.add(elemento);
        }
        return resultado;
    }

    private boolean contieneEntry(List<Entry<Integer, String>> entries, Integer clave, String valor) {
        for (Entry<Integer, String> entry : entries) {
            if (clave.equals(entry.getClave()) && valor.equals(entry.getValor())) {
                return true;
            }
        }
        return false;
    }
}
