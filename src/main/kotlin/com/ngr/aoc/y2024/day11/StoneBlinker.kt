package com.ngr.aoc.y2024.day11

class StoneBlinker(private val initialStones: List<Long>) {

    fun blink(n: Int): Long {
        var stones = initialStones.fold(mutableMapOf<Long, Long>()) { stones, stone ->
            stones.addNb(stone, 1)
            stones
        }
        repeat(n) {
            val newStones = mutableMapOf<Long, Long>()
            stones.forEach {
                val stone = it.key
                val nb = it.value
                when {
                    stone == 0L -> newStones.addNb(1, nb)
                    stone.hasEvenDigits -> stone.split().also {
                        newStones.addNb(it[0], nb)
                        newStones.addNb(it[1], nb)
                    }

                    else -> newStones.addNb(stone * 2024, nb)
                }
            }
            stones = newStones
        }

        return stones.values.sumOf { it }
    }

    private val Long.hasEvenDigits: Boolean
        get() = toString().length % 2 == 0

    private fun Long.split() =
        toString().let {
            it.chunked(it.length / 2)
        }.map { it.toLong() }

    private fun MutableMap<Long, Long>.addNb(stone: Long, nb: Long) {
        this[stone] = this[stone]?.plus(nb) ?: nb
    }
}