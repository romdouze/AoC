package com.ngr.aoc.y2024.day16

import com.ngr.aoc.y2024.day16.Dir.E
import java.awt.Point

class Maze(
    private val walls: Set<Point>,
    private val start: Point,
    private val end: Point,
) {
    fun allShortestPaths(): Map<Point, Path> {
        val startPos = start
        val toVisit = ArrayDeque(listOf(startPos))
        val visited = mutableMapOf(start to Path(start))

        while (toVisit.isNotEmpty()) {
            val currentPos = toVisit.removeFirst()
            val currentPath = visited[currentPos]!!
            if (currentPos != end) {
                Dir.entries.forEach {
                    val newPos = currentPos + it
                    val newPath = currentPath.addStep(it)
                    if (!walls.contains(newPos) &&
                        !toVisit.contains(currentPos) &&
                        (!visited.containsKey(newPos) || visited[newPos]!!.score() > newPath.score())
                    ) {
                        toVisit.add(newPos)
                        visited[newPos] = newPath
                    }
                }
            }
        }

        return visited
    }

    fun allBestPathsToEnd(): List<Path> {
        val allPaths = allShortestPaths()
        val firstBestPathToEnd = allPaths[end]!!
        val bestScore = firstBestPathToEnd.score()

        val allBestPathsToEnd = mutableSetOf(firstBestPathToEnd)

        val toVisit = ArrayDeque(listOf(firstBestPathToEnd to 0))

        while (toVisit.isNotEmpty()) {
            val (bestPathToExplore, skip) = toVisit.removeFirst()

            with(bestPathToExplore) {
                p.withIndex()
                    .drop(1)
                    .reversed()
                    .drop(if (skip > 0) skip - 1 else 0)
                    .forEach {
                        val (i, p) = it
                        val dir = dirs[i - 1]
                        Dir.entries.filter { it != dir }
                            .forEach { newDir ->
                                val newP = p - newDir
                                val alternatePartialPath = allPaths[newP]
                                if (alternatePartialPath != null) {
                                    val alternatePath = alternatePartialPath.addStep(newDir)
                                        .addSteps(dirs.subList(i, dirs.size))
                                    if (alternatePath.score() <= bestScore && allBestPathsToEnd.add(alternatePath)) {
                                        toVisit.add(alternatePath to i)
                                    }
                                }
                            }
                    }
            }
        }

        return allBestPathsToEnd.toList()
    }
}

data class Path(
    val dirs: List<Dir> = emptyList(),
    val p: List<Point>,
) {
    constructor(start: Point) : this(p = listOf(start))

    fun addStep(dir: Dir) = Path(dirs + dir, p + (p.last() + dir))

    fun addSteps(dirs: List<Dir>): Path {
        var newPath = Path(this.dirs, p)
        dirs.forEach { dir ->
            newPath = newPath.addStep(dir)
        }
        return newPath
    }

    fun score() =
        with(dirs) {
            count() + filterIndexed { i, d -> d != if (i > 0) this[i - 1] else E }.count() * 1000
        }
}

enum class Dir(val dx: Int, val dy: Int) {
    E(1, 0),
    S(0, 1),
    W(-1, 0),
    N(0, -1);

    companion object {
        fun left(from: Dir) =
            when (from) {
                E -> N
                S -> E
                W -> S
                N -> W
            }

        fun right(from: Dir) =
            when (from) {
                E -> S
                S -> W
                W -> N
                N -> E
            }
    }
}

operator fun Point.plus(dir: Dir) = Point(x + dir.dx, y + dir.dy)
operator fun Point.minus(dir: Dir) = Point(x - dir.dx, y - dir.dy)


typealias Pos = Pair<Point, Dir>

val Pos.p: Point get() = first
val Pos.dir: Dir get() = second