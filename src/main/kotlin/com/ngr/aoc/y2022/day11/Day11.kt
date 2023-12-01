package com.ngr.aoc.y2022.day11

import com.ngr.aoc.Day
import com.ngr.aoc.y2022.day11.Monkey.Rules
import java.math.BigInteger

class Day11 : Day<Monkey, BigInteger, BigInteger>() {

    private var currentMonkey: Int = 0
    private lateinit var currentItems: List<WorryLevel>
    private lateinit var operation: (WorryLevel) -> WorryLevel
    private var divisibleBy: Int = 1
    private var throwToIfTrue: Int = 0
    private var throwToIfFalse: Int = 0

    private companion object {
        const val MONKEY_PREFIX = "Monkey "
        const val ITEMS_PREFIX = "Starting items: "
        const val OPERATION_PREFIX = "Operation: new = "
        val OPERATION_PATTERN = "(?<left>.+) (?<operator>.?) (?<right>.+)".toPattern()
        const val DIVISIBLE_BY_PREFIX = "Test: divisible by "
        const val IF_TRUE_PREFIX = "If true: throw to monkey "
        const val IF_FALSE_PREFIX = "If false: throw to monkey "
        const val SEPARATOR = "--"
    }

    override fun handleLine(lines: MutableList<Monkey>, line: String) {
        line.trim().let { l ->
            if (l.startsWith(MONKEY_PREFIX)) {
                currentMonkey = l.removePrefix(MONKEY_PREFIX).dropLast(1).toInt()
            } else if (l.startsWith(ITEMS_PREFIX)) {
                currentItems = l.removePrefix(ITEMS_PREFIX).split(", ").map { WorryLevel(it.toInt()) }
            } else if (l.startsWith(OPERATION_PREFIX)) {
                val operationStr = l.removePrefix(OPERATION_PREFIX)
                val matcher = OPERATION_PATTERN.matcher(operationStr).also { it.matches() }
                val leftStr = matcher.group("left")
                val operatorStr = matcher.group("operator")
                val rightStr = matcher.group("right")
                operation = { old ->
                    if (rightStr == "old")
                        old.square()
                    else {
                        val right = rightStr.toBigInteger()
                        when (operatorStr) {
                            "+" -> old + right
                            "*" -> old * right
                            else -> throw IllegalArgumentException("Unknown operator: $operatorStr")
                        }
                    }
                }
            } else if (l.startsWith(DIVISIBLE_BY_PREFIX)) {
                divisibleBy = l.removePrefix(DIVISIBLE_BY_PREFIX).toInt()
            } else if (l.startsWith(IF_TRUE_PREFIX)) {
                throwToIfTrue = l.removePrefix(IF_TRUE_PREFIX).toInt()
            } else if (l.startsWith(IF_FALSE_PREFIX)) {
                throwToIfFalse = l.removePrefix(IF_FALSE_PREFIX).toInt()
            } else if (l == SEPARATOR) {
                lines.add(
                    currentMonkey,
                    Monkey(
                        currentItems.toMutableList(),
                        Rules(operation, divisibleBy, throwToIfTrue, throwToIfFalse)
                    )
                )
            }
        }
    }

    override fun part1(lines: List<Monkey>) =
        processRounds(lines, 20, 3)

    override fun part2(lines: List<Monkey>) =
        processRounds(lines, 10000, 1)


    private fun processRounds(lines: List<Monkey>, rounds: Int, worryReduction: Int) =
        lines.map {
            Monkey(
                it.items.toMutableList(),
                it.rules
            )
        }
            .apply {
                repeat(rounds) {
                    forEach {
                        it.inspectAllItems(this, worryReduction)
                    }
                }
            }
            .map { it.activity }
            .sortedDescending()
            .take(2)
            .reduce { a, b -> a * b }
}