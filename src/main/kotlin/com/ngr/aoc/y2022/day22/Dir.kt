package com.ngr.aoc.y2022.day22

enum class Dir(val dx: Int, val dy: Int, val rotate: (Dir) -> Dir, val symbol: String) {
    R(
        1, 0,
        {
            when (it) {
                R -> D
                L -> U
                else -> throw IllegalArgumentException("Cannot rotate $it")
            }
        },
        "R"
    ),
    L(
        -1, 0,
        {
            when (it) {
                R -> U
                L -> D
                else -> throw IllegalArgumentException("Cannot rotate $it")
            }
        },
        "L"
    ),
    U(
        0, -1,
        {
            when (it) {
                R -> R
                L -> L
                else -> throw IllegalArgumentException("Cannot rotate $it")
            }
        },
        "U"
    ),
    D(
        0, 1,
        {
            when (it) {
                R -> L
                L -> R
                else -> throw IllegalArgumentException("Cannot rotate $it")
            }
        },
        "D"
    );

    companion object {
        fun fromString(symbol: String) =
            values().firstOrNull { it.symbol == symbol }
    }
}