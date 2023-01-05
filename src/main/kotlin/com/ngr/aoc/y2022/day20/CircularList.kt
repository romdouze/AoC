package com.ngr.aoc.y2022.day20

class CircularList<T>(
    initialItems: List<T>
) {
    private val items: MutableList<T> = initialItems.toMutableList()

    fun move(fromIndex: Int, toIndex: Long) {
        add(toIndex, items.removeAt(fromIndex))
    }

    fun indexOf(item: T) =
        items.toSet().indexOf(item)

    private fun add(index: Long, value: T) {
        items.add(index.mod(items.size), value)
    }

    operator fun get(index: Int) =
        items[index.mod(items.size)]
}