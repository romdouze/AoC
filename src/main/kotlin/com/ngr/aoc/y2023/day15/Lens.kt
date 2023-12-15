package com.ngr.aoc.y2023.day15


data class Lens(
    val label: String,
    var focal: Int,
)

fun String.hash() =
    this.fold(0) { hash, c ->
        ((hash + c.code) * 17) % 256
    }