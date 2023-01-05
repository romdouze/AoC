package com.ngr.aoc.y2022.day21

enum class Operator(
    val symbol: String,
    val operation: (left: Long, right: Long) -> Long,
    val adjustRight: (output: Long, left: Long) -> Long,
    val adjustLeft: (output: Long, right: Long) -> Long,
) {
    ADD(
        "+",
        Long::plus,
        { output, left -> output - left },
        { output, right -> output - right },
    ),
    MINUS(
        "-",
        Long::minus,
        { output, left -> left - output },
        { output, right -> right + output },
    ),
    TIMES(
        "*",
        Long::times,
        { output, left -> output / left },
        { output, right -> output / right },
    ),
    DIV(
        "/",
        Long::div,
        { output, left -> left / output },
        { output, right -> right * output },
    );

    companion object {
        fun fromString(symbol: String) =
            values().first { it.symbol == symbol }
    }
}