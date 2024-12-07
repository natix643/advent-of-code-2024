package aoc2024.day01

import aoc2024.Input
import kotlin.math.absoluteValue

object Day01a {

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
