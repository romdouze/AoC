package com.ngr.aoc.y2022.day17

data class LPoint(var x: Long, var y: Long) {

    constructor(point: LPoint) : this(point.x, point.y)

    operator fun plus(other: LPoint) =
        LPoint(x + other.x, y + other.y)

    fun move(move: Move) {
        x += move.dx.toLong()
        y += move.dy.toLong()
    }

    fun inbound() =
        Day17.WIDTH_RANGE.contains(x) && y >= Day17.FLOOR
}