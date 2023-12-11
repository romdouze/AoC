package com.ngr.aoc.y2023.day10

import com.ngr.aoc.Day
import com.ngr.aoc.y2023.day10.Dir.LEFT
import com.ngr.aoc.y2023.day10.Dir.RIGHT
import java.awt.Point


class Day10 : Day<String, Int, Int>() {

    private val map = mutableMapOf<Point, PipeBit>()

    private var rows = 0
    private var cols = 0
    private lateinit var start: Point

    override fun handleLine(lines: MutableList<String>, line: String) {
        line.forEachIndexed { x, c ->
            if (c == 'S') {
                start = Point(x, rows)
            } else if (c != '.') {
                map[Point(x, rows)] = PipeBit.fromChar(c)
            }
        }
        cols = line.length
        rows++
    }

    override fun part1(lines: List<String>): Int {
        val loop = buildLoop()

        return loop.size / 2 + 1
    }

    override fun part2(lines: List<String>): Int {
        val loop = buildLoop()
        val loopPoints = loop.map { it.point }.toSet()

        val inside = (-1..cols).flatMap { x ->
            (-1..rows).map { y ->
                Point(x, y)
            }
        }.minus(loopPoints).toMutableSet()

        val queue = ArrayDeque(listOf(Point(-1, -1)))

        val loopDirection = loop.filter { it.turn != null }.groupBy { it.turn!! }
            .maxBy { it.value.size }.key
        val sidesToVisit = when (loopDirection) {
            RIGHT -> loop.flatMap { it.sides.second }.toSet()
            LEFT -> loop.flatMap { it.sides.first }.toSet()
            else -> throw IllegalArgumentException("Loop should not be turning $loopDirection")
        }.filter { !loopPoints.contains(it) }.toSet()

        inside.remove(start)
        inside.removeAll(sidesToVisit)
        queue.addAll(sidesToVisit)

        while (queue.isNotEmpty()) {
            val currentPoint = queue.removeFirst()

            (-1..1).forEach { dx ->
                (-1..1).forEach { dy ->
                    if (!(dx == 0 && dy == 0)) {
                        val neighbour = currentPoint.plus(dx, dy)
                        if (inside.remove(neighbour) && !queue.contains(neighbour)) {
                            queue.add(neighbour)
                        }
                    }
                }
            }
        }

        return inside.size
    }

    private fun buildLoop(): Set<LoopItem> {
        val loop = mutableSetOf<LoopItem>()
        val startingDirs = Dir.values().filter { dir ->
            map[start + dir]?.let { it.outDir(dir) != null } ?: false
        }

        var currentPoint = (start + startingDirs[0]) to startingDirs[0]
        do {
            val (point, dir) = currentPoint
            val pipeBit = map[point]!!
            loop.add(LoopItem(point, pipeBit.sides(dir, point), pipeBit.turn(dir)))
            val outDir = pipeBit.outDir(dir)!!
            currentPoint = (point + outDir) to outDir
        } while (currentPoint.first != start)

        return loop
    }
}