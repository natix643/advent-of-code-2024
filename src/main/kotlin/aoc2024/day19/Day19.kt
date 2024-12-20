package aoc2024.day19

val example = """
    r, wr, b, g, bwu, rb, gb, br

    brwrr
    bggr
    gbbr
    rrbgbr
    ubwu
    bwurrg
    brgr
    bbrgwb
""".trimIndent().lines()

val example2 = """
    rrg, rr, gu

    rrgu
""".trimIndent().lines()

fun parseTowels(input: List<String>): List<String> {
    return input.first()
        .split(", ")
        .sortedByDescending { it.length }
}

fun parseDesigns(input: List<String>): List<String> {
    return input.drop(2)
}
