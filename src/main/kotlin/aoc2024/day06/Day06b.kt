package aoc2024.day06

import aoc2024.common.CLEAR_SCREEN
import aoc2024.common.Input
import aoc2024.common.Matrix
import aoc2024.common.Point
import aoc2024.common.expecting
import aoc2024.day06.Day06b.Orientation.DOWN
import aoc2024.day06.Day06b.Orientation.LEFT
import aoc2024.day06.Day06b.Orientation.RIGHT
import aoc2024.day06.Day06b.Orientation.UP
import aoc2024.day06.Day06b.Tile.EMPTY
import aoc2024.day06.Day06b.Tile.GUARD_DOWN
import aoc2024.day06.Day06b.Tile.GUARD_LEFT
import aoc2024.day06.Day06b.Tile.GUARD_RIGHT
import aoc2024.day06.Day06b.Tile.GUARD_UP
import aoc2024.day06.Day06b.Tile.OBSTACLE_EXTRA
import aoc2024.day06.Day06b.Tile.PATH_HORIZONTAL
import aoc2024.day06.Day06b.Tile.PATH_JUNCTION
import aoc2024.day06.Day06b.Tile.PATH_VERTICAL
import kotlin.time.measureTimedValue

object Day06b {

    val exampleObstacles = listOf(
        Point(3, 6),
        Point(6, 7),
        Point(7, 7),
        Point(1, 8),
        Point(3, 8),
        Point(7, 9)
    )

    const val DRAW = false
    const val REDRAW_MS = 50L
    val DRAW_MAX_X: Int? = null
    val DRAW_MAX_Y: Int? = null

    enum class Tile(val char: Char) {

        EMPTY('.'),
        OBSTACLE('#'),
        OBSTACLE_EXTRA('O'),
        PATH_VERTICAL('|'),
        PATH_HORIZONTAL('-'),
        PATH_JUNCTION('+'),
        GUARD_UP('^'),
        GUARD_DOWN('v'),
        GUARD_LEFT('<'),
        GUARD_RIGHT('>');

        override fun toString() = char.toString()

        companion object {

            fun parse(char: Char): Tile {
                return entries.find {
                    it.char == char
                } ?: throw IllegalArgumentException("Invalid tile: $char")
            }

            fun guards() = setOf(GUARD_UP, GUARD_DOWN, GUARD_LEFT, GUARD_RIGHT)
            fun obstacles() = setOf(OBSTACLE, OBSTACLE_EXTRA)
            fun paths() = setOf(PATH_VERTICAL, PATH_HORIZONTAL, PATH_JUNCTION)
        }
    }

    data class Position(
        val point: Point,
        val orientation: Orientation
    )

    enum class Orientation(val char: Char) {
        UP('^'),
        DOWN('v'),
        LEFT('<'),
        RIGHT('>');

        fun turnRight(): Orientation = when (this) {
            UP -> RIGHT
            DOWN -> LEFT
            LEFT -> UP
            RIGHT -> DOWN
        }

        override fun toString() = char.toString()
    }

    class LoopException(position: Position) : RuntimeException("$position")

    fun findGuard(matrix: Matrix<Tile>): Position {
        for (tile in Tile.guards()) {
            val point = matrix.find(tile)
            if (point != null) {
                return Position(
                    point = point,
                    orientation = when (tile) {
                        GUARD_UP -> UP
                        GUARD_DOWN -> DOWN
                        GUARD_LEFT -> LEFT
                        GUARD_RIGHT -> RIGHT
                        else -> throw IllegalArgumentException("$tile")
                    }
                )
            }
        }
        throw IllegalStateException("Guard not found")
    }

    fun tick(matrix: Matrix<Tile>, guard: Position): Position? {
        val currentTile = matrix[guard.point]

        val updatedTile = when (guard.orientation) {
            UP, DOWN -> {
                when (currentTile) {
                    PATH_VERTICAL, EMPTY -> PATH_VERTICAL
                    PATH_HORIZONTAL, PATH_JUNCTION -> PATH_JUNCTION
                    else -> throw IllegalArgumentException("${guard.point} -> $currentTile")
                }
            }
            LEFT, RIGHT -> {
                when (currentTile) {
                    PATH_HORIZONTAL, EMPTY -> PATH_HORIZONTAL
                    PATH_VERTICAL, PATH_JUNCTION -> PATH_JUNCTION
                    else -> throw IllegalArgumentException("${guard.point} -> $currentTile")
                }
            }
        }

        val nextPosition = when (guard.orientation) {
            UP -> matrix.above(guard.point)
            DOWN -> matrix.bellow(guard.point)
            LEFT -> matrix.leftOf(guard.point)
            RIGHT -> matrix.rightOf(guard.point)
        }

        return when {
            nextPosition == null -> {
                matrix[guard.point] = updatedTile
                null
            }
            matrix[nextPosition] in (Tile.paths() + EMPTY) -> {
                matrix[guard.point] = updatedTile
                Position(nextPosition, guard.orientation)
            }
            matrix[nextPosition] in Tile.obstacles() -> {
                matrix[guard.point] = PATH_JUNCTION
                Position(guard.point, guard.orientation.turnRight())
            }
            else -> throw IllegalArgumentException("$nextPosition -> ${matrix[nextPosition]}")
        }
    }

    fun walk(matrix: Matrix<Tile>, start: Position) {
        matrix[start.point] = EMPTY
        val history = mutableSetOf(start)

        var position: Position? = start

        while (true) {
            if (DRAW) {
                print(CLEAR_SCREEN)
                println("Step ${history.size}\n")
                println(matrix.format(DRAW_MAX_X, DRAW_MAX_Y) { point, item ->
                    if (point == position?.point) position!!.orientation.char else item.char
                })
                Thread.sleep(REDRAW_MS)
            }
            if (position == null) {
                break
            }

            position = tick(matrix, position)
            if (position in history) {
                throw LoopException(position!!)
            }
            if (position != null) {
                history.add(position)
            }
        }
    }

    fun loops(matrix: Matrix<Tile>, guard: Position, extraObstacle: Point): Boolean {
        matrix[extraObstacle] = OBSTACLE_EXTRA
        return try {
            walk(matrix, start = guard)
            false
        } catch (e: LoopException) {
            true
        }
    }

    fun runWithoutExtraObstacles(matrix: Matrix<Tile>, guard: Position): List<Point> {
        walk(matrix, guard)
        return matrix.findAll { it in Tile.paths() }
    }

    val input = Input.day06
    val matrix = Matrix.parse(input, Tile::parse)
    val guard = findGuard(matrix)

    val possibleObstacles = runWithoutExtraObstacles(matrix.clone(), guard) - guard.point

    val result = measureTimedValue {
        possibleObstacles.count {
            loops(matrix.clone(), guard, it)
        }
    }
}

fun main() {
    println(Day06b.result.value.expecting(1618))
    println("duration: ${Day06b.result.duration}")
}
