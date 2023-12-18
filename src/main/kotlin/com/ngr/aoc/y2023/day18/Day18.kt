package com.ngr.aoc.y2023.day18

import com.ngr.aoc.Day
import kotlin.math.abs

class Day18 : Day<Instruction, Long, Long>() {
    override fun handleLine(lines: MutableList<Instruction>, line: String) {
        lines.add(
            Instruction.fromString(line)
        )
    }

    override fun part1(lines: List<Instruction>) =
        measureHole(lines, Instruction::dir, Instruction::distance)

    override fun part2(lines: List<Instruction>) =
        measureHole(lines, Instruction::trueDir, Instruction::trueDistance)

    private fun measureHole(
        lines: List<Instruction>,
        dirSelector: (Instruction) -> Dir,
        distanceSelector: (Instruction) -> Long
    ): Long {
        val vertices = mutableSetOf<PointL>()
        var pos = PointL(0, 0)

        lines.forEach { instruction ->
            pos = pos.plus(dirSelector(instruction), distanceSelector(instruction))
                .also { vertices.add(it) }
        }

        return abs(lines.sumOf { distanceSelector(it) } + vertices.zipWithNext()
            .plus(vertices.last() to vertices.first())
            .sumOf {
                (it.first.y + it.second.y) * (it.first.x - it.second.x)
            }) / 2 + 1
    }
}