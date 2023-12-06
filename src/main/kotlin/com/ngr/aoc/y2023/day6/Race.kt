package com.ngr.aoc.y2023.day6

import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.sqrt

data class Race(
    val time: Long,
    val recordDistance: Long,
) {
    fun winningRange() =
        Solver.solve(time, recordDistance)
}

object Solver {
    fun solve(time: Long, distance: Long): LongRange {
        val d = (time * time - 4 * distance).toDouble()
        val lower = (time - sqrt(d)) / 2
        val upper = (time + sqrt(d)) / 2

        return ceil(lower).toLong()..floor(upper).toLong()
    }
}