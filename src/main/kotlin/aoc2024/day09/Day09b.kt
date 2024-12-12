package aoc2024.day09

import aoc2024.common.Input
import aoc2024.common.expecting
import java.util.Collections.nCopies

object Day09b {

    fun defragment(disk: Disk): Disk {
        val result = disk.toMutableList()

        var rightBound = result.lastIndex

//        println(result.prettyFormat())
        while (rightBound > 0) {
            if (result[rightBound] == null) {
                rightBound--
            } else {
                val fileStart = findFileStart(result, rightBound)
                val fileSize = rightBound - fileStart + 1
                val spaceStart = findSpaceStart(result, endBeforeIndex = fileStart, size = fileSize)

                if (spaceStart != -1) {
                    for (i in spaceStart..<(spaceStart + fileSize)) {
                        result[i] = result[rightBound]
                    }
                    for (i in fileStart..rightBound) {
                        result[i] = null
                    }
//                    println(result.prettyFormat())
                }
                rightBound -= fileSize
            }
        }

        return result
    }

    fun findFileStart(disk: Disk, endIndex: Int): Int {
        val id = disk[endIndex]

        for (i in endIndex.downTo(0)) {
            if (disk[i] != id) {
                return i + 1
            }
        }
        return 0
    }

    fun findSpaceStart(disk: Disk, endBeforeIndex: Int, size: Int): Int {
        val space = nCopies(size, null)

        for (i in 0..(endBeforeIndex - size)) {
            if (disk[i] == null) {
                if (disk.subList(i, i + size) == space) {
                    return i
                }
            }
        }
        return -1
    }

    val input = Input.day09.first()

    val denseFormat = input.parseDenseFormat()
    val disk = denseFormat.expand()

    val result = defragment(disk).checksum()
}

fun main() {
    println(Day09b.result.expecting(6307279963620))
}
