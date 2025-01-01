package com.ngr.aoc.y2024.day11

import com.ngr.aoc.Day

class Day11 : Day<Long, Long, Long>() {
    override fun handleLine(lines: MutableList<Long>, line: String) {
        lines.addAll(line.split(" ").map { it.toLong() })
    }

    override fun part1(lines: List<Long>) =
        StoneBlinker(lines).blink(25)

    override fun part2(lines: List<Long>) =
        StoneBlinker(lines).blink(75)
}