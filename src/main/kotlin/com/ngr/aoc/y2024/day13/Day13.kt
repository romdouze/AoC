package com.ngr.aoc.y2024.day13

import com.ngr.aoc.Day
import java.io.InputStream

class Day13 : Day<ClawMachine, Int, Long>() {

    override fun readInput(data: InputStream): List<ClawMachine> {
        val machines = mutableListOf<ClawMachine>()
        data.bufferedReader().also { reader ->
            do {
                val buttonA = reader.readLine()
                val buttonB = reader.readLine()
                val prize = reader.readLine()

                machines.add(ClawMachine(buttonA, buttonB, prize))
            } while (reader.readLine() != null)
        }
        return machines
    }

    override fun handleLine(lines: MutableList<ClawMachine>, line: String) {
        // DO NOTHING
    }

    override fun part1(lines: List<ClawMachine>) =
        lines.filter { it.isWinnable }
            .sumOf { it.winCost }

    override fun part2(lines: List<ClawMachine>) =
        lines.filter { it.isWinnableOffset }
            .sumOf { it.winCostOffset }
}