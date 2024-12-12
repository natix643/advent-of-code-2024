package aoc2024.day04

import aoc2024.common.Input

object Day04b {

    fun isMas(matrix: Matrix, middle: Point): Boolean {
        if (listOf(middle.x, middle.y).any { it == 0 || it == matrix.lastIndex }) {
            return false
        }
        if (matrix[middle.y][middle.x] != 'A') {
            return false
        }

        val ascending = listOf(
            Point(middle.x - 1, middle.y - 1),
            middle,
            Point(middle.x + 1, middle.y + 1)
        ).map {
            matrix[it.y][it.x]
        }.joinToString("")

        val descending = listOf(
            Point(middle.x - 1, middle.y + 1),
            middle,
            Point(middle.x + 1, middle.y - 1)
        ).map {
            matrix[it.y][it.x]
        }.joinToString("")

        return listOf(ascending, descending).all {
            it in listOf("MAS", "SAM")
        }
    }

    fun countMas(matrix: Matrix): Int {
        var count = 0

        matrix.indices.forEach { y ->
            matrix.indices.forEach { x ->
                if (isMas(matrix, Point(x, y))) {
                    count++
                }
            }
        }
        return count
    }

    val input = Input.day04
    val matrix = input.toMatrix()

    val result = countMas(matrix)
}

fun main() {
    println(Day04b.result)
}
