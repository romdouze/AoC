package com.ngr.aoc.y2022.day17

import com.ngr.aoc.y2022.Day
import com.ngr.aoc.y2022.day17.Move.D
import com.ngr.aoc.y2022.day17.Move.U

class Day17 : Day<Move, Long, Long>() {

    companion object {
        private const val WIDTH = 7
        val WIDTH_RANGE = (0 until WIDTH)
        const val FLOOR = 0

        private val SHAPE_ORDER = listOf(Shape.`-`, Shape.X, Shape.L, Shape.I, Shape.O)
        private const val SPAWN_OFFSET_X = 2
        private const val SPAWN_OFFSET_Y = 4
    }

    override fun handleLine(lines: MutableList<Move>, line: String) {
        lines.addAll(
            line.toCharArray()
                .map { Move.fromChar(it) }
        )
    }

    override fun part1(lines: List<Move>): Long {
        val shapeCycle = SHAPE_ORDER.toMutableList()
        val moveCycle = lines.toMutableList()
        val allRocks = mutableSetOf<LPoint>()
        var rockCount = 0
        var top = FLOOR - 1L

        val maxRocks = 2022
        while (rockCount < maxRocks) {
            val shape = shapeCycle.cycle()
            val newRock = Rock.spawn(shape, LPoint(SPAWN_OFFSET_X.toLong(), top + SPAWN_OFFSET_Y))

            do {
                val move = moveCycle.cycle()
                if (newRock.canMove(move, allRocks)) {
                    newRock.move(move)
                }
                newRock.move(D)
            } while (!newRock.collision(allRocks) && newRock.inbound())

            newRock.move(U)
            allRocks.addAll(newRock.points)
            top = maxOf(top, newRock.top)

            rockCount++
        }

        return top + 1
    }

    override fun part2(lines: List<Move>): Long {
        val shapeCycle = SHAPE_ORDER.toMutableList()
        val moveCycle = lines.toMutableList()
        val allRocks = mutableSetOf<LPoint>()
        var rockCount = 0L
        var top = FLOOR - 1L

        val maxRocks = 1000000000000L
        while (rockCount < maxRocks) {
            val shape = shapeCycle.cycle()
            val newRock = Rock.spawn(shape, LPoint(SPAWN_OFFSET_X.toLong(), top + SPAWN_OFFSET_Y))

            do {
                val move = moveCycle.cycle()
                if (newRock.canMove(move, allRocks)) {
                    newRock.move(move)
                }
                newRock.move(D)
            } while (!newRock.collision(allRocks) && newRock.inbound())

            newRock.move(U)
            allRocks.addAll(newRock.points)
            top = maxOf(top, newRock.top)

            rockCount++
        }

        return top + 1
    }

    private fun Rock.collision(others: Set<LPoint>) =
        points.any { others.contains(it) }

    private fun Rock.canMove(move: Move, others: Set<LPoint>): Boolean {
        val movedRock = clone().apply { move(move) }
        return movedRock.inbound() && !movedRock.collision(others)
    }

}