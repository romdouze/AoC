package com.ngr.aoc.y2022.day22

import java.awt.Point

data class Location(
    val face: Int,
    val pos: Point,
    var dir: Dir,
) {
    fun rotate(rotation: Dir) {
        dir = dir.rotate(rotation)
    }

    fun walk() {
        pos += dir
    }
}