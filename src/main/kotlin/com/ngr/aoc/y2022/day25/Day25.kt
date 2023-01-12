package com.ngr.aoc.y2022.day25

import com.ngr.aoc.y2022.Day

class Day25 : Day<String, String, String>() {
    override fun handleLine(lines: MutableList<String>, line: String) {
        lines.add(line)
    }

    override fun part1(lines: List<String>) =
        Decoder.encode(
            lines.sumOf { Decoder.decode(it) }
        )

    override fun part2(lines: List<String>): String {
        TODO("Not yet implemented")
    }
}