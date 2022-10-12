package com.ngr.aoc

import java.io.File

const val INPUT = "input/input-1-1.txt"

fun main() {
    val result = File(INPUT).readLines()
        .map { it.toInt() }
        .let {
            it.filterIndexed { index, _ ->
                index > 2 &&
                        it[index] + it[index - 1] + it[index - 2] > it[index - 1] + it[index - 2] + it[index - 3]
            }
        }.count()

    print(result)
}