package com.ngr.aoc.y2022.day5

import com.ngr.aoc.y2022.Day
import java.io.InputStream

class Day5 : Day<Operation, String, String>() {

    private val initialStacks = mutableListOf<Stack>()

    override fun readInput(data: InputStream): List<Operation> {
        val drawing = mutableListOf<String>()
        data.mark(0)
        data.bufferedReader().let { reader ->
            do {
                val line = reader.readLine()
                drawing.add(line)
            } while (!line.startsWith(" "))
        }

        data.reset()

        drawing.last()
            .let { indicesLine ->
                indicesLine.forEachIndexed { i, c ->
                    if (c.isDigit()) {
                        val elements = mutableListOf<String>()
                        elements.addAll(
                            drawing.reversed()
                                .drop(1)
                                .map { it.padEnd(drawing.last().length, ' ') }
                                .map { it[i].toString() }
                                .filterNot { it.isBlank() }
                        )
                        initialStacks.add(c.digitToInt() - 1, Stack(elements))
                    }
                }
            }

        return super.readInput(data)
    }

    override fun handleLine(lines: MutableList<Operation>, line: String) {
        if (line.startsWith("move")) {
            lines.add(Operation.fromString(line))
        }
    }

    override fun part1(lines: List<Operation>): String {
        val stacks = initialStacks.clone()
        lines.forEach { stacks.applyOperation9000(it) }
        return stacks.joinToString("") { it.removeLast() }
    }

    override fun part2(lines: List<Operation>): String {
        val stacks = initialStacks.clone()
        lines.forEach { stacks.applyOperation9001(it) }
        return stacks.joinToString("") { it.removeLast() }
    }
}