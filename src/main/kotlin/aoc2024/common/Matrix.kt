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

    operator fun set(point: Point, value: T) {
        items[point.y][point.x] = value
    }

    fun neighbors(p: Point): List<Point> {
        return listOfNotNull(above(p), bellow(p), leftOf(p), rightOf(p))
    }

    fun above(p: Point): Point? = Point(p.x, p.y - 1).takeIf(::isInBounds)
    fun bellow(p: Point): Point? = Point(p.x, p.y + 1).takeIf(::isInBounds)
    fun leftOf(p: Point): Point? = Point(p.x - 1, p.y).takeIf(::isInBounds)
    fun rightOf(p: Point): Point? = Point(p.x + 1, p.y).takeIf(::isInBounds)

    private fun isInBounds(point: Point): Boolean {
        return (point.y in items.indices) && (point.x in items[point.y].indices)
    }

    fun find(value: T): Point? {
        for (y in items.indices) {
            for (x in items[y].indices) {
                if (items[y][x] == value) {
                    return Point(x, y)
                }
            }
        }
        return null
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
            return Matrix(
                items = lines.map { line ->
                    line.mapTo(mutableListOf()) { char ->
                        parseChar(char)
                    }
                }
            )
        }
    }

}
