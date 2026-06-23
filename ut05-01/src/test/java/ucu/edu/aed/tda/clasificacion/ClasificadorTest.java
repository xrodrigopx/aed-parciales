package ucu.edu.aed.tda.clasificacion;

import junit.framework.TestCase;

public class ClasificadorTest extends TestCase {

    private ClasificadorImpl clasificador;
    private GeneradorDatosGenericos generador;

    protected void setUp() {
        clasificador = new ClasificadorImpl();
        generador = new GeneradorDatosGenericos();
    }

    // ==================== estaOrdenado ====================

    public void testEstaOrdenadoConVectorOrdenado() {
        int[] datos = {1, 2, 3, 4, 5};
        assertTrue(clasificador.estaOrdenado(datos));
    }

    public void testEstaOrdenadoConVectorDesordenado() {
        int[] datos = {5, 3, 1, 4, 2};
        assertFalse(clasificador.estaOrdenado(datos));
    }

    public void testEstaOrdenadoConUnSoloElemento() {
        int[] datos = {42};
        assertTrue(clasificador.estaOrdenado(datos));
    }

    public void testEstaOrdenadoConElementosIguales() {
        int[] datos = {3, 3, 3, 3};
        assertTrue(clasificador.estaOrdenado(datos));
    }

    // ==================== Inserción Directa ====================

    public void testInsercionDirectaConVectorPequeno() {
        int[] datos = {42, 7, 19, 3, 55};
        clasificador.insercionDirecta(datos);
        assertTrue(clasificador.estaOrdenado(datos));
    }

    public void testInsercionDirectaConVectorAscendente() {
        int[] datos = generador.generarDatosAscendentes();
        clasificador.insercionDirecta(datos);
        assertTrue(clasificador.estaOrdenado(datos));
    }

    public void testInsercionDirectaConVectorDescendente() {
        int[] datos = generador.generarDatosDescendentes();
        clasificador.insercionDirecta(datos);
        assertTrue(clasificador.estaOrdenado(datos));
    }

    public void testInsercionDirectaConVectorAleatorio() {
        int[] datos = generador.generarDatosAleatorios();
        clasificador.insercionDirecta(datos);
        assertTrue(clasificador.estaOrdenado(datos));
    }

    public void testInsercionDirectaConUnSoloElemento() {
        int[] datos = {99};
        clasificador.insercionDirecta(datos);
        assertTrue(clasificador.estaOrdenado(datos));
    }

    public void testInsercionDirectaValoresCorrectos() {
        int[] datos = {5, 2, 8, 1, 9};
        clasificador.insercionDirecta(datos);
        assertEquals(1, datos[0]);
        assertEquals(2, datos[1]);
        assertEquals(5, datos[2]);
        assertEquals(8, datos[3]);
        assertEquals(9, datos[4]);
    }

    // ==================== Shellsort ====================

    public void testShellConVectorPequeno() {
        int[] datos = {42, 7, 19, 3, 55};
        Integer[] incrementos = {3, 1};
        clasificador.shell(datos, incrementos);
        assertTrue(clasificador.estaOrdenado(datos));
    }

    public void testShellConIncrementos5_3_1Ascendente() {
        int[] datos = generador.generarDatosAscendentes();
        Integer[] incrementos = {5, 3, 1};
        clasificador.shell(datos, incrementos);
        assertTrue(clasificador.estaOrdenado(datos));
    }

    public void testShellConIncrementos5_3_1Descendente() {
        int[] datos = generador.generarDatosDescendentes();
        Integer[] incrementos = {5, 3, 1};
        clasificador.shell(datos, incrementos);
        assertTrue(clasificador.estaOrdenado(datos));
    }

    public void testShellConIncrementos5_3_1Aleatorio() {
        int[] datos = generador.generarDatosAleatorios();
        Integer[] incrementos = {5, 3, 1};
        clasificador.shell(datos, incrementos);
        assertTrue(clasificador.estaOrdenado(datos));
    }

    public void testShellValoresCorrectos() {
        int[] datos = {256, 458, 655, 298, 43, 648, 778, 621, 655, 19, 124, 847};
        Integer[] incrementos = {5, 3, 1};
        clasificador.shell(datos, incrementos);
        assertTrue(clasificador.estaOrdenado(datos));
        assertEquals(19, datos[0]);
        assertEquals(847, datos[datos.length - 1]);
    }

    // ==================== Burbuja ====================

    public void testBurbujaConVectorPequeno() {
        int[] datos = {42, 7, 19, 3, 55};
        clasificador.burbuja(datos);
        assertTrue(clasificador.estaOrdenado(datos));
    }

    public void testBurbujaConVectorAscendente() {
        int[] datos = generador.generarDatosAscendentes();
        clasificador.burbuja(datos);
        assertTrue(clasificador.estaOrdenado(datos));
    }

    public void testBurbujaConVectorDescendente() {
        int[] datos = generador.generarDatosDescendentes();
        clasificador.burbuja(datos);
        assertTrue(clasificador.estaOrdenado(datos));
    }

    public void testBurbujaConVectorAleatorio() {
        int[] datos = generador.generarDatosAleatorios();
        clasificador.burbuja(datos);
        assertTrue(clasificador.estaOrdenado(datos));
    }

    public void testBurbujaValoresCorrectos() {
        int[] datos = {5, 2, 8, 1, 9};
        clasificador.burbuja(datos);
        assertEquals(1, datos[0]);
        assertEquals(2, datos[1]);
        assertEquals(5, datos[2]);
        assertEquals(8, datos[3]);
        assertEquals(9, datos[4]);
    }

    // ==================== Clasificación Directa (Selección) ====================

    public void testClasificacionDirectaConVectorPequeno() {
        int[] datos = {42, 7, 19, 3, 55};
        clasificador.clasificacionDirecta(datos);
        assertTrue(clasificador.estaOrdenado(datos));
    }

    public void testClasificacionDirectaConVectorAleatorio() {
        int[] datos = generador.generarDatosAleatorios();
        clasificador.clasificacionDirecta(datos);
        assertTrue(clasificador.estaOrdenado(datos));
    }

    // ==================== Quicksort ====================

    public void testQuicksortConVectorPequeno() {
        int[] datos = {42, 7, 19, 3, 55};
        clasificador.quicksort(datos);
        assertTrue(clasificador.estaOrdenado(datos));
    }

    public void testQuicksortConVectorAleatorio() {
        int[] datos = generador.generarDatosAleatorios();
        clasificador.quicksort(datos);
        assertTrue(clasificador.estaOrdenado(datos));
    }

    // ==================== Heapsort ====================

    public void testHeapsortConVectorPequeno() {
        int[] datos = {42, 7, 19, 3, 55};
        clasificador.heapsort(datos);
        assertTrue(clasificador.estaOrdenado(datos));
    }

    public void testHeapsortConVectorAleatorio() {
        int[] datos = generador.generarDatosAleatorios();
        clasificador.heapsort(datos);
        assertTrue(clasificador.estaOrdenado(datos));
    }
}
