package ucu.edu.aed.classes.problem_set_01;

import ucu.edu.aed.utils.FileUtils;

/**
 * Punto de entrada que orquesta la carga de compras y prestamos.
 */
public class Main {
    private static final String BASE_RESOURCES = "src/main/resources";

    public static void main(String[] args) {
        // crea la biblioteca para arrancar
        Library library = new Library();

        try {
            // procesa compras desde archivo
            FileUtils.procesarCompras(BASE_RESOURCES + "/adquisiciones.txt", library);
            // procesa prestamos desde archivo
            FileUtils.procesarPrestamos(BASE_RESOURCES + "/prestamos.txt", library);
        } catch (RuntimeException e) {
            System.err.println("Error al procesar archivos: " + e.getMessage());
        }

        // muestra algunos casos de prueba manuales
        demostrarOperaciones(library);
        // imprime el estado final del inventario
        library.imprimirInventario();
    }

    // ejecuta operaciones de ejemplo sobre la biblioteca
    private static void demostrarOperaciones(Library library) {
        try {
            // alta de un libro nuevo
            double totalAlta = library.compraLibros(2001, "Bases de Datos", 70.00, 2);
            System.out.printf("1) Alta de nuevo libro -> total invertido: %.2f%n", totalAlta);

            // reposicion de un libro existente
            double totalReposicion = library.compraLibros(1001, "Clean Code", 58.90, 1);
            System.out.printf("2) Reposicion de ejemplares -> total invertido: %.2f%n", totalReposicion);

            // registra un prestamo
            library.prestaYDevuelve(1003, "Prestamo", 1);
            System.out.println("3) Prestamo registrado para ISBN 1003");

            // registra una devolucion
            library.prestaYDevuelve(1002, "Devolucion", 1);
            System.out.println("3) Devolucion registrada para ISBN 1002");

            // retira un libro del catalogo
            library.retirarLibro(1010);
            System.out.println("4) Libro 1010 retirado del catalogo");

            // consulta stock actual
            int stock = library.consultarStock(1001);
            System.out.printf("5) Stock consultado para ISBN 1001: %d ejemplares%n", stock);

            // pide el listado ordenado por titulo
            System.out.println("6) Listado alfabetico por titulo:");
            Book[] ordenados = library.listarLibrosPorTitulo();
            for (Book book : ordenados) {
                // imprime cada libro ordenado
                System.out.printf("   - %s (ISBN %d) | stock: %d%n", book.getTitulo(), book.getIsbn(), book.getStock());
            }
        } catch (RuntimeException e) {
            System.err.println("Error durante la demostracion de operaciones: " + e.getMessage());
        }
    }
}
