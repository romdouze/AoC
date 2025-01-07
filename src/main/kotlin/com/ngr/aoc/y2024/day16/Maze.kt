package com.ngr.aoc.y2024.day16

import com.ngr.aoc.y2024.day16.Dir.Companion.left
import com.ngr.aoc.y2024.day16.Dir.Companion.right
import com.ngr.aoc.y2024.day16.Dir.E
import com.ngr.aoc.y2024.day16.Step.FORWARD
import java.awt.Point

class Maze(
    private val walls: Set<Point>,
    private val start: Point,
    private val end: Point,
) {
    fun shortestPathToEndWithNormalFlood(): List<Dir> {
        val startPos = start
        val toVisit = ArrayDeque(listOf(startPos to E))
        val visited = mutableMapOf<Point, List<Dir>>(start to emptyList())

        while (toVisit.isNotEmpty() && !(visited.containsKey(end))) {
            val (currentPos, dir) = toVisit.removeFirst()
            val currentPath = visited[currentPos]!!
            if (currentPos != end) {
                Dir.entries.forEach {
                    val newPos = currentPos + it
                    val newPath = currentPath + it
                    if (!walls.contains(newPos) &&
                        !toVisit.contains(currentPos to it) &&
                        (!visited.containsKey(newPos) || visited[newPos]!!.score() > newPath.score())
                    ) {
                        toVisit.add(newPos to it)
                        visited[newPos] = newPath
                    }
                }
            }
        }

        return visited[end]!!
    }

    fun shortestPathToEnd(): Path {

        val startPos = start to E
        val toVisit = ArrayDeque(listOf(startPos))
        val visited = mutableMapOf<Pos, Path>(startPos to Path(startPos))

        while (toVisit.isNotEmpty()) {
            val currentPos = toVisit.removeFirst()

            if (currentPos.p != end) {
                Step.entries.forEach { newStep ->
                    val nextPos = newStep.applyTo(currentPos)
                    val newPath = visited[currentPos]!!.addStep(newStep, nextPos)
                    if (!walls.contains(nextPos.p) &&
                        !toVisit.contains(nextPos) &&
                        (!visited.containsKey(nextPos) || visited[nextPos]!!.score > newPath.score)
                    ) {
                        toVisit.add(nextPos)
                        visited[nextPos] = newPath
                    }
                }
            }
        }

        return visited.filter { it.key.first == end }
            .minBy { it.value.score }
            .value
    }

    fun bestPathsToEnd(): List<Path> {

        val bestPaths = mutableListOf<Path>()
        var bestScore = shortestPathToEnd().score

        val startPos = start to E
        val toVisit: ArrayDeque<Pair<Pos, Path>> = ArrayDeque(listOf(startPos to Path(startPos)))
        val visited = mutableSetOf<Pair<Pos, Path>>()


        while (toVisit.isNotEmpty()) {
            val (currentPos, currentPath) = toVisit.removeFirst()
            visited.add(currentPos to currentPath)

            if (currentPos.p == end) {
                if (currentPath.score < bestScore) {
                    bestPaths.clear()
                    bestPaths.add(currentPath)
                    bestScore = currentPath.score
                } else if (currentPath.score == bestScore) {
                    bestPaths.add(currentPath)
                }
            } else {
                val lastStep = currentPath.steps.lastOrNull()
                Step.entries.filter { it == FORWARD || lastStep?.isTurn != it.isTurn }
                    .forEach { newStep ->
                        val nextPos = newStep.applyTo(currentPos)
                        val newPath = currentPath.addStep(newStep, nextPos)
                        val posAndPath = nextPos to newPath
                        if (newPath.score <= bestScore &&
                            !walls.contains(nextPos.p) &&
                            !toVisit.contains(posAndPath) &&
                            !visited.contains(posAndPath) &&
                            !currentPath.alreadyVisited(nextPos)
                        ) {
                            toVisit.add(posAndPath)
                        }
                    }
            }
        }

        return bestPaths
    }
}

data class Path(
    val steps: List<Step> = emptyList(),
    val poses: List<Pos>,
) {
    constructor(start: Pos) : this(poses = listOf(start))

    val score = steps.sumOf { it.score }

    fun addStep(step: Step, pos: Pos) = Path(steps + step, poses + pos)

    fun alreadyVisited(pos: Pos) = poses.contains(pos) && poses.dropLast(1).none { it.p == pos.p }

    override fun equals(other: Any?) =
        other is Path && other.steps == steps

    override fun hashCode() = steps.hashCode()
}

fun List<Dir>.score() =
    count() + filterIndexed { i, d -> d != if (i > 1) this[i - 1] else E }.count() * 1000

enum class Step(val isTurn: Boolean, val score: Int, val applyTo: (Pos) -> Pos) {
    FORWARD(false, 1, { Pos(it.p + it.dir, it.dir) }),
    LEFT(true, 1000, { Pos(it.p, left(it.dir)) }),
    RIGHT(true, 1000, { Pos(it.p, right(it.dir)) }),
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

typealias Pos = Pair<Point, Dir>

val Pos.p: Point get() = first
val Pos.dir: Dir get() = second