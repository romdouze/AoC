package com.ngr.aoc.y2022.day19

import kotlin.math.ceil

data class State(
    var clock: Int = 0,
    val collectors: ResourceAmounts = ResourceAmounts(mapOf(Resource.ORE to 1)),
    val resources: ResourceAmounts = ResourceAmounts(),
    val purchases: MutableList<Pair<Resource, Int>> = mutableListOf(),
) {

    fun whenCanBuy(recipe: Recipe) =
        if (recipe.cost
                .filter { resources[it.resource] < it.amount }
                .any { collectors[it.resource] == 0 }
        ) {
            Int.MAX_VALUE
        } else {
            waitTime(recipe) + clock
        }

    fun tryToWaitAndBuy(recipe: Recipe, timeLimit: Int) {
        val wait = 1 + waitTime(recipe)
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
                resources[resource] + collectors[resource] * remainingTime + remainingTime * (remainingTime - 1) / 2
            }

    fun waitUntil(timeLimit: Int) {
        wait(timeLimit - clock)
    }

    private fun wait(time: Int) {
        collect(time)
        clock(time)
    }

    private fun buy(recipe: Recipe) {
        collectors += recipe.resource to 1
        purchases.add(recipe.resource to clock)
        recipe.cost
            .forEach {
                resources.add(it.resource, -it.amount)
            }
    }

    private fun collect(time: Int) {
        Resource.values()
            .forEach {
                resources.add(it, collectors[it] * time)
            }
    }

    private fun clock(time: Int = 1) {
        clock += time
    }

    private fun waitTime(recipe: Recipe) =
        recipe.cost
            .filter { resources[it.resource] < it.amount }
            .maxOfOrNull { waitTimeCost(it) } ?: 0

    private fun waitTimeCost(cost: Cost) = ceil(
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