package com.ngr.aoc.y2023.day2

import com.ngr.aoc.Day
import com.ngr.aoc.y2023.day2.Color.BLUE
import com.ngr.aoc.y2023.day2.Color.GREEN
import com.ngr.aoc.y2023.day2.Color.RED
import kotlin.math.max

class Day2 : Day<Game, Int, Int>() {

    override fun handleLine(lines: MutableList<Game>, line: String) {
        lines.add(Game.fromString(line))
    }

    override fun part1(lines: List<Game>): Int {
        val maxColors = mapOf(
            BLUE to 14,
            RED to 12,
            GREEN to 13,
        )

        return lines.filter { game ->
            game.handfuls.none {
                it.cubes.any { color -> color.value > maxColors[color.key]!! }
            }
        }.sumOf { it.id }
    }

    override fun part2(lines: List<Game>): Int =
        lines.sumOf { game ->
            game.handfuls.fold(
                mapOf(
                    BLUE to 0,
                    RED to 0,
                    GREEN to 0,
                )
            ) { maxColors, handful ->
                mapOf(
                    BLUE to max(maxColors[BLUE] ?: 0, handful.blue),
                    RED to max(maxColors[RED] ?: 0, handful.red),
                    GREEN to max(maxColors[GREEN] ?: 0, handful.green),
                )
            }.let { it.values.reduce { acc, i -> acc * i } }
        }
}