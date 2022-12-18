package com.ngr.aoc.y2022.day14

import com.ngr.aoc.y2022.Day
import java.awt.Point

class Day14 : Day<String, Int, Int>() {

    private val droppingPoint = Point(500, 0)

    private val initialSpots = mutableMapOf<Point, Item>()
    private var leftMost: Int = droppingPoint.x
    private var rightMost: Int = droppingPoint.x
    private var downMost: Int = droppingPoint.y

    override fun handleLine(lines: MutableList<String>, line: String) {
        initialSpots.putAll(
            line.split(" -> ")
                .map {
                    it.split(",")
                        .let { p -> Point(p[0].toInt(), p[1].toInt()) }
                        .also { p -> updateEdges(p) }
                }
                .windowed(2) {
                    it[0].join(it[1])
                }
                .flatten()
                .associateWith { Item.ROCK }
        )
    }

    override fun part1(lines: List<String>) =
        stackSand(droppingPoint, initialSpots.toMutableMap())

    override fun part2(lines: List<String>) =
        stackSand(droppingPoint, initialSpots.toMutableMap(), downMost + 2)

    private fun stackSand(start: Point, spots: MutableMap<Point, Item>, floor: Int? = null): Int {
        var count = 0

        do {
            val restingSpot = dropNewSand(start, spots, floor)
            if (restingSpot.inbound(floor)) {
                spots[restingSpot] = Item.SAND
                count++
            }
        } while (restingSpot.inbound(floor) && !spots.containsKey(droppingPoint))

        return count
    }

    private fun dropNewSand(start: Point, spots: Map<Point, Item>, floor: Int? = null): Point {
        var rested = false
        var current = Point(start)

        while (!rested && current.inbound(floor)) {
            if (floor != null && current.y == floor - 1) {
                rested = true
            } else if (!spots.containsKey(Point(current.x, current.y + 1))) {
                current = Point(current.x, current.y + 1)
            } else if (!spots.containsKey(Point(current.x - 1, current.y + 1))) {
                current = Point(current.x - 1, current.y + 1)
            } else if (!spots.containsKey(Point(current.x + 1, current.y + 1))) {
                current = Point(current.x + 1, current.y + 1)
            } else {
                rested = true
            }
        }

        return current
    }

    private fun updateEdges(point: Point) {
        if (point.x < leftMost) leftMost = point.x
        if (point.x > rightMost) rightMost = point.x
        if (point.y > downMost) downMost = point.y
    }

    private fun Point.inbound(floor: Int?) =
        floor != null || (x in leftMost..rightMost && y <= downMost)
}