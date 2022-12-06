package com.ngr.aoc.y2022.day6

import com.ngr.aoc.y2022.Day

class Day6 : Day<String, Int, Int>() {
    override fun handleLine(lines: MutableList<String>, line: String) {
        lines.add(line)
    }

    override fun part1(lines: List<String>) =
        lines.first()
            .findMarker(4)

    override fun part2(lines: List<String>) =
        lines.first()
            .findMarker(14)

    private fun String.findMarker(size: Int) =
        windowed(size)
            .indexOfFirst { it.toSet().size == size } + size
}