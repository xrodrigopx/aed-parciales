package ucu.edu.aed.classes.problem_set_01;

import java.util.List;
import java.util.Stack;

/*
 * ============================================================
 * EJERCICIO 19 — Parte 1: Pseudocode de controlCorchetes
 * ============================================================
 *
 * Especificación en lenguaje natural:
 *   Dada una lista de caracteres que representa el código fuente de un
 *   programa, determina si la secuencia de llaves ({ }) está correctamente
 *   balanceada: cada llave de apertura '{' debe tener una llave de cierre '}'
 *   correspondiente, en el orden correcto.
 *
 * Precondiciones:
 *   - listaDeEntrada no es nula (puede ser vacía).
 *   - Los únicos caracteres relevantes son '{' y '}'; el resto se ignoran.
 *
 * Postcondiciones:
 *   - Devuelve VERDADERO si la secuencia de llaves está bien formada.
 *   - Devuelve FALSO en cualquier otro caso (cierre sin apertura, o
 *     aperturas sin cerrar al finalizar).
 *   - La listaDeEntrada no es modificada.
 *
 * Seudocódigo:
 * ─────────────────────────────────────────────────────────────
 * función controlCorchetes(listaDeEntrada: Lista de Caracteres): Booleano
 *
 *   pila <- nueva Pila vacía
 *
 *   para cada carácter c en listaDeEntrada hacer
 *     si c = '{' entonces
 *       pila.meter(c)
 *     sino si c = '}' entonces
 *       si pila.esVacía() entonces
 *         devolver FALSO        // cierre sin apertura previa
 *       fin si
 *       pila.sacar()            // eliminar la apertura que acabamos de cerrar
 *     fin si
 *     // cualquier otro carácter se ignora
 *   fin para
 *
 *   devolver pila.esVacía()     // VERDADERO solo si no quedó ninguna apertura sin cerrar
 *
 * fin función
 * ─────────────────────────────────────────────────────────────
 *
 * Casos de prueba:
 *   Entrada: ""          → VERDADERO  (vacío, trivialmente correcto)
 *   Entrada: "{}"        → VERDADERO
 *   Entrada: "{}{{}}    → VERDADERO  (ejemplo bien formado del enunciado)
 *   Entrada: "{{}{{}    → FALSO      (ejemplo mal formado del enunciado)
 *   Entrada: "}"         → FALSO      (cierre sin apertura)
 *   Entrada: "{"         → FALSO      (apertura sin cierre)
 *   Entrada: "}{"        → FALSO      (orden invertido)
 *
 * Análisis de complejidad (Parte 2):
 *   - Tiempo: O(n), donde n es la longitud de listaDeEntrada.
 *     Se recorre la lista una sola vez; cada carácter produce a lo sumo
 *     una operación meter/sacar, ambas O(1) en una pila enlazada.
 *   - Espacio adicional (pila): O(n) en el peor caso (todos '{').
 * ============================================================
 */

// ejercicio 19 / 26 - analizar que los corchetes o llaves abran y cierren bien
// complejidad temporal es O(n) porque hay que recorrer toda la lista ingresada vez por vez
// la memoria extra es O(n) tambien por si de casualidad todos son llaves de abrir
public class Expresion {

    private final List<Character> caracteres;

    // guardo la lista de caracteres a leer
    public Expresion(List<Character> caracteres) {
        this.caracteres = caracteres;
    }

    // ejercicio 26: chequeo principal de llaves usando java.util.Stack
    public boolean controlCorchetes(List<Character> listaDeEntrada) {
        Stack<Character> pila = new Stack<>();

        for (char c : listaDeEntrada) {
            if (c == '{') {
                // meto a la pila porque la abri y hay que cerrarla
                pila.push(c);
            } else if (c == '}') {
                // encontre una de cerrar pero no tenia ninguna abriendo, ta roto todo
                if (pila.empty()) {
                    return false;
                }
                // como acabo de cerrar elimino de la pila a su pareja de apertura
                pila.pop();
            }
            // los otros caracteres no me importan a mi
        }

        // por ultimo veo si no quedo nada colgado sin cerrar
        return pila.empty();
    }

    // llama al original pero sin parametros
    public boolean controlCorchetes() {
        return controlCorchetes(this.caracteres);
    }
}
