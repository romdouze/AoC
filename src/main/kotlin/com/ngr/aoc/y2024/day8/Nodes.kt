package com.ngr.aoc.y2024.day8

import java.awt.Point

fun antinodes(a: Point, b: Point) =
    with(Point(b.x - a.x, b.y - a.y)) {
        listOfNotNull(
            Point(b.x + x, b.y + y).takeIf { it.inside() },
            Point(a.x - x, a.y - y).takeIf { it.inside() },
        )
    }

fun antinodesWithResonance(a: Point, b: Point) =
    with(Point(b.x - a.x, b.y - a.y)) {
        val antinodes = mutableListOf<Point>()
        var antinode = a
        while (antinode.inside()) {
            antinodes.add(antinode)
            antinode = Point(antinode.x - x, antinode.y - y)
        }
        antinode = b
        while (antinode.inside()) {
            antinodes.add(antinode)
            antinode = Point(antinode.x + x, antinode.y + y)
        }

        antinodes
    }

fun Point.inside() =
    (0 until WIDTH).contains(x) &&
            (0 until HEIGHT).contains(y)