package com.ngr.aoc.y2023.day23

import com.ngr.aoc.Day
import java.awt.Point

class Day23 : Day<String, Int, Int>() {

    private val map = mutableMapOf<Point, Tile>()

    private var rows = 0

    override fun handleLine(lines: MutableList<String>, line: String) {
        map.putAll(
            line.mapIndexedNotNull { x, c -> Tile.fromChar(c)?.let { Point(x, rows) to it } }
        )
        rows++
    }

    override fun part1(lines: List<String>) =
        findLongestPath(Tile::dirs)

    // 1h + Stack overflow issue :(
    override fun part2(lines: List<String>) =
        findLongestPath { Dir.values().toList() }

    private fun findLongestPath(availableDirs: (Tile) -> List<Dir>): Int {
        val start = map.keys.minBy { it.y }
        val end = map.keys.maxBy { it.y }

        return LongestPathSolver(start, end, map, availableDirs)
            .longestPath().size - 1
    }
}