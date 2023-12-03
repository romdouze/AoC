package com.ngr.aoc.y2023.day3

import com.ngr.aoc.Day
import java.awt.Point
import kotlin.math.abs

class Day3 : Day<String, Long, Long>() {

    private companion object {
        private val NUMBER_PATTERN = "\\d+".toRegex()
        private val SYMBOL_PATTERN = "[^\\d.\\n]".toRegex()
    }

    private val numberRanges = mutableMapOf<Pair<Int, IntRange>, Int>()
    private val symbols = mutableSetOf<Pair<Point, String>>()

    private var row = 0

    override fun handleLine(lines: MutableList<String>, line: String) {
        lines.add(line)

        NUMBER_PATTERN.findAll(line).forEach {
            numberRanges[row to it.range] = it.value.toInt()
        }

        SYMBOL_PATTERN.findAll(line).forEach {
            symbols.add(Point(it.range.first, row) to it.value)
        }

        row++
    }

    override fun part1(lines: List<String>): Long {
        val remainingNumbers = numberRanges.toMutableMap()

        return symbols.map { it.first }
            .sumOf { s ->
                val nearbyNumbers = remainingNumbers
                    .nearbyNumbers(s)
                    .also { it.keys.forEach { remainingNumbers.remove(it) } }

                nearbyNumbers.map { it.value.toLong() }.sum()
            }
    }

    override fun part2(lines: List<String>): Long =
        symbols.filter { it.second == "*" }
            .associateWith { numberRanges.nearbyNumbers(it.first) }
            .filter { it.value.size == 2 }
            .map { it.value.values.fold(1L) { acc, i -> acc * i } }
            .sum()

    private fun Map<Pair<Int, IntRange>, Int>.nearbyNumbers(pos: Point) =
        filter { abs(it.key.first - pos.y) <= 1 }
            .filter { it.key.second.intersect(pos.x - 1..pos.x + 1).isNotEmpty() }
}