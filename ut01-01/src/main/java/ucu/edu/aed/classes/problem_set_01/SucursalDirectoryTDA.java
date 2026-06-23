package ucu.edu.aed.classes.problem_set_01;

import java.util.function.Predicate;
import java.util.function.Consumer;
import ucu.edu.aed.classes.LinkedList;
import ucu.edu.aed.utils.FileUtils;

// dir pa sucursales tal como pide el ej 23
// a diferencia del anterior que andaba con la de java utli, este usa la de nosotros (LinkedList)
public class SucursalDirectoryTDA {

    private final LinkedList<String> sucursales;

    public SucursalDirectoryTDA() {
        this.sucursales = new LinkedList<>();
    }

    // agrega una ciudad al fondo de todo
    public void agregar(String ciudad) {
        sucursales.agregar(validar(ciudad));
    }

    // chequea si existe o no ignorando mayus o minus
    public boolean buscar(String ciudad) {
        final String objetivo = normalizar(ciudad);
        Predicate<String> criterio = new Predicate<String>() {
            public boolean test(String c) {
                return c.equalsIgnoreCase(objetivo);
            }
        };
        return sucursales.buscar(criterio) != null;
    }

    // borra la primera vez q aparezca este loco
    public boolean quitar(String ciudad) {
        final String objetivo = normalizar(ciudad);
        Predicate<String> criterio = new Predicate<String>() {
            public boolean test(String c) {
                return c.equalsIgnoreCase(objetivo);
            }
        };
        // la saca exactito como estaba en la base
        String encontrada = sucursales.buscar(criterio);
        if (encontrada != null) {
            return sucursales.remover(encontrada);
        }
        return false;
    }

    // imprime los wachos y les mete el simbolo q le pases entre medios
    public String imprimir(String separador) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sucursales.tamaño(); i++) {
            sb.append(sucursales.obtener(i));
            sb.append(separador);
        }
        return sb.toString();
    }

    // lista uno bajo el otro tranca asi vemos rapido que ande
    public void listar() {
        for (int i = 0; i < sucursales.tamaño(); i++) {
            System.out.println(" - " + sucursales.obtener(i));
        }
    }

    public int cantidad() {
        return sucursales.tamaño();
    }

    public boolean estaVacio() {
        return sucursales.esVacio();
    }

    // para q lea el .txt con la dir q le pase
    public void cargarDesdeArchivo(String ruta) {
        FileUtils.leerLineas(ruta, new Consumer<String>() {
            public void accept(String linea) {
                if (linea == null) {
                    return;
                }
                if (!linea.isBlank()) {
                    agregar(linea);
                }
            }
        });
    }

    // cositas auxiliares pa que no explote
    private String validar(String ciudad) {
        String normalizada = normalizar(ciudad);
        if (normalizada.isBlank()) {
            throw new IllegalArgumentException("No me tires algo vacio");
        }
        return normalizada;
    }

    private String normalizar(String ciudad) {
        if (ciudad == null) {
            throw new IllegalArgumentException("Nulo el nombre de ciudad");
        }
        return ciudad.trim();
    }
}
