package com.ngr.aoc.y2024.day15

import java.awt.Point

open class Warehouse(
    open val walls: Set<Point>,
    initialBoxes: Set<Point>,
    robotStart: Point,
) {
    open val width = WIDTH
    val height = HEIGHT

    open val currentBoxes = initialBoxes.associateWith { Box(it) }
        .toMutableMap()
    open var currentRobot = robotStart

    fun moveRobot(moves: List<Dir>) {
        moves.forEach { m ->
            move(m)
        }
    }

    fun move(move: Dir) {
        val dest = currentRobot + move

        if (dest.isWall()) return
        if (!dest.isBox()) {
            currentRobot = dest
            return
        }

        pushBoxes(dest, move)
    }

    protected open fun pushBoxes(dest: Point, move: Dir) {
        var endOfBoxStack = dest + move
        while (endOfBoxStack.isBox()) {
            endOfBoxStack += move
        }

        if (endOfBoxStack.isWall()) return

        currentRobot = dest
        currentBoxes.remove(dest)
        currentBoxes[endOfBoxStack] = Box(endOfBoxStack)
    }

    fun score() =
        currentBoxes.values.toSet().sumOf { it.score }

    protected fun Point.isWall() =
        this in walls

    protected fun Point.isBox() =
        currentBoxes.containsKey(this)

    private fun print() {
        System.err.print(" ")
        (0 until width).forEach {
            System.err.print(it)
        }
        System.err.println("")
        (0 until height).forEach { y ->
            System.err.print(y)
            (0 until width).forEach { x ->
                val p = Point(x, y)
                when {
                    p == currentRobot -> System.err.print("@")
                    p.isWall() -> System.err.print("#")
                    p.isBox() -> System.err.print("O")
                    else -> System.err.print(".")
                }
            }
            System.err.println("")
        }
    }
}

class WideWarehouse(
    walls: Set<Point>,
    initialBoxes: Set<Point>,
    robotStart: Point,
) : Warehouse(walls, initialBoxes, robotStart) {
    override val width = WIDTH * 2

    override val walls = walls.flatMap {
        listOf(Point(it.x * 2, it.y), Point(it.x * 2 + 1, it.y))
    }.toSet()
    override val currentBoxes = initialBoxes.map {
        listOf(Point(it.x * 2, it.y), Point(it.x * 2 + 1, it.y))
            .let { it to Box(it) }
    }.let {
        val m = it.flatMap { pair ->
            pair.first.map { it to pair.second }
        }
        mutableMapOf(*m.toTypedArray())
    }
    override var currentRobot = Point(robotStart.x * 2, robotStart.y)

    override fun pushBoxes(dest: Point, move: Dir) {
        val pushedBoxes = mutableSetOf<Box>()
        var endOfBoxStack = listOf(dest)

        when (move) {
            in listOf(Dir.`<`, Dir.`>`) -> {
                endOfBoxStack = listOf(dest + move)
                while (endOfBoxStack.any { it.isBox() }) {
                    pushedBoxes.add(currentBoxes[endOfBoxStack[0]]!!)
                    endOfBoxStack = listOf(endOfBoxStack[0] + move)
                }
            }

            else -> {
                while (endOfBoxStack.none { it.isWall() } && endOfBoxStack.any { it.isBox() }) {
                    val boxRow = endOfBoxStack.mapNotNull { currentBoxes[it] }
                    pushedBoxes.addAll(boxRow)
                    endOfBoxStack = boxRow.flatMap { it.p }
                        .map { it + move }
                }

            }
        }

        if (endOfBoxStack.any { it.isWall() }) return

        currentRobot = dest
        pushedBoxes.forEach {
            it.p.forEach { currentBoxes.remove(it) }
        }
        pushedBoxes.forEach {
            it.p.map { it + move }
                .let {
                    val box = Box(it)
                    box.p.forEach { currentBoxes[it] = box }
                }
        }
    }
}

class Box(val p: List<Point>) {
    constructor(p: Point) : this(listOf(p))

    val score = p[0].x + p[0].y * 100

    fun move(dir: Dir) =
        Box(p.map { it + dir })

    override fun equals(other: Any?) =
        other is Box && p == other.p

    override fun hashCode() =
        p.hashCode()

    override fun toString() =
        p.toString()
}

const val WALL = '#'
const val BOX = 'O'
const val ROBOT = '@'

enum class Dir(val dx: Int, val dy: Int) {
    `>`(1, 0),
    `v`(0, 1),
    `<`(-1, 0),
    `^`(0, -1);

    companion object {
        fun fromString(str: String) =
            entries.first { it.name == str }
    }
}

operator fun Point.plus(dir: Dir) =
    Point(x + dir.dx, y + dir.dy)