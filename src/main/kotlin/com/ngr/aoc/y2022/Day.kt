package com.ngr.aoc.y2022

import com.ngr.aoc.y2022.common.timed
import java.io.InputStream

abstract class Day<InputType, Part1OutputType, Part2OutputType> {

    companion object {
        const val RUN_METHOD = "runAll"
    }

    fun runAll(fileName: String): DayResult? {
        val inputStream = object {}.javaClass.getResourceAsStream(fileName) ?: run {
            println("Input file not found: $fileName")
            return null
        }

        val (dataLines, readTime) = timed {
            inputStream.use {
                readInput(it)
            }
        }


        return DayResult(
            readTime,
            timed { handleResult { part1(dataLines) } },
            timed { handleResult { part2(dataLines) } },
        )
    }

    private fun <T> handleResult(toRun: () -> T) =
        try {
            Result.success(toRun().toString())
        } catch (e: Throwable) {
            Result.failure(e)
        }

    protected open fun readInput(data: InputStream): List<InputType> {
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