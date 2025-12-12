package com.ngr.aoc.y2025.day12

import com.ngr.aoc.Day

class Day12 : Day<ChristmasPresents, Int, Int>() {
    override fun handleLine(lines: MutableList<ChristmasPresents>, line: String) {
        if (line.contains("x"))
            lines.add(line.split(": ").let {
                val size = it[0].split("x").map { it.toInt() }
                ChristmasPresents(
                    size[0],
                    size[1],
                    it[1].split(" ").map { it.toInt() }
                )
            })
    }

    override fun part1(lines: List<ChristmasPresents>) =
        lines.count { it.x * it.y > it.amounts.sum() * 7 } // Why make it complicated?

    override fun part2(lines: List<ChristmasPresents>): Int {
        TODO("Not yet implemented")
    }
}