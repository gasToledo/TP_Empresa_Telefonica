package menu

object Mediador {

    /**
     *
    La clase "Mediador" es una "clase de única instancia" creada
    para controlar y visualizar el movimiento de entre menús.

    Para ello primero se crea una lista mutable que contiene los menús
     */
    private val pilaMenus = mutableListOf<Menu>()

    /**
     *
    La función "menuAnterior()" consulta si el tamaño de la lista
    es mayor a cero, y de serlo elimina el último menu agregado a la pila.
     */
    private fun menuAnterior() {

        if (pilaMenus.size > 0) {
            pilaMenus.remove(pilaMenus.last())
        }
    }

    /**
     *
    La función "agregarMenu()" recibe por parámetro un menú y
    lo añade a la pila.
     */
    private fun agregarMenu(actual: Menu) {

        pilaMenus.add(actual)
    }

    /**
     *
    La función "seleccionar" recibe por parámetro un Input de tipo Int.
    Al "input" se le resta uno(para evitar confusiones a la hora de elegir por terminal
    la elección), luego comprueba si es una "opción" o un "menú", de ser una opción se ejecuta
    la acción del mismo y en caso contrario se agrega el menú a la pila para ser mostrado.
     */
    private fun seleccionar(input: Int) {

        val seleccion = pilaMenus.last().getChild(input - 1)

        if (seleccion is Opcion) {

            seleccion.operation()

        } else if (seleccion is Menu) {

            agregarMenu(seleccion)
        }
    }

    /**
     *
    La función "navegar()" recibe por parámetro un "Menú".

    Luego añade el menú ingresado a la pila utilizando la función
    "agregarMenu()" y entra en un loop que permite "navegar" entre menús
    y usar las opciones de los mismos mientras la pila de menús no este vacía.
     */
    fun navegar(root: Menu) {

        agregarMenu(root)

        println(
            """
            
            Entraste al sistema
            
           
        """.trimIndent()
        )
        do {

            val cantidadDeElementos = pilaMenus.last().getChildSize()

            pilaMenus.last().printChild()
            if (pilaMenus.size > 1) {

                println("${cantidadDeElementos + 1} -> Volver")
            } else {
                println("${cantidadDeElementos + 1} -> Salir")
            }

            val input = readLine()?.toIntOrNull() ?: 0

            if (input in 1..cantidadDeElementos) {
                seleccionar(input)
            } else if (input == cantidadDeElementos + 1) {
                menuAnterior()
            }

        } while (pilaMenus.isNotEmpty())

    }

}