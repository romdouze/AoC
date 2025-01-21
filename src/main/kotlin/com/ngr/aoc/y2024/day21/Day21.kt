package com.ngr.aoc.y2024.day21

import com.ngr.aoc.Day

class Day21 : Day<String, Int, Int>() {
    override fun handleLine(lines: MutableList<String>, line: String) {
        lines.add(line)
    }

    override fun part1(lines: List<String>) =
        lines.sumOf { code ->
            KeypadChain(2).shortestInputForCode(code)
        }

    override fun part2(lines: List<String>) =
        lines.sumOf { code ->
            KeypadChain(25).shortestInputForCode(code)
        }
}