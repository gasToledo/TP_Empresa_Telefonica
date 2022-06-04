package clases.sistema

import clases.cliente.Cliente
import clases.cliente.TipoCliente
import clases.llamada.Llamada
import clases.llamada.LlamadaFinDeSemana
import clases.llamada.LlamadaNocturna
import clases.llamada.LlamadaRegular
import excepciones.ClienteYaExistente
import excepciones.NoExisteCliente
import java.math.RoundingMode
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.format.FormatStyle

/**
 * La clase "Sistema" es la que manejara todos los datos recibidos de la clase "Cliente"
 * y las clases hijas de "LLamada".
 */
class Sistema {
    /**
     * Comienza creando un Map privado utilizando al "Cliente" como "Key" y
     * una lista mutable de "Llamada" (Aplicando Polimorfismo) como "Value".
     */
    private val listaClientes = mutableMapOf<Cliente, MutableList<Llamada>>()

    /**
     * La función "ingresarClientes" recibe como parámetro una variable de tipo Cliente y
     * consulta si este cliente ya se encuentra dado de alta en el sistema, de ser verdad, lanza
     * la excepción "ClienteYaExistente" y su mensaje, si no ingresa al Cliente en el sistema
     * agregándole una lista de tipo Llamada vacía.
     */
    //DOKO? KOKO?
    private fun ingresarClientes(alta: Cliente) {
        val historialLlamadas = mutableListOf<Llamada>()
        listaClientes[alta] = historialLlamadas
        println("Dado de alta correctamente")
    }

    private fun verificarCliente(codigo: Int) {
        if (!listaClientes.contains(Cliente(codigo))) {
            throw NoExisteCliente("[ERROR] Cliente no existente.")
        }
    }

    private fun verificarCliente(cliente: Cliente) {
        if (!listaClientes.contains(cliente)) {
            throw NoExisteCliente("[ERROR] Cliente no existente.")
        }
    }

    private fun ingresarFecha(): LocalDate {
        var date: LocalDate? = null
        do {
            println("Ingrese la fecha en que sera dado de alta [dd-MM-yyyy]")
            val fecha = readLine()!!.toString()

            try {
                val dateAux = LocalDate.parse(fecha, DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                date = if (dateAux.isAfter(LocalDate.now())) null else dateAux
            } catch (e: DateTimeParseException) {
                println(e.message)
            }
        } while (date == null)

        return date
    }

    private fun ingresarHora(): LocalTime {
        var time: LocalTime? = null
        do {
            println("Ingrese hora de la llamada. [hh:mm]")
            val hora = readLine()!!.toString()

            try {
                time = LocalTime.parse(hora, DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))
            } catch (e: DateTimeParseException) {
                println(e.message)
            }
        } while (time == null)

        return time
    }

    /**
     * La función "darDeAltaCliente()" permite agregar clientes al mapa de una manera manual por terminal.
     */
    fun darDeAltaCliente() {
        println("Ingrese el codigo de cliente")
        val codigo = readLine()!!.toIntOrNull() ?: 0

        if (listaClientes.contains(Cliente(codigo))) {
            throw ClienteYaExistente("[ERROR] El cliente ya se encuentra registrado")
        }

        println("Ingrese el nombre")
        val nombre = readLine()!!.toString()

        println("Ingrese el apellido")
        val apellido = readLine()!!.toString()

        val clienteAlta = Cliente(codigo, nombre, apellido, ingresarFecha())
        ingresarClientes(clienteAlta)
    }

    fun darDeAltaCliente(codigo: Int, nombre: String, apellido: String, date: LocalDate) {
        val clienteAlta = Cliente(codigo, nombre, apellido, date)
        ingresarClientes(clienteAlta)
    }


    /**
     * La función "darDeBajaCliente()" permite Remover clientes del MAP de una manera manual por terminal.
     */
    fun darDeBajaCliente() {
        println("Ingresar el codigo del cliente")
        val codigo = readLine()!!.toIntOrNull() ?: 0

        verificarCliente(codigo)

        val clienteEliminado = getCliente(codigo)
        listaClientes.remove(clienteEliminado)
        println("Se ha dado de baja al cliente ${clienteEliminado.getNombreCliente()} ${clienteEliminado.getApellidoCliente()}")
    }

