package aoc2024.day19

import aoc2024.common.Input
import aoc2024.common.expecting

object Day19a {

    fun isPossible(
        design: String,
    ): Boolean {
        if (design == "") {
            return true
        }

        for (towel in towels) {
            val memoized = memo[memoKey(design = design, towel = towel)]
            if (memoized != null) {
                return memoized
            }

            if (design.startsWith(towel)) {
                val possible = isPossible(design.substring(towel.length))
                memo[memoKey(design = design, towel = towel)] = possible
                //println("design=$design, towel=$towel, memo=$memo")
                if (possible) {
                    return true
                }
            }
        }
        return false
    }

    fun memoKey(design: String, towel: String) = "$towel/$design"

    val input = Input.day19
    val towels = parseTowels(input)
    val designs = parseDesigns(input)

    val memo = mutableMapOf<String, Boolean>()

    val result = designs.count { isPossible(it) }
}

fun main() {
    println(Day19a.result.expecting(317))
}
