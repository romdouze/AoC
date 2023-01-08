package com.ngr.aoc.y2022.day22

import com.ngr.aoc.y2022.day22.Dir.R
import java.awt.Point

class WarpableMapWalker(
    drawing: List<String>,
    private val faceSize: Int,
) {

    companion object {
        const val WALL = '#'
        const val EMPTY = '.'
        const val NOTHING = ' '
    }

    private val map: Cube
    private val faceOffsets: Map<Int, Point>

    init {
        val width = drawing[0].length / faceSize
        val faces = mutableMapOf<Int, MutableList<CharSequence>>()
        drawing.chunked(faceSize)
            .forEachIndexed { y, rows ->
                rows.forEach { row ->
                    row.chunked(faceSize)
                        .forEachIndexed { x, chunk ->
                            faces[y * width + x]?.add(chunk)
                                ?: run { faces[y * width + x] = mutableListOf(chunk) }
                        }
                }
            }

        map = faces.filter { it.value.none { it.contains(NOTHING) } }.toMap()
        faceOffsets = map.keys.associateWith { Point(it % width * faceSize, it / width * faceSize) }
    }

    lateinit var location: Location private set

    fun goToStartingPosition() {
        map.keys.min().let { startingFace ->
            location = Location(startingFace, Point(map[startingFace]!![0].indexOf(EMPTY), 0), R)
        }
    }

    fun walk(instruction: Instruction, faceWarper: FaceWarper) {
        when (instruction) {
            is Rotate -> location.rotate(instruction.rotation)
            is Move -> {
                var count = 0

                while (count < instruction.steps && map.canWalk(location, faceWarper)) {
                    location.walk()
                    if (!map.inbound(location.pos)) {
                        location = faceWarper.warp(location)
                    }
                    count++
                }
            }
        }
    }

    fun score() =
        faceOffsets[location.face]!!.let { offset ->
            1000 * (offset.y + location.pos.y + 1) + 4 * (offset.x + location.pos.x + 1) + location.dir.score
        }

    private val Dir.score
        get() =
            when (this) {
                R -> 0
                Dir.D -> 1
                Dir.L -> 2
                Dir.U -> 3
            }
}