package com.ngr.aoc.y2024.day19

import com.ngr.aoc.Day
import java.io.InputStream

class Day19 : Day<String, Int, Int>() {

    private lateinit var towels: List<String>

    override fun readInput(data: InputStream): List<String> {

        val patterns = mutableListOf<String>()

        data.bufferedReader().use {
            towels = it.readLine().split(",").map { it.trim() }

            it.readLine()

            var line = it.readLine()
            do {
                patterns.add(line)
                line = it.readLine()
            } while (line != null)

        }

        return patterns
    }

    override fun handleLine(lines: MutableList<String>, line: String) {
        // DO NOTHING
    }

    override fun part1(lines: List<String>): Int {
        TODO("Not yet implemented")
    }

    override fun part2(lines: List<String>): Int {
        TODO("Not yet implemented")
    }
}