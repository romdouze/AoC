package com.ngr.aoc

import com.ngr.aoc.Constants.className
import com.ngr.aoc.Constants.filename
import com.ngr.aoc.Day.Companion.RUN_METHOD
import kotlinx.coroutines.runBlocking
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.functions

object Solver {
    private const val YEAR = 2024
    private const val DAY = 3

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

        timed {
            runBlocking {
                dayResult?.apply {
                    println("input read time: ${inputReadTime.print()}")
                    part1.await()
                        .also {
                            println("part1: ${it.first.print()} [${it.second.print()}]")
                        }
                    part2.await()
                        .also {
                            println("part2: ${it.first.print()} [${it.second.print()}]")
                        }
                }
            } ?: println("No result!")
        }.also { println("Total running time: ${it.second.print()}") }
    }
}