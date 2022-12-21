package com.ngr.aoc.y2022.day17


fun <T> MutableList<T>.cycle() =
    removeFirst().also { add(it) }

