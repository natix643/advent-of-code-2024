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
    fun rows(): List<List<T>> = items

    fun columns(): List<List<T>> {
        return items.first().indices.map { x ->
            items.indices.map { y ->
                items[y][x]
            }
        }
    }

    operator fun get(point: Point): T? =
        items[point.y][point.x]

    operator fun set(point: Point, value: T) {
        items[point.y][point.x] = value
    }

    fun neighbors(point: Point): List<Point> {
        return listOfNotNull(above(point), bellow(point), leftOf(point), rightOf(point))
    }

    fun above(point: Point): Point? = Point(point.x, point.y - 1).takeIf(::isInBounds)
    fun bellow(point: Point): Point? = Point(point.x, point.y + 1).takeIf(::isInBounds)
    fun leftOf(point: Point): Point? = Point(point.x - 1, point.y).takeIf(::isInBounds)
    fun rightOf(point: Point): Point? = Point(point.x + 1, point.y).takeIf(::isInBounds)

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
        return findAll { it == value }
    }

    fun findAll(predicate: (T) -> Boolean): List<Point> {
        return items.indices.flatMap { y ->
            items[y].indices.mapNotNull { x ->
                if (predicate(items[y][x])) Point(x, y) else null
            }
        }
    }

    fun format(
        maxX: Int? = null,
        maxY: Int? = null,
        formatItem: ((point: Point, item: T) -> Char)? = null
    ): String {
        val builder = StringBuilder()

        items.forEachIndexed { y, row ->
            if (maxY == null || y <= maxY) {
                row.forEachIndexed { x, item ->
                    if (maxX == null || x <= maxX) {
                        if (formatItem != null) {
                            builder.append(formatItem(Point(x, y), item))
                        } else {
                            builder.append(item)
                        }
                    }
                }
                builder.appendLine()
            }
        }
        return builder.toString()
    }

    fun clone(): Matrix<T> {
        return Matrix(items.map { it.toMutableList() })
    }

    companion object {
        fun parse(lines: List<String>): Matrix<Char> {
            return parse(lines) { it }
        }

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
