package com.ngr.aoc.y2022.day23

import com.ngr.aoc.Day
import com.ngr.aoc.y2022.day23.Dir.E
import com.ngr.aoc.y2022.day23.Dir.N
import com.ngr.aoc.y2022.day23.Dir.S
import com.ngr.aoc.y2022.day23.Dir.W
import java.awt.Point

class Day23 : Day<Point, Int, Int>() {

    private companion object {
        private const val ELF = '#'
        private const val EMPTY = '.'
    }

    private var row = 0

    override fun handleLine(lines: MutableList<Point>, line: String) {
        lines.addAll(
            line.mapIndexed { x, c -> c to Point(x, row) }
                .filter { it.first == ELF }
                .map { it.second }
        )
        row++
    }

    override fun part1(lines: List<Point>): Int {
        val allElves = lines
            .map { Point(it) }
            .associateWith { Elf(it) }
            .toMutableMap()

        spreadElves(allElves, 10)

        return score(allElves.keys)
    }

    override fun part2(lines: List<Point>): Int {
        val allElves = lines
            .map { Point(it) }
            .associateWith { Elf(it) }
            .toMutableMap()

        return spreadElves(allElves, null)
    }

    private fun spreadElves(
        allElves: MutableMap<Point, Elf>,
        maxRounds: Int?
    ): Int {
        val dirs = mutableListOf(N, S, W, E)

        val haveMoved = mutableListOf<Elf>()
        val allProposals = mutableSetOf<Point>()
        val duplicatedProposals = mutableSetOf<Point>()

        var rounds = 0
        do {
            haveMoved.clear()
            allProposals.clear()
            duplicatedProposals.clear()

            allElves.values.forEach { elf ->
                if (elf.allNeighbours.intersect(allElves.keys).isEmpty()) {
                    elf.proposal = null
                } else {
                    dirs.firstOrNull {
                        it.neighboursCheck()
                            .map { elf.pos + it }
                            .intersect(allElves.keys)
                            .isEmpty()
                    }?.also {
                        elf.proposal = it
                        if (!allProposals.add(elf.proposedPos)) {
                            duplicatedProposals.add(elf.proposedPos)
                        }
                    } ?: run { elf.proposal = null }
                }
            }

            allElves.values
                .filter { it.proposal != null }
                .filter { !duplicatedProposals.contains(it.proposedPos) }
                .forEach {
                    allElves.remove(it.pos)
                    it.move()
                    allElves[it.pos] = it

                    haveMoved.add(it)
                }

            dirs.add(dirs.removeFirst())
            rounds++
        } while ((maxRounds == null || rounds < maxRounds) && haveMoved.isNotEmpty())

        return rounds
    }

    private fun score(allElves: Set<Point>): Int {
        val widthRange = allElves.maxOf { it.x } - allElves.minOf { it.x } + 1
        val heightRange = allElves.maxOf { it.y } - allElves.minOf { it.y } + 1

        return widthRange * heightRange - allElves.size
    }
}