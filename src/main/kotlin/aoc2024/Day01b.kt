package aoc2024

object Day01b : Day01() {

    val input = Input.day01
    val pairs = input.toPairs()

    val left = pairs.map { it.first }

    val right = pairs
        .map { it.second }
        .groupBy { it }
        .mapValues { (_, values) -> values.size }

    val result = left.sumOf { x ->
        x * (right[x] ?: 0)
    }
}

fun main() {
    println(Day01b.result)
}