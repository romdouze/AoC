package com.ngr.aoc.y2025.day11

import com.ngr.aoc.Day

class Day11 : Day<Node, Int, Long>() {
    private val network = Network()

    override fun handleLine(lines: MutableList<Node>, line: String) {
        val node = Node.fromString(line)
        lines.add(node)
        network.addNode(node)
    }

    override fun part1(lines: List<Node>) =
        network.nbPaths("you", "out")

    // graphviz analysis shows choke points
    override fun part2(lines: List<Node>) =
        with(network) {
            nbPaths("svr", "fft", listOf("dzg", "cxm", "tyv", "gyo", "prq")).toLong() *
                    (nbPaths("fft", "pza", listOf("pza", "vfl", "pcm")) *
                            nbPaths("pza", "dac", listOf("nzp", "rat", "you", "gmw", "tyc")) +
                            nbPaths("fft", "vfl", listOf("pza", "vfl", "pcm")) *
                            nbPaths("vfl", "dac", listOf("nzp", "rat", "you", "gmw", "tyc")) +
                            nbPaths("fft", "pcm", listOf("pza", "vfl", "pcm")) *
                            nbPaths("pcm", "dac", listOf("nzp", "rat", "you", "gmw", "tyc"))
                            ) *
                    nbPaths("dac", "out")
        }
}