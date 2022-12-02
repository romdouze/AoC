package com.ngr.aoc.y2021

const val INPUT = "/input/2021/input-1-1.txt"

fun main() {
    val result = object {}.javaClass.getResourceAsStream(INPUT)?.bufferedReader()?.readLines()
        ?.map { it.toInt() }
        ?.let {
            it.filterIndexed { index, _ ->
                index > 2 &&
                        it[index] + it[index - 1] + it[index - 2] > it[index - 1] + it[index - 2] + it[index - 3]
            }
        }?.count()

    print(result)
}