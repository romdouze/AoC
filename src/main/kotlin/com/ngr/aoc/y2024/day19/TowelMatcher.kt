package com.ngr.aoc.y2024.day19

class TowelMatcher(
    private val towels: List<String>,
    private val existenceOnly: Boolean,
) {
    private val patternMatches = mutableMapOf<String, MutableSet<List<String>>>()
    private val impossiblePatterns = mutableSetOf<String>()

    fun match(pattern: String): List<List<String>> {
        System.err.println(pattern)
        val matches = mutableListOf<List<String>>()
        findMatch(pattern, 0, emptyList(), matches)
        System.err.println("${matches.size} matches found")
        System.err.println("${patternMatches.size} partial mapped")
        System.err.println("${impossiblePatterns.size} impossible found")
        return matches
    }

    private fun findMatch(
        pattern: String,
        index: Int,
        currentMatch: List<String>,
        matches: MutableList<List<String>>,
    ): List<List<String>> {
        addToCache(pattern.take(index), currentMatch)

        val partialPattern = pattern.drop(index)
        if (impossiblePatterns.contains(partialPattern)) {
            return emptyList()
        }

        return patternMatches[partialPattern]?.let { cachedMatches ->
            val allMatches = cachedMatches.map { currentMatch + it }
            matches.addAll(allMatches)
            allMatches
        } ?: run {
            val allMatches = mutableListOf<List<String>>()
            towels.filter {
                partialPattern.startsWith(it)
            }.forEach { match ->
                if (existenceOnly && allMatches.isEmpty() || !existenceOnly) {
                    allMatches.addAll(findMatch(pattern, index + match.length, currentMatch + match, matches))
                }
            }
            if (allMatches.isEmpty()) {
                impossiblePatterns.add(partialPattern)
            } else {
                addAllToCache(partialPattern, allMatches.map { it.drop(currentMatch.size) })
            }
            allMatches
        }
    }

    private fun addToCache(pattern: String, match: List<String>) {
        patternMatches.compute(pattern) { _, it ->
            it?.also {
                it.add(match)
            } ?: mutableSetOf(match)
        }
    }

    private fun addAllToCache(pattern: String, matches: List<List<String>>) {
        patternMatches.compute(pattern) { _, it ->
            it?.also {
                it.addAll(matches)
            } ?: matches.toMutableSet()
        }
    }
}