package com.ngr.aoc.y2024.day4

import com.ngr.aoc.Day
import com.ngr.aoc.y2024.day4.Dir.Companion.diagonals

class Day4 : Day<List<Char>, Int, Int>() {
    override fun handleLine(lines: MutableList<List<Char>>, line: String) {
        lines.add(line.toList())
    }

    override fun part1(lines: List<List<Char>>) =
        lines.indices.sumOf { y ->
            lines[y].indices.sumOf { x ->
                if (lines[y][x] == XMAS.first()) {
                    Dir.entries.count { dir ->
                        XMAS.indices.drop(1).all { i ->
                            val xi = x + dir.dx * i
                            val yi = y + dir.dy * i
                            lines.inside(xi, yi) && lines[yi][xi] == XMAS[i]
                        }
                    }
                } else 0
            }
        }

    override fun part2(lines: List<List<Char>>) =
        (1..lines.lastIndex - 1).sumOf { y ->
            (1..lines[y].lastIndex - 1).count { x ->
                lines[y][x] == 'A' &&
                        diagonals.let {
                            val ends = it.map { dir ->
                                val xi = x + dir.dx
                                val yi = y + dir.dy
                                Triple(lines[yi][xi], xi, yi)
                            }
                            val partition = ends.partition { it.first == 'M' }
                            ends.all { it.first in listOf('M', 'S') } &&
                                    partition.first.size == 2 &&
                                    (partition.first[0].second == partition.first[1].second ||
                                            partition.first[0].third == partition.first[1].third)
                        }
            }
        }
}