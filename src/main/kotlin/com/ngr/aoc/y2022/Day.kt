package com.ngr.aoc.y2022

import java.io.InputStream

abstract class Day<InputType, Part1OutputType, Part2OutputType> {

    fun run(fileName: String): Result? {
        val inputStream = object {}.javaClass.getResourceAsStream(fileName) ?: return null

        val dataLines = inputStream.use {
            readInput(inputStream)
        }

        return Result(
            part1(dataLines).toString(),
            part2(dataLines).toString(),
        )
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