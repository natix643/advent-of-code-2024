package aoc2024

object Day02b : Day02() {

    fun isReportSafe(report: IntList): Boolean {
        return report.steps().isSafe() ||
                generateCandidates(report).any {
                    it.steps().isSafe()
                }
    }

    fun generateCandidates(steps: IntList): List<IntList> {
        return steps.indices.map { i ->
            steps.toMutableList().apply {
                removeAt(i)
            }
        }
    }

    val reports = parseReports(Input.day02)

    val result = reports.count {
        isReportSafe(it)
    }
}

fun main() {
    println(Day02b.result)
}
