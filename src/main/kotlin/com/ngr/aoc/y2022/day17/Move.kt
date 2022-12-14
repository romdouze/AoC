package com.ngr.aoc.y2022.day17

import java.awt.Point

enum class Move(
    val dx: Int, val dy: Int
) {
    L(-1, 0), R(1, 0), D(0, -1), U(0, 1);

    companion object {
        fun fromChar(c: Char) =
            when (c) {
                '<' -> L
                '>' -> R
                else -> throw IllegalArgumentException("Unexpected push: $c")
            }
    }

    operator fun times(n: Int) =
        Point(dx * n, dy * n)
}