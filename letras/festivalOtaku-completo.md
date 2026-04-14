UNIVERSIDAD CATÓLICA DEL URUGUAY                                  FACULTAD DE INGENIERÍA Y TECNOLOGÍAS
Algoritmos y Estructuras de Datos  -  Primer Parcial

NOMBRE COMPLETO: ___________________________________________________________
FECHA: ___________________________________________   HOJAS: _____________________

Ejercicio 1: Árboles

Árboles binarios:

Consideraciones importantes.

●  Se debe mostrar paso a paso la inserción de cada clave.
●  Cuando se produce un desbalance, se debe identificar el nodo desbalanceado, el
tipo de desbalance y los distintos nodos que se ven involucrados en la operación
de balanceo.

●  En caso de rotaciones complejas, se puede obviar mostrar el estado intermedio

luego de realizar la primera rotación.

A continuación, se muestra un árbol binario de búsqueda, resultado de haber insertado
algunas claves. Se desea insertar las claves 45, 35, 25, 37 y 3, y luego, eliminar las claves 20, 25
y 30, de tal forma que el árbol resultante en cada inserción sea AVL.

En cada paso previo o posterior a la inserción se debe:

●  Dibujar el árbol resultante.
●  Señalar si ocurre un desbalance y en qué nodo.

Indicar la rotación necesaria, si corresponde, y mostrar el árbol resultante.

Árboles tries:

CONSIDERACIONES IMPORTANTES

●  Dibujar el árbol parcial resultante.
●  Los nodos que representen el final de una palabra deben marcarse claramente.

A continuación, se presenta un conjunto de palabras a insertar en un árbol trie:

mano, monitor, masa, manso, montevideo, universidad

UNIVERSIDAD CATÓLICA DEL URUGUAY                                  FACULTAD DE INGENIERÍA Y TECNOLOGÍAS
Algoritmos y Estructuras de Datos  -  Primer Parcial

Ejercicio 2: Festival Otaku – Pseudocódigo

Te  encuentras  realizando  una  consultoría  para  la  organización  del  Festival  Otaku  Nacional,
específicamente en el área gastronómica temática conocida como “Bento Multiverso”, donde
los fans pueden armar su propio combo bento formado por distintos ingredientes inspirados
en animes famosos (onigiris de Naruto, katsudon de Yuri on Ice, curry de One Piece, etc.) y una
bebida temática (ramune, té matcha, sake sin alcohol, etc.).

las  combinaciones  son  seguras  para  el  consumo.  Algunas
Sin  embargo,  no  todas
combinaciones  específicas  pueden  causar  malestar  por  incompatibilidades  de  sabor  o  por
regulaciones del festival. Por lo tanto, uno de los problemas que te plantean es determinar si un
cierto combo bento es viable para ser ofrecido a los visitantes.

Luego de un análisis con el equipo organizador, tú sugieres crear dos estructuras:

•  Una lista de ingredientes permitidos, que indica qué ingredientes pueden combinarse

con cualquier bebida sin restricciones.

•  Una  lista  de  combinaciones  prohibidas,  que  indica  pares  específicos  de  {bebida  &

ingrediente} que no deben combinarse en ninguna circunstancia.

Se debe, dado un combo (es decir, una combinación de una bebida temática y un conjunto de
ingredientes), indicar si es viable o no.

Las reglas son:

•  Si  un  ingrediente  aparece  en  la  lista  de  permitidos,  entonces  está  habilitado  para  ser

combinado con cualquier bebida.

•  Si una combinación aparece en la lista de prohibidas, entonces no se podrá combinar

esa bebida con ese ingrediente.

•  Si  un  ingrediente  no  aparece  en  ninguna  de  las  listas,  entonces  por  precaución  no

debería combinarse con ninguna bebida.

Se pide entonces: desarrollar un algoritmo que, dado un combo propuesto (en la forma de una
bebida y un conjunto de ingredientes), indique si es viable o no.

Firma:comboViable(Bebida: identificadorBebida, Ingredientes: Lista de identificadorIngrediente): booleano

Justificación y Elección de Estructura de Datos
Se debe fundamentar claramente por qué la estructura seleccionada (lista enlazada, ABB, AVL,
etc.) es la más conveniente para este problema.

Desarrollo Algorítmico

lenguaje  natural,  especificar
Para  cada  método,  es  necesario:  explicar
precondiciones y postcondiciones, presentar el pseudocódigo y analizar el orden del tiempo de
ejecución.

lógica  en

la

2

UNIVERSIDAD CATÓLICA DEL URUGUAY                                  FACULTAD DE INGENIERÍA Y TECNOLOGÍAS
Algoritmos y Estructuras de Datos  -  Primer Parcial

Ejercicio 3: Festival Otaku – Programación en Java

Este ejercicio comprende 3 partes:

1.  Desarrollo de la funcionalidad especificada más abajo
2.  Desarrollo  del  programa  ejecutable  con  datos  de  ejemplo  provistos  y  entrega  de  todo  el

Proyecto Maven en la tarea correspondiente de la webasignatura.
3.  Desarrollo de los casos de prueba para las funcionalidades requeridas

PARTE  1: Funcionalidad a desarrollar (vale 45%):
Descargar de la webasignatura el archivo “Parcial1-2025.zip” que contiene el Proyecto Maven a ser
completado.

Se debe desarrollar un sistema que permita:

•  Leer desde archivos de texto la información necesaria para el análisis.
•  Almacenar toda esta información en estructuras de datos adecuadas, de forma
que se puedan realizar búsquedas de manera eficiente al momento de evaluar
la viabilidad de los combos.

•  Determinar, para cada combo propuesto, si es viable según las reglas definidas.
•  Escribir los resultados en un archivo de salida indicando, para cada combo

analizado, si es viable o no.

PARTE 2: PROGRAMA (vale 30%)
La clase principal se denomina “Main”, y tiene su correspondiente método “main”. En éste,
implementa lo necesario para aplicar los TDA y métodos desarrollados.

1.  Leer y cargar los datos.
2.
3.  Escribir un archivo de texto “Resultado.txt” con los resultados.

Invocar los métodos programados.

PARTE 3: TEST CASES (vale 25%).
Implementa el o los Casos de Prueba necesarios para verificar el correcto funcionamiento de los
métodos implementados para resolver las funcionalidades listadas más arriba.

NOTAS IMPORTANTES:

•  Se proveen las interfaces y clases necesarias. Deben implementarse los métodos

necesarios (respetando las firmas indicadas). NO SE DEBEN ALTERAR LAS
INTERFACES provistas, ni agregar otros métodos que los requeridos en las interfases.

•  NO SE DEBE INCLUIR NINGUN MÉTODO NO SOLICITADO O INNECESARIO.

3

UNIVERSIDAD CATÓLICA DEL URUGUAY                                  FACULTAD DE INGENIERÍA Y TECNOLOGÍAS
Algoritmos y Estructuras de Datos  -  Primer Parcial

•  ENTREGA:  Debes entregar TODO el proyecto Maven y los archivos de salida

solicitados, en un archivo comprimido “Parcial1.zip” en la tarea publicada en la
webasignatura, hasta la hora indicada.

4


