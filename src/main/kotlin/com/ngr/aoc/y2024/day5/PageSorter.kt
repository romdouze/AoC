package com.ngr.aoc.y2024.day5

class PageSorter(rules: List<Pair<Int, Int>>) {

    val rulesMap = rules.groupBy { it.first }
        .mapValues { it.value.map { it.second } }

    val pageComparator = Comparator<Int> { a, b ->
        if (rulesMap[a]?.contains(b) == true) 1
        else -1
    }

    fun isSorted(pages: List<Int>) =
        pages.zipWithNext { a, b -> pageComparator.compare(a, b) > 0 }
            .all { it }
}