package com.ngr.aoc.y2022.day9

import com.ngr.aoc.Day
import java.awt.Point

class Day9 : Day<Motion, Int, Int>() {
    override fun handleLine(lines: MutableList<Motion>, line: String) {
        line.split(" ")
            .let {
                lines.add(Motion(Dir.fromString(it[0]), it[1].toInt()))
            }
    }

    override fun part1(lines: List<Motion>) =
        walkRope(Rope(2), lines).size

    override fun part2(lines: List<Motion>) =
        walkRope(Rope(10), lines).size

    private fun walkRope(rope: Rope, motions: List<Motion>): Set<Point> {
        val visitedByTail = mutableSetOf(Point(rope.tail))

        motions.forEach { motion ->
            repeat(motion.length) {
                rope.moveHead(motion.dir)
                visitedByTail.add(Point(rope.tail))
            }
        }

        return visitedByTail
    }
}