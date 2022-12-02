package com.ngr.aoc.y2022

import kotlin.reflect.full.createInstance
import kotlin.reflect.full.functions


private const val DAY = 1

private const val PATH = "/input/2022/"
private const val FILENAME = "${PATH}input-$DAY.txt"
private const val PACKAGE = "com.ngr.aoc.y2022"

fun main(args: Array<String>) {

    val classForDay = Class.forName("$PACKAGE.Day$DAY").kotlin
    val result = classForDay.functions
        .firstOrNull { it.name == "run" }
        ?.call(
            classForDay.createInstance(),
            FILENAME
        ) as Result?

    result?.apply {
        println("part1: $part1")
        println("part2: $part2")
    }
}