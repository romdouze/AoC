package com.ngr.aoc.y2022.day19

typealias Cost = Pair<Resource, Int>

val Cost.resource get() = first
val Cost.amount get() = second