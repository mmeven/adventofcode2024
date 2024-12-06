package main.kotlin

import java.io.File

fun resolveDay5() {
    val pages = mutableMapOf<Int, MutableList<Int>>()
    val input = getInput()

    val (orders, prints) = input.groupBy { it.contains("|") }.values.let {
        Pair(it.first(), it.last())
    }

    orders.forEach { line ->
        val (number1, number2) = line.split("|").let {
            Pair(it.first().toInt(), it.last().toInt())
        }

        if (!pages.contains(number2)) {
            pages[number2] = mutableListOf(number1)
        } else {
            pages[number2]!!.add(number1)
        }
    }

    val correctLines = mutableListOf<List<Int>>()
    val incorrectLines = mutableListOf<List<Int>>()

    prints.map { it.split(",").map { it.toInt() } }.forEach Orders@ { printOrder ->
        val printed = printOrder

        printed.subList(0, printed.lastIndex).forEachIndexed { index, printedPage ->
            if (printed.subList(index + 1, printed.lastIndex + 1).any { pages.getOrDefault(printedPage, emptyList()).contains(it) }) {
                incorrectLines += printOrder
                return@Orders
            }
        }

        correctLines += printOrder
    }

    val fixedLines = mutableListOf<List<Int>>()

    incorrectLines.forEach { printOrder ->
        val printed = printOrder.toMutableList()
        var fixed = false

        var index = 0
        while (index < printed.lastIndex) {
            val printedPage = printed[index]
            val wrongPageIndex = printed.subList(index, printed.lastIndex + 1).indexOfFirst { pages.getOrDefault(printedPage, emptyList()).contains(it) } + index

            if (wrongPageIndex != -1 && wrongPageIndex > index) {
                printed.removeAt(index)
                printed.add(index = wrongPageIndex, element = printedPage)

                fixed = true
            } else {
                index++
            }
        }

        if (fixed) {
            fixedLines += printed
        }
    }

    val resultExo1 = correctLines.sumOf {
        it[it.lastIndex / 2]
    }

    val resultExo2 = fixedLines.sumOf {
        it[it.lastIndex / 2]
    }

    println("Result Exo 1 : $resultExo1")
    println("Result Exo 2 : $resultExo2")
}

private fun getInput(): List<String> {
    val inputFile = File("src/main/resources/inputDay5.txt")

    val lines = mutableListOf<String>()

    inputFile.forEachLine {
        if (it.isNotBlank()) {
            lines += it
        }
    }

    return lines
}