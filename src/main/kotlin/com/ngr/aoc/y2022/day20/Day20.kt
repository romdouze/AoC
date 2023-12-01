package com.ngr.aoc.y2022.day20

import com.ngr.aoc.Day

class Day20 : Day<Item, Long, Long>() {

    private companion object {
        private const val DECRYPTION_KEY = 811589153L
    }

    private lateinit var zeroItem: Item

    override fun handleLine(lines: MutableList<Item>, line: String) {
        val item = Item(line.toLong(), lines.size)
        lines.add(item)
        if (item.value == 0L) zeroItem = item
    }

    override fun part1(lines: List<Item>) =
        mix(lines).extractCoordinates()

    override fun part2(lines: List<Item>) =
        mix(lines.map { Item(it.value * DECRYPTION_KEY, it.order) }, 10)
            .extractCoordinates()

    private fun mix(items: List<Item>, iterations: Int = 1): CircularList<Item> {
        val mixedList = CircularList(items)

        items
            .sortedBy { it.order }
            .also { sortedItems ->
                repeat(iterations) {
                    sortedItems.forEach {
                        val fromIndex = mixedList.indexOf(it)
                        mixedList.move(fromIndex, fromIndex + it.value)
                    }
                }
            }

        return mixedList
    }

    private fun CircularList<Item>.extractCoordinates(): Long {
        val zeroPosition = indexOf(zeroItem)
        return this[zeroPosition + 1000].value + this[zeroPosition + 2000].value + this[zeroPosition + 3000].value
    }
}