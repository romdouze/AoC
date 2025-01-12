package com.ngr.aoc.y2024.day20

import java.awt.Point
import kotlin.math.abs

class CheatRacer(
    private val walls: Set<Point>,
    private val start: Point,
    private val end: Point,
) {
    private fun raceToEnd(): List<Point> {
        val visited = mutableMapOf(start to listOf(start))
        val toVisit = ArrayDeque(listOf(start))

        while (toVisit.isNotEmpty() && !visited.containsKey(end)) {
            val currentPos = toVisit.removeFirst()

            Dir.entries.forEach { dir ->
                val newPos = currentPos + dir
                if (!walls.contains(newPos) &&
                    !visited.containsKey(newPos) &&
                    !toVisit.contains(newPos)
                ) {
                    toVisit.add(newPos)
                    visited[newPos] = visited[currentPos]!! + newPos
                }
            }
        }

        return visited[end]!!
    }

    fun findCheats(cheatWindow: Int, minCheatSave: Int): Int {
        val path = raceToEnd()
            .withIndex()
            .associate { it.value to it.index }

        return path.entries.sumOf { start ->
            path.entries
                .count { end ->
                    val cheatLength = end.key.manhattanTo(start.key)
                    cheatLength <= cheatWindow && end.value - start.value - cheatLength >= minCheatSave
                }
        }
    }
}

enum class Dir(val dx: Int, val dy: Int) {
    E(1, 0),
    S(0, 1),
    W(-1, 0),
    N(0, -1),
}

operator fun Point.plus(dir: Dir) =
    Point(x + dir.dx, y + dir.dy)

fun Point.manhattanTo(other: Point) =
    abs(x - other.x) + abs(y - other.y)