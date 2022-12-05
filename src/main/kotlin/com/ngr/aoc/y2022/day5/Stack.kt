package com.ngr.aoc.y2022.day5

typealias Stack = ArrayDeque<String>

fun List<Stack>.applyOperation9000(operation: Operation) {
    this[operation.to - 1].addAll(
        this[operation.from - 1].removeN(operation.count)
    )
}

fun List<Stack>.applyOperation9001(operation: Operation) {
    this[operation.to - 1].addAll(
        this[operation.from - 1].removeN(operation.count).reversed()
    )
}

fun Stack.removeN(count: Int) =
    (1..count).map { removeLast() }

fun List<Stack>.clone() =
    map { ArrayDeque(it) }