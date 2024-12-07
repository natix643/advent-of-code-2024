package aoc2024.day02

import kotlin.math.absoluteValue

typealias IntList = List<Int>

val example = """
    7 6 4 2 1
    1 2 7 8 9
    9 7 6 2 1
    1 3 2 4 5
    8 6 4 4 1
    1 3 6 7 9
""".trimIndent().lines()

fun parseReports(input: List<String>): List<IntList> = input.map { line ->
    line.split(" ").map { it.toInt() }
}

fun IntList.isSafe(): Boolean {
    return all { it.absoluteValue in (1..3) } &&
            (all { it > 0 } || all { it < 0 })
}

fun IntList.steps(): IntList =
    windowed(2).map { (x, y) -> (y - x) }
