package ucu.edu.aed.classes.problem_set_01;

import java.util.Comparator;
import java.util.function.Predicate;

import ucu.edu.aed.classes.LinkedList;
import ucu.edu.aed.tda.TDALista;

/**
 * Gestiona un catálogo de libros respaldado por {@link LinkedList}.
 * Se encarga de registrar compras, préstamos y devoluciones.
 */
public class Library {

    // lista principal del catalogo
    private final LinkedList<Book> lista;

    // arma la biblioteca vacia
    public Library() {
        this.lista = new LinkedList<>();
    }

    // registra una compra o alta y devuelve el total invertido
    public double compraLibros(int codigoLibro, String tituloLibro, double precioReposicion, int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser positiva");
        }

        // busca si ya existe el libro por isbn
        Book existente = buscarPorIsbn(codigoLibro);
        if (existente == null) {
            // si no existe, crea un libro nuevo
            Book nuevo = new Book(codigoLibro, tituloLibro, precioReposicion, cantidad);
            lista.agregar(nuevo);
        } else {
            // si existe, actualiza datos y suma stock
            existente.setTitulo(tituloLibro);
            existente.setPrecio(precioReposicion);
            existente.agregarStock(cantidad);
        }

        return precioReposicion * cantidad;
    }

    // procesa un prestamo o una devolucion
    public void prestaYDevuelve(int codigoLibro, String tipoOperacion, int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser positiva");
        }

        // valida que el libro exista antes de tocar stock
        Book libro = buscarPorIsbn(codigoLibro);
        if (libro == null) {
            throw new IllegalArgumentException("El libro con codigo " + codigoLibro + " no existe en el catalogo");
        }

        if ("Prestamo".equalsIgnoreCase(tipoOperacion)) {
            // Si la cantidad solicitada excede el stock, se presta hasta donde alcance
            int cantidadAPrestar = Math.min(cantidad, libro.getStock());
            if (cantidadAPrestar > 0) {
                libro.descontarStock(cantidadAPrestar);
            } else {
                System.out.println("No hay stock para prestar el libro: " + codigoLibro);
            }
        } else if ("Devolucion".equalsIgnoreCase(tipoOperacion)) {
            // en devolucion se suma stock
            libro.agregarStock(cantidad);
        } else {
            throw new IllegalArgumentException("Tipo de operacion desconocido: " + tipoOperacion);
        }
    }

    // busca un libro por isbn dentro del catalogo
    private Book buscarPorIsbn(final int codigoLibro) {
        Predicate<Book> criterio = new Predicate<Book>() {
            public boolean test(Book book) {
                return book.getIsbn() == codigoLibro;
            }
        };
        return lista.buscar(criterio);
    }

    // saca un libro del catalogo
    public boolean retirarLibro(int codigoLibro) {
        Book libro = buscarPorIsbn(codigoLibro);
        if (libro == null) {
            throw new IllegalArgumentException("No existe un libro con codigo " + codigoLibro);
        }
        return lista.remover(libro);
    }

    // consulta stock de un libro puntual
    public int consultarStock(int codigoLibro) {
        Book libro = buscarPorIsbn(codigoLibro);
        if (libro == null) {
            throw new IllegalArgumentException("No existe un libro con codigo " + codigoLibro);
        }
        return libro.getStock();
    }

    // devuelve los libros ordenados por titulo
    public Book[] listarLibrosPorTitulo() {
        Comparator<Book> comp = new Comparator<Book>() {
            public int compare(Book a, Book b) {
                return a.getTitulo().compareToIgnoreCase(b.getTitulo());
            }
        };
        TDALista<Book> ordenada = lista.ordenar(comp);
        Object[] arr = ((LinkedList<Book>) ordenada).aArreglo();
        Book[] resultado = new Book[arr.length];
        for (int i = 0; i < arr.length; i++) {
            resultado[i] = (Book) arr[i];
        }
        return resultado;
    }

    // imprime el inventario completo
    public void imprimirInventario() {
        if (lista.esVacio()) {
            System.out.println("No hay libros cargados en la biblioteca.");
            return;
        }

        System.out.println("Inventario actualizado:");
        for (int i = 0; i < lista.tamaño(); i++) {
            // toma cada libro y lo muestra en pantalla
            Book libro = lista.obtener(i);
            System.out.printf("- %d | %s | stock: %d | precio: %.2f%n", libro.getIsbn(), libro.getTitulo(),
                    libro.getStock(), libro.getPrecio());
        }
    }
}
