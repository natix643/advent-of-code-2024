package aoc2024.day10

import aoc2024.common.Input
import aoc2024.common.Matrix
import aoc2024.common.Point
import aoc2024.common.expecting

object Day10b {

    val example1 = """
        .....0.
        ..4321.
        ..5..2.
        ..6543.
        ..7..4.
        ..8765.
        ..9....
    """.trimIndent().lines()

    val example2 = """
        ..90..9
        ...1.98
        ...2..7
        6543456
        765.987
        876....
        987....
    """.trimIndent().lines()

    val example3 = """
        012345
        123456
        234567
        345678
        4.6789
        56789.
    """.trimIndent().lines()

    val example4 = Day10a.example5

    class Path(
        val last: Point,
        val previous: Path? = null
    ) {
        override fun toString(): String {
            val sb = StringBuilder("[")
            var path: Path? = this

            while (path != null) {
                sb.append(path.last)
                if (path.previous != null) {
                    sb.append(", ")
                }
                path = path.previous
            }
            return sb.append("]").toString()
        }
    }

    fun findPaths(matrix: Matrix<Int?>, start: Point): List<Path> {
        val queue = ArrayDeque<Path>().apply {
            add(Path(start))
        }
        val paths = mutableListOf<Path>()

        while (queue.isNotEmpty()) {
            // println(queue)
            val currentPath = queue.removeFirst()
            val lastPoint = currentPath.last

            if (matrix[lastPoint] == 9) {
                paths += currentPath
            }

            val nextPaths = matrix.neighbors(lastPoint).filter {
                matrix[it] == matrix[lastPoint]!! + 1
            }.map {
                Path(it, currentPath)
            }
            queue += nextPaths
        }
        return paths
    }

    val input = Input.day10

    val matrix = parseMatrix(input)
    val starts = matrix.findAll(0)

    val result = starts.sumOf {
        findPaths(matrix, it).size
    }
}

fun main() {
    println(Day10b.result.expecting(1326))
}
