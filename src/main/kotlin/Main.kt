package main.kotlin

import kotlin.system.measureTimeMillis

fun main() {
    println("Hello World!")

    measure("main.kotlin.resolveDay1") { resolveDay1() }
    measure("main.kotlin.resolveDay2") { resolveDay2() }
    measure("main.kotlin.resolveDay3") { resolveDay3() }
    measure("main.kotlin.resolveDay4") { resolveDay4() }
    measure("main.kotlin.resolveDay5") { resolveDay5() }
    measure("main.kotlin.resolveDay6") { resolveDay6() }
}

fun measure(text: String, code: () -> Unit) {
    val time = measureTimeMillis(code)
    println("execution time of ${text}: ${time}ms")
}