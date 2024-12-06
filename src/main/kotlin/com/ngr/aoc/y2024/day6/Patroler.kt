package com.ngr.aoc.y2024.day6

import com.ngr.aoc.y2024.day6.Dir.N
import java.awt.Point

class Patroller(
    val obstacles: Set<Point>,
    val start: Point,
    val width: Int,
    val height: Int
) {
    private val widthRange = (0 until width)
    private val heightRange = (0 until height)

    fun allVisitedPos(): Set<Point> {
        var visited = mutableSetOf<Point>()
        var pos = start
        var dir = N

        do {
            visited.add(pos)
            while (obstacles.contains(pos + dir)) {
                dir = dir.turnRight()
            }
            pos += dir
        } while (pos.inside())

        return visited
    }

    fun hasLoop(): Boolean {
        var visited = mutableSetOf<Pair<Point, Dir>>()
        var pos = start
        var dir = N

        do {
            visited.add(pos to dir)
            while (obstacles.contains(pos + dir)) {
                dir = dir.turnRight()
            }
            pos += dir
        } while (pos.inside() && !visited.contains(pos to dir))

        return pos.inside()
    }

    private fun Point.inside() =
        widthRange.contains(x) && heightRange.contains(y)
}

enum class Dir(val dx: Int, val dy: Int) {
    N(0, -1),
    E(1, 0),
    S(0, 1),
    W(-1, 0);

    fun turnRight() = when (this) {
        N -> E
        E -> S
        S -> W
        W -> N
    }
}

operator fun Point.plus(dir: Dir) = Point(x + dir.dx, y + dir.dy)