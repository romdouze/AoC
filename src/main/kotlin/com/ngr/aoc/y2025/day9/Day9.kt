package com.ngr.aoc.y2025.day9

import com.ngr.aoc.Day
import com.ngr.aoc.common.generateAllPairs
import java.awt.Point

class Day9 : Day<Point, Long, Long>() {
    override fun handleLine(lines: MutableList<Point>, line: String) {
        line.split(",").let {
            lines.add(Point(it[0].toInt(), it[1].toInt()))
        }
    }

    override fun part1(lines: List<Point>) =
        lines.generateAllPairs()
            .map { it.asRectangle() }
            .maxOf { it.area() }

    override fun part2(lines: List<Point>) =
        lines.findEdges().let { edges ->
            lines.generateAllPairs()
                .map { it.asRectangle() }
                .filter { pair -> edges.none { pair.intersects(it) } }
                .maxOf { it.area() }
        }
}