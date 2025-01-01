package com.ngr.aoc.y2024.day11

class StoneBlinker(private val initialStones: List<Long>) {

    private val blinkCache = mutableMapOf<Long, List<Long>>()

    fun blink(n: Int): List<Long> {
        var stones = initialStones
        repeat(n) {
            val newStones = mutableListOf<Long>()
            stones.forEach {
                when {
                    it == 0L -> newStones.add(1L)
                    it.hasEvenDigits -> newStones.addAll(it.split())
                    else -> newStones.add(it * 2024)
                }
            }
            stones = newStones
        }

        return stones
    }

    fun blinkBetter(n: Int): Long {
        return initialStones.sumOf { blink(it, 0, n) }
    }

    private fun blink(stone: Long, currentBlink: Int, blinks: Int): Long =
        if (currentBlink == blinks) {
            1L
        } else {
            if (blinkCache.containsKey(stone)) {
                exploreCache(stone, currentBlink, blinks)
            } else {
                when {
                    stone == 0L -> {
                        blinkCache[stone] = listOf(1L)
                        blink(1L, currentBlink + 1, blinks)
                    }

                    stone.hasEvenDigits -> stone.split()
                        .also { blinkCache[stone] = it }
                        .let {
                            blink(it[0], currentBlink + 1, blinks) +
                                    blink(it[1], currentBlink + 1, blinks)
                        }

                    else -> {
                        blinkCache[stone] = listOf(stone * 2024)
                        blink(stone * 2024, currentBlink + 1, blinks)
                    }
                }
            }
        }

    private fun exploreCache(stone: Long, currentBlink: Int, blinks: Int): Long {
        var b = currentBlink
        var stones = listOf(stone)
        val missing = mutableListOf<Pair<Long, Int>>()
        while (b < blinks) {
            stones = stones.flatMap { s ->
                blinkCache[s] ?: let {
                    missing.add(s to b)
                    emptyList()
                }
            }
            b++
        }
        return stones.size.toLong() + missing.sumOf { blink(it.first, it.second, blinks) }
    }

    private val Long.hasEvenDigits: Boolean
        get() = toString().length % 2 == 0

    private fun Long.split() =
        toString().let {
            it.chunked(it.length / 2)
        }.map { it.toLong() }
}