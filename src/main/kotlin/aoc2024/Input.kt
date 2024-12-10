package aoc2024

object Input {

    val day01 get() = readLines("Day01.txt")
    val day02 get() = readLines("Day02.txt")
    val day03 get() = readLines("Day03.txt")
    val day04 get() = readLines("Day04.txt")
    val day05 get() = readLines("Day05.txt")
    val day07 get() = readLines("Day07.txt")
    val day09 get() = readLines("Day09.txt")
    val day10 get() = readLines("Day10.txt")

    private fun readLines(filename: String): List<String> {
        return javaClass.getResourceAsStream(filename)!!.bufferedReader().use {
            it.readLines()
        }
    }
}
