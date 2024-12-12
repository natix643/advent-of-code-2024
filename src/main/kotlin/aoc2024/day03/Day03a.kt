package aoc2024.day03

import aoc2024.common.Input

object Day03a {

    val example = listOf("xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))")

    val input = Input.day03

    val regex = Regex("""mul\((\d+),(\d+)\)""")

    fun eval(match: MatchResult): Int {
        val (_, x, y) = match.groupValues
        return x.toInt() * y.toInt()
    }

    val result = input.flatMap { line ->
        regex.findAll(line).map { match ->
            eval(match)
        }
    }.sum()

}

fun main() {
    println(Day03a.result)
}
