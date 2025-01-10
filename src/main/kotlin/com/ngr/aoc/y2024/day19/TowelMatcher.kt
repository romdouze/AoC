package com.ngr.aoc.y2024.day19

class TowelMatcher(
    private val towels: List<String>,
) {
    private companion object {
        private val patternMatches = mutableMapOf<String, MutableList<String>>()
    }

    fun match(pattern: String) =
        findMatch(pattern, emptyList())

    private fun findMatch(pattern: String, match: List<String>): List<String> {
        if (pattern.isEmpty()) {
            return match
        }
        return patternMatches[pattern] ?: towels.filter {
            pattern.startsWith(it)
        }.flatMap {
            findMatch(pattern.substring(it.length), match + it)
                .also {
                    patternMatches.computeIfAbsent(pattern) { mutableListOf() }
                        .addAll(it)
                }
        }
    }
}