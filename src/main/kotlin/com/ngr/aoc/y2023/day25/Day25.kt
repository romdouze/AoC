package com.ngr.aoc.y2023.day25

import com.ngr.aoc.Day

class Day25 : Day<Wire, Int, Int>() {


    override fun handleLine(lines: MutableList<Wire>, line: String) {
        line.split(": ").let {
            val name = it[0]
            val others = it[1].split(" ")

            others.forEach {
                lines.add(Wire(name, it))
            }
        }
    }

    override fun part1(lines: List<Wire>) =
        Graph(lines.toSet())
            .let {
                Graph(
                    (lines - setOf(
                        it.edge("lxt", "lsv")!!,
                        it.edge("qmr", "ptj")!!,
                        it.edge("xvh", "dhn")!!
                    )).toSet()
                ).connectivity()
                    .let { it[0].size * it[1].size }
            }

    override fun part2(lines: List<Wire>): Int {
        TODO("Not yet implemented")
    }
}