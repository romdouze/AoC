package com.ngr.aoc.y2025.day6

import com.ngr.aoc.Day

class Day6 : Day<Pair<List<String>, Operation>, Long, Long>() {

    private val rawLines = mutableListOf<String>()

    override fun handleLine(lines: MutableList<Pair<List<String>, Operation>>, line: String) {
        if (line.contains("\\d+".toRegex())) {
            rawLines.add(line)
        } else {
            val indexedOps = line.withIndex()
                .filter { (_, ch) -> !ch.isWhitespace() }
            indexedOps.forEachIndexed { i, op ->
                val endColumn = if (i != indexedOps.lastIndex) indexedOps[i + 1].index - 1 else null
                lines.add(rawLines.map {
                    it.substring(
                        op.index,
                        endColumn ?: it.length
                    )
                } to Operation.fromChar(op.value))
            }
        }
    }

    override fun part1(lines: List<Pair<List<String>, Operation>>) =
        lines.sumOf { line ->
            line.first
                .map { it.trim().toLong() }
                .reduce { a, b -> line.second.compute(a, b) }
        }

    override fun part2(lines: List<Pair<List<String>, Operation>>) =
        lines.sumOf { line ->
            line.first
                .let { numbers ->
                    numbers[0].lastIndex.downTo(0)
                        .map { index ->
                            numbers.mapNotNull { it[index].takeIf { !it.isWhitespace() } }
                                .joinToString(separator = "")
                        }
                }.map { it.toLong() }
                .reduce { a, b -> line.second.compute(a, b) }
        }
}