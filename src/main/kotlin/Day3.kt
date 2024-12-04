package main.kotlin

import java.io.File
import kotlin.math.abs
import kotlin.math.max

fun resolveDay3() {
    val regexDo = Regex("don't\\(\\)[\\S\\s]*?(do\\(\\)|\\n|$)")

    var allMemory = ""

    getInput().forEach { line ->
        allMemory += line
    }

    val resultExo1 = solve(allMemory)

    allMemory = regexDo.replace(allMemory, "")

    val resultExo2 = solve(allMemory)

    println("Result Exo 1 : $resultExo1")
    println("Result Exo 1 : $resultExo2")
}

private fun solve(content: String): Int {
    var result = 0
    val regex = Regex(".*?(mul\\([0-9]{1,3},[0-9]{1,3}\\)).*?")

    regex.findAll(content).forEach { match ->
        match.groups[1]!!.value.removePrefix("mul(").removeSuffix(")").split(",").map { it.toInt() }.let {
            result += it.first() * it.last()
        }
    }

    return result
}

private fun getInput(): List<String> {
    val inputFile = File("src/main/resources/inputDay3.txt")

    val lines = mutableListOf<String>()

    inputFile.forEachLine {
        if (it.isNotBlank()) {
            lines += it
        }
    }

    return lines
}