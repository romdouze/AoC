package com.ngr.aoc.y2022.day24

import java.awt.Point

data class State(
    val location: Point,
    val blizzards: Set<Blizzard>
)