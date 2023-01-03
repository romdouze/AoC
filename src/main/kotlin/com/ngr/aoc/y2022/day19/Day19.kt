package com.ngr.aoc.y2022.day19

import com.ngr.aoc.y2022.Day

class Day19 : Day<Blueprint, Int, Int>() {
    override fun handleLine(lines: MutableList<Blueprint>, line: String) {
        lines.add(Blueprint.fromString(line))
    }

    override fun part1(lines: List<Blueprint>): Int {
        var bestBlueprint = Pair(-1, -1)
        for (blueprintEngine in lines.map { BlueprintEngine(it, 24) }) {
            println("**** blueprint ${blueprintEngine.blueprint.id} ****")
            val thisBest = blueprintEngine.maximizeGeode(bestBlueprint.second)
            if (thisBest.resources[Resource.GEODE] > bestBlueprint.second) {
                bestBlueprint = blueprintEngine.blueprint.id to thisBest.resources[Resource.GEODE]
            }
        }
        return bestBlueprint.first * bestBlueprint.second
    }

    override fun part2(lines: List<Blueprint>): Int {
        TODO("Not yet implemented")
    }
}