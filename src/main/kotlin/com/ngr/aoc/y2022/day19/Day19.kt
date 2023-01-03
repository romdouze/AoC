package com.ngr.aoc.y2022.day19

import com.ngr.aoc.y2022.Day
import com.ngr.aoc.y2022.day19.Resource.GEODE

class Day19 : Day<Blueprint, Int, Int>() {
    override fun handleLine(lines: MutableList<Blueprint>, line: String) {
        lines.add(Blueprint.fromString(line))
    }

    override fun part1(lines: List<Blueprint>) =
        lines
            .map { BlueprintEngine(it, 24) }
            .fold(0) { sum, blueprintEngine ->
                println("**** blueprint ${blueprintEngine.blueprint.id} ****")
                val thisBest = blueprintEngine.maximizeGeode()
                sum + thisBest.resources[GEODE] * blueprintEngine.blueprint.id
            }

    override fun part2(lines: List<Blueprint>) =
        lines.take(3)
            .map { BlueprintEngine(it, 32) }
            .fold(1) { acc, blueprintEngine ->
                println("**** blueprint ${blueprintEngine.blueprint.id} ****")
                val thisBest = blueprintEngine.maximizeGeode()
                acc * thisBest.resources[GEODE]
            }
}