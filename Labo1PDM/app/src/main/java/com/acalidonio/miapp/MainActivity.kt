package com.acalidonio.miapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        main()

    }

    // Clase del ej 1
    class Computadora(
        var ram: Int,
        var almacenamiento: Int,
        var sistemaOperativo: String,
        val programasInstalados: List<String>
    ) {
        fun encender() {
            println("La computadora se está encendiendo...")
        }

        fun apagar() {
            println("La computadora se está apagando...")
        }

        fun cambiarRam(nuevaRam: Int) {
            this.ram = nuevaRam
            println("Se cambió la RAM a una de $nuevaRam GB.")
        }

        fun cambiarAlmacenamiento(nuevoAlmacenamiento: Int) {
            this.almacenamiento = nuevoAlmacenamiento
            println("Almacenamiento cambiado a uno de $nuevoAlmacenamiento GB.")
        }

        fun actualizarSO(nuevoSO: String) {
            this.sistemaOperativo = nuevoSO
            println("Sistema operativo actualizado a $nuevoSO.")
        }

        fun obtenerProgramasEsteAnio(anio: String = "2026"): List<String> {
            return programasInstalados.filter { it.contains(anio) }
        }
    }

    // Clase del ej 2
    class Calculadora(
        val marca: String,
        val aniosDeVida: Int,
        var precio: Double
    ) {
        fun sumar(a: Double, b: Double) = a + b
        fun restar(a: Double, b: Double) = a - b
        fun multiplicar(a: Double, b: Double) = a * b
        fun dividir(a: Double, b: Double): Double {
            return if (b != 0.0) {
                a / b
            } else {
                println("Error: No es posible dividir entre cero.")
                Double.NaN
            }
        }
    }

    // Clase del ej 3
    data class Estudiante(
        val nombre: String,
        val carnet: String,
        val asignatura: String
    )

    fun main() {
        // Test Ej 1
        println("Ejercicio 1")
        val programas = listOf("Notion 2026", "Facebook 2024", "VS Code 2026", "Spotify 2026", "IntelliJ 2025")
        val miPC = Computadora(8, 512, "Windows 10", programas)
        miPC.encender()
        miPC.cambiarRam(16)
        miPC.cambiarAlmacenamiento(1024)
        miPC.actualizarSO("Windows 11")
        val programasActuales = miPC.obtenerProgramasEsteAnio("2026")
        println("Programas del 2026: $programasActuales")
        miPC.apagar()

        // Test Ej 2
        println("\nEjercicio 2")
        val miCalc = Calculadora("Casio", 10, 15.99)
        println("Suma: 10 + 5 = ${miCalc.sumar(10.0, 5.0)}")
        println("División: 10 / 0 = ${miCalc.dividir(10.0, 0.0)}")
        println("División: 10 / 2 = ${miCalc.dividir(10.0, 2.0)}")
        println("Multiplicación: 12 * 4 = ${miCalc.multiplicar(12.0, 4.0)}")
        println("Resta: 10 - 8 = ${miCalc.restar(10.0, 8.0)}")

        // Test Ej 3
        println("\nEjercicio 3")
        val ciclo01 = mutableListOf<Estudiante>()

        // Estudiantes de PDM
        ciclo01.add(Estudiante("André Calidonio", "AC001", "Programación de Dispositivos Móviles"))
        ciclo01.add(Estudiante("María López", "ML002", "Programación de Dispositivos Móviles"))
        ciclo01.add(Estudiante("Juan Pérez", "JP003", "Programación de Dispositivos Móviles"))

        // Estudiantes de Análisis Numérico
        ciclo01.add(Estudiante("Gabriela Juárez", "GJ004", "Análisis numérico"))
        ciclo01.add(Estudiante("Pedro Osorio", "PO005", "Análisis numérico"))
        ciclo01.add(Estudiante("Eduardo Llanos", "EL006", "Análisis numérico"))
        ciclo01.add(Estudiante("Manuel Aguilares", "MA007", "Análisis numérico"))

        val soloPDM = ciclo01.filter { it.asignatura == "Programación de Dispositivos Móviles" }
        println("Estudiantes en Programación de Dispositivos Móviles:")
        soloPDM.forEach { println("- ${it.nombre} (${it.carnet})") }
    }
}