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

    fun apply(itemRange: ItemRange, workflows: Map<String, Workflow>): List<Pair<ItemRange, Boolean>> {
        val outcomes = mutableListOf<Pair<ItemRange, Boolean>>()
        var remainingRange = itemRange
        var ruleIndex = 0
        while (ruleIndex < rules.size && remainingRange.isNotEmpty()) {
            if (rules[ruleIndex].first.applies(remainingRange)) {
                val (condition, outcome) = rules[ruleIndex]
                val restrictedRange = condition.restrictToApply(remainingRange)
                outcomes.addAll(outcome.apply(restrictedRange, workflows))
                remainingRange -= restrictedRange
            }
            ruleIndex++
        }

        if (remainingRange.isNotEmpty()) {
            outcomes.addAll(default.apply(remainingRange, workflows))
        }

        return outcomes
    }
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

    fun applies(itemRange: ItemRange) =
        when (fieldSelector) {
            Item::x -> itemRange.xRange
            Item::m -> itemRange.mRange
            Item::a -> itemRange.aRange
            Item::s -> itemRange.sRange
            else -> error("fieldSelector is invalid")
        }.let { range ->
            when (operator) {
                Operator.GT -> (range.first - 1..range.last - 1).contains(value)
                Operator.LT -> (range.first + 1..range.last + 1).contains(value)
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

    private fun restrictToApply(range: IntRange) =
        when (operator) {
            Operator.GT -> (max(value + 1, range.first)..range.last)
            Operator.LT -> (range.first..min(value - 1, range.last))
        }

    override fun toString() =
        "${extractFieldName(fieldSelector.toString())}${operator.label}$value"
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
    abstract fun apply(itemRange: ItemRange, workflows: Map<String, Workflow>): List<Pair<ItemRange, Boolean>>
}

object A : Outcome() {
    override fun apply(item: Item, workflows: Map<String, Workflow>) = true
    override fun apply(itemRange: ItemRange, workflows: Map<String, Workflow>) = listOf(itemRange to true)

    override fun toString() = "-> A"
}

object R : Outcome() {
    override fun apply(item: Item, workflows: Map<String, Workflow>) = false
    override fun apply(itemRange: ItemRange, workflows: Map<String, Workflow>) = listOf(itemRange to false)

    override fun toString() = "-> R"
}

class Send(
    private val target: String,
) : Outcome() {
    override fun apply(item: Item, workflows: Map<String, Workflow>) =
        workflows[target]!!.apply(item, workflows)

    override fun apply(itemRange: ItemRange, workflows: Map<String, Workflow>) =
        workflows[target]!!.apply(itemRange, workflows)

    override fun toString() =
        "-> $target"
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
) {
    operator fun minus(other: ItemRange) =
        ItemRange(
            xRange - other.xRange,
            mRange - other.mRange,
            aRange - other.aRange,
            sRange - other.sRange,
        )

    fun isNotEmpty() =
        listOf(xRange, mRange, aRange, sRange).any { !it.isEmpty() }
}

operator fun IntRange.minus(other: IntRange) =
    subtract(other)
        .takeIf { it.isNotEmpty() }
        ?.let { (it.min()..it.max()) }
        ?: this

fun extractFieldName(string: String) =
    string.removePrefix("val com.ngr.aoc.y2023.day19.Item.").first().toString()