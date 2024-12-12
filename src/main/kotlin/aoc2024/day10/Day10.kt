package aoc2024.day10

import aoc2024.common.Matrix

fun parseMatrix(lines: List<String>): Matrix<Int?> {
    return Matrix.parse(lines) {
        if (it == '.') null else it.digitToInt()
    }
}
