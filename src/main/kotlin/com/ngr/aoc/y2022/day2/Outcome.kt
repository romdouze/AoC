package com.ngr.aoc.y2022.day2

enum class Outcome {
    WIN, DRAW, LOSE;

    companion object {
        fun fromSymbol(symbol: String) =
            when (symbol) {
                "X" -> LOSE
                "Y" -> DRAW
                "Z" -> WIN
                else -> throw IllegalArgumentException("Unexpected symbol: $symbol")
            }
    }
}