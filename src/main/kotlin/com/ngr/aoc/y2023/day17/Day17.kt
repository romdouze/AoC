package com.ngr.aoc.y2023.day17

import com.ngr.aoc.Day
import com.ngr.aoc.y2023.day17.CrucibleType.REGULAR
import com.ngr.aoc.y2023.day17.CrucibleType.ULTRA
import com.ngr.aoc.y2023.day17.Dir.DOWN
import com.ngr.aoc.y2023.day17.Dir.RIGHT
import java.awt.Point

class Day17 : Day<String, Int, Int>() {

    private val map = mutableMapOf<Point, Int>()

    private var rows = 0
    private var cols = 0

    override fun handleLine(lines: MutableList<String>, line: String) {
        map.putAll(line.mapIndexed { index, c -> Point(index, rows) to c.digitToInt() })
        cols = line.length
        rows++
    }

    override fun part1(lines: List<String>) =
        pushCrucibles(REGULAR).heatLoss

    override fun part2(lines: List<String>) =
        pushCrucibles(ULTRA).heatLoss

    private fun pushCrucibles(crucibleType: CrucibleType): State {
        val toVisit = ArrayDeque(
            listOf(
                State(
                    crucibleType = crucibleType,
                    pos = Point(0, 0),
                    dir = RIGHT,
                    path = emptyList(),
                    heatLoss = 0,
                ),
                State(
                    crucibleType = crucibleType,
                    pos = Point(0, 0),
                    dir = DOWN,
                    path = emptyList(),
                    heatLoss = 0,
                )
            ),
        )
        val bestMap = mutableMapOf<Point, MutableSet<State>>()
        var best = State(
            crucibleType = crucibleType,
            pos = Point(-1, -1),
            dir = RIGHT,
            path = emptyList(),
            heatLoss = Int.MAX_VALUE,
        )
        do {
            val current = toVisit.removeFirst()

            current.turnOptions()
                .filter { (current.pos + it).inbound() }
                .map { current.move(it) }
                .forEach { newState ->
                    if (bestMap[newState.pos] == null) {
                        bestMap[newState.pos] = mutableSetOf()
                    }
                    val knownBest = bestMap[newState.pos]?.firstOrNull { it == newState }
                    if (knownBest == null || knownBest.heatLoss > newState.heatLoss) {
                        if (newState.pos == Point(
                                cols - 1,
                                rows - 1
                            ) && newState.heatLoss < (bestMap[newState.pos]?.minOfOrNull { it.heatLoss }
                                ?: Int.MAX_VALUE)
                        ) {
                            println("new best: $newState")
                            best = newState
                        } else {
                            toVisit.add(newState)
                        }
                        bestMap[newState.pos]!!.remove(knownBest)
                        bestMap[newState.pos]!!.add(newState)
                    }
                }

        } while (toVisit.isNotEmpty())

        return best
    }

    private fun State.move(dir: Dir) =
        (pos + dir).let {
            State(
                crucibleType,
                it,
                dir,
                path + (it to dir),
                heatLoss + map[it]!!
            )
        }

    private fun Point.inbound() =
        (0 until cols).contains(x) &&
                (0 until rows).contains(y)
}