package com.ngr.aoc.y2024.day10

import java.awt.Point

class MapWalker(
    private val map: Map<Point, Int>
) {

    companion object {
        private const val MAX_HEIGHT = 9
    }

    fun scoreTrailheads(trailheads: List<Point>) =
        trailheads.sumOf {
            hikesFrom(it).toSet().count()
        }

    fun scoreTrailheadsWithRating(trailheads: List<Point>) =
        trailheads.sumOf {
            hikesFrom(it).count()
        }

    private fun hikesFrom(trailhead: Point): List<Point> {
        val hikeEnds = mutableListOf<Point>()

        val toVisit = ArrayDeque(listOf(trailhead))

        while (toVisit.isNotEmpty()) {
            val pos = toVisit.removeFirst()
            val height = map[pos]!!

            if (height == MAX_HEIGHT) {
                hikeEnds.add(pos)
            }

            toVisit.addAll(
                Dir.entries.map { pos + it }
                    .filter { map[it] == height + 1 }
            )
        }

        return hikeEnds
    }
}

enum class Dir(val dx: Int, val dy: Int) {
    N(0, -1),
    E(1, 0),
    S(0, 1),
    W(-1, 0),
}

operator fun Point.plus(dir: Dir) = Point(x + dir.dx, y + dir.dy)