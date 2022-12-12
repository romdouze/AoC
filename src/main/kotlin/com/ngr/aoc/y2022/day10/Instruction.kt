package com.ngr.aoc.y2022.day10

import com.ngr.aoc.y2022.day10.Operation.ADD
import com.ngr.aoc.y2022.day10.Operation.NOOP

data class Instruction(
    val operation: Operation,
    val param: Int?,
) {
    companion object {
        fun fromString(line: String) =
            line.split(" ")
                .let {
                    Operation.fromString(it[0])
                        .let { op ->
                            when (op) {
                                NOOP -> Instruction(op, null)
                                ADD -> Instruction(op, it[1].toInt())
                            }
                        }
                }
    }
}