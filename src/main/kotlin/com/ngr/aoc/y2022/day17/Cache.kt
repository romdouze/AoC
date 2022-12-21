package com.ngr.aoc.y2022.day17

class CacheKey(
    val shapeCursor: Int,
    val moveCursor: Int,
    tops: List<Long>
) {
    val topsNormalized = tops.min()
        .let { min ->
            tops.map { it - min }
        }

    override fun toString() =
        "s: $shapeCursor, m: $moveCursor, tops:[${topsNormalized.joinToString(", ")}]"

    override fun equals(other: Any?): Boolean {
        return if (other is CacheKey) {
            shapeCursor == other.shapeCursor && moveCursor == other.moveCursor && topsNormalized == other.topsNormalized
        } else {
            super.equals(other)
        }
    }

    override fun hashCode(): Int {
        var result = shapeCursor
        result = 31 * result + moveCursor
        result = 31 * result + topsNormalized.hashCode()
        return result
    }
}

class CachedResult(
    val shapeCursorDelta: Long,
    val moveCursorDelta: Long,
    val topsDelta: List<Long>
)