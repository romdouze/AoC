package com.ngr.aoc.y2022

import kotlin.reflect.full.createInstance
import kotlin.reflect.full.functions
import kotlin.time.DurationUnit

object Solver {
    private const val DAY = 11

    private const val PATH = "/input/2022/"
    private const val FILENAME = "${PATH}input-$DAY.txt"
    private const val PACKAGE = "com.ngr.aoc.y2022.day$DAY"

    @JvmStatic
    fun main(args: Array<String>) {

        val classForDay = Class.forName("$PACKAGE.Day$DAY").kotlin
        val dayResult = classForDay.functions
            .firstOrNull { it.name == "run" }
            ?.call(
                classForDay.createInstance(),
                FILENAME
            ) as DayResult?

        dayResult?.apply {
            println("part1: ${part1.first.print()} [${part1.second.toString(DurationUnit.MILLISECONDS, 2)}]")
            println("part2: ${part2.first.print()} [${part2.second.toString(DurationUnit.MILLISECONDS, 2)}]")
        }
    }

    private fun <T> Result<T>.print() =
        if (isSuccess) getOrNull().toString() else toString()
}