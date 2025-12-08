package com.ngr.aoc.y2025.day8

import kotlin.math.pow
import kotlin.math.sqrt

fun groupPair(groups: MutableList<MutableSet<Point3D>>, pair: Pair<Point3D, Point3D>) {
    val groupOfFirst = groups.find { it.contains(pair.first) }
    val groupOfSecond = groups.find { it.contains(pair.second) }

    when {
        groupOfFirst == null && groupOfSecond == null -> groups.add(mutableSetOf(pair.first, pair.second))
        groupOfFirst != null && groupOfSecond == null -> groupOfFirst.add(pair.second)
        groupOfFirst == null && groupOfSecond != null -> groupOfSecond.add(pair.first)
        groupOfFirst != null && groupOfSecond != null && groupOfFirst != groupOfSecond -> {
            groupOfFirst.addAll(groupOfSecond)
            groups.remove(groupOfSecond)
        }
    }
}

data class Point3D(val x: Double, val y: Double, val z: Double) {
    fun distanceTo(other: Point3D) =
        sqrt((x - other.x).pow(2) + (y - other.y).pow(2) + (z - other.z).pow(2))
}