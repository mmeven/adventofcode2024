package main.kotlin

import java.io.File
import kotlin.math.abs
import kotlin.math.max

fun resolveDay2() {
    var safeReportCount = 0

    var safeishReportCount = 0

    getInput().forEach { report ->
        val levels = report.split(" ").map { it.toInt() }
        safeReportCount += solve(levels, true)

        safeishReportCount += solve(levels, false)
    }

    println("Result Exo 1 = $safeReportCount")
    println("Result Exo 2 = $safeishReportCount")
}

private fun solve(levels: List<Int>, skipped: Boolean): Int {
    val isIncreasing = levels.first() < levels.last()

    var firstIndex = 0
    var secondIndex = 1

    while (secondIndex <= levels.lastIndex) {
        val diff = levels[firstIndex] - levels[secondIndex]
        val wrong = if (diff == 0) {
            true
        } else if (diff < 0 && !isIncreasing) {
            true
        } else if (diff > 0 && isIncreasing) {
            true
        } else if (abs(diff) > 3) {
            true
        } else {
            false
        }

        if (wrong) {
            return if (skipped) {
                0
            } else {
                val levels1 = levels.toMutableList().also {
                    it.removeAt(firstIndex)
                }
                val levels2 = levels.toMutableList().also {
                    it.removeAt(secondIndex)
                }

                max(solve(levels1, true), solve(levels2, true))
            }
        } else {
            firstIndex++
            secondIndex++
        }
    }

    return 1
}

private fun getInput(): List<String> {
    val inputFile = File("src/main/resources/inputDay2.txt")

    val lines = mutableListOf<String>()

    inputFile.forEachLine {
        if (it.isNotBlank()) {
            lines += it
        }
    }

    return lines
}