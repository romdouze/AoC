package com.ngr.aoc.y2023.day10

import com.ngr.aoc.y2023.day10.Dir.DOWN
import com.ngr.aoc.y2023.day10.Dir.LEFT
import com.ngr.aoc.y2023.day10.Dir.RIGHT
import com.ngr.aoc.y2023.day10.Dir.UP
import java.awt.Point

enum class PipeBit(
    val outDir: (Dir) -> Dir?,
    val sides: (Dir, Point) -> Pair<Set<Point>, Set<Point>>,
    val turn: (Dir) -> Dir?,
) {
    `-`(
        outDir = {
            when (it) {
                RIGHT -> RIGHT
                LEFT -> LEFT
                else -> null
            }
        },
        sides = { dir, point ->
            val rightSideDir = when (dir) {
                RIGHT -> DOWN
                LEFT -> UP
                else -> throw IllegalArgumentException("Cannot enter $this from $dir")
            }
            val leftSideDir = when (dir) {
                RIGHT -> UP
                LEFT -> DOWN
                else -> throw IllegalArgumentException("Cannot enter $this from $dir")
            }
            setOf(point + rightSideDir) to setOf(point + leftSideDir)
        },
        turn = { null },
    ),
    `7`(
        outDir = {
            when (it) {
                RIGHT -> DOWN
                UP -> LEFT
                else -> null
            }
        },
        sides = { dir, point ->
            val curvedSide = setOf(Point(0, -1), Point(1, -1), Point(1, 0))
            val rightSidePoints = when (dir) {
                RIGHT -> emptySet()
                UP -> curvedSide
                else -> throw IllegalArgumentException("Cannot enter $this from $dir")
            }
            val leftSidePoints = when (dir) {
                RIGHT -> curvedSide
                UP -> emptySet()
                else -> throw IllegalArgumentException("Cannot enter $this from $dir")
            }
            rightSidePoints.map { point + it }.toSet() to leftSidePoints.map { point + it }.toSet()
        },
        turn = {
            when (it) {
                RIGHT -> RIGHT
                UP -> LEFT
                else -> throw IllegalArgumentException("Cannot enter $this from $it")
            }
        }
    ),
    `|`(
        outDir = {
            when (it) {
                UP -> UP
                DOWN -> DOWN
                else -> null
            }
        },
        sides = { dir, point ->
            val rightSideDir = when (dir) {
                UP -> RIGHT
                DOWN -> LEFT
                else -> throw IllegalArgumentException("Cannot enter $this from $dir")
            }
            val leftSideDir = when (dir) {
                UP -> LEFT
                DOWN -> RIGHT
                else -> throw IllegalArgumentException("Cannot enter $this from $dir")
            }
            setOf(point + rightSideDir) to setOf(point + leftSideDir)
        },
        turn = { null },
    ),
    `J`(
        outDir = {
            when (it) {
                RIGHT -> UP
                DOWN -> LEFT
                else -> null
            }
        },
        sides = { dir, point ->
            val curvedSide = setOf(Point(0, 1), Point(1, 1), Point(1, 0))
            val rightSidePoints = when (dir) {
                RIGHT -> curvedSide
                DOWN -> emptySet()
                else -> throw IllegalArgumentException("Cannot enter $this from $dir")
            }
            val leftSidePoints = when (dir) {
                RIGHT -> emptySet()
                DOWN -> curvedSide
                else -> throw IllegalArgumentException("Cannot enter $this from $dir")
            }
            rightSidePoints.map { point + it }.toSet() to leftSidePoints.map { point + it }.toSet()
        },
        turn = {
            when (it) {
                RIGHT -> LEFT
                DOWN -> RIGHT
                else -> throw IllegalArgumentException("Cannot enter $this from $it")
            }
        }
    ),
    `L`(
        outDir = {
            when (it) {
                LEFT -> UP
                DOWN -> RIGHT
                else -> null
            }
        },
        sides = { dir, point ->
            val curvedSide = setOf(Point(-1, 0), Point(-1, 1), Point(0, 1))
            val rightSidePoints = when (dir) {
                LEFT -> emptySet()
                DOWN -> curvedSide
                else -> throw IllegalArgumentException("Cannot enter $this from $dir")
            }
            val leftSidePoints = when (dir) {
                LEFT -> curvedSide
                DOWN -> emptySet()
                else -> throw IllegalArgumentException("Cannot enter $this from $dir")
            }
            rightSidePoints.map { point + it }.toSet() to leftSidePoints.map { point + it }.toSet()
        },
        turn = {
            when (it) {
                LEFT -> RIGHT
                DOWN -> LEFT
                else -> throw IllegalArgumentException("Cannot enter $this from $it")
            }
        }
    ),
    `F`(
        outDir = {
            when (it) {
                UP -> RIGHT
                LEFT -> DOWN
                else -> null
            }
        },
        sides = { dir, point ->
            val curvedSide = setOf(Point(-1, 0), Point(-1, -1), Point(0, -1))
            val rightSidePoints = when (dir) {
                UP -> emptySet()
                LEFT -> curvedSide
                else -> throw IllegalArgumentException("Cannot enter $this from $dir")
            }
            val leftSidePoints = when (dir) {
                UP -> curvedSide
                LEFT -> emptySet()
                else -> throw IllegalArgumentException("Cannot enter $this from $dir")
            }
            rightSidePoints.map { point + it }.toSet() to leftSidePoints.map { point + it }.toSet()
        },
        turn = {
            when (it) {
                UP -> RIGHT
                LEFT -> LEFT
                else -> throw IllegalArgumentException("Cannot enter $this from $it")
            }
        }
    );

    companion object {
        fun fromChar(char: Char) =
            values().first { it.name == char.toString() }
    }
}

data class LoopItem(
    val point: Point,
    val sides: Pair<Set<Point>, Set<Point>>,
    val turn: Dir?,
)

operator fun Point.plus(dir: Dir) =
    Point(x + dir.dx, y + dir.dy)

operator fun Point.plus(other: Point) =
    Point(x + other.x, y + other.y)

fun Point.plus(dx: Int, dy: Int) =
    Point(x + dx, y + dy)

enum class Dir(val dx: Int, val dy: Int) {
    UP(0, -1), DOWN(0, 1), RIGHT(1, 0), LEFT(-1, 0);
}