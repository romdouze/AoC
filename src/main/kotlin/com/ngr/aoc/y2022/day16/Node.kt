package com.ngr.aoc.y2022.day16

data class Node(
    val valve: Valve,
    var open: Boolean = false,
    var elapsed: Int = Int.MIN_VALUE,
    var released: Int = Int.MIN_VALUE,
    var path: List<Node> = emptyList(),
)