package com.ngr.aoc.y2024.day24

import com.ngr.aoc.Day
import java.io.InputStream

class Day24 : Day<Gate, Long, String>() {

    private val startingValues = mutableMapOf<String, Boolean>()

    override fun readInput(data: InputStream): List<Gate> {

        val gates = mutableListOf<Gate>()

        data.bufferedReader().use {
            var line = it.readLine()
            do {
                line.split(": ").also {
                    startingValues[it[0]] = it[1] == "1"
                }
                line = it.readLine()
            } while (line.isNotEmpty())

            line = it.readLine()
            do {
                gates.add(Gate.fromString(line))
                line = it.readLine()
            } while (line != null)

        }

        return gates
    }

    override fun handleLine(lines: MutableList<Gate>, line: String) {
        // DO NOTHING
    }

    override fun part1(lines: List<Gate>) =
        Circuit(lines, startingValues)
            .apply { simulate() }
            .finalOutput()

    override fun part2(lines: List<Gate>) =
        CircuitFixer(lines, startingValues)
            .findFix()
            .sorted().joinToString(",")
}