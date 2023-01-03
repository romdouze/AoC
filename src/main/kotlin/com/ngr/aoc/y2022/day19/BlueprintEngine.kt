package com.ngr.aoc.y2022.day19

import com.ngr.aoc.y2022.day19.Resource.GEODE
import java.lang.Integer.max

class BlueprintEngine(
    val blueprint: Blueprint,
    private val timeLimit: Int,
) {

    fun maximizeGeode(previousBest: Int): State {

        val initialState = State()
        var mostGeodes = initialState
        val states = ArrayDeque(listOf(initialState))

        var count = 0

        while (states.isNotEmpty()) {
            val state = states.removeFirst().clone()

            if (state.clock == timeLimit - 1) {
                val bestGeode = max(previousBest, mostGeodes.resources[GEODE])
                state.waitUntil(timeLimit)
                if (state.resources[GEODE] > bestGeode) {
                    mostGeodes = state
                    println("new Maximum: $mostGeodes")
                }
            } else {

                blueprint.recipes
                    .filter { state.canAffordByWaiting(it) }
                    .filter { state.hasReasonToBuy(it) }
                    .forEach { recipe ->
                        val newState = state.clone()
                        newState.tryToWaitAndBuy(recipe, timeLimit)

                        val bestGeode = max(previousBest, mostGeodes.potentialResource(GEODE, timeLimit))
                        if (newState.potentialResource(GEODE, timeLimit) > bestGeode) {
                            mostGeodes = newState
                            println("new Maximum: $mostGeodes")
                        }

                        if (!states.contains(newState) &&
                            newState.couldDoBetterThan(bestGeode, timeLimit)
                        ) {
                            states.addLast(newState)
                        }
                    }
            }
            count++
        }
        return mostGeodes
    }

    private fun State.couldDoBetterThan(bestGeode: Int, timeLimit: Int): Boolean {
        val remainingTime = timeLimit - clock
        val collectors = collectors[GEODE] + cart[GEODE]
        return resources[GEODE] + collectors * (collectors - 1) / 2 + remainingTime * (remainingTime - 1) / 2 > bestGeode
    }

    private fun State.hasReasonToBuy(recipe: Recipe): Boolean {
        return recipe.resource == GEODE ||
                collectors[recipe.resource] + cart[recipe.resource] < blueprint.recipes.maxOf {
            it.cost.firstOrNull { it.resource == recipe.resource }?.amount ?: 0
        }
    }
}