package com.ngr.aoc.y2024.day21

import com.ngr.aoc.y2024.day21.KeypadType.NUMERIC
import com.ngr.aoc.y2024.day21.Move.A
import com.ngr.aoc.y2024.day21.Move.E
import com.ngr.aoc.y2024.day21.Move.N
import com.ngr.aoc.y2024.day21.Move.S
import com.ngr.aoc.y2024.day21.Move.W
import java.awt.Point
import java.util.concurrent.ConcurrentHashMap
import kotlin.math.abs

class KeypadChain(
    private val depth: Int,
) {
    fun shortestInputForCode(code: String) =
        KeypadOperator(NUMERIC).shortestInputForCode(code.toKeys()).let {
                var currentPaths = it
            repeat(depth) {
                    currentPaths = currentPaths
                        .let { KeypadOperator(KeypadType.DIRECTIONAL).shortestInputForCode(it.toKeys()) }
                }
                currentPaths
        }.size * code.dropLast(1).toInt()
}

class KeypadOperator(
    keypadType: KeypadType,
) {
    private val keypad = Keypad(keypadType)
    private var key = Key.A

    fun shortestInputForCode(code: List<Key>) =
        code.map { nextKey ->
            (keypad.bestPathTo(key, nextKey) + A)
                .also { key = nextKey }
        }.reduce { acc, list ->
            acc + list
        }
}

class Keypad(
    private val type: KeypadType
) {
    fun bestPathTo(from: Key, to: Key) =
        type.pathCache.computeIfAbsent(from) { ConcurrentHashMap() }
            .computeIfAbsent(to) {
                // Only for NUMERIC, DIRECTIONAL is prefilled

                if (from == to) return@computeIfAbsent emptyList()

                val bottomRow = listOf(Key.`0`, Key.A)
                val leftColumn = listOf(Key.`1`, Key.`4`, Key.`7`)
                val orderedMoves =
                    if (from in bottomRow && to in leftColumn || from in leftColumn && to in bottomRow)
                        listOf(N, E, S, W)
                    else listOf(W, S, N, E)

                val fromPos = posOf(from)
                val toPos = posOf(to)
                val dx = toPos.x - fromPos.x
                val dy = toPos.y - fromPos.y

                val moves = mutableListOf<Move>()

                orderedMoves.forEach {
                    when {
                        it == W && dx < 0 -> repeat(abs(dx)) { moves.add(W) }
                        it == E && dx > 0 -> repeat(abs(dx)) { moves.add(E) }
                        it == N && dy < 0 -> repeat(abs(dy)) { moves.add(N) }
                        it == S && dy > 0 -> repeat(abs(dy)) { moves.add(S) }
                    }
                }

                moves
            }

    private fun posOf(key: Key): Point = type.keyPositions[key]
        ?: error("No key $key for keypad $type")
}

enum class KeypadType(
    val keyPositions: Map<Key, Point>,
    val keys: Set<Key> = keyPositions.keys,
    pathCache: Map<Key, Map<Key, List<Move>>> = emptyMap(),
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
        keyPositions = mapOf(
            Key.A to Point(2, 0),
            Key.`>` to Point(2, 1),
            Key.v to Point(1, 1),
            Key.`<` to Point(0, 1),
            Key.`^` to Point(1, 0),
        ),
        pathCache = mapOf(
            Key.A to mapOf(
                Key.A to emptyList(),
                Key.`>` to listOf(S),
                Key.v to listOf(W, S),
                Key.`<` to listOf(S, W, W),
                Key.`^` to listOf(W),
            ),
            Key.`>` to mapOf(
                Key.A to listOf(N),
                Key.`>` to emptyList(),
                Key.v to listOf(W),
                Key.`<` to listOf(W, W),
                Key.`^` to listOf(W, N),
            ),
            Key.v to mapOf(
                Key.A to listOf(N, E),
                Key.`>` to listOf(E),
                Key.v to emptyList(),
                Key.`<` to listOf(W),
                Key.`^` to listOf(N),
            ),
            Key.`<` to mapOf(
                Key.A to listOf(E, E, N),
                Key.`>` to listOf(E, E),
                Key.v to listOf(E),
                Key.`<` to emptyList(),
                Key.`^` to listOf(E, N),
            ),
            Key.`^` to mapOf(
                Key.A to listOf(E),
                Key.`>` to listOf(S, E),
                Key.v to listOf(S),
                Key.`<` to listOf(S, W),
                Key.`^` to emptyList(),
            ),
        )
    );

    val pathCache = ConcurrentHashMap<Key, ConcurrentHashMap<Key, List<Move>>>()
        .also { cache ->
            pathCache.entries.forEach { entry ->
                cache.computeIfAbsent(entry.key) { ConcurrentHashMap() }
                entry.value.entries.forEach { moves ->
                    cache[entry.key]!!.computeIfAbsent(moves.key) { moves.value }
                }
            }
        }
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
    A(0, 0, Key.A)
}

fun List<Move>.toKeys() =
    map { it.key }

fun String.toKeys() =
    map { Key.fromString(it.toString()) }