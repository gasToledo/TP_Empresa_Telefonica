package clases.llamada

import java.time.LocalDate
import java.time.LocalTime

class LlamadaNocturna(
    codigo_cliente: Int,
    fecha_llamada: LocalDate,
    hora_llamada: LocalTime,
    duracion_llamada: Double,
    tipo_llamada: Char,
) : Llamada(codigo_cliente, fecha_llamada, hora_llamada, duracion_llamada, tipo_llamada) {

    /**
     * "LlamadaNocturna" es clase hija de "Llamada" y sobreescribe el metodo
     * "calcularPrecio()" aplicando los costos requeridos.
     */
    override fun calcularPrecio(): Double {
        val minuto: Double = duracion_llamada / 60.0

        //Dependiendo si es 'I' Internacional o 'L' Local se cobra el minuto.
        val costoFinalLlamada: Double = if (tipo_llamada == 'I') {
            (minuto * 0.02) * 2
        } else {
            (minuto * 0.02)
        }

        return costoFinalLlamada
    }
}