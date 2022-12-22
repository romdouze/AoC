package com.ngr.aoc.y2022.day18

enum class Dir(val dx: Int, val dy: Int, val dz: Int) {
    U(0, 1, 0),
    D(0, -1, 0),
    R(1, 0, 0),
    L(-1, 0, 0),
    F(0, 0, 1),
    B(0, 0, -1);

    val opposit
        get() = when (this) {
            U -> D
            D -> U
            R -> L
            L -> R
            F -> B
            B -> F
        }
}