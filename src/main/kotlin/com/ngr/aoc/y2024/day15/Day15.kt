package com.ngr.aoc.y2024.day15

import com.ngr.aoc.Day
import java.awt.Point
import java.io.InputStream

var WIDTH = 0
var HEIGHT = 0

class Day15 : Day<Dir, Int, Int>() {

    private val walls = mutableSetOf<Point>()
    private val boxes = mutableSetOf<Point>()
    private lateinit var robotStart: Point

    override fun readInput(data: InputStream): List<Dir> {
        data.bufferedReader().use {
            var y = 0
            var line = it.readLine()
            WIDTH = line.length
            do {
                line.forEachIndexed { x, c ->
                    Point(x, y).also {
                        when (c) {
                            BOX -> boxes.add(it)
                            WALL -> walls.add(it)
                            ROBOT -> robotStart = it
                        }
                    }
                }
                line = it.readLine()
                y++
            } while (line.isNotEmpty())

            HEIGHT = y

            val moves = mutableListOf<Dir>()
            line = it.readLine()
            do {
                line.forEach { moves.add(Dir.fromString(it.toString())) }
                line = it.readLine()
            } while (line != null)

            return moves
        }
    }

    override fun handleLine(lines: MutableList<Dir>, line: String) {
        // DO NOTHING
    }

    override fun part1(lines: List<Dir>) =
        Warehouse(walls, boxes, robotStart)
            .apply { moveRobot(lines) }
            .score()

    override fun part2(lines: List<Dir>) =
        WideWarehouse(walls, boxes, robotStart)
            .apply { moveRobot(lines) }
            .score()
}