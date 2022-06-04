package menu

import excepciones.ClienteYaExistente
import excepciones.NoExisteCliente

class Opcion(private val descripcion: String, private val accion: () -> Unit) : EsMenu {

    /*
    La clase "Opción" implementa la interfaz "EsMenu"
    y recibe por parámetro una descripción de su uso y
    una función que recibe nada y devuelve nada(Esto permitiría
    que la opción pueda realizar a futuro cualquier acción que sea necesaria).


    La función "operation()" ejecuta la función vacía agregada por
    parámetro.
     */
    fun operation() {

        try {
            this.accion()
        } catch (e: NoExisteCliente) {
            println(e.message)
        } catch (e: ClienteYaExistente) {
            println(e.message)
        }

    }

    override fun printChild() {
        println("-> [ $this ] ")
    }

    override fun toString(): String = this.descripcion
}
