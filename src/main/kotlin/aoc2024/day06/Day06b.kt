package aoc2024.day06

import aoc2024.common.CLEAR_SCREEN
import aoc2024.common.Matrix
import aoc2024.common.Point
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

object Day06b {

    const val DRAW = true
    const val REDRAW_MS = 100L

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

    data class Guard(
        val position: Point,
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

    class LoopException(point: Point) : RuntimeException("$point")

    fun findGuard(matrix: Matrix<Tile>): Guard {
        for (tile in Tile.guards()) {
            val point = matrix.find(tile)
            if (point != null) {
                return Guard(
                    position = point,
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

    fun tick(matrix: Matrix<Tile>, guard: Guard): Guard? {
        val currentTile = matrix[guard.position]

        val updatedTile = when (guard.orientation) {
            UP, DOWN -> {
                when (currentTile) {
                    EMPTY -> PATH_VERTICAL
                    PATH_HORIZONTAL, PATH_JUNCTION -> PATH_JUNCTION
                    else -> throw LoopException(guard.position)
                }
            }
            LEFT, RIGHT -> {
                when (currentTile) {
                    EMPTY -> PATH_HORIZONTAL
                    PATH_VERTICAL, PATH_JUNCTION -> PATH_JUNCTION
                    else -> throw LoopException(guard.position)
                }
            }
        }

        val nextPosition = when (guard.orientation) {
            UP -> matrix.above(guard.position)
            DOWN -> matrix.bellow(guard.position)
            LEFT -> matrix.leftOf(guard.position)
            RIGHT -> matrix.rightOf(guard.position)
        }

        when {
            nextPosition == null -> {
                matrix[guard.position] = updatedTile
                return null
            }
            matrix[nextPosition] in (Tile.paths() + EMPTY) -> {
                matrix[guard.position] = updatedTile
                return Guard(nextPosition, guard.orientation)
            }
            matrix[nextPosition] in Tile.obstacles() -> {
                matrix[guard.position] = PATH_JUNCTION
                return Guard(guard.position, guard.orientation.turnRight())
            }
            else -> throw IllegalArgumentException("$nextPosition -> ${matrix[nextPosition]}")
        }
    }

    fun walk(matrix: Matrix<Tile>, start: Guard) {
        var step = 0
        var guard: Guard? = start
        matrix[guard!!.position] = EMPTY

        while (true) {
            if (DRAW) {
                print(CLEAR_SCREEN)
                println("Step $step\n")
                println(matrix.format { point, item ->
                    if (point == guard?.position) guard!!.orientation.char else item.char
                })
                Thread.sleep(REDRAW_MS)
            }

            if (guard == null) {
                break
            }
            guard = tick(matrix, guard)
            step++
        }
    }

    fun loops(matrix: Matrix<Tile>, guard: Guard, extraObstacle: Point): Boolean {
        matrix[extraObstacle] = OBSTACLE_EXTRA
        return try {
            walk(matrix, start = guard)
            false
        } catch (e: LoopException) {
            println(e)
            true
        }
    }

    val input = example
    val matrix = Matrix.parse(input, Tile::parse)

    val guard = findGuard(matrix)
    val extraObstacle = Point(7, 7)
    val result = loops(matrix, guard, extraObstacle)

}

fun main() {
    println(Day06b.result)
}
