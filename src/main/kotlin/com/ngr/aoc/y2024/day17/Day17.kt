package com.ngr.aoc.y2024.day17

import com.ngr.aoc.Day
import java.io.InputStream

class Day17 : Day<Int, String, Int>() {

    private lateinit var computer: Computer

    override fun readInput(data: InputStream): List<Int> {
        val program = mutableListOf<Int>()

        data.bufferedReader().use {
            val a = it.readLine().removePrefix("Register A: ").toInt()
            val b = it.readLine().removePrefix("Register B: ").toInt()
            val c = it.readLine().removePrefix("Register C: ").toInt()

            it.readLine()

            computer = Computer(a, b, c)

            program.addAll(it.readLine().removePrefix("Program: ").split(",").map { it.toInt() })
        }

        return program
    }

    override fun handleLine(lines: MutableList<Int>, line: String) {
        // DO NOTHING
    }

    override fun part1(lines: List<Int>) =
        computer.copy().run(lines).output.joinToString(",")

    override fun part2(lines: List<Int>) =
        CloneFinder(computer).findClone(lines)
}