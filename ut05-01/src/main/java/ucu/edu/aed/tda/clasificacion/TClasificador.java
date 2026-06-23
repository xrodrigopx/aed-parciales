package ucu.edu.aed.tda.clasificacion;

public abstract class TClasificador {

    /**
     * Ordena el vector por inserción directa
     */
    abstract void insercionDirecta(int[] datos);

    /**
     * Ordena el vector utilizando shellsort con un vector de incrementos datos
     */
    abstract void shell(int[] datos, Integer[] incrementos);

    /**
     * Ordena el vector utilizando la técnica de Burbuja
     */
    abstract void burbuja(int[] datos);


    public void quicksort(int[] datos) {
        quicksort(datos, 0, datos.length - 1);
    }

    /**
     * método recursivo de quicksort
     */
    private void quicksort(int[] datos, int i, int j) {
        if (i <= j) {
            int indicePivote = obtenerPivote(datos, i, j);
            // debe ser un rango válido
            if (indicePivote >= i) {
                if (indicePivote < j) {
                    int pivote = datos[indicePivote];
                    int k = particion(datos, i, j, pivote);
                    quicksort(datos, i, k - 1);
                    quicksort(datos, k, j);
                }
            }
        }
    }

    /**
     * método auxiliar y utilizado en quicksort
     * devuelve un valor del vector ubicado entre i <= x < j
     */
    protected abstract int obtenerPivote(int[] datos, int i, int j);

    /**
     * método auxiliar y utilizado en quicksort
     */
    protected abstract int particion(int[] datos, int i, int j, int pivote);

    /**
     * Ordena el vector utilizando clasificación directa
     */
    public abstract void clasificacionDirecta(int[] datos);

    /**
     * Ordena el vector utilizando heapsort
     */
    public abstract void heapsort(int[] datos);

    /**
     * método auxiliar y utilizado en heapsort
     */
    protected abstract void desplazaElemento(int[] datos, int primero, int ultimo);

    /**
     * método de ayuda para intercambiar valores en un vector dados sus índices
     */
    protected void intercambiar(int[] datos, int i, int j) {
        int aux = datos[i];
        datos[i] = datos[j];
        datos[j] = aux;
    }

    /**
     * calcula el tiempo de ejecución de una tarea en milisegundos
     */
    public long medirTiempoDeEjecución(Runnable tarea) {
        long start = System.currentTimeMillis();

        tarea.run();

        return (System.currentTimeMillis() - start);
    }


}
