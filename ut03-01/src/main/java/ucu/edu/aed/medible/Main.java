package ucu.edu.aed.medible;


import ucu.edu.aed.medible.lib.Medible;
import ucu.edu.aed.medible.lib.Medicion;
import ucu.edu.aed.medible.medibles.MedicionBuscarLinkedList;
import ucu.edu.aed.tda.trie.TTrie;
import ucu.edu.aed.tda.trie.impl.Trie;
import ucu.edu.aed.utils.FileUtils;

import java.util.*;

public class Main {

    private static final int REPETICIONES = 100;

    public static void main(String[] args) {
        TTrie<String> trie = new Trie<String>();
        LinkedList<String> linkedList = new LinkedList<>();
        ArrayList<String> arrayList = new ArrayList<>();
        Map<String, String> hashMap = new HashMap<>();
        Map<String, String> treeMap = new TreeMap<>();

        List<String> palabrasParaAgregar = new LinkedList<>();
        List<String> palabrasParaBuscar = new LinkedList<>();
        FileUtils.leerLineas("./ut03/listado-general-desordenado.txt", new java.util.function.Consumer<String>() {
            public void accept(String linea) {
                palabrasParaAgregar.add(linea);
            }
        });
        FileUtils.leerLineas("./ut03/listado-general-palabrasBuscar.txt", new java.util.function.Consumer<String>() {
            public void accept(String linea) {
                palabrasParaBuscar.add(linea);
            }
        });

        for (String p : palabrasParaAgregar) {
            // insertar la palabra p en el trie
            // TODO
            // insertar la palabra p en el linkedList
            linkedList.add(p);
            // insertar la palabra p en el arrayList
//            arrayList.add(p);
            // insertar la palabra p en el hashMap
            // TODO
            // insertar la palabra p en el treeMap
            // TODO
        }

        List<Medible<List<String>>> medibles = new LinkedList<>();
        medibles.add(new MedicionBuscarLinkedList(linkedList));

        // TODO implementar MedicionBuscarArrayList
//        medibles.add(new MedicionBuscarArrayList(arrayList));

        // TODO implementar MedicionBuscarTrie
        // medibles.add(new MedicionBuscarTrie(trie));

        // TODO implementar MedicionBuscarHashMap
        // medibles.add(new MedicionBuscarHashMap(hashMap));

        // TODO implementar MedicionBuscarTreeMap
        // medibles.add(new MedicionBuscarTreeMap(treeMap));

        StringBuilder sb = new StringBuilder();
        sb.append("algoritmo,tiempo,memoria\n");

        for (Medible<List<String>> m : medibles) {
            Medicion mi = m.medir(REPETICIONES, palabrasParaBuscar);
            mi.print();
            sb
                    .append(mi.toCSV())
                    .append("\n");
        }

        FileUtils.escribirLineas("./salida.csv", sb.toString());
    }
}