package com.ngr.aoc.y2022.day17


class Cycle<T>(private val items: List<T>) {

    val size = items.size
    var cursor = 0

    fun cycle() =
        items[cursor]
            .also { shift(1) }

    fun shift(n: Long) {
        cursor = (cursor.toLong() + n).mod(items.size)
    }
}

