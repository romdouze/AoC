package com.ngr.aoc.y2022.day24

import java.awt.Point

data class Blizzard(
    val pos: Point,
    val dir: Dir,
) {
    fun move(widthRange: IntRange, heightRange: IntRange) {
        pos += dir
        if (pos.x == 0) pos.x = widthRange.last - 1
        if (pos.x == widthRange.last) pos.x = 1
        if (pos.y == 0) pos.y = heightRange.last - 1
        if (pos.y == heightRange.last) pos.y = 1
    }
}