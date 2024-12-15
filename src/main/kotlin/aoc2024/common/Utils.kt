package aoc2024.common

const val CLEAR_SCREEN = "\u001b[H\u001b[2J"

fun <T> T.expecting(expected: Any): T {
    if (this != expected) {
        throw IllegalStateException("expected value to be $expected, but was $this")
    }
    return this
}
