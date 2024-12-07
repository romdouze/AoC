package com.ngr.aoc.y2024.day7

import com.ngr.aoc.Day
import com.ngr.aoc.y2024.day7.Operator.ADD
import com.ngr.aoc.y2024.day7.Operator.CONCATENATE
import com.ngr.aoc.y2024.day7.Operator.MULTIPLY

class Day7 : Day<Equation, Long, Long>() {
    override fun handleLine(lines: MutableList<Equation>, line: String) {
        lines.add(
            line.split(" ").let {
                Equation(
                    it[0].dropLast(1).toLong(),
                    it.drop(1).map { it.toLong() },
                )
            }
        )
    }

    override fun part1(lines: List<Equation>) =
        lines.filter { it.canBeTrue(listOf(ADD, MULTIPLY)) }
            .sumOf { it.testValue }

    override fun part2(lines: List<Equation>) =
        lines.filter { it.canBeTrue(listOf(ADD, MULTIPLY, CONCATENATE)) }
            .sumOf { it.testValue }
}