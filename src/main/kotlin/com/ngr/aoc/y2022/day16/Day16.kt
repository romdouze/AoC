package com.ngr.aoc.y2022.day16

import com.ngr.aoc.y2022.Day

class Day16 : Day<String, Int, Int>() {

    private companion object {
        private const val START = "AA"
        private const val MAX_TIME = 30
        private const val MOVE_TIME = 1
        private const val OPEN_TIME = 1
    }

    private val initialNetwork: MutableMap<String, Valve> = mutableMapOf()

    override fun handleLine(lines: MutableList<String>, line: String) {
        Valve.fromString(line)
            .also { initialNetwork[it.name] = it }
    }

    override fun part1(lines: List<String>): Int {
        val network = initialNetwork.entries.associate { it.key to Node(it.value) }

        var currentNode = network[START]!!
            .apply {
                elapsed = 0
                released = 0
                path = listOf(this)
            }

        while (network.values.any { currentNode.elapsed < MAX_TIME }) {

            currentNode = network.values
                .filter { it != currentNode || (!currentNode.open && currentNode.valve.rate > 0) }
                .maxBy { it.released }
        }

        return network.values.maxOf { it.released }
    }

    override fun part2(lines: List<String>): Int {
        TODO("Not yet implemented")
    }
}