package com.ngr.aoc.y2024.day9

import kotlin.math.min

object FileSystem {
    fun singleBlockCompressionChecksum(blocks: List<Int>, free: List<Int>): Long {
        var checksum = 0L

        var compressedPos = 0
        var endId = blocks.lastIndex
        var endAdded = 0

        var id = 0
        while (id < endId) {
            repeat(blocks[id]) {
                checksum += id * compressedPos
                compressedPos++
            }
            var added = 0
            while (added < free[id] && endId > id) {
                val toAdd = min((free[id] - added), (blocks[endId] - endAdded))
                repeat(toAdd) {
                    checksum += endId * compressedPos
                    compressedPos++
                }
                endAdded += toAdd
                if (endAdded >= blocks[endId]) {
                    endAdded = 0
                    endId--
                }
                added += toAdd
            }
            id++
        }

        if (endAdded > 0) {
            repeat(blocks[endId] - endAdded) {
                checksum += endId * compressedPos
                compressedPos++
            }
        }

        return checksum
    }

    fun wholeFileCompressionChecksum(blocks: List<Int>, free: List<Int>): Long {
        val compressedFiles = mutableListOf<Int>()
        val emptySpace = mutableMapOf<Int, Int>()
        var compressedPos = 0

        blocks.indices.forEach { id ->
            repeat(blocks[id]) {
                compressedFiles.add(id)
                compressedPos++
            }
            val free = free.getOrNull(id)
            if (free != null && free > 0) {
                emptySpace[compressedPos] = free
                repeat(free) {
                    compressedFiles.add(-1)
                    compressedPos++
                }
            }
        }

        blocks.indices.reversed().forEach { id ->
            val blockSize = blocks[id]
            val blockPos = compressedFiles.indexOfFirst { it == id }

            emptySpace.entries.sortedBy { it.key }
                .takeWhile { it.key < blockPos }
                .firstOrNull { it.value >= blocks[id] }
                ?.also { space ->
                    val spacePos = space.key
                    val spaceSize = space.value

                    repeat(blockSize) {
                        compressedFiles[spacePos + it] = id
                        compressedFiles[blockPos + it] = -1
                    }
                    emptySpace.remove(spacePos)
                    if (spaceSize > blockSize) {
                        emptySpace[spacePos + blockSize] = spaceSize - blockSize
                    }

                    val spaceToExpand = emptySpace.entries
                        .firstOrNull { it.key + it.value == blockPos }
                    val spaceToJoin = emptySpace.entries
                        .firstOrNull { it.key == blockPos + blockSize }

                    when {
                        spaceToExpand != null && spaceToJoin != null -> {
                            emptySpace.remove(spaceToJoin.key)
                            emptySpace[spaceToExpand.key] = spaceToExpand.value + blockSize + spaceToJoin.value
                        }

                        spaceToExpand != null -> {
                            emptySpace[spaceToExpand.key] = spaceToExpand.value + blockSize
                        }

                        spaceToJoin != null -> {
                            emptySpace.remove(spaceToJoin.key)
                            emptySpace[blockPos] = blockSize + spaceToJoin.value
                        }
                    }
                }
        }

        return compressedFiles.foldIndexed(0L) { pos, checksum, value ->
            if (value == -1) {
                checksum
            } else {
                checksum + value * pos
            }
        }
    }
}