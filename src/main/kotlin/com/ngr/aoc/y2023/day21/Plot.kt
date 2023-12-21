package com.ngr.aoc.y2023.day21

import java.awt.Point


operator fun Point.plus(dir: Dir) =
    Point(x + dir.dx, y + dir.dy)

enum class Dir(val dx: Int, val dy: Int) {
    N(0, -1),
    S(0, 1),
    W(-1, 0),
    E(1, 0),
}