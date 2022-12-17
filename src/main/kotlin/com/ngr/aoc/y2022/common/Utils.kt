@file:OptIn(ExperimentalTime::class)

package com.ngr.aoc.y2022.common

import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

fun <T> timed(label: String? = null, toTime: () -> T): Pair<T, Duration> {
    var result: T
    val elapsed = measureTime { result = toTime() }
    return result to elapsed
        .also { label?.let { println("$it: ${elapsed.print()}") } }
}

fun Duration.print() =
    toString(DurationUnit.MILLISECONDS, 2)