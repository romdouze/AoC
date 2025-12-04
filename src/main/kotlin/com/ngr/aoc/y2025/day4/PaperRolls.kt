package com.ngr.aoc.y2025.day4

import java.awt.Point

enum class Dir(val dx: Int, val dy: Int) {
    N(0, -1),
    NE(1, -1),
    E(1, 0),
    SE(1, 1),
    S(0, 1),
    SW(-1, 1),
    W(-1, 0),
    NW(-1, -1),
}

fun Collection<Point>.allAccessible() =
    filter { pos ->
        pos.neighbors().filter { contains(it) }.size < 4
    }.toSet()


fun Point.neighbors() =
    Dir.entries.map { Point(x + it.dx, y + it.dy) }
        .filter { it.inside() }

fun Point.inside() =
    x in (0 until WIDTH) && y in (0 until HEIGHT)