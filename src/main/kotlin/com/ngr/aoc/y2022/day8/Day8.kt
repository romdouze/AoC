package com.ngr.aoc.y2022.day8

import com.ngr.aoc.Day
import java.awt.Point

class Day8 : Day<String, Int, Int>() {

    private val heights: MutableList<List<Int>> = mutableListOf()
    private val visibility: MutableList<MutableList<Boolean>> = mutableListOf()

    override fun handleLine(lines: MutableList<String>, line: String) {
        heights.add(line.map { it.digitToInt() })
        visibility.add(line.map { false }.toMutableList())
    }

    override fun part1(lines: List<String>): Int {
        Dir.values()
            .forEach { dir ->
                dir.slices(heights)
                    .map { slice ->
                        slice.map { it.first }.visibility()
                            .forEachIndexed { i, visible ->
                                val pos = slice[i].second
                                visibility[pos.y][pos.x] = visibility[pos.y][pos.x] || visible
                            }
                    }
            }

        return visibility.sumOf { row -> row.count { it } }
    }

    override fun part2(lines: List<String>) =
        heights.flatMapIndexed { y, row ->
            List(row.size) { x ->
                heights.scenicScore(Point(x, y))
            }
        }.max()

    private fun List<Int>.visibility() =
        mapIndexed { i, height ->
            (0 until i)
                .none { this[it] >= height }
        }

    private fun MutableList<List<Int>>.scenicScore(pos: Point) =
        Dir.values().map { dir ->
            val p = Point(pos).move(dir)
            var count = 1
            while (inbound(p) && this[p.y][p.x] < this[pos.y][pos.x]) {
                count++
                p.move(dir)
            }
            if (!inbound(p)) count--
            count
        }.reduce { acc, i -> acc * i }

    private fun MutableList<List<Int>>.inbound(pos: Point) =
        widthRange().contains(pos.x) && heightRange().contains(pos.y)
}
