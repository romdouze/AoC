package com.ngr.aoc.y2023.day14

import com.ngr.aoc.Day
import java.awt.Point

class Day14 : Day<String, Int, Int>() {

    private val roundRocks = mutableSetOf<Point>()
    private val squareRocks = mutableSetOf<Point>()

    private var rows = 0
    private var cols = 0

    override fun handleLine(lines: MutableList<String>, line: String) {
        line.forEachIndexed { x, c ->
            when (c) {
                '#' -> squareRocks.add(Point(x, rows))
                'O' -> roundRocks.add(Point(x, rows))
            }
        }
        rows++
        cols = line.length
    }

    override fun part1(lines: List<String>) =
        tilt(roundRocks, Dir.NORTH).sumOf { rows - it.y }


    override fun part2(lines: List<String>): Int {
        val requiredCycles = 1000000000

        val cache = mutableMapOf<Set<Point>, Pair<Set<Point>, Int>>()

        var rocks = roundRocks.toSet()
        var cycles = 0
        while (cycles < requiredCycles && !cache.containsKey(rocks)) {
            var cycled = rocks
            Dir.values().forEach {
                cycled = tilt(cycled, it)
            }
            cache[rocks] = cycled to cycles
            rocks = cycled
            cycles++
        }

        if (cycles < requiredCycles) {
            val cachedCycle = cache[rocks]!!.second
            val loopSize = cycles - cachedCycle
            val remainingCycles = (requiredCycles - cycles) % loopSize

            repeat(remainingCycles) {
                rocks = cache[rocks]!!.first
            }
        }

        return rocks.sumOf { rows - it.y }
    }

    private fun tilt(roundRocks: Set<Point>, dir: Dir): MutableSet<Point> {
        val tilted = mutableSetOf<Point>()

        roundRocks.sortedWith(dir.comparator)
            .forEach { rock ->
                val slopeRange = dir.slopeRange(rock, rows, cols)
                slopeRange
                    .firstOrNull {
                        squareRocks.contains(it) || tilted.contains(it)
                    }
                    .also {
                        tilted.add(
                            it?.let { Point(it.x - dir.dx, it.y - dir.dy) } ?: slopeRange.last()
                        )
                    }
            }

        return tilted
    }

    enum class Dir(
        val comparator: Comparator<Point>,
        val slopeRange: (Point, Int, Int) -> List<Point>,
        val dx: Int,
        val dy: Int,
    ) {
        NORTH(
            Comparator.comparing { it.y },
            { rock, _, _ -> (0..rock.y).reversed().map { Point(rock.x, it) } },
            dx = 0,
            dy = -1,
        ),
        WEST(
            Comparator.comparing { it.x },
            { rock, _, _ -> (0..rock.x).reversed().map { Point(it, rock.y) } },
            dx = -1,
            dy = 0,
        ),
        SOUTH(
            Comparator.comparing<Point, Int> { it.y }.reversed(),
            { rock, rows, _ -> (rock.y until rows).map { Point(rock.x, it) } },
            dx = 0,
            dy = 1,
        ),
        EAST(
            Comparator.comparing<Point, Int> { it.x }.reversed(),
            { rock, _, cols -> (rock.x until cols).map { Point(it, rock.y) } },
            dx = 1,
            dy = 0,
        ),
    }
}
