package com.ngr.aoc.y2024.day21

import com.ngr.aoc.Day
import com.ngr.aoc.y2024.day21.KeypadType.NUMERIC

class Day21 : Day<String, Int, Int>() {
    override fun handleLine(lines: MutableList<String>, line: String) {
        lines.add(line)
    }

    override fun part1(lines: List<String>) =
        lines.sumOf { code ->
            KeypadOperator(NUMERIC).allShortestInputsForCode(code.toKeys())
                .let { allPaths ->
                    val minSize = allPaths.minOf { it.size }
                    allPaths.filter { it.size == minSize }
                }
                .flatMap { KeypadOperator(KeypadType.DIRECTIONAL).allShortestInputsForCode(it.toKeys()) }
                .let { allPaths ->
                    val minSize = allPaths.minOf { it.size }
                    allPaths.filter { it.size == minSize }
                }
                .flatMap { KeypadOperator(KeypadType.DIRECTIONAL).allShortestInputsForCode(it.toKeys()) }
                .let { allPaths ->
                    val minSize = allPaths.minOf { it.size }
                    allPaths.filter { it.size == minSize }
                }
                .minBy { it.size }.size * code.dropLast(1).toInt()
        }

    override fun part2(lines: List<String>): Int {
        TODO("Not yet implemented")
    }
}