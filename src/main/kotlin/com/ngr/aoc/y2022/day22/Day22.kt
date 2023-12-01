package com.ngr.aoc.y2022.day22

import com.ngr.aoc.Day
import com.ngr.aoc.y2022.day22.Dir.D
import com.ngr.aoc.y2022.day22.Dir.L
import com.ngr.aoc.y2022.day22.Dir.R
import com.ngr.aoc.y2022.day22.Dir.U
import java.awt.Point
import java.io.InputStream

class Day22 : Day<Instruction, Int, Int>() {

    private companion object {
        private const val FACE_SIZE = 50
    }

    private lateinit var map: WarpableMapWalker

    override fun readInput(data: InputStream): List<Instruction> {
        val drawing = mutableListOf<String>()
        val path: String

        data.bufferedReader().let { reader ->
            do {
                val line = reader.readLine()
                drawing.add(line)
            } while (line.isNotEmpty())
            drawing.removeLast()

            path = reader.readLine()
        }
        map = WarpableMapWalker(drawing, FACE_SIZE)
        return Instruction.fromString(path).toList()
    }

    override fun handleLine(lines: MutableList<Instruction>, line: String) {
        // DO NOTHING
    }

    override fun part1(lines: List<Instruction>): Int {
        doInstructions(
            lines,
            mapOf(
                1 to mapOf(
                    R to { Location(2, Point(0, it.y), R) },
                    D to { Location(4, Point(it.x, 0), D) },
                    L to { Location(2, Point(FACE_SIZE - 1, it.y), L) },
                    U to { Location(7, Point(it.x, FACE_SIZE - 1), U) },
                ),
                2 to mapOf(
                    R to { Location(1, Point(0, it.y), R) },
                    D to { Location(2, Point(it.x, 0), D) },
                    L to { Location(1, Point(FACE_SIZE - 1, it.y), L) },
                    U to { Location(2, Point(it.x, FACE_SIZE - 1), U) },
                ),
                4 to mapOf(
                    R to { Location(4, Point(0, it.y), R) },
                    D to { Location(7, Point(it.x, 0), D) },
                    L to { Location(4, Point(FACE_SIZE - 1, it.y), L) },
                    U to { Location(1, Point(it.x, FACE_SIZE - 1), U) },
                ),
                6 to mapOf(
                    R to { Location(7, Point(0, it.y), R) },
                    D to { Location(9, Point(it.x, 0), D) },
                    L to { Location(7, Point(FACE_SIZE - 1, it.y), L) },
                    U to { Location(9, Point(it.x, FACE_SIZE - 1), U) },
                ),
                7 to mapOf(
                    R to { Location(6, Point(0, it.y), R) },
                    D to { Location(1, Point(it.x, 0), D) },
                    L to { Location(6, Point(FACE_SIZE - 1, it.y), L) },
                    U to { Location(4, Point(it.x, FACE_SIZE - 1), U) },
                ),
                9 to mapOf(
                    R to { Location(9, Point(0, it.y), R) },
                    D to { Location(6, Point(it.x, 0), D) },
                    L to { Location(9, Point(FACE_SIZE - 1, it.y), L) },
                    U to { Location(6, Point(it.x, FACE_SIZE - 1), U) },
                )
            )
        )

        return map.score()
    }

    override fun part2(lines: List<Instruction>): Int {
        doInstructions(
            lines,
            mapOf(
                1 to mapOf(
                    R to { Location(2, Point(0, it.y), R) },
                    D to { Location(4, Point(it.x, 0), D) },
                    L to { Location(6, Point(0, FACE_SIZE - it.y - 1), R) },
                    U to { Location(9, Point(0, it.x), R) },
                ),
                2 to mapOf(
                    R to { Location(7, Point(FACE_SIZE - 1, FACE_SIZE - it.y - 1), L) },
                    D to { Location(4, Point(FACE_SIZE - 1, it.x), L) },
                    L to { Location(1, Point(FACE_SIZE - 1, it.y), L) },
                    U to { Location(9, Point(it.x, FACE_SIZE - 1), U) },
                ),
                4 to mapOf(
                    R to { Location(2, Point(it.y, FACE_SIZE - 1), U) },
                    D to { Location(7, Point(it.x, 0), D) },
                    L to { Location(6, Point(it.y, 0), D) },
                    U to { Location(1, Point(it.x, FACE_SIZE - 1), U) },
                ),
                6 to mapOf(
                    R to { Location(7, Point(0, it.y), R) },
                    D to { Location(9, Point(it.x, 0), D) },
                    L to { Location(1, Point(0, FACE_SIZE - it.y - 1), R) },
                    U to { Location(4, Point(0, it.x), R) },
                ),
                7 to mapOf(
                    R to { Location(2, Point(FACE_SIZE - 1, FACE_SIZE - it.y - 1), L) },
                    D to { Location(9, Point(FACE_SIZE - 1, it.x), L) },
                    L to { Location(6, Point(FACE_SIZE - 1, it.y), L) },
                    U to { Location(4, Point(it.x, FACE_SIZE - 1), U) },
                ),
                9 to mapOf(
                    R to { Location(7, Point(it.y, FACE_SIZE - 1), U) },
                    D to { Location(2, Point(it.x, 0), D) },
                    L to { Location(1, Point(it.y, 0), D) },
                    U to { Location(6, Point(it.x, FACE_SIZE - 1), U) },
                )
            )
        )

        return map.score()
    }

    private fun doInstructions(lines: List<Instruction>, faceWarper: FaceWarper) {
        map.goToStartingPosition()

        lines.forEach { instruction ->
            map.walk(instruction, faceWarper)
        }
    }
}