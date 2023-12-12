package com.ngr.aoc.y2023.day12

import com.ngr.aoc.Day

class Day12 : Day<SpringRow, Int, Int>() {
    override fun handleLine(lines: MutableList<SpringRow>, line: String) {
        lines.add(
            line.split(" ").let {
                SpringRow(
                    it[0],
                    it[1].split(",").map { it.toInt() }
                )
            }
        )
    }

    override fun part1(lines: List<SpringRow>) =
        lines.sumOf { it.enumerate() }

    override fun part2(lines: List<SpringRow>): Int {
        TODO("Not yet implemented")
    }
}