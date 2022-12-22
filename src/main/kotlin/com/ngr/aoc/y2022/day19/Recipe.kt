package com.ngr.aoc.y2022.day19

typealias Recipe = Pair<Resource, Set<Cost>>

val Recipe.resource get() = first
val Recipe.cost get() = second