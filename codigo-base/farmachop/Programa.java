/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Josse
 */
public class Programa {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ejercicio1();
    }

    public static void ejercicio1() {
        String[] suerosArray = ManejadorArchivosGenerico.leerArchivo("src/Sueros.txt");

        for (String sueros : suerosArray) {
            System.err.println(sueros);
        }
       
        //...
       
    }
}
