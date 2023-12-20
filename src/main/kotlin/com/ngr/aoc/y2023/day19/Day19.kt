package com.ngr.aoc.y2023.day19

import com.ngr.aoc.Day
import java.io.InputStream

class Day19 : Day<Item, Int, Long>() {

    private val workflows = mutableMapOf<String, Workflow>()

    override fun readInput(data: InputStream): List<Item> {
        val items = mutableListOf<Item>()
        data.bufferedReader().also { reader ->
            var line = reader.readLine()
            do {
                Workflow.fromString(line).also {
                    workflows[it.name] = it
                }

                line = reader.readLine()
            } while (line.isNotEmpty())

            line = reader.readLine()
            do {
                items.add(Item.fromString(line))

                line = reader.readLine()
            } while (!line.isNullOrEmpty())
        }
        return items
    }

    override fun handleLine(lines: MutableList<Item>, line: String) {
        // DO NOTHING
    }

    override fun part1(lines: List<Item>) =
        lines.filter { workflows["in"]!!.apply(it, workflows) }
            .sumOf { it.x + it.m + it.a + it.s }

    override fun part2(lines: List<Item>): Long {
        val baseRange = (1..4000)
        val startRange = ItemRange(
            baseRange,
            baseRange,
            baseRange,
            baseRange,
        )

        val itemRanges = workflows["in"]!!.apply(startRange, workflows)

        return itemRanges.filter { it.second }
            .map { it.first }
            .sumOf {
                it.xRange.count().toLong() * it.mRange.count() * it.aRange.count() * it.sRange.count()
            }
    }
}