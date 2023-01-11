package com.ngr.aoc.y2022.day24

import java.awt.Point

data class State(
    val location: Point,
    val path: List<Point>,
    private val cycleLength: Int,
) {

    val clock = path.size % cycleLength

    override fun equals(other: Any?) =
        if (other is State) {
            location == other.location && clock == other.clock
        } else {
            super.equals(other)
        }

    override fun hashCode() =
        location.hashCode() + 31 * clock.hashCode()

    fun clone(newLocation: Point? = null) =
        State(
            Point(newLocation ?: location),
            path.map { Point(it) } + (newLocation?.let { listOf(Point(newLocation)) } ?: emptyList()),
            cycleLength
        )

    override fun toString() =
        "location=${location.print()}, path=${path.map { it.print() }}"
}