package com.ngr.aoc.y2024.day12

import com.ngr.aoc.Day
import java.awt.Point

var WIDTH = 0
var HEIGHT = 0

class Day12 : Day<String, Int, Int>() {

    private val garden = mutableMapOf<Point, Char>()

    override fun handleLine(lines: MutableList<String>, line: String) {
        line.forEachIndexed { x, type -> garden[Point(x, HEIGHT)] = type }
        WIDTH = line.length
        HEIGHT++
    }

    override fun part1(lines: List<String>) =
        Gardener(garden).price()

    override fun part2(lines: List<String>) =
        Gardener(garden).discountedPrice()
}