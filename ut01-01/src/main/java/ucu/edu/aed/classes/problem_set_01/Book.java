package ucu.edu.aed.classes.problem_set_01;

import java.util.Objects;

/**
 * Entidad simple que representa un libro disponible para prestamos.
 */
public class Book {
    // codigo identificador del libro
    private int isbn;
    // titulo del ejemplar
    private String titulo;
    // precio de reposicion
    private double precio;
    // cantidad disponible
    private int stock;

    // crea el libro con sus datos base
    public Book(int isbn, String titulo, double precio, int stock) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.precio = precio;
        this.stock = stock;
    }


    // devuelve el isbn
    public int getIsbn() {
        return isbn;
    }

    // devuelve el titulo
    public String getTitulo() {
        return titulo;
    }


    // actualiza el isbn
    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    // actualiza el titulo
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    // devuelve el precio
    public double getPrecio() {
        return precio;
    }

    // actualiza el precio
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    // devuelve el stock
    public int getStock() {
        return stock;
    }

    // actualiza el stock
    public void setStock(int stock) {
        this.stock = stock;
    }


    // suma unidades al stock
    public void agregarStock(int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad a agregar debe ser positiva");
        }
        this.stock += cantidad;
    }

    // resta unidades validando que alcance
    public void descontarStock(int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad a descontar debe ser positiva");
        }
        if (cantidad > stock) {
            throw new IllegalArgumentException("No se puede descontar más stock del disponible");
        }
        this.stock -= cantidad;
    }


    @Override
    // compara libros por isbn
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Book other = (Book) obj;
        return isbn == other.isbn;
    }

    @Override
    // acompaña al equals con el isbn
    public int hashCode() {
        return Objects.hash(isbn);
    }
}
