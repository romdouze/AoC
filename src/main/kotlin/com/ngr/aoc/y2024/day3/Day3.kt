package com.ngr.aoc.y2024.day3

import com.ngr.aoc.Day

class Day3 : Day<Triple<Int, Int, Boolean>, Int, Int>() {
    override fun handleLine(lines: MutableList<Triple<Int, Int, Boolean>>, line: String) {
        lines.addAll(MulParser.parse(line))
    }

    override fun part1(lines: List<Triple<Int, Int, Boolean>>) =
        lines.sumOf { it.first * it.second }

    override fun part2(lines: List<Triple<Int, Int, Boolean>>) =
        lines.filter { it.third }.sumOf { it.first * it.second }
}