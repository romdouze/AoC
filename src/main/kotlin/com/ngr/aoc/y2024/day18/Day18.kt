package com.ngr.aoc.y2024.day18

import com.ngr.aoc.Day
import java.awt.Point

class Day18 : Day<Point, Int, String>() {
    override fun handleLine(lines: MutableList<Point>, line: String) {
        lines.add(
            line.split(",").let {
                Point(it[0].toInt(), it[1].toInt())
            }
        )
    }

    override fun part1(lines: List<Point>) =
        MemoryNavigator(lines)
            .navigateAfterN(1024)!!
            .count()

    override fun part2(lines: List<Point>) =
        MemoryNavigator(lines)
            .pathBlockedBy()
            .let { "${it.x},${it.y}" }
}