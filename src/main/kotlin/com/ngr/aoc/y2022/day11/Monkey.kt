package com.ngr.aoc.y2022.day11

import com.ngr.aoc.timed
import java.math.BigInteger

class Monkey(
    val items: MutableList<WorryLevel>,
    val rules: Rules,
) {
    var activity: BigInteger = BigInteger.ZERO

    fun inspectAllItems(monkeys: List<Monkey>, worryReduction: Int) {
        while (items.isNotEmpty()) {
            items.removeFirst()
                .also { timed(null) { inspectItem(it, monkeys, worryReduction) } }
        }
    }

    private fun inspectItem(item: WorryLevel, monkeys: List<Monkey>, worryReduction: Int) {
        rules.apply {
            val operated = operation(item)
            val newWorryLevel = operated / worryReduction.toBigInteger()
            val remainder = newWorryLevel.remaindersBy[divisibleBy]
            val targetMonkey = if (remainder == 0) throwToIfTrue else throwToIfFalse
            monkeys[targetMonkey].items.add(newWorryLevel)
            activity++
        }
    }

    override fun toString() =
        "Monkey: activity=$activity with ${items.size} items"

    data class Rules(
        val operation: (WorryLevel) -> WorryLevel,
        val divisibleBy: Int,
        val throwToIfTrue: Int,
        val throwToIfFalse: Int,
    )
}