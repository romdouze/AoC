package com.ngr.aoc.y2023.day22

import com.ngr.aoc.Day

class Day22 : Day<Brick, Int, Int>() {
    override fun handleLine(lines: MutableList<Brick>, line: String) {
        lines.add(
            line.split("~").let { it[0].split(",") to it[1].split(",") }
                .let {
                    Brick(
                        lines.size,
                        it.first[0].toInt()..it.second[0].toInt(),
                        it.first[1].toInt()..it.second[1].toInt(),
                        it.first[2].toInt()..it.second[2].toInt(),
                    )
                }
        )
    }

    override fun part1(lines: List<Brick>): Int {
        val supportedBy = buildSupportMap(lines)

        return lines.size -
                supportedBy.filter { it.value.size == 1 }
                    .flatMap { it.value }
                    .toSet().size
    }

    override fun part2(lines: List<Brick>): Int {
        val supportedBy = buildSupportMap(lines)

        val supportBricks = supportedBy
            .filter { it.value.size == 1 }
            .flatMap { it.value }
            .toSet()

        return supportBricks.sumOf { brick ->
            val dominoes = mutableSetOf<Int>()

            var chain = listOf(brick)
            while (chain.isNotEmpty()) {
                chain = supportedBy.filter {
                    !dominoes.contains(it.key) && chain.containsAll(it.value - dominoes)
                }.map { it.key }
                dominoes.addAll(chain)
            }

            dominoes.size
        }
    }

    private fun buildSupportMap(lines: List<Brick>): Map<Int, List<Int>> {
        val supportedBy = mutableMapOf<Int, MutableList<Int>>()
        val brickPile = mutableMapOf<Point3D, Int>()

        lines.sortedBy { it.zRange.first }
            .forEach { brick ->
                var dz = 1
                var fallenBrick = brick.fallBy(dz)
                while (fallenBrick.zRange.first > 0 && fallenBrick.points.none { brickPile.containsKey(it) }) {
                    fallenBrick = brick.fallBy(++dz)
                }
                brick.fallBy(dz - 1).also { b ->
                    brickPile.filter { fallenBrick.points.contains(it.key) }
                        .map { it.value }.toSet().forEach {
                            if (!supportedBy.containsKey(b.id)) supportedBy[b.id] = mutableListOf()
                            supportedBy[b.id]!!.add(it)
                        }
                    brickPile.putAll(b.points.associateWith { b.id })
                }
            }

        return supportedBy
    }
}