package ucu.edu.aed.classes.problem_set_01;

// punto de entrada pa probar el sucursaldirectory usando la lista que arme io
// tiene las 4 preguntas de opcion multiple que pide la letra
public class SucursalesAppTDA {

    // donde buscar los txt magicos
    private static final String RESOURCES = "src/main/resources/";

    public static void main(String[] args) {

        System.out.println("=== PREGUNTA 1: Cargar suc1.txt ===");
        SucursalDirectoryTDA dir = new SucursalDirectoryTDA();
        dir.cargarDesdeArchivo(RESOURCES + "suc1.txt");

        System.out.println("Ciudades cargadas:");
        dir.listar();
        System.out.println("Total de elementos: " + dir.cantidad());
        // debe dar 105

        System.out.println("\n=== PREGUNTA 2: Eliminar Chicago ===");
        boolean eliminada = dir.quitar("Chicago");
        System.out.println("Chicago se elimino? " + eliminada);
        System.out.println("Lista tras borrarlo:");
        dir.listar();
        System.out.println("Total dspues de borrar de chicago: " + dir.cantidad());
        
        System.out.println("Ciudad que sigue a Hong Kong:");
        buscarSiguiente(dir, "Hong Kong");

        System.out.println("\n=== PREGUNTA 3: Cargar suc2.txt y eliminar Shenzen/Tokio ===");
        SucursalDirectoryTDA dir2 = new SucursalDirectoryTDA();
        dir2.cargarDesdeArchivo(RESOURCES + "suc2.txt");
        System.out.println("Ciudades en suc2.txt antes que borremos: " + dir2.cantidad());
        dir2.listar();

        dir2.quitar("Shenzen");
        dir2.quitar("Tokio");

        System.out.println("Ciudades que quedan:");
        dir2.listar();
        System.out.println("Resto: " + dir2.cantidad());
        // suc2 tiene 7 de arranque y sacas 2 -> 5

        System.out.println("\n=== PREGUNTA 4: Cargar suc3 e imprimir con ';' ===");
        SucursalDirectoryTDA dir3 = new SucursalDirectoryTDA();
        dir3.cargarDesdeArchivo(RESOURCES + "suc3.txt");
        String resultado = dir3.imprimir(";");
        System.out.println("El Resultado del Imprimir(\";\"):");
        System.out.println(resultado);
        // Montreal;Caracas;Tulsa;Mobile;Vancouver;
    }

    // funcion de ayuda pa buscar la ciudad que sigue
    private static void buscarSiguiente(SucursalDirectoryTDA dir, String ciudad) {
        
        // saco lo que tiene y lo meto en array trucho q armo rapido pa iterar
        int n = dir.cantidad();
        String[] ciudades = new String[n];
        
        String[] all = dir.imprimir("\n").trim().split("\n");
        for (int i = 0; i < all.length; i++) {
            ciudades[i] = all[i].trim();
        }
        for (int i = 0; i < ciudades.length - 1; i++) {
            if (ciudades[i].equalsIgnoreCase(ciudad)) {
                System.out.println("La q sigue justo a '" + ciudad + "' es: " + ciudades[i + 1]);
                return;
            }
        }
        System.out.println("No ta la ciudad '" + ciudad + "' o justo es la del fondo loco.");
    }
}
