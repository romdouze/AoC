package com.ngr.aoc.y2025.day7

import com.ngr.aoc.Day
import java.awt.Point

var WIDTH: Int = 0
var HEIGHT: Int = 0

class Day7 : Day<Point, Int, Long>() {

    private lateinit var start: Point

    override fun handleLine(lines: MutableList<Point>, line: String) {
        WIDTH = line.length
        line.forEachIndexed { x, c ->
            if (c != '.') {
                val pos = Point(x, HEIGHT)
                if (c == 'S') {
                    start = pos
                } else {
                    lines.add(pos)
                }
            }
        }
        HEIGHT++
    }

    override fun part1(lines: List<Point>): Int {
        var splits = 0
        var beams = setOf(start.x)
        (start.y until HEIGHT).forEach { y ->
            val newBeams = mutableSetOf<Int>()
            beams.forEach { beam ->
                if (lines.contains(Point(beam, y))) {
                    splits++
                    newBeams.add(beam - 1)
                    newBeams.add(beam + 1)
                } else {
                    newBeams.add(beam)
                }
            }
            beams = newBeams
        }
        return splits
    }

    override fun part2(lines: List<Point>): Long {
        var beams = mapOf(start.x to 1L)
        (start.y until HEIGHT).forEach { y ->
            val newBeams = mutableMapOf<Int, Long>()
            beams.forEach { beam ->
                val (x, beamSize) = beam
                if (lines.contains(Point(x, y))) {
                    newBeams[x - 1] = (newBeams[x - 1] ?: 0) + beamSize
                    newBeams[x + 1] = (newBeams[x + 1] ?: 0) + beamSize
                } else {
                    newBeams[x] = (newBeams[x] ?: 0) + beamSize
                }
            }
            beams = newBeams
        }
        return beams.values.sum()
    }
}