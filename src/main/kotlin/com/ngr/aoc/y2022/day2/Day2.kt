package com.ngr.aoc.y2022.day2

import com.ngr.aoc.Day

class Day2 : Day<String, Int, Int>() {

    override fun handleLine(lines: MutableList<String>, line: String) {
        lines.add(line)
    }

    override fun part1(lines: List<String>) =
        lines.sumOf {
            getDuel(it)
                .score()
        }

    override fun part2(lines: List<String>) =
        lines.sumOf {
            buildDuel(it)
                .score()
        }

    private fun buildDuel(line: String): Duel =
        line.split(" ")
            .let {
                val first = Shape.fromSymbol(it[0])
                Duel(first, first.getOutcome(Outcome.fromSymbol(it[1])))
            }

    private fun getDuel(line: String): Duel =
        line.split(" ")
            .map { Shape.fromSymbol(it) }
            .zipWithNext()
            .first()

}