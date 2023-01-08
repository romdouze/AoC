package com.ngr.aoc.y2022.day22

import com.ngr.aoc.y2022.Day
import com.ngr.aoc.y2022.day22.Dir.*
import com.ngr.aoc.y2022.day22.WarpableMapWalker.Companion.NOTHING
import java.io.InputStream

class Day22 : Day<Instruction, Int, Int>() {

    private lateinit var map: WarpableMapWalker

    override fun readInput(data: InputStream): List<Instruction> {
        val drawing = mutableListOf<String>()
        val path: String

        data.bufferedReader().let { reader ->
            do {
                val line = reader.readLine()
                drawing.add(line)
            } while (line.isNotEmpty())
            drawing.removeLast()

            path = reader.readLine()
        }
        val longest = drawing.maxOf { it.length }
        map = WarpableMapWalker(drawing.map { it.padEnd(longest, NOTHING) })
        return Instruction.fromString(path).toList()
    }

    override fun handleLine(lines: MutableList<Instruction>, line: String) {
        // DO NOTHING
    }

    override fun part1(lines: List<Instruction>): Int {
        map.goToStartingPosition()

        lines.forEach { instruction ->
            map.walk(instruction)
        }

        return map.location.score()
    }

    override fun part2(lines: List<Instruction>): Int {
        TODO("Not yet implemented")
    }

    private fun Location.score() =
        1000 * (pos.y + 1) + 4 * (pos.x + 1) + dir.score

    private val Dir.score
        get() =
            when (this) {
                R -> 0
                D -> 1
                L -> 2
                U -> 3
            }
}