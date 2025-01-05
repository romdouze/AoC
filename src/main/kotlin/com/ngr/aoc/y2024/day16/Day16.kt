package com.ngr.aoc.y2024.day16

import com.ngr.aoc.Day
import java.awt.Point

class Day16 : Day<String, Int, Int>() {

    private var WIDTH = 0
    private var HEIGHT = 0

    private val walls = mutableSetOf<Point>()
    private lateinit var start: Point
    private lateinit var end: Point

    override fun handleLine(lines: MutableList<String>, line: String) {
        lines.forEachIndexed { x, c ->
            if (c == "#") {
                walls.add(Point(x, HEIGHT))
            } else if (c == "S") {
                start = Point(x, HEIGHT)
            } else if (c == "E") {
                end = Point(x, HEIGHT)
            }
        }
        WIDTH = line.length
        HEIGHT++
    }

    override fun part1(lines: List<String>): Int {
        TODO("Not yet implemented")
    }

    override fun part2(lines: List<String>): Int {
        TODO("Not yet implemented")
    }
}