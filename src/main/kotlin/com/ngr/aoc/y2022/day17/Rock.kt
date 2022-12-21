package com.ngr.aoc.y2022.day17

class Rock(
    val points: Set<LPoint>
) {

    val top get() = points.maxOf { it.y }

    companion object {
        fun spawn(shape: Shape, offset: LPoint) =
            Rock(
                shape.template
                    .map { LPoint(it + offset) }
                    .toSet()
            )
    }

    fun move(move: Move) {
        points.onEach { it.move(move) }
    }

    fun inbound() =
        points.all { it.inbound() }

    fun clone() =
        Rock(points.map { LPoint(it) }.toSet())
}