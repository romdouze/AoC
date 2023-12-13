package com.ngr.aoc

import com.ngr.aoc.Constants.className
import com.ngr.aoc.Constants.filename
import com.ngr.aoc.Day.Companion.RUN_METHOD
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.functions

object Solver {
    private const val YEAR = 2023
    private const val DAY = 13

    @JvmStatic
    fun main(args: Array<String>) {

        println("Running day $DAY [$YEAR]")

        val classForDay = Class.forName(className(DAY, YEAR)).kotlin
        val dayResult = classForDay.functions
            .firstOrNull { it.name == RUN_METHOD }
            ?.call(
                classForDay.createInstance(),
                filename(DAY, YEAR)
            ) as DayResult?

        dayResult?.apply {
            println("input read time: ${inputReadTime.print()}")
            println("part1: ${part1.first.print()} [${part1.second.print()}]")
            println("part2: ${part2.first.print()} [${part2.second.print()}]")
        } ?: println("No result!")
    }
}