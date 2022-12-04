package com.ngr.aoc.y2022

class Day4 : Day<Pair<IntRange, IntRange>, Int, Int>() {
    override fun handleLine(lines: MutableList<Pair<IntRange, IntRange>>, line: String) {
        lines.add(
            line.split(",")
                .map {
                    val split = it.split("-")
                    (split[0].toInt()..split[1].toInt())
                }
                .zipWithNext()
                .first()
        )
    }

    override fun part1(lines: List<Pair<IntRange, IntRange>>) =
        lines.count {
            it.first.isFullyContainedIn(it.second) || it.second.isFullyContainedIn(it.first)
        }

    override fun part2(lines: List<Pair<IntRange, IntRange>>) =
        lines.count {
            it.first.overlapsWith(it.second)
        }

    private fun IntRange.isFullyContainedIn(other: IntRange) =
        first >= other.first && last <= other.last

    private fun IntRange.overlapsWith(other: IntRange) =
        first <= other.last && last >= other.first
}