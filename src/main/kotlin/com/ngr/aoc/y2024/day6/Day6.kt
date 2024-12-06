package com.ngr.aoc.y2024.day6

import com.ngr.aoc.Day
import java.awt.Point

class Day6 : Day<String, Int, Int>() {

    private val OBSTACLE = '#'
    private val START = '^'
    private var WIDTH = 0
    private var HEIGHT = 0

    private val obstacles = mutableSetOf<Point>()
    private lateinit var start: Point

    override fun handleLine(lines: MutableList<String>, line: String) {
        line.indexOf(START)
            .takeIf { it != -1 }
            ?.let {
                start = Point(it, HEIGHT)
            }
        obstacles.addAll(line.mapIndexedNotNull { x, c ->
            c.takeIf { it == OBSTACLE }
                ?.let { Point(x, HEIGHT) }
        })
        WIDTH = line.length
        HEIGHT++
    }

    override fun part1(lines: List<String>) =
        Patroller(obstacles, start, WIDTH, HEIGHT)
            .allVisitedPos().count()

    override fun part2(lines: List<String>) =
        Patroller(obstacles, start, WIDTH, HEIGHT)
            .allVisitedPos().minus(start)
            .count { newObstacle ->
                Patroller(obstacles + newObstacle, start, WIDTH, HEIGHT)
                    .hasLoop()
            }
}