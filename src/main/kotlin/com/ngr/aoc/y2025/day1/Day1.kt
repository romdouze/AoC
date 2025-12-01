package com.ngr.aoc.y2025.day1

import com.ngr.aoc.Day
import kotlin.math.abs

class Day1 : Day<Int, Int, Int>() {
    override fun handleLine(lines: MutableList<Int>, line: String) {
        line[0].also {
            if (it == 'R')
                lines.add(line.drop(1).toInt())
            else
                lines.add(-line.drop(1).toInt())
        }
    }

    override fun part1(lines: List<Int>): Int {
        var count = 0
        lines.fold(50) { acc, rotation ->
            (acc + rotation)
                .mod(100)
                .also {
                    if (it == 0) count++
                }
        }
        return count
    }

    override fun part2(lines: List<Int>): Int {
        var count = 0
        lines.fold(50) { acc, rotation ->
            count += abs(rotation / 100)

            val newValue = acc + rotation % 100
            if (acc != 0 && newValue !in 1 until 100 && rotation % 100 != 0) count++

            newValue
                .mod(100)
        }
        return count
    }
}