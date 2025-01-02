package com.ngr.aoc.y2024.day12

import java.awt.Point

class Gardener(
    private val garden: Map<Point, Char>
) {

    private val gardenGroups: List<Pair<Set<Point>, List<Pair<Point, Dir>>>>

    init {
        val groups = mutableListOf<Pair<Set<Point>, List<Pair<Point, Dir>>>>()
        val remaining = ArrayDeque(garden.keys)
        while (remaining.isNotEmpty()) {
            val firstPlotInGroup = remaining.removeFirst()
            val groupType = garden[firstPlotInGroup]!!
            val group = mutableSetOf<Point>()
            val perimeter = mutableListOf<Pair<Point, Dir>>()
            val toVisit = ArrayDeque(listOf(firstPlotInGroup))
            while (toVisit.isNotEmpty()) {
                val plot = toVisit.removeFirst()
                group.add(plot)
                remaining.remove(plot)
                Dir.entries
                    .map { (plot + it) to it }
                    .partition {
                        val plot = it.first
                        remaining.contains(plot) && garden[plot] == groupType && !(toVisit.contains(plot))
                    }.also {
                        it.first.forEach {
                            toVisit.add(it.first)
                        }
                        val outside = it.second.filter {
                            garden[it.first] != groupType || !(it.first.inside())
                        }
                        perimeter.addAll(outside.map { it })
                    }
            }
            groups.add(group.toSet() to perimeter)
        }
        gardenGroups = groups
    }

    fun price() =
        gardenGroups.sumOf { it.first.size * it.second.size }

    fun discountedPrice() =
        gardenGroups.sumOf { pair ->
            val group = pair.first
            val perimeter = pair.second
            var sides = 0

            val remainingPerimeter = ArrayDeque(perimeter)
            while (remainingPerimeter.isNotEmpty()) {
                val (perimeterStart, perimeterDir) = remainingPerimeter.removeFirst()
                val sideDirs = Dir.sideDirs(perimeterDir)
                sideDirs.forEach { sideDir ->
                    var sidePlot = perimeterStart + sideDir
                    while (remainingPerimeter.remove(sidePlot to perimeterDir)) {
                        sidePlot = sidePlot + sideDir
                    }
                }
                sides++
            }
            sides * group.size
        }
}

enum class Dir(val dx: Int, val dy: Int) {
    E(1, 0),
    S(0, 1),
    W(-1, 0),
    N(0, -1);

    companion object {
        fun sideDirs(dir: Dir) =
            when (dir) {
                E, W -> listOf(N, S)
                S, N -> listOf(E, W)
            }
    }
}

operator fun Point.plus(dir: Dir) =
    Point(x + dir.dx, y + dir.dy)

fun Point.inside() =
    (0 until WIDTH).contains(x) && (0 until HEIGHT).contains(y)