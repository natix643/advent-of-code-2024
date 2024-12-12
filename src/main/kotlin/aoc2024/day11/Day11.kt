package aoc2024.day11

import java.util.LinkedList

const val example1 = "0 1 10 99 999"
const val example2 = "125 17"

fun parseStones(input: String): MutableList<Long> {
    return input.split(" ").mapTo(LinkedList()) {
        it.toLong()
    }
}

fun Long.digits(): Int = toString().length

fun Long.split(): Pair<Long, Long> {
    val string = toString()
    val left = string.take(string.length / 2).toLong()
    val right = string.drop(string.length / 2).toLong()
    return left to right
}
