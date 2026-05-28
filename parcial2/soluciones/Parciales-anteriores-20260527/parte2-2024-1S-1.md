# UNIVERSIDAD CATÓLICA DEL URUGUAY — FACULTAD DE INGENIERÍA Y TECNOLOGÍAS
## ALGORITMOS Y ESTRUCTURA DE DATOS — SEGUNDO PARCIAL
### 29 de junio 2024

---

## PARTE 2: Ejercicios de pseudocódigo
**Duración: 60 minutos**

---

## EJERCICIO 1

Eres un analista de datos del departamento meteorológico encargado de analizar los datos de temperatura recopilados durante la última década. Las lecturas de temperatura se registran diariamente y se almacenan en una matriz. El conjunto de datos contiene 3,652 entradas únicas (365 días por año durante 10 años, sin considerar los años bisiestos).

Dada la importancia de este análisis, necesitas ordenar las lecturas de temperatura de manera eficiente para observar tendencias y detectar anomalías. Dado que las lecturas de temperatura son relativamente estables año tras año, se espera que los datos estén **casi ordenados**.

Se requiere implementar este algoritmo de ordenación en un microcontrolador con recursos de memoria limitados. Los microcontroladores suelen tener RAM y almacenamiento restringidos, por lo que se requieren algoritmos que minimicen el uso de memoria adicional.

**Se requiere implementar un algoritmo de ordenamiento que resulte apropiado para el contexto planteado anteriormente, especificando lenguaje natural, pre y post condiciones relevantes, pseudocódigo del algoritmo y orden del tiempo de ejecución.**

---

## EJERCICIO 2

### Escenario

En los centros de datos modernos, las redes consisten en una compleja malla de máquinas que incluyen tanto nodos de procesamiento como switches. Los nodos de procesamiento son los encargados de realizar las tareas de computación y procesamiento de datos, mientras que los switches gestionan el enrutamiento de datos entre estos nodos de procesamiento. La comunicación eficiente entre los nodos de procesamiento es crucial para un rendimiento óptimo, pero debe gestionarse cuidadosamente para garantizar que el flujo de datos sea correcto y eficiente a través de la red.

Dado este contexto, es necesario calcular todos los caminos posibles entre dos nodos de procesamiento especificados en la red. Sin embargo, existe una restricción crítica: no se permiten conexiones directas entre los nodos de procesamiento. La comunicación entre los nodos de procesamiento debe enrutarse mediante switches. Esta restricción asegura que la red mantenga un sistema de enrutamiento estructurado y manejable, previniendo la comunicación directa entre máquinas que podría llevar a cuellos de botella o ineficiencias.

### Objetivo

El objetivo de esta tarea es **desarrollar un algoritmo capaz de calcular todos los posibles caminos desde un nodo de procesamiento de origen especificado hasta un nodo de procesamiento de destino especificado. Los caminos deben adherirse a las reglas de comunicación de la red**, donde los datos solo pueden viajar de un nodo de procesamiento a otro a través de uno o más switches. Se debe **especificar en lenguaje natural una descripción del algoritmo, pre y post condiciones relevantes, pseudocódigo y orden del tiempo de ejecución.**
