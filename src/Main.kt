/*Herencia y Encapsulamiento:
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
        println("Saldo: s/.$saldo")
        println("Número de consignaciones: $numeroConsignaciones")
        println("Número de retiros: $numeroRetiros")
        println("Tasa anual: $tasaAnual %")
        println("Comisión mensual: s/.$comisionMensual")
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
        println("--- Cuenta de Ahorros -------------")
        println("Saldo: s/.$saldo")
        println("Comisión mensual: s/.$comisionMensual")
        val totalTransacciones = numeroConsignaciones + numeroRetiros
        println("Número de transacciones realizadas: $totalTransacciones")
        println("-----------------------------------")
    }
}

class CuentaCorriente(
    saldo: Float,
    tasaAnual: Float
) : Cuenta(saldo, tasaAnual) {

    var sobregiro: Float = 0f

    override fun retirar(cantidad: Float) {
        if (cantidad <= saldo) {
            super.retirar(cantidad)
        } else {
            sobregiro += cantidad - saldo
            saldo = 0f
            numeroRetiros++
        }
    }

    override fun consignar(cantidad: Float) {
        var cantidadReal = cantidad
        if (sobregiro > 0) {
            val aPagar = min(cantidad, sobregiro)
            sobregiro -= aPagar
            cantidadReal -= aPagar
        }
        super.consignar(cantidadReal)
    }

    override fun extractoMensual() {
        super.extractoMensual()
    }

    override fun imprimir() {
        println("--- Cuenta Corriente ---")
        println("Saldo: s/.$saldo")
        println("Comisión mensual: s/.$comisionMensual")
        val totalTransacciones = numeroConsignaciones + numeroRetiros
        println("Número de transacciones realizadas: $totalTransacciones")
        println("Valor de sobregiro: s/.$sobregiro")
        println("-----------------------------------")
    }
}

fun main() {
    println("--- Creando Cuenta de Ahorros ---")
    //Creamos una cuenta con saldo inicial de 17050 y tasa anual del 5%
    val miCuentaAhorros = CuentaAhorros(17050f, 5f)
    miCuentaAhorros.imprimir()

    println("\n--- Realizando Operaciones ---")
    println("* Realizando 5 retiros para probar la comisión extra")
    miCuentaAhorros.retirar(50f)
    miCuentaAhorros.retirar(80f)
    miCuentaAhorros.retirar(120f)
    miCuentaAhorros.retirar(150f)
    miCuentaAhorros.retirar(100f)
    println("* Realizando una consignación de s/.500")
    miCuentaAhorros.consignar(500f)
    println("-----------------------------------")

    println("\n--- Estado Antes del Extracto Mensual ---")
    miCuentaAhorros.imprimir()

    println("\n--- Generando Extracto Mensual ---")
    println("* Aplicando comisión por 1 retiro extra (s/.1000) y el interés mensual")
    miCuentaAhorros.extractoMensual()
    println("-----------------------------------")

    println("\n--- Estado Final de la Cuenta ---")
    miCuentaAhorros.imprimir()

    println("¿La cuenta está activa? ${miCuentaAhorros.activa}")
}

