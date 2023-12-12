package com.ngr.aoc.y2023.day12

data class SpringRow(
    val record: String,
    val groups: List<Int>,
) {

    private val cache = mutableMapOf<Pair<Int, Int>, Long>()

    fun enumerate() =
        enumerate(0, 0)

    private fun enumerate(i: Int, j: Int): Long {
        if (j > groups.lastIndex) {
            return if (i < record.length && record.substring(i).any { it == '#' }) 0 else 1
        }

        val nextI = (i until record.length).firstOrNull { record[it] in arrayOf('#', '?') }

        if (nextI == null) {
            return 0
        }

        if (cache.containsKey(nextI to j)) {
            return cache[nextI to j]!!
        }

        var count = 0L
        if (canPlace(nextI, j)) {
            count += enumerate(nextI + groups[j] + 1, j + 1)
        }
        if (record[nextI] == '?') {
            count += enumerate(nextI + 1, j)
        }

        cache[nextI to j] = count

        return cache[nextI to j]!!
    }

    private fun canPlace(i: Int, j: Int) =
        i + groups.subList(j, groups.size).sum() + (groups.size - j - 1) <= record.length &&
                record.substring(i, i + groups[j]).none { it == '.' } &&
                (i + groups[j] >= record.length || record[i + groups[j]] != '#')
}