package com.ngr.aoc.y2023.day17

import com.ngr.aoc.Day
import com.ngr.aoc.y2023.day17.Dir.RIGHT
import java.awt.Point

class Day17 : Day<String, Int, Int>() {

    private val map = mutableMapOf<Point, Int>()

    private var rows = 0
    private var cols = 0

    override fun handleLine(lines: MutableList<String>, line: String) {
        map.putAll(line.mapIndexed { index, c -> Point(index, rows) to c.digitToInt() })
        cols = line.length
        rows++
    }

    override fun part1(lines: List<String>): Int {
        val start = Point(0, 0) to RIGHT
        val toVisit = ArrayDeque<Pair<Point, Dir>>()

        do {

        } while (toVisit.isNotEmpty())
    }

    override fun part2(lines: List<String>): Int {
        TODO("Not yet implemented")
    }

    fun State.move(dir: Dir) =
        (pos + dir).let {
            State(
                Point(pos.x, pos.y),
                dir,
                path + (it to dir),
                heatLoss + map[it]!!
            )
        }

    fun Point.inbound() =
        (0 until cols).contains(x) &&
                (0 until rows).contains(y)
}