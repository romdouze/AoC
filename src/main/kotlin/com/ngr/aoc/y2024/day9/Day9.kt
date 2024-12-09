package com.ngr.aoc.y2024.day9

import com.ngr.aoc.Day

class Day9 : Day<Int, Long, Long>() {

    private lateinit var blocksAndFree: Pair<List<Int>, List<Int>>

    override fun handleLine(
        lines: MutableList<Int>,
        line: String
    ) {
        blocksAndFree = line.mapIndexed { index, ch -> ch.digitToInt() to index }
            .partition { it.second % 2 == 0 }
            .let { it.first.map { it.first } to it.second.map { it.first } }

        lines.addAll(line.map { it.digitToInt() })
    }

    override fun part1(lines: List<Int>) =
        FileSystem.singleBlockCompressionChecksum(blocksAndFree.first, blocksAndFree.second)

    override fun part2(lines: List<Int>) =
        FileSystem.wholeFileCompressionChecksum(blocksAndFree.first, blocksAndFree.second)
}