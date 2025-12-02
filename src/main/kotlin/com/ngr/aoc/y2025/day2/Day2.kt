package com.ngr.aoc.y2025.day2

import com.ngr.aoc.Day

class Day2 : Day<LongRange, Long, Long>() {
    override fun handleLine(lines: MutableList<LongRange>, line: String) {
        lines.addAll(
            line.split(",")
                .map {
                    it.split("-")
                        .let {
                            it[0].toLong()..it[1].toLong()
                        }
                }
        )
    }

    override fun part1(lines: List<LongRange>) =
        lines.sumOf { range ->
            range.map { it.toString() }
                .filter { isRepeating(it, 2) }
                .sumOf { it.toLong() }
        }

    override fun part2(lines: List<LongRange>) =
        lines.sumOf { range ->
            range.map { it.toString() }
                .filter {
                    (2..it.length).any { n -> isRepeating(it, n) }
                }.sumOf { it.toLong() }
        }

    private fun isRepeating(id: String, n: Int) =
        id.length % n == 0 && id.chunked(id.length / n).zipWithNext().all { (a, b) -> a == b }
}