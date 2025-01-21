package com.ngr.aoc.y2024.day21

import com.ngr.aoc.y2024.day21.Key.A
import java.awt.Point

class KeypadOperator(
    keypadType: KeypadType,
) {
    private val keypad = Keypad(keypadType)
    private var key = A

    fun allShortestInputsForCode(code: List<Key>) =
        code.map { nextKey ->
            (keypad.allBestPathsTo(key, nextKey).map { it + Move.A })
                .also { key = nextKey }
        }.reduce { acc, lists ->
            acc.flatMap { path -> lists.map { path + it } }
        }
}

class Keypad(
    private val type: KeypadType
) {
    fun allBestPathsTo(from: Key, to: Key) =
        type.pathCache.computeIfAbsent(from) { mutableMapOf() }
            .computeIfAbsent(to) {
                allShortestPathsTo(from, to)
            }

    private fun allShortestPathsTo(from: Key, to: Key): List<List<Move>> {
        val toPos = posOf(to)
        val allPaths = allShortestPaths(from, to)
        val firstBestPathToEnd = allPaths[toPos]!!
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
                        val move = moves[i - 1]
                        Move.moves.filter { it != move }
                            .forEach { newMove ->
                                val newP = p - newMove
                                val alternatePartialPath = allPaths[newP]
                                if (alternatePartialPath != null) {
                                    val alternatePath = alternatePartialPath.addStep(newMove)
                                        .addSteps(moves.subList(i, moves.size))
                                    if (alternatePath.score() <= bestScore && allBestPathsToEnd.add(alternatePath)) {
                                        toVisit.add(alternatePath to i - 1)
                                    }
                                }
                            }
                    }
            }
        }

        return allBestPathsToEnd.map { it.moves }
    }

    private fun allShortestPaths(from: Key, to: Key): Map<Point, Path> {
        val fromPos = posOf(from)
        val toPos = posOf(to)
        val toVisit = ArrayDeque(listOf(fromPos))
        val visited = mutableMapOf(fromPos to Path(fromPos))

        while (toVisit.isNotEmpty()) {
            val currentPos = toVisit.removeFirst()
            val currentPath = visited[currentPos]!!
            if (currentPos != toPos) {
                Move.moves.forEach {
                    val newPos = currentPos + it
                    val newPath = currentPath.addStep(it)
                    if (
                        type.poses.contains(newPos) &&
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

    private fun posOf(key: Key): Point = type.keyPositions[key]
        ?: error("No key $key for keypad $type")
}

data class Path(
    val moves: List<Move> = emptyList(),
    val p: List<Point>,
) {
    constructor(start: Point) : this(p = listOf(start))

    fun addStep(move: Move) = Path(moves + move, p + (p.last() + move))

    fun addSteps(moves: List<Move>): Path {
        var newPath = Path(this.moves, p)
        moves.forEach { move ->
            newPath = newPath.addStep(move)
        }
        return newPath
    }

    fun score() = moves.count()

    override fun equals(other: Any?) =
        other is Path && other.moves == moves

    override fun hashCode() =
        moves.hashCode()
}

enum class KeypadType(
    val keyPositions: Map<Key, Point>,
    val keys: Set<Key> = keyPositions.keys,
    val poses: Set<Point> = keyPositions.values.toSet()
) {
    NUMERIC(
        mapOf(
            Key.A to Point(2, 3),
            Key.`0` to Point(1, 3),
            Key.`1` to Point(0, 2),
            Key.`2` to Point(1, 2),
            Key.`3` to Point(2, 2),
            Key.`4` to Point(0, 1),
            Key.`5` to Point(1, 1),
            Key.`6` to Point(2, 1),
            Key.`7` to Point(0, 0),
            Key.`8` to Point(1, 0),
            Key.`9` to Point(2, 0),
        )
    ),
    DIRECTIONAL(
        mapOf(
            Key.A to Point(2, 0),
            Key.`>` to Point(2, 1),
            Key.v to Point(1, 1),
            Key.`<` to Point(0, 1),
            Key.`^` to Point(1, 0),
        )
    );

    val pathCache = mutableMapOf<Key, MutableMap<Key, List<List<Move>>>>()
}

enum class Key {
    `>`,
    v,
    `<`,
    `^`,
    A,
    `0`,
    `1`,
    `2`,
    `3`,
    `4`,
    `5`,
    `6`,
    `7`,
    `8`,
    `9`;

    companion object {
        fun fromString(key: String) =
            entries.first { it.name == key }
    }
}

enum class Move(val dx: Int, val dy: Int, val key: Key) {
    E(1, 0, Key.`>`),
    S(0, 1, Key.v),
    W(-1, 0, Key.`<`),
    N(0, -1, Key.`^`),
    A(0, 0, Key.A);

    companion object {
        val moves = entries.minus(A)
    }
}

operator fun Point.plus(move: Move) = Point(x + move.dx, y + move.dy)
operator fun Point.minus(move: Move) = Point(x - move.dx, y - move.dy)

fun List<Move>.toKeys() =
    map { it.key }

fun String.toKeys() =
    map { Key.fromString(it.toString()) }