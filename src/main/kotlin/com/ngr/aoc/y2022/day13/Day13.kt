package com.ngr.aoc.y2022.day13

import com.ngr.aoc.Day

class Day13 : Day<Pair<Packet, Packet>, Int, Int>() {

    private companion object {
        private val DIVIDERS = listOf(Packet("[[2]]"), Packet("[[6]]"))
    }

    private var currentFirst: Packet? = null

    override fun handleLine(lines: MutableList<Pair<Packet, Packet>>, line: String) {
        if (line.isEmpty()) return

        val currentPacket = Packet(line)

        currentFirst =
            if (currentFirst == null) {
                currentPacket
            } else {
                lines.add(currentFirst!! to currentPacket)
                null
            }
    }

    override fun part1(lines: List<Pair<Packet, Packet>>) =
        lines.foldIndexed(0) { i, sum, pair ->
            if (pair.compare() <= 0) {
                sum + i + 1
            } else {
                sum
            }
        }

    override fun part2(lines: List<Pair<Packet, Packet>>) =
        lines
            .flatMap { it.toList() }
            .let { it + DIVIDERS }
            .sortedWith(Packet::compareTo)
            .foldIndexed(1) { i, acc, packet ->
                if (DIVIDERS.contains(packet)) acc * (i + 1)
                else acc
            }
}