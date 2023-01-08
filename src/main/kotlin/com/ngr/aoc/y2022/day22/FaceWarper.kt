package com.ngr.aoc.y2022.day22

import java.awt.Point

typealias FaceWarper = Map<Int, Map<Dir, (Point) -> Location>>

fun FaceWarper.warp(location: Location) = this[location.face]!![location.dir]!!(location.pos)