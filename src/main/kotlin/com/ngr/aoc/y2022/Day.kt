package com.ngr.aoc.y2022

import java.io.InputStream

abstract class Day<InputType, Part1OutputType, Part2OutputType> {

    fun run(fileName: String): DayResult? {
        val inputStream = object {}.javaClass.getResourceAsStream(fileName) ?: return null

        val dataLines = inputStream.use {
            readInput(inputStream)
        }

        return DayResult(
            handleResult { part1(dataLines) },
            handleResult { part2(dataLines) },
        )
    }

    private fun <T> handleResult(toRun: () -> T) =
        try {
            Result.success(toRun().toString())
        } catch (e: Throwable) {
            Result.failure(e)
        }

    private fun readInput(data: InputStream): List<InputType> {
        val lines = mutableListOf<InputType>()
        data.bufferedReader().forEachLine {
            handleLine(lines, it)
        }

        return lines
    }

    abstract fun handleLine(lines: MutableList<InputType>, line: String)

    abstract fun part1(lines: List<InputType>): Part1OutputType

    abstract fun part2(lines: List<InputType>): Part2OutputType
}