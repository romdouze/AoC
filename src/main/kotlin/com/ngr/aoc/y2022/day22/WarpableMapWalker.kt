package com.ngr.aoc.y2022.day22

import com.ngr.aoc.y2022.day22.Dir.*
import java.awt.Point

class WarpableMapWalker(private val map: List<String>) {

    companion object {
        const val WALL = '#'
        const val EMPTY = '.'
        const val NOTHING = ' '
    }

    lateinit var location: Location private set

    private val heightRange = map.indices

    fun goToStartingPosition() {
        location = Location(Point(map[0].indexOf(EMPTY), 0), R)
    }

    fun walk(instruction: Instruction) {
        when (instruction) {
            is Rotate -> location.rotate(instruction.rotation)
            is Move -> {
                when (location.dir) {
                    R, L -> WarpingChunkWalker(map[location.pos.y].toCharArray().toList(), location.dir == L)
                        .walk(location.pos.x, instruction.steps)
                        .also { location.pos.x = it }
                    U, D -> WarpingChunkWalker(heightRange.map { map[Point(location.pos.x, it)] }, location.dir == U)
                        .walk(location.pos.y, instruction.steps)
                        .also { location.pos.y = it }
                }
            }
        }
    }

//    private fun reach(move: Move): Int {
//
//    }

    operator fun List<String>.get(point: Point) =
        map[point.y][point.x]
}