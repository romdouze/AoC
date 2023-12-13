package com.ngr.aoc.y2023.day12

data class SpringRow(
    val record: String,
    val groups: List<Int>,
) {

    private companion object {
        private val combinationCache = mutableMapOf<Pair<Int, Int>, List<List<Int>>>()

        private fun enumerate(indices: List<Int>, k: Int): List<List<Int>> {
            return if (k == 1) {
                (0 until indices.size - k + 1).map { i ->
                    listOf(indices[i])
                }
            } else {
                (0 until indices.size - k + 1).flatMap { i ->
                    enumerate(indices.drop(i + 1), k - 1).map {
                        listOf(indices[i]).plus(it)
                    }
                }
            }
        }

        private fun combinations(n: Int, k: Int) =
            combinationCache.computeIfAbsent(n to k) {
                enumerate((0 until n).toList(), k)
            }
    }

    fun enumerate(): Int {
        val combinations = combinations(record.length - groups.sum() + 1, groups.size)
        return combinations.count { canBePlaced(it) }
    }

    private fun canBePlaced(combination: List<Int>): Boolean {
        val line = (0 until record.length - groups.sum() + 1)
            .mapIndexed { index, _ ->
                val blockIndex = combination.indexOf(index)
                if (blockIndex != -1) {
                    val blockSize = groups[blockIndex]!!
                    "#".repeat(blockSize) + "."
                } else {
                    "."
                }
            }.joinToString("") { it }
            .dropLast(1)

        return line.indices.none { line[it] == '.' && record[it] == '#' || line[it] == '#' && record[it] == '.' }
    }
}