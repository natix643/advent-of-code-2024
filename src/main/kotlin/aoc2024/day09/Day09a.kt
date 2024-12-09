package aoc2024.day09

import aoc2024.Input
import aoc2024.expecting

object Day09a {

    fun Disk.rearrange(): Disk {
        val result = toMutableList()

        var left = 0
        var right = result.lastIndex

//    println(result.prettyFormat())
        while (left < right) {
            if (result[left] != null) {
                left++
            } else if (result[right] == null) {
                right--
            } else {
                result[left] = result[right]
                result[right] = null
//                println(result.prettyFormat())
            }
        }
        return result
    }

    val input = Input.day09.first()

    val denseFormat = input.parseDenseFormat()
    val disk = denseFormat.expand()

    val result = disk.rearrange().checksum()
}

fun main() {
    println(Day09a.result.expecting(6291146824486))
}
