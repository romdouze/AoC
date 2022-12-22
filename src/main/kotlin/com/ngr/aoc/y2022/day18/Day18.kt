package com.ngr.aoc.y2022.day18

import com.ngr.aoc.y2022.Day
import java.lang.Integer.max
import java.lang.Integer.min

class Day18 : Day<Point3D, Int, Int>() {
    override fun handleLine(lines: MutableList<Point3D>, line: String) {
        val split = line.split(",")
        lines.add(Point3D(split[0].toInt(), split[1].toInt(), split[2].toInt()))
    }

    override fun part1(lines: List<Point3D>) =
        assembleCubes(lines).values.sumOf { it.openSides.values.count { it } }

    override fun part2(lines: List<Point3D>): Int {
        val allCubes = assembleCubes(lines)
        markExteriorSides(allCubes)

        return allCubes.values.sumOf { it.exteriorSides.values.count { it } }
    }

    private fun assembleCubes(lines: List<Point3D>): MutableMap<Point3D, LavaCube> {
        val allCubes = mutableMapOf<Point3D, LavaCube>()

        lines.forEach { pos ->
            val newCube = LavaCube(pos)

            Dir.values()
                .forEach { dir ->
                    allCubes[newCube.pos + dir]?.also {
                        it.openSides[dir.opposit] = false
                        newCube.openSides[dir] = false
                    }
                }

            allCubes[newCube.pos] = newCube
        }
        return allCubes
    }

    private fun markExteriorSides(allCubes: MutableMap<Point3D, LavaCube>) {
        var (minX, maxX) = Pair(Int.MAX_VALUE, Int.MIN_VALUE)
        var (minY, maxY) = Pair(Int.MAX_VALUE, Int.MIN_VALUE)
        var (minZ, maxZ) = Pair(Int.MAX_VALUE, Int.MIN_VALUE)
        allCubes.keys.forEach {
            minX = min(minX, it.x)
            maxX = max(maxX, it.x)
            minY = min(minY, it.y)
            maxY = max(maxY, it.y)
            minZ = min(minZ, it.z)
            maxZ = max(maxZ, it.z)
        }

        val xRange = (minX - 1..maxX + 1)
        val yRange = (minY - 1..maxY + 1)
        val zRange = (minZ - 1..maxZ + 1)

        val start = Point3D(xRange.first, yRange.first, zRange.first)

        val toVisit = ArrayDeque(listOf(start))
        val visited = mutableSetOf<Point3D>()

        while (toVisit.isNotEmpty()) {
            val point = toVisit.removeFirst()

            Dir.values()
                .map { it to point + it }
                .filterNot { visited.contains(it.second) }
                .filterNot { toVisit.contains(it.second) }
                .filter { it.second.inbound(xRange, yRange, zRange) }
                .forEach {
                    allCubes[it.second]
                        ?.apply {
                            exteriorSides[it.first] = true
                        }
                        ?: run {
                            toVisit.add(it.second)
                        }
                }

            visited.add(point)
        }
    }

    private fun Point3D.inbound(xRange: IntRange, yRange: IntRange, zRange: IntRange) =
        xRange.contains(x) && yRange.contains(y) && zRange.contains(z)
}