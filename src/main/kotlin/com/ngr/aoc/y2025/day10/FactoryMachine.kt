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

    fun findFewestPressesToTargetJoltage(): Int {
        val toVisit = ArrayDeque(listOf(List(buttons.size) { 0 }))

        var fewestPresses = Int.MAX_VALUE
        while (toVisit.isNotEmpty()) {
            val presses = toVisit.removeFirst()
            val joltage = computeJoltage(presses)

            if (joltage.withIndex().none { it.value > targetJoltage[it.index] }) {
                if (joltage == targetJoltage) {
                    val nbPresses = presses.sum()
                    if (nbPresses < fewestPresses) {
                        fewestPresses = nbPresses
                    }
                }

                presses.indices.forEach {
                    val newPresses = presses.toMutableList()

                    newPresses[it] = newPresses[it] + 1

                    if (!(toVisit.contains(newPresses))) {
                        toVisit.add(newPresses)
                    }
                }
            }
        }

        return fewestPresses
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

    private fun computeJoltage(presses: List<Int>): List<Int> {
        val joltage = mutableMapOf<Int, Int>()
        presses.forEachIndexed { buttonIndex, nb ->
            buttons[buttonIndex].forEach { joltage[it] = joltage.getOrDefault(it, 0) + nb }
        }

        return joltage.entries.sortedBy { it.key }.map { it.value }.toList()
    }
}