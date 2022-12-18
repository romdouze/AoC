package com.ngr.aoc.y2022.day15

import java.awt.Point
import kotlin.math.absoluteValue

fun Point.manhattan(other: Point) =
    (x - other.x).absoluteValue + (y - other.y).absoluteValue