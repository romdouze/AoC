package com.ngr.aoc.y2022.day17

import com.ngr.aoc.y2022.day17.Day17.Companion.WIDTH_RANGE

class Rock(
    val points: Set<LPoint>
) {

    val top get() = points.maxOf { it.y }
    val tops
        get() = WIDTH_RANGE.map { x ->
            points.filter { it.x == x.toLong() }.maxOfOrNull { it.y } ?: Long.MIN_VALUE
        }

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