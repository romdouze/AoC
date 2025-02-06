package com.ngr.aoc.y2024.day22

import com.ngr.aoc.Day

class Day22 : Day<Long, Long, Long>() {
    override fun handleLine(lines: MutableList<Long>, line: String) {
        lines.add(line.toLong())
    }

    override fun part1(lines: List<Long>) =
        lines.sumOf { PseudoRandomer(it).generateNthSecret(2000) }

    override fun part2(lines: List<Long>): Long {
        TODO("Not yet implemented")
    }
}