    /**
     * La función "ingresarLlamadaACliente()" recibe como parámetros un Cliente y una Llamada
     * consulta si la key "cliente" es nula o vacía y de ser así, lanza la excepción NoExisteCliente
     * de existir en el Map ingresa la llamada al value Llamada.
     */
    private fun ingresarLlamadaACliente(cliente: Cliente, llamada: Llamada) {
        verificarCliente(cliente)
        listaClientes[cliente]?.add(llamada)
    }

    fun realizarLlamada(cliente: Cliente) {
        //Primero comprueba que el cliente no sea nulo o este vacío
        verificarCliente(cliente)

        val fecha = ingresarFecha()
        val hora = ingresarHora()

        println("Ingrese la duracion de la llamada en segundos. [ss.n]. ")
        val duracion = readLine()!!.toDoubleOrNull() ?: 0.0

        var tipo: Char
        do {
            println("Ingrese el tipo de llamada [Internacional (I)] o [Local (L)]. ")
            val aux: String = readLine()!!.toString()
            tipo = aux.single()
        } while (tipo != 'L' && tipo != 'I')


        //Comprobamos que tipo de llamada es
        val llamada: Llamada = if (esFinDeSemana(fecha)) {
            LlamadaFinDeSemana(cliente.getCodigoCliente(), fecha, hora, duracion, tipo)
        } else if (esNoche(hora) || cliente.getTipoCliente() == TipoCliente.NUEVO) {
            LlamadaNocturna(cliente.getCodigoCliente(), fecha, hora, duracion, tipo)
        } else {
            LlamadaRegular(cliente.getCodigoCliente(), fecha, hora, duracion, tipo)
        }

        this.ingresarLlamadaACliente(cliente, llamada)
        println("Llamada guardada con exito.")
    }

    private fun esFinDeSemana(fecha: LocalDate): Boolean {
        return fecha.dayOfWeek == DayOfWeek.SUNDAY || fecha.dayOfWeek == DayOfWeek.SATURDAY
    }

    private fun esNoche(hora: LocalTime): Boolean {
        return hora.hour in 22 downTo 5
    }

    /*
    la función "calcularCostoLlamadasCliente()" recibe por parámetro
    el código de cliente de tipo Int, comprueba que el cliente exista en el Map
    y de existir recorre la lista de llamadas de ese cliente sumándole a la variable
    "precioTotal" el costo de cada llamada, una vez termina toda la lista de llamadas
    retorna "precioTotal" redondeado de manera positiva para el cliente.
     */
    fun calcularCostoLlamadasCliente(codigo: Int): Double {
        verificarCliente(codigo)

        var precioTotal = 0.0
        listaClientes[getCliente(codigo)]?.forEach {
            precioTotal += it.calcularPrecio()
        }

        return precioTotal.toBigDecimal().setScale(2, RoundingMode.HALF_DOWN).toDouble()
    }

    /*
    La función "calcularCostoLlamadaTodos()", primero crea la variable "costoTotal",
    luego recorre el Map y reutiliza la función "calcularCostoLlamadasCliente" en cada cliente
    sumándole el precio de todas las llamadas a "costoTotal", se redondea en positivo para el cliente y retornado el mismo.
     */
    fun calcularCostoLlamadasTodos(): Double {
        var costoTotal = 0.0
        val lista = listaClientes.keys
        for (i in 0 until lista.size) {
            val codigo = lista.elementAt(i).getCodigoCliente()
            costoTotal += calcularCostoLlamadasCliente(codigo)
        }

        return costoTotal.toBigDecimal().setScale(2, RoundingMode.HALF_DOWN).toDouble()
    }

    fun getCliente(cod_cliente: Int): Cliente {
        listaClientes.keys.forEach {
            if (it.getCodigoCliente() == cod_cliente) {
                return it
            }
        }

        throw NoExisteCliente("[ERROR] Cliente no existente.")
    }

    /*
    La función "verClientes()" imprime por cada cliente una lista
    de todas sus llamadas con datos de las mismas.
     */
    fun verClientes() {
        listaClientes.forEach {
            println(
                """
                ${it.key}
                
Historial de llamadas : ${it.value}
   *************************************
            """.trimMargin()
            )
        }
    }

}