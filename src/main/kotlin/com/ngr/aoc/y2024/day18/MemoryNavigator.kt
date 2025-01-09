package com.ngr.aoc.y2024.day18

import java.awt.Point

const val WIDTH = 71
const val HEIGHT = 71

class MemoryNavigator(
    private val walls: List<Point>
) {
    private val start = Point(0, 0)
    private val end = Point(WIDTH - 1, HEIGHT - 1)

    fun navigateAfterN(n: Int): List<Point>? {
        val walls = walls.take(n)

        val visited = mutableMapOf(start to emptyList<Point>())
        val toVisit = ArrayDeque(listOf(start))

        while (toVisit.isNotEmpty() && !visited.containsKey(end)) {
            val currentPos = toVisit.removeFirst()

            Dir.entries.forEach { dir ->
                val newPos = currentPos + dir
                if (newPos.inside() &&
                    !walls.contains(newPos) &&
                    !visited.containsKey(newPos) &&
                    !toVisit.contains(newPos)
                ) {
                    toVisit.add(newPos)
                    visited[newPos] = visited[currentPos]!! + newPos
                }
            }
        }

        return visited[end]
    }

    fun pathBlockedBy() =
        walls.size.downTo(1024)
            .first { navigateAfterN(it) != null }
            .let { walls[it] }
}

enum class Dir(val dx: Int, val dy: Int) {
    E(1, 0),
    S(0, 1),
    W(-1, 0),
    N(0, -1),
}

fun Point.inside() =
    (0 until WIDTH).contains(x) && (0 until HEIGHT).contains(y)

operator fun Point.plus(dir: Dir) =
    Point(x + dir.dx, y + dir.dy)