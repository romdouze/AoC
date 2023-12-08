package com.ngr.aoc.y2023.day8

data class Node(
    val id: String,
    val left: String,
    val right: String,
)

data class Loop(
    val offset: Int,
    val length: Int,
    val endpoints: List<Int>,
)