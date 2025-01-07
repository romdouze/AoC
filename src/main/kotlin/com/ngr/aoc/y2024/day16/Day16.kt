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
        line.forEachIndexed { x, c ->
            when (c) {
                '#' -> walls.add(Point(x, HEIGHT))
                'S' -> start = Point(x, HEIGHT)
                'E' -> end = Point(x, HEIGHT)
            }
        }
        WIDTH = line.length
        HEIGHT++
    }

    override fun part1(lines: List<String>) =
        Maze(walls, start, end)
            .shortestPathToEndWithNormalFlood()
            .score()

    override fun part2(lines: List<String>) =
        Maze(walls, start, end)
            .bestPathsToEnd()
            .flatMap { it.poses }
            .map { it.p }.toSet()
            .count()
}