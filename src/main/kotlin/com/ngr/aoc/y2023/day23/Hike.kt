package com.ngr.aoc.y2023.day23

import java.awt.Point


class LongestPathSolver(
    private val start: Point,
    private val end: Point,
    private val map: Map<Point, Tile>,
    private val availableDirs: (Tile) -> List<Dir>
) {
    private val visited = mutableSetOf<Point>()

    fun longestPath() =
        longestPath(start)

    private fun longestPath(
        start: Point,
    ): List<Point> {
        if (map[start] == null || visited.contains(start)) {
            return emptyList()
        }
        if (start == end) {
            return listOf(start)
        }
        visited.add(start)
        var path = listOf<Point>()
        availableDirs(map[start]!!)
            .forEach { dir ->
                val newStart = start + dir
                longestPath(newStart)
                    .also {
                        if (it.size > path.size) {
                            path = listOf(start) + it
                        }
                    }
            }
        visited.remove(start)

        return path
    }
}

enum class Tile(val dirs: List<Dir>, val char: Char) {
    `_`(Dir.values().toList(), '.'),
    L(listOf(Dir.W), '<'),
    U(listOf(Dir.N), '^'),
    R(listOf(Dir.E), '>'),
    D(listOf(Dir.S), 'v');

    companion object {
        fun fromChar(char: Char) =
            values().firstOrNull { it.char == char }
    }
}

enum class Dir(val dx: Int, val dy: Int) {
    N(0, -1),
    S(0, 1),
    W(-1, 0),
    E(1, 0),
}

operator fun Point.plus(dir: Dir) =
    Point(x + dir.dx, y + dir.dy)