package com.ngr.aoc.y2022.day12

import java.awt.Point


fun Point.move(dir: Dir) =
    apply {
        x += dir.dx
        y += dir.dy
    }

operator fun Point.plus(dir: Dir) =
    Point(x + dir.dx, y + dir.dy)