package com.ngr.aoc.y2022.day12

import java.awt.Point

typealias Grid<T> = MutableList<MutableList<T>>

val List<List<*>>.width get() = this[0].size
val List<List<*>>.height get() = this.size

fun <T> List<List<T>>.at(at: Point) = this[at.y][at.x]
fun <T> List<MutableList<T>>.set(at: Point, element: T) {
    this[at.y][at.x] = element
}

fun List<List<*>>.inbound(at: Point) =
    (0 until width).contains(at.x) && (0 until height).contains(at.y)

typealias FloodCell = Pair<Int, Point>

val FloodCell.dist get() = first
val FloodCell.pos get() = second

fun <T> Grid<T>.flood(start: Point, end: Point?, canWalk: (a: T, b: T) -> Boolean): Grid<FloodCell> {
    val queue = ArrayDeque(listOf(start))

    val flood: Grid<FloodCell> =
        (0 until height).map { y ->
            (0 until width).map { x -> FloodCell(Int.MAX_VALUE, Point(x, y)) }.toMutableList()
        }.toMutableList()

    flood.set(start, FloodCell(0, start))
    while (!queue.isEmpty() && (end == null || flood.at(end).dist == Int.MAX_VALUE)) {
        val currentPoint = queue.removeFirst()
        val dist = flood.at(currentPoint).dist

        Dir.values()
            .filter {
                val next = currentPoint + it
                flood.inbound(next) &&
                        !queue.contains(next) &&
                        flood.at(next).dist > dist &&
                        canWalk(at(currentPoint), at(next))
            }
            .forEach {
                val next = currentPoint + it
                flood.set(next, FloodCell(dist + 1, next))
                queue.addLast(next)
            }
    }

    return flood
}