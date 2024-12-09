package aoc2024.day09

import aoc2024.Input
import aoc2024.expecting

object Day09a {

    fun Disk.rearrange(): Disk {
        val result = toMutableList()

        var leftBound = 0
        var rightBound = result.lastIndex

//    println(result.prettyFormat())
        while (leftBound < rightBound) {
            if (result[leftBound] != null) {
                leftBound++
            } else if (result[rightBound] == null) {
                rightBound--
            } else {
                result[leftBound] = result[rightBound]
                result[rightBound] = null
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
