package ucu.edu.aed.classes.problem_set_01;

/**
 * Punto de entrada para demostrar las operaciones requeridas sobre el
 * directorio de sucursales usando {@link java.util.LinkedList}.
 */
public class SucursalesApp {

    public static void main(String[] args) {
        // arma el directorio y lo completa desde archivo
        SucursalDirectory directorio = new SucursalDirectory();
        directorio.cargarDesdeArchivo();

        // suma una sucursal manual
        directorio.agregar("Lima");

        // verifica si existe una sucursal
        System.out.println("¿Existe la sucursal de Lima? " + directorio.buscar("lima"));

        // elimina una sucursal si esta
        boolean quitada = directorio.quitar("Madrid");
        System.out.println("¿Se removió Madrid? " + quitada);

        // muestra todo el listado
        System.out.println("Listado de sucursales:");
        for (String ciudad : directorio.listar()) {
            System.out.println(" - " + ciudad);
        }

        // informa cuantas hay cargadas
        System.out.println("Cantidad total: " + directorio.cantidad());

        // indica si no queda ninguna
        System.out.println("¿Directorio vacío? " + directorio.estaVacio());
    }
}
