package aoc2024.day10

import aoc2024.Input
import aoc2024.expecting

object Day10a {

    val example1 = """
        0123
        1234
        8765
        9876
    """.trimIndent().lines()

    val example2 = """
        ...0...
        ...1...
        ...2...
        6543456
        7.....7
        8.....8
        9.....9
    """.trimIndent().lines()

    val example3 = """
        ..90..9
        ...1.98
        ...2..7
        6543456
        765.987
        876....
        987....
    """.trimIndent().lines()

    val example4 = """
        10..9..
        2...8..
        3...7..
        4567654
        ...8..3
        ...9..2
        .....01
    """.trimIndent().lines()

    val example5 = """
        89010123
        78121874
        87430965
        96549874
        45678903
        32019012
        01329801
        10456732
    """.trimIndent().lines()

    fun findEnds(matrix: Matrix, start: Point): Set<Point> {
        val queue = ArrayDeque(listOf(start))
        val seen = mutableSetOf(start)
        val ends = mutableSetOf<Point>()

        while (queue.isNotEmpty()) {
            // println(queue)
            val current = queue.removeFirst()
            if (matrix[current] == 9) {
                ends += current
            }

            val next = matrix.neighbors(current).filter {
                (matrix[it] == matrix[current]!! + 1) && (it !in seen)
            }
            queue += next
            seen += next
        }
        return ends
    }

    val input = Input.day10

    val matrix = input.parseMatrix()
    val starts = matrix.findStarts()

    val result = starts.sumOf {
        findEnds(matrix, it).size
    }
}

fun main() {
    println(Day10a.result.expecting(709))
}
