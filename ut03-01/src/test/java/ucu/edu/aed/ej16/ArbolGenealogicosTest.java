package ucu.edu.aed.ej16;

import junit.framework.TestCase;
import java.util.List;

public class ArbolGenealogicosTest extends TestCase {

    /*
     * Árbol de ejemplo (10 personas, 3 generaciones):
     *
     * Abuela (1940)
     * ├── Hijo1 (1965)
     * │   ├── Nieto1 (1990)
     * │   ├── Nieto2 (1993)
     * │   └── Nieto3 (1995)
     * └── Hijo2 (1968)
     *     ├── Nieto4 (1992)
     *     ├── Nieto5 (1994)
     *     ├── Nieto6 (1997)
     *     └── Nieto7 (1999)
     */
    private ArbolGenealogico crearArbolDeEjemplo() {
        ArbolGenealogico arbol = new ArbolGenealogico(new Persona("Abuela", 1940));
        arbol.agregarHijo("Abuela", new Persona("Hijo1", 1965));
        arbol.agregarHijo("Abuela", new Persona("Hijo2", 1968));
        arbol.agregarHijo("Hijo1", new Persona("Nieto1", 1990));
        arbol.agregarHijo("Hijo1", new Persona("Nieto2", 1993));
        arbol.agregarHijo("Hijo1", new Persona("Nieto3", 1995));
        arbol.agregarHijo("Hijo2", new Persona("Nieto4", 1992));
        arbol.agregarHijo("Hijo2", new Persona("Nieto5", 1994));
        arbol.agregarHijo("Hijo2", new Persona("Nieto6", 1997));
        arbol.agregarHijo("Hijo2", new Persona("Nieto7", 1999));
        return arbol;
    }

    public void testCantidadPersonas() {
        ArbolGenealogico arbol = crearArbolDeEjemplo();
        assertEquals(10, arbol.cantidadPersonas());
    }

    public void testAltura() {
        ArbolGenealogico arbol = crearArbolDeEjemplo();
        assertEquals(2, arbol.altura());
    }

    public void testListarDescendientesDeRaiz() {
        ArbolGenealogico arbol = crearArbolDeEjemplo();
        List<Persona> descendientes = arbol.listarDescendientes("Abuela");
        assertEquals(9, descendientes.size());
    }

    public void testListarDescendientesDeHijo() {
        ArbolGenealogico arbol = crearArbolDeEjemplo();
        List<Persona> descendientes = arbol.listarDescendientes("Hijo1");
        assertEquals(3, descendientes.size());
    }

    public void testListarDescendientesDeHojaEsVacio() {
        ArbolGenealogico arbol = crearArbolDeEjemplo();
        List<Persona> descendientes = arbol.listarDescendientes("Nieto1");
        assertTrue(descendientes.isEmpty());
    }

    public void testListarDescendientesPersonaInexistente() {
        ArbolGenealogico arbol = crearArbolDeEjemplo();
        List<Persona> descendientes = arbol.listarDescendientes("NoExiste");
        assertTrue(descendientes.isEmpty());
    }

    public void testObtenerGeneracionCero() {
        ArbolGenealogico arbol = crearArbolDeEjemplo();
        List<Persona> gen0 = arbol.obtenerGeneracion(0);
        assertEquals(1, gen0.size());
        assertEquals("Abuela", gen0.get(0).getNombre());
    }

    public void testObtenerGeneracionUno() {
        ArbolGenealogico arbol = crearArbolDeEjemplo();
        List<Persona> gen1 = arbol.obtenerGeneracion(1);
        assertEquals(2, gen1.size());
    }

    public void testObtenerGeneracionDos() {
        ArbolGenealogico arbol = crearArbolDeEjemplo();
        List<Persona> gen2 = arbol.obtenerGeneracion(2);
        assertEquals(7, gen2.size());
    }

    public void testObtenerGeneracionInexistente() {
        ArbolGenealogico arbol = crearArbolDeEjemplo();
        List<Persona> gen5 = arbol.obtenerGeneracion(5);
        assertTrue(gen5.isEmpty());
    }

    public void testAncestroComun() {
        ArbolGenealogico arbol = crearArbolDeEjemplo();
        Persona ancestro = arbol.ancestroComun("Nieto1", "Nieto2");
        assertNotNull(ancestro);
        assertEquals("Hijo1", ancestro.getNombre());
    }

    public void testAncestroComun_PrimosRetornaAbuela() {
        ArbolGenealogico arbol = crearArbolDeEjemplo();
        Persona ancestro = arbol.ancestroComun("Nieto1", "Nieto4");
        assertNotNull(ancestro);
        assertEquals("Abuela", ancestro.getNombre());
    }

    public void testAncestroComun_UnoEsAncestroDelOtro() {
        ArbolGenealogico arbol = crearArbolDeEjemplo();
        Persona ancestro = arbol.ancestroComun("Hijo1", "Nieto1");
        assertNotNull(ancestro);
        assertEquals("Hijo1", ancestro.getNombre());
    }

    public void testAncestroComun_PersonaInexistente() {
        ArbolGenealogico arbol = crearArbolDeEjemplo();
        Persona ancestro = arbol.ancestroComun("Nieto1", "NoExiste");
        assertNull(ancestro);
    }

    public void testEsDescendiente_Verdadero() {
        ArbolGenealogico arbol = crearArbolDeEjemplo();
        assertTrue(arbol.esDescendiente("Nieto1", "Hijo1"));
        assertTrue(arbol.esDescendiente("Nieto1", "Abuela"));
        assertTrue(arbol.esDescendiente("Hijo2", "Abuela"));
    }

    public void testEsDescendiente_Falso() {
        ArbolGenealogico arbol = crearArbolDeEjemplo();
        assertFalse(arbol.esDescendiente("Nieto1", "Hijo2"));
        assertFalse(arbol.esDescendiente("Abuela", "Hijo1"));
    }

    public void testEsDescendiente_PersonaInexistente() {
        ArbolGenealogico arbol = crearArbolDeEjemplo();
        assertFalse(arbol.esDescendiente("NoExiste", "Abuela"));
        assertFalse(arbol.esDescendiente("Nieto1", "NoExiste"));
    }

    public void testAgregarHijoPadreInexistenteRetornaFalse() {
        ArbolGenealogico arbol = crearArbolDeEjemplo();
        assertFalse(arbol.agregarHijo("NoExiste", new Persona("Nuevo", 2000)));
    }
}
