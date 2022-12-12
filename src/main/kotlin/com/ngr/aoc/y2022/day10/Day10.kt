package com.ngr.aoc.y2022.day10

import com.ngr.aoc.y2022.Day
import kotlin.math.abs

class Day10 : Day<Instruction, Int, String>() {

    private val marks = IntProgression.fromClosedRange(20, 220, 40)
    private val registry = mutableListOf(1)
    override fun handleLine(lines: MutableList<Instruction>, line: String) {
        updateRegistry(Instruction.fromString(line))
    }

    override fun part1(lines: List<Instruction>) =
        registry
            .let { seq ->
                marks.sumOf { it * seq[it - 1] }
            }

    override fun part2(lines: List<Instruction>): String {
        val drawing = StringBuilder("\n")
        registry.let { seq ->
            (0 until 6).forEach { y ->
                (0 until 39).forEach { x ->
                    drawing.append(
                        if (abs(seq[y * 40 + x] - x) <= 1) {
                            "#"
                        } else {
                            "."
                        }
                    )
                }
                drawing.append("\n")
            }
        }

        return drawing.toString()
    }

    private fun updateRegistry(it: Instruction) {
        when (it.operation) {
            Operation.NOOP -> registry.add(registry.last())
            Operation.ADD -> {
                registry.add(registry.last())
                registry.add(registry.last() + it.param!!)
            }
        }
    }
}