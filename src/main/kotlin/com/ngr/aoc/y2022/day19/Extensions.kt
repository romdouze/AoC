package com.ngr.aoc.y2022.day19

fun MutableMap<Resource, Int>.inc(resource: Resource, n: Int?) {
    this[resource] = this[resource]!! + (n ?: 0)
}