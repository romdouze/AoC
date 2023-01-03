package com.ngr.aoc.y2022.day19

import com.ngr.aoc.y2022.day19.Resource.GEODE

class BlueprintEngine(
    val blueprint: Blueprint,
    private val timeLimit: Int,
) {

    fun maximizeGeode(): State {

        val initialState = State()
        var mostGeodes = initialState
        val states = ArrayDeque(listOf(initialState))

        var count = 0

        while (states.isNotEmpty()) {
            val state = states.removeFirst().clone()

            blueprint.recipes
                .filter { state.canAffordByWaiting(it) }
                .filter { state.hasReasonToBuy(it) }
                .forEach { recipe ->
                    val newState = state.clone()
                    newState.tryToWaitAndBuy(recipe, timeLimit)

                    if (newState.clock >= timeLimit) {
                        val bestGeode = mostGeodes.resources[GEODE]
                        if (newState.potentialResource(GEODE, timeLimit) > bestGeode) {
                            mostGeodes = newState
                            println("new Maximum: $mostGeodes")
                        }
                    } else {
                        if (!states.contains(newState) &&
                            newState.couldDoBetterThan(mostGeodes.resources[GEODE], timeLimit)
                        ) {
                            states.addLast(newState)
                        }
                    }
                }
            count++
        }
        println("count: $count")
        return mostGeodes
    }

    private fun State.couldDoBetterThan(bestGeode: Int, timeLimit: Int): Boolean {
        val remainingTime = timeLimit - clock
        return resources[GEODE] + collectors[GEODE] * remainingTime + remainingTime * (remainingTime + 1) / 2 > bestGeode
    }

    private fun State.hasReasonToBuy(recipe: Recipe): Boolean {
        return recipe.resource == GEODE ||
                collectors[recipe.resource] < blueprint.recipes.maxOf {
            it.cost.firstOrNull { it.resource == recipe.resource }?.amount ?: 0
        }
    }
}