package com.ngr.aoc.y2025.day9

import java.awt.Point
import kotlin.math.max
import kotlin.math.min

data class Rectangle(val x1: Int, val y1: Int, val x2: Int, val y2: Int) {
    fun area() = (x2 - x1 + 1).toLong() * (y2 - y1 + 1)

    fun intersects(other: Rectangle) =
        x1 < other.x2 && x2 > other.x1 && y1 < other.y2 && y2 > other.y1
}

fun List<Point>.findEdges() =
    zipWithNext().map { it.asRectangle() }

fun Pair<Point, Point>.asRectangle(): Rectangle {
    val topLeft = Point(min(first.x, second.x), min(first.y, second.y))
    val bottomRight = Point(max(first.x, second.x), max(first.y, second.y))

    return Rectangle(topLeft.x, topLeft.y, bottomRight.x, bottomRight.y)
}