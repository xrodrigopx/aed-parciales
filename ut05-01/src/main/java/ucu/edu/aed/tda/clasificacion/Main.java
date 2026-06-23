package ucu.edu.aed.tda.clasificacion;

public class Main {

    public static void main(String[] args) {
        ClasificadorImpl clasificador = new ClasificadorImpl();
        GeneradorDatosGenericos generador = new GeneradorDatosGenericos();

        // --- Prueba con vector pequeño desordenado (Parte D, paso 1-5) ---
        int[] vectorPequeno = {42, 7, 19, 3, 55};
        System.out.println("Vector original:");
        imprimirVector(vectorPequeno);

        clasificador.insercionDirecta(vectorPequeno);
        System.out.println("Despues de insercionDirecta:");
        imprimirVector(vectorPequeno);
        System.out.println("Esta ordenado: " + clasificador.estaOrdenado(vectorPequeno));

        // --- Prueba con vectores de distintos tipos (Parte D, paso 6-8) ---
        int[] vectorAscendente = generador.generarDatosAscendentes();
        int[] vectorDescendente = generador.generarDatosDescendentes();
        int[] vectorAleatorio = generador.generarDatosAleatorios();

        System.out.println("\nInsercion Directa ");
        clasificador.insercionDirecta(vectorAscendente);
        System.out.println("Ascendente ordenado: " + clasificador.estaOrdenado(vectorAscendente));
        clasificador.insercionDirecta(vectorDescendente);
        System.out.println("Descendente ordenado: " + clasificador.estaOrdenado(vectorDescendente));
        clasificador.insercionDirecta(vectorAleatorio);
        System.out.println("Aleatorio ordenado: " + clasificador.estaOrdenado(vectorAleatorio));

        // Regenerar para Shell y Burbuja
        vectorAscendente = generador.generarDatosAscendentes();
        vectorDescendente = generador.generarDatosDescendentes();
        vectorAleatorio = generador.generarDatosAleatorios();
        Integer[] incrementos = {5, 3, 1};

        System.out.println("\nShellsort");
        clasificador.shell(vectorAscendente, incrementos);
        System.out.println("Ascendente ordenado: " + clasificador.estaOrdenado(vectorAscendente));
        clasificador.shell(vectorDescendente, incrementos);
        System.out.println("Descendente ordenado: " + clasificador.estaOrdenado(vectorDescendente));
        clasificador.shell(vectorAleatorio, incrementos);
        System.out.println("Aleatorio ordenado: " + clasificador.estaOrdenado(vectorAleatorio));

        // Regenerar para Burbuja
        vectorAscendente = generador.generarDatosAscendentes();
        vectorDescendente = generador.generarDatosDescendentes();
        vectorAleatorio = generador.generarDatosAleatorios();

        System.out.println("\nBurbuja");
        clasificador.burbuja(vectorAscendente);
        System.out.println("Ascendente ordenado: " + clasificador.estaOrdenado(vectorAscendente));
        clasificador.burbuja(vectorDescendente);
        System.out.println("Descendente ordenado: " + clasificador.estaOrdenado(vectorDescendente));
        clasificador.burbuja(vectorAleatorio);
        System.out.println("Aleatorio ordenado: " + clasificador.estaOrdenado(vectorAleatorio));
    }

    private static void imprimirVector(int[] datos) {
        for (int i = 0; i < datos.length; i++) {
            System.out.print(datos[i]);
            if (i < datos.length - 1) {
                System.out.print(" - ");
            }
        }
        System.out.println();
    }
}
