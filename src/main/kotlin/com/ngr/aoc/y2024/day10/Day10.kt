package com.ngr.aoc.y2024.day10

import com.ngr.aoc.Day
import java.awt.Point

class Day10 : Day<Point, Int, Int>() {

    private var WIDTH = 0
    private var HEIGHT = 0

    private val map = mutableMapOf<Point, Int>()

    override fun handleLine(lines: MutableList<Point>, line: String) {
        map.putAll(line.mapIndexed { x, ch ->
            val point = Point(x, HEIGHT)
            if (ch == '0') {
                lines.add(point)
            }
            point to ch.digitToInt()
        }.associate { it.first to it.second })
        WIDTH = line.length
        HEIGHT++
    }

    override fun part1(lines: List<Point>) =
        MapWalker(map).scoreTrailheads(lines)

    override fun part2(lines: List<Point>) =
        MapWalker(map).scoreTrailheadsWithRating(lines)
}