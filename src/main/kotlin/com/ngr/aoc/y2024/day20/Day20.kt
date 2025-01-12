package com.ngr.aoc.y2024.day20

import com.ngr.aoc.Day
import java.awt.Point

class Day20 : Day<String, Int, Int>() {

    private var WIDTH = 0
    private var HEIGHT = 0

    private val walls = mutableSetOf<Point>()
    private lateinit var start: Point
    private lateinit var end: Point

    override fun handleLine(lines: MutableList<String>, line: String) {
        line.forEachIndexed { x, c ->
            Point(x, HEIGHT).also {
                when (c) {
                    '#' -> walls.add(it)
                    'S' -> start = it
                    'E' -> end = it
                }
            }
        }
        WIDTH = line.length
        HEIGHT++
    }

    override fun part1(lines: List<String>) =
        CheatRacer(walls, start, end)
            .findCheats(2, 100)

    override fun part2(lines: List<String>) =
        CheatRacer(walls, start, end)
            .findCheats(20, 100)
}