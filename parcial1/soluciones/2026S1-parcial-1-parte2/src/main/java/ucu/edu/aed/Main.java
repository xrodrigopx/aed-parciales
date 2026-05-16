
package ucu.edu.aed;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import ucu.edu.aed.modelo.Tarea;
import ucu.edu.aed.sistema.SistemaGestion;
import ucu.edu.aed.sistema.SistemaGestionImpl;

/**
 * Punto de entrada del proyecto.
 */
public class Main {

    public static void main(String[] args) {
        String archivo = args.length > 0 ? args[0] : "naves.txt";

        SistemaGestion sistema = new SistemaGestionImpl();

        cargarTareas(sistema, archivo);

        System.out.println("Tareas cargadas. Procesando todas las tareas pendientes...");

        // Procesar todas las tareas hasta vaciar las colas
        Tarea procesada;
        int totalProcesadas = 0;
        do {
            procesada = sistema.procesarTarea();
            if (procesada != null) {
                totalProcesadas++;
                System.out.println("Procesada: " + procesada);
            }
        } while (procesada != null);

        System.out.println("Total de tareas procesadas: " + totalProcesadas);

        // Demostrar buscarTareaProcesada (R3)
        Tarea encontrada = sistema.buscarTareaProcesada(5);
        System.out.println("Buscar tarea procesada con id=5: " + encontrada);

        // Demostrar cancelarTarea (R4) — en este punto ya se procesaron todas,
        // así que recargamos algunas para la demostración
        SistemaGestion sistema2 = new SistemaGestionImpl();
        sistema2.recibirTarea(new Tarea(200, "Tarea demo cancelacion", 3));
        sistema2.recibirTarea(new Tarea(201, "Tarea demo cancelacion 2", 1));
        Tarea cancelada = sistema2.cancelarTarea(200);
        System.out.println("Cancelada tarea con id=200: " + cancelada);
    }

    /**
     * Lee el archivo e invoca {@code recibirTarea} en el sistema por cada línea válida.
     */
    private static void cargarTareas(SistemaGestion sistema, String archivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty() || linea.startsWith("#")) continue;
                String[] partes = linea.split(";");
                if (partes.length < 3) continue;
                try {
                    int id = Integer.parseInt(partes[0].trim());
                    String descripcion = partes[1].trim();
                    int criticidad = Integer.parseInt(partes[2].trim());
                    Tarea tarea = new Tarea(id, descripcion, criticidad);
                    boolean admitida = sistema.recibirTarea(tarea);
                    if (!admitida) {
                        System.out.println("Tarea en espera: " + tarea);
                    }
                } catch (IllegalArgumentException ex) {
                    System.err.println("Línea inválida: " + linea);
                }
            }
        } catch (IOException e) {
            System.err.println("No se pudo leer " + archivo + ": " + e.getMessage());
        }
    }
}
