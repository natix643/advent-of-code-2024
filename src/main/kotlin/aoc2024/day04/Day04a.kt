package aoc2024.day04

import aoc2024.common.Input
import kotlin.math.min

object Day04a {

    val regex = Regex("XMAS")

    fun countOccurrences(string: String): Int {
        return listOf(string, string.reversed()).sumOf {
            regex.findAll(it).count()
        }
    }

    fun columns(matrix: Matrix): List<String> {
        return matrix.first().indices.map { x ->
            matrix.indices.map { y ->
                matrix[y][x]
            }.joinToString("")
        }
    }

    fun ascendingDiagonal(matrix: Matrix, start: Point): String {
        val builder = StringBuilder()

        val xDiff = matrix.first().lastIndex - start.x
        val yDiff = matrix.lastIndex - start.y
        val max = min(xDiff, yDiff)

        for (i in 0..max) {
            builder.append(matrix[start.y + i][start.x + i])
        }
        return builder.toString()
    }

    fun descendingDiagonal(matrix: Matrix, start: Point): String {
        val builder = StringBuilder()

        val xDiff = matrix.first().lastIndex - start.x
        val yDiff = start.y
        val max = min(xDiff, yDiff)

        for (i in 0..max) {
            builder.append(matrix[start.y - i][start.x + i])
        }
        return builder.toString()
    }

    fun ascendingDiagonals(matrix: Matrix): List<String> {
        val result = mutableListOf<String>()
        val max = matrix.lastIndex

        for (x in max.downTo(0)) {
            result += ascendingDiagonal(matrix, Point(x, 0))
        }
        for (y in 1..max) {
            result += ascendingDiagonal(matrix, Point(0, y))
        }
        return result
    }

    fun descendingDiagonals(matrix: Matrix): List<String> {
        val result = mutableListOf<String>()
        val max = matrix.lastIndex

        for (y in 0..max) {
            result += descendingDiagonal(matrix, Point(0, y))
        }
        for (x in 1..max) {
            result += descendingDiagonal(matrix, Point(x, max))
        }
        return result
    }

    val input = Input.day04
    val matrix = input.toMatrix()

    val rows = input
    val columns = columns(matrix)
    val ascendingDiagonals = ascendingDiagonals(matrix)
    val descendingDiagonals = descendingDiagonals(matrix)

    val result = listOf(rows, columns, ascendingDiagonals, descendingDiagonals)
        .flatten()
        .sumOf { countOccurrences(it) }
}

fun main() {
    println(Day04a.result)
}
