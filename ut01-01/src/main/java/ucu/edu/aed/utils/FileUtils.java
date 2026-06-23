package ucu.edu.aed.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.function.Consumer;

import ucu.edu.aed.classes.problem_set_01.Library;

public class FileUtils {
    /**
     * Ejemplo de uso
     * FileUtils.leerLineas("./path/al/archivo/archivo.txt", linea -> {
     * System.out.println(linea);
     * });
     *
     * @param path     Ruta del archivo
     * @param consumer función que recibe cada linea del archivo
     */
    public static void leerLineas(String path, Consumer<String> consumer) {

        try {
            URL resource = FileUtils.class.getClassLoader().getResource(path);
            Path p = resource == null ? Paths.get(path) : Paths.get(resource.toURI());

            try (BufferedReader reader = Files.newBufferedReader(p)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    consumer.accept(line);
                }
            }
        } catch (IOException | URISyntaxException e) {

            System.err.println("Error al leer el archivo " + path);
            System.err.println(e.getLocalizedMessage());
        }

    }

    /**
     * Guarda contenido en el path indicado
     *
     * @param path      ruta de persistencia
     * @param contenido string del contenido
     */
    public static void escribirLineas(String path, String... contenido) {
        try {
            Files.write(Paths.get(path), Arrays.asList(contenido));
        } catch (IOException e) {
            System.err.println("Error al escribir el archivo " + path);
            System.err.println(e.getLocalizedMessage());
        }
    }

    /**
     * Procesa el archivo de adquisiciones y delega en {@link Library#compraLibros}.
     * Cada linea con formato codigo;titulo;precio;cantidad se transforma en una compra.
     */
    public static void procesarCompras(String path, final Library library) {
        procesarArchivo(path, 4, new Consumer<String[]>() {
            public void accept(String[] columnas) {
                int codigo = Integer.parseInt(columnas[0].trim());
                String titulo = limpiarCampo(columnas[1]);
                double precio = Double.parseDouble(columnas[2].trim());
                int cantidad = Integer.parseInt(columnas[3].trim());
                double total = library.compraLibros(codigo, titulo, precio, cantidad);
                System.out.printf("Compra registrada: %s (+%d) | Valor: %.2f%n", titulo, cantidad, total);
            }
        }, "adquisiciones");
    }

    /**
     * Procesa el archivo de prestamos/devoluciones ejecutando {@link Library#prestaYDevuelve}.
     */
    public static void procesarPrestamos(String path, final Library library) {
        procesarArchivo(path, 3, new Consumer<String[]>() {
            public void accept(String[] columnas) {
                int codigo = Integer.parseInt(columnas[0].trim());
                String tipo = limpiarCampo(columnas[1]);
                int cantidad = Integer.parseInt(columnas[2].trim());
                library.prestaYDevuelve(codigo, tipo, cantidad);
                System.out.printf("Operacion %s aplicada al libro %d por %d unidades.%n", tipo, codigo, cantidad);
            }
        }, "prestamos");
    }

    private static void procesarArchivo(String path, final int columnasMinimas, final Consumer<String[]> handler, final String descripcion) {
        leerLineas(path, new Consumer<String>() {
            public void accept(String linea) {
                if (esLineaDeDatos(linea)) {
                    String[] columnas = linea.split(";");
                    if (columnas.length < columnasMinimas) {
                        System.err.println("Linea invalida de " + descripcion + ": " + linea);
                        return;
                    }
                    try {
                        handler.accept(columnas);
                    } catch (RuntimeException e) {
                        System.err.println("Error al procesar " + descripcion + ": " + e.getMessage());
                    }
                }
            }
        });
    }

    private static boolean esLineaDeDatos(String linea) {
        if (linea == null) {
            return false;
        }
        if (linea.isBlank()) {
            return false;
        }
        return !linea.toUpperCase().startsWith("CODIGO_LIBRO");
    }

    private static String limpiarCampo(String valor) {
        return valor.replace("\"", "").trim();
    }
}
