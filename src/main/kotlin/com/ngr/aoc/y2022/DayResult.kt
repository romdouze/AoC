package com.ngr.aoc.y2022

import kotlin.time.Duration

data class DayResult(
    val inputReadTime: Duration,
    val part1: Pair<Result<String>, Duration>,
    val part2: Pair<Result<String>, Duration>
)