package com.ngr.aoc.y2025.day6

enum class Operation(val compute: (Long, Long) -> Long) {
    `*`({ a, b -> a * b }),
    `+`({ a, b -> a + b }),
    ;

    companion object {
        fun fromChar(str: Char) =
            Operation.entries.first { it.name == str.toString() }
    }
}