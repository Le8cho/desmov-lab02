/*Herencia y Encapsulamiento
Desarrollar un programa que modele una cuenta bancaria
*/
import kotlin.math.min

open class Cuenta(
    protected var saldo: Float,
    protected val tasaAnual: Float
) {
    protected var numeroConsignaciones: Int = 0
    protected var numeroRetiros: Int = 0
    protected var comisionMensual: Float = 0f

    open fun consignar(cantidad: Float) {
        saldo += cantidad
        numeroConsignaciones++
    }

    open fun retirar(cantidad: Float) {
        if (cantidad <= saldo) {
            saldo -= cantidad
            numeroRetiros++
        } else {
            println("La cantidad a retirar excede el saldo actual.")
        }
    }

    fun calcularInteresMensual() {
        val interesMensual = saldo * (tasaAnual / 12) / 100
        saldo += interesMensual
    }

    open fun extractoMensual() {
        saldo -= comisionMensual
        calcularInteresMensual()
    }

    open fun imprimir() {
        println("Saldo: $saldo")
        println("Número de consignaciones: $numeroConsignaciones")
        println("Número de retiros: $numeroRetiros")
        println("Tasa anual: $tasaAnual")
        println("Comisión mensual: $comisionMensual")
    }
}

class CuentaAhorros(
    saldo: Float,
    tasaAnual: Float
) : Cuenta(saldo, tasaAnual) {

    val activa: Boolean
        get() = saldo >= 10000

    override fun consignar(cantidad: Float) {
        if (activa) {
            super.consignar(cantidad)
        } else {
            println("No se puede consignar, la cuenta está inactiva.")
        }
    }

    override fun retirar(cantidad: Float) {
        if (activa) {
            super.retirar(cantidad)
        } else {
            println("No se puede retirar, la cuenta está inactiva.")
        }
    }

    override fun extractoMensual() {
        if (numeroRetiros > 4) {
            comisionMensual += (numeroRetiros - 4) * 1000f
        }
        super.extractoMensual()
    }

    override fun imprimir() {
        println("--- Cuenta de Ahorros ---")
        println("Saldo: $saldo")
        println("Comisión mensual: $comisionMensual")
        val totalTransacciones = numeroConsignaciones + numeroRetiros
        println("Número de transacciones realizadas: $totalTransacciones")
        println("-------------------------")
    }
}

fun main() {

}

