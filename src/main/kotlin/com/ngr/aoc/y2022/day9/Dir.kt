package com.ngr.aoc.y2022.day9

enum class Dir(
    val dx: Int,
    val dy: Int,
) {
    D(0, -1),
    U(0, 1),
    R(1, 0),
    L(-1, 0);

    companion object {
        fun fromString(s: String) =
            values().first { it.name == s }
    }
}
