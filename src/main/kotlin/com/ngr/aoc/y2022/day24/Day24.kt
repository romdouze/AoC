package com.ngr.aoc.y2022.day24

import com.ngr.aoc.y2022.Day
import java.awt.Point

class Day24 : Day<String, Int, Int>() {

    private companion object {
        private const val WALL = '#'
        private const val EMPTY = '.'
    }

    private lateinit var widthRange: IntRange
    private lateinit var heightRange: IntRange
    private lateinit var innerWidthRange: IntRange
    private lateinit var innerHeightRange: IntRange
    private lateinit var start: Point
    private lateinit var end: Point

    private val initialBlizzards: MutableSet<Blizzard> = mutableSetOf()
    private var row = 0

    private val allActions: List<Dir?> = Dir.values().toList() + null

    override fun handleLine(lines: MutableList<String>, line: String) {
        lines.add(line)

        initialBlizzards.addAll(line.mapIndexedNotNull { x, c ->
            Dir.fromString(c)?.let { Blizzard(Point(x, row), it) }
        })
        row++
    }

    override fun part1(lines: List<String>): Int {
        initConstants(lines)

        val toVisit = ArrayDeque(listOf(State(Point(start), initialBlizzards.toSet(), emptyList())))
        val visited = mutableSetOf<State>()

        var bestPath: State? = null

        while (toVisit.isNotEmpty()) {
            visited.add(toVisit.removeFirst())
            val state = visited.last().clone()

            moveBlizzards(state.blizzards)
            val allBlizzardSpots = state.blizzards.map { it.pos }.toSet()

            allActions
                .filter { action ->
                    (state.location + action).let {
                        (it == end || (it == start && action == null)) || (inbound(it) && !allBlizzardSpots.contains(it))
                    }
                }
                .forEach { action ->
                    val newState = state.clone(state.location + action)
                    if (newState.location == end &&
                        newState.path.size < (bestPath?.path?.size ?: Int.MAX_VALUE)
                    ) {
                        bestPath = newState
                        println("new best bath: $bestPath")
                    } else {
                        if (!visited.contains(newState)) {
                            toVisit.add(newState)
                        }
                    }
                }

        }

        return bestPath!!.path.size
    }

    override fun part2(lines: List<String>): Int {
        TODO("Not yet implemented")
    }

    private fun moveBlizzards(allBlizzards: Set<Blizzard>) {
        allBlizzards.onEach { it.move(widthRange, heightRange) }
    }

    private fun inbound(pos: Point) =
        innerWidthRange.contains(pos.x) && innerHeightRange.contains(pos.y)

    private fun initConstants(lines: List<String>) {
        widthRange = lines[0].indices
        innerWidthRange = (1 until lines[0].length - 1)
        heightRange = lines.indices
        innerHeightRange = (1 until lines.size - 1)
        start = Point(lines[0].indexOfFirst { it == EMPTY }, 0)
        end = Point(lines.last().indexOfFirst { it == EMPTY }, heightRange.last)
    }
}