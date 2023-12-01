package com.ngr.aoc.y2022.day1

import com.ngr.aoc.Day

class Day1 : Day<List<Int>, Int, Int>() {

    override fun handleLine(lines: MutableList<List<Int>>, line: String) {
        if (lines.isEmpty()) lines.add(mutableListOf())
        if (line.isBlank()) {
            lines.add(listOf())
        } else {
            lines[lines.size - 1] = lines.last().toMutableList().apply { add(Integer.valueOf(line)) }
        }
    }

    override fun part1(lines: List<List<Int>>) =
        lines.maxBy { it.sum() }.sum()

    override fun part2(lines: List<List<Int>>) =
        lines.sortedByDescending { it.sum() }
            .take(3).sumOf { it.sum() }
}