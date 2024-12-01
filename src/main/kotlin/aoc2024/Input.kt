package aoc2024

object Input {

    val day01 get() = readLines("Day01.txt")

    private fun readLines(filename: String): List<String> {
        return javaClass.getResourceAsStream(filename)!!.bufferedReader().use {
            it.readLines()
        }
    }
}
