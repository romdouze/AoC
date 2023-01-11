package com.ngr.aoc.y2022.day24

import java.awt.Point

data class Blizzard(
    val pos: Point,
    val dir: Dir,
) {
    fun move(innerWidthRange: IntRange, innerHeightRange: IntRange) {
        pos += dir
        if (pos.x < innerWidthRange.first) pos.x = innerWidthRange.last
        if (pos.x > innerWidthRange.last) pos.x = innerWidthRange.first
        if (pos.y < innerHeightRange.first) pos.y = innerHeightRange.last
        if (pos.y > innerHeightRange.last) pos.y = innerHeightRange.first
    }

    fun project(clock: Int, innerWidthRange: IntRange, innerHeightRange: IntRange) =
        (pos + dir * clock).let {
            Point(
                (it.x - innerWidthRange.first).mod(innerWidthRange.last - innerWidthRange.first + 1) + innerWidthRange.first,
                (it.y - innerHeightRange.first).mod(innerHeightRange.last - innerHeightRange.first + 1) + innerHeightRange.first
            )
        }

    fun clone() =
        Blizzard(
            Point(pos),
            dir,
        )
}