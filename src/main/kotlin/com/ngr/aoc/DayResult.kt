package com.ngr.aoc

import kotlinx.coroutines.Deferred
import kotlin.time.Duration

data class DayResult(
    val inputReadTime: Duration,
    val part1: Deferred<Pair<Result<String>, Duration>>,
    val part2: Deferred<Pair<Result<String>, Duration>>,
)