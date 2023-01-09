package com.ngr.aoc.y2022.day24

import com.ngr.aoc.y2022.Day
import java.awt.Point

class Day24 : Day<String, Int, Int>() {

    private companion object {
        private const val WALL = '#'
        private const val EMPTY = '.'
    }

    private lateinit var widthRange: IntRange
    private lateinit var heightRange: IntRange
    private lateinit var start: Point
    private lateinit var end: Point

    private val initialBlizzards: MutableSet<Blizzard> = mutableSetOf()
    private var row = 0

    private val allActions: List<Dir?> = Dir.values().toList() + null

    override fun handleLine(lines: MutableList<String>, line: String) {
        lines.add(line)

        initialBlizzards.addAll(line.mapIndexedNotNull { x, c ->
            Dir.fromString(c)?.let { Blizzard(Point(x, row), it) }
        })
        row++
    }

    override fun part1(lines: List<String>): Int {
        initConstants(lines)

        val allBlizzards = initialBlizzards.toSet()
        val toVisit = ArrayDeque(listOf(State(Point(start), allBlizzards)))

        while (toVisit.isNotEmpty()) {

        }

        return 0
    }

    override fun part2(lines: List<String>): Int {
        TODO("Not yet implemented")
    }

    private fun initConstants(lines: List<String>) {
        widthRange = lines[0].indices
        heightRange = lines.indices
        start = Point(lines[0].indexOfFirst { it == EMPTY }, 0)
        end = Point(lines[heightRange.last].indexOfFirst { it == EMPTY }, heightRange.last)
    }
}