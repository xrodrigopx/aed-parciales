UNIVERSIDAD CATÓLICA DEL URUGUAY

ALGORITMOS Y ESTRUCTURAS DE DATOS

Este ejercicio comprende 3 partes:

FACULTAD DE INGENIERÍA Y TECNOLOGÍAS

PRIMER PARCIAL

11 de mayo de 2024

PARTE 3: Ejercicio de programación JAVA

1.  Desarrollo de la funcionalidad especificada más abajo
2.  Desarrollo del programa ejecutable con datos de ejemplo provistos y entrega de todo el Proyecto Maven en

la tarea correspondiente de la webasignatura.

3.  Desarrollo de los casos de prueba para las funcionalidades requeridas

ESCENARIO
El sistema de inventario representa un almacén que contiene una variedad de productos. Cada producto
está asociado con un código único y se organiza jerárquicamente en categorías y subcategorías. Los nodos
internos del árbol representan estas categorías, mientras que los nodos hoja representan los productos
individuales sin subcategorías.

Como una medida posible que da una idea sobre la forma de un árbol binario, se define como "longitud de
trayectoria interna" (LTI) de un árbol a la suma de los niveles de todos los nodos del árbol, y como
"longitud de trayectoria interna media" (LTIM) a ese valor dividido por la cantidad de nodos del árbol
(tamaño).

 donde "n" es el tamaño del árbol y "hi" es el nivel del nodo "i"

𝐿𝑇𝐼 =   ∑ ℎ𝑖

𝑛
𝑖=1

𝐿𝑇𝐼𝑀 =

𝐿𝑇𝐼
𝑛

PARTE  1: Funcionalidad a desarrollar (vale 45%):

Descargar  de  la  webasignatura  el  archivo  “Parcial1-2024-1.zip”  que  contiene  el  Proyecto  Maven  a  ser
completado.

Se desea:

1.  Implementa en la clase apropiada, un método que, calcule la longitud de trayectoria interna media

de un árbol.

Tipo Producto

Identificador: numérico
Nombre: alfanumérico

PARTE 2: PROGRAMA (vale 30%)

La clase principal se denomina “Parcial1”, y tiene su correspondiente método “main”. En éste, implementa
lo necesario para aplicar los TDA y métodos desarrollados.

1.  Leer y cargar los datos de los productos
2.  Invocar el método programado
3.  Escribe un archivo de texto “salida.txt”, con la longitud de trayectoria interna, la altura del árbol y

la longitud de trayectoria interna media, cada dato en una línea distinta.

PARTE 3: TEST CASES (vale 25%).

Implementa  el  o  los  Casos  de  Prueba  necesarios  para  verificar  el  correcto  funcionamiento  del  método
implementado (sólo a nivel del TArbolDeProductos).

1

UNIVERSIDAD CATÓLICA DEL URUGUAY

ALGORITMOS Y ESTRUCTURAS DE DATOS

NOTAS IMPORTANTES:

FACULTAD DE INGENIERÍA Y TECNOLOGÍAS

PRIMER PARCIAL

11 de mayo de 2024

●  Se proveen las interfaces y clases necesarias. Deben implementarse los métodos necesarios

(respetando las firmas indicadas). NO SE DEBEN ALTERAR LAS INTERFACES provistas, ni agregar
otros métodos que los requeridos en las interfases.

●  NO SE DEBEN CREAR NUEVAS CLASES Y NO SE DEBE INCLUIR NINGUN METODO NO SOLICITADO O

INNECESARIO.

ENTREGA:  Debes entregar TODO el proyecto Maven y los archivos de salida solicitados, en un archivo
comprimido “Parcial1.zip” en la tarea publicada en la webasignatura, hasta la hora indicada.

2


