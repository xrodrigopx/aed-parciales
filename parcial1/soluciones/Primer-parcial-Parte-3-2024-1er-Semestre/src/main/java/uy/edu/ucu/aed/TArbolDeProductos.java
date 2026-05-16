
package uy.edu.ucu.aed;

import java.util.List;

public class TArbolDeProductos extends TArbolBB<Producto> {

    /**
     * Carga productos desde un archivo CSV con formato: identificador,nombre
     * Inserta cada producto en el árbol usando el identificador como etiqueta.
     *
     * @param rutaArchivo Ruta del archivo a leer.
     */
    public void cargarDesdeArchivo(String rutaArchivo) {
        String[] lineas = ManejadorArchivosGenerico.leerArchivo(rutaArchivo);
        for (String linea : lineas) {
            if (linea != null && !linea.trim().isEmpty()) {
                String[] partes = linea.split(",");
                if (partes.length >= 2) {
                    int id = Integer.parseInt(partes[0].trim());
                    String nombre = partes[1].trim();
                    Producto p = new Producto(id, nombre);
                    this.insertar(id, p);
                }
            }
        }
    }

    /**
     * Retorna una cadena con los productos en inorden (por identificador ascendente),
     * con formato "id:nombre" separados por " - ".
     *
     * @return Cadena con los productos en inorden.
     */
    public String productosEnInOrden() {
        List<Producto> lista = this.inOrden();
        if (lista == null || lista.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        boolean primero = true;
        for (Producto p : lista) {
            if (!primero) {
                sb.append(" - ");
            }
            sb.append(p.getIdentificador()).append(":").append(p.getNombre());
            primero = false;
        }
        return sb.toString();
    }

    /**
     * Retorna una cadena con los productos en preorden,
     * con formato "id:nombre" separados por " - ".
     *
     * @return Cadena con los productos en preorden.
     */
    public String productosEnPreOrden() {
        List<Producto> lista = this.preOrden();
        if (lista == null || lista.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        boolean primero = true;
        for (Producto p : lista) {
            if (!primero) {
                sb.append(" - ");
            }
            sb.append(p.getIdentificador()).append(":").append(p.getNombre());
            primero = false;
        }
        return sb.toString();
    }

    /**
     * Retorna una cadena con los productos en postorden,
     * con formato "id:nombre" separados por " - ".
     *
     * @return Cadena con los productos en postorden.
     */
    public String productosEnPostOrden() {
        List<Producto> lista = this.postOrden();
        if (lista == null || lista.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        boolean primero = true;
        for (Producto p : lista) {
            if (!primero) {
                sb.append(" - ");
            }
            sb.append(p.getIdentificador()).append(":").append(p.getNombre());
            primero = false;
        }
        return sb.toString();
    }

    /**
     * Retorna el total de productos en el árbol.
     *
     * @return Cantidad de productos.
     */
    public int cantidadDeProductos() {
        if (esVacio()) {
            return 0;
        }
        return raiz.obtenerTamaño();
    }
}
