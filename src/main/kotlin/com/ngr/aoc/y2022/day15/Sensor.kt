package com.ngr.aoc.y2022.day15

import java.awt.Point
import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.math.min

data class Sensor(
    val pos: Point,
    val beacon: Point,
) {
    private val range by lazy {
        pos.manhattan(beacon)
    }

    fun exclusionOnRow(row: Int, widthRange: IntRange? = null, heightRange: IntRange? = null) =
        if (heightRange?.contains(row) == false) {
            IntRange.EMPTY
        } else {
            (pos.y - row).absoluteValue
                .let { distToRow ->
                    val radius = range - distToRow
                    if (radius >= 0) {
                        (max(pos.x - radius, widthRange?.start ?: Int.MIN_VALUE)..min(
                            (pos.x + radius),
                            widthRange?.endInclusive ?: Int.MAX_VALUE
                        ))
                    } else IntRange.EMPTY
                }
        }
}