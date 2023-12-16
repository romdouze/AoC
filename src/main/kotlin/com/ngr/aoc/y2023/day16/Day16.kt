package com.ngr.aoc.y2023.day16

import com.ngr.aoc.Day
import com.ngr.aoc.y2023.day16.Dir.DOWN
import com.ngr.aoc.y2023.day16.Dir.LEFT
import com.ngr.aoc.y2023.day16.Dir.RIGHT
import com.ngr.aoc.y2023.day16.Dir.UP
import java.awt.Point
import kotlin.math.max

class Day16 : Day<String, Int, Int>() {

    private val mirrors = mutableMapOf<Point, Mirror>()

    private var rows = 0
    private var cols = 0
    override fun handleLine(lines: MutableList<String>, line: String) {
        line.forEachIndexed { x, c -> Mirror.fromChar(c)?.also { mirrors[Point(x, rows)] = it } }

        cols = line.length
        rows++
    }

    override fun part1(lines: List<String>): Int {
        return energize(Point(-1, 0) to RIGHT)
    }

    override fun part2(lines: List<String>) =
        max(
            (0..cols).maxOf {
                max(
                    energize(Point(it, -1) to DOWN),
                    energize(Point(it, rows) to UP)
                )
            },
            (0..rows).maxOf {
                max(
                    energize(Point(-1, it) to RIGHT),
                    energize(Point(cols, it) to LEFT)
                )
            }
        )

    private fun energize(start: Pair<Point, Dir>): Int {
        val energized = mutableSetOf<Point>()
        val visited = mutableSetOf<Pair<Point, Dir>>()

        var current = listOf(start)

        while (current.isNotEmpty()) {
            current = current.map { beam ->
                energized.add(beam.first)
                visited.add(beam)
                val inDir = beam.second
                val point = beam.first + inDir
                val outDirs = mirrors[point]?.outDir?.invoke(inDir)
                (outDirs?.map { point to it } ?: listOf(point to inDir))
                    .filterNot { visited.contains(it) }
                    .filter { it.first.inBound() }
            }.flatten()
        }

        return energized.filter { it.inBound() }.size
    }

    private fun Point.inBound() =
        (0 until rows).contains(y) &&
                (0 until cols).contains(x)
}
