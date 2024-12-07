package aoc2024.day04

val example = """
    MMMSXXMASM
    MSAMXMSMSA
    AMXSXMAAMM
    MSAMASMSMX
    XMASAMXAMM
    XXAMMXXAMA
    SMSMSASXSS
    SAXAMASAAA
    MAMMMXMMMM
    MXMXAXMASX
""".trimIndent().lines()

typealias Matrix = List<List<Char>>

data class Point(
    val x: Int,
    val y: Int
)

fun List<String>.toMatrix(): Matrix = map {
    it.toCharArray().toList()
}
