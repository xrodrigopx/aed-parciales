ALGORITMOS Y ESTRUCTURAS DE DATOS
 PRIMER PARCIAL (SEGUNDO SEMESTRE 2024)
PARTE 3: Ejercicio de programación en Java
Duración: 60 minutos

Escenario

En genealogía, un árbol genealógico es una representación de las relaciones familiares a lo
largo de generaciones. Tradicionalmente, los hijos se encuentran en los niveles inferiores y
los padres en los superiores. Sin embargo, en esta variación del problema, invertimos
el árbol: los padres están en los niveles inferiores, y sus descendientes directos están
en los superiores. De este modo, cada nodo tiene a lo sumo dos hijos, que representan al
padre y la madre, y las ramas suben a través de las generaciones.

Este tipo de estructura invertida es útil para analizar la ascendencia de una persona. En
lugar de ver cómo se expande la familia hacia abajo, nos enfocamos en cómo se consolida
hacia arriba, identificando el linaje que conecta a cada persona con sus padres, abuelos,
bisabuelos, y así sucesivamente.

Problema a resolver: Cálculo de los grados de parentesco

El problema consiste en calcular los grados de parentesco entre dos personas en un
árbol genealógico invertido, diferenciando si el parentesco es por consanguinidad o
político. En genealogía, el grado de parentesco mide cuán cercana es la relación entre dos
individuos en función de su ascendencia, y puede estar vinculado ya sea por lazos de
sangre (consanguinidad) o por matrimonio (parentesco político).

El árbol genealógico invertido sigue la misma estructura que hemos planteado antes,
donde cada persona tiene hasta dos padres (representados como nodos hijos). El objetivo
es rastrear estas conexiones para determinar la cercanía de dos personas y el tipo de
parentesco.

Definiciones clave

•  Parentesco por consanguinidad: Relación directa de sangre entre dos personas

(padres, abuelos, bisabuelos, etc.).

•  Parentesco por afinidad o político: Relación establecida por matrimonio (ej.,

suegros, cuñados, etc.).

PARTE  1: Funcionalidad a desarrollar (vale 45%)

Implementar, en la clase Genealogia, el método calcularParentesco y todos los métodos
que sean necesarios para completarlo.

PARTE 2: PROGRAMA (vale 30%)

1.  Replicar en la clase Main, con las estructuras apropiadas, el siguiente árbol:

Juan

/    \

Pedro   Marta

/   \     /   \

José  Ana  Luis  Clara

2.  Realizar las invocaciones necesarias para calcular los grados y tipos de

parentescos entre:
a.  Juan y Ana
b.  Juan y una Persona que no se encuentre en el árbol
c.  José y Marta

3.  Emitir un archivo de salida, de nombre "Resultados.txt" con cada resultado del paso

anterior y el listado de personas por la cual se recorre para llegar al grado de
parentesco indicado.

PARTE 3: TEST CASES (vale 25%)

Implementa el o los Casos de Prueba necesarios para verificar el correcto funcionamiento
del método implementado (en la clase GenealogiaTests_JUnit5).

NOTAS IMPORTANTES:

•  Se proveen las interfaces y clases necesarias. Deben implementarse los

métodos necesarios (respetando las firmas indicadas). NO SE DEBEN ALTERAR
LAS INTERFACES provista, salvo en los casos que se considere apropiados.

•  NO SE DEBEN CREAR NUEVAS CLASES Y NO SE DEBE INCLUIR NINGUN

METODO NO SOLICITADO O INNECESARIO.


