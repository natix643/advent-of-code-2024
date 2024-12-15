package aoc2024.day06

import aoc2024.common.CLEAR_SCREEN
import aoc2024.common.Matrix
import aoc2024.common.Point
import aoc2024.day06.Tile.EMPTY
import aoc2024.day06.Tile.GUARD_DOWN
import aoc2024.day06.Tile.GUARD_LEFT
import aoc2024.day06.Tile.GUARD_RIGHT
import aoc2024.day06.Tile.GUARD_UP
import aoc2024.day06.Tile.OBSTACLE
import aoc2024.day06.Tile.VISITED

val example = """
    ....#.....
    .........#
    ..........
    ..#.......
    .......#..
    ..........
    .#..^.....
    ........#.
    #.........
    ......#...
""".trimIndent().lines()

enum class Tile(val char: Char) {

    EMPTY('.'),
    OBSTACLE('#'),
    VISITED('X'),
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

        fun guards() = listOf(GUARD_UP, GUARD_DOWN, GUARD_LEFT, GUARD_RIGHT)
    }
}

fun Matrix<Tile>.findGuard(): Point? {
    for (tile in Tile.guards()) {
        val point = find(tile)
        if (point != null) {
            return point
        }
    }
    return null
}

fun turnRight(guard: Tile): Tile = when (guard) {
    GUARD_UP -> GUARD_RIGHT
    GUARD_DOWN -> GUARD_LEFT
    GUARD_LEFT -> GUARD_UP
    GUARD_RIGHT -> GUARD_DOWN
    else -> throw IllegalArgumentException("$guard")
}

fun tick(matrix: Matrix<Tile>): Boolean {
    val guardPosition = matrix.findGuard()
        ?: return false
    val guardTile = matrix[guardPosition]

    val nextPosition = when (guardTile) {
        GUARD_UP -> matrix.above(guardPosition)
        GUARD_DOWN -> matrix.bellow(guardPosition)
        GUARD_LEFT -> matrix.leftOf(guardPosition)
        GUARD_RIGHT -> matrix.rightOf(guardPosition)
        else -> throw IllegalArgumentException("$guardPosition -> $guardTile")
    }

    if (nextPosition == null) {
        matrix[guardPosition] = VISITED
        return true
    }

    when (matrix[nextPosition]) {
        EMPTY, VISITED -> {
            matrix[nextPosition] = guardTile
            matrix[guardPosition] = VISITED
        }
        OBSTACLE -> {
            matrix[guardPosition] = turnRight(guardTile)
        }
        else -> throw IllegalArgumentException("$nextPosition -> ${matrix[nextPosition]}")
    }

    return true
}

fun walk(matrix: Matrix<Tile>, draw: Boolean, redrawMs: Long) {
    var step = 0
    var walking = true

    while (walking) {
        if (draw) {
            print(CLEAR_SCREEN)
            println("Step $step\n")
            println(matrix.format())

            Thread.sleep(redrawMs)
        }
        walking = tick(matrix)
        step++
    }
}
