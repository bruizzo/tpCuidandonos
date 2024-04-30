# TP: Cuidándonos

### Alumnos:

- González Mayer, Santiago *(Legajo: 209.601-8)*
- Izzo, Bruno *(Legajo: 152.751-4)*

<hr>

###  Decisiones de Diseño

Pensamos que un viaje puede estar en tres estados: Pendiente, En Curso y Finalizado. Un viaje está Pendiente cuando aún no ha sido comenzado por un transeúnte; en el momento en que el usuario comienza a transitar, el viaje pasa a estado En Curso.
Cuando se introducen las paradas intermedias, nosotros consideramos que el camino entre cada parada es un viaje en sí; esto nos significó un cambio de comportamiento en nuestro método comenzar(), ya que no es lo mismo comenzar un viaje desde el origen que comenzar un viaje desde una parada intermedia. Para solucionar esto, utilizamos el **patrón State**, ya que el comportamiento depende del estado y además se produce una transición entre los mismos.

Para la utilización de la API de Google "Distance Matrix", optamos por la implementación del **patrón Adapter**. Ya que no conocemos el funcionamiento de la API, nosotros creamos una clase adaptadora que nos sirve de intermediaria entre nuestra clase Viaje y la clase propia de la API.

Con respecto al uso de notificaciones y a la ejecución de las tareas de alerta, nos inclinamos por utilizar el **patrón Strategy**. Si bien el enunciado no aclaraba en ambos casos si la implementación era propia de nuestro sistema o utilizaba sistemas externos,, diseñamos nuestra solución considerando que dependían de nosotros (aunque no implementamos los métodos correspondientes).  En ambos casos, tenemos clases concretas para desarrollar cada estrategia: en el caso de las alertas, implementan una interfaz, mientras que en el caso de las notificaciones, heredan de una clase abstracta (esto último porque decidimos que las notificaciones tengan en común un atributo para contener un mensaje).