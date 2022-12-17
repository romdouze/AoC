package com.ngr.aoc.y2022.day12

enum class Dir(
    val dx: Int,
    val dy: Int,
) {
    N(0, -1),
    S(0, 1),
    E(1, 0),
    W(-1, 0)
}
