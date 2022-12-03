package com.ngr.aoc.y2022


class Day3 : Day<String, Int, Int>() {
    override fun handleLine(lines: MutableList<String>, line: String) {
        lines.add(line)
    }

    override fun part1(lines: List<String>) =
        lines
            .map {
                val mid = it.length / 2
                it.substring(0 until mid) to it.substring(mid)
            }.sumOf {
                it.first.intersect(it.second)
                    .first()
                    .score()
            }

    override fun part2(lines: List<String>) =
        lines.chunked(3)
            .sumOf {
                it[0].intersect(it[1]).toString()
                    .intersect(it[2])
                    .first()
                    .score()
            }

    private fun String.intersect(other: String) =
        toCharArray().intersect(other.asIterable().toSet())

    private fun Char.score() =
        if (isLowerCase()) {
            this - 'a' + 1
        } else {
            this - 'A' + 27
        }
}