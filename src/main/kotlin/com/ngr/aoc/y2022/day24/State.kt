package com.ngr.aoc.y2022.day24

import java.awt.Point

data class State(
    val location: Point,
    val blizzards: Set<Blizzard>,
    val path: List<Point>,
) {

    override fun equals(other: Any?) =
        if (other is State) {
            location == other.location && blizzards == other.blizzards
        } else {
            super.equals(other)
        }

    override fun hashCode() =
        location.hashCode() + 31 * blizzards.hashCode()

    fun clone(newLocation: Point? = null) =
        State(
            Point(newLocation ?: location),
            blizzards.map { it.clone() }.toSet(),
            path.map { Point(it) } + (newLocation?.let { listOf(Point(newLocation)) } ?: emptyList()),
        )
}