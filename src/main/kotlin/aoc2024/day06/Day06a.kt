package aoc2024.day06

import aoc2024.common.Input
import aoc2024.common.Matrix
import aoc2024.common.expecting
import aoc2024.day06.Day06a.duration
import aoc2024.day06.Tile.VISITED
import kotlin.time.measureTime

object Day06a {

    val input = Input.day06
    val matrix = Matrix.parse(input, Tile::parse)

    val duration = measureTime {
        walk(matrix, draw = false, redrawMs = 0)
    }

    val result = matrix.findAll(VISITED).size
}

fun main() {
    println(Day06a.result.expecting(4778))
    println("duration: $duration")
}
