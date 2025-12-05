package com.ngr.aoc.common

import com.ngr.aoc.Day

abstract class WithInputHandling {
    protected fun <InputType> Day<InputType, *, *>.handleInput(input: String): List<InputType> {
        val output = mutableListOf<InputType>()
        input.lines().forEach {
            handleLine(output, it)
        }
        return output
    }
}