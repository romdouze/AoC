package com.ngr.aoc.y2022.day22

import com.ngr.aoc.y2022.day22.WarpableMapWalker.Companion.EMPTY
import java.awt.Point

typealias Cube = Map<Int, List<CharSequence>>

private val Cube.sizeRange get() = (0 until this.values.first().size)

fun Cube.canWalk(location: Location, faceWarper: FaceWarper) =
    (location.pos + location.dir).let {
        if (inbound(it)) {
            this[location.face, it] == EMPTY
        } else {
            this[faceWarper.warp(location)] == EMPTY
        }
    }

fun Cube.inbound(pos: Point) =
    sizeRange.contains(pos.x) && sizeRange.contains(pos.y)


operator fun Cube.get(face: Int, pos: Point) =
    this[face]!![pos.y][pos.x]

operator fun Cube.get(location: Location) =
    this[location.face, location.pos]