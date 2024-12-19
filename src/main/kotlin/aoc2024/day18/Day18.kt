package aoc2024.day18

import aoc2024.common.CLEAR_SCREEN
import aoc2024.common.Matrix
import aoc2024.common.Point
import aoc2024.day18.Tile.*

val example = """
    5,4
    4,2
    4,5
    3,0
    2,1
    6,3
    2,4
    1,5
    0,6
    3,3
    2,6
    5,1
    1,2
    5,5
    2,5
    6,5
    1,4
    0,4
    6,4
    1,1
    6,1
    1,0
    0,5
    1,6
    2,0
""".trimIndent().lines()

val exampleSize = 12
val inputSize = 1024

object Visualisation {
    val enabled = false
    val intervalMs = 100L
}

enum class Tile(val char: Char) {
    SAFE('.'),
    CORRUPTED('#'),
    PATH('O')
}

fun parsePoints(lines: List<String>): List<Point> {
    return lines.map { line ->
        val (x, y) = line.split(",").map { it.toInt() }
        Point(x, y)
    }
}

fun maxCoords(points: List<Point>) = Point(
    points.maxOf { it.x },
    points.maxOf { it.y }
)

fun emptyMatrix(maxCoords: Point): Matrix<Tile> = Matrix(
    items = List(maxCoords.y + 1) {
        MutableList(maxCoords.x + 1) { SAFE }
    }
)

fun corrupt(matrix: Matrix<Tile>, points: List<Point>) {
    for (point in points) {
        matrix[point] = CORRUPTED
    }
}

fun findShortestPath(matrix: Matrix<Tile>, target: Point): List<Point>? {
    val seen = mutableSetOf(Point(0, 0))
    val queue = ArrayDeque(seen)
    val predecessors = mutableMapOf<Point, Point>()

    var step = 0
    while (queue.isNotEmpty()) {
        val current = queue.removeFirst()

        if (Visualisation.enabled) {
            print(CLEAR_SCREEN)
            println("Step ${step++}\n")
            println(matrix.format { point, item ->
                if (point in seen) PATH.char else item.char
            })
            Thread.sleep(Visualisation.intervalMs)
        }

        if (current == target) {
            return constructPath(current, predecessors)
        }

        val neighbors = matrix.neighbors(current).filter {
            it !in seen && matrix[it] != CORRUPTED
        }
        neighbors.forEach {
            seen += it
            predecessors[it] = current
        }
        queue.addAll(neighbors)
    }
    return null
}

fun constructPath(target: Point, predecessors: Map<Point, Point>): List<Point> {
    val path = mutableListOf<Point>()
    var current: Point? = target

    while (current != null) {
        path += current
        current = predecessors[current]
    }
    return path
}
