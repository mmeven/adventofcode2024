package main.kotlin

import java.io.File
import kotlin.math.abs

fun resolveDay1() {
    val firstColumn = mutableListOf<Int>()
    val secondColumn = mutableListOf<Int>()
    val occurences = mutableMapOf<Int, Int>()

    getInput().forEach {
        val splitted = it.split("\\s+".toRegex())

        val firstInt = splitted.first().toInt()
        val secondInt = splitted.last().toInt()

        firstColumn.addFirst(firstInt)
        secondColumn.addFirst(secondInt)
        occurences[secondInt] = occurences.getOrDefault(secondInt, 0) + 1
    }

    firstColumn.sort()
    secondColumn.sort()

    var resultExo1 = 0

    firstColumn.forEachIndexed { index, i ->
        resultExo1 += abs(i - secondColumn[index])
    }

    println("Result Exo 1 = $resultExo1")

    var resultExo2 = 0
    firstColumn.forEach {
        resultExo2 += it * occurences.getOrDefault(it, 0)
    }
    println("Result Exo 2 = $resultExo2")
}

private fun getInput(): List<String> {
    val inputFile = File("src/main/resources/inputDay1.txt")

    val lines = mutableListOf<String>()

    inputFile.forEachLine {
        if (it.isNotBlank()) {
            lines += it
        }
    }

    return lines
}