package com.ngr.aoc.y2023.day2

import com.ngr.aoc.y2023.day2.Color.BLUE
import com.ngr.aoc.y2023.day2.Color.GREEN
import com.ngr.aoc.y2023.day2.Color.RED


data class Game(
    val id: Int,
    val handfuls: List<Handful>,
) {
    companion object {

        private val GAME_ID_PATTERN = "Game (?<id>\\d+): ".toRegex()
        private val COLOR_PATTERN = "(?<count>\\d+) (?<color>green|red|blue)".toRegex()

        fun fromString(line: String): Game {
            val idMatchResult = GAME_ID_PATTERN.find(line)!!
            val id = idMatchResult.groups["id"]!!.value.toInt()
            val handfulsStr = line.removePrefix(idMatchResult.value)

            val handfuls = handfulsStr.trim().split("; ")
                .map {
                    it.split(", ")
                        .map {
                            val groups = COLOR_PATTERN.find(it)!!.groups
                            Color.fromString(groups["color"]!!.value) to groups["count"]!!.value.toInt()
                        }.associate { it }
                }.map { Handful(it) }

            return Game(
                id,
                handfuls,
            )
        }
    }
}

data class Handful(
    val blue: Int,
    val red: Int,
    val green: Int,
) {
    constructor(handful: Map<Color, Int>) : this(
        blue = handful[BLUE] ?: 0,
        red = handful[RED] ?: 0,
        green = handful[GREEN] ?: 0,
    )

    val cubes = mapOf(
        BLUE to blue,
        RED to red,
        GREEN to green,
    )
}

enum class Color {
    BLUE, RED, GREEN;

    companion object {
        fun fromString(str: String) =
            Color.valueOf(str.uppercase())
    }
}