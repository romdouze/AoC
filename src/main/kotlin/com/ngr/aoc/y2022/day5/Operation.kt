package com.ngr.aoc.y2022.day5

data class Operation(
    val count: Int,
    val from: Int,
    val to: Int,
) {
    companion object {
        private val pattern = "move (\\d+) from (\\d+) to (\\d+)".toRegex()

        fun fromString(input: String): Operation {
            val (count, from, to) = pattern.find(input)!!.destructured
            return Operation(count.toInt(), from.toInt(), to.toInt())
        }
    }
}