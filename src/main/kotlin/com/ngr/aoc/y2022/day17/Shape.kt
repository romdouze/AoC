package com.ngr.aoc.y2022.day17


enum class Shape(val template: Set<LPoint>) {
    I(
        setOf(
            LPoint(0, 0),
            LPoint(0, 1),
            LPoint(0, 2),
            LPoint(0, 3),
        )
    ),
    L(
        setOf(
            LPoint(0, 0),
            LPoint(1, 0),
            LPoint(2, 0),
            LPoint(2, 1),
            LPoint(2, 2),
        )
    ),
    O(
        setOf(
            LPoint(0, 0),
            LPoint(0, 1),
            LPoint(1, 1),
            LPoint(1, 0),
        )
    ),
    X(
        setOf(
            LPoint(1, 0),
            LPoint(0, 1),
            LPoint(1, 1),
            LPoint(2, 1),
            LPoint(1, 2),
        )
    ),
    `-`(
        setOf(
            LPoint(0, 0),
            LPoint(1, 0),
            LPoint(2, 0),
            LPoint(3, 0),
        )
    )
}