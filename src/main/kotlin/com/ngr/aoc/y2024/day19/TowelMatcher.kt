package com.ngr.aoc.y2024.day19

class TowelMatcher(
    private val towels: List<String>,
) {
    private val patternMatchCounts = mutableMapOf<String, Long>()

    fun matchCount(pattern: String) =
        findMatchCount(pattern)

    private fun findMatchCount(
        pattern: String
    ): Long {
        if (pattern.isEmpty()) {
            return 1
        }

        return patternMatchCounts[pattern] ?: towels
            .filter { pattern.startsWith(it) }
            .sumOf {
                findMatchCount(pattern.substring(it.length))
            }.also {
                patternMatchCounts[pattern] = it
            }
    }

}