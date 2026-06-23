package ucu.edu.aed.tda.clasificacion;

public class ClasificadorImpl extends TClasificador {

    // agarra cada elemento y lo mete en su lugar dentro de lo ya ordenado
    @Override
    void insercionDirecta(int[] datos) {
        for (int i = 1; i < datos.length; i++) {
            int clave = datos[i];
            int j = i - 1;
            boolean continuar = true;
            while (continuar) {
                if (j < 0) {
                    continuar = false;
                } else if (datos[j] <= clave) {
                    continuar = false;
                } else {
                    datos[j + 1] = datos[j];
                    j--;
                }
            }
            datos[j + 1] = clave;
        }
    }

    // insercion directa pero con saltos de tamanio h, se repite para cada incremento
    @Override
    void shell(int[] datos, Integer[] incrementos) {
        for (int inc = 0; inc < incrementos.length; inc++) {
            int h = incrementos[inc];
            for (int i = h; i < datos.length; i++) {
                int clave = datos[i];
                int j = i - h;
                boolean continuar = true;
                while (continuar) {
                    if (j < 0) {
                        continuar = false;
                    } else if (datos[j] <= clave) {
                        continuar = false;
                    } else {
                        datos[j + h] = datos[j];
                        j -= h;
                    }
                }
                datos[j + h] = clave;
            }
        }
    }

    // en cada pasada el mayor va al fondo, cada vuelta el rango se achica en uno
    @Override
    void burbuja(int[] datos) {
        for (int i = 0; i < datos.length - 1; i++) {
            for (int j = 0; j < datos.length - 1 - i; j++) {
                if (datos[j] > datos[j + 1]) {
                    intercambiar(datos, j, j + 1);
                }
            }
        }
    }

    // busca el minimo del resto y lo pone al principio, repite
    @Override
    public void clasificacionDirecta(int[] datos) {
        for (int i = 0; i < datos.length - 1; i++) {
            int indiceMenor = i;
            for (int j = i + 1; j < datos.length; j++) {
                if (datos[j] < datos[indiceMenor]) {
                    indiceMenor = j;
                }
            }
            if (indiceMenor != i) {
                intercambiar(datos, i, indiceMenor);
            }
        }
    }

    // toma el elemento del medio como pivote
    @Override
    protected int obtenerPivote(int[] datos, int i, int j) {
        return (i + j) / 2;
    }

    // mueve los punteros hacia adentro y swapea cuando se cruzan elementos fuera de lugar
    // los if anidados evitan que izquierda o derecha se salgan del rango
    @Override
    protected int particion(int[] datos, int i, int j, int pivote) {
        int izquierda = i;
        int derecha = j;
        boolean continuar = true;
        while (continuar) {
            boolean avanzarIzq = true;
            while (avanzarIzq) {
                if (izquierda > j) {
                    avanzarIzq = false;
                } else if (datos[izquierda] >= pivote) {
                    avanzarIzq = false;
                } else {
                    izquierda++;
                }
            }
            boolean avanzarDer = true;
            while (avanzarDer) {
                if (derecha < i) {
                    avanzarDer = false;
                } else if (datos[derecha] <= pivote) {
                    avanzarDer = false;
                } else {
                    derecha--;
                }
            }
            if (izquierda > derecha) {
                continuar = false;
            } else {
                intercambiar(datos, izquierda, derecha);
                izquierda++;
                derecha--;
            }
        }
        return izquierda;
    }

    // baja el elemento desde primero hasta donde le corresponde en el heap
    @Override
    protected void desplazaElemento(int[] datos, int primero, int ultimo) {
        int padre = primero;
        int hijoIzq = 2 * padre + 1;
        boolean continuar = true;
        while (continuar) {
            if (hijoIzq > ultimo) {
                continuar = false;
            } else {
                int hijoCandidato = hijoIzq;
                int hijoDer = hijoIzq + 1;
                if (hijoDer <= ultimo) {
                    if (datos[hijoDer] > datos[hijoIzq]) {
                        hijoCandidato = hijoDer;
                    }
                }
                if (datos[padre] >= datos[hijoCandidato]) {
                    continuar = false;
                } else {
                    intercambiar(datos, padre, hijoCandidato);
                    padre = hijoCandidato;
                    hijoIzq = 2 * padre + 1;
                }
            }
        }
    }

    // primero arma el heap, despues va sacando el maximo y lo manda al final
    @Override
    public void heapsort(int[] datos) {
        for (int i = datos.length / 2 - 1; i >= 0; i--) {
            desplazaElemento(datos, i, datos.length - 1);
        }
        for (int i = datos.length - 1; i > 0; i--) {
            intercambiar(datos, 0, i);
            desplazaElemento(datos, 0, i - 1);
        }
    }

    // recorre el vector y si encuentra un par desordenado devuelve false
    public boolean estaOrdenado(int[] datos) {
        for (int i = 0; i < datos.length - 1; i++) {
            if (datos[i] > datos[i + 1]) {
                return false;
            }
        }
        return true;
    }
}
