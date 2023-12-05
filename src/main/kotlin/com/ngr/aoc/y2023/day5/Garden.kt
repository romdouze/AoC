package com.ngr.aoc.y2023.day5

import kotlin.math.min

data class Converter(
    val source: Category,
    val destination: Category,
    val map: List<Pair<LongRange, LongRange>>
) {
    fun convert(source: Long) =
        matchingRange(source)
            ?.convert(source)
            ?: source

    fun convert(sourceRange: Pair<Long, Long>): List<Pair<Long, Long>> {
        var chunkedRange = mutableListOf<Pair<Long, Long>>()
        var lower = sourceRange.first
        var width = sourceRange.second
        do {
            val range = matchingRange(lower)
                ?: map.filter { (lower..lower + width).contains(it.first.first) }
                    .minByOrNull { it.first.first }
                    ?.let { ((lower until it.first.first) to (lower until it.first.first)) }
                ?: (lower..lower + width to lower..lower + width)

            val matchingLower = range.convert(lower)
            val matchingWidth = min(width, range.first.last - lower + 1)

            chunkedRange.add(matchingLower to matchingWidth)

            lower = min(lower + width + 1, range.first.last + 1)
            width = width - matchingWidth
        } while (lower <= sourceRange.first + sourceRange.second)

        return chunkedRange
    }

    private fun matchingRange(source: Long) =
        map.firstOrNull { it.first.contains(source) }

    private fun Pair<LongRange, LongRange>.convert(source: Long) =
        source + (second.first - first.first)
}

enum class Category {
    SEED, SOIL, FERTILIZER, WATER, LIGHT, TEMPERATURE, HUMIDITY, LOCATION;

    companion object {
        fun fromString(line: String) =
            values().first { it.name == line.uppercase() }
    }
}