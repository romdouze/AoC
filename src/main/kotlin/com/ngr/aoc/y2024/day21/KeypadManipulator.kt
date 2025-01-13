package com.ngr.aoc.y2024.day21

import com.ngr.aoc.y2024.day21.Key.`0`
import com.ngr.aoc.y2024.day21.Key.`1`
import com.ngr.aoc.y2024.day21.Key.`2`
import com.ngr.aoc.y2024.day21.Key.`3`
import com.ngr.aoc.y2024.day21.Key.`4`
import com.ngr.aoc.y2024.day21.Key.`5`
import com.ngr.aoc.y2024.day21.Key.`6`
import com.ngr.aoc.y2024.day21.Key.`7`
import com.ngr.aoc.y2024.day21.Key.`8`
import com.ngr.aoc.y2024.day21.Key.`9`
import com.ngr.aoc.y2024.day21.Key.`<`
import com.ngr.aoc.y2024.day21.Key.`>`
import com.ngr.aoc.y2024.day21.Key.A
import com.ngr.aoc.y2024.day21.Key.`^`
import com.ngr.aoc.y2024.day21.Key.v
import java.awt.Point

class KeypadManipulator(
    private val keypadType: KeypadType,
) {
    private val keypad = Keypad(keypadType)
    private var pos = keypad.

    fun inputForCode(code: String): List<Key> {

    }
}

class Keypad(
    private val type: KeypadType
) {

    fun posOf(key: Key): Point = type.keyPositions[key]
        ?: error("No key $key for keypad $type")
}

enum class KeypadType(
    val keyPositions: Map<Key, Point>,
    val keys: Set<Key> = keyPositions.keys,
    val poses: Set<Point> = keyPositions.values.toSet()
) {
    NUMERIC(
        mapOf(
            A to Point(2, 3),
            `0` to Point(1, 3),
            `1` to Point(0, 2),
            `2` to Point(1, 2),
            `3` to Point(2, 2),
            `4` to Point(0, 1),
            `5` to Point(1, 1),
            `6` to Point(2, 1),
            `7` to Point(0, 0),
            `8` to Point(0, 1),
            `9` to Point(0, 2),
        )
    ),
    DIRECTIONAL(
        mapOf(
            A to Point(2, 0),
            `>` to Point(2, 1),
            v to Point(1, 1),
            `<` to Point(0, 1),
            `^` to Point(1, 0),
        )
    ),
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
    `9`,
}