package main.kotlin

import java.io.File

sealed class Tile {
    data object Wall: Tile()
    data class Empty(val walkedVectors: MutableList<Int> = mutableListOf()): Tile()
}

fun resolveDay6() {
    val map = mutableListOf<MutableList<Tile>>()
    val input = getInput()

    var firstPosition = Pair(0, 0)

    input.forEachIndexed { y, line ->
        map += line.mapIndexed { x, c ->
            when (c) {
                '#' -> Tile.Wall
                '^' -> {
                    firstPosition = Pair(x, y)
                    Tile.Empty()
                }
                else -> Tile.Empty()
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
    var walkedTiles = 0
    var currentPosition = Pair(firstPosition.first, firstPosition.second)
    var possibleLoops = 0

    while (currentPosition.first in 0..map.lastIndex && currentPosition.second in 0..map.lastIndex) {
        val currentTile = map[currentPosition.second][currentPosition.first]

        if (currentTile is Tile.Wall) {
            currentPosition -= vectors[currentVector]
            currentVector = (currentVector + 1) % 4
        } else if (currentTile is Tile.Empty) {
            if (currentTile.walkedVectors.isEmpty()) {
                walkedTiles++
            }
            currentTile.walkedVectors += currentVector
        }

        currentPosition += vectors[currentVector]

        // on essaie de mettre un mur ici
        if (currentPosition.first in 0..map.lastIndex && currentPosition.second in 0..map.lastIndex) {
            val nextTile = map[currentPosition.second][currentPosition.first]

            if (nextTile is Tile.Empty) {
                var alternativeCurrentPosition = currentPosition.copy() - vectors[currentVector]
                val alternativeMap = map.map { line ->
                        line.map {
                        if (it is Tile.Empty) {
                            it.copy()
                        } else {
                            it
                        }
                    }.toMutableList()
                }
                alternativeMap[currentPosition.second][currentPosition.first] = Tile.Wall
                var alternativeVector = currentVector

                while (alternativeCurrentPosition.first in 0..map.lastIndex && alternativeCurrentPosition.second in 0..map.lastIndex) {
                    val alternativeCurrentTile = alternativeMap[alternativeCurrentPosition.second][alternativeCurrentPosition.first]

                    if (alternativeCurrentTile is Tile.Wall) {
                        alternativeCurrentPosition -= vectors[alternativeVector]
                        alternativeVector = (alternativeVector + 1) % 4
                    } else if (alternativeCurrentTile is Tile.Empty) {
                        if (alternativeCurrentTile.walkedVectors.contains(alternativeVector)) {
                            possibleLoops++
                            break
                        }
                        alternativeCurrentTile.walkedVectors += currentVector
                    }

                    alternativeCurrentPosition += vectors[currentVector]
                }
            }
        }
    }

    /*
    while (true) {
        val nextPosition = guardPosition + vectors[currentVector]

        // ok parce que carré - on sort de la MAP, c'est fini
        if (!(nextPosition.first in 0..map.lastIndex && nextPosition.second in 0..map.lastIndex)) {
            break
        }

        val nextTile = map[nextPosition.second][nextPosition.first]
        if (nextTile == Tile.Wall) { // On tourne, et on ajoute la deuxième direction à cette case
            currentVector = (currentVector + 1) % 4
            (map[guardPosition.second][guardPosition.first] as? Tile.Walked)?.vectors?.add(currentVector)
        } else {
            var alternativeVector = (currentVector + 1) % 4
            var nextPositionIfTurning = guardPosition + vectors[alternativeVector]
            val newMap = map.map { it.toMutableList() } // copie

            while (true) {
                if(!(nextPositionIfTurning.first in 0..map.lastIndex && nextPositionIfTurning.second in 0..map.lastIndex)) {
                    break
                }

                val nextTileIfTurning = newMap[nextPositionIfTurning.second][nextPositionIfTurning.first]

                if (nextTileIfTurning == Tile.Wall) {
                    alternativeVector = (alternativeVector + 1) % 4
                    (newMap[guardPosition.second][guardPosition.first] as? Tile.Walked)?.vectors?.add(currentVector)
                } else if ((nextTileIfTurning is Tile.Walked && nextTileIfTurning.vectors.contains(alternativeVector))) {
                    possibleLoops++
                    break
                } else if (nextTileIfTurning is Tile.Empty) {
                    newMap[guardPosition.second][guardPosition.first] = Tile.Walked(alternativeVector)
                } else if (nextTileIfTurning is Tile.Walked) {
                    nextTileIfTurning.vectors.add(currentVector)
                }

                nextPositionIfTurning += vectors[alternativeVector]
            }

            if (nextTile == Tile.Empty) {
                walkedTiles++
                map[nextPosition.second][nextPosition.first] = Tile.Walked(currentVector)
            } else if (nextTile is Tile.Walked) {
                nextTile.vectors.add(currentVector)
            }

            guardPosition = nextPosition
        }
    }*/

    println("Result Exo 1 : $walkedTiles")
    println("Result Exo 2 : $possibleLoops")

    map.forEach { line ->
        line.forEach { tile ->
            when (tile) {
                is Tile.Empty -> {
                    if (tile.walkedVectors.isEmpty()) {
                        print(".")
                    } else {
                        when (tile.walkedVectors.first()) {
                            0 -> print("↑")
                            1 -> print("→")
                            2 -> print("↓")
                            3 -> print("←")
                        }
                    }
                }
                Tile.Wall -> print("#")
            }
        }
        println()
    }
}

operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>): Pair<Int, Int> {
    return Pair(first + other.first, second + other.second)
}

operator fun Pair<Int, Int>.minus(other: Pair<Int, Int>): Pair<Int, Int> {
    return Pair(first - other.first, second - other.second)
}

private fun getInput(): List<String> {
    val inputFile = File("src/main/resources/inputTest.txt")

    val lines = mutableListOf<String>()

    inputFile.forEachLine {
        if (it.isNotBlank()) {
            lines += it
        }
    }

    return lines
}