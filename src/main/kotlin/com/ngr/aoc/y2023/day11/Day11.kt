package com.ngr.aoc.y2023.day11

import com.ngr.aoc.Day
import com.ngr.aoc.common.generateAllPairs
import java.awt.Point
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class Day11 : Day<String, Long, Long>() {

    private var rows = 0
    private var cols = 0
    private val galaxies = mutableSetOf<Point>()
    override fun handleLine(lines: MutableList<String>, line: String) {
        line.forEachIndexed { x, c ->
            if (c == '#') {
                galaxies.add(Point(x, rows))
            }
        }
        cols = line.length
        rows++
    }

    override fun part1(lines: List<String>): Long {
        val emptyRows = (0 until rows)
            .filter { y -> galaxies.none { it.y == y } }.toSet()
        val emptyCols = (0 until cols)
            .filter { x -> galaxies.none { it.x == x } }.toSet()

        val galaxiesList = galaxies.toList()
        val allPairs = galaxiesList.generateAllPairs()

        return allPairs.sumOf { it.first.distance(it.second, emptyRows, emptyCols, 2L) }
    }

    override fun part2(lines: List<String>): Long {
        val emptyRows = (0 until rows)
            .filter { y -> galaxies.none { it.y == y } }.toSet()
        val emptyCols = (0 until cols)
            .filter { x -> galaxies.none { it.x == x } }.toSet()

        val galaxiesList = galaxies.toList()
        val allPairs = galaxiesList.generateAllPairs()

        return allPairs.sumOf { it.first.distance(it.second, emptyRows, emptyCols, 1000000L) }
    }

    private fun Point.distance(other: Point, emptyRows: Set<Int>, emptyCols: Set<Int>, expansion: Long) =
        abs(x - other.x) + abs(y - other.y) + emptyCols.count {
            (min(x, other.x)..max(
                x,
                other.x
            )).contains(it)
        } * (expansion - 1) + emptyRows.count { (min(y, other.y)..max(y, other.y)).contains(it) } * (expansion - 1)

}