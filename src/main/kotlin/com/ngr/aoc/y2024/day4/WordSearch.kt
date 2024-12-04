package com.ngr.aoc.y2024.day4

const val XMAS = "XMAS"

enum class Dir(val dx: Int, val dy: Int) {
    N(0, -1),
    NE(1, -1),
    E(1, 0),
    SE(1, 1),
    S(0, 1),
    SW(-1, 1),
    W(-1, 0),
    NW(-1, -1);

    companion object {
        val diagonals = listOf(NE, SE, SW, NW)
    }
}

fun List<List<Char>>.inside(x: Int, y: Int) =
    this.indices.contains(y) && this[0].indices.contains(x)