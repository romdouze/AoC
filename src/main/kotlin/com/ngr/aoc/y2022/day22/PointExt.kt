package com.ngr.aoc.y2022.day22

import java.awt.Point

operator fun Point.plus(dir: Dir) =
    Point(x + dir.dx, y + dir.dy)

operator fun Point.plusAssign(dir: Dir) {
    x += dir.dx
    y += dir.dy
}