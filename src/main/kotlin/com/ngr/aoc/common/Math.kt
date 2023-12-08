package com.ngr.aoc.common

fun lcm(vararg n: Int): Int {
    val x1 = n.toList()

    val x = x1.toMutableList()
    while (x.toSet().size > 1) {
        x.min().also { min ->
            x.indexOfFirst { it == min }
                .also { x[it] = min + x1[it] }
        }
    }
    return x[0]
}