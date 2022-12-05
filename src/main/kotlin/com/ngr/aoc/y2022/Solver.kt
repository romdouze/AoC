package com.ngr.aoc.y2022

import kotlin.reflect.full.createInstance
import kotlin.reflect.full.functions

object Solver {
    private const val DAY = 4

    private const val PATH = "/input/2022/"
    private const val FILENAME = "${PATH}input-$DAY.txt"
    private const val PACKAGE = "com.ngr.aoc.y2022"

    @JvmStatic fun main(args: Array<String>) {

        val classForDay = Class.forName("$PACKAGE.Day$DAY").kotlin
        val dayResult = classForDay.functions
            .firstOrNull { it.name == "run" }
            ?.call(
                classForDay.createInstance(),
                FILENAME
            ) as DayResult?

        dayResult?.apply {
            println("part1: ${part1.print()}")
            println("part2: ${part2.print()}")
        }
    }

    private fun <T> Result<T>.print() =
        if (isSuccess) getOrNull().toString() else toString()
}