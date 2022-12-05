package com.ngr.aoc.y2022.day2

import com.ngr.aoc.y2022.day2.Outcome.*

enum class Shape(
    val score: Int,
    val fight: (Shape) -> Int,
    val getOutcome: (Outcome) -> Shape,
) {
    ROCK(1,
        {
            when (it) {
                ROCK -> 3
                PAPER -> 0
                SCISSORS -> 6
            }
        },
        {
            when (it) {
                WIN -> PAPER
                DRAW -> ROCK
                LOSE -> SCISSORS
            }
        }),
    PAPER(2,
        {
            when (it) {
                ROCK -> 6
                PAPER -> 3
                SCISSORS -> 0
            }
        },
        {
            when (it) {
                WIN -> SCISSORS
                DRAW -> PAPER
                LOSE -> ROCK
            }
        }),

    SCISSORS(3,
        {
            when (it) {
                ROCK -> 0
                PAPER -> 6
                SCISSORS -> 3
            }
        },
        {
            when (it) {
                WIN -> ROCK
                DRAW -> SCISSORS
                LOSE -> PAPER
            }
        });

    companion object {
        fun fromSymbol(symbol: String) =
            when (symbol) {
                "A", "X" -> ROCK
                "B", "Y" -> PAPER
                "C", "Z" -> SCISSORS
                else -> throw IllegalArgumentException("Unexpected symbol: $symbol")
            }
    }
}