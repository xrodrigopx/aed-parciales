
package ucu.edu.aed;

import org.junit.Before;
import org.junit.Test;

import ucu.edu.aed.modelo.Tarea;
import ucu.edu.aed.sistema.SistemaGestion;
import ucu.edu.aed.sistema.SistemaGestionImpl;

import static org.junit.Assert.*;

/**
 * Tests del sistema de gestión de tareas operativas de la nave.
 * Cubre casos normales, bordes y errores esperados.
 */
public class SistemaGestionTest {

    private SistemaGestion sistema;
    private SistemaGestionImpl sistemaImpl;

    @Before
    public void setUp() {
        sistemaImpl = new SistemaGestionImpl();
        sistema = sistemaImpl;
    }

    // -------------------------------------------------------------------------
    // Tests de Tarea
    // -------------------------------------------------------------------------

    @Test
    public void tareaValidaSeCreaSinExcepcion() {
        Tarea tarea = new Tarea(1, "Descripcion", 3);
        assertEquals(1, tarea.getId());
        assertEquals("Descripcion", tarea.getDescripcion());
        assertEquals(3, tarea.getCriticidad());
    }

    @Test(expected = IllegalArgumentException.class)
    public void tareaConCriticidadFueraDeRangoLanzaExcepcion() {
        new Tarea(1, "Descripcion", 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void tareaConIdNegativoLanzaExcepcion() {
        new Tarea(-1, "Descripcion", 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void tareaConDescripcionNulaLanzaExcepcion() {
        new Tarea(1, null, 2);
    }

    // -------------------------------------------------------------------------
    // Tests de recibirTarea (R1)
    // -------------------------------------------------------------------------

    @Test
    public void recibirTareaNulaRetornaFalse() {
        boolean resultado = sistema.recibirTarea(null);
        assertFalse(resultado);
        assertEquals(0, sistemaImpl.getCantidadPendientesTotal());
    }

    @Test
    public void recibirTareaValidaRetornaTrue() {
        Tarea tarea = new Tarea(1, "Tarea normal", 3);
        boolean resultado = sistema.recibirTarea(tarea);
        assertTrue(resultado);
        assertEquals(1, sistemaImpl.getCantidadPendientesTotal());
    }

    @Test
    public void recibirTareasHastaMaximoCriticasLasPonerEnEspera() {
        // Agregar 10 tareas críticas (máximo permitido)
        for (int i = 1; i <= 10; i++) {
            assertTrue(sistema.recibirTarea(new Tarea(i, "Critica " + i, 1)));
        }
        assertEquals(10, sistemaImpl.getCantidadCriticasPendientes());

        // La undécima crítica debe ir a espera
        boolean resultado = sistema.recibirTarea(new Tarea(11, "Critica extra", 2));
        assertFalse(resultado);
        assertEquals(1, sistemaImpl.getCantidadEnEspera());
    }

    @Test
    public void recibirTareasHastaMaximoPendientesLasPonerEnEspera() {
        // Agregar 25 tareas normales (máximo total)
        for (int i = 1; i <= 25; i++) {
            assertTrue(sistema.recibirTarea(new Tarea(i, "Normal " + i, 3)));
        }
        assertEquals(25, sistemaImpl.getCantidadPendientesTotal());

        // La tarea 26 debe ir a espera
        boolean resultado = sistema.recibirTarea(new Tarea(26, "Extra", 4));
        assertFalse(resultado);
        assertEquals(1, sistemaImpl.getCantidadEnEspera());
    }

    @Test
    public void tareaEnEsperaIngressaDespuesDeQueHayaLugar() {
        // Llenar con 10 críticas
        for (int i = 1; i <= 10; i++) {
            sistema.recibirTarea(new Tarea(i, "Critica " + i, 1));
        }
        // Agregar una crítica extra que va a espera
        sistema.recibirTarea(new Tarea(11, "Critica espera", 1));
        assertEquals(1, sistemaImpl.getCantidadEnEspera());

        // Procesar una tarea libera lugar
        sistema.procesarTarea();

        // La tarea en espera debe haber ingresado
        assertEquals(0, sistemaImpl.getCantidadEnEspera());
        assertEquals(10, sistemaImpl.getCantidadCriticasPendientes());
    }

    // -------------------------------------------------------------------------
    // Tests de procesarTarea (R2)
    // -------------------------------------------------------------------------

    @Test
    public void procesarTareaEnSistemaVacioRetornaNull() {
        Tarea resultado = sistema.procesarTarea();
        assertNull(resultado);
    }

    @Test
    public void procesarPriorizaCriticasSobreNormales() {
        sistema.recibirTarea(new Tarea(1, "Normal", 3));
        sistema.recibirTarea(new Tarea(2, "Critica", 1));

        Tarea procesada = sistema.procesarTarea();
        assertEquals(2, procesada.getId()); // la crítica va primero
    }

    @Test
    public void procesarCriticasMenorNumeroPrimero() {
        sistema.recibirTarea(new Tarea(1, "Criticidad 2", 2));
        sistema.recibirTarea(new Tarea(2, "Criticidad 1", 1));

        Tarea procesada = sistema.procesarTarea();
        assertEquals(2, procesada.getId()); // criticidad 1 antes que 2
    }

    @Test
    public void procesarEmpateEnCriticidadUsaFIFO() {
        sistema.recibirTarea(new Tarea(1, "Criticidad 3 primera", 3));
        sistema.recibirTarea(new Tarea(2, "Criticidad 3 segunda", 3));

        Tarea procesada = sistema.procesarTarea();
        assertEquals(1, procesada.getId()); // la primera en llegar va primero
    }

    @Test
    public void procesarRegistraEnHistorial() {
        sistema.recibirTarea(new Tarea(42, "Tarea historial", 2));
        sistema.procesarTarea();

        Tarea encontrada = sistema.buscarTareaProcesada(42);
        assertNotNull(encontrada);
        assertEquals(42, encontrada.getId());
    }

    // -------------------------------------------------------------------------
    // Tests de buscarTareaProcesada (R3)
    // -------------------------------------------------------------------------

    @Test
    public void buscarTareaProcesadaNoExistenteRetornaNull() {
        Tarea resultado = sistema.buscarTareaProcesada(999);
        assertNull(resultado);
    }

    @Test
    public void buscarTareaProcesadaExistente() {
        sistema.recibirTarea(new Tarea(7, "Tarea 7", 1));
        sistema.procesarTarea();

        Tarea encontrada = sistema.buscarTareaProcesada(7);
        assertNotNull(encontrada);
        assertEquals(7, encontrada.getId());
    }

    // -------------------------------------------------------------------------
    // Tests de cancelarTarea (R4)
    // -------------------------------------------------------------------------

    @Test
    public void cancelarTareaPendienteRetornaTarea() {
        sistema.recibirTarea(new Tarea(10, "Cancelable", 3));
        Tarea cancelada = sistema.cancelarTarea(10);

        assertNotNull(cancelada);
        assertEquals(10, cancelada.getId());
        assertEquals(0, sistemaImpl.getCantidadPendientesTotal());
    }

    @Test
    public void cancelarTareaYaProcesadaRetornaNull() {
        sistema.recibirTarea(new Tarea(10, "Procesable", 3));
        sistema.procesarTarea();

        // No debería poder cancelarse una tarea ya procesada
        Tarea cancelada = sistema.cancelarTarea(10);
        assertNull(cancelada);
    }

    @Test
    public void cancelarTareaInexistenteRetornaNull() {
        Tarea cancelada = sistema.cancelarTarea(999);
        assertNull(cancelada);
    }

    @Test
    public void cancelarTareaEnEsperaFunciona() {
        // Llenar pool con 25 tareas normales
        for (int i = 1; i <= 25; i++) {
            sistema.recibirTarea(new Tarea(i, "Normal " + i, 3));
        }
        // La tarea 26 va a espera
        sistema.recibirTarea(new Tarea(26, "En espera", 4));
        assertEquals(1, sistemaImpl.getCantidadEnEspera());

        // Cancelar la tarea en espera
        Tarea cancelada = sistema.cancelarTarea(26);
        assertNotNull(cancelada);
        assertEquals(26, cancelada.getId());
        assertEquals(0, sistemaImpl.getCantidadEnEspera());
    }
}
