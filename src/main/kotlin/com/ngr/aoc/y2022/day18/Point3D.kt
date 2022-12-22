package com.ngr.aoc.y2022.day18

data class Point3D(
    val x: Int,
    val y: Int,
    val z: Int,
) {
    operator fun plus(dir: Dir) =
        Point3D(x + dir.dx, y + dir.dy, z + dir.dz)
}