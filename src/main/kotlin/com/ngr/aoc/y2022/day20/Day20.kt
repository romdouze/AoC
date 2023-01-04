package com.ngr.aoc.y2022.day20

import com.ngr.aoc.y2022.Day

class Day20 : Day<Item, Int, Int>() {

    private lateinit var zeroItem: Item

    override fun handleLine(lines: MutableList<Item>, line: String) {
        val item = Item(line.toInt(), lines.size)
        lines.add(item)
        if (item.value == 0) zeroItem = item
    }

    override fun part1(lines: List<Item>): Int {
        val mixedList = CircularList(lines)

        lines
            .sortedBy { it.order }
            .forEach {
                val fromIndex = mixedList.indexOf(it)
                mixedList.move(fromIndex, fromIndex + it.value)
            }

        val zeroPosition = mixedList.indexOf(zeroItem)
        return mixedList[zeroPosition + 1000].value + mixedList[zeroPosition + 2000].value + mixedList[zeroPosition + 3000].value
    }

    override fun part2(lines: List<Item>): Int {
        TODO("Not yet implemented")
    }
}