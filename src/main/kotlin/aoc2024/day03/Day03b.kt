package aoc2024.day03

import aoc2024.common.Input

object Day03b {

    val example = listOf("xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))")

    val input = Input.day03

    val regex = Regex("""mul\((\d+),(\d+)\)|do\(\)|don't\(\)""")

    fun eval(lines: List<String>): Int {
        var sum = 0
        var on = true

        for (line in lines) {
            for (match in regex.findAll(line)) {
                val (instruction, x, y) = match.groupValues
                when {
                    instruction == "do()" -> on = true
                    instruction == "don't()" -> on = false
                    on -> sum += x.toInt() * y.toInt()
                }
            }
        }

        return sum
    }

    val result = eval(input)
}

fun main() {
    println(Day03b.result)
}
