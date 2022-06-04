package menu


class Menu(private val descripcion: String) : EsMenu {

    /*
    La clase "Menu" implementa la interfaz "EsMenu" y recibe
    por parámetro un string que describe su función.

    Al inicializarse crea una lista mutable vacía para en futuro
    agregar más clases que implementen "EsMenu"
     */

    private val menuList = mutableListOf<EsMenu>()

    fun addChild(nuevo: EsMenu) {

        menuList.add(nuevo)
    }

    fun getChild(index: Int): EsMenu {

        return menuList[index]
    }

    /*
    La función "printChild()" imprime por terminal
    en primera instancia la descripción de la instancia y
    luego, la posición(sumándole uno, para no generar confusión a la hora
     de elegir menús u opciones por terminal) y descripción de los integrantes de las listas
     */
    override fun printChild() {

        println(" [ ${toString()} ] ")


        menuList.forEachIndexed { index, it ->
            println("${index + 1} -> $it ")
        }

    }

    override fun toString(): String = this.descripcion

    fun getChildSize(): Int {

        return menuList.size
    }
}