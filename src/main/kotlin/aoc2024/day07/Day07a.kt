package aoc2024.day07

import aoc2024.Input
import aoc2024.expecting

object Day07a {

    val input = Input.day07
    val equations = parseEquations(input)

    val operators = listOf<Operator>(
        { x, y -> x + y },
        { x, y -> x * y }
    )

    val result = equations.filter {
        it.result in possibleResults(it.operands, operators)
    }.sumOf {
        it.result
    }
}

fun main() {
    println(Day07a.result.expecting(4122618559853))
}
