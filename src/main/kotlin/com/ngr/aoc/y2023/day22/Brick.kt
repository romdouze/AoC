package com.ngr.aoc.y2023.day22

data class Brick(
    val id: Int,
    val xRange: IntRange,
    val yRange: IntRange,
    val zRange: IntRange,
) {
    val points by lazy {
        xRange.flatMap { x ->
            yRange.flatMap { y ->
                zRange.map { z -> Point3D(x, y, z) }
            }
        }
    }

    fun fallBy(dz: Int) =
        copy(
            zRange = zRange.first - dz..zRange.last - dz
        )
}

data class Point3D(
    val x: Int,
    val y: Int,
    val z: Int,
)