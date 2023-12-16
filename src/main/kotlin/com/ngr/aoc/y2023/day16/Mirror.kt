package com.ngr.aoc.y2023.day16

import com.ngr.aoc.y2023.day16.Dir.DOWN
import com.ngr.aoc.y2023.day16.Dir.LEFT
import com.ngr.aoc.y2023.day16.Dir.RIGHT
import com.ngr.aoc.y2023.day16.Dir.UP
import java.awt.Point


enum class Mirror(
    val label: Char,
    val outDir: (Dir) -> List<Dir>
) {
    L(
        label = '\\',
        outDir = {
            when (it) {
                LEFT -> listOf(UP)
                RIGHT -> listOf(DOWN)
                UP -> listOf(LEFT)
                DOWN -> listOf(RIGHT)
            }
        }
    ),
    F(
        label = '/',
        outDir = {
            when (it) {
                LEFT -> listOf(DOWN)
                RIGHT -> listOf(UP)
                UP -> listOf(RIGHT)
                DOWN -> listOf(LEFT)
            }
        }
    ),
    `-`(
        label = '-',
        outDir = {
            when (it) {
                LEFT -> listOf(LEFT)
                RIGHT -> listOf(RIGHT)
                UP -> listOf(LEFT, RIGHT)
                DOWN -> listOf(LEFT, RIGHT)
            }
        }
    ),
    `|`(
        label = '|',
        outDir = {
            when (it) {
                LEFT -> listOf(UP, DOWN)
                RIGHT -> listOf(UP, DOWN)
                UP -> listOf(UP)
                DOWN -> listOf(DOWN)
            }
        }
    );

    companion object {
        fun fromChar(c: Char) =
            values().firstOrNull { it.label == c }
    }
}

operator fun Point.plus(dir: Dir) =
    Point(x + dir.dx, y + dir.dy)

enum class Dir(val dx: Int, val dy: Int) {
    LEFT(-1, 0),
    RIGHT(1, 0),
    UP(0, -1),
    DOWN(0, 1),
}