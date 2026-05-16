package ucu.edu.aed.domain;

import ucu.edu.aed.utils.FileUtils;

public class App {
    public static void main(String[] args) {
        // Agregar a esta carpeta las clases necesarias para
        // representar las entidades del problema.
        
        
        // Demostrar en este método el uso de las funcionalidades solicitadas.
        System.out.println("Tu código va aquí.");


        // Ejemplo de lectura de archivo:
        FileUtils.leerLineas("ingredientes_permitidos.csv", line -> {
            // Procesar cada línea del archivo de ingredientes permitidos.
            System.out.println("Ingrediente permitido: " + line);
        });
    }
}