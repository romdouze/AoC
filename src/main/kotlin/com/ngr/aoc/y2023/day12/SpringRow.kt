package com.ngr.aoc.y2023.day12

data class SpringRow(
    val record: String,
    val groups: List<Int>,
) {

    private val cache = mutableMapOf<Pair<Int, Int>, Int>()

    fun enumerate() =
        enumerate(record.length - 1, groups.size - 1)

    private fun enumerate(i: Int, j: Int): Int {
        if (i < 0 || j < 0) {
            return 0
        }
        if (i == 0 && j == 0) {
            return 1
        }
        if (cache.containsKey(i to j)) {
            return cache[i to j]!!
        }

        cache[i to j] = 0
        val whiteCount = enumerate(i - 1, j)
        val blackCount = enumerate(i - groups[j] - extra(j), j - 1)

        if (whiteCount > 0 && record[i] != '#') {
            cache[i to j] = cache[i to j]!! + whiteCount
        }
        if (blackCount > 0 && canPlace(i, j)) {
            cache[i to j] = cache[i to j]!! + blackCount
        }

        return cache[i to j]!!
    }

    private fun canPlace(i: Int, j: Int): Boolean {
        (i - groups[j] + 1..i).forEach {
            if (record[it] == '.') return false
        }
        return !(extra(j) == 1 && record[i - groups[j]] == '#')
    }

    private fun extra(j: Int) = if (j > 0) 1 else 0
}