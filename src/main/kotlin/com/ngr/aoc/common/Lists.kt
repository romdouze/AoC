package com.ngr.aoc.common

fun <T> List<T>.generateAllPairs() =
    flatMapIndexed { i, a ->
        drop(i + 1).map { a to it }
    }