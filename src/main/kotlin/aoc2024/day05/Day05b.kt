package aoc2024.day05

import aoc2024.common.Input
import aoc2024.common.expecting

object Day05b {

    val input = Input.day05

    val rules = parseRules(input)
    val updates = parseUpdates(input)

    val result = updates.mapNotNull {
        val sorted = sort(it, rules)
        if (it != sorted) sorted else null
    }.sumOf {
        it.middle()
    }
}

fun main() {
    println(Day05b.result.expecting(6456))
}
