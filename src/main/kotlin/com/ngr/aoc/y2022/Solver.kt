package com.ngr.aoc.y2022

import com.ngr.aoc.y2022.common.print
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.functions

object Solver {
    private const val DAY = 15

    private const val PATH = "/input/2022/"
    private const val FILENAME = "${PATH}input-$DAY.txt"
    private const val PACKAGE = "com.ngr.aoc.y2022.day$DAY"

    @JvmStatic
    fun main(args: Array<String>) {

        println("Running day $DAY")

        val classForDay = Class.forName("$PACKAGE.Day$DAY").kotlin
        val dayResult = classForDay.functions
            .firstOrNull { it.name == "run" }
            ?.call(
                classForDay.createInstance(),
                FILENAME
            ) as DayResult?

        dayResult?.apply {
            println("input read time: ${inputReadTime.print()}")
            println("part1: ${part1.first.print()} [${part1.second.print()}]")
            println("part2: ${part2.first.print()} [${part2.second.print()}]")
        } ?: println("No result!")
    }

    private fun <T> Result<T>.print() =
        if (isSuccess) getOrNull().toString() else toString()
}