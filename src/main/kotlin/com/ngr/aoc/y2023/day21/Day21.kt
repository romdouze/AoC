package com.ngr.aoc.y2023.day21

import com.ngr.aoc.Day
import java.awt.Point

class Day21 : Day<String, Int, Long>() {

    private val plots = mutableSetOf<Point>()

    private var start = Point(-1, -1)

    private var rows = 0
    private var cols = 0

    override fun handleLine(lines: MutableList<String>, line: String) {
        line.forEachIndexed { x, c ->
            if (c == '.' || c == 'S') {
                plots.add(Point(x, rows))
                if (c == 'S') start = Point(x, rows)
            }
        }
        rows++
        cols = line.length
    }

    override fun part1(lines: List<String>): Int {
        var locations = setOf(start)

        repeat(64) {
            locations = locations.flatMap { pos ->
                Dir.values().map { pos + it }.filter { plots.contains(it) }
            }.toSet()
        }

        return locations.size
    }

    override fun part2(lines: List<String>): Long {
        val requiredSteps = 26501365
        var locations = setOf(start)

        repeat(cols * 2 + cols / 2) {
            locations = locations.flatMap { pos ->
                Dir.values().map { pos + it }.filter { it.infinitelyInbound() }
            }.toSet()
        }

        /*
             /^\
            //1\\
            <101>
            \\1//
             \v/
         */

        val oddLocations = locations.filter {
            (0 until cols).contains(it.x) && (0 until rows).contains(it.y)
        }
        val evenLocations = locations.filter {
            (-cols until 0).contains(it.x) && (0 until rows).contains(it.y)
        }
        val WCornerLocations = locations.filter {
            (-2 * cols until -1 * cols).contains(it.x) && (0 until rows).contains(it.y)
        }
        val ECornerLocations = locations.filter {
            (2 * cols until 3 * cols).contains(it.x) && (0 until rows).contains(it.y)
        }
        val NCornerLocations = locations.filter {
            (0 until cols).contains(it.x) && (-2 * rows until -1 * rows).contains(it.y)
        }
        val SCornerLocations = locations.filter {
            (0 until cols).contains(it.x) && (2 * rows until 3 * rows).contains(it.y)
        }
        val NWEdgeInnerLocations = locations.filter {
            (-cols until 0).contains(it.x) && (-rows until 0).contains(it.y)
        }
        val NWEdgeOuterLocations = locations.filter {
            (-2 * cols until -cols).contains(it.x) && (-rows until 0).contains(it.y)
        }
        val NEEdgeInnerLocations = locations.filter {
            (cols until 2 * cols).contains(it.x) && (-rows until 0).contains(it.y)
        }
        val NEEdgeOuterLocations = locations.filter {
            (2 * cols until 3 * cols).contains(it.x) && (-rows until 0).contains(it.y)
        }
        val SEEdgeInnerLocations = locations.filter {
            (cols until 2 * cols).contains(it.x) && (rows until 2 * rows).contains(it.y)
        }
        val SEEdgeOuterLocations = locations.filter {
            (2 * cols until 3 * cols).contains(it.x) && (rows until 2 * rows).contains(it.y)
        }
        val SWEdgeInnerLocations = locations.filter {
            (-cols until 0).contains(it.x) && (rows until 2 * rows).contains(it.y)
        }
        val SWEdgeOuterLocations = locations.filter {
            (-2 * cols until -cols).contains(it.x) && (rows until 2 * rows).contains(it.y)
        }

        val gridWidth = (requiredSteps * 2 + 1) / cols
        val oddGridCountMiddleRow = (gridWidth - 2L) / 2
        val oddGridCount = oddGridCountMiddleRow * (oddGridCountMiddleRow + 1) / 2 +
                (oddGridCountMiddleRow - 1) * (oddGridCountMiddleRow) / 2
        val evenGridCountMiddleRow = oddGridCountMiddleRow + 1
        val evenGridCount = evenGridCountMiddleRow * (evenGridCountMiddleRow + 1) / 2 +
                (evenGridCountMiddleRow - 1) * (evenGridCountMiddleRow) / 2

        return oddLocations.size.toLong() * oddGridCount +
                evenLocations.size * evenGridCount +
                NWEdgeInnerLocations.size * (gridWidth / 2 - 1) +
                NEEdgeInnerLocations.size * (gridWidth / 2 - 1) +
                SEEdgeInnerLocations.size * (gridWidth / 2 - 1) +
                SWEdgeInnerLocations.size * (gridWidth / 2 - 1) +
                NWEdgeOuterLocations.size * (gridWidth / 2) +
                NEEdgeOuterLocations.size * (gridWidth / 2) +
                SEEdgeOuterLocations.size * (gridWidth / 2) +
                SWEdgeOuterLocations.size * (gridWidth / 2) +
                WCornerLocations.size + ECornerLocations.size + NCornerLocations.size + SCornerLocations.size
    }

    private fun Point.infinitelyInbound() =
        plots.contains(Point(x.mod(cols), y.mod(rows)))
}