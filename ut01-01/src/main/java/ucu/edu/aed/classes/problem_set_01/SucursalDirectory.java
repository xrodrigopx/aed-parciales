package ucu.edu.aed.classes.problem_set_01;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import java.util.function.Consumer;
import ucu.edu.aed.utils.FileUtils;

/**
 * Directorio simple basado en {@link LinkedList} que mantiene los nombres de
 * las
 * ciudades donde existen sucursales.
 */
public class SucursalDirectory {

    private static final String SUCURSALES_PATH = "src/main/resources/sucursales.txt";
    private final LinkedList<String> sucursales = new LinkedList<>();

    /**
     * Carga una ciudad nueva al directorio.
     *
     * @param ciudad nombre de la ciudad
     */
    public void agregar(String ciudad) {
        sucursales.add(validar(ciudad));
    }

    /**
     * Busca si existe una ciudad en el directorio (ignora mayúsculas/minúsculas).
     */
    public boolean buscar(String ciudad) {
        return indiceDeCiudad(ciudad) >= 0;
    }

    /**
     * Quita la primera coincidencia de la ciudad indicada.
     */
    public boolean quitar(String ciudad) {
        int indice = indiceDeCiudad(ciudad);
        if (indice >= 0) {
            sucursales.remove(indice);
            return true;
        }
        return false;
    }

    /**
     * Devuelve una copia inmutable de las sucursales.
     */
    public List<String> listar() {
        return Collections.unmodifiableList(new ArrayList<>(sucursales));
    }

    /**
     * Cantidad de sucursales registradas.
     */
    public int cantidad() {
        return sucursales.size();
    }

    /**
     * Indica si no hay sucursales cargadas.
     */
    public boolean estaVacio() {
        return sucursales.isEmpty();
    }

    /**
     * Carga todas las sucursales enumeradas en el archivo de recursos (una por
     * línea).
     */
    public void cargarDesdeArchivo() {
        // lee cada linea y carga solo las validas
        FileUtils.leerLineas(SUCURSALES_PATH, new Consumer<String>() {
            public void accept(String linea) {
                if (esLineaValida(linea)) {
                    agregar(linea);
                }
            }
        });
    }

    // limpia y valida el nombre de ciudad
    private static String validar(String ciudad) {
        String normalizada = normalizar(ciudad);
        if (normalizada.isBlank()) {
            throw new IllegalArgumentException("El nombre de la ciudad no puede ser vacío");
        }
        return normalizada;
    }

    // recorta espacios y controla nulos
    private static String normalizar(String ciudad) {
        if (ciudad == null) {
            throw new IllegalArgumentException("La ciudad no puede ser nula");
        }
        return ciudad.trim();
    }

    // busca la ciudad ignorando mayusculas y minusculas
    private int indiceDeCiudad(String ciudad) {
        String objetivo = normalizar(ciudad);
        int indice = 0;
        Iterator<String> iterator = sucursales.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().equalsIgnoreCase(objetivo)) {
                return indice;
            }
            indice++;
        }
        return -1;
    }

    // ignora lineas vacias o en blanco
    private static boolean esLineaValida(String linea) {
        if (linea == null) {
            return false;
        }
        return !linea.isBlank();
    }
}
