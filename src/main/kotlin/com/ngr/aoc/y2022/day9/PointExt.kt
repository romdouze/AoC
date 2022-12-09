package com.ngr.aoc.y2022.day9

import java.awt.Point
import kotlin.math.sign

fun Point.move(dir: Dir) =
    apply {
        x += dir.dx
        y += dir.dy
    }

fun Point.catchupWith(head: Point) =
    apply {
        if (distanceSq(head) >= 4) {
            x += (head.x - x).sign
            y += (head.y - y).sign
        }
    }
