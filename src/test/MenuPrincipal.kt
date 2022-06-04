package test


import clases.cliente.Cliente
import clases.sistema.Sistema
import menu.Mediador
import menu.Menu
import menu.Opcion
import java.time.LocalDate


fun main() {

    val movistar = Sistema()
    /*
    Se configura el estilo de menús y opciones
     */
    val menuPrincipal = Menu("Sistema")
    val menuClientes = Menu("Menu de clientes")
    val menuLlamadas = Menu("Menu de llamadas")


    val darDeAltaClientes = Opcion("Dar de alta cliente") { movistar.darDeAltaCliente() }

    val darDeBajaClientes = Opcion("Dar de baja cliente") { movistar.darDeBajaCliente() }

    val costoTotalTodosClientes =
        Opcion("Obtener costo total de todos los clientes") { println(movistar.calcularCostoLlamadasTodos()) }

    val costoTotalClienteEspecifico = Opcion("Obtener costo total de un cliente") {
        println("Ingresar codigo del cliente")
        val codigo = readLine()!!.toIntOrNull() ?: 0

        println(movistar.calcularCostoLlamadasCliente(codigo))
    }

    val realizarLlamada = Opcion("Realizar una llamada") {
        println("Ingresar codigo del cliente")
        val codigo = readLine()!!.toIntOrNull() ?: 0

        val cliente: Cliente = movistar.getCliente(codigo)
        movistar.realizarLlamada(cliente)
    }

    val verClientes = Opcion("Ver clientes en el sistema") { movistar.verClientes() }

    movistar.darDeAltaCliente(123, "Gaston", "Toledo", LocalDate.now())

    menuPrincipal.addChild(menuClientes)
    menuPrincipal.addChild(menuLlamadas)

    menuClientes.addChild(darDeAltaClientes)
    menuClientes.addChild(darDeBajaClientes)
    menuClientes.addChild(verClientes)

    menuLlamadas.addChild(realizarLlamada)
    menuLlamadas.addChild(costoTotalClienteEspecifico)
    menuLlamadas.addChild(costoTotalTodosClientes)

    //****************************************

    Mediador.navegar(menuPrincipal)

}


