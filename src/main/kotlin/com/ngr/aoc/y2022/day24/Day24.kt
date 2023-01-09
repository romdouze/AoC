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
    private var cycleLength: Int = Int.MAX_VALUE

    private var row = 0
    private val initialBlizzards: MutableSet<Blizzard> = mutableSetOf()
    private val blizzardCache: MutableMap<Int, Set<Point>> = mutableMapOf()

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
        println("cycleLength: $cycleLength")

        val bestPath = findBestPath(State(Point(start), emptyList(), cycleLength), start, end)

        return bestPath.clock
    }

    override fun part2(lines: List<String>): Int {

        val firstLeg = findBestPath(State(Point(start), emptyList(), cycleLength), start, end)
        val secondLeg = findBestPath(firstLeg, end, start)
        val thirdLeg = findBestPath(secondLeg, start, end)

        return thirdLeg.clock
    }

    private fun findBestPath(initialState: State, start: Point, end: Point): State {
        val toVisit = ArrayDeque(listOf(initialState))
        val visited = mutableSetOf<State>()

        var bestPath: State? = null

        while (toVisit.isNotEmpty() && bestPath == null) {
            val state = toVisit.removeFirst()
            visited.add(state)

            val allBlizzardSpots = projectBlizzards(state.cycleClock + 1, initialBlizzards)

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
                        println("found best path: $bestPath")
                    } else {
                        if (!toVisit.contains(newState) && !visited.contains(newState)) {
                            toVisit.add(newState)
                        }
                    }
                }

        }

        println("visited: ${visited.size}")

        return bestPath!!
    }

    private fun projectBlizzards(clock: Int, allBlizzards: Set<Blizzard>) =
        blizzardCache.computeIfAbsent(clock % cycleLength) {
            allBlizzards.map { it.project(clock, innerWidthRange, innerHeightRange) }.toSet()
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
        cycleLength =
            lcm(innerWidthRange.last - innerWidthRange.first + 1, innerHeightRange.last - innerHeightRange.first + 1)
    }
}