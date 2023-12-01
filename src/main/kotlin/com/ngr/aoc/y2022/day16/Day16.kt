package com.ngr.aoc.y2022.day16

import com.ngr.aoc.Day

class Day16 : Day<String, Int, Int>() {

    private companion object {
        private const val START = "AA"
        private const val MAX_TIME = 30
    }

    private val initialNetwork: MutableMap<String, Valve> = mutableMapOf()

    override fun handleLine(lines: MutableList<String>, line: String) {
        Valve.fromString(line)
            .also { initialNetwork[it.name] = it }
    }

    override fun part1(lines: List<String>): Int {
        val maxTime = MAX_TIME
        val network: Network = initialNetwork.entries.associate { it.key to Node(it.value) }
            .plus(
                initialNetwork.entries
                    .filter { it.value.rate > 0 }
                    .associate { it.key.asOpen() to Node(it.value, open = true) }
            )

        val allPaths = mutableListOf<Path>()
        network.generatePaths(
            maxTime,
            emptyPath(START),
            network.allOpenable.map { it.name },
            allPaths
        )

        val bestPath = allPaths.maxBy { network.released(it, maxTime) }

        return network.released(bestPath, maxTime)
    }

    // WAY TOO SLOW !
    override fun part2(lines: List<String>): Int {
        val maxTime = MAX_TIME - 4
        val network: Network = initialNetwork.entries.associate { it.key to Node(it.value) }
            .plus(
                initialNetwork.entries
                    .filter { it.value.rate > 0 }
                    .associate { it.key.asOpen() to Node(it.value, open = true) }
            )

        val allPaths = mutableListOf<Pair<Path, Path>>()
        network.generateMultiPath(
            maxTime,
            emptyPath(START) to emptyPath(START),
            network.allOpenable.map { it.name } to network.allOpenable.map { it.name },
            allPaths
        )

        val bestPath = allPaths.maxBy { it.toList().sumOf { network.released(it, maxTime) } }

        return bestPath.toList().sumOf { network.released(it, maxTime) }
    }
}