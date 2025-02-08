package com.ngr.aoc.y2024.day25

import com.ngr.aoc.Day
import java.io.InputStream

class Day25 : Day<String, Int, Int>() {

    private val locks = mutableListOf<List<Int>>()
    private val keys = mutableListOf<List<Int>>()

    override fun readInput(data: InputStream): List<String> {
        data.bufferedReader().use {
            do {
                var line: String?
                val schematic = mutableListOf<String>()

                line = it.readLine()
                do {
                    schematic.add(line!!)
                    line = it.readLine()
                } while (!line.isNullOrEmpty())

                schematic.let {
                    if (it[0].contains("#")) {
                        locks to it.drop(1)
                    } else {
                        keys to it.dropLast(1)
                    }
                }.also {
                    it.first.add(
                        it.second.fold(mutableListOf(0, 0, 0, 0, 0)) { acc, s ->
                            s.indices.forEach {
                                acc[it] += if (s[it] == '#') 1 else 0
                            }
                            acc
                        }
                    )
                }

            } while (line != null)
        }
        return emptyList()
    }

    override fun handleLine(lines: MutableList<String>, line: String) {
        // DO NOTHING
    }

    override fun part1(lines: List<String>) =
        locks.sumOf { lock ->
            keys.count { key ->
                lock.indices.all { lock[it] + key[it] <= 5 }
            }
        }

    override fun part2(lines: List<String>): Int {
        TODO("Not yet implemented")
    }
}