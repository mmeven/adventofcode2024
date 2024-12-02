import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
    println("Hello World!")

    measure("resolveDay1") { resolveDay1() }
}

fun measure(text: String, code: () -> Unit) {
    val time = measureTimeMillis(code)
    println("execution time of ${text}: ${time}ms")
}