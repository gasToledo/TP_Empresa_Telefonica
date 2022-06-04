package clases.llamada

import java.math.RoundingMode
import java.time.LocalDate
import java.time.LocalTime
import kotlin.math.roundToInt

/**
 * Creamos la clase abstracta "Llamada" para poder generar "Herencia, Polimorfismo" y facilitar el uso del código.
 */
abstract class Llamada(
    private val codigo_cliente: Int,
    private val fecha_llamada: LocalDate,
    private val hora_llamada: LocalTime,
    protected val duracion_llamada: Double,
    protected val tipo_llamada: Char,
) {

    /**
     * "calcularPrecio()" sera una función que cada clase hija de Llamada debera sobreescribir y
     * utilizarla como sea conveniente.
     */
    abstract fun calcularPrecio(): Double

    /**
     * Se sobreescribe la función "toString()" para poder presentar la información de la clase correctamente
     */
    override fun toString(): String {
        return """
            
            
            Fecha de llamada : $fecha_llamada
            Hora de llamada : $hora_llamada
            Duracion de llamada : ${(duracion_llamada / 60).roundToInt()} min
            Tipo de llamada : $tipo_llamada
            Precio de la llamada : ${calcularPrecio().toBigDecimal().setScale(2, RoundingMode.HALF_DOWN).toDouble()}
    
        """.trimIndent()
    }
}