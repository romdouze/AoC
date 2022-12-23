package com.ngr.aoc.y2022.day19

data class State(
    var time: Int = 0,
    val collectors: MutableMap<Resource, Int> =
        mapOf(
            Resource.ORE to 1,
            Resource.CLAY to 0,
            Resource.OBSIDIAN to 0,
            Resource.GEODE to 0
        ).toMutableMap(),
    val resources: MutableMap<Resource, Int> =
        Resource.values()
            .associateWith { 0 }
            .toMutableMap()
) {
    fun canAfford(recipe: Recipe) =
        recipe.cost.none {
            resources[it.resource]!! < it.amount
        }

    fun buy(recipe: Recipe) {
        collectors.inc(recipe.resource, 1)
        recipe.cost
            .forEach {
                resources.inc(it.resource, -it.amount)
            }
    }

    fun collect() {
        Resource.values()
            .forEach {
                resources.inc(it, collectors[it])
            }
    }

    fun clock() {
        time++
    }

    fun clone() =
        copy(collectors = collectors.toMutableMap(), resources = resources.toMutableMap())
}