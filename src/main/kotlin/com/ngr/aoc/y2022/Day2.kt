package com.ngr.aoc.y2022

import com.ngr.aoc.y2022.Day2.Outcome.*
import com.ngr.aoc.y2022.Day2.Shape

typealias Duel = Pair<Shape, Shape>

class Day2 : Day<String, Int, Int>() {


    override fun handleLine(lines: MutableList<String>, line: String) {
        lines.add(line)
    }

    override fun part1(lines: List<String>) =
        lines.sumOf {
            getDuel(it)
                .score()
        }

    override fun part2(lines: List<String>) =
        lines.sumOf {
            buildDuel(it)
                .score()
        }

    private fun buildDuel(line: String): Duel =
        line.split(" ")
            .let {
                val first = Shape.fromSymbol(it[0])
                Duel(first, first.getOutcome(Outcome.fromSymbol(it[1])))
            }

    private fun getDuel(line: String): Duel =
        line.split(" ")
            .map { Shape.fromSymbol(it) }
            .zipWithNext()
            .first()

    private fun Duel.score() =
        second.fight(first) + second.score


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
}