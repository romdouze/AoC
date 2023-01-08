package com.ngr.aoc.y2022.day22

sealed interface Instruction {
    companion object {
        private val INSTRUCTION_PATTERN = "\\d+|\\w".toRegex()

        fun fromString(line: String) =
            INSTRUCTION_PATTERN.findAll(line)
                .map { it.value }
                .map {
                    Dir.fromString(it)
                        ?.let { Rotate(it) }
                        ?: Move(it.toInt())
                }
    }
}

data class Move(val steps: Int) : Instruction

data class Rotate(val rotation: Dir) : Instruction