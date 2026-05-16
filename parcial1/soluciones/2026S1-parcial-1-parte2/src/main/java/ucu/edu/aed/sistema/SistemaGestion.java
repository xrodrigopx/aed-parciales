package ucu.edu.aed.sistema;

import ucu.edu.aed.modelo.Tarea;

/**
 * Contrato del sistema de gestión de tareas operativas de una nave autónoma.
 *
 * <p>Declara exactamente los cuatro métodos exigidos por el enunciado del parcial:
 * <ul>
 *   <li>{@link #recibirTarea(Tarea)} — R1: Recepción de tareas con cupos y cola de espera.</li>
 *   <li>{@link #procesarTarea()} — R2: Procesamiento por mayor criticidad disponible y FIFO en empates.</li>
 *   <li>{@link #buscarTareaProcesada(int)} — R3: Recuperación por ID en el historial.</li>
 *   <li>{@link #cancelarTarea(int)} — R4: Cancelación de tareas pendientes (no procesadas).</li>
 * </ul>
 *
 * <p>Las implementaciones pueden exponer información adicional (contadores de estado,
 * acceso a estructuras internas para tests, etc.) sin que esto forme parte del contrato.</p>
 */
public interface SistemaGestion {

    /**
     * R1 — Recibe una nueva tarea para ser gestionada por el sistema.
     *
     * <p>Reglas que debe respetar la implementación:
     * <ul>
     *   <li>El total de tareas pendientes nunca puede superar 25.</li>
     *   <li>Las tareas pendientes con criticidad 1 o 2 nunca pueden superar 10
     *       simultáneamente.</li>
     *   <li>Si la tarea no puede admitirse inmediatamente por alguna de las dos
     *       restricciones anteriores, debe quedar en espera hasta que pueda ingresar
     *       (no se descarta).</li>
     *   <li>Las tareas con criticidad 1 y 2 tienen prioridad de ejecución sobre las
     *       de criticidad 3 y 4.</li>
     *   <li>Las tareas con criticidad 3 y 4 deben organizarse respetando estrictamente
     *       el orden en que fueron recibidas.</li>
     * </ul>
     *
     * @param tarea tarea a recibir; las implementaciones deben tolerar {@code null}
     *              devolviendo {@code false} sin alterar el estado interno
     * @return {@code true} si la tarea fue admitida inmediatamente al pool de pendientes;
     *         {@code false} si quedó en espera o si el parámetro era {@code null}
     */
    boolean recibirTarea(Tarea tarea);

    /**
     * R2 — Selecciona y ejecuta la próxima tarea pendiente.
     *
     * <p>Reglas que debe respetar la implementación:
     * <ul>
     *   <li>Siempre se ejecuta primero la tarea de mayor criticidad disponible
     *       (criticidad 1 antes que 2; críticas antes que normales).</li>
     *   <li>En empate de criticidad, se ejecuta primero la tarea que llegó antes (FIFO).</li>
     *   <li>Tras ejecutarse, la tarea debe registrarse en el historial de tareas
     *       procesadas para poder ser recuperada por {@link #buscarTareaProcesada(int)}.</li>
     * </ul>
     *
     * @return la tarea ejecutada o {@code null} si no había ninguna pendiente
     */
    Tarea procesarTarea();

    /**
     * R3 — Recupera una tarea ya ejecutada a partir de su ID.
     *
     * <p>El historial debe satisfacer, una vez procesadas al menos 75 tareas:
     * <ul>
     *   <li>Permitir buscar una tarea por su ID.</li>
     *   <li>Garantizar tiempo de búsqueda <strong>logarítmico en peor caso</strong>,
     *       independientemente del orden de inserción de los IDs.</li>
     * </ul>
     *
     * @param id identificador de la tarea procesada
     * @return la tarea encontrada o {@code null} si no existe en el historial
     */
    Tarea buscarTareaProcesada(int id);

    /**
     * R4 — Cancela una tarea pendiente a partir de su ID.
     *
     * <p>Reglas que debe respetar la implementación:
     * <ul>
     *   <li>Solo pueden cancelarse tareas que aún no hayan sido procesadas.</li>
     *   <li>Una tarea ya almacenada en el historial nunca debe poder cancelarse
     *       ni modificarse a través de este método.</li>
     * </ul>
     *
     * @param id identificador de la tarea a cancelar
     * @return la tarea cancelada o {@code null} si no se encontró pendiente
     *         (ya fue procesada o nunca existió)
     */
    Tarea cancelarTarea(int id);
}
