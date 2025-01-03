package com.ngr.aoc.y2024.day14

import java.awt.Point

data class Robot(
    val p: Point,
    val v: Point,
) {
    companion object {
        private val ROBOT_PATTERN = "p=(?<px>\\d+),(?<py>\\d+) v=(?<vx>-?\\d+),(?<vy>-?\\d+)".toRegex()

        fun fromString(s: String) =
            ROBOT_PATTERN.find(s)!!.let {
                Robot(
                    Point(it.groups["px"]!!.value.toInt(), it.groups["py"]!!.value.toInt()),
                    Point(it.groups["vx"]!!.value.toInt(), it.groups["vy"]!!.value.toInt()),
                )
            }
    }
}

class Floor(
    initialRobots: List<Robot>,
) {
    private companion object {
        private const val WIDTH = 101
        private const val HEIGHT = 103
    }

    var currentRobots: List<Robot> = initialRobots

    val safetyFactor: Int
        get() : Int {
            val quadrants = listOf(
                mutableListOf<Robot>(),
                mutableListOf<Robot>(),
                mutableListOf<Robot>(),
                mutableListOf<Robot>(),
            )

            currentRobots.forEach {
                with(it.p) {
                    if (x < WIDTH / 2 && y < HEIGHT / 2) quadrants[0].add(it)
                    if (x < WIDTH / 2 && y > HEIGHT / 2) quadrants[1].add(it)
                    if (x > WIDTH / 2 && y > HEIGHT / 2) quadrants[2].add(it)
                    if (x > WIDTH / 2 && y < HEIGHT / 2) quadrants[3].add(it)
                }
            }

            return quadrants.fold(1) { acc, robots -> acc * robots.size }
        }

    fun moveAll(n: Int) {
        currentRobots = currentRobots.map {
            Robot(
                Point(
                    (it.p.x + n * it.v.x).warp(WIDTH),
                    (it.p.y + n * it.v.y).warp(HEIGHT),
                ),
                it.v,
            )
        }
    }

    private fun Int.warp(range: Int) =
        this.mod(range)
}