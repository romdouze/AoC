package com.ngr.aoc.y2022.day24

import java.awt.Point

operator fun Point.plus(dir: Dir?) =
    Point(x + (dir?.dx ?: 0), y + (dir?.dy ?: 0))

operator fun Point.plusAssign(dir: Dir?) {
    x += dir?.dx ?: 0
    y += dir?.dy ?: 0
}