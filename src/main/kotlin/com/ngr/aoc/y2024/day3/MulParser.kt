package com.ngr.aoc.y2024.day3

object MulParser {

    private val MUL_PATTERN = "mul\\((?<x>\\d+),(?<y>\\d+)\\)".toRegex()
    private val DO = "do()"
    private val DONT = "don't()"

    fun parse(input: String) =
        with(parseEnabledRanges(input)) {
            MUL_PATTERN.findAll(input)
                .map { match ->
                    Triple(
                        match.groups["x"]!!.value.toInt(),
                        match.groups["y"]!!.value.toInt(),
                        this.any { it.contains(match.range.first) })
                }
                .toList()
        }

    private fun parseEnabledRanges(input: String): List<IntRange> {
        var startDo = 0
        val enabledRanges = mutableListOf<IntRange>()
        while (startDo < input.length) {
            var endDo = input.indexOf(DONT, startDo)
            if (endDo == -1) {
                endDo = input.lastIndex
            }
            enabledRanges.add(startDo..endDo)
            var endDont = input.indexOf(DO, endDo)
            if (endDont == -1) {
                endDont = input.length
            }
            startDo = endDont
        }

        return enabledRanges
    }
}