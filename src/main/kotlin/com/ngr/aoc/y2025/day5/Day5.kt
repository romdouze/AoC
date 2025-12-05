package com.ngr.aoc.y2025.day5

import com.ngr.aoc.Day

class Day5 : Day<Long, Int, Long>() {

    private val ranges = mutableSetOf<LongRange>()

    override fun handleLine(lines: MutableList<Long>, line: String) {
        if (line.isBlank()) return
        if (line.contains("-")) {
            ranges.add(line.split("-").let { it[0].toLong()..it[1].toLong() })
        } else {
            lines.add(line.toLong())
        }
    }

    override fun part1(lines: List<Long>) =
        lines.count { line -> ranges.any { it.contains(line) } }

    override fun part2(lines: List<Long>) =
        ranges.aggregate().sumOf { it.size }
}