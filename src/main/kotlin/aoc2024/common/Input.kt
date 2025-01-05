package aoc2024.common

object Input {

    val day01 get() = readLines("Day01.txt")
    val day02 get() = readLines("Day02.txt")
    val day03 get() = readLines("Day03.txt")
    val day04 get() = readLines("Day04.txt")
    val day05 get() = readLines("Day05.txt")
    val day06 get() = readLines("Day06.txt")
    val day07 get() = readLines("Day07.txt")
    val day09 get() = readLines("Day09.txt")
    val day10 get() = readLines("Day10.txt")
    val day11 get() = readLines("Day11.txt")
    val day18 get() = readLines("Day18.txt")
    val day19 get() = readLines("Day19.txt")
    val day25 get() = readLines("Day25.txt")

    private fun readLines(filename: String): List<String> {
        return javaClass.getResourceAsStream("/aoc2024/$filename")!!.bufferedReader().use {
            it.readLines()
        }
    }
}
