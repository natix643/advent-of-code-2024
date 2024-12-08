package aoc2024.day05

import java.util.*

val example = """
    47|53
    97|13
    97|61
    97|47
    75|29
    61|13
    75|53
    29|13
    97|29
    53|29
    61|53
    97|53
    61|29
    47|13
    75|47
    97|75
    47|61
    75|61
    47|29
    75|13
    53|13

    75,47,61,53,29
    97,61,53,29,13
    75,29,13
    75,97,47,61,53
    61,13,29
    97,13,75,29,47
""".trimIndent().lines()

typealias Rules = SortedMap<Int, SortedSet<Int>>
typealias Update = List<Int>

fun parseRules(lines: List<String>): Rules {
    return lines
        .takeWhile { it.isNotEmpty() }
        .map { line ->
            val (x, y) = line.split("|").map { it.toInt() }
            x to y
        }
        .groupBy({ it.first }, { it.second })
        .mapValues { (_, values) -> values.toSortedSet() }
        .toSortedMap()
}

fun parseUpdates(lines: List<String>): List<Update> {
    return lines
        .dropWhile { it.isNotEmpty() }
        .drop(1)
        .map { line ->
            line.split(",").map { it.toInt() }
        }
}

fun sort(update: Update, rules: Rules): Update {
    return update.sortedWith { x, y ->
        if (rules[x]?.contains(y) == true) -1 else 1
    }
}

fun Update.middle(): Int = this[lastIndex / 2]
