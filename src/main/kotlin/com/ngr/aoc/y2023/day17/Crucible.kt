package com.ngr.aoc.y2023.day17

import com.ngr.aoc.y2023.day17.CrucibleType.REGULAR
import com.ngr.aoc.y2023.day17.CrucibleType.ULTRA
import java.awt.Point


data class State(
    val crucibleType: CrucibleType,
    val pos: Point,
    val dir: Dir,
    val path: List<Pair<Point, Dir>>,
    val heatLoss: Int,
) {

    val currentStraight = path.size - (path.indexOfLast { it.second != dir } + 1)

    fun turnOptions() =
        when (crucibleType) {
            REGULAR -> listOf(
                dir.leftTurn(),
                dir.rightTurn()
            ) + (if (currentStraight < 3) listOf(dir) else emptyList())

            ULTRA -> when {
                currentStraight < 4 -> listOf(dir)
                currentStraight < 10 -> listOf(dir.leftTurn(), dir, dir.rightTurn())
                else -> listOf(dir.leftTurn(), dir.rightTurn())
            }
        }

    override fun equals(other: Any?): Boolean {
        return if (other is State) {
            pos == other.pos && dir == other.dir && currentStraight == other.currentStraight
        } else super.equals(other)
    }

    override fun hashCode(): Int {
        return pos.hashCode() + 17 * dir.hashCode() + 31 * currentStraight.hashCode()
    }

    override fun toString() =
        "[$heatLoss] ${pos.print()}-$dir => ${path.joinToString { "${it.first.print()}-${it.second}" }}"
}

enum class CrucibleType {
    REGULAR, ULTRA
}

enum class Dir(
    val dx: Int,
    val dy: Int,
    val leftTurn: () -> Dir,
    val rightTurn: () -> Dir,
) {
    UP(
        dx = 0,
        dy = -1,
        leftTurn = { LEFT },
        rightTurn = { RIGHT },
    ),
    DOWN(
        dx = 0,
        dy = 1,
        leftTurn = { RIGHT },
        rightTurn = { LEFT },
    ),
    LEFT(
        dx = -1,
        dy = 0,
        leftTurn = { DOWN },
        rightTurn = { UP },
    ),
    RIGHT(
        dx = 1,
        dy = 0,
        leftTurn = { UP },
        rightTurn = { DOWN },
    );
}

operator fun Point.plus(dir: Dir) =
    Point(x + dir.dx, y + dir.dy)

fun Point.print() = "($x,$y)"