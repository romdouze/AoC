package com.ngr.aoc.y2022.day15

import com.ngr.aoc.Day
import java.awt.Point
import kotlin.math.max

class Day15 : Day<Sensor, Int, Long>() {

    private companion object {
        private val SENSOR_INPUT_PATTERN =
            "Sensor at x=(?<sx>-?\\d+), y=(?<sy>-?\\d+): closest beacon is at x=(?<bx>-?\\d+), y=(?<by>-?\\d+)".toRegex()
        private const val ROW = 2000000

        private const val WIDTH = 4000000
        private const val HEIGHT = 4000000

        private val WIDTH_RANGE = (0..WIDTH)
        private val HEIGHT_RANGE = (0..HEIGHT)
    }

    override fun handleLine(lines: MutableList<Sensor>, line: String) {
        val (sx, sy, bx, by) = SENSOR_INPUT_PATTERN.find(line)!!.destructured
        lines.add(
            Sensor(
                Point(sx.toInt(), sy.toInt()),
                Point(bx.toInt(), by.toInt()),
            )
        )
    }

    override fun part1(lines: List<Sensor>) =
        lines.map { it.beacon }.toSet()
            .let { allBeacons ->
                lines.flatMap { sensor ->
                    sensor.exclusionOnRow(ROW)
                }.toSet()
                    .filter { !allBeacons.map { it.y }.toSet().contains(it) }
                    .size
            }

    override fun part2(lines: List<Sensor>): Long {
        var foundGap: Int?
        var row = HEIGHT_RANGE.first
        do {
            foundGap = lines.map { sensor ->
                sensor.exclusionOnRow(row, WIDTH_RANGE, HEIGHT_RANGE)
            }.findGap()
            if (foundGap == null) {
                row++
            }
        } while (foundGap == null && HEIGHT_RANGE.contains(row))

        return Point(foundGap!!, row)
            .let { it.x * 4000000L + it.y }
    }

    private fun List<IntRange>.findGap(): Int? {
        val sorted = sortedBy { it.first }
        var currentEnd = sorted.first().last

        sorted.drop(1).forEach {
            if (it.first <= currentEnd) {
                currentEnd = max(currentEnd, it.last)
            } else {
                return it.first - 1
            }
        }
        return null
    }
}