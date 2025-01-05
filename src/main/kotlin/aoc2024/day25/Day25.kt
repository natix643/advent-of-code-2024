package aoc2024.day25

import aoc2024.common.Input
import aoc2024.common.Matrix
import aoc2024.common.expecting

val example = """
    #####
    .####
    .####
    .####
    .#.#.
    .#...
    .....

    #####
    ##.##
    .#.##
    ...##
    ...#.
    ...#.
    .....

    .....
    #....
    #....
    #...#
    #.#.#
    #.###
    #####

    .....
    .....
    #.#..
    ###..
    ###.#
    ###.#
    #####

    .....
    .....
    .....
    #....
    #.#..
    #.#.#
    #####
""".trimIndent()

const val PIN = '#'
const val MAX_HEIGHT = 5

fun parseMatrices(input: String): List<Matrix<Char>> {
    return input
        .split(Regex("\n\n"))
        .map { block -> Matrix.parse(block.lines()) }
}

fun Matrix<Char>.isLock(): Boolean {
    return rows().first().all { it == PIN }
}

fun Matrix<Char>.isKey(): Boolean {
    return rows().last().all { it == PIN }
}

fun Matrix<Char>.heights(): List<Int> {
    return columns().map { column ->
        column.count { it == PIN } - 1
    }
}

fun fit(lockHeights: List<Int>, keyHeights: List<Int>): Boolean {
    return lockHeights.zip(keyHeights).all { (l, k) ->
        l + k <= MAX_HEIGHT
    }
}

object Day25 {
    val input = Input.day25.joinToString("\n")

    val matrices = parseMatrices(input)

    val locks = matrices.filter { it.isLock() }.map { it.heights() }
    val keys = matrices.filter { it.isKey() }.map { it.heights() }

    val result = locks.sumOf { lock ->
        keys.count { key ->
            fit(lock, key)
        }
    }
}

fun main() {
    println(Day25.result.expecting(3133))
}
