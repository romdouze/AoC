package com.ngr.aoc.y2022.day19

import com.ngr.aoc.y2022.day19.Resource.*


typealias Cost = Pair<Resource, Int>

data class Blueprint(
    val id: Int,
    val recipes: Map<Resource, Set<Cost>>,
) {
    companion object {

        private val BLUEPRINT_PATTERN =
            "Blueprint (?<id>\\d+): Each ore robot costs (?<oreOreCost>\\d+) ore. Each clay robot costs (?<clayOreCost>\\d+) ore. Each obsidian robot costs (?<obsidianOreCost>\\d+) ore and (?<obsidianClayCost>\\d+) clay. Each geode robot costs (?<geodeOreCost>\\d+) ore and (?<geodeObsidianCost>\\d+) obsidian.".toRegex()

        fun fromString(line: String): Blueprint {
            val (id, oreOreCost, clayOreCost, obsidianOreCost, obsidianClayCost, geodeOreCost, geodeObsidianCost) =
                BLUEPRINT_PATTERN.find(line)!!.destructured

            return Blueprint(
                id.toInt(),
                mapOf(
                    ORE to setOf(Cost(ORE, oreOreCost.toInt())),
                    CLAY to setOf(Cost(ORE, clayOreCost.toInt())),
                    OBSIDIAN to setOf(
                        Cost(ORE, obsidianOreCost.toInt()),
                        Cost(CLAY, obsidianClayCost.toInt())
                    ),
                    GEODE to setOf(
                        Cost(ORE, geodeOreCost.toInt()),
                        Cost(OBSIDIAN, geodeObsidianCost.toInt())
                    ),
                )
            )
        }
    }
}