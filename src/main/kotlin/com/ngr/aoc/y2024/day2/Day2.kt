package com.ngr.aoc.y2024.day2

import com.ngr.aoc.Day
import kotlin.math.abs
import kotlin.math.sign

class Day2 : Day<List<Int>, Int, Int>() {
    override fun handleLine(lines: MutableList<List<Int>>, line: String) {
        lines.add(line.split(" ").map { it.toInt() })
    }

    override fun part1(lines: List<List<Int>>) =
        lines.count { isValid(it) }

    override fun part2(lines: List<List<Int>>) =
        lines.count {
            isValid(it) ||
                    it.indices.any { i -> isValid(it.filterIndexed { index, _ -> index != i }) }
        }

    private fun isValid(levels: List<Int>) =
        (levels[0] - levels[1]).sign.let { s ->
            levels.zipWithNext().all { pair ->
                (pair.first - pair.second).let { d ->
                    d.sign == s && (1..3).contains(abs(d))
                }
            }
        }
}