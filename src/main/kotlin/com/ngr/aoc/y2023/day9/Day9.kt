package com.ngr.aoc.y2023.day9

import com.ngr.aoc.Day

class Day9 : Day<List<Int>, Long, Long>() {
    override fun handleLine(lines: MutableList<List<Int>>, line: String) {
        lines.add(line.split(" ").map { it.toInt() })
    }

    override fun part1(lines: List<List<Int>>) =
        lines.sumOf { row ->
            val diffs = mutableListOf(row.toMutableList())
            do {
                diffs.add(
                    diffs.last().indices.drop(1).map {
                        diffs.last()[it] - diffs.last()[it - 1]
                    }.toMutableList()
                )
            } while (diffs.last().any { it != 0 })

            diffs.indices.reversed().drop(1)
                .forEach {
                    diffs[it].add(diffs[it].last() + diffs[it + 1].last())
                }

            diffs[0].last().toLong()
        }

    override fun part2(lines: List<List<Int>>) =
        lines.sumOf { row ->
            val diffs = mutableListOf(row.toMutableList())
            do {
                diffs.add(
                    diffs.last().indices.drop(1).map {
                        diffs.last()[it] - diffs.last()[it - 1]
                    }.toMutableList()
                )
            } while (diffs.last().any { it != 0 })

            diffs.indices.reversed().drop(1)
                .forEach {
                    diffs[it].add(0, diffs[it].first() - diffs[it + 1].first())
                }

            diffs[0].first().toLong()
        }
}