package aoc2024.day07

val example = """
    190: 10 19
    3267: 81 40 27
    83: 17 5
    156: 15 6
    7290: 6 8 6 15
    161011: 16 10 13
    192: 17 8 14
    21037: 9 7 18 13
    292: 11 6 16 20
""".trimIndent().lines()

typealias Operator = (x: Long, y: Long) -> Long

data class Equation(
    val result: Long,
    val operands: List<Long>
)

fun parseEquations(lines: List<String>): List<Equation> {
    return lines.map { line ->
        val (left, right) = line.split(":")
        Equation(
            result = left.toLong(),
            operands = right.trim().split(" ").map { it.toLong() }
        )
    }
}

fun possibleResults(operands: List<Long>, operators: List<Operator>): Set<Long> {
    var results = setOf(operands.first())

    for (operand in operands.drop(1)) {
        val nextResults = mutableSetOf<Long>()

        for (previous in results) {
            for (operator in operators) {
                nextResults += operator(previous, operand)
            }
        }
        results = nextResults
    }
    return results
}
