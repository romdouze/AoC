@file:OptIn(ExperimentalTime::class)

package com.ngr.aoc.y2022.common

import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

fun <T> timed(label: String, toTime: () -> T): T {
    var result: T
    val elapsed = measureTime { result = toTime() }
    return result.also { println("$label: ${elapsed.toString(DurationUnit.MILLISECONDS, 2)}") }
}