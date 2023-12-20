package com.ngr.aoc.y2023.day19

import kotlin.math.max
import kotlin.math.min


data class Item(
    val x: Int,
    val m: Int,
    val a: Int,
    val s: Int,
) {
    companion object {
        private val ITEM_PATTERN = "\\{x=(?<x>\\d+),m=(?<m>\\d+),a=(?<a>\\d+),s=(?<s>\\d+)}".toRegex()

        fun fromString(string: String) =
            ITEM_PATTERN.find(string)!!.groups.let {
                Item(
                    it["x"]!!.value.toInt(),
                    it["m"]!!.value.toInt(),
                    it["a"]!!.value.toInt(),
                    it["s"]!!.value.toInt(),
                )
            }
    }
}

data class Workflow(
    val name: String,
    val rules: List<Pair<Condition, Outcome>>,
    val default: Outcome,
) {

    val outcomes = rules.map { it.second } + default

    companion object {
        private val WORKFLOW_PATTERN = "(?<name>\\w+)\\{(?<rules>.+)}".toRegex()

        fun fromString(string: String) =
            WORKFLOW_PATTERN.find(string)!!.groups.let {
                val name = it["name"]!!.value
                val rulesStr = it["rules"]!!.value.split(",")
                val rules = rulesStr.dropLast(1)
                    .map {
                        it.split(":").let { Condition.fromString(it[0]) to Outcome.fromString(it[1]) }
                    }
                val default = rulesStr.last().let { Outcome.fromString(it) }

                Workflow(name, rules, default)
            }
    }

    fun apply(item: Item, workflows: Map<String, Workflow>) =
        rules.firstOrNull { it.first.applies(item) }
            ?.second?.apply(item, workflows)
            ?: default.apply(item, workflows)


}

data class Condition(
    val fieldSelector: Item.() -> Int,
    val operator: Operator,
    val value: Int,
) {
    companion object {
        private val CONDITION_PATTERN = "(?<field>\\w)(?<operator>[<|>])(?<value>\\d+)".toRegex()

        fun fromString(string: String) =
            CONDITION_PATTERN.find(string)!!.groups.let {
                val fieldSelector = when (it["field"]!!.value) {
                    "x" -> Item::x
                    "m" -> Item::m
                    "a" -> Item::a
                    "s" -> Item::s
                    else -> throw IllegalArgumentException("Unexpected field for [$string]")
                }

                Condition(
                    fieldSelector,
                    Operator.fromString(it["operator"]!!.value),
                    it["value"]!!.value.toInt(),
                )
            }
    }

    fun applies(item: Item) =
        item.fieldSelector().let {
            when (operator) {
                Operator.GT -> it > value
                Operator.LT -> it < value
            }
        }

    fun restrictToApply(itemRange: ItemRange): ItemRange {
        var (xRange, mRange, aRange, sRange) = itemRange

        when (fieldSelector) {
            Item::x -> xRange = restrictToApply(xRange)
            Item::m -> mRange = restrictToApply(mRange)
            Item::a -> aRange = restrictToApply(aRange)
            Item::s -> sRange = restrictToApply(sRange)
        }
        return ItemRange(xRange, mRange, aRange, sRange)
    }

    fun restrictToExclude(itemRange: ItemRange): ItemRange {
        var (xRange, mRange, aRange, sRange) = itemRange

        when (fieldSelector) {
            Item::x -> xRange = restrictToExclude(xRange)
            Item::m -> mRange = restrictToExclude(mRange)
            Item::a -> aRange = restrictToExclude(aRange)
            Item::s -> sRange = restrictToExclude(sRange)
        }
        return ItemRange(xRange, mRange, aRange, sRange)
    }

    private fun restrictToApply(range: IntRange) =
        when (operator) {
            Operator.GT -> (max(value + 1, range.first)..range.last)
            Operator.LT -> (range.first..min(value - 1, range.last))
        }

    private fun restrictToExclude(range: IntRange) =
        when (operator) {
            Operator.GT -> (range.first..min(value, range.last))
            Operator.LT -> (max(value, range.first)..range.last)
        }
}

abstract class Outcome {

    companion object {
        fun fromString(string: String) =
            when (string) {
                "A" -> A
                "R" -> R
                else -> Send(string)
            }
    }

    abstract fun apply(item: Item, workflows: Map<String, Workflow>): Boolean
}

object A : Outcome() {
    override fun apply(item: Item, workflows: Map<String, Workflow>) = true
}

object R : Outcome() {
    override fun apply(item: Item, workflows: Map<String, Workflow>) = false
}

class Send(
    private val target: String,
) : Outcome() {
    override fun apply(item: Item, workflows: Map<String, Workflow>) =
        workflows[target]!!.apply(item, workflows)
}

enum class Operator(val label: String) {
    GT(">"), LT("<");

    companion object {
        fun fromString(string: String) =
            values().first { it.label == string }
    }
}

data class ItemRange(
    val xRange: IntRange,
    val mRange: IntRange,
    val aRange: IntRange,
    val sRange: IntRange,
)