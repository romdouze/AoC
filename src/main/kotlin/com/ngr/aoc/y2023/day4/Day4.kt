package com.ngr.aoc.y2023.day4

import com.ngr.aoc.Day

class Day4 : Day<Scratchcard, Int, Int>() {
    override fun handleLine(lines: MutableList<Scratchcard>, line: String) {
        lines.add(Scratchcard.fromString(line))
    }

    override fun part1(lines: List<Scratchcard>): Int =
        lines.sumOf {
            it.matching
                .takeIf { it.isNotEmpty() }
                ?.let { Math.pow(2.0, it.size.toDouble() - 1) }
                ?: 0.0
        }.toInt()

    override fun part2(lines: List<Scratchcard>): Int {
        val copies = mutableMapOf<Int, Int>()

        lines.forEach { card ->
            (card.id + 1 until card.id + card.matching.size + 1)
                .forEach { copies[it] = (copies[it] ?: 0) + (copies[card.id] ?: 0) + 1 }
        }

        return lines.size + copies.values.sum()
    }
}