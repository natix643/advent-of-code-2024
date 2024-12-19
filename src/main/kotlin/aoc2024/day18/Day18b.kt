package aoc2024.day18

import aoc2024.common.Input
import aoc2024.common.Matrix
import aoc2024.common.Point
import aoc2024.common.expecting
import kotlin.time.measureTimedValue

object Day18b {

    fun findFirstBlockingPoint(matrix: Matrix<Tile>, target: Point, points: List<Point>): Point? {
        for (point in points) {
            corrupt(matrix, listOf(point))
            if (findShortestPath(matrix, target) == null) {
                return point
            }
        }
        return null
    }

    val input = Input.day18
    val safeCorruptedPoints = inputSize

    val points = parsePoints(input)
    val target = maxCoords(points)

    val matrix = emptyMatrix(target).apply {
        corrupt(this, points.take(safeCorruptedPoints))
    }

    val result = measureTimedValue {
        findFirstBlockingPoint(matrix, target, points.drop(safeCorruptedPoints))
    }

}

fun main() {
    println(Day18b.result.value.expecting(Point(58, 62)))
    println(Day18b.result.duration)
}
