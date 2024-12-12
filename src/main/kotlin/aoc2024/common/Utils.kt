package aoc2024.common

fun <T> T.expecting(expected: Any): T {
    if (this != expected) {
        throw IllegalStateException("expected value to be $expected, but was $this")
    }
    return this
}
