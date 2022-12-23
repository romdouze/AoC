package com.ngr.aoc.y2022.day19

import com.ngr.aoc.y2022.day19.Resource.GEODE

class BlueprintEngine(
    val blueprint: Blueprint,
    private val timeLimit: Int,
) {

    fun maximizeGeode(): State {

        val states = ArrayDeque(listOf(State()))

        while (states.any { it.time < timeLimit }) {
            val state = states.removeFirst().clone()

            state.collect()
            state.clock()
            if (state.time < timeLimit) {
                states.addLast(state)

                if (blueprint.recipes.any { state.canAfford(it) }) {
                    val buyingStates = ArrayDeque(listOf(state))
                    while (buyingStates.isNotEmpty()) {
                        val canBuyState = buyingStates.removeFirst()
                        blueprint.recipes
                            .filter { state.canAfford(it) }
                            .forEach { recipe ->
                                val buyingState = canBuyState.clone()
                                buyingState.buy(recipe)
                                states.addLast(buyingState)
                                if (blueprint.recipes.any { buyingState.canAfford(it) }) {
                                    buyingStates.addLast(buyingState)
                                }
                            }
                    }
                }
            }
        }

        return states.maxBy { it.resources[GEODE]!! }
    }
}