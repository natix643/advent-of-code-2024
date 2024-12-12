package aoc2024.common

data class Point(
    val x: Int,
    val y: Int
) {
    override fun toString() = "($x, $y)"
}

class Matrix<T>(
    private val items: List<MutableList<T>>
) {
    operator fun get(point: Point): T? =
        items[point.y][point.x]

    fun neighbors(p: Point): List<Point> {
        return listOf(
            Point(p.x + 1, p.y),
            Point(p.x, p.y + 1),
            Point(p.x - 1, p.y),
            Point(p.x, p.y - 1)
        ).filter {
            (it.y in items.indices) && (it.x in items[it.y].indices)
        }
    }

    fun findAll(value: T): List<Point> {
        return items.indices.flatMap { y ->
            items[y].indices.mapNotNull { x ->
                if (items[y][x] == value) Point(x, y) else null
            }
        }
    }

    fun format(formatItem: ((T) -> Char)? = null): String {
        val builder = StringBuilder()

        for (row in items) {
            for (item in row) {
                if (formatItem != null) {
                    builder.append(formatItem(item))
                } else {
                    builder.append(item)
                }
            }
            builder.appendLine()
        }
        return builder.toString()
    }

    companion object {
        fun <T> parse(lines: List<String>, parseChar: (Char) -> T): Matrix<T> {
            return Matrix(items = lines.map { line ->
                line.mapTo(mutableListOf()) { char ->
                    parseChar(char)
                }
            })
        }
    }
}
