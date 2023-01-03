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
    val purchases: MutableList<ResourceAmounts> = mutableListOf(),
    val cart: ResourceAmounts = ResourceAmounts()
) {
    fun canAffordByWaiting(recipe: Recipe) =
        recipe.cost
            .filter { resources[it.resource] < it.amount }
            .none { collectors[it.resource] + cart[it.resource] == 0 }

    fun tryToWaitAndBuy(recipe: Recipe, timeLimit: Int) {
        if (canAfford(recipe)) {
            addToCart(recipe)
        } else {
            wait(1)
            buy()
            val wait = recipe.cost
                .filter { resources[it.resource] < it.amount }
                .maxOfOrNull { waitTime(it) } ?: 0
            if (clock + wait <= timeLimit) {
                wait(wait)
                addToCart(recipe)
            } else {
                waitUntil(timeLimit)
            }
        }
    }

    fun potentialResource(resource: Resource, timeLimit: Int) =
        resources[resource] + (collectors[resource] + cart[resource]) * (timeLimit - clock)

    fun canAfford(recipe: Recipe) =
        recipe.cost.none {
            resources[it.resource] < it.amount
        }

    fun addToCart(recipe: Recipe) {
        cart.add(recipe.resource, 1)
        recipe.cost
            .forEach {
                resources.add(it.resource, -it.amount)
            }
    }

    fun buy() {
        if (cart.isNotEmpty()) {
            collectors.addAll(cart)
            purchases.add(cart.clone())
            cart.clear()
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
            purchases = purchases.map { it.clone() }.toMutableList(),
            cart = cart.clone()
        )

    override fun equals(other: Any?): Boolean {
        return if (other is State) {
            clock == other.clock &&
                    resources == other.resources &&
                    collectors == other.collectors &&
                    cart == other.cart
        } else {
            super.equals(other)
        }
    }

    override fun hashCode(): Int {
        return resources.hashCode() +
                31 * collectors.hashCode() +
                31 * cart.hashCode() +
                31 * clock
    }
}