package com.ngr.aoc.y2022.day19

import kotlin.math.ceil

data class State(
    var clock: Int = 0,
    val collectors: ResourceAmounts = ResourceAmounts(
        mapOf(
            Resource.ORE to 1,
            Resource.CLAY to 0,
            Resource.OBSIDIAN to 0,
            Resource.GEODE to 0
        )
    ),
    val resources: ResourceAmounts = ResourceAmounts(
        Resource.values()
            .associateWith { 0 }
            .toMutableMap()
    ),
    val purchases: MutableList<Pair<Resource, Int>> = mutableListOf(),
) {
    fun canAffordByWaiting(recipe: Recipe) =
        recipe.cost
            .filter { resources[it.resource] < it.amount }
            .none { collectors[it.resource] == 0 }

    fun tryToWaitAndBuy(recipe: Recipe, timeLimit: Int) {
        val wait = 1 +
                (recipe.cost
                    .filter { resources[it.resource] < it.amount }
                    .maxOfOrNull { waitTime(it) } ?: 0)
        if (clock + wait <= timeLimit) {
            wait(wait)
            buy(recipe)
        } else {
            waitUntil(timeLimit)
        }
    }

    fun potentialResource(resource: Resource, timeLimit: Int) =
        (timeLimit - clock)
            .let { remainingTime ->
                resources[resource] + collectors[resource] * remainingTime + remainingTime * (remainingTime + 1) / 2
            }

    fun buy(recipe: Recipe) {
        collectors += recipe.resource to 1
        purchases.add(recipe.resource to clock)
        recipe.cost
            .forEach {
                resources.add(it.resource, -it.amount)
            }
    }

    fun waitUntil(timeLimit: Int) {
        wait(timeLimit - clock)
    }

    fun wait(time: Int) {
        collect(time)
        clock(time)
    }

    fun collect(time: Int) {
        Resource.values()
            .forEach {
                resources.add(it, collectors[it] * time)
            }
    }

    fun clock(time: Int = 1) {
        clock += time
    }

    private fun waitTime(cost: Cost) = ceil(
        (cost.amount - resources[cost.resource]) / collectors[cost.resource].toDouble()
    ).toInt()

    fun clone() =
        copy(
            collectors = collectors.clone(),
            resources = resources.clone(),
            purchases = purchases.toMutableList(),
        )

    override fun equals(other: Any?): Boolean {
        return if (other is State) {
            clock == other.clock &&
                    resources == other.resources &&
                    collectors == other.collectors
        } else {
            super.equals(other)
        }
    }

    override fun hashCode(): Int {
        return resources.hashCode() +
                31 * collectors.hashCode() +
                31 * clock
    }
}