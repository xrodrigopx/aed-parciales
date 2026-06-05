UNIDAD TEMÁTICA  3: Listas, Pilas, Colas

PRACTICOS DOMICILIARIOS INDIVIDUALES #10

EJERCICIO 1

Te  encuentras  realizando  una  consultoría  en  farmachop,  y  uno  de  los  problemas  que  te  plantean  es
determinar  si  un  cierto  preparado  es  viable.  Un  preparado  es  una  mezcla  de  diferentes  fármacos,
habitualmente  disueltos  en  un  cierto  suero.  Existe  una  enorme  cantidad  de  fármacos,  y  varios  sueros
diferentes,  y  el  problema  es  que  no  siempre  un  fármaco  puede  ser  disuelto  con  un  cierto  suero:  estas
combinaciones podrían ser perjudiciales para la salud.
Luego de un análisis con el equipo, tú sugieres crear dos estructuras:
•  Una  lista  blanca  de  fármacos,  donde  se  define  cuáles  medicamentos  se  pueden  diluir  en  cualquier
suero.
•  Una lista negra de pares {suero & fármaco}, donde se definen qué conjunciones de suero y fármaco
son inválidas.

Se debe: dado un preparado propuesto, es decir, una composición de un tipo de suero y un conjunto de
fármacos, indicar si éste es viable o no.

Si un fármaco aparece en la lista blanca, entonces está habilitado con cualquier suero. Si aparece en la lista
negra, entonces no se podrá diluir en el o los sueros indicados. Si no aparece en ninguna lista, entonces por
precaución no debería ser diluido en ninguno de los sueros disponibles…

Se pide entonces:

1.  Describe las estructuras de datos a utilizar y sus funcionalidades.
2.  Desarrolla un algoritmo que, dado un preparado (en la forma de un suero y un conjunto de fármacos),

indique si es viable o no

Firma: preparadoViable (Suero: identificadorSuero, Farmacos: Lista de identificadorFarmaco) : booleano.

3.  Analiza el orden del tiempo de ejecución del algoritmo desarrollado

EJERCICIO 2

Continuamos con el problema del ejercicio anterior.
Se debe: dado un preparado propuesto, es decir, una composición de un tipo de suero y un conjunto de
fármacos, indicar si éste es viable o no.

Si un fármaco aparece en la lista blanca, entonces está habilitado con cualquier suero. Si aparece en la lista
negra, entonces no se podrá diluir en el o los sueros indicados. Si no aparece en ninguna lista, entonces por
precaución no debería ser diluido en ninguno de los sueros disponibles…

Se proveen cuatro archivos:

•  “sueros.txt”: cada línea tiene un identificador de suero (número entero) y una descripción del suero,

separados por comas. {idSuero, descSuero}

•  “farmacos.txt”: cada línea tiene un identificador de fármaco (número entero) y una descripción del

fármaco, separados por comas. {idfarmaco, descfarmaco}

•  “listablanca.txt”: cada línea tiene un identificador de fármaco (número entero) que puede ser diluido

en cualquier suero. { idfarmaco }

•  “listanegra.txt”: cada línea tiene un identificador de suero y un identificador de fármaco (números
enteros, separados por comas, que indican que ese suero no puede ser usado para diluir el fármaco
indicado. {idSuero, idfarmaco }

A realizar

1.  Describe las estructuras de datos a utilizar y sus funcionalidades.
2.  Desarrolla un método que, dado un preparado (en la forma de un identificador de suero y un conjunto

de identificadores de fármacos), indique si es viable o no

Firma: preparadoViable (Suero: identificadorSuero, Farmacos: Lista de identificadorFarmaco) : booleano.

3.  Desarrolla el o los casos de prueba (“test case”, al menos uno) para verificar la corrección de la

funcionalidad implementada.

4.  En el programa principal, implementa lo necesario para, dados un identificador de suero y un

conjunto de identificadores de fármacos, imprimir por pantalla:
o  El identificador y la descripción del suero (en una línea)
o  Los identificadores y descripciones de cada fármaco (uno por línea)
o  Si es VIABLE o NO VIABLE de acuerdo a la invocación del  método desarrollado.
5.  Ejecuta el programa principal con los datos indicados en el pizarrón y registra los resultados
mostrados en pantalla en un archivo de texto “consulta.txt” e inclúyelo en la entrega.

NOTA IMPORTANTE: se deberá controlar que los identificadores de sueros y fármacos que aparecen en las
listas blanca y negra, así como en las consultas, sean identificadores válidos de productos (es decir, deben
existir en las estructuras correspondientes a sueros y fármacos).


