package com.ngr.aoc.y2024.day22

class PseudoRandomer(
    private val initialSecret: Long,
) {
    fun generateNthSecret(n: Int): Long {
        var currentSecret = initialSecret
        repeat(n) {
            currentSecret = generateNextSecret(currentSecret)
        }

        return currentSecret
    }

    private fun generateNextSecret(secret: Long): Long {
        var nextSecret = secret.mixWith(secret * 64).prune()
        nextSecret = nextSecret.mixWith(nextSecret / 32).prune()
        nextSecret = nextSecret.mixWith(nextSecret * 2048).prune()

        return nextSecret
    }

    private fun Long.mixWith(other: Long) =
        this.xor(other)

    private fun Long.prune() =
        this % 16777216
}