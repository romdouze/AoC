package com.ngr.aoc.y2024.day23

import com.ngr.aoc.Day

class Day23 : Day<Pair<String, String>, Int, String>() {
    override fun handleLine(lines: MutableList<Pair<String, String>>, line: String) {
        line.split("-").also {
            lines.add(it[0] to it[1])
            lines.add(it[1] to it[0])
        }
    }

    override fun part1(lines: List<Pair<String, String>>) =
        LanParty(lines).findTriples()
            .count { it.any { it.startsWith("t") } }

    override fun part2(lines: List<Pair<String, String>>) =
        LanParty(lines).findLanParty().joinToString(separator = ",")
}