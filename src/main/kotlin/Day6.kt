package main.kotlin

import java.io.File

sealed class Tile {
    data object Empty: Tile()
    data object Wall: Tile()
    data object Walked: Tile()
}

fun resolveDay6() {
    val map = mutableListOf<MutableList<Tile>>()
    val input = getInput()

    var guardPosition = Pair(0, 0)

    input.forEachIndexed { y, line ->
        map += line.mapIndexed { x, c ->
            when (c) {
                '#' -> Tile.Wall
                '^' -> {
                    guardPosition = Pair(x, y)
                    Tile.Walked
                }
                else -> Tile.Empty
            }
        }.toMutableList()
    }

    val vectors = listOf(
        Pair(0, -1),
        Pair(1, 0),
        Pair(0, 1),
        Pair(-1, 0)
    )

    var currentVector = 0
    var walkedTiles = 1

    // ok parce que carré
    while (true) {
        val nextPosition = guardPosition + vectors[currentVector]

        if (!(nextPosition.first in 0..map.lastIndex && nextPosition.second in 0..map.lastIndex)) {
            break
        }

        val nextTile = map[nextPosition.second][nextPosition.first]
        if (nextTile == Tile.Wall) {
            currentVector = (currentVector + 1) % 4
        } else  {
            if (nextTile == Tile.Empty) {
                walkedTiles++
                map[nextPosition.second][nextPosition.first] = Tile.Walked
            }
            guardPosition = nextPosition
        }
    }

    println("Result Exo 1 : $walkedTiles")
    //println("Result Exo 2 : $resultExo2")
}

operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>): Pair<Int, Int> {
    return Pair(first + other.first, second + other.second)
}

private fun getInput(): List<String> {
    val inputFile = File("src/main/resources/inputDay6.txt")

    val lines = mutableListOf<String>()

    inputFile.forEachLine {
        if (it.isNotBlank()) {
            lines += it
        }
    }

    return lines
}