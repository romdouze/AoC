package com.ngr.aoc.y2025.day10

import com.ngr.aoc.Day

class Day10 : Day<FactoryMachine, Int, Long>() {
    override fun handleLine(
        lines: MutableList<FactoryMachine>,
        line: String
    ) {
        lines.add(FactoryMachine.fromString(line))
    }

    override fun part1(lines: List<FactoryMachine>) =
        lines.sumOf { it.findFewestPressesToTargetIndicators() }

    override fun part2(lines: List<FactoryMachine>): Long {
        TODO("Not yet implemented")
    }
}