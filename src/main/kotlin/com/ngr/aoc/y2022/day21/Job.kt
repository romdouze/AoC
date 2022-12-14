package com.ngr.aoc.y2022.day21

sealed interface Job {
    fun perform(monkeys: Map<String, Monkey>): Long
}

data class Yell(
    val value: Long
) : Job {
    override fun perform(monkeys: Map<String, Monkey>) = value
}

data class Operation(
    val left: String,
    val right: String,
    val operator: Operator,
) : Job {
    override fun perform(monkeys: Map<String, Monkey>) =
        operator.operation(
            monkeys[left]!!.performJob(monkeys),
            monkeys[right]!!.performJob(monkeys)
        )
}