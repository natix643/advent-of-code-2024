package aoc2024

import kotlin.math.absoluteValue

object Day01a : Day01() {

    val input = Input.day01
    val pairs = input.toPairs()

    val left = pairs.map { it.first }.sorted()
    val right = pairs.map { it.second }.sorted()

    val result = left.zip(right).sumOf { (x, y) ->
        (x - y).absoluteValue
    }
}

fun main() {
    println(Day01a.result)
}
