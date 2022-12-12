package com.ngr.aoc.y2022.day10

enum class Operation(
    val value: String,
) {
    NOOP("noop"),
    ADD("addx");

    companion object {
        fun fromString(line: String) =
            values().first { it.value == line }
    }
}