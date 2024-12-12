package aoc2024.day11

import aoc2024.Input
import aoc2024.expecting

object Day11a {

    fun print(stones: List<Long>) {
        println(stones.joinToString(" "))
    }

    fun blink(stones: MutableList<Long>, times: Int) {
        // println(stones.size)
        repeat(times) { i ->
            val iterator = stones.listIterator()
            while (iterator.hasNext()) {
                val stone = iterator.next()
                when {
                    stone == 0L -> {
                        iterator.set(1)
                    }
                    stone.digits() % 2 == 0 -> {
                        val (left, right) = stone.split()
                        iterator.remove()
                        iterator.add(left)
                        iterator.add(right)
                    }
                    else -> {
                        iterator.set(stone * 2024)
                    }
                }
            }
            // println("$i -> ${stones.size}")
        }
    }

    val input = Input.day11.first()
    val stones = parseStones(input)

    val result = run {
        blink(stones, 25)
        stones.size
    }
}

fun main() {
    println(Day11a.result.expecting(209412))
}
