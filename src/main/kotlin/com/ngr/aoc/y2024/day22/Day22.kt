package com.ngr.aoc.y2024.day22

import com.ngr.aoc.Day

const val NB_SECRETS = 2000

class Day22 : Day<Long, Long, Long>() {
    override fun handleLine(lines: MutableList<Long>, line: String) {
        lines.add(line.toLong())
    }

    override fun part1(lines: List<Long>) =
        lines.sumOf { PseudoBuyer(it).getNthSecret(2000) }

    override fun part2(lines: List<Long>) =
        PseudoHaggler(lines.map { PseudoBuyer(it) }).maximizeBananas()
}