package com.ngr.aoc.y2024.day19

class TowelMatcher(
    private val towels: List<String>,
) {
    private companion object {
        private val patternMatches = mutableMapOf<String, MutableSet<List<String>>>()

        private fun addToCache(pattern: String, match: List<String>) {
            patternMatches.compute(pattern) { _, it ->
                it?.also {
                    it.add(match)
                } ?: mutableSetOf(match)
            }
        }
    }

    fun match(pattern: String): List<List<String>> {
        val matches = mutableListOf<List<String>>()
        findMatch(pattern, 0, emptyList(), matches)
        return matches
    }

    private fun findMatch(pattern: String, index: Int, currentMatch: List<String>, matches: MutableList<List<String>>) {
        addToCache(pattern.take(index), currentMatch)

        val partialPattern = pattern.drop(index)
        if (partialPattern.isEmpty()) {
            matches.add(currentMatch)
        } else {
            towels.filter {
                partialPattern.startsWith(it)
            }.forEach { match ->
                findMatch(pattern, index + match.length, currentMatch + match, matches)
            }
        }
    }
}