package com.ngr.aoc.y2022.day23

enum class Dir(val dx: Int, val dy: Int, val neighboursCheck: () -> List<Dir>) {
    N(0, -1, { listOf(NW, N, NE) }),
    NE(1, -1, { listOf() }),
    E(1, 0, { listOf(NE, E, SE) }),
    SE(1, 1, { listOf() }),
    S(0, 1, { listOf(SE, S, SW) }),
    SW(-1, 1, { listOf() }),
    W(-1, 0, { listOf(SW, W, NW) }),
    NW(-1, -1, { listOf() })
}