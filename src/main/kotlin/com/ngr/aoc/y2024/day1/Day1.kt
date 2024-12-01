package com.ngr.aoc.y2024.day1

import com.ngr.aoc.Day
import kotlin.math.abs

class Day1 : Day<Int, Int, Int>() {

    private val inputLists: Pair<MutableList<Int>, MutableList<Int>> = Pair(mutableListOf(), mutableListOf())

    override fun handleLine(lines: MutableList<Int>, line: String) {
        line.split("\\s+".toRegex())
            .also {
                inputLists.first.add(it[0].toInt())
                inputLists.second.add(it[1].toInt())
            }
    }

    override fun part1(lines: List<Int>) =
        inputLists.first.sorted()
            .zip(inputLists.second.sorted())
            .sumOf { abs(it.first - it.second) }

    override fun part2(lines: List<Int>) =
        inputLists.first.sumOf { first ->
            first * inputLists.second.count { it == first }
        }
}