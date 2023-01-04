package com.ngr.aoc.y2022.day20

class CircularList<T>(
    initialItems: List<T>
) {
    private val items: MutableList<T> = initialItems.toMutableList()

    fun move(fromIndex: Int, toIndex: Int) {
        val actualToIndex =
            (if (toIndex < fromIndex && toIndex <= 0) toIndex - 1
            else if (toIndex > fromIndex && toIndex >= items.size - 1) toIndex + 1
            else toIndex)
                .mod(items.size)

        val item = items[fromIndex]
        add(actualToIndex + if (actualToIndex > fromIndex) 1 else 0, item)
        items.remove(item)
    }

    fun indexOf(item: T) =
        items.toSet().indexOf(item)

    private fun add(index: Int, value: T) {
        items.add(index, value)
    }

    operator fun get(index: Int) =
        items[index.mod(items.size)]
}