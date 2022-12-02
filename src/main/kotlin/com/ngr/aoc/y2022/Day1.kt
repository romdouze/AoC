package com.ngr.aoc.y2022

import java.io.InputStream

class Day1 : Day<List<Int>, Int, Int>() {

    override fun readInput(data: InputStream): List<List<Int>> {
        val splitted = mutableListOf(mutableListOf<Int>())
        data.bufferedReader().forEachLine {
            if (it.isBlank()) {
                splitted.add(mutableListOf())
            } else {
                splitted.last().add(Integer.valueOf(it))
            }
        }

        return splitted
    }

    override fun part1(lines: List<List<Int>>) =
        lines.maxBy { it.sum() }.sum()

    override fun part2(lines: List<List<Int>>) =
        lines.sortedByDescending { it.sum() }
            .take(3).sumOf { it.sum() }
}