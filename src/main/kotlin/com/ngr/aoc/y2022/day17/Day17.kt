package com.ngr.aoc.y2022.day17

import com.ngr.aoc.y2022.Day
import com.ngr.aoc.y2022.day17.Move.D
import com.ngr.aoc.y2022.day17.Move.U
import java.lang.Long.max

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

    override fun part1(lines: List<Move>) =
        stackRocks(lines, 2022)

    override fun part2(lines: List<Move>): Long {
        return stackRocks(lines, 1000000000000L)
    }

    private fun stackRocks(lines: List<Move>, maxRocks: Long): Long {
        val shapeCycle = Cycle(SHAPE_ORDER)
        val moveCycle = Cycle(lines)
        val allRocks = mutableSetOf<LPoint>()
        var rockCount = 0L
        var top = FLOOR - 1L
        var tops = WIDTH_RANGE.map { top }

        val cache = mutableMapOf<CacheKey, Pair<CachedResult, Long>>()

        while (rockCount < maxRocks) {

            val cacheKey = CacheKey(
                shapeCycle.cursor,
                moveCycle.cursor,
                tops,
            )

            cache[cacheKey]?.let {
                val cacheValue = cache[cacheKey]!!
                val (cacheResult, count) = cacheValue
                val cacheResultsByCount = cache.values.associate { it.second to it.first }

                val cumulativeForRepetition = computeCumulative(count, rockCount, cacheResultsByCount)

                val cycleLength = rockCount - count
                val repetitions = (maxRocks - rockCount) / cycleLength

                shapeCycle.shift(cumulativeForRepetition.shapeCursorDelta * repetitions)
                moveCycle.shift(cumulativeForRepetition.shapeCursorDelta * repetitions)
                rockCount += cycleLength * repetitions
                tops = tops.mapIndexed { x, t -> t + cumulativeForRepetition.topsDelta[x] * repetitions }
                top = tops.max()

                val missingRocks = maxRocks - rockCount
                val cumulativeForCompletion = computeCumulative(count, count + missingRocks, cacheResultsByCount)

                shapeCycle.shift(cumulativeForCompletion.shapeCursorDelta)
                moveCycle.shift(cumulativeForCompletion.shapeCursorDelta)
                rockCount += missingRocks - 1
                tops = tops.mapIndexed { x, t -> t + cumulativeForCompletion.topsDelta[x] }
                top = tops.max()

            } ?: run {
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

                val rockTops = newRock.tops
                val newTops = tops.mapIndexed { x, t -> max(t, rockTops[x]) }

                cache[cacheKey] = CachedResult(
                    shapeCycle.cursor - cacheKey.shapeCursor.toLong(),
                    moveCycle.cursor - cacheKey.moveCursor.toLong(),
                    newTops.mapIndexed { x, t -> t - tops[x] }
                ) to rockCount

                tops = newTops
            }

            rockCount++
        }

        return top + 1
    }

    private fun computeCumulative(
        start: Long,
        end: Long,
        cacheResultsByCount: Map<Long, CachedResult>,
    ): CachedResult {
        var cumulativeShapeCursorDelta = 0L
        var cumulativeMoveCursorDelta = 0L
        val cumulativeTopsDeltas = WIDTH_RANGE.map { 0L }.toMutableList()
        (start until end).forEach { i ->
            cumulativeShapeCursorDelta += cacheResultsByCount[i]!!.shapeCursorDelta
            cumulativeMoveCursorDelta += cacheResultsByCount[i]!!.moveCursorDelta
            WIDTH_RANGE.onEach { x -> cumulativeTopsDeltas[x] += cacheResultsByCount[i]!!.topsDelta[x] }
        }
        return CachedResult(cumulativeShapeCursorDelta, cumulativeMoveCursorDelta, cumulativeTopsDeltas)
    }

    private fun Rock.collision(others: Set<LPoint>) =
        points.any { others.contains(it) }

    private fun Rock.canMove(move: Move, others: Set<LPoint>): Boolean {
        val movedRock = clone().apply { move(move) }
        return movedRock.inbound() && !movedRock.collision(others)
    }
}