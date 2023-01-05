package com.ngr.aoc.y2022.day21

enum class Operator(val symbol: String, val operation: (Long, Long) -> Long) {
    ADD("+", Long::plus),
    MINUS("-", Long::minus),
    TIMES("*", Long::times),
    DIV("/", Long::div);

    companion object {
        fun fromString(symbol: String) =
            values().first { it.symbol == symbol }
    }
}