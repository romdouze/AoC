package com.ngr.aoc.y2024.day21

import com.ngr.aoc.y2024.day21.Key.A
import java.awt.Point

class KeypadOperator(
    keypadType: KeypadType,
) {
    private val keypad = Keypad(keypadType)
    private var key = A

    fun possibleInputsForCode(code: List<Key>) =
        code.flatMap { nextKey ->
            (keypad.allPathsTo(key, nextKey).map { it + Move.A })
                .also { key = nextKey }
        }
}

class Keypad(
    private val type: KeypadType
) {

    fun allPathsTo(from: Key, to: Key) =
        type.pathCache.computeIfAbsent(from) { mutableMapOf() }
            .computeIfAbsent(to) {
                computeAllPathsTo(from, to)
            }

    private fun computeAllPathsTo(from: Key, to: Key): List<List<Move>> {
        val fromPos = posOf(from)
        val toPos = posOf(to)
        val visited = mutableMapOf(fromPos to mutableListOf(emptyList<Move>()))
        val toVisit = ArrayDeque(listOf(fromPos))

        while (toVisit.isNotEmpty() && !visited.containsKey(toPos)) {
            val currentPos = toVisit.removeFirst()

            Move.moves.forEach { move ->
                val newPos = currentPos + move
                if (type.poses.contains(newPos) &&
                    !visited.containsKey(newPos) &&
                    !toVisit.contains(newPos)
                ) {
                    toVisit.add(newPos)
                    visited.computeIfAbsent(newPos) { mutableListOf() }
                        .addAll(visited[currentPos]!!.map { it + move })
                }
            }
        }

        return visited[toPos]!!
    }

    private fun posOf(key: Key): Point = type.keyPositions[key]
        ?: error("No key $key for keypad $type")
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
            Key.`8` to Point(0, 1),
            Key.`9` to Point(0, 2),
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

operator fun Point.plus(move: Move) =
    Point(x + move.dx, y + move.dy)

fun List<Move>.toKeys() =
    map { it.key }

fun String.toKeys() =
    map { Key.fromString(it.toString()) }