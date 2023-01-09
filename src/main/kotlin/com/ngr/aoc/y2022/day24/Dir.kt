package com.ngr.aoc.y2022.day24

enum class Dir(val dx: Int, val dy: Int, val symbol: Char) {
    N(0, -1, '^'),
    E(1, 0, '>'),
    S(0, 1, 'v'),
    W(-1, 0, '<');

    companion object {
        fun fromString(symbol: Char) =
            values().firstOrNull { it.symbol == symbol }
    }
}