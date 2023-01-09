package com.ngr.aoc.y2022.day24

import java.awt.Point

data class State(
    val location: Point,
    val path: List<Point>,
    private val cycleLength: Int,
) {

    val clock = path.size
    val cycleClock = clock % cycleLength

    override fun equals(other: Any?) =
        if (other is State) {
            location == other.location && cycleClock == other.cycleClock
        } else {
            super.equals(other)
        }

    override fun hashCode() =
        location.hashCode() + 31 * cycleClock.hashCode()

    fun clone(newLocation: Point? = null) =
        State(
            Point(newLocation ?: location),
            path + (newLocation?.let { listOf(Point(newLocation)) } ?: emptyList()),
            cycleLength
        )

    override fun toString() =
        "location=${location.print()}, clock=$clock, path=${path.map { it.print() }}"
}