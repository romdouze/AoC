package com.ngr.aoc.y2022.day12

import com.ngr.aoc.y2022.Day
import java.awt.Point

class Day12 : Day<String, Int, Int>() {

    private val heights: Grid<Char> = mutableListOf()
    private lateinit var start: Point
    private lateinit var end: Point

    override fun handleLine(lines: MutableList<String>, line: String) {
        line
            .let { l ->
                l.indexOf("S").also { if (it != -1) start = Point(it, heights.size) }
                l.replace("S", "a")
            }
            .let { l ->
                l.indexOf("E").also { if (it != -1) end = Point(it, heights.size) }
                l.replace("E", "z")
            }
            .also { heights.add(it.toCharArray().toMutableList()) }
    }


    override fun part1(lines: List<String>) =
        heights.flood(start, end) { a, b -> b <= a + 1 }
            .at(end)
            .dist

    override fun part2(lines: List<String>) =
        heights.flood(end, null) { a, b -> a <= b + 1 }
            .flatten()
            .filter {
                heights.at(it.pos) == 'a'
            }.minOf { it.dist }
}