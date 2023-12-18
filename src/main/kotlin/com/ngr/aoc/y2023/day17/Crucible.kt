package com.ngr.aoc.y2023.day17

import java.awt.Point


data class State(
    val pos: Point,
    val dir: Dir,
    val path: List<Pair<Point, Dir>>,
    val heatLoss: Int,
) {

}

enum class Dir(
    val dx: Int,
    val dy: Int,
    val leftTurn: () -> Dir,
    val rightTurn: () -> Dir,
) {
    UP(
        dx = 0,
        dy = -1,
        leftTurn = { LEFT },
        rightTurn = { RIGHT },
    ),
    DOWN(
        dx = 0,
        dy = -1,
        leftTurn = { RIGHT },
        rightTurn = { LEFT },
    ),
    LEFT(
        dx = -1,
        dy = 0,
        leftTurn = { DOWN },
        rightTurn = { UP },
    ),
    RIGHT(
        dx = 1,
        dy = 0,
        leftTurn = { UP },
        rightTurn = { DOWN },
    ),
}

operator fun Point.plus(dir: Dir) =
    Point(x + dir.dx, y + dir.dy)
