package aoc2024.day09

const val example = "2333133121414131402"

typealias Disk = List<Int?>
typealias DenseFormat = List<Int>

fun String.parseDenseFormat(): DenseFormat {
    return toCharArray().map { it.digitToInt() }
}

fun DenseFormat.expand(): Disk {
    val result = mutableListOf<Int?>()

    var isFile = true
    var id = 0

    for (blockCount in this) {
        for (i in 1..blockCount) {
            result += if (isFile) id else null
        }
        if (isFile) {
            id++
        }
        isFile = !isFile
    }
    return result
}

fun Disk.checksum(): Long {
    return mapIndexed { index, i ->
        index.toLong() * (i ?: 0)
    }.sum()
}

fun Disk.prettyFormat(): String {
    val s = StringBuilder()
    for (block in this) {
        s.append(block ?: '.')
    }
    return s.toString()
}
