package ucu.edu.aed.classes;

public class ejercicio7 {


    int[] arreglo = new int[10];
    int contador = 0;

    public void Intercambia(int num1, int num2) {
        // se inicializa en -1 para distinguir "no encontrado" de "esta en indice 0"
        int pos1 = -1;
        int pos2 = -1;
        for (int i = 0; i < arreglo.length; i++) {
            if(arreglo[i] == num1) {
                pos1 = i;
            } else if (arreglo[i] == num2) {
                pos2 = i;
            }
        }
        // solo intercambia si ambos valores fueron encontrados en el arreglo
        if (pos1 != -1) {
            if (pos2 != -1) {
                arreglo[pos1] = num2;
                arreglo[pos2] = num1;
            }
        }
    }

    public void ordenar() {
        for (int i = 0; i < arreglo.length; i++){
            // j > i asegura que se comparen todos los pares hasta la posicion i
            for (int j = arreglo.length-1; j > i; j--) {
                Intercambia(arreglo[j], arreglo[j-1]);
                contador++;
            }
        }
        System.out.print(contador);
    }


    
}
