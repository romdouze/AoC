package com.ngr.aoc.y2022.day22

import com.ngr.aoc.y2022.day22.WarpableMapWalker.Companion.EMPTY
import com.ngr.aoc.y2022.day22.WarpableMapWalker.Companion.NOTHING

class WarpingChunkWalker(
    fullChunk: List<Char>,
    private val reverse: Boolean
) {

    private val offset = fullChunk.indexOfFirst { it != NOTHING }
    private val trimmedChunk = fullChunk.joinToString("") { it.toString() }
        .trim().toCharArray()

    fun walk(from: Int, steps: Int): Int {
        var count = 0
        var to = from - offset

        val step = if (reverse) -1 else 1
        while (count < steps && trimmedChunk[(to + step).mod(trimmedChunk.size)] == EMPTY) {
            to = (to + step).mod(trimmedChunk.size)
            count++
        }

        return to + offset
    }

}