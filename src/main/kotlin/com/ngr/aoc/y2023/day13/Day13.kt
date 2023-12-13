package com.ngr.aoc.y2023.day13

import com.ngr.aoc.Day
import org.apache.commons.text.similarity.LevenshteinDistance
import kotlin.math.min

class Day13 : Day<MutableList<String>, Int, Int>() {
    override fun handleLine(lines: MutableList<MutableList<String>>, line: String) {
        if (lines.isEmpty()) {
            lines.add(mutableListOf())
        }
        if (line.isNotBlank()) {
            lines.last().add(line)
        } else {
            lines.add(mutableListOf())
        }
    }

    override fun part1(lines: List<MutableList<String>>) =
        lines.sumOf { findReflexionColumn(it) + 100 * findReflexionRow(it) }

    override fun part2(lines: List<MutableList<String>>) =
        lines.sumOf { findReflexionColumnOffByOne(it) + 100 * findReflexionRowOffByOne(it) }

    private fun findReflexionRow(lines: List<String>) =
        lines.indices.drop(1).firstOrNull { row ->
            val width = min(row, lines.size - row)
            (1..width).all {
                lines[row - it] == lines[row + it - 1]
            }
        } ?: 0

    private fun findReflexionColumn(lines: List<String>) =
        findReflexionRow(
            transpose(lines)
        )

    private fun findReflexionRowOffByOne(lines: List<String>) =
        lines.indices.drop(1).firstOrNull { row ->
            val width = min(row, lines.size - row)
            (1..width).sumOf {
                LevenshteinDistance().apply(lines[row - it], lines[row + it - 1])
            } == 1
        } ?: 0

    private fun findReflexionColumnOffByOne(lines: List<String>) =
        findReflexionRowOffByOne(
            transpose(lines)
        )

    private fun transpose(lines: List<String>) = lines.first().indices.map { index ->
        lines.map { it[index] }.joinToString("") { it.toString() }
    }
}