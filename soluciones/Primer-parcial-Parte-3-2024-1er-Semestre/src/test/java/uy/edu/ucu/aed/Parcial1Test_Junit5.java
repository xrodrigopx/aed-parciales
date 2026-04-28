
package uy.edu.ucu.aed;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

/**
 * Tests para TArbolDeProductos y TArbolBB con Producto.
 */
public class Parcial1Test_Junit5 {

    TArbolDeProductos arbol;

    @BeforeEach
    public void setUp() {
        arbol = new TArbolDeProductos();
        // Insertar productos de prueba
        arbol.insertar(5, new Producto(5, "Manzana"));
        arbol.insertar(3, new Producto(3, "Banana"));
        arbol.insertar(7, new Producto(7, "Cereza"));
        arbol.insertar(1, new Producto(1, "Durazno"));
        arbol.insertar(4, new Producto(4, "Kiwi"));
    }

    @AfterEach
    public void tearDown() {
        arbol = null;
    }

    @Test
    public void arbolNoEsVacioDespuesDeInserciones() {
        assertFalse(arbol.esVacio());
    }

    @Test
    public void insertarProductoNuevoRetornaVerdadero() {
        boolean resultado = arbol.insertar(10, new Producto(10, "Naranja"));
        assertTrue(resultado);
    }

    @Test
    public void insertarProductoDuplicadoRetornaFalso() {
        // La etiqueta 5 ya existe
        boolean resultado = arbol.insertar(5, new Producto(5, "Duplicado"));
        assertFalse(resultado);
    }

    @Test
    public void buscarProductoExistenteRetornaProducto() {
        Producto p = arbol.buscar(3);
        assertNotNull(p);
        assertEquals("Banana", p.getNombre());
    }

    @Test
    public void buscarProductoInexistenteRetornaNull() {
        Producto p = arbol.buscar(99);
        assertNull(p);
    }

    @Test
    public void inOrdenRetornaProductosEnOrdenAscendente() {
        List<Producto> lista = arbol.inOrden();
        assertNotNull(lista);
        assertEquals(5, lista.size());
        // El primer elemento debe ser el de menor identificador
        assertEquals(1, lista.get(0).getIdentificador());
        assertEquals(3, lista.get(1).getIdentificador());
        assertEquals(4, lista.get(2).getIdentificador());
        assertEquals(5, lista.get(3).getIdentificador());
        assertEquals(7, lista.get(4).getIdentificador());
    }

    @Test
    public void preOrdenRetornaRaizPrimero() {
        List<Producto> lista = arbol.preOrden();
        assertNotNull(lista);
        // La raíz es el primer elemento insertado (5)
        assertEquals(5, lista.get(0).getIdentificador());
    }

    @Test
    public void postOrdenRetornaRaizUltimo() {
        List<Producto> lista = arbol.postOrden();
        assertNotNull(lista);
        // La raíz aparece al final en postorden
        assertEquals(5, lista.get(lista.size() - 1).getIdentificador());
    }

    @Test
    public void eliminarProductoExistenteReduceCantidad() {
        int antes = arbol.cantidadDeProductos();
        arbol.eliminar(3);
        int despues = arbol.cantidadDeProductos();
        assertEquals(antes - 1, despues);
    }

    @Test
    public void eliminarProductoExistenteNoApareceEnBusqueda() {
        arbol.eliminar(7);
        assertNull(arbol.buscar(7));
    }

    @Test
    public void eliminarProductoInexistenteNoCambiaArbol() {
        int antes = arbol.cantidadDeProductos();
        arbol.eliminar(999);
        assertEquals(antes, arbol.cantidadDeProductos());
    }

    @Test
    public void cantidadDeProductosEsCorrecta() {
        assertEquals(5, arbol.cantidadDeProductos());
    }

    @Test
    public void arbolVacioRetornaCantidadCero() {
        TArbolDeProductos arbolVacio = new TArbolDeProductos();
        assertEquals(0, arbolVacio.cantidadDeProductos());
    }

    @Test
    public void productosEnInOrdenFormatoEsCorrecto() {
        // Solo un elemento para verificar formato
        TArbolDeProductos arbolSimple = new TArbolDeProductos();
        arbolSimple.insertar(1, new Producto(1, "Limón"));
        String resultado = arbolSimple.productosEnInOrden();
        assertEquals("1:Limón", resultado);
    }

    @Test
    public void vaciarArbolLoDejaVacio() {
        arbol.vaciar();
        assertTrue(arbol.esVacio());
    }
}
