package com.ngr.aoc.y2022

import com.ngr.aoc.y2022.Day.Companion.RUN_METHOD
import com.ngr.aoc.y2022.common.Constants.className
import com.ngr.aoc.y2022.common.Constants.filename
import com.ngr.aoc.y2022.common.print
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.functions

object Solver {
    private const val DAY = 21

    @JvmStatic
    fun main(args: Array<String>) {

        println("Running day $DAY")

        val classForDay = Class.forName(className(DAY)).kotlin
        val dayResult = classForDay.functions
            .firstOrNull { it.name == RUN_METHOD }
            ?.call(
                classForDay.createInstance(),
                filename(DAY)
            ) as DayResult?

        dayResult?.apply {
            println("input read time: ${inputReadTime.print()}")
            println("part1: ${part1.first.print()} [${part1.second.print()}]")
            println("part2: ${part2.first.print()} [${part2.second.print()}]")
        } ?: println("No result!")
    }
}