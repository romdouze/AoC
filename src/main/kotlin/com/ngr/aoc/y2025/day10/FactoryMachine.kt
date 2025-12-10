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

    fun findFewestPressesToTargetIndicators() =
        enumerateButtonCombinations()
            .filter { combination ->
                val indicators = targetIndicators.map { false }.toMutableList()
                combination.map { buttons[it] }
                    .forEach { button ->
                        button.forEach { indicators[it] = !(indicators[it]) }
                    }
                indicators == targetIndicators
            }
            .minOf { it.size }

    private fun enumerateButtonCombinations() =
        (1 until (2.0.pow(buttons.size).toInt())).map {
            it.toButtonCombination()
        }

    private fun Int.toButtonCombination() =
        toString(2).reversed().let { str ->
            buttons.indices.filter { str.getOrElse(it) { '0' } == '1' }
        }
}