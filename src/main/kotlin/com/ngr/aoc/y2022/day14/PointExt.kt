package com.ngr.aoc.y2022.day14

import java.awt.Point
import kotlin.math.sign


fun Point.join(other: Point): List<Point> {
    val list = mutableListOf(this)

    while (!list.contains(other)) {
        val last = list.last()
        list.add(Point(last.x + (other.x - this.x).sign, last.y + (other.y - this.y).sign))
    }

    return list
}