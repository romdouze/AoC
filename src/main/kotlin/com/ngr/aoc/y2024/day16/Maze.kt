package com.ngr.aoc.y2024.day16

import com.ngr.aoc.y2024.day16.Dir.E
import com.ngr.aoc.y2024.day16.Step.FORWARD
import java.awt.Point

class Maze(
    private val walls: Set<Point>,
    private val start: Point,
    private val end: Point,
) {
    fun shortestPathsToEnd(): List<Path> {

        val startPos = start to E
        val toVisit = ArrayDeque(listOf(startPos))
        val visited = mutableMapOf<Pos, List<Path>>(startPos to listOf(Path(emptyList(), setOf(startPos))))

        while (toVisit.isNotEmpty()) {
            val currentPos = toVisit.removeFirst()
            val currentPaths = visited[currentPos]!!

            if (currentPos.p != end) {
                Step.entries.forEach { newStep ->
                    val nextPos = newStep.applyTo(currentPos)
                    val newPaths = currentPaths.map { it.addStep(newStep, nextPos) }
                    if (!walls.contains(nextPos.p) &&
                        !toVisit.contains(nextPos) &&
//                        (!visited.containsKey(nextPos) || visited[nextPos]!![0].score > newPaths[0].score)
                        currentPaths.none { it.alreadyVisited(nextPos) }
                    ) {
                        toVisit.add(nextPos)
                        visited[nextPos] = (visited[nextPos] ?: emptyList()) + newPaths
                    }
                }
            }
        }

        return visited.filter { it.key.first == end }
            .minBy { it.value[0].score }
            .value
    }
}

data class Path(
    val steps: List<Step>,
    val poses: Set<Pos>,
) {
    val score = steps.partition { it == FORWARD }
        .let {
            it.first.size + it.second.size * 1000
        }

    fun addStep(step: Step, pos: Pos) = Path(steps + step, poses + pos)

    fun alreadyVisited(pos: Pos) = poses.contains(pos)
}

enum class Step(val applyTo: (Pos) -> Pos) {
    FORWARD({ Pos(it.p + it.dir, it.dir) }),
    LEFT({ Pos(it.p, Dir.left(it.dir)) }),
    RIGHT({ Pos(it.p, Dir.right(it.dir)) }),
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