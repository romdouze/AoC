package com.ngr.aoc.y2025.day10

import kotlin.math.pow

data class FactoryMachine(
    val targetIndicators: List<Boolean>,
    val buttons: List<List<Int>>,
    val targetJoltage: List<Int>,
) {
    companion object {
        fun fromString(str: String) =
            str.split(" ").let { splits ->
                lateinit var targetIndicators: List<Boolean>
                val buttons = mutableListOf<List<Int>>()
                lateinit var targetJoltage: List<Int>
                splits.forEach { split ->
                    when (split[0]) {
                        '[' -> targetIndicators = split.removeSurrounding(prefix = "[", suffix = "]")
                            .toCharArray()
                            .map { it == '#' }

                        '(' -> buttons.add(
                            split.removeSurrounding(prefix = "(", suffix = ")")
                                .split(",").map { it.toInt() }
                        )

                        '{' -> targetJoltage = split.removeSurrounding(prefix = "{", suffix = "}")
                            .split(",").map { it.toInt() }
                    }
                }

                FactoryMachine(
                    targetIndicators = targetIndicators,
                    buttons = buttons,
                    targetJoltage = targetJoltage,
                )
            }
    }

    private val pressesCache = mutableMapOf<List<Boolean>, List<List<Int>>>()

    fun findFewestPressesToTargetJoltage() = findFewestPressesToJoltage(targetJoltage)

    fun findFewestPressesToJoltage(targetJoltage: List<Int>): Int {
        if (targetJoltage.any { it < 0 }) return 1000000 // No possible solution
        if (targetJoltage.all { it == 0 }) return 0

        val oddJoltage = targetJoltage.map { it % 2 == 1 }

        return findAllPressesToIndicators(oddJoltage).minOfOrNull { presses ->
            val resultingJoltage = targetJoltage
                .mapIndexed { i, j -> j - presses.count { buttons[it].contains(i) } }
                .map { it / 2 }

            2 * findFewestPressesToJoltage(resultingJoltage) + presses.size
        } ?: 1000000 // No possible solution
    }

    fun findFewestPressesToTargetIndicators() = findFewestPressesToIndicators(targetIndicators)

    fun findFewestPressesToIndicators(targetIndicators: List<Boolean>) =
        findAllPressesToIndicators(targetIndicators).minOf { it.size }

    fun findAllPressesToIndicators(targetIndicators: List<Boolean>) =
        pressesCache.getOrPut(targetIndicators) {
            enumerateButtonCombinations()
                .filter { combination ->
                    val indicators = targetIndicators.map { false }.toMutableList()
                    combination.map { buttons[it] }
                        .forEach { button ->
                            button.forEach { indicators[it] = !(indicators[it]) }
                        }
                    indicators == targetIndicators
                }
        }


    private fun enumerateButtonCombinations() =
        (0 until (2.0.pow(buttons.size).toInt())).map {
            it.toButtonCombination()
        }

    private fun Int.toButtonCombination() =
        toString(2).reversed().let { str ->
            buttons.indices.filter { str.getOrElse(it) { '0' } == '1' }
        }
}