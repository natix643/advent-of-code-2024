package aoc2024

open class Day01 {

    val example = """
        3   4
        4   3
        2   5
        1   3
        3   9
        3   3
    """.trimIndent().lines()

    fun List<String>.toPairs() : List<Pair<Int, Int>> {
        return map { line ->
            val (left, right) = line.split(Regex("\\s+")).map { it.toInt() }
            left to right
        }
    }

}
