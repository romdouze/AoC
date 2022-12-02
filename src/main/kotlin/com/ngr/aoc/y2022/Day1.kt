package com.ngr.aoc.y2022

class Day1 {

    companion object {
        const val PATH = "/input/2022/"
        private const val DAY = 1
        private const val INPUT = "input-$DAY-<part>.txt"
    }

    fun part1() {
        val lines = readInput(PATH + INPUT.replace("<part>", "1"))

        println(lines.maxBy { it.sum() }.sum())
    }

    fun part2() {
        val lines = readInput(PATH + INPUT.replace("<part>", "1"))

        println(
            lines.sortedByDescending { it.sum() }
                .take(3).sumOf { it.sum() }
        )
    }

    private fun readInput(input: String): MutableList<MutableList<Int>> {
        val splitted = mutableListOf(mutableListOf<Int>())
        object {}.javaClass.getResourceAsStream(input)?.bufferedReader()?.forEachLine {
            if (it.isBlank()) {
                splitted.add(mutableListOf())
            } else {
                splitted.last().add(Integer.valueOf(it))
            }
        }

        return splitted
    }
}