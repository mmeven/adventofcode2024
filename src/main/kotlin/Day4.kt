package main.kotlin

import java.io.File
import kotlin.math.abs
import kotlin.math.max

fun resolveDay4() {
    val lettersMap: MutableList<List<Char>> = mutableListOf()

    getInput().forEach { line ->
        lettersMap += line.toCharArray().toList()
    }

    var resultExo1 = 0
    var resultExo2 = 0

    lettersMap.forEachIndexed { y, line ->
        line.forEachIndexed { x, char ->
            if (char == 'X') {
                resultExo1 += searchXMAS(lettersMap, x, y)
            }

            if (x >= 1 && y >= 1 && x <= line.lastIndex-1 && y <= lettersMap.lastIndex-1) {
                resultExo2 += searchMASMAS(lettersMap, x, y)
            }
        }
    }

    println("Result Exo 1 : $resultExo1")
    println("Result Exo 2 : $resultExo2")
}

private fun searchXMAS(map: List<List<Char>>, x: Int, y: Int): Int {
    var result = 0
    val maxX = map.first().lastIndex-3
    val maxY = map.lastIndex-3

    if (x >= 3 && y >= 3 && "${map[y-1][x-1]}${map[y-2][x-2]}${map[y-3][x-3]}" == "MAS") {
        result += 1
    }
    if (y >= 3 && "${map[y-1][x]}${map[y-2][x]}${map[y-3][x]}" == "MAS") {
        result += 1
    }
    if (y >= 3 && x <= maxX && "${map[y-1][x+1]}${map[y-2][x+2]}${map[y-3][x+3]}" == "MAS") {
        result += 1
    }
    if (x <= maxX && "${map[y][x+1]}${map[y][x+2]}${map[y][x+3]}" == "MAS") {
        result += 1
    }
    if (x <= maxX && y <= maxY && "${map[y+1][x+1]}${map[y+2][x+2]}${map[y+3][x+3]}" == "MAS") {
        result += 1
    }
    if (y <= maxX && "${map[y+1][x]}${map[y+2][x]}${map[y+3][x]}" == "MAS") {
        result += 1
    }
    if (y <= maxX && x >= 3 && "${map[y+1][x-1]}${map[y+2][x-2]}${map[y+3][x-3]}" == "MAS") {
        result += 1
    }
    if (x >= 3 && "${map[y][x-1]}${map[y][x-2]}${map[y][x-3]}" == "MAS") {
        result += 1
    }

    return result
}

private fun searchMASMAS(map: List<List<Char>>, x: Int, y: Int): Int {
    var result = 0

    /*
    M M
     A
    S S
     */
    if ("${map[y-1][x-1]}${map[y][x]}${map[y+1][x+1]}" == "MAS" && "${map[y-1][x+1]}${map[y][x]}${map[y+1][x-1]}" == "MAS") {
        result += 1
    }

    /*
    S M
     A
    S M
     */
    if ("${map[y-1][x+1]}${map[y][x]}${map[y+1][x-1]}" == "MAS" && "${map[y+1][x+1]}${map[y][x]}${map[y-1][x-1]}" == "MAS") {
        result += 1
    }

    /*
    S S
     A
    M M
     */
    if ("${map[y+1][x-1]}${map[y][x]}${map[y-1][x+1]}" == "MAS" && "${map[y+1][x+1]}${map[y][x]}${map[y-1][x-1]}" == "MAS") {
        result += 1
    }

    /*
    M S
     A
    M S
     */
    if ("${map[y-1][x-1]}${map[y][x]}${map[y+1][x+1]}" == "MAS" && "${map[y+1][x-1]}${map[y][x]}${map[y-1][x+1]}" == "MAS") {
        result += 1
    }

    return result
}

private fun getInput(): List<String> {
    val inputFile = File("src/main/resources/inputDay4.txt")

    val lines = mutableListOf<String>()

    inputFile.forEachLine {
        if (it.isNotBlank()) {
            lines += it
        }
    }

    return lines
}