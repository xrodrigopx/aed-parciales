
package uy.edu.ucu.aed;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        TArbolDeProductos arbol = new TArbolDeProductos();

        // Cargar el árbol a partir del archivo
        arbol.cargarDesdeArchivo("src/main/resources/productos.csv");

        // Mostrar recorrido inorden (productos ordenados por identificador)
        System.out.println("Recorrido InOrden:");
        System.out.println(arbol.productosEnInOrden());

        // Mostrar recorrido preorden
        System.out.println("Recorrido PreOrden:");
        System.out.println(arbol.productosEnPreOrden());

        // Mostrar recorrido postorden
        System.out.println("Recorrido PostOrden:");
        System.out.println(arbol.productosEnPostOrden());

        // Mostrar cantidad total de productos
        System.out.println("Total de productos: " + arbol.cantidadDeProductos());
    }
}
