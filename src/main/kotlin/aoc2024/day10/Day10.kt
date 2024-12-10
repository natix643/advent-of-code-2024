package aoc2024.day10

typealias Matrix = List<List<Int?>>

data class Point(
    val x: Int,
    val y: Int
) {
    override fun toString() = "($x, $y)"
}

fun List<String>.parseMatrix(): Matrix {
    return map { line ->
        line.map {
            if (it == '.') null else it.digitToInt()
        }
    }
}

fun Matrix.print() {
    for (row in this) {
        for (i in row) {
            print(i ?: '.')
        }
        println()
    }
}

operator fun Matrix.get(point: Point): Int? =
    this[point.y][point.x]

fun Matrix.findStarts(): List<Point> {
    return indices.flatMap { y ->
        this[y].indices.mapNotNull { x ->
            if (this[y][x] == 0) Point(x, y) else null
        }
    }
}

fun Matrix.neighbors(p: Point): List<Point> {
    return listOf(
        Point(p.x + 1, p.y),
        Point(p.x, p.y + 1),
        Point(p.x - 1, p.y),
        Point(p.x, p.y - 1)
    ).filter {
        (it.y in this.indices) && (it.x in this[it.y].indices)
    }
}
