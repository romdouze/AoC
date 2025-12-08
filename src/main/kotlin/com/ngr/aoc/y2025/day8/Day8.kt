package com.ngr.aoc.y2025.day8

import com.ngr.aoc.Day
import com.ngr.aoc.common.generateAllPairs

class Day8 : Day<Point3D, Int, Long>() {
    override fun handleLine(
        lines: MutableList<Point3D>,
        line: String
    ) {
        line.split(",").let {
            lines.add(Point3D(it[0].toDouble(), it[1].toDouble(), it[2].toDouble()))
        }
    }

    override fun part1(lines: List<Point3D>): Int {
        val groups = mutableListOf<MutableSet<Point3D>>()

        lines.generateAllPairs()
            .sortedBy { it.first.distanceTo(it.second) }
            .take(1000)
            .forEach { groupPair(groups, it) }

        return groups.sortedByDescending { it.size }
            .take(3)
            .fold(1) { acc, group -> acc * group.size }
    }

    override fun part2(lines: List<Point3D>): Long {
        val groups = mutableListOf<MutableSet<Point3D>>()

        val allPairs = ArrayDeque(
            lines.generateAllPairs()
                .sortedBy { it.first.distanceTo(it.second) }
        )
        lateinit var pair: Pair<Point3D, Point3D>

        while (groups.size != 1 || groups[0].size < lines.size) {
            pair = allPairs.removeFirst()

            groupPair(groups, pair)
        }

        return pair.first.x.toLong() * pair.second.x.toLong()
    }
}