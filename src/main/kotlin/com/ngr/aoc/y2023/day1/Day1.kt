package com.ngr.aoc.y2023.day1

import com.ngr.aoc.Day

class Day1 : Day<String, Int, Int>() {

    override fun handleLine(lines: MutableList<String>, line: String) {
        lines.add(line)
    }

    override fun part1(lines: List<String>) =
        lines.sumOf {
            "${it.first { it.isDigit() }}${it.last { it.isDigit() }}".toInt()
        }

    override fun part2(lines: List<String>) =
        lines.sumOf { line ->
            val first = digits
                .filter { line.contains(it.key) }
                .minBy {
                    line.indexOf(it.key)
                }.value
            val second = digits
                .filter { line.contains(it.key) }
                .maxBy {
                    line.lastIndexOf(it.key)
                }.value
            "$first$second".toInt()
        }

    private val digits = mapOf(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9,
        "1" to 1,
        "2" to 2,
        "3" to 3,
        "4" to 4,
        "5" to 5,
        "6" to 6,
        "7" to 7,
        "8" to 8,
        "9" to 9,
    )
}