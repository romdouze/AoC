package com.ngr.aoc.y2022.day22

import java.awt.Point

data class Location(
    val pos: Point,
    var dir: Dir,
) {

    fun move(steps: Int) {
        pos.x += dir.dx * steps
        pos.y += dir.dy * steps
    }

    fun rotate(rotation: Dir) {
        dir = dir.rotate(rotation)
    }
}