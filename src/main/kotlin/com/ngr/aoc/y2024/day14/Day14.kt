package com.ngr.aoc.y2024.day14

import com.ngr.aoc.Day

class Day14 : Day<Robot, Int, Int>() {
    override fun handleLine(lines: MutableList<Robot>, line: String) {
        lines.add(Robot.fromString(line))
    }

    override fun part1(lines: List<Robot>) =
        Floor(lines).apply { moveAll(100) }
            .safetyFactor

    override fun part2(lines: List<Robot>): Int {
        val floor = Floor(lines)
        var n = 0
        var minSafety = Int.MAX_VALUE
        var bestN = 0
        while (n < 10000) {
            n++
            floor.moveAll(1)
            val safetyFactor = floor.safetyFactor
            if (safetyFactor < minSafety) {
                minSafety = safetyFactor
                bestN = n
            }
        }

        return bestN
    }
}