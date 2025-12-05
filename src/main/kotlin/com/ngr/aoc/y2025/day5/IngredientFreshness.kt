package com.ngr.aoc.y2025.day5

fun Set<LongRange>.aggregate(): Set<LongRange> {
    val aggregated = mutableSetOf<LongRange>()

    forEach { range ->
        var toAdd = range
        val toRemove = mutableSetOf<LongRange>()

        if (aggregated.none { it.contains(toAdd) }) {
            aggregated.firstOrNull { it.contains(toAdd.first) }
                ?.also {
                    toRemove.add(it)
                    toAdd = it.first..toAdd.last
                }
            aggregated.firstOrNull { it.contains(toAdd.last) }
                ?.also {
                    toRemove.add(it)
                    toAdd = toAdd.first..it.last
                }
            toRemove.addAll(aggregated.filter { toAdd.contains(it) })
            aggregated.removeAll(toRemove)
            aggregated.add(toAdd)
        }
    }

    return aggregated
}

val LongRange.size: Long
    get() = last - first + 1

fun LongRange.contains(other: LongRange) =
    contains(other.first) && contains(other.last)