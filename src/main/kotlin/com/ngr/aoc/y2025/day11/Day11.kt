package com.ngr.aoc.y2025.day11

import com.ngr.aoc.Day

class Day11 : Day<Node, Int, Int>() {
    private val network = Network()

    override fun handleLine(lines: MutableList<Node>, line: String) {
        val node = Node.fromString(line)
        lines.add(node)
        network.addNode(node)
    }

    override fun part1(lines: List<Node>) =
        network.nbPaths("you", "out")

    override fun part2(lines: List<Node>) =
        with(network) {
            (nbPaths("svr", "dac"))
        }
}