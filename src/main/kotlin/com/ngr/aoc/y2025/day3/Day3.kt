package com.ngr.aoc.y2025.day3

import com.ngr.aoc.Day

class Day3 : Day<String, Long, Long>() {
    override fun handleLine(lines: MutableList<String>, line: String) {
        lines.add(line)
    }

    override fun part1(lines: List<String>) =
        lines.sumOf { makeLargestNumber(it, 2) }

    override fun part2(lines: List<String>) =
        lines.sumOf { makeLargestNumber(it, 12) }

    private fun makeLargestNumber(line: String, n: Int): Long {
        val digits = mutableListOf<Char>()
        var remainingLine = line
        (0 until n).reversed().forEach {
            val digit = remainingLine.dropLast(it).max()
            digits.add(digit)
            remainingLine = remainingLine.substringAfter(digit)
        }
        return String(digits.toCharArray()).toLong()
    }

}