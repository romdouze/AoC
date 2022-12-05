package com.ngr.aoc.y2022.day2

typealias Duel = Pair<Shape, Shape>

fun Duel.score() =
    second.fight(first) + second.score