package com.ngr.aoc.y2025.day4

import com.ngr.aoc.Day
import java.awt.Point

var WIDTH: Int = 0
var HEIGHT: Int = 0

class Day4 : Day<Point, Int, Int>() {
    override fun handleLine(lines: MutableList<Point>, line: String) {
        WIDTH = line.length
        line.forEachIndexed { x, c ->
            if (c == '@') {
                val pos = Point(x, HEIGHT)
                lines.add(pos)
            }
        }
        HEIGHT++
    }

    override fun part1(lines: List<Point>) =
        lines.toSet().allAccessible().size

    override fun part2(lines: List<Point>): Int {
        val remaining = lines.toMutableSet()
        val allRemoved = mutableSetOf<Point>()

        do {
            val justRemoved = remaining.allAccessible()
            allRemoved.addAll(justRemoved)
            remaining.removeAll(justRemoved)
        } while (justRemoved.isNotEmpty())

        return allRemoved.size
    }
}