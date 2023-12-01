package com.ngr.aoc

import kotlin.time.Duration

data class DayResult(
    val inputReadTime: Duration,
    val part1: Pair<Result<String>, Duration>,
    val part2: Pair<Result<String>, Duration>
)