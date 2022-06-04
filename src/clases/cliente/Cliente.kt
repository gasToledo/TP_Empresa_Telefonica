package clases.cliente

import java.time.LocalDate

/**
 * Clase Cliente, por constructor se piden los argumentos : codigo de cliente(un valor distintivo de cada cliente),
 * nombre, apellido, fecha de alta en el sistema, y tipo de cliente (Para diferenciar entre NUEVOS Y ESTANDAR).
 */
class Cliente(
	private val codigo_cliente: Int,
	private val nombre_cliente: String = "",
	private val apellido_cliente: String = "",
	private val fecha_de_alta_cliente: LocalDate = LocalDate.now(),
	private var tipoCliente: TipoCliente = TipoCliente.NUEVO,
) {

    init {
        tipoDeCliente()
    }

    /**
     * En la funcion privada "tipoDeCliente()" consultamos si "la fecha de alta del cliente" se encuentra
     * entre los ultimos 6 meses de la fecha actual y la fecha actual, de ser asi, el cliente obtendría el tipo "NUEVO"
     * sino "ESTANDAR".
     */
    private fun tipoDeCliente() {
        val haceSeisMeses = LocalDate.now().minusMonths(6)

        this.tipoCliente =
            if (fecha_de_alta_cliente in haceSeisMeses..LocalDate.now())
                TipoCliente.NUEVO
            else
                TipoCliente.ESTANDAR
    }

    fun getCodigoCliente(): Int {
        return this.codigo_cliente
    }

    fun getTipoCliente(): TipoCliente {
        return this.tipoCliente
    }

    fun getNombreCliente(): String {
        return this.nombre_cliente
    }

    fun getApellidoCliente(): String {
        return this.apellido_cliente
    }

    /**
     * Sobreescribimos la función heredada toString() para que retorne la información relevante.
     */
    override fun toString(): String {
        return """
            
            Codigo: $codigo_cliente
            Nombre: $nombre_cliente $apellido_cliente
            Tipo de cliente: $tipoCliente
            Fecha de alta: $fecha_de_alta_cliente
        """.trimIndent()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Cliente

        return (codigo_cliente == other.codigo_cliente)
    }

    override fun hashCode(): Int {
        return codigo_cliente
    }
}