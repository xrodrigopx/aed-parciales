package ucu.edu.aed.ej10;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class UtilidadesMapa {

    // Parte 1: elimina todas las entradas cuyo valor sea null.
    public static void eliminarValoresNulos(Map<String, String> mapa) {
        if (mapa == null) {
            return;
        }
        List<String> clavesAEliminar = new ArrayList<>();
        for (Map.Entry<String, String> entrada : mapa.entrySet()) {
            if (entrada.getValue() == null) {
                clavesAEliminar.add(entrada.getKey());
            }
        }
        for (String clave : clavesAEliminar) {
            mapa.remove(clave);
        }
    }

    // Parte 2: devuelve un nuevo mapa con claves y valores intercambiados.
    // Lanza IllegalArgumentException si hay valores duplicados en el original.
    public static Map<String, String> invertirMapa(Map<String, String> original) {
        if (original == null) {
            return null;
        }
        Map<String, String> invertido = new HashMap<>();
        for (Map.Entry<String, String> entrada : original.entrySet()) {
            String valor = entrada.getValue();
            if (invertido.containsKey(valor)) {
                throw new IllegalArgumentException("Valor duplicado encontrado: " + valor);
            }
            invertido.put(valor, entrada.getKey());
        }
        return invertido;
    }

    // Parte 3: ordena la lista por longitud de cadena (menor a mayor).
    // Si dos cadenas tienen la misma longitud, las ordena lexicográficamente.
    public static void ordenarPorLongitud(List<String> lista) {
        if (lista == null) {
            return;
        }
        Collections.sort(lista, new Comparator<String>() {
            public int compare(String a, String b) {
                if (a.length() != b.length()) {
                    return a.length() - b.length();
                }
                return a.compareTo(b);
            }
        });
    }

    // Parte 4: dado una lista de líneas y un número N, imprime N líneas al azar.
    public static void imprimirLineasAleatorias(List<String> lineas, int cantidad) {
        if (lineas == null) {
            return;
        }
        if (lineas.isEmpty()) {
            return;
        }
        if (cantidad <= 0) {
            return;
        }
        Random random = new Random();
        for (int i = 0; i < cantidad; i++) {
            int indice = random.nextInt(lineas.size());
            System.out.println(lineas.get(indice));
        }
    }
}
