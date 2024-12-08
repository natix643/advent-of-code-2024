package aoc2024.day05

import aoc2024.Input
import aoc2024.expecting

object Day05a {

    val input = Input.day05

    val rules = parseRules(input)
    val updates = parseUpdates(input)

    val result = updates.filter {
        it == sort(it, rules)
    }.sumOf {
        it.middle()
    }
}

fun main() {
    println(Day05a.result.expecting(4569))
}
