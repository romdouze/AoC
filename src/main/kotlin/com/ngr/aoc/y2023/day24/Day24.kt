package com.ngr.aoc.y2023.day24

import com.ngr.aoc.Day

class Day24 : Day<HailStone, Int, Int>() {


    override fun handleLine(lines: MutableList<HailStone>, line: String) {
        lines.add(HailStone.fromString(line))
    }

    override fun part1(lines: List<HailStone>): Int {
        val window = 200000000000000.0..400000000000000.0

        val allPairs = lines.flatMapIndexed { index, h ->
            lines.drop(index + 1).map { h to it }
        }

        return allPairs.count {
            it.first.intersection2DWith(it.second)
                ?.let { intersection ->
                    intersection.inWindow(xRange = window, yRange = window) &&
                            it.first.inFuture2D(intersection) &&
                            it.second.inFuture2D(intersection)
                } ?: false
        }
    }

    override fun part2(lines: List<HailStone>): Int {
        TODO("Not yet implemented")
    }
}