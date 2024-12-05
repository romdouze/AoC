package com.ngr.aoc.y2024.day5

import com.ngr.aoc.Day
import java.io.InputStream

class Day5 : Day<List<Int>, Int, Int>() {

    private val rules = mutableListOf<Pair<Int, Int>>()

    override fun readInput(data: InputStream): List<List<Int>> {
        val lines = mutableListOf<List<Int>>()

        data.bufferedReader().also { reader ->
            var line = reader.readLine()
            do {
                rules.add(line.split("|").map { it.toInt() }.let { it[0] to it[1] })
                line = reader.readLine()
            } while (line.isNotEmpty())

            line = reader.readLine()
            do {
                lines.add(line.split(",").map { it.toInt() })
                line = reader.readLine()
            } while (line?.isNotEmpty() == true)
        }

        return lines
    }

    override fun handleLine(
        lines: MutableList<List<Int>>,
        line: String
    ) {
        lines.add(line.split(",").map { it.toInt() })
    }

    override fun part1(lines: List<List<Int>>) =
        PageSorter(rules).let { sorter ->
            lines.filter { sorter.isSorted(it) }
                .sumOf { it[it.lastIndex / 2] }
        }

    override fun part2(lines: List<List<Int>>) =
        PageSorter(rules).let { sorter ->
            lines.filterNot { sorter.isSorted(it) }
                .map { it.sortedWith(sorter.pageComparator) }
                .sumOf { it[it.lastIndex / 2] }
        }
}