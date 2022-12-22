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

            if (state.clock == timeLimit - 1) {
                val newState = state.clone()
                newState.waitUntil(timeLimit)
                if (newState.resources[GEODE] > mostGeodes.resources[GEODE]) {
                    mostGeodes = newState
                    println("new Maximum: $mostGeodes")
                }
            } else {
                val recipesToTry =
                    blueprint.recipes
                        .filter { it.resource == GEODE }
                        .filter { (state.whenCanBuy(it)) == state.clock }
                        .ifEmpty {
                            blueprint.recipes
                                .filter {
                                    state.whenCanBuy(it).let { boughtAt ->
                                        boughtAt <= timeLimit - 1
                                    }
                                }
                                .filter { state.hasReasonToBuy(it) }
                        }

                recipesToTry
                    .forEach { recipe ->
                        val newState = state.clone()
                        newState.tryToWaitAndBuy(recipe, timeLimit)

                        if (newState.clock >= timeLimit) {
                            if (newState.resources[GEODE] > mostGeodes.resources[GEODE]) {
                                mostGeodes = newState
                                println("new Maximum: $mostGeodes")
                            }
                        } else {
                            if (!states.contains(newState) &&
                                newState.potentialResource(GEODE, timeLimit) > mostGeodes.resources[GEODE]
                            ) {
                                states.addLast(newState)
                            }
                        }
                    }
            }

            count++
        }
        println("count: $count")
        return mostGeodes
    }

    private fun State.hasReasonToBuy(recipe: Recipe): Boolean {
        return recipe.resource == GEODE ||
                collectors[recipe.resource] < blueprint.recipes.maxOf {
            it.cost.firstOrNull { it.resource == recipe.resource }?.amount ?: 0
        }
    }
}