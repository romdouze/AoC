package com.ngr.aoc.y2024.day8

import com.ngr.aoc.Day
import java.awt.Point

var WIDTH: Int = 0
var HEIGHT: Int = 0

class Day8 : Day<Pair<Point, Char>, Int, Int>() {

    private val nodesMap = mutableMapOf<Char, List<Point>>()

    override fun handleLine(lines: MutableList<Pair<Point, Char>>, line: String) {
        WIDTH = line.length
        line.forEachIndexed { x, c ->
            if (c != '.') {
                val pos = Point(x, HEIGHT)
                lines.add(pos to c)
                if (!nodesMap.contains(c)) {
                    nodesMap[c] = listOf()
                }
                nodesMap[c] = nodesMap[c]!! + listOf(pos)
            }
        }
        HEIGHT++
    }

    override fun part1(lines: List<Pair<Point, Char>>) =
        nodesMap.values.flatMap { nodes ->
            nodes.flatMapIndexed { index, n1 ->
                (index + 1 until nodes.size).map { n1 to nodes[it] }
            }.flatMap { (n1, n2) -> antinodes(n1, n2) }
        }.toSet().size

    override fun part2(lines: List<Pair<Point, Char>>) =
        nodesMap.values.flatMap { nodes ->
            nodes.flatMapIndexed { index, n1 ->
                (index + 1 until nodes.size).map { n1 to nodes[it] }
            }.flatMap { (n1, n2) -> antinodesWithResonance(n1, n2) }
        }.toSet().size
}