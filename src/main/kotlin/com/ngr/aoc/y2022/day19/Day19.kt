package com.ngr.aoc.y2022.day19

import com.ngr.aoc.y2022.Day
import com.ngr.aoc.y2022.day19.Resource.GEODE

class Day19 : Day<Blueprint, Int, Int>() {
    override fun handleLine(lines: MutableList<Blueprint>, line: String) {
        lines.add(Blueprint.fromString(line))
    }

    override fun part1(lines: List<Blueprint>) =
        lines.map { BlueprintEngine(it, 24) }
            .maxOf {
                it.maximizeGeode()
                    .resources[GEODE]!! * it.blueprint.id
            }

    override fun part2(lines: List<Blueprint>): Int {
        TODO("Not yet implemented")
    }
}