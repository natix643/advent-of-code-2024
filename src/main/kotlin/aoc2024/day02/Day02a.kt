package aoc2024.day02

import aoc2024.Input

object Day02a : Day02() {

    fun isReportSafe(report: IntList): Boolean {
        return report.steps().isSafe()
    }

    val reports = parseReports(Input.day02)

    val result = reports.count {
        isReportSafe(it)
    }

}

fun main() {
    println(Day02a.result)
}
