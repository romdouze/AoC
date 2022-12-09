package com.ngr.aoc.y2022.day9

import java.awt.Point

class Rope(
    length: Int,
    init: List<Point>? = null
) {
    val knots: List<Point> = init ?: (1..length).map { Point(0, 0) }
    val head = knots.first()
    val tail = knots.last()

    fun moveHead(dir: Dir) {
        head.move(dir)
        knots.drop(1)
            .forEachIndexed { i, knot -> knot.catchupWith(knots[i]) }
    }
}