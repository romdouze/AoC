package com.ngr.aoc.y2022.day25

import kotlin.math.pow


object Decoder {
    private val snafuToDigit = mapOf(
        '2' to 2L,
        '1' to 1L,
        '0' to 0L,
        '-' to -1L,
        '=' to -2L,
    )

    private val digitToSnafu = snafuToDigit.entries
        .associate {
            it.value to it.key
        }

    private val rangeCache = mutableMapOf<Int, LongRange>()

    fun decode(value: String): Long {
        val digits = value.map { snafuToDigit[it]!! }

        return digits.reversed()
            .reduceIndexed { index, acc, digit ->
                acc + digit * 5.0.pow(index.toDouble()).toLong()
            }
    }

    fun encode(value: Long): String {
        var digits = 1
        while (!rangeFor(digits).contains(value)) {
            digits++
        }

        val snafu = LongArray(digits)
        var remainder = value

        snafu.indices.reversed().forEach { i ->
            if (i == 0) {
                snafu[i] = remainder
            } else {
                snafu[i] = digitToSnafu.keys.sorted().first {
                    rangeFor(i)
                        .contains(remainder - it * 5.0.pow(i.toDouble()).toLong())
                }
                remainder -= snafu[i] * 5.0.pow(i.toDouble()).toLong()
            }
        }

        return snafu.reversed().joinToString("") { digitToSnafu[it]!!.toString() }
    }

    private fun rangeFor(digits: Int) =
        rangeCache.computeIfAbsent(digits) {
            decode("".padEnd(digits, digitToSnafu[-2]!!))..decode("".padEnd(digits, digitToSnafu[2]!!))
        }
}