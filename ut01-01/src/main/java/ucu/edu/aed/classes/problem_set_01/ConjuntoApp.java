package ucu.edu.aed.classes.problem_set_01;

import ucu.edu.aed.classes.Set;
import ucu.edu.aed.tda.TDAConjunto;

// main que arma la logica para probar con los cursos pf y aed
public class ConjuntoApp {

    public static void main(String[] args) {

        // instancio mis dos conjuntos usando mi objeto
        Set<Alumno> aed1 = new Set<>();   // Algoritmos 1
        Set<Alumno> pf   = new Set<>();   // Programacion funcional

        // hago algunos guachos de prueba truchos
        Alumno a1 = new Alumno(1001, "Ana",     "García");
        Alumno a2 = new Alumno(1002, "Bruno",   "López");
        Alumno a3 = new Alumno(1003, "Carla",   "Martínez");
        Alumno a4 = new Alumno(1004, "Diego",   "Pérez");
        Alumno a5 = new Alumno(1005, "Elena",   "Rodríguez");
        Alumno a6 = new Alumno(1006, "Felipe",  "Sánchez");
        Alumno a7 = new Alumno(1007, "Gabriela","Torres");

        // inserto en curso Algoritmos
        aed1.agregar(a1);   // Ana        esta tb en en PF
        aed1.agregar(a2);   // Bruno      esta tb en en PF
        aed1.agregar(a3);   // Carla      esta tb en en PF
        aed1.agregar(a4);   // Diego      este loco solo esta aca
        aed1.agregar(a5);   // Elena      esta gurisa tmbn solo esta aca

        // y a la clase de Prog Funcional agrego tmb
        pf.agregar(a1);     
        pf.agregar(a2);     
        pf.agregar(a3);     
        pf.agregar(a6);     
        pf.agregar(a7);     

        System.out.println("=== Curso AED1 ===");
        imprimirConjunto(aed1);

        System.out.println("\n=== Curso PF ===");
        imprimirConjunto(pf);

        // uno todo en un conjunto gigante (todos sin que esten repetidos)
        System.out.println("\n=== UNIÓN (AED1 ∪ PF) ===");
        System.out.println("Metidos en uno o el otro:");
        TDAConjunto<Alumno> union = aed1.union(pf);
        imprimirConjunto(union);

        // saco cuales tienen metidos los 2 de casualidad
        System.out.println("\n=== INTERSECCIÓN (AED1 ∩ PF) ===");
        System.out.println("Cursando ambos de simultaneo:");
        TDAConjunto<Alumno> interseccion = aed1.interseccion(pf);
        imprimirConjunto(interseccion);
    }

    // funcion pa imprimir asi no copio pego tanto despues
    private static void imprimirConjunto(TDAConjunto<Alumno> conjunto) {
        if (conjunto.esVacio()) {
            System.out.println("  (vacio)");
            return;
        }
        for (int i = 0; i < conjunto.tamaño(); i++) {
            System.out.println("  " + conjunto.obtener(i));
        }
        System.out.println("  Total: " + conjunto.tamaño());
    }
}
