package com.ngr.aoc.y2022.day23

import java.awt.Point

data class Elf(
    val pos: Point
) {
    var proposal: Dir? = null
    val proposedPos: Point get() = proposal?.let { pos + it } ?: pos
    val allNeighbours: Set<Point>
        get() =
            Dir.values()
                .map { pos + it }
                .toSet()

    fun move() {
        proposal?.also {
            pos += it
        }
    }
}