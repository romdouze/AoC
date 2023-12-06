package com.ngr.aoc.y2023.day6

import com.ngr.aoc.Day
import java.io.InputStream

class Day6 : Day<Race, Long, Long>() {

    private companion object {
        private const val TIME_PREFIX = "Time:"
        private const val DISTANCE_PREFIX = "Distance:"
    }

    private lateinit var megaRace: Race

    override fun readInput(data: InputStream): List<Race> {
        val times = mutableListOf<Long>()
        val distances = mutableListOf<Long>()

        data.bufferedReader().let { reader ->
            val timesLine = reader.readLine().removePrefix(TIME_PREFIX)
            times.addAll(
                timesLine.trim()
                    .split(" ")
                    .filter { it.isNotBlank() }
                    .map { it.trim().toLong() }
            )

            val distancesLine = reader.readLine().removePrefix(DISTANCE_PREFIX)
            distances.addAll(
                distancesLine.trim()
                    .split(" ")
                    .filter { it.isNotBlank() }
                    .map { it.trim().toLong() }
            )

            megaRace = Race(
                times.map { it.toString() }
                    .joinToString("") { it }
                    .toLong(),
                distances.map { it.toString() }
                    .joinToString("") { it }
                    .toLong(),
            )
        }

        return times.zip(distances)
            .map { Race(it.first, it.second) }
    }

    override fun handleLine(lines: MutableList<Race>, line: String) {
        // DO NOTHING
    }

    override fun part1(lines: List<Race>) =
        lines.map {
            it.winningRange().count()
        }.fold(1L) { acc, i -> acc * i }

    override fun part2(lines: List<Race>) =
        megaRace.winningRange().count().toLong()
}

