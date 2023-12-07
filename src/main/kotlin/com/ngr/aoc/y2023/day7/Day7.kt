package com.ngr.aoc.y2023.day7

import com.ngr.aoc.Day
import com.ngr.aoc.y2023.day7.Hand.Companion.comparatorWithJoker

class Day7 : Day<Hand, Long, Long>() {
    override fun handleLine(lines: MutableList<Hand>, line: String) {
        line.split(" ").also {
            lines.add(Hand(it[0], it[1].toLong()))
        }
    }

    override fun part1(lines: List<Hand>) =
        lines.sorted()
            .foldIndexed(0L) { rank, sum, hand ->
                sum + hand.bid * (rank + 1)
            }

    override fun part2(lines: List<Hand>) =
        lines.sortedWith(comparatorWithJoker)
            .foldIndexed(0L) { rank, sum, hand ->
                sum + hand.bid * (rank + 1)
            }
}