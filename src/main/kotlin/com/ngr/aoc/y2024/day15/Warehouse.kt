package com.ngr.aoc.y2024.day15

import com.ngr.aoc.y2024.day15.Dir.entries
import java.awt.Point

open class Warehouse(
    open val walls: Set<Point>,
    initialBoxes: Set<Point>,
    robotStart: Point,
) {
    val currentBoxes = initialBoxes.map { Box(it) }.toMutableSet()
    var currentRobot = robotStart

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

        var endOfBoxStack = dest + move
        while (endOfBoxStack.isBox()) {
            endOfBoxStack += move
        }

        if (endOfBoxStack.isWall()) return

        currentRobot = dest
        currentBoxes.remove(Box(dest))
        currentBoxes.add(Box(endOfBoxStack))
    }

    fun score() =
        currentBoxes.sumOf { it.score }

    private fun Point.isWall() =
        this in walls

    private fun Point.isBox() =
        currentBoxes.any { it.occupies(this) }
}

class WideWarehouse(
    walls: Set<Point>,
    initialBoxes: Set<Point>,
    robotStart: Point,
) : Warehouse(walls, initialBoxes, robotStart) {
    override val walls = walls.flatMap {
        listOf(Point(it.x * 2, it.y), Point(it.x * 2 + 1, it.y))
    }.toSet()
}

open class Box(val p: List<Point>) {
    constructor(p: Point) : this(listOf(p))

    val score = p[0].x + p[0].y * 100

    fun move(dir: Dir) =
        Box(p.map { it + dir })

    fun occupies(point: Point) =
        p.any { it == point }

    override fun equals(other: Any?) =
        other is Box && p == other.p

    override fun hashCode() =
        p.hashCode()
}

class WideBox(p: List<Point>) : Box(p) {
//    override
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