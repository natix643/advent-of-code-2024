package aoc2024.day11

import aoc2024.common.Input
import aoc2024.common.expecting
import java.util.TreeMap

object Day11b {

    fun List<Long>.histogram(): Map<Long, Long> {
        return groupingBy { it }
            .eachCount()
            .mapValues { (_, count) -> count.toLong() }
    }

    fun MutableMap<Long, Long>.incAt(k: Long, count: Long) {
        this[k] = (this[k] ?: 0) + count
    }

    fun blink(histogram: Map<Long, Long>, times: Int): Map<Long, Long> {
        lateinit var target: MutableMap<Long, Long>
        var source = histogram
        // println(source)

        repeat(times) {
            target = TreeMap()
            source.forEach { (stone, count) ->
                when {
                    stone == 0L -> {
                        target.incAt(1, count)
                    }
                    stone.digits() % 2 == 0 -> {
                        val (left, right) = stone.split()
                        target.incAt(left, count)
                        target.incAt(right, count)
                    }
                    else -> {
                        target.incAt(stone * 2024, count)
                    }
                }
            }
            source = target
            // println(source)
        }
        return target
    }

    val input = Input.day11.first()

    val stones = parseStones(input)
    val histogram = stones.histogram()

    val result = blink(histogram, 75)
        .map { (_, count) -> count }
        .sum()
}

fun main() {
    println(Day11b.result.expecting(248967696501656))
}